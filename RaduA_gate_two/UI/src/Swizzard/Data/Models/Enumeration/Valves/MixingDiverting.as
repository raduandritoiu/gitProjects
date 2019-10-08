package Swizzard.Data.Models.Enumeration.Valves
{
	public class MixingDiverting
	{
		public static const UNKNOWN:uint	= 0;
		public static const MIXING:uint		= 2;
		public static const DIVERTING:uint	= 4;
		
		
		public static function toString(type:uint):String
		{
			var result:Array	= new Array();
			
			if (type == 0)
				return "";
				
			if ((type & MIXING) == MIXING)
				result.push("Mixing");
			
			if ((type & DIVERTING) == DIVERTING)
				result.push("Diverting");
			
			return result.join(", ");
		}
	}
}