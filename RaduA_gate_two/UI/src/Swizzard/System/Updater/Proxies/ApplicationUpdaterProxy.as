package Swizzard.System.Updater.Proxies
{
	import flash.events.ErrorEvent;
	import flash.events.ProgressEvent;
	
	import mx.controls.Alert;
	
	import Swizzard.System.Updater.Commands.UpdateCommand;
	
	import air.update.ApplicationUpdater;
	import air.update.events.DownloadErrorEvent;
	import air.update.events.StatusFileUpdateErrorEvent;
	import air.update.events.StatusUpdateErrorEvent;
	import air.update.events.StatusUpdateEvent;
	import air.update.events.UpdateEvent;
	
	import org.puremvc.as3.patterns.proxy.Proxy;
	
	
	public class ApplicationUpdaterProxy extends Proxy 
	{
		public static const NAME:String	 			= "ApplicationUpdaterProxy";
		
		// RADU_TODO_MUST_UNDO: also undo the content of the .xml
//		public static const UPDATE_URL:String		= "C:/radua/work_related_projects/simple_select/develop_test_builds_release/SimpleSelect_2_1_9/versions/ApplicationUpdate_2_1_9.xml"; 
		public static const UPDATE_URL:String		= "https://www.swizzard.usa.siemens.com/Data2/versions/ApplicationUpdate_2_1_10.xml"; 
		
		
		public static const UPDATE_START:String		= "applicationUpdateStart";
		public static const UPDATE_PROGRESS:String	= "applicationUpdateProgress";
		
		
		private var applicationUpdater:ApplicationUpdater;
		private var newVersion:String;
		
		
		public function ApplicationUpdaterProxy() {
			super(NAME);
		}
		
		
		override public function onRegister():void {
			applicationUpdater				= new ApplicationUpdater();			
			applicationUpdater.updateURL	= UPDATE_URL;
			applicationUpdater.delay		= 2;
			
			applicationUpdater.addEventListener(UpdateEvent.CHECK_FOR_UPDATE, 		checkForUpdateHandler, false, 0, true);
			applicationUpdater.addEventListener(UpdateEvent.DOWNLOAD_START, 		updateDownloadStartHandler, false, 0, true);
			applicationUpdater.addEventListener(ProgressEvent.PROGRESS,				updateProgressHandler, false, 0, true);
			applicationUpdater.addEventListener(DownloadErrorEvent.DOWNLOAD_ERROR,	downloadErrorHandler, false, 0, true);
			
			applicationUpdater.addEventListener(StatusUpdateEvent.UPDATE_STATUS,		updateStatusHandler, false, 0, true);
			applicationUpdater.addEventListener(StatusUpdateErrorEvent.UPDATE_ERROR,	statusUpdateErrorHandler, false, 0, true);
			applicationUpdater.addEventListener(StatusFileUpdateErrorEvent.FILE_UPDATE_ERROR, fileUpdateErrorHandler, false, 0, true);
			
			applicationUpdater.addEventListener(ErrorEvent.ERROR, 					updateErrorHandler, false, 0, true);
			
			applicationUpdater.initialize();
			applicationUpdater.checkNow();
		}
		
		
		override public function onRemove():void {
			if (applicationUpdater != null) {
				applicationUpdater.removeEventListener(UpdateEvent.CHECK_FOR_UPDATE, 		checkForUpdateHandler, false);
				applicationUpdater.removeEventListener(UpdateEvent.DOWNLOAD_START, 			updateDownloadStartHandler, false);
				applicationUpdater.removeEventListener(ProgressEvent.PROGRESS,				updateProgressHandler, false);
				applicationUpdater.removeEventListener(DownloadErrorEvent.DOWNLOAD_ERROR,	downloadErrorHandler, false);
				
				applicationUpdater.removeEventListener(StatusUpdateEvent.UPDATE_STATUS,		updateStatusHandler, false);
				applicationUpdater.removeEventListener(StatusUpdateErrorEvent.UPDATE_ERROR,	statusUpdateErrorHandler, false);
				applicationUpdater.removeEventListener(StatusFileUpdateErrorEvent.FILE_UPDATE_ERROR, fileUpdateErrorHandler, false);
				
				applicationUpdater.removeEventListener(ErrorEvent.ERROR, 					updateErrorHandler, false);
			}
			applicationUpdater = null;
		}
		
		
		public function updateNow():void {
			applicationUpdater.downloadUpdate();
		}
		
		
		public function check():void {
			applicationUpdater.checkNow();
		}
		
		
		private function updateStatusHandler(event:StatusUpdateEvent):void {
			if (event.available) {
				newVersion	= event.version;
				sendNotification(UpdateCommand.UPDATE_PENDING, UpdateCommand.APPLICATION_TYPE);
				event.preventDefault();
			}
			else {
				sendNotification(UpdateCommand.CHECK_FOR_UPDATE, UpdateCommand.DATABASE_TYPE);
			}
		}
		
		
		private function updateDownloadStartHandler(event:UpdateEvent):void {
			sendNotification(UPDATE_START, newVersion);	
		}
		
		
		private function updateProgressHandler(event:ProgressEvent):void {
			sendNotification(UPDATE_PROGRESS, event); 
		}
		
		
		private function statusUpdateErrorHandler(event:StatusUpdateErrorEvent):void {
			Alert.show("Error accessing App update server.");
		}
		
		
		private function updateErrorHandler(event:ErrorEvent):void {
			Alert.show("Error Executing App update: " + event.text, "Error");
		}
		
		
		private function fileUpdateErrorHandler(event:StatusFileUpdateErrorEvent):void {}
		private function downloadErrorHandler(event:DownloadErrorEvent):void {}
		private function checkForUpdateHandler(event:UpdateEvent):void {}
	}
}