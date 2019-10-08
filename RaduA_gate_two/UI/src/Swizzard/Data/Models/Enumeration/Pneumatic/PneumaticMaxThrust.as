package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticMaxThrust
	{
		public static const Unknown:Number 	= 0		// - "Unknown"
		public static const LB_40:Number 	= 40; 	// - "40 lb (178 N)"
		public static const LB_55:Number 	= 55; 	// - "55 lb (245 N)" 
													// 	or "55 lb (98 N)" (have no ideea whcich)
		public static const LB_64:Number 	= 64; 	// - "64 lb (285 N)"
		public static const LB_88:Number 	= 88; 	// - "88 lb (391 N)"
		public static const LB_89:Number 	= 89; 	// - "89 lb (396 N)"
		public static const LB_121:Number 	= 121; 	// - "121 lb (538 N)"
		public static const LB_179:Number 	= 179; 	// - "179 lb (796 N)"
		
		
		public static function toString(flag:Number):String {
			switch (flag) {
				case LB_40:
					return "40 lb (178 N)";
					
				case LB_55:
					return "55 lb (245 N)";
				
				case LB_64:
					return "64 lb (285 N)";
				
				case LB_88:
					return "88 lb (391 N)";
				
				case LB_89:
					return "89 lb (396 N)";
				
				case LB_121:
					return "121 lb (538 N)";
				
				case LB_179:
					return "179 lb (796 N)";
			}
			
			return "Unknown";		
		}
	}
}