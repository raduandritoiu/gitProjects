package Swizzard.Data.Models.Enumeration
{
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticType;
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;

	public class ExtendedProductType
	{
		public static const UNKNOWN:uint			= 0;
		public static const VALVE:uint 				= 1;
		public static const ACTUATOR_VALVE:uint 	= 2;
		public static const MISC:uint 				= 4;
		public static const ASSEMBLY:uint 			= 8;
		public static const DAMPER_ACTUATOR:uint 	= 16;
		public static const ACTUATOR_BOTH:uint 		= 18;
		public static const ACCESSORY:uint 		= 32;
		public static const PNEUMATIC:uint 			= 64;

		
		public static function toString(flag:uint):String {
			switch (flag) {
				case UNKNOWN:
					return "Unknown";
				
				case VALVE:
					return "Valve";
				
				case ACTUATOR_VALVE:
					return "Actuator";
				
				case ASSEMBLY:
					return "Assembly";
				
				case MISC:
					return "Misc";
				
				case DAMPER_ACTUATOR:
					return "Damper";
				
				case ACTUATOR_BOTH:
					return "General Actuator";
				
				case ACCESSORY:
					return "Accessory Kit";
					
				case PNEUMATIC:
					return "Pneumatic";
			}
			
			return "N/A";
		}
		
		
		public static function toSubProductString(flag:uint):String {
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
		
		
		public static function toDamperDimensionType(flag:uint):uint {
			switch (flag) {
				case UNKNOWN:
				case VALVE:
				case ACTUATOR_VALVE:
				case ASSEMBLY:
				case MISC:
					return ProductType.MISC;
					
				case DAMPER_ACTUATOR:
				case ACTUATOR_BOTH:
					return ProductType.DAMPER;
					
				case ACCESSORY:
					return ProductType.ACCESSORY;
			}
			
			return UNKNOWN;
		}
		
		
		public static function toValveDimensionType(flag:uint):uint {
			switch (flag) {
				case UNKNOWN:
				case DAMPER_ACTUATOR:
				case ACCESSORY:
				case PNEUMATIC:
					return ProductType.UNKNOWN;
					
				case VALVE:
					return ProductType.VALVE;
					
				case ACTUATOR_VALVE:
				case ACTUATOR_BOTH:
					return ProductType.ACTUATOR;
					
				case ASSEMBLY:
					return ProductType.ASSEMBLY;
					
				case MISC:
					return ProductType.MISC;
			}
			
			return UNKNOWN;
		}
		
		
		public static function fromValveDimensionType(flag:uint):uint {
			switch (flag) {
				case ProductType.UNKNOWN:
					return UNKNOWN;
					
				case ProductType.VALVE:
					return VALVE;
					
				case ProductType.ACTUATOR:
					return ACTUATOR_VALVE;
					
				case ProductType.ASSEMBLY:
					return ASSEMBLY;
					
				case ProductType.MISC:
					return MISC;
					
				case ProductType.DAMPER:
					return DAMPER_ACTUATOR;
					
				case ProductType.ACCESSORY:
					return ACCESSORY;
					
				case ProductType.PNEUMATIC:
					return PNEUMATIC;
			}
			
			return UNKNOWN;
		}
	}
}