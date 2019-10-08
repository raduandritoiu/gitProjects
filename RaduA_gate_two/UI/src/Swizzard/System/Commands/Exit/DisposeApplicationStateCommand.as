package Swizzard.System.Commands.Exit
{
	import Swizzard.Data.Proxies.DataProxy;
	import Swizzard.System.Preferences.proxies.UserPreferencesProxy;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;

	[ExcludeClass]
	public class DisposeApplicationStateCommand extends SimpleCommand
	{
		public function DisposeApplicationStateCommand()
		{
			super();
		}
		
		override public function execute(notification:INotification):void
		{
			// Remove Proxies
			facade.removeProxy(DataProxy.NAME);
			facade.removeProxy(UserPreferencesProxy.NAME);
		}
	}
}