package Swizzard.Data.AsynchronousQuery.Commands.Valves
{
	
	import org.puremvc.as3.patterns.command.AsyncMacroCommand;

	/**
	 * Valve Query Command
	 * 
	 *  The valve query command is a macro command that executes every command to query each valve type then executes the query complete command
	 * 
	 * This command is registered to execute for the "executeValveQuery" notificaiton.
	 *  
	 * @see Swizzard.Data.AsynchronousQuery.Commands.Valves.BallValveQueryCommand
	 * @see Swizzard.Data.AsynchronousQuery.Commands.Valves.ButterflyValveQueryCommand
	 * @see Swizzard.Data.AsynchronousQuery.Commands.Valves.GlobeValveQueryCommand
	 * @see Swizzard.Data.AsynchronousQuery.Commands.Valves.MagneticValveQueryCommand
	 * @see Swizzard.Data.AsynchronousQuery.Commands.Valves.ZoneValveQueryCommand
	 * 
	 * @see Swizzard.Data.AsynchronousQuery.Commands.QueryCompleteCommand
	 * 
	 */
	public class ValveQueryCommand extends AsyncMacroCommand
	{
		/**
		 * The executeValveQuery notification is sent to intiate querying of the valve database. This notificaiton must send the QueryToken as its body.
		 * 
		 * @see Swizzard.Data.AsynchronousQuery.QueryToken
		 **/
		public static const EXECUTE_VALVE_QUERY:String	= "executeValveQuery";
		
		/**
		 * The valvesReceived notification is sent when the querying for valves has completed. The body of this notification is the collection of valves.
		 */
		public static const VALVES_RECEIVED:String		= "valvesReceived";
		
		/**
		 * The actuatorsReceived notification is sent when the querying for actuators has completed. The body of this notification is the collection of actuators. 
		 */
		public static const ACTUATORS_RECEIVED:String	= "actuatorsReceived";
		
		public static const QUERY_RESULTS_RECEIVED:String	= "queryResultsReceived";
		
		
		public function ValveQueryCommand()
		{
			super();
		}
		
		
		override protected function initializeAsyncMacroCommand():void
		{
			addSubCommand(ValveQueryPreinitializationCommand);
			
			addSubCommand(BallValveQueryCommand);
			addSubCommand(ButterflyValveQueryCommand);
			addSubCommand(GlobeValveQueryCommand);
			addSubCommand(MagneticValveQueryCommand);
			addSubCommand(ZoneValveQueryCommand);
			addSubCommand(PICValveQueryCommand);
			
			addSubCommand(ValveQueryCompleteCommand);
		}
	}
}