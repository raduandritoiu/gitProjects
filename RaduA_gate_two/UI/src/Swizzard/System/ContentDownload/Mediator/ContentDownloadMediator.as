package Swizzard.System.ContentDownload.Mediator
{
	import flash.events.MouseEvent;
	
	import mx.events.EffectEvent;
	
	import spark.effects.Fade;
	
	import Swizzard.System.ContentDownload.Proxy.ContentProxy;
	import Swizzard.System.ContentDownload.UI.ContentDownloadView;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	
	public class ContentDownloadMediator extends Mediator
	{
		public static const NAME:String	= "ContentDownloadMediator";
		
		private var fadeAnimation:Fade;
		private var hidden:Boolean;
		
		
		public function ContentDownloadMediator(view:Object) {
			super(NAME, view);
		}
		
		
		public function get view():ContentDownloadView {
			return viewComponent as ContentDownloadView;
		}
		
				
		override public function onRegister():void {
			hidden = true;
			fadeAnimation = new Fade(view);
			fadeAnimation.duration = 650;
			fadeAnimation.alphaFrom = 1;
			fadeAnimation.alphaTo = 0;
			fadeAnimation.addEventListener(EffectEvent.EFFECT_END, fadeAnimationEnded, false, 0, true);
			view.cancelButton.addEventListener(MouseEvent.CLICK, cancelButtonClickHandler, false, 0, true);	
		}
		
		
		private function fadeAnimationEnded(event:EffectEvent):void {
			view.alpha = 0;
			view.visible = false;
		}
		
		
		private function cancelButtonClickHandler(event:MouseEvent):void {
			var proxy:ContentProxy = facade.retrieveProxy(ContentProxy.NAME) as ContentProxy;
			proxy.cancelDownload();
			dispose();	
		}
		
		
		override public function listNotificationInterests():Array {
			return [ContentProxy.DOWNLOAD_STARTED,
				ContentProxy.DOWNLOAD_ENDED, 
				ContentProxy.DOWNLOAD_PROGRESS, 
				ContentProxy.DOWNLOAD_COMPLETE];
		}
		
		
		override public function handleNotification(notification:INotification):void {
			switch (notification.getName())
			{
				case ContentProxy.DOWNLOAD_STARTED:
					view.alpha = 1;
					view.visible = true;
					hidden = false;
					break;
				
				case ContentProxy.DOWNLOAD_ENDED:
					if (!hidden) {
						view.alpha = 0;
						view.visible = false;
						hidden = true;
					}
					break;

				case ContentProxy.DOWNLOAD_PROGRESS:
					var progress:Array	= notification.getBody() as Array;
					if (view.progressBar.indeterminate) {
						view.progressBar.indeterminate = false;
					}					
					view.progressBar.setProgress(progress[0], progress[1]);
					break;
				
				case ContentProxy.DOWNLOAD_COMPLETE:
					dispose();
					break;
			}
		}
		
		
		private function dispose():void {
			if (!hidden) {
				fadeAnimation.play();
				hidden = true;
			}
		}
	}
}