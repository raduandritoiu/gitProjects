package Swizzard.Data.Models.Enumeration.Dampers
{
	public class DamperAuxilarySwitch
	{
		public static const UNKNOWN:int 		= 0; 	// "Unknown"
		public static const WITH_AUX:int 		= 1; 	// "Yes
		public static const NO_AUX:int 			= 2; 	// "No"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case WITH_AUX:
					return "Yes";
					
				case NO_AUX:
					return "No";
			}
			
			return "N/A";
		}
	}
}