package Swizzard.Data.Models.Enumeration.Dampers
{
	public class DamperPlenum
	{
		public static const UNKNOWN:int 		= 0;	// "Unknown"
		public static const PLENUM:int 			= 1;	// "Plenum"
		public static const STANDARD:int 		= 2;	// "Standard"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case PLENUM:
					return "Plenum";
					
				case STANDARD:
					return "Standard";
			}
			
			return "N/A";
		}
	}
}