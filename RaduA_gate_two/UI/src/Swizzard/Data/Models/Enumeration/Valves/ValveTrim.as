package Swizzard.Data.Models.Enumeration.Valves
{
	public class ValveTrim
	{
		public static const BRASS:uint				= 1;
		public static const BRASS_NICKEL:uint		= 2;
		public static const BRONZE:uint				= 3;
		public static const STAINLESS_STELL:uint	= 4;
		public static const BRASS__BRONZE:uint		= 5;
		public static const CHROME_BRASS:uint		= 6;
		public static const PLATED_BRASS:uint		= 12;
		
		
		public static function toString(type:uint):String
		{
			switch (type)
			{
				case 1:
					return "Brass";
					break;
				
				case 2:
					return "Brass/Nickel";
					break;
				
				case 3:
					return "Bronze";
					break;
				
				case 4:
					return "Stainless Steel";
					break;
				
				case 6:
					return "Chrome/Brass";
					break;
			}
			return "N/A";
		}
	}
}