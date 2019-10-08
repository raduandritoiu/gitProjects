package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticHousing 
	{
		public static const Unknown:int 	= 0		// - "Unknown"
		public static const ALUMINUN:int 	= 1		// - "Aluminum"
		public static const STEEL:int 		= 2		// - "Steel with epoxy electrocoat"

		
		public static function toString(flag:int):String {
			switch (flag) {
				case ALUMINUN:
					return "Aluminum";
					
				case STEEL:
					return "Steel";
			}
			
			return "Unknown";		
		}
	}
}