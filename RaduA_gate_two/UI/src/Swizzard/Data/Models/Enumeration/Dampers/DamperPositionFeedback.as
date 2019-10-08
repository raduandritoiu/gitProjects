package Swizzard.Data.Models.Enumeration.Dampers
{
	public class DamperPositionFeedback
	{
		public static const UNKNOWN:int 		= 0;	// "Unknown"
		public static const WITH_FEED:int 		= 1;	// "Yes"
		public static const NO_FEED:int 		= 2;	// "No"
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case WITH_FEED:
					return "Yes";
					
				case NO_FEED:
					return "No";
			}
			
			return "N/A";
		}
	}
}