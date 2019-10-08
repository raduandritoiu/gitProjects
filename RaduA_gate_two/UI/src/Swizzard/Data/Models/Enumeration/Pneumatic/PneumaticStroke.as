package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticStroke 
	{
		public static const Unknown:int 	= 0		// - "Unknown"
		public static const MM_60:int 		= 60	// - "2-3/8 in (60 mm)"
		public static const MM_70:int 		= 70	// - "2-3/4 in (70 mm)"
		public static const MM_76:int 		= 76	// - "3 in (76 mm)"
		public static const MM_102:int 		= 102	// - "4 in (102 mm)"
		public static const MM_178:int 		= 178	// - "7 in (178 mm)"

		
		public static function toString(flag:int):String {
			switch (flag) {
				case MM_60:
					return "2-3/8 in (60 mm)";
					
				case MM_70:
					return "2-3/4 in (70 mm)";
					
				case MM_76:
					return "3 in (76 mm)";
					
				case MM_102:
					return "4 in (102 mm)";
					
				case MM_178:
					return "7 in (178 mm)";
			}
			
			return "Unknown";		
		}
	}
}