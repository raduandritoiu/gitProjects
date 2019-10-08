package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticDiaphArea 
	{
		public static const Unknown:int 	= 0			// - "Unknown"
		public static const CM_52:int 		= 52		// - "8 sq in (52 sq cm)"
		public static const CM_71:int 		= 71		// - "11 sq in (71 sq cm)"
		public static const CM_115:int 		= 115		// - "17.9 sq in (115 sq cm)"
		public static const CM_126:int 		= 126		// - "19.6 inches2 (126 cm2)"
		public static const CM_230:int 		= 230		// - "35.8 sq in (230 sq cm)"// - 230		- "35.8 sq in (230 sq cm)"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case CM_52:
					return "8 sq in (52 sq cm)";
					
				case CM_71:
					return "11 sq in (71 sq cm)";
					
				case CM_115:
					return "17.9 sq in (115 sq cm)";
					
				case CM_126:
					return "19.6 inches2 (126 cm2)";
					
				case CM_230:
					return "35.8 sq in (230 sq cm)";
			}
			
			return "Unknown";		
		}
	}
}