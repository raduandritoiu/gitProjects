package Swizzard.Data.AsynchronousQuery.Commands.Accessory
{
	import org.puremvc.as3.patterns.command.AsyncMacroCommand;
	
	
	public class AccessoryQueryCommand extends AsyncMacroCommand
	{
		public static const EXECUTE_ACCESSORY_QUERY:String = "execute_accessory_query_weh23";
		public static const ACCESSORIES_RECEIVED:String = "execute_dampers_query_4395hnb";
		
		
		public function AccessoryQueryCommand() {
			super();
		}
		
		
		override protected function initializeAsyncMacroCommand():void {
			addSubCommand(BaseAccessoryQueryCommand);
			addSubCommand(ExtraPneumAccQueryCommand);
			addSubCommand(AccessoryQueryCompleteCommand);
		}
	}
}