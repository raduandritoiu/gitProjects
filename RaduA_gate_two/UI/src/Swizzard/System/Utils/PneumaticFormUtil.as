package Swizzard.System.Utils
{
	import flash.utils.Dictionary;
	
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticMaxThrust;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticMountingStyle;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticPosRelay;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticSelection;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticSpringRange;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticStroke;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticType;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticULCerts;
	import Swizzard.Data.Models.Query.PneumaticQueryModel;
	import Swizzard.Data.Utils.PneumaticTypeInfoUtil;
	
	
	public class PneumaticFormUtil
	{
		private static const ALL_VAL:int = 0;
		
		private static var pneumaticSelectionInfo:Dictionary;
		private static var pneumaticSelectionTable:Dictionary;
		

		public function PneumaticFormUtil()
		{}
		
		
		public static function generatePneumaticSelectionInfo():void {
			pneumaticSelectionInfo = new Dictionary();
			var info:PneumaticTypeInfoUtil;
			
			// DAMPER
			info = new PneumaticTypeInfoUtil();
			info.selectableTypes 	= TypeSel([PneumaticType.NO_3, PneumaticType.NO_4, PneumaticType.NO_6]);
			info.selectableStroke 	= StrokeSel([PneumaticStroke.MM_60, PneumaticStroke.MM_102]);
			info.selectableSpringRange = SpringRangeSel([PneumaticSpringRange.PSI_3_7, PneumaticSpringRange.PSI_3_8, 
				PneumaticSpringRange.PSI_3_13, PneumaticSpringRange.PSI_5_10, PneumaticSpringRange.PSI_8_13, PneumaticSpringRange.PSI_HES]);
			info.selectableMaxThrust = MaxThrustSel([PneumaticMaxThrust.LB_40, PneumaticMaxThrust.LB_55, PneumaticMaxThrust.LB_64, 
				PneumaticMaxThrust.LB_88, PneumaticMaxThrust.LB_89, PneumaticMaxThrust.LB_121, PneumaticMaxThrust.LB_179]);
			info.selection = PneumaticSelection.DAMPER;
			pneumaticSelectionInfo[PneumaticSelection.DAMPER] = info;
			
			// WITH POSITIONER
			info = new PneumaticTypeInfoUtil();
			info.selectableTypes 	= TypeSel([PneumaticType.NO_3, PneumaticType.NO_4, PneumaticType.NO_6]);
			info.selectableStroke 	= StrokeSel([PneumaticStroke.MM_60, PneumaticStroke.MM_102]);
			info.selectableSpringRange = SpringRangeSel([PneumaticSpringRange.PSI_8_13]);
			info.selectableMaxThrust = MaxThrustSel([PneumaticMaxThrust.LB_40, PneumaticMaxThrust.LB_55, PneumaticMaxThrust.LB_89]);
			info.selection = PneumaticSelection.WITH_POSITIONER;
			pneumaticSelectionInfo[PneumaticSelection.WITH_POSITIONER] = info;
			
			// HIGH FORCE
			info = new PneumaticTypeInfoUtil();
			info.selectableTypes 	= TypeSel([PneumaticType.TANDEM, PneumaticType.HIGH_FORCE]);
			info.selectableStroke 	= StrokeSel([PneumaticStroke.MM_102, PneumaticStroke.MM_178]);
			info.selectableSpringRange = SpringRangeSel([PneumaticSpringRange.PSI_8_13]);
			info.selectablePosRelay = PosRelaySel([PneumaticPosRelay.YES]);
			info.selection = PneumaticSelection.HIGH_FORCE;
			pneumaticSelectionInfo[PneumaticSelection.HIGH_FORCE] = info;
			
			// UL LISTED
			info = new PneumaticTypeInfoUtil();
			info.selectableTypes 	= TypeSel([PneumaticType.NO_3, PneumaticType.NO_4, PneumaticType.NO_6, PneumaticType.TANDEM]);
			info.selectableStroke 	= StrokeSel([PneumaticStroke.MM_60, PneumaticStroke.MM_76, PneumaticStroke.MM_102]);
			info.selectableSpringRange = SpringRangeSel([PneumaticSpringRange.PSI_3_7, PneumaticSpringRange.PSI_3_8, 
				PneumaticSpringRange.PSI_3_13, PneumaticSpringRange.PSI_5_10, PneumaticSpringRange.PSI_8_13, PneumaticSpringRange.PSI_HES]);
			info.selection = PneumaticSelection.UL_LISTING;
			pneumaticSelectionInfo[PneumaticSelection.UL_LISTING] = info;
			
			// EVERYTHING
			info = new PneumaticTypeInfoUtil();
			info.selectableTypes 	= TypeSel([PneumaticType.NO_3, PneumaticType.NO_4, PneumaticType.NO_6, PneumaticType.HIGH_FORCE, PneumaticType.TANDEM]);
			info.selectableStroke 	= StrokeSel([PneumaticStroke.MM_60, PneumaticStroke.MM_70, PneumaticStroke.MM_76, PneumaticStroke.MM_102, PneumaticStroke.MM_178]);
			info.selectableSpringRange 	= SpringRangeSel([PneumaticSpringRange.PSI_3_7, PneumaticSpringRange.PSI_3_8, PneumaticSpringRange.PSI_3_13,
				PneumaticSpringRange.PSI_5_10, PneumaticSpringRange.PSI_8_13, PneumaticSpringRange.PSI_HES]);
			info.selectableMaxThrust 	= MaxThrustSel([PneumaticMaxThrust.LB_40, PneumaticMaxThrust.LB_55, PneumaticMaxThrust.LB_64, 
				PneumaticMaxThrust.LB_88, PneumaticMaxThrust.LB_89, PneumaticMaxThrust.LB_121, PneumaticMaxThrust.LB_179]);
			info.selectableULCert 	= ULCertSel([PneumaticULCerts.YES, PneumaticULCerts.NO]);
			info.selectablePosRelay 	= PosRelaySel([PneumaticPosRelay.YES, PneumaticPosRelay.NO]);
			info.selectableMountingStyle = MountingStyleSel([PneumaticMountingStyle.FIXED, PneumaticMountingStyle.FRONT, 
				PneumaticMountingStyle.PIVOT, PneumaticMountingStyle.TANDEM, PneumaticMountingStyle.UNIVERSAL]);
			info.selection = PneumaticSelection.ALL;
			pneumaticSelectionInfo[PneumaticSelection.ALL] = info;
			
			// NONE
			info = new PneumaticTypeInfoUtil();
			info.selection = PneumaticSelection.NONE;
			pneumaticSelectionInfo[PneumaticSelection.NONE] = info;
		}
		
		
		private static function TypeSel(vals:Array):Array {
			var arr:Array = [];
			if (vals.length > 0) arr.push({label:"All", value:ALL_VAL});
			for (var i:int = 0; i < vals.length; i++) {
				arr.push({label:PneumaticType.toString(vals[i]), value:vals[i]});
			}
			return arr;
		}
		
		private static function StrokeSel(vals:Array):Array {
			var arr:Array = [];
			if (vals.length > 0) arr.push({label:"All", value:ALL_VAL});
			for (var i:int = 0; i < vals.length; i++) {
				arr.push({label:PneumaticStroke.toString(vals[i]), value:vals[i]});
			}
			return arr;
		}
		
		private static function PosRelaySel(vals:Array):Array {
			var arr:Array = [];
			if (vals.length > 0) arr.push({label:"All", value:ALL_VAL});
			for (var i:int = 0; i < vals.length; i++) {
				arr.push({label:PneumaticPosRelay.toString(vals[i]) , value:vals[i]});
			}
			return arr;
		}
		
		private static function ULCertSel(vals:Array):Array {
			var arr:Array = [];
			if (vals.length > 0) arr.push({label:"All", value:ALL_VAL});
			for (var i:int = 0; i < vals.length; i++) {
				arr.push({label:PneumaticULCerts.toString(vals[i]) , value:vals[i]});
			}
			return arr;
		}
		
		private static function SpringRangeSel(vals:Array):Array {
			var arr:Array = [];
			if (vals.length > 0) arr.push({label:"All", value:ALL_VAL});
			for (var i:int = 0; i < vals.length; i++) {
				arr.push({label:PneumaticSpringRange.toString(vals[i]) , value:vals[i]});
			}
			return arr;
		}
		
		private static function MaxThrustSel(vals:Array):Array {
			var arr:Array = [];
			if (vals.length > 0) arr.push({label:"All", value:ALL_VAL});
			for (var i:int = 0; i < vals.length; i++) {
				arr.push({label:vals[i]+" lb" , value:vals[i]});
			}
			return arr;
		}
		
		private static function MountingStyleSel(vals:Array):Array {
			var arr:Array = [];
			if (vals.length > 0) arr.push({label:"All", value:ALL_VAL});
			for (var i:int = 0; i < vals.length; i++) {
				arr.push({label:PneumaticMountingStyle.toString(vals[i]) , value:vals[i]});
			}
			return arr;
		}
		
		
		public static function generatePneumaticSelectionTable():void {
			pneumaticSelectionTable = new Dictionary();
			var poss:Array;
			
			// DAMPER
			poss = [];
			// Stroke Length							Spring Range								Force @ 18psi							Actuator Size
			poss.push({stroke:PneumaticStroke.MM_60,	springRange:PneumaticSpringRange.PSI_3_13, 	maxThrust:PneumaticMaxThrust.LB_88, 	type:PneumaticType.NO_3});
			poss.push({stroke:PneumaticStroke.MM_60,	springRange:PneumaticSpringRange.PSI_5_10, 	maxThrust:PneumaticMaxThrust.LB_64, 	type:PneumaticType.NO_3});
			poss.push({stroke:PneumaticStroke.MM_60,	springRange:PneumaticSpringRange.PSI_8_13, 	maxThrust:PneumaticMaxThrust.LB_40, 	type:PneumaticType.NO_3});
			poss.push({stroke:PneumaticStroke.MM_60,	springRange:PneumaticSpringRange.PSI_HES, 	maxThrust:PneumaticMaxThrust.LB_55, 	type:PneumaticType.NO_4});
			// what about this one???
			poss.push({stroke:PneumaticStroke.MM_76,	springRange:PneumaticSpringRange.PSI_HES, 	maxThrust:PneumaticMaxThrust.LB_55, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102,	springRange:PneumaticSpringRange.PSI_3_7, 	maxThrust:PneumaticMaxThrust.LB_121, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102,	springRange:PneumaticSpringRange.PSI_3_8, 	maxThrust:PneumaticMaxThrust.LB_179, 	type:PneumaticType.NO_6});
			poss.push({stroke:PneumaticStroke.MM_102,	springRange:PneumaticSpringRange.PSI_3_13, 	maxThrust:PneumaticMaxThrust.LB_55, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102,	springRange:PneumaticSpringRange.PSI_3_13, 	maxThrust:PneumaticMaxThrust.LB_89, 	type:PneumaticType.NO_6});
			poss.push({stroke:PneumaticStroke.MM_102,	springRange:PneumaticSpringRange.PSI_5_10, 	maxThrust:PneumaticMaxThrust.LB_88, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102,	springRange:PneumaticSpringRange.PSI_8_13, 	maxThrust:PneumaticMaxThrust.LB_55, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102,	springRange:PneumaticSpringRange.PSI_8_13, 	maxThrust:PneumaticMaxThrust.LB_89, 	type:PneumaticType.NO_6});
			poss.push({stroke:PneumaticStroke.MM_102,	springRange:PneumaticSpringRange.PSI_HES, 	maxThrust:PneumaticMaxThrust.LB_55, 	type:PneumaticType.NO_4});
			pneumaticSelectionTable[PneumaticSelection.DAMPER] = poss;
			
			// WITH POSITIONER
			poss = [];
			// Stroke Length							Spring Range								Force @ 18psi						Actuator Size
			poss.push({stroke:PneumaticStroke.MM_60,	springRange:PneumaticSpringRange.PSI_8_13, 	maxThrust:PneumaticMaxThrust.LB_40, type:PneumaticType.NO_3});
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_8_13, 	maxThrust:PneumaticMaxThrust.LB_55, type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_8_13, 	maxThrust:PneumaticMaxThrust.LB_89, type:PneumaticType.NO_6});
			pneumaticSelectionTable[PneumaticSelection.WITH_POSITIONER] = poss;
			
			// HIGH FORCE
			poss = [];
			// Stroke Length							Spring Range								Positioner						Actuator Size
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_8_13, 	posRelay:PneumaticPosRelay.YES,	type:PneumaticType.TANDEM});
			poss.push({stroke:PneumaticStroke.MM_178, 	springRange:PneumaticSpringRange.PSI_8_13, 	posRelay:PneumaticPosRelay.YES,	type:PneumaticType.HIGH_FORCE});
			pneumaticSelectionTable[PneumaticSelection.HIGH_FORCE] = poss;
			
			// UL Listed
			poss = [];
			// Stroke Length							Spring Range								Actuator Size
			poss.push({stroke:PneumaticStroke.MM_60, 	springRange:PneumaticSpringRange.PSI_3_7, 	type:PneumaticType.NO_3});
			poss.push({stroke:PneumaticStroke.MM_60, 	springRange:PneumaticSpringRange.PSI_5_10, 	type:PneumaticType.NO_3});
			poss.push({stroke:PneumaticStroke.MM_60, 	springRange:PneumaticSpringRange.PSI_8_13, 	type:PneumaticType.NO_3});
			poss.push({stroke:PneumaticStroke.MM_76, 	springRange:PneumaticSpringRange.PSI_HES, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_3_7, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_3_13, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_5_10, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_8_13, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_HES, 	type:PneumaticType.NO_4});
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_3_13, 	type:PneumaticType.NO_6});
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_8_13, 	type:PneumaticType.NO_6});
			poss.push({stroke:PneumaticStroke.MM_102, 	springRange:PneumaticSpringRange.PSI_8_13, 	type:PneumaticType.TANDEM});
			pneumaticSelectionTable[PneumaticSelection.UL_LISTING] = poss;
		}
		
		private static function filterOne(source:Array, name:String, val:int):Array {
			var res:Array = [];
			for (var i:int = 0; i < source.length; i++) {
				var elem:Object = source[i];
				if (val == ALL_VAL) 
					res.push(elem);
				else if (!elem.hasOwnProperty(name))
					res.push(elem);
				else if (elem[name] == val)
					res.push(elem);
			}
			return res;
		}
		
		private static function filterMany(source:Array, constraints:Array, ignore:String = "bogus"):Array {
			var res:Array = source;
			for (var i:int = 0; i < constraints.length; i++) {
				var constraint:Object = constraints[i];
				if (constraint.name != ignore) {
					res = filterOne(res, constraint.name, constraint.val);
				}
			}
			return res;
		}
		
		private static function extractVals(source:Array, name:String):Array {
			var res:Array = [];
			for (var i:int = 0; i < source.length; i++) {
				var elem:Object = source[i];
				if (elem.hasOwnProperty(name))
					res.push(elem[name]);
			}
			res = res.sort(Array.NUMERIC);
			return res;
		}
		
		
		/**
		 * Returns the valve information for the given valve type
		 **/
		public static function getPneumaticEnumInfo2(queryModel:PneumaticQueryModel):PneumaticTypeInfoUtil {
			if (queryModel.selection == PneumaticSelection.NONE || queryModel.selection == PneumaticSelection.ALL)
				return getPneumaticEnumInfo(queryModel.selection);
				
			var source:Array = pneumaticSelectionTable[queryModel.selection];
			var info:PneumaticTypeInfoUtil = new PneumaticTypeInfoUtil();
			// create constraints
			var constraints:Array = [{name:"stroke", val:queryModel.stroke}, {name:"springRange", val:queryModel.springRange},
				{name:"maxThrust", val:queryModel.maxThrust}, {name:"posRelay", val:queryModel.posRelay}, {name:"type", val:queryModel.pneumType}];
			
			var arr:Array;
			// stroke
			arr = filterMany(source, constraints, "stroke");
			info.selectableStroke = StrokeSel(extractVals(arr, "stroke"));
			// springRange
			arr = filterMany(source, constraints, "springRange");
			info.selectableSpringRange = SpringRangeSel(extractVals(arr, "springRange"));
			// maxThrust
			arr = filterMany(source, constraints, "maxThrust");
			info.selectableMaxThrust = MaxThrustSel(extractVals(arr, "maxThrust"));
			// posRelay
			arr = filterMany(source, constraints, "posRelay");
			info.selectablePosRelay = PosRelaySel(extractVals(arr, "posRelay"));
			// type
			arr = filterMany(source, constraints, "type");
			info.selectableTypes = TypeSel(extractVals(arr, "type"));
			
			info.selection = queryModel.selection;
			return info;
		}
		
		
		public static function getPneumaticEnumInfo(selection:int):PneumaticTypeInfoUtil {
			if (pneumaticSelectionInfo.hasOwnProperty(selection)) {
				return pneumaticSelectionInfo[selection] as PneumaticTypeInfoUtil;
			}			
			return null;
		}
	}
}