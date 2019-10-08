package Swizzard.System.Commands.Init
{
	import Swizzard.System.ApplicationFacade;
	import Swizzard.System.ApplicationMediator;
	
	import caurina.transitions.Tweener;
	import caurina.transitions.properties.FilterShortcuts;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;

	public class ViewPrepCommand extends SimpleCommand
	{
		
		//Called by the macro StartupCommand 
		override public function execute(notification:INotification):void
		{
			var app:Object = notification.getBody();
			
			Tweener.init();
			FilterShortcuts.init();
			//DisplayShortcuts.init();
						
			//facade.registerMediator(new exampleMediator() );
			
			
			facade.registerMediator(new ApplicationMediator(app));
			
			
			sendNotification(ApplicationFacade.PREP_VIEW);
		}
		
		
	}
}