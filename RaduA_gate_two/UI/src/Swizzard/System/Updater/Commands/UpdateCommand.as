package Swizzard.System.Updater.Commands
{
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	
	import Swizzard.System.ContentDownload.Proxy.ContentProxy;
	import Swizzard.System.Updater.Mediators.ApplicationUpdaterMediator;
	import Swizzard.System.Updater.Mediators.DatabaseUpdaterMediator;
	import Swizzard.System.Updater.Proxies.ApplicationUpdaterProxy;
	import Swizzard.System.Updater.Proxies.DatabaseUpdaterProxy;
	import Swizzard.System.Updater.UI.UpdaterWindow;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	

	public class UpdateCommand extends SimpleCommand
	{
		public static const APPLICATION_TYPE:String		= "application";
		public static const DATABASE_TYPE:String		= "database";
		public static const CONTENT_TYPE:String			= "content";
		
		
		public static const CHECK_FOR_UPDATE:String		= "checkForUpdate";
		public static const UPDATE_PENDING:String		= "updatePending";
		public static const INITIATE_UPDATE:String		= "initiateUpdate";
		public static const RESET_DB_VERSION:String		= "resetDbVersion";
		public static const LOG_CONTENT_DOWNLOAD:String	= "logContentDownload";
		
		
		public function UpdateCommand() {
			super();
		}
		
		
		override public function execute(notification:INotification):void {
			var appProxy:ApplicationUpdaterProxy	= facade.retrieveProxy(ApplicationUpdaterProxy.NAME) as ApplicationUpdaterProxy;
			var dbProxy:DatabaseUpdaterProxy		= facade.retrieveProxy(DatabaseUpdaterProxy.NAME) as DatabaseUpdaterProxy;
			var updateType:String					= notification.getBody() as String;
			
			switch (notification.getName()) {
				case CHECK_FOR_UPDATE:
				{
					switch (updateType) {
						case APPLICATION_TYPE:
							appProxy.check();
							break;
						
						case DATABASE_TYPE:
							dbProxy.checkForUpdate();
							break;
					}					
					break;
				}
				
				case INITIATE_UPDATE:
				{
					switch (updateType) {
						case APPLICATION_TYPE:
							appProxy.updateNow();
							break;
						
						case DATABASE_TYPE:
							dbProxy.updateNow();
							break;
					}
					break;
				}
				
				case UPDATE_PENDING:
				{
					// Show Update Window
					var win:UpdaterWindow;
					switch (updateType) {
						case APPLICATION_TYPE:
							if (facade.hasMediator(ApplicationUpdaterMediator.NAME))
								break;
							win	= UpdaterWindow.Show();
							facade.registerMediator(new ApplicationUpdaterMediator(win));
							break;
						
						case DATABASE_TYPE:
							if (facade.hasMediator(DatabaseUpdaterMediator.NAME))
								break;
							win	= UpdaterWindow.Show();
							facade.registerMediator(new DatabaseUpdaterMediator(win));
							break;
						
						case CONTENT_TYPE:
							Alert.show("Would you like to update your offline content?", "Content Update Available", Alert.YES | Alert.NO, null, updateContentConfirmHandler);
							break;
					}
					break;
				}
				
				case LOG_CONTENT_DOWNLOAD:
				{
					dbProxy.logContentDownload();
					break;
				}
					
				case RESET_DB_VERSION:
				{
					dbProxy.resetDatabaseVersion();
					break;
				}
			}
		}
		
		
		private function updateContentConfirmHandler(event:CloseEvent):void {
			if (event.detail == Alert.YES) {
				var cd:ContentProxy = facade.retrieveProxy(ContentProxy.NAME) as ContentProxy;
				if (cd == null) {
					cd = new ContentProxy();
					facade.registerProxy(cd);
				}
				cd.downloadCatalog(true);
 			}
		}
	}
}