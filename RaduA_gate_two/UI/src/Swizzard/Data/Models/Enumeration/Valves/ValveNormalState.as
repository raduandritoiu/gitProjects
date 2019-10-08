package Swizzard.Data.Models.Enumeration.Valves
{
	public class ValveNormalState
	{
		private static var _dataProvider:Array;
		
		public static const NA:uint					= 0;
		public static const NORMALLY_OPEN:uint		= 1;
		public static const NORMALLY_CLOSED:uint	= 2;
		
		
		public static function get DATA_PROVIDER():Array
		{
			if (!_dataProvider)
				createDataProvider();
			return _dataProvider;
		}		
		
		
		private static function createDataProvider():void
		{
			_dataProvider = new Array();
			_dataProvider.push({label: "All",			value: null});
			_dataProvider.push({label: "Normally Open", value: 1});
			_dataProvider.push({label: "Normally Closed", value: 2});
			
			//_dataProvider.sortOn("label");
		}
		
		
		public static function toString(flag:uint):String
		{
			switch (flag)
			{
				case NORMALLY_OPEN:
					return "Normally Open";
					break;
				
				case NORMALLY_CLOSED:
					return "Normally Closed";
					break;
			}
			return "";
		}
	}
}