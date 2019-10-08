package Swizzard.Data.Models.Enumeration.Actuator
{
	public class ActuatorMotorType
	{
		private static var _dataProvider:Array;
		
		public static const NA:uint			= 0;
		public static const ELECTRIC:uint	= 1;
		public static const PNEUMATIC:uint	= 2;
		
		
		public static function get DATA_PROVIDER():Array
		{
			if (!_dataProvider)
				createDataProvider();
			return _dataProvider;
		}
		
		
		private static function createDataProvider():void
		{
			_dataProvider = new Array();
			_dataProvider.push({label: "All", 		value: null});
			_dataProvider.push({label: "Electric", 	value: 1});
			_dataProvider.push({label: "Pneumatic", value: 2});
		}
		
		
		public static function toString(flag:uint):String
		{
			switch (flag)
			{
				case NA:
					return "N/A";
					break;
				
				case ELECTRIC:
					return "Electric";
					break;
				
				case PNEUMATIC:
					return "Pneumatic";
					break;
			}
			return "N/A";
		}
	}
}