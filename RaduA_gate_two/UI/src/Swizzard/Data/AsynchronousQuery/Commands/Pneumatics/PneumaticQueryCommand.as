package Swizzard.Data.AsynchronousQuery.Commands.Pneumatics
{
	import org.puremvc.as3.patterns.command.AsyncMacroCommand;
	
	
	public class PneumaticQueryCommand extends AsyncMacroCommand
	{
		public static const EXECUTE_PNEUMATIC_QUERY:String = "execute_pneumatic_query_uebv92";
		public static const PNEUMATICS_RECEIVED:String = "pneumatics_received_kh43inv";
		
		
		public function PneumaticQueryCommand() {
			super();
		}
		
		
		override protected function initializeAsyncMacroCommand():void {
			addSubCommand(BasePneumaticQueryCommand);
			
			addSubCommand(PneumaticQueryCompleteCommand);
		}
	}
}