package Swizzard.Data.Models.Enumeration.Valves
{
	public class ValvePattern
	{
		public static const TWO_WAY:uint	= 1;
		public static const THREE_WAY:uint	= 2;
		public static const BOTH:uint		= 3;
		public static const SIX_WAY:uint	= 4;
		
		
		public static function toString(flag:uint):String
		{
			switch (flag)
			{
				case TWO_WAY:
					return "2 Way";
					break;
				
				case THREE_WAY:
					return "3 Way";
					break;
				
				case BOTH:
					return "2 & 3 Way";
					break;
				
				case SIX_WAY:
					return "6 Way";
					break;
			}
			return "N/A";
		}
	}
}