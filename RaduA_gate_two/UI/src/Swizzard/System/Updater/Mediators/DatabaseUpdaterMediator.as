package Swizzard.System.Updater.Mediators
{
	import flash.events.MouseEvent;
	import flash.events.ProgressEvent;
	
	import Swizzard.System.ApplicationMediator;
	import Swizzard.System.Updater.Commands.UpdateCommand;
	import Swizzard.System.Updater.Proxies.DatabaseUpdaterProxy;
	import Swizzard.System.Updater.UI.UpdaterWindow;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	
	public class DatabaseUpdaterMediator extends Mediator
	{
		public static const NAME:String	= "DatabaseUpdaterWindowMediator";
		
		
		public function DatabaseUpdaterMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
		}
		
		
		override public function onRegister():void {
			window.title					= "Database Update Available";
			window.updatePendingLabel.text	= "Do you want to update your product database?";
			window.yesButton.addEventListener(MouseEvent.CLICK, yesButtonClickHandler, false, 0, true);
			window.noButton.addEventListener(MouseEvent.CLICK, noButtonClickHandler, false, 0, true);	
		}
		
		
		private function yesButtonClickHandler(event:MouseEvent):void {
			sendNotification(UpdateCommand.INITIATE_UPDATE, "database");
		}
		
		
		private function noButtonClickHandler(event:MouseEvent):void {
			window.close();
			facade.removeMediator(NAME);
			delete this;
		}
		
		
		override public function listNotificationInterests():Array {
			return [DatabaseUpdaterProxy.UPDATE_PROGRESS, DatabaseUpdaterProxy.UPDATE_START, DatabaseUpdaterProxy.UPDATE_COMPLETE];
		}
		
		
		override public function handleNotification(notification:INotification):void {
			switch (notification.getName())
			{
				case DatabaseUpdaterProxy.UPDATE_START:
				{
					window.viewIndex = 1;
					break;
				}
					
				case DatabaseUpdaterProxy.UPDATE_PROGRESS:
				{
					if (window.progressBar.indeterminate)
						window.progressBar.indeterminate	= false;
					var event:ProgressEvent	= notification.getBody() as ProgressEvent;
					window.progressBar.setProgress(event.bytesLoaded, event.bytesTotal);
					break;
				}
				
					// RADU_TODO: this should be done as a notification and the function refreshQuery() should be private
				case DatabaseUpdaterProxy.UPDATE_COMPLETE:
				{
					var appMediator:ApplicationMediator = facade.retrieveMediator(ApplicationMediator.NAME) as ApplicationMediator;
					appMediator.refreshValveQuery();
					window.close();
					facade.removeMediator(NAME);
					delete this;
				}
			}
		}
		
		
		private function get window():UpdaterWindow {
			return this.viewComponent as UpdaterWindow;
		}
	}
}