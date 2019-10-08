package Swizzard.Data.Models.Enumeration.Dampers
{
	public class DamperScalableSignal
	{
		public static const UNKNOWN:int 		= 0;	// "Unknown"
		public static const WITH_SIG:int 		= 1;	// "Yes"
		public static const NO_SIG:int 			= 2;	// "No"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case WITH_SIG:
					return "Yes";
					
				case NO_SIG:
					return "No";
			}
			
			return "N/A";
		}	}
}