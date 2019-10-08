package Swizzard.Data.Models.Enumeration.Dampers
{
	public class DamperControlSignal
	{
		public static const UNKNOWN:int 		= 0;	// "Unknown"
		public static const PT_2:int 			= 1;	// "2-position"
		public static const Floating:int 		= 2;	// "Floating control"
		public static const Floating_PT_2:int 	= 3;	// "Floating control/2-positions"
		public static const VDC_0_10:int 		= 4;	// "0-10 VDC"
		public static const VDC_2_10:int 		= 8;	// "2-10 VDC"
		public static const VDC_0_2_10:int 		= 12;	// "0-10/2-10 VDC"
		public static const Universal:int 		= 16;	// "Universal"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case PT_2:
					return "2-position";
					
				case Floating:
					return "Floating";
					
				case Floating_PT_2:
					return "Floating/2-position";
					
				case VDC_0_10:
					return "0-10 VDC";
				
				case VDC_2_10:
					return "2-10 VDC";
				
				case VDC_0_2_10:
					return "0-10/2-10 VDC";
				
				case Universal:
					return "Universal";
			}
			
			return "N/A";
		}
	}
}