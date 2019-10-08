package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticDiaphMaterial 
	{
		public static const Unknown:int 	= 0		// - "Unknown"
		public static const RUBBER:int 		= 1		//- "Ozone resistant rubber"
		public static const EPDM:int 		= 2		// - "EPDM ozone resistant"
		public static const SILICON:int 	= 3		// - "Silicon rubber ozone res"
		public static const WTF:int 		= 4		// - "------"

		
		public static function toString(flag:int):String {
			switch (flag) {
				case RUBBER:
					return "Ozone resistant rubber";
				
				case EPDM:
					return "EPDM ozone resistant";
				
				case SILICON:
					return "Silicon rubber ozone res";
					
				case WTF:
					return "Wtf ???";
			}
			
			return "Unknown";		
		}
	}
}