package Swizzard.System.Commands.Init
{
	import org.puremvc.as3.patterns.command.MacroCommand;

	public class StartupCommand extends MacroCommand
	{
		
		override protected function initializeMacroCommand():void
		{
			addSubCommand(ModelPrepCommand);
			addSubCommand(ConstructEnumerationDataCommand);
			addSubCommand(ViewPrepCommand);
		}
	}
}