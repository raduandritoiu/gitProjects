package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticSpringRange 
	{
		public static const Unknown:int 	= 0			// - "Unknown"
		public static const PSI_3_7:int 	= 1			// - "3-7 psi (21-48 kPa)"	
		public static const PSI_3_8:int 	= 2			// - "3-8 psi (21-55 kPa)"	
		public static const PSI_3_13:int 	= 3			// - "3-13 psi (21-90 kPa)"	
		public static const PSI_5_10:int 	= 4			// - "5-10 psi (35-70 kPa)"	
		public static const PSI_8_13:int 	= 5			// - "8-13 psi (55-90 kPa)"	
		public static const PSI_HES:int 	= 6			// - "2-3 psi, 8-13 psi (14-21, 55-90 kPa) Hesitation Model"

		
		public static function toString(flag:int):String {
			switch (flag) {
				case PSI_3_7:
					return "3-7 psi (21-48 kPa)";
				
				case PSI_3_8:
					return "3-8 psi (21-55 kPa)";
				
				case PSI_3_13:
					return "3-13 psi (21-90 kPa)";
				
				case PSI_5_10:
					return "5-10 psi (35-70 kPa)";
				
				case PSI_8_13:
					return "8-13 psi (55-90 kPa)";
				
				case PSI_HES:
					return "2-3, 8-13 Hesitation";
			}
			
			return "Unknown";		
		}
	}
}