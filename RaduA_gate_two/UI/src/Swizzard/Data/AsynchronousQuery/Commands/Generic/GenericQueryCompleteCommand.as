package Swizzard.Data.AsynchronousQuery.Commands.Generic
{
	import Swizzard.Data.AsynchronousQuery.Token.BaseQueryToken;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	
	import utils.LogUtils;
	
	
	public class GenericQueryCompleteCommand extends SimpleCommand
	{
		public function GenericQueryCompleteCommand() {
			super();
		}
		
		
		override public function execute(notification:INotification):void {
			var token:BaseQueryToken = notification.getBody() as BaseQueryToken;
			
			LogUtils.Debug("(" + token.target + ") Number Of Executed Queries: " + token.numQueries + "\n");
			
			if (token.queryModel) {
				token.queryModel.clearChanged();
			}
			if (token.queryModel.resetPending) {
				// If resent was pending, send notificaitons with emtpy collections to clear the grids.
				token.queryModel.resetPending = false;
				sendNotification(token.completeNotification, []);
				return;
			}
			if (token.results == null) {
				token.results = [];
			}
			sendNotification(token.completeNotification, token.results);
		}
	}
}