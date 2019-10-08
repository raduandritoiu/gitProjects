package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticOperTemp 
	{
		public static const Unknown:int 	= 0		// - "Unknown"
		public static const TEMP_1:int 		= 1		// - "-20°F to +160°F (-29°C to 71°C)"
		public static const TEMP_2:int 		= 2		// - "-20 to +200°F (-29°C to 93°C)"
		public static const TEMP_3:int 		= 3		// - "50°F to 140°F (10°C to 60°C)"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case TEMP_1:
					return "-20°F to +160°F (-29°C to 71°C)";
				
				case TEMP_2:
					return "-20 to +200°F (-29°C to 93°C)";
				
				case TEMP_3:
					return "50°F to 140°F (10°C to 60°C)";
			}
			
			return "Unknown";		
		}
	}
}