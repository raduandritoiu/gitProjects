package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticStem 
	{
		public static const Unknown:int 	= 0		// - "Unknown"
		public static const PLATED:int 		= 1		// - "Plated Steel"
		public static const STAINLESS:int 	= 2		// - "Stainless Steel"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case PLATED:
					return "Plated Steel";
					
				case STAINLESS:
					return "Stainless Steel";
			}
			
			return "Unknown";		
		}
	}
}