package Swizzard.System.Utils
{
	import flash.utils.Dictionary;
	
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorControlSignal;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorEndSwitch;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorMotorType;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorPositioner;
	import Swizzard.Data.Models.Enumeration.Valves.FlowCharacteristics;
	import Swizzard.Data.Models.Enumeration.Valves.ValveConnection;
	import Swizzard.Data.Models.Enumeration.Valves.ValveMedium;
	import Swizzard.Data.Models.Enumeration.Valves.ValveTrim;
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;
	import Swizzard.Data.Utils.ValveTypeInformation;
	
	
	public class ValveFormUtil
	{
		private static var valveTypeInfo:Dictionary;
		
		/**
		 * Generate valve type information
		 * 
		 * This is used by the forms to determine the enumeration for selectable properties
		 * based off of the selected valve.
		 **/
		public static function generateValveTypeInfo():void
		{
			valveTypeInfo	= new Dictionary();
			
			// Zone Valve Info
			var zoneInfo:ValveTypeInformation	 = new ValveTypeInformation();
			zoneInfo.valveType			= ValveType.ZONE;
			zoneInfo.selectableCV		= [1, 2.5, 4, 4.1, 7];
			zoneInfo.selectableSizes	= [{label: "All", value: null}, {label: "1/2", value: .5}, {label: "3/4", value: .75}, {label: "1", value: 1}];
			zoneInfo.selectableTrim		= [{label: "All", value: null}, {label: "Brass/Bronze", value: ValveTrim.BRASS__BRONZE}];
			zoneInfo.selectableConnections	= [{label: "All", value: null}, {label: "NPT", value: ValveConnection.NPT}, {label: "Sweat", value: ValveConnection.SWEAT}];
			zoneInfo.selectableMedium		= [{label: "Water", value: ValveMedium.WATER}, {label: "Glycol", value: ValveMedium.GLYCOL}];
			zoneInfo.selectablePressureClass= [{label: "All", value: null}, {label: "ANSI 125", value: 125}];
			zoneInfo.selectablePortConfig	= 
											[
												{label: "All", 						value: 0},
												{label: "2 Way", 					value: 1},
				 								{label: "2 Way Normally Open", 		value: 2},
				 								{label: "2 Way Normally Closed", 	value: 3},
				 								{label: "2 Way Normally Closed", 	value: 3},
				 								{label: "3 Way Diverting", 			value: 6}
				 							];
			
			zoneInfo.actuatorTypes			= [{label: "All", value: -1}, {label: "Electric", value: ActuatorMotorType.ELECTRIC}];
			zoneInfo.supplyVoltage			= [{label: "All", value: -1}, {label: "24V", value: 24}, {label: "120V", value: 120}];
			zoneInfo.electricControlSignal	= [{label: "All", value: -1}, {label: "On/Off", value: ActuatorControlSignal.ON_OFF}, {label: "Floating", value: ActuatorControlSignal.s3_POS}, {label: "0-10V", value: ActuatorControlSignal.s0_10_VDC}];
			zoneInfo.selectableFlowChar		= [{label: "All", value: null}, {label: "Linear", value: FlowCharacteristics.LINEAR}];
			
			zoneInfo.disablePositioner		= true;
			valveTypeInfo[zoneInfo.valveType]	= zoneInfo;
			
			
			// Ball Valve Info
			var ballInfo:ValveTypeInformation	= new ValveTypeInformation();
			ballInfo.valveType				= ValveType.BALL;
			ballInfo.selectableCV			= [0.3, 0.4, 0.5, 0.63, 0.8, 1, 1.2, 1.5, 1.6, 1.9, 2.5, 2.9, 4, 4.7, 6.3, 10, 16, 25, 40, 63, 100, 160, 250];
			ballInfo.selectableSizes		= [{label: "All", value: null}, {label: "1/2", value: .5}, {label: "3/4", value: .75}, {label: "1", value: 1}, {label: "1-1/4", value: 1.25}, {label: "1-1/2", value: 1.5}, {label: "2", value: 2}];
			ballInfo.selectableTrim			= [{label: "All", value: null}, {label: "Plated Brass", value: ValveTrim.PLATED_BRASS}, {label: "Stainless Steel", value: ValveTrim.STAINLESS_STELL}];
			ballInfo.selectableConnections	= [{label: "All", value: null}, {label: "FxF", value: ValveConnection.FxF}, {label: "UFxUF", value: ValveConnection.UFxUF}];
			ballInfo.selectableMedium		= [{label: "Water", value: ValveMedium.WATER}, {label: "Glycol", value: ValveMedium.GLYCOL}];
			ballInfo.selectablePressureClass = [{label: "All", value: null}, {label: "232 psi", value: 232}, {label: "600 WOG", value: 600}];
			ballInfo.selectablePortConfig	= 
											[
												{label: "All", 						value: null},
												{label: "2 Way", 					value: 1},
				 								{label: "2 Way Normally Open", 		value: 2},
				 								{label: "2 Way Normally Closed", 	value: 3},
												{label: "3 Way", 					value: 4},
				 								{label: "3 Way Mixing", 			value: 5},
												{label: "3 Way Diverting", 			value: 6},
												{label: "6 Way", 					value: 8}
				 							];
			
			ballInfo.actuatorTypes			= [{label: "All", value: -1}, {label: "Electric", value: ActuatorMotorType.ELECTRIC}];
			ballInfo.supplyVoltage			= [{label: "All", value: -1}, {label: "24V", value: 24}, {label: "120V", value: 120}];
			ballInfo.electricControlSignal	= [{label: "All", value: -1}, {label: "On/Off", value: ActuatorControlSignal.ON_OFF}, {label: "Floating", value: ActuatorControlSignal.s3_POS}, {label: "0-10V", value: ActuatorControlSignal.s0_10_VDC}, {label: "2-10V", value: ActuatorControlSignal.s2_10_VDC}];
			ballInfo.selectableFlowChar		= [{label: "All", value: null}, {label: "Equal %", value: FlowCharacteristics.EQUAL_PERCENT}, {label: "Linear", value: FlowCharacteristics.LINEAR}];
			ballInfo.disablePositioner		= true;
			ballInfo.endSwitchSelection		= [{label: "All", value: -1}, {label: "None", value: ActuatorEndSwitch.NONE}, {label: "Electric", value: ActuatorEndSwitch.ELECTRIC}];
			valveTypeInfo[ballInfo.valveType]	= ballInfo;
			
			
			// Globe Valve Info
			var globeInfo:ValveTypeInformation	= new ValveTypeInformation();
			globeInfo.valveType					= ValveType.GLOBE;
			globeInfo.selectableCV			= [0.4, 0.63, 1, 1.6, 2.5, 4, 6.3, 10, 16, 25, 40, 63, 100, 160, 250, 400];
			globeInfo.selectableSizes		= [{label: "All", value: null}, {label: "1/2", value: .5}, {label: "3/4", value: .75}, {label: "1", value: 1}, {label: "1-1/4", value: 1.25}, {label: "1-1/2", value: 1.5}, {label: "2", value: 2}, {label: "2-1/2", value: 2.5}, {label: "3", value: 3}, {label: "4", value: 4}, {label: "5", value: 5}, {label: "6", value: 6}];
			globeInfo.selectableTrim		= [{label: "All", value: null}, {label: "Brass/Bronze", value: ValveTrim.BRASS__BRONZE}, {label: "Stainless Steel", value: ValveTrim.STAINLESS_STELL}];
			globeInfo.selectableConnections	= [{label: "All", value: null}, {label: "FxF", value: ValveConnection.FxF}, {label: "FxUM", value: ValveConnection.FxUM}, {label: "AFxUM", value: ValveConnection.AFxUM}, {label: "Flared", value: ValveConnection.FRxFR}, {label: "Flanged", value: ValveConnection.FLxFL}]; // Removed By PO 2-27-2014 {label: "FxUF", value: ValveConnection.FxUF}, {label: "UFxUF", value: ValveConnection.UFxUF},
			globeInfo.selectableFlowChar	= [{label: "All", value: null}, {label: "Equal %", value: FlowCharacteristics.EQUAL_PERCENT}, {label: "Linear", value: FlowCharacteristics.LINEAR}];
			globeInfo.selectableMedium		= [{label: "Water", value: ValveMedium.WATER}, {label: "Glycol", value: ValveMedium.GLYCOL}, {label: "Steam", value: ValveMedium.STEAM}];
			globeInfo.selectablePressureClass	= [{label: "All", value: null}, {label: "ANSI 125", value: 125}, {label: "ANSI 250", value: 250}];
			globeInfo.selectablePortConfig	= 
												[
													{label: "All", 						value: null},
													{label: "2 Way", 					value: 1},
												 	{label: "2 Way Normally Open", 		value: 2},
												 	{label: "2 Way Normally Closed", 	value: 3},
												 	{label: "3 Way", 					value: 4},
												 	{label: "3 Way Mixing", 			value: 5},
												 	{label: "3 Way Diverting", 			value: 6}
				 								];
			
			globeInfo.actuatorTypes				= [{label: "All", value: -1}, {label: "Electric", value: ActuatorMotorType.ELECTRIC}, {label: "Pneumatic", value: ActuatorMotorType.PNEUMATIC}];
			globeInfo.supplyVoltage				= [{label: "All", value: -1}, {label: "24V", value: 24}];;
			globeInfo.electricControlSignal		= [{label: "All", value: -1}, {label: "On/Off", value: ActuatorControlSignal.ON_OFF}, {label: "Floating", value: ActuatorControlSignal.s3_POS}, {label: "0-10V", value: ActuatorControlSignal.s0_10_VDC}, {label: "4-20mA", value: ActuatorControlSignal.s4_20mA}];
			globeInfo.pneumaticControlSignal	= [{label: "All", value: -1}, {label: "2-6psi", value: ActuatorControlSignal.s2_6_PSI}, {label: "2-12psi", value: ActuatorControlSignal.s2_12_PSI}, {label: "3-8psi", value: ActuatorControlSignal.s3_8_PSI}, {label: "3-13psi", value: ActuatorControlSignal.s3_13_PSI}, {label: "5-10psi", value: ActuatorControlSignal.s5_10_PSI}, {label: "8-13psi", value: ActuatorControlSignal.s8_13_PSI}, {label: "10-14psi", value: ActuatorControlSignal.s10_14_PSI}, {label: "10-15psi", value: ActuatorControlSignal.s10_15_PSI}, {label: "10-20psi", value: ActuatorControlSignal.s10_20_PSI}];
			globeInfo.selectablePositioner		= [{label: "All", value: -1}, {label: "None", value: 0}, {label: "Standard", value: ActuatorPositioner.STANDARD}];
			valveTypeInfo[globeInfo.valveType]	= globeInfo;
			
			
			// Magnetic Valve Type
			var magneticInfo:ValveTypeInformation	= new ValveTypeInformation();
			magneticInfo.valveType					= ValveType.MAGNETIC;
			magneticInfo.selectableCV				= [0.7, 1.8, 3.5, 5.9, 9.4, 14, 23.3, 35, 58.5, 93, 152];
			magneticInfo.selectableSizes			= [{label: "All", value: null}, {label: "1/2", value: .5}, {label: "3/4", value: .75}, {label: "1", value: 1}, {label: "1-1/4", value: 1.25}, {label: "1-1/2", value: 1.5}, {label: "2", value: 2}, {label: "2-1/2", value: 2.5}, {label: "3", value: 3}, {label: "4", value: 4}];
			magneticInfo.selectableTrim				= [{label: "All", value: null}, {label: "Brass/Bronze", value: ValveTrim.BRASS__BRONZE}, {label: "Stainless Steel", value: ValveTrim.STAINLESS_STELL}];
			magneticInfo.selectableConnections		= [{label: "All", value: null}, {label: "UFxUF", value: ValveConnection.UFxUF}, {label: "Flanged", value: ValveConnection.FLxFL}, {label: "NPT Flange", value: ValveConnection.NPT_FLANGE}, {label: "Weld Flange", value: ValveConnection.WELD_FLANGE}];
			magneticInfo.selectableMedium			= [{label: "Water", value: ValveMedium.WATER}, {label: "Glycol", value: ValveMedium.GLYCOL}, {label: "Steam", value: ValveMedium.STEAM}];
			magneticInfo.selectablePressureClass	= [{label: "All", value: null}, {label: "ANSI 125", value : 125}];
			magneticInfo.selectablePortConfig		= 
												[
													{label: "All", 					value: null},
													{label: "2 Way", 				value: 1},
				 									{label: "3 Way Mixing", 		value: 5},
				 									{label: "2 & 3 Way",			value: 7}
				 								];
			
			magneticInfo.actuatorTypes				= [{label: "All", value: -1}, {label: "Electric", value: ActuatorMotorType.ELECTRIC}];
			magneticInfo.supplyVoltage				= [{label: "All", value: -1}, {label: "24V", value: 24}];
			magneticInfo.electricControlSignal		= [{label: "All", value: -1}, {label: "0-10V", value: ActuatorControlSignal.s0_10_VDC}, {label: "4-20mA", value: ActuatorControlSignal.s4_20mA}];
			magneticInfo.selectableFlowChar			= [{label: "All", value: null}, {label: "Linear & Equal %", value: FlowCharacteristics.LINEAR_EQUAL_PERCENT}];
			magneticInfo.disablePositioner			= true;
			valveTypeInfo[magneticInfo.valveType]	= magneticInfo;
			
			
			// Butterfly Valve Type
			var butterflyInfo:ValveTypeInformation	= new ValveTypeInformation();
			butterflyInfo.valveType					= ValveType.BUTTERFLY;
			butterflyInfo.selectableCV				= [58, 60, 135, 151, 165, 229, 262, 419, 511, 647, 740, 870, 1051, 1141, 1160, 1242, 1580, 1661, 1754, 2254, 2439, 2524, 2892, 3401, 3470, 3570, 4593, 5240, 6682];
			butterflyInfo.selectableSizes			= [{label: "All", value: null}, {label: "2", value: 2}, {label: "2-1/2", value: 2.5}, {label: "3", value: 3}, {label: "4", value: 4}, {label: "5", value: 5}, {label: "6", value: 6}, {label: "8", value: 8}, {label: "10", value: 10}, {label: "12", value: 12}, {label: "14", value: 14}, {label: "16", value: 16}, {label: "18", value: 18} /*, {label: "20", value: 20} */];
			butterflyInfo.selectableTrim			= [];//{label: "All", value: null}];
			butterflyInfo.selectableMedium			= [{label: "Water", value: ValveMedium.WATER}, {label: "Glycol", value: ValveMedium.GLYCOL}, {label: "Steam", value: ValveMedium.STEAM}];
			butterflyInfo.selectablePressureClass	= [{label: "All", value: null}, {label: "ANSI 150", value: 150}];
			butterflyInfo.selectablePortConfig		= 
												[
													{label: "All", 						value: null},
													{label: "2 Way", 					value: 1},
				 									{label: "2 Way Normally Open", 		value: 2},
				 									{label: "2 Way Normally Closed", 	value: 3},
				 									{label: "3 Way", 					value: 4}
				 									/* {label: "3 Way Mixing", 			value: 5},
				 									{label: "3 Way Diverting", 			value: 6} */
				 								];
				 							
			// Wafer not in data set
			butterflyInfo.selectableConnections	= [{label: "All", value: null}, {label: "Lug", value: ValveConnection.LUG}, {label: "Wafer", value: ValveConnection.WAFER}];
			
			 
			butterflyInfo.actuatorTypes			= [{label: "All", value: -1}, {label: "Electric", value: ActuatorMotorType.ELECTRIC}, {label: "Pneumatic", value: ActuatorMotorType.PNEUMATIC}];
			butterflyInfo.supplyVoltage			= [{label: "All", value: -1}, {label: "24V", value: 24}, {label: "120V", value: 120}, {label: "100-240 VAC/DC", value: 24240}];
			butterflyInfo.electricControlSignal	= [{label: "All", value: -1}, {label: "On/Off", value: ActuatorControlSignal.ON_OFF}, {label: "Floating", value: ActuatorControlSignal.s3_POS}, {label: "0-10V", value: ActuatorControlSignal.s0_10_VDC}, {label: "4-20mA", value: ActuatorControlSignal.s4_20mA}];
			butterflyInfo.pneumaticControlSignal	= [{label: "All", value: -1}, {label: "20 PSI STD", value: ActuatorControlSignal.s20PSISTD}, {label: "60 PSI High Press", value: ActuatorControlSignal.s60PSIHighPress}, {label: "60 PSI Double Acting", value: ActuatorControlSignal.DOUBLE_ACTING_60PSI}];
			butterflyInfo.endSwitchSelection		= [{label: "All", value: -1}, {label: "None", value: ActuatorEndSwitch.NONE}, {label: "Pneumatic", 	value: ActuatorEndSwitch.PNEUMATIC}, {label: "Electric", value: ActuatorEndSwitch.ELECTRIC}, {label: "Potentiometer", value: ActuatorEndSwitch.POTENTIOMETER}];
			butterflyInfo.selectableFlowChar	= [];
			butterflyInfo.selectablePositioner	= [{label: "All", value: -1}, {label: "Pneumatic", value: ActuatorPositioner.PNEUMATIC}, {label: "E/P Valve 24V", value: ActuatorPositioner.EP_VALVE_24V}, {label: "E/P Valve 120V", value: ActuatorPositioner.EP_VALVE_120V}];
			valveTypeInfo[butterflyInfo.valveType]	= butterflyInfo;
			
			
			// PICV Valve Type
			var picvInfo:ValveTypeInformation	= new ValveTypeInformation();
			picvInfo.valveType					= ValveType.PICV;
			picvInfo.disableCVForm				= true;
			picvInfo.selectableCV				= null;
			picvInfo.selectableSizes			= [{label: "All", value: null}, {label: "1/2", value: .5}, {label: "3/4", value: .75}, {label: "1", value: 1}, {label: "1-1/4", value: 1.25}, {label: "1-1/2", value: 1.5}, {label: "2", value: 2}, {label: "2-1/2", value: 2.5}, {label: "3", value: 3}, {label: "4", value: 4}, {label: "5", value: 5}, {label: "6", value: 6}];
			picvInfo.selectableTrim				= [{label: "All", value: null}, {label: "Brass", value: ValveTrim.BRASS}];
			picvInfo.selectableConnections		= [{label: "All", value: null}, {label: "FxF", value: ValveConnection.FxF}, {label: "Flanged", value: ValveConnection.FLxFL}];
			picvInfo.selectableMedium			= [{label: "Water", value: ValveMedium.WATER}, {label: "Glycol", value: ValveMedium.GLYCOL}];
			picvInfo.selectablePressureClass	= [{label: "All", value: null}, {label: "ANSI 250", value: 250}, {label: "ANSI 125", value: 125}];
			picvInfo.selectablePortConfig		= 
												[
													{label: "All", 						value: null},
													{label: "2 Way", 					value: 1},
													{label: "2 Way Normally Open", 		value: 2},
													{label: "2 Way Normally Closed", 	value: 3}
												];
			picvInfo.selectableFlowChar			= [{label: "Linear", value: FlowCharacteristics.LINEAR}];
			picvInfo.actuatorTypes				= [{label: "Electric", value: ActuatorMotorType.ELECTRIC}];
			picvInfo.supplyVoltage				= [{label: "24V", value: 24}];
			picvInfo.electricControlSignal		= [{label: "All", value: -1}, {label: "Floating", value: ActuatorControlSignal.s3_POS}, {label: "0-10V", value: ActuatorControlSignal.s0_10_VDC}, {label: "4-20mA", value: ActuatorControlSignal.s4_20mA}];
			picvInfo.disablePositioner			= true;
			valveTypeInfo[picvInfo.valveType]	= picvInfo;
		}
		
		
		/**
		 * Returns the valve information for the given valve type
		 **/
		public static function getValveEnumInfo(type:uint):ValveTypeInformation
		{
			if (valveTypeInfo.hasOwnProperty(type))
				return valveTypeInfo[type] as ValveTypeInformation;
				
			return null;
		}
	}
}