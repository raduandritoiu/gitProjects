package Swizzard.System.Persistence.Commands
{
	import Swizzard.System.Persistence.Proxies.PersistenceProxy;
	import Swizzard.System.Models.VSSTProject;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;

	public class PersistenceCommand extends SimpleCommand
	{
		public static const LOAD_PROJECT:String		= "loadProject";
		public static const SAVE_PROJECT:String		= "saveProject";
		public static const SAVE_AS_PROJECT:String	= "saveAsProject";
		public static const NEW_PROJECT:String		= "newProject";
		
		
		public function PersistenceCommand() {
			super();
		}
		
		
		override public function execute(notification:INotification):void {
			var proxy:PersistenceProxy = facade.retrieveProxy(PersistenceProxy.NAME) as PersistenceProxy;
			
			switch (notification.getName()) {
				case LOAD_PROJECT:
					proxy.loadProject();
					break;
				
				case SAVE_PROJECT:
					proxy.saveProject(notification.getBody() as VSSTProject);
					break;
					
				case SAVE_AS_PROJECT:
					proxy.saveProject(notification.getBody() as VSSTProject, true);
					break;
					
				case NEW_PROJECT:
					proxy.reset();
					break;
			}
		}
	}
}