package Swizzard.Data.Models.Enumeration
{
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticType;
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;
	
	
	public class ProductType
	{
		public static const UNKNOWN:uint		= 0;
		public static const VALVE:uint			= 1;
		public static const ACTUATOR:uint		= 2;
		public static const ASSEMBLY:uint		= 3;
		public static const MISC:uint			= 4;
		public static const DAMPER:uint 		= 16;
		public static const ACCESSORY:uint 		= 32;
		public static const PNEUMATIC:uint 		= 64;
		
		
		public static function toString(flag:uint):String
		{
			switch (flag)
			{
				case UNKNOWN:
					return "Unknown";
					break;
				
				case VALVE:
					return "Valve";
					break;
				
				case ACTUATOR:
					return "Actuator";
					break;
				
				case ASSEMBLY:
					return "Assembly";
					break;
				
				case MISC:
					return "Misc";
					break;
				
				case DAMPER:
					return "Damper";
					break;
				
				case ACCESSORY:
					return "Accessory Kit";
					break;
				
				case PNEUMATIC:
					return "Pneumatic";
					break;
			}
			
			return "N/A";
		}
		
		
		public static function toSubProductString(flag:uint):String 
		{
			var description:String = ValveType.toString(flag);
			if (description != "N/A") {
				return description;
			}
			
			description = DamperType.toString(flag);
			if (description != "N/A") {
				return description;
			}
			
			description = PneumaticType.toString(flag);
			if (description != "N/A") {
				return description;
			}
			
			return "N/A";
		}
	}
}