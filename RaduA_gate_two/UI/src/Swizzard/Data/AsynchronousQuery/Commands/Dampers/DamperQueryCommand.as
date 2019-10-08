package Swizzard.Data.AsynchronousQuery.Commands.Dampers
{
	import org.puremvc.as3.patterns.command.AsyncMacroCommand;
	
	
	public class DamperQueryCommand extends AsyncMacroCommand
	{
		public static const EXECUTE_DAMPER_QUERY:String = "execute_damper_query_uebv92";
		public static const DAMPERS_RECEIVED:String = "dampers_received_kh43inv";
		
		
		public function DamperQueryCommand() {
			super();
		}
		
		
		override protected function initializeAsyncMacroCommand():void {
			addSubCommand(SpringDamperQueryCommand);
			addSubCommand(NonSpringDamperQueryCommand);
			addSubCommand(FireDamperQueryCommand);
			addSubCommand(FastDamperQueryCommand);
			
			addSubCommand(ExtraSpringQuerryCommand);
			addSubCommand(ExtraNonSpringQuerryCommand);
			
			addSubCommand(DamperQueryCompleteCommand);
		}
	}
}