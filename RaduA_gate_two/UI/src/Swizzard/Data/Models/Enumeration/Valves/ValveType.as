package Swizzard.Data.Models.Enumeration.Valves
{
	public class ValveType
	{
		public static const UNKNOWN:uint	= 0;
		public static const BALL:uint		= 1;
		public static const GLOBE:uint		= 2;
		public static const ZONE:uint		= 3;
		public static const MAGNETIC:uint	= 4;
		
		// RADUA 
//		public static const BUTTERFLY:uint	= 5;
		
		public static const PICV:uint		= 6;
		
		
		public static function toString(flag:uint):String
		{
			switch (flag)
			{
				case BALL:
					return "Ball";
					break;

				case GLOBE:
					return "Globe";
					break;
				
				case ZONE:
					return "Zone";
					break;
				
				case MAGNETIC:
					return "Magnetic";
					break;
				
				//RADUA
//				case BUTTERFLY:
//					return "Butterfly";
//					break;
				
				case PICV:
					return "PICV";
					break;
			}
			
			return "N/A";
		}
	}
}