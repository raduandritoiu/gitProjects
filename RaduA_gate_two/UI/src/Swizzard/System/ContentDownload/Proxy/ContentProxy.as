package Swizzard.System.ContentDownload.Proxy
{
	import flash.data.SQLConnection;
	import flash.data.SQLMode;
	import flash.data.SQLResult;
	import flash.data.SQLStatement;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.events.SQLErrorEvent;
	import flash.events.SQLEvent;
	import flash.events.SecurityErrorEvent;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.net.URLLoader;
	import flash.net.URLLoaderDataFormat;
	import flash.net.URLRequest;
	import flash.utils.ByteArray;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IViewCursor;
	import mx.controls.Alert;
	
	import Swizzard.Data.Models.SiemensDocument;
	import Swizzard.System.Updater.Commands.UpdateCommand;
	
	import org.puremvc.as3.patterns.proxy.Proxy;
	
	import utils.LogUtils;
	
	
	public class ContentProxy extends Proxy
	{
		public static const NAME:String					= "ContentProxy";
		
		public static const DOWNLOAD_STARTED:String		= "downloadStarted_234gp";
		public static const DOWNLOAD_PROGRESS:String	= "downloadProgress_as435";
		public static const DOWNLOAD_COMPLETE:String	= "downloadComplete_s45gj";
		public static const DOWNLOAD_ENDED:String		= "downloadEnded_asd3";
		
		
		public static const contentStorageFolder:File	= File.applicationStorageDirectory.resolvePath("vsstContent");
		
		
		// the database connection - that shouldn't be here
		private var database:SQLConnection;
		
		private var loader:URLLoader;
		
		private var contentDownloadQueue:ArrayCollection;
		private var contentDownloadCursor:IViewCursor;
		private var duplicateTable:Dictionary;
		
		private var downloadPending:Boolean;
		private var overwriteExisting:Boolean;
		
		
		public function ContentProxy(data:Object=null) {
			super(NAME, data);
		}
		
		
		override public function onRegister():void {
			// try to connect to the data base
			var dbFile:File	= File.applicationDirectory.resolvePath("parts.db");
			if (!dbFile.exists) {
				return;
			}
			
			database = new SQLConnection();
			database.addEventListener(SQLErrorEvent.ERROR,	connectionErrorHandler, false, 0, true);
			database.addEventListener(SQLEvent.OPEN, 		connectionOpenHandler, false, 0, true);
			try {
				database.openAsync(dbFile, SQLMode.READ);	
			}
			catch (err:Error) {
				// Proxy Initialization Failed (Remove It)
				facade.removeProxy(NAME);
			}
			
			// create the loader for the actual documents
			loader				= new URLLoader();
			loader.dataFormat	= URLLoaderDataFormat.BINARY;
			loader.addEventListener(IOErrorEvent.IO_ERROR,				downloadIOErrorHandler, false, 0, true);
			loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR,	downloadSecurityErrorHandler, false, 0, true);
			loader.addEventListener(Event.COMPLETE,						downloadCompleteHandler, false, 0, true);
			loader.addEventListener(ProgressEvent.PROGRESS, 			downloadProgressHandler, false, 0, true);
		}
		
		
		override public function onRemove():void {
			database.close();
		}
		
		
		private function connectionErrorHandler(event:SQLErrorEvent):void {
			Alert.show("Error Accessing Product Database");
			facade.removeProxy(NAME);
		}
		
		
		private function connectionOpenHandler(event:SQLEvent):void {
			if (downloadPending) {
				downloadPending	= false;
				downloadCatalog(overwriteExisting);
			}
		}
		
		
		public function downloadCatalog(overwrite:Boolean = false):void {
			overwriteExisting = overwrite;
			
			// pend the download if no DB connection
			if (!database.connected) {
				downloadPending	= true;
				return;
			}
			
			// create SQL statement and execute
			var statement:SQLStatement	= new SQLStatement();
			statement.sqlConnection		= database;
			statement.text				= "SELECT * FROM Documents";
			statement.itemClass			= SiemensDocument;
			statement.addEventListener(SQLErrorEvent.ERROR, SQLDocsErrorHandler, false, 0, true);
			statement.addEventListener(SQLEvent.RESULT, 	SQLDocsResultHandler, false, 0, true);
			statement.execute();
			
			sendNotification(DOWNLOAD_STARTED);
		}
		
		
		private function SQLDocsErrorHandler(event:SQLErrorEvent):void {
			event.target.removeEventListener(SQLEvent.RESULT, 		SQLDocsResultHandler);
			event.target.removeEventListener(SQLErrorEvent.ERROR,	SQLDocsErrorHandler);
		}
		
		
		private function SQLDocsResultHandler(event:SQLEvent):void {
			event.target.removeEventListener(SQLEvent.RESULT, 		SQLDocsResultHandler);
			event.target.removeEventListener(SQLErrorEvent.ERROR,	SQLDocsErrorHandler);
			
			var statement:SQLStatement	= event.target as SQLStatement;
			var result:SQLResult		= statement.getResult();
			
			// get the documents from the SQL result
			duplicateTable				= new Dictionary();
			var filteredResult:Array	= result.data.filter(duplicateFilter);
			duplicateTable				= null;
			
			contentDownloadQueue		= new ArrayCollection(filteredResult);
			startDownload();
		}
		
		
		private function duplicateFilter(item:SiemensDocument, index:int, array:Array):Boolean {
			if (duplicateTable.hasOwnProperty(item.location)) {
				return false;
			}
			else {
				duplicateTable[item.location] = true;
				return true;
			}
		}
		
		
		private function startDownload():void {
			if (overwriteExisting && contentStorageFolder.exists) {
				contentStorageFolder.deleteDirectory(true);
			}
			if (!contentStorageFolder.exists) {
				contentStorageFolder.createDirectory();
			}
			
			contentDownloadCursor = contentDownloadQueue.createCursor();
			
			var doc:SiemensDocument	= contentDownloadCursor.current as SiemensDocument;
			loader.load(new URLRequest(doc.location));
		}
		
		
		private function downloadProgressHandler(event:ProgressEvent):void {
			var currentIndex:uint = contentDownloadCursor.bookmark.getViewIndex(); //contentDownloadQueue.getItemIndex(contentDownloadCursor.current);
			var progress:Number	= event.bytesLoaded / event.bytesTotal;
			sendNotification(DOWNLOAD_PROGRESS, [currentIndex + progress, contentDownloadQueue.length]);
		}
		
		
		private function downloadIOErrorHandler(event:IOErrorEvent):void {
			LogUtils.Error("Error downloading Document: " + event.text);
			downloadNext();
		}
		
		
		private function downloadSecurityErrorHandler(event:SecurityErrorEvent):void {
			LogUtils.Error("Error downloading Document: " + event.text);
			downloadNext();
		}
		
		
		private function downloadCompleteHandler(event:Event):void {
			var doc:SiemensDocument	= contentDownloadCursor.current as SiemensDocument;
			
			// Save File - create storage directory if it doesn't exist
			if (!contentStorageFolder.exists) {
				contentStorageFolder.createDirectory();
			}
			
			var fileName:String	= doc.location.substr(doc.location.lastIndexOf("/") + 1); 
			var target:File	= contentStorageFolder.resolvePath(fileName);
			var stream:FileStream = new FileStream();
			
			try {
				stream.open(target, FileMode.WRITE);
				stream.writeBytes(loader.data as ByteArray);
			}
			catch (err:Error) {
				// Error Writing File
				LogUtils.Error("Error writing Document (" + fileName + ") in storage!");
			}
			finally {
				stream.close();
			}
			
			loader.data	= null;
			
			// Move To Next
			downloadNext();
		}
		
		
		private function downloadNext():void {
			// test if the end of the road
			if (!contentDownloadCursor.moveNext()) {
				downloadCompleted();
				return;
			}
			
			// maybe update the progress bar here too
			var currentIndex:uint = contentDownloadCursor.bookmark.getViewIndex();
			sendNotification(DOWNLOAD_PROGRESS, [currentIndex, contentDownloadQueue.length]);
			
			// see if document exists
			if (!contentStorageFolder.exists) {
				contentStorageFolder.createDirectory();
			}
			var doc:SiemensDocument	= contentDownloadCursor.current as SiemensDocument;
			var fileName:String	= doc.location.substr(doc.location.lastIndexOf("/") + 1);
			var target:File	= contentStorageFolder.resolvePath(fileName);
			if (target.exists) {
				downloadNext();
				return;
			}
			
			// finaly load the document
			loader.load(new URLRequest(doc.location));	
		}
		
		
		private function downloadCompleted():void {
			sendNotification(DOWNLOAD_COMPLETE);
			sendNotification(UpdateCommand.LOG_CONTENT_DOWNLOAD);
		}
		
		
		public function cancelDownload():void {
			loader.close();
			contentDownloadQueue.removeAll();
		}
	}
}