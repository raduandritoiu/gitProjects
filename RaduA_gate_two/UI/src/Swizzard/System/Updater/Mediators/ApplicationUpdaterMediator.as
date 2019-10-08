package Swizzard.System.Updater.Mediators
{
	import flash.events.MouseEvent;
	import flash.events.ProgressEvent;
	
	import Swizzard.System.Updater.Commands.UpdateCommand;
	import Swizzard.System.Updater.Proxies.ApplicationUpdaterProxy;
	import Swizzard.System.Updater.UI.UpdaterWindow;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	
	public class ApplicationUpdaterMediator extends Mediator
	{
		public static const NAME:String	= "ApplicationUpdaterWindowMediator";
		
		
		public function ApplicationUpdaterMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
		} 
		
		
		override public function onRegister():void {
			window.title					= "Application Update Available";
			window.updatePendingLabel.text	= "Do you want to update your application?";
			
			window.yesButton.addEventListener(MouseEvent.CLICK, yesButtonClickHandler, false, 0, true);
			window.noButton.addEventListener(MouseEvent.CLICK, noButtonClickHandler, false, 0, true);	
			window.continueButton.addEventListener(MouseEvent.CLICK, continueClickHandler, false, 0, true);
			window.disagreeButton.addEventListener(MouseEvent.CLICK, disagreeButtonClickHandler, false, 0, true);
			
			window.eulaText.location	= "assets/EULA.htm";
		}
		
		
		private function yesButtonClickHandler(event:MouseEvent):void {
			window.width 	= 500;
			window.height	= 400;
			window.viewIndex = 2;
		}
		
		
		private function noButtonClickHandler(event:MouseEvent):void {
			window.close();
			facade.removeMediator(NAME);
			delete this;
		}
		
		
		private function continueClickHandler(event:MouseEvent):void {
			sendNotification(UpdateCommand.INITIATE_UPDATE, UpdateCommand.APPLICATION_TYPE);
		}
		
		
		private function disagreeButtonClickHandler(event:MouseEvent):void {
			window.close();
			facade.removeMediator(NAME);
			delete this;
		}
		
		
		override public function listNotificationInterests():Array {
			return [ApplicationUpdaterProxy.UPDATE_PROGRESS, ApplicationUpdaterProxy.UPDATE_START];
		}
		
		
		override public function handleNotification(notification:INotification):void {
			switch (notification.getName()) {
				case ApplicationUpdaterProxy.UPDATE_START:
				{
					window.width 	= 330;
					window.height	= 230;
					window.viewIndex = 1;
					break;
				}
				
				case ApplicationUpdaterProxy.UPDATE_PROGRESS:
				{
					if (window.progressBar.indeterminate)
						window.progressBar.indeterminate	= false;
					
					var event:ProgressEvent	= notification.getBody() as ProgressEvent;
					window.progressBar.setProgress(event.bytesLoaded, event.bytesTotal);
					break;
				}
			}
		}
		
		
		private function get window():UpdaterWindow {
			return viewComponent as UpdaterWindow;
		}
	}
}