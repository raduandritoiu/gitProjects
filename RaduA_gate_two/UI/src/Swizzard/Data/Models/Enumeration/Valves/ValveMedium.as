package Swizzard.Data.Models.Enumeration.Valves
{
	public class ValveMedium
	{
		public static const UNKNOWN:uint	= 0;
		public static const WATER:uint		= 2;
		public static const GLYCOL:uint		= 4;
		public static const STEAM:uint		= 8;
		
		
		public static function flagToString(flag:uint):String
		{
			var result:String	= new String();
			
			if ((WATER & flag) == WATER)
			{
				result += "Water";
			}
			
			if ((STEAM & flag) == STEAM)
			{
				if (result)
					result += ", ";
				result += "Steam";
			}
			
			if ((GLYCOL & flag) == GLYCOL)
			{
				if (result)
					result += ", ";
				result += "Glycol";
			}
			
			if (!result)
				result == "N/A";
			
			return result;
		}
	}
}