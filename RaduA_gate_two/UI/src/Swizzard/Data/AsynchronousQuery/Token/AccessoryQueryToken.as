package Swizzard.Data.AsynchronousQuery.Token
{
	import Swizzard.Data.AsynchronousQuery.Commands.Accessory.AccessoryQueryCommand;
	import Swizzard.Data.Models.Query.AccessoryQueryModel;
	
	
	public class AccessoryQueryToken extends BaseQueryToken
	{
		public function AccessoryQueryToken() {
			super();
			_completeNotification = AccessoryQueryCommand.ACCESSORIES_RECEIVED;
		}
		
		
		public function get accessoryQueryModel():AccessoryQueryModel {
			return _queryModel as AccessoryQueryModel;
		}
	}
}