package Swizzard.System.Updater.Proxies
{
	import com.adobe.utils.DateUtil;
	
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.events.SQLEvent;
	import flash.events.SecurityErrorEvent;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.net.Responder;
	import flash.net.URLLoader;
	import flash.net.URLLoaderDataFormat;
	import flash.net.URLRequest;
	import flash.utils.ByteArray;
	
	import mx.controls.Alert;
	
	import Swizzard.Data.Proxies.DataProxy;
	import Swizzard.System.Updater.Commands.UpdateCommand;
	import Swizzard.System.Models.DatabaseUpdateInfo;
	
	import org.puremvc.as3.patterns.proxy.Proxy;

	
	public class DatabaseUpdaterProxy extends Proxy
	{
		// must find a more consistent way of doing the versioning for databases:
		// - somehow keep up with the app versioning
		// - but allow separate (more than one) updates between 2 consecutive app updates
		public static const INITIAL_DATABASE_VERSION:uint	= 24; 
		public static const NAME:String	 			= "DatabaseUpdaterProxy";
		
		// RADU_TODO_MUST_UNDO: also undo the content of the .xml
//		public static const UPDATE_URL:String		= "C:/radua/work_related_projects/simple_select/develop_test_builds_release/SimpleSelect_2_1_9/versions/DatabaseUpdate_2_1_9.xml"; 
		public static const UPDATE_URL:String		= "https://www.swizzard.usa.siemens.com/Data2/versions/DatabaseUpdate_2_1_10.xml";
		
		
		public static const UPDATE_START:String		= "databaseUpdateStart";
		public static const UPDATE_PROGRESS:String	= "databaseUpdateProgress";
		public static const UPDATE_COMPLETE:String	= "databaseUpdateComplete";
		
		private static const UPDATE_MODE:uint	= 1;
		private static const DOWNLOAD_MODE:uint	= 2;
		
		
		private var updateInformation:DatabaseUpdateInfo;
		private var lastContentDownloadDate:Date;
		private var currentDbVersion:int;
		private var loader:URLLoader;
		private var mode:uint;
		
		
		public function DatabaseUpdaterProxy()
		{
			super(NAME);
			
			mode = 0;
			
			loader	= new URLLoader();
			loader.addEventListener(ProgressEvent.PROGRESS,				progressHandler, false, 0, true);
			loader.addEventListener(Event.COMPLETE, 					completeHandler, false, 0, true);
			loader.addEventListener(IOErrorEvent.IO_ERROR,				ioErrorHandler, false, 0, true);
			loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, 	securityErrorHandler, false, 0, true);
			
			// Local DB Version Number Storage
			var updateFile:File		= File.applicationStorageDirectory.resolvePath("dbUpdate");
			var stream:FileStream	= new FileStream();
			
			if (!updateFile.exists) {
				// Default to version 1
				stream.open(updateFile, FileMode.WRITE);
				stream.writeInt(INITIAL_DATABASE_VERSION);
				stream.writeObject(null);
				currentDbVersion = INITIAL_DATABASE_VERSION;
				stream.close();
			}
			else {
				// Read Version From Storage
				stream.open(updateFile, FileMode.READ);
				currentDbVersion		= stream.readInt();
				lastContentDownloadDate	= stream.readObject() as Date;
				stream.close();
			}
			// Check for Version Update after Application Update is complete
		}
		
		
		public function resetDatabaseVersion():void {
			currentDbVersion		= INITIAL_DATABASE_VERSION;
			
			var stream:FileStream	= new FileStream();
			var updateFile:File		= File.applicationStorageDirectory.resolvePath("dbUpdate");
			stream.open(updateFile, FileMode.WRITE);
			stream.writeInt(INITIAL_DATABASE_VERSION);
			stream.writeObject(lastContentDownloadDate);
			stream.close();
			
			// mode < 1 means there is no action in progress
			if (mode < 1)
				checkForUpdate();
		}
		
		
		public function checkForUpdate():void {
			mode = UPDATE_MODE;
			loader.dataFormat = URLLoaderDataFormat.TEXT;
			loader.load(new URLRequest(UPDATE_URL));
		}
		
		
		public function updateNow():void {
			mode	= DOWNLOAD_MODE;
			var request:URLRequest	= new URLRequest(updateInformation.dataFileUri);
			request.idleTimeout		= 120000;
			loader.dataFormat		= URLLoaderDataFormat.BINARY;
			loader.load(request);
			sendNotification(UPDATE_START);
		}
		
		
		public function logContentDownload():void {
			lastContentDownloadDate = new Date();
			persistDbInfo();
		}
		
		
		public function importDatabaseFile(file:File):void {
			if (!file.exists)
				return;
			
			// Read File
			var data:ByteArray	= new ByteArray();
			var importStream:FileStream	= new FileStream();
			importStream.open(file, FileMode.READ);
			importStream.readBytes(data);
			importStream.close();
			
			// Release Data file
			facade.removeProxy(DataProxy.NAME);
			
			// Write New Data file
			var fileStream:FileStream	= new FileStream();
			fileStream.open(DataProxy.DATA_FILE, FileMode.WRITE);
			fileStream.writeBytes(data);
			fileStream.close();
			
			// Attach New Data File
			facade.registerProxy(new DataProxy(true));
			sendNotification(UPDATE_COMPLETE, "database");
		}
		
		
		private function completeHandler(event:Event):void {
			switch (mode) {
				case UPDATE_MODE:
				{
					// Notify User if Update is Pending
					var result:XML		= new XML(loader.data);
					updateInformation	= DatabaseUpdateInfo.BuildFromXml(result);
					
					if (updateInformation.version > currentDbVersion) {
						// Send Notification Database Update Pending.
						sendNotification(UpdateCommand.UPDATE_PENDING, UpdateCommand.DATABASE_TYPE);
					}
					else if (lastContentDownloadDate && (DateUtil.compareDates(updateInformation.contentDateModified, lastContentDownloadDate) < 0)) {
						// Send Notification Database Update Pending.
						sendNotification(UpdateCommand.UPDATE_PENDING, UpdateCommand.CONTENT_TYPE);
					}						
					loader.data	= null;
					break;
				}
				
				case DOWNLOAD_MODE:
				{
					mode	= 0;
					// Close Existing Database to prepare for update
					var dataProxy:DataProxy	= facade.retrieveProxy(DataProxy.NAME) as DataProxy;
					dataProxy.closeAsync(new Responder(updateDatabaseClosed));
					break;
				}
			}
		}
		
		
		private function updateDatabaseClosed(event:SQLEvent):void {
			// Release Data file
			facade.removeProxy(DataProxy.NAME);
			
			// Write New Data file
			var fileStream:FileStream	= new FileStream();
			fileStream.open(DataProxy.DATA_FILE, FileMode.WRITE);
			fileStream.writeBytes(loader.data as ByteArray);
			fileStream.close();
			
			loader.data	= null;
			
			// Attach New Data File
			facade.registerProxy(new DataProxy(true));
			
			// Persist New Version Number
			currentDbVersion = updateInformation.version;
			persistDbInfo();
			sendNotification(UPDATE_COMPLETE, UpdateCommand.DATABASE_TYPE);	
		}
		
		
		private function persistDbInfo():void {
			var updateFile:File	= File.applicationStorageDirectory.resolvePath("dbUpdate");
			var stream:FileStream = new FileStream();
			stream.open(updateFile, FileMode.WRITE);
			stream.writeInt(currentDbVersion);
			stream.writeObject(lastContentDownloadDate);
			stream.close();
		}
		
		
		private function progressHandler(event:ProgressEvent):void {
			if (mode == DOWNLOAD_MODE) {
				sendNotification(UPDATE_PROGRESS, event);
			}
		}
		

		private function ioErrorHandler(event:IOErrorEvent):void {
			// I/O Error
			if (UPDATE_MODE) {
				Alert.show("Error accessing DB update server.");	
			}
			else {
				Alert.show("Error downloading DB database");
			}
			sendNotification(UPDATE_COMPLETE, UpdateCommand.DATABASE_TYPE);	
		}
		
		
		private function securityErrorHandler(event:SecurityErrorEvent):void {
			// Security Error
			if (UPDATE_MODE) {
				Alert.show("Error accessing DB update server.");	
			}
			else {
				Alert.show("Error downloading database");
			}
			sendNotification(UPDATE_COMPLETE, UpdateCommand.DATABASE_TYPE);	
		}
	}
}