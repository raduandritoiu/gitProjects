package Swizzard.Data.Models.Enumeration.Valves
{
	public class FlowCharacteristics
	{
		public static const NA:uint						= 0;
		public static const EQUAL_PERCENT:uint			= 1;
		public static const MODIFIED_EQUAL_PERCENT:uint	= 2;
		public static const LINEAR:uint					= 3;
		public static const QUICK_OPENING:uint			= 4;
		public static const LINEAR_EQUAL_PERCENT:uint	= 5;
		
		
		public static function toString(type:uint):String
		{
			switch (type)
			{
				case 1:
					return "Equal %";
					break;
				
				case 2:
					return "Modified Equal %";
					break;
				
				case 3:
					return "Linear";
					break;
				
				case 4:
					return "Quick Opening";
					break;
				
				case 5:
					return "Linear/Equal %";
					break;
			}
			return "Unknown";
		}
	}
}