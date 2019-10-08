package Swizzard.Data.Models.Enumeration.Dampers
{
	public class DamperType
	{
		public static const UNKNOWN:int				= 0;
		public static const SPRING_RETURN:int		= 101;
		public static const NON_SPRING_RETURN:int	= 102;
		public static const FIRE_AND_SMOKE:int		= 103;
		public static const FAST_ACTING:int			= 104;
		public static const PNEUMATIC_GENERAL:int	= 105;
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case SPRING_RETURN:
					return "Spring Return";
				
				case NON_SPRING_RETURN:
					return "Non-Spring Return";
				
				case FIRE_AND_SMOKE:
					return "Fire and Smoke";
				
				case FAST_ACTING:
					return "Fast-Acting";
				
				case PNEUMATIC_GENERAL:
					return "Pneumatics";
			}
			
			return "N/A";
		}
	}
}