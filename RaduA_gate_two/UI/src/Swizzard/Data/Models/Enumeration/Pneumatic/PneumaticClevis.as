package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticClevis 
	{
		public static const Unknown:int 	= 0		// - "Unknown"
		public static const YES:int 		= 1		// - "Y"
		public static const NO:int 			= 2		// - "N"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case YES:
					return "Yes";
				
				case NO:
					return "No";
			}
			
			return "Unknown";		
		}
	}
}