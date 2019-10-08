package Swizzard.System.Commands.Exit
{
	import org.puremvc.as3.patterns.command.MacroCommand;

	public class ShutdownCommand extends MacroCommand
	{
		public static const SHUTDOWN:String	= "shutdown";
		
		public function ShutdownCommand()
		{
			super();
		}
		
		override protected function initializeMacroCommand():void
		{
			// Dispose Application State (Proxies, Network, etc...)
			addSubCommand(DisposeApplicationStateCommand);
			
			// Exit Application Command
			addSubCommand(ShutdownFinalCommand);
		}
		
	}
}