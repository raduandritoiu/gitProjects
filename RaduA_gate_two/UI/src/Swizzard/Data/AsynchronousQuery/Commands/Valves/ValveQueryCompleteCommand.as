package Swizzard.Data.AsynchronousQuery.Commands.Valves
{
	import Swizzard.Data.AsynchronousQuery.Token.ValveQueryToken;
	import Swizzard.Data.Models.Query.ValveSystemQueryModel;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	
	import utils.LogUtils;

	
	/**
	 * Query Complete Command
	 * 
	 * This is the last command executed in the query sequence. After the valve querying commands have completed and concatenated their result to the query token,
	 * this command will send the final notification of either valvesRecieved or actuatorsReceived depending upon the query requested. The body of the notification
	 * will be the collection of objects queried.
	 *  
	 * 
	 * @see Swizzard.Data.Models.SiemensValve
	 * @see Swizzard.Data.Models.SiemensActuator
	 * 
	 * @author Michael Rochelle
	 * 
	 */	
	

	public class ValveQueryCompleteCommand extends SimpleCommand
	{
		public function ValveQueryCompleteCommand() {
			super();
		}
		
			
		override public function execute(notification:INotification):void {
			var token:ValveQueryToken	= notification.getBody() as ValveQueryToken;
			var queryModel:ValveSystemQueryModel = token.valveQueryModel;
			
			LogUtils.Debug("Number Of Executed Queries: " + token.numQueries);
			
			if (queryModel.actuator)
				queryModel.actuator.clearChanged();
			
			if (queryModel.valve)
				queryModel.valve.clearChanged();					
			
			if (token.queryModel.resetPending) {
				// If resent was pending, send notificaitons with emtpy collections to clear the grids.
				token.queryModel.resetPending	= false;
				sendNotification(ValveQueryCommand.VALVES_RECEIVED, []);
				sendNotification(ValveQueryCommand.ACTUATORS_RECEIVED, []);
				return;
			}
			
			if (token.hasValvesAndActuators) {
				sendNotification(ValveQueryCommand.QUERY_RESULTS_RECEIVED, token);
			}
			else if (queryModel.valve.valveTypes.length < 1) {
				// No valves queries.
				sendNotification(ValveQueryCommand.ACTUATORS_RECEIVED, 	[]);
				sendNotification(ValveQueryCommand.VALVES_RECEIVED, 	[]);
			}
			else {
				if (token.resultsChanged || token.isValveQuery) {
					sendNotification(ValveQueryCommand.VALVES_RECEIVED, token.results);
				}
				if (token.actuatorResultsChanged || !token.isValveQuery) {
					sendNotification(ValveQueryCommand.ACTUATORS_RECEIVED, token.actuatorResults);
				}
			}
		}
	}
}