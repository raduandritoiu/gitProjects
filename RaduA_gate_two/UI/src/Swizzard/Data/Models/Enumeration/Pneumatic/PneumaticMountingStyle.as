package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticMountingStyle 
	{
		public static const Unknown:int 	= 0		// - "Unknown"
		public static const FRONT:int 		= 1		// - "Front"
		public static const FIXED:int 		= 2		// - "Fixed"
		public static const PIVOT:int 		= 3		// - "Pivot"
		public static const TANDEM:int 		= 4		// - "Tandem"
		public static const UNIVERSAL:int 	= 5		// - "Universal - Extended Shaft"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case FRONT:
					return "Front";
					
				case FIXED:
					return "Fixed";
					
				case PIVOT:
					return "Pivot";
					
				case TANDEM:
					return "Tandem";
					
				case UNIVERSAL:
					return "Universal - Extended Shaft";
			}
			
			return "Unknown";		
		}
	}
}