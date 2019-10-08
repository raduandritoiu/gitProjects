package Swizzard.Data.Models.Enumeration.Dampers
{
	
	public class DamperSystemSupply
	{
		public static const UNKNOWN:int 	= 0;	// "Unknown"
		public static const VAC_24:int 		= 1;	// "24 VAC"
		public static const DC_24:int 		= 2;	// "24 DC"			//- this is not used, just reserver, 
																		// there is no value of such kind in the database
		public static const VAC_DC_24:int 	= 3;	// "24 VAC/DC"
		public static const VAC_120:int 	= 4;	// "120 VAC"
		public static const VAC_230:int 	= 8;	// "230 VAC"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case VAC_24:
					return "24 VAC";
				
				case VAC_DC_24:
					return "24 VAC/DC";
				
				case VAC_120:
					return "120 VAC";
				
				case VAC_230:
					return "230 VAC";
			}
			
			return "N/A";
		}
	}
}