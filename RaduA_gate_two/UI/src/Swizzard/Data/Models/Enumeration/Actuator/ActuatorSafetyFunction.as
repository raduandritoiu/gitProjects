package Swizzard.Data.Models.Enumeration.Actuator
{
	public class ActuatorSafetyFunction
	{
		private static var _dataProvider:Array;
		
		public static const NA:uint					= 0;
		public static const SPRING_RETURN:uint		= 1;
		public static const NON_SPRING_RETURN:uint	= 2;
		
		
		public static function get DATA_PROVIDER():Array
		{
			if (!_dataProvider)
				createDataProvider();
			return _dataProvider;
		}		
		
		
		private static function createDataProvider():void
		{
			_dataProvider		= new Array();
			_dataProvider.push({label: "All",				value: -1});
			_dataProvider.push({label: "Spring Return", 	value: 1});
			_dataProvider.push({label: "Non Spring Return", value: 2});
			//_dataProvider.sortOn("label");
		}
		
		
		public static function toString(flag:uint):String
		{
			switch (flag)
			{
				case NA:
					return "N/A";
					break;
				
				case SPRING_RETURN:
					return "Spring Return";
					break;
				
				case NON_SPRING_RETURN:
					return "Non Spring Return";
					break;
			}
			return "N/A";
		}
	}
}