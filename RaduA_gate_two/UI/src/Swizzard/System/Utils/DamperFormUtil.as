package Swizzard.System.Utils
{
	import flash.utils.Dictionary;
	
	import Swizzard.Data.Models.Enumeration.Dampers.DamperAuxilarySwitch;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperControlSignal;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperPlenum;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperPositionFeedback;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperScalableSignal;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperSystemSupply;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;
	import Swizzard.Data.Utils.DamperTypeInfoUtil;
	
	
	public class DamperFormUtil
	{
		public static const ALL_VAL:int = 0;
//		public static const DEFAULT_VAL:int = -1;
		
		
		private static var damperTypeInfo:Dictionary;
		
		
		public function DamperFormUtil()
		{}
		
		
		public static function generateDamperTypeInfo():void {
			damperTypeInfo = new Dictionary();
			
			var family:Array; // just for kiks and for my own understanding and remember the product family 
			
			var springInfo:DamperTypeInfoUtil 		= new DamperTypeInfoUtil();
			springInfo.damperType 					= DamperType.SPRING_RETURN;
			springInfo.selectableTorque 			= [20, 35, 62, 160];
			springInfo.selectableControlSignal 		= [{label:"All", value:ALL_VAL}, {label:"2 PT", value:DamperControlSignal.PT_2}, {label:"Floating", value:DamperControlSignal.Floating}, {label:"0-10 VDC", value:DamperControlSignal.VDC_0_10}, {label:"2-10 VDC", value:DamperControlSignal.VDC_2_10}, {label:"0-10/2-10 VDC", value: DamperControlSignal.VDC_0_2_10}];
			springInfo.selectableSystemSupply 		= [{label:"All", value:ALL_VAL}, {label:"24 VAC", value:DamperSystemSupply.VAC_24}, {label:"24 VAC/DC", value:DamperSystemSupply.VAC_DC_24}, {label:"120 VAC", value:DamperSystemSupply.VAC_120}];
			springInfo.selectablePlenumRating 		= [{label:"All", value:ALL_VAL}, {label:"Plenum", value:DamperPlenum.PLENUM}, {label:"Standard", value:DamperPlenum.STANDARD}];
			springInfo.selectableAuxilarySwitch 	= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperAuxilarySwitch.WITH_AUX}, {label:"No", value:DamperAuxilarySwitch.NO_AUX}];
			springInfo.selectablePositionFeedback 	= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperPositionFeedback.WITH_FEED}, {label:"No", value:DamperPositionFeedback.NO_FEED}];
			springInfo.selectableScalableSignal 	= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperScalableSignal.WITH_SIG}, {label:"No", value:DamperScalableSignal.NO_SIG}];
			damperTypeInfo[springInfo.damperType] 	= springInfo;
			family = ["GCA", "GMA", "GQD"];
			
			
			var nonSpringInfo:DamperTypeInfoUtil 		= new DamperTypeInfoUtil();
			nonSpringInfo.damperType 					= DamperType.NON_SPRING_RETURN;
			nonSpringInfo.selectableTorque 				= [20, 44, 88, 132, 221, 310];
			nonSpringInfo.selectableControlSignal 		= [{label:"All", value:ALL_VAL}, {label:"Floating/2PT", value:DamperControlSignal.Floating_PT_2}, {label:"0-10 VDC", value:DamperControlSignal.VDC_0_10}, {label:"0-10/2-10 VDC", value:DamperControlSignal.VDC_0_2_10}];
			nonSpringInfo.selectableSystemSupply 		= [{label:"All", value:ALL_VAL}, {label:"24 VAC", value:DamperSystemSupply.VAC_24}]; // - delete All ???
			nonSpringInfo.selectablePlenumRating 		= [{label:"All", value:ALL_VAL}, {label:"Plenum", value:DamperPlenum.PLENUM}, {label:"Standard", value:DamperPlenum.STANDARD}];
			nonSpringInfo.selectableAuxilarySwitch 		= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperAuxilarySwitch.WITH_AUX}, {label:"No", value:DamperAuxilarySwitch.NO_AUX}];
			nonSpringInfo.selectablePositionFeedback 	= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperPositionFeedback.WITH_FEED}, {label:"No", value:DamperPositionFeedback.NO_FEED}];
			nonSpringInfo.selectableScalableSignal 		= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperScalableSignal.WITH_SIG}, {label:"No", value:DamperScalableSignal.NO_SIG}];
			damperTypeInfo[nonSpringInfo.damperType] 	= nonSpringInfo;
			family = ["GDE", "GLB", "GEB", "GBB", "GIB"];
			
				
			var fireInfo:DamperTypeInfoUtil 		= new DamperTypeInfoUtil();
			fireInfo.damperType 					= DamperType.FIRE_AND_SMOKE;
			fireInfo.selectableTorque 				= [53, 142];
			fireInfo.selectableControlSignal 		= [{label:"All", value:ALL_VAL}, {label:"2 PT", value:DamperControlSignal.PT_2}]; // - delete All ???
			fireInfo.selectableSystemSupply 		= [{label:"All", value:ALL_VAL}, {label:"24 VAC", value:DamperSystemSupply.VAC_24}, {label:"24 VAC/DC", value:DamperSystemSupply.VAC_DC_24}, {label:"120 VAC", value:DamperSystemSupply.VAC_120}, {label:"230 VAC", value:DamperSystemSupply.VAC_230}];
			fireInfo.selectablePlenumRating 		= [{label:"All", value:ALL_VAL}, {label:"Standard", value:DamperPlenum.STANDARD}]; // - delete All ???
			fireInfo.selectableAuxilarySwitch 		= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperAuxilarySwitch.WITH_AUX}, {label:"No", value:DamperAuxilarySwitch.NO_AUX}];
			fireInfo.selectablePositionFeedback 	= [{label:"All", value:ALL_VAL}, {label:"No", value:DamperPositionFeedback.NO_FEED}]; // - delete All ???
			fireInfo.selectableScalableSignal 		= [{label:"All", value:ALL_VAL}, {label:"No", value:DamperScalableSignal.NO_SIG}]; // - delete All ???
			damperTypeInfo[fireInfo.damperType] 	= fireInfo;
			family = ["GND", "GGD"];
			
			
			var fastInfo:DamperTypeInfoUtil 		= new DamperTypeInfoUtil();
			fastInfo.damperType 					= DamperType.FAST_ACTING;
			fastInfo.selectableTorque 				= [53];
			fastInfo.selectableControlSignal 		= [{label:"All", value:ALL_VAL}, {label:"Universal", value:DamperControlSignal.Universal}]; // - delete All ???
			fastInfo.selectableSystemSupply 		= [{label:"All", value:ALL_VAL}, {label:"24 VAC/DC", value:DamperSystemSupply.VAC_DC_24}]; // - delete All ???
			fastInfo.selectablePlenumRating 		= [{label:"All", value:ALL_VAL}, {label:"Plenum", value:DamperPlenum.PLENUM}]; // - delete All ???
			fastInfo.selectableAuxilarySwitch 		= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperAuxilarySwitch.WITH_AUX}, {label:"No", value:DamperAuxilarySwitch.NO_AUX}];
			fastInfo.selectablePositionFeedback 	= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperPositionFeedback.WITH_FEED}, {label:"No", value:DamperPositionFeedback.NO_FEED}];
			fastInfo.selectableScalableSignal 		= [{label:"All", value:ALL_VAL}, {label:"No", value:DamperScalableSignal.NO_SIG}]; // - delete All ???
			damperTypeInfo[fastInfo.damperType] 	= fastInfo;
			family = ["GNP", "GAP"];
			
			/*
			var pneumaticInfo:DamperTypeInfoUtil 		= new DamperTypeInfoUtil();
			pneumaticInfo.damperType 					= DamperType.PNEUMATIC_GENERAL;
			pneumaticInfo.selectableTorque 				= [];
			pneumaticInfo.selectableControlSignal 		= [];
			pneumaticInfo.selectableSystemSupply 		= [{label:"All", value:ALL_VAL}, {label:"24 VAC", value:DamperSystemSupply.VAC_24}, {label:"24 VAC/DC", value:DamperSystemSupply.VAC_DC_24}, {label:"120 VAC", value:DamperSystemSupply.VAC_120}, {label:"230 VAC", value:DamperSystemSupply.VAC_230}];
			pneumaticInfo.selectablePlenumRating 		= [{label:"All", value:ALL_VAL}, {label:"Plenum", value:DamperPlenum.PLENUM}, {label:"Standard", value:DamperPlenum.STANDARD}];
			pneumaticInfo.selectableAuxilarySwitch 		= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperAuxilarySwitch.WITH_AUX}, {label:"No", value:DamperAuxilarySwitch.NO_AUX}];
			pneumaticInfo.selectablePositionFeedback 	= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperPositionFeedback.WITH_FEED}, {label:"No", value:DamperPositionFeedback.NO_FEED}];
			pneumaticInfo.selectableScalableSignal 		= [{label:"All", value:ALL_VAL}, {label:"Yes", value:DamperScalableSignal.WITH_SIG}, {label:"No", value:DamperScalableSignal.NO_SIG}];
			damperTypeInfo[pneumaticInfo.damperType] 	= pneumaticInfo;
			*/
		}
		
		
		/**
		 * Returns the valve information for the given valve type
		 **/
		public static function getDamperEnumInfo(type:int):DamperTypeInfoUtil {
			if (damperTypeInfo.hasOwnProperty(type)) {
				return damperTypeInfo[type] as DamperTypeInfoUtil;
			}			
			return null;
		}
	}
}