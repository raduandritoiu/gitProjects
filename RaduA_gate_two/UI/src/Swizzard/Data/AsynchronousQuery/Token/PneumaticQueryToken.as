package Swizzard.Data.AsynchronousQuery.Token
{
	import Swizzard.Data.AsynchronousQuery.Commands.Pneumatics.PneumaticQueryCommand;
	import Swizzard.Data.Models.Query.PneumaticQueryModel;
	
	
	public class PneumaticQueryToken extends BaseQueryToken
	{
		public function PneumaticQueryToken() {
			super();
			_completeNotification = PneumaticQueryCommand.PNEUMATICS_RECEIVED;
		}
		
		
		public function get penumaticQueryModel():PneumaticQueryModel {
			return _queryModel as PneumaticQueryModel
		}
	}
}