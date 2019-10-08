package Swizzard.Data.Models.Enumeration.Pneumatic
{
	public class PneumaticType
	{
		public static const UNKNOWN:int		= 0;
		public static const NO_3:int		= 203;
		public static const NO_4:int		= 204;
		public static const NO_6:int		= 206;
		public static const HIGH_FORCE:int	= 207;
		public static const TANDEM:int		= 208;
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case NO_3:
					return "No. 3";
					
				case NO_4:
					return "No. 4";
					
				case NO_6:
					return "No. 6";
					
				case HIGH_FORCE:
					return "High Force";
					
				case TANDEM:
					return "Tandem";
			}
			
			return "Unknown";
		}	
	}
}