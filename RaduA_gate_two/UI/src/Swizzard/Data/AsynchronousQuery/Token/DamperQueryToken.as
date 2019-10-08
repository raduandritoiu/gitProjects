package Swizzard.Data.AsynchronousQuery.Token
{
	import Swizzard.Data.AsynchronousQuery.Commands.Dampers.DamperQueryCommand;
	import Swizzard.Data.Models.Query.DamperQueryModel;
	
	
	public class DamperQueryToken extends BaseQueryToken
	{
		public function DamperQueryToken() {
			super();
			_completeNotification = DamperQueryCommand.DAMPERS_RECEIVED;
		}
		
		
		public function get damperQueryModel():DamperQueryModel {
			return _queryModel as DamperQueryModel;
		}
	}
}