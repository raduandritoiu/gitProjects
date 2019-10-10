package Swizzard.Data.AsynchronousQuery.Commands.Valves
{
	import Swizzard.Data.AsynchronousQuery.Token.ValveQueryToken;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorControlSignal;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorMotorType;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorPositioner;
	import Swizzard.Data.Models.Enumeration.Valves.FlowCharacteristics;
	import Swizzard.Data.Models.Enumeration.Valves.ValveConnection;
	import Swizzard.Data.Models.Enumeration.Valves.ValveMedium;
	import Swizzard.Data.Models.Enumeration.Valves.ValveNormalState;
	import Swizzard.Data.Models.Enumeration.Valves.ValvePattern;
	import Swizzard.Data.Models.Enumeration.Valves.ValveTrim;
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;
	import Swizzard.Data.Models.Query.ActuatorQueryModel;
	import Swizzard.Data.Models.Query.ValveQueryModel;
	import Swizzard.Data.Models.Query.ValveSystemQueryModel;
	
	import j2inn.Data.Query.QueryGroup;
	import j2inn.Data.Query.SQLDataQuery;
	import j2inn.Data.Query.SQLQueryItem;
	import j2inn.Data.Query.SubTable;
	import j2inn.Data.Query.Enumeration.QueryOperations;
	import j2inn.Data.Query.Enumeration.QueryPredicates;
	
	import org.puremvc.as3.interfaces.INotification;
	
	import utils.LogUtils;
	

	public class PICValveQueryCommand extends BaseValveQueryCommand
	{
		private static const ACTUATOR_REQUERY_FIELDS:Array	= ["signal", "supplyVoltage", "endSwitch", "positioner", "hasHeater",
																"motor"];
		
		public function PICValveQueryCommand()
		{
			super();
			valveType	= ValveType.PICV;
		}
		
		
		override protected function shouldRequeryValues():Boolean
		{
			return super.shouldRequeryValues() || WillRequeryValves(token);
		}
		
		
		public static function WillRequeryValves(token:ValveQueryToken):Boolean
		{
			if (token.valve)
				return false; // Do not requery when valve is selected (Requested by Byran)
			
			var actuator:ActuatorQueryModel = token.valveQueryModel.actuator;
			if (actuator)
			{
				for (var prop:String in actuator.changed)
				{
					if (ACTUATOR_REQUERY_FIELDS.indexOf(prop) > -1)
						return true;
				}
			}
			
			return false;	
		}
		
		
		override public function execute(notification:INotification):void
		{
			var t:ValveQueryToken = notification.getBody() as ValveQueryToken;
			var queryModel:ValveSystemQueryModel = t.valveQueryModel;
			var shouldSkip:Boolean = false;
			
			if (queryModel.actuator.signal == ActuatorControlSignal.s2_10_VDC)
				shouldSkip	= true;
			else if (queryModel.actuator.signal == ActuatorControlSignal.ON_OFF)
				shouldSkip	= true;
			
			if (queryModel.actuator.positioner == ActuatorPositioner.STANDARD)
				shouldSkip	= true;
			
			if (queryModel.actuator.supplyVoltage == 24240)
				shouldSkip	= true;
			
			if (queryModel.actuator.hasHeater == 1)
				shouldSkip	= true;
			
			if (queryModel.actuator.endSwitch > 0)
				shouldSkip	= true;
			
			if (ActuatorControlSignal.IsPneumaticSignal(queryModel.actuator.signal))
				shouldSkip	= true;
			
			if (shouldSkip || (queryModel.valve.discType > -1) || 
				(queryModel.actuator.positioner > 1) || (queryModel.actuator.supplyVoltage == 120) ||
				(queryModel.actuator.motor == ActuatorMotorType.PNEUMATIC))
			{
				skip();
				return;
			}
			
			super.execute(notification);
		}
		
		
		/**
		 * @inherit 
		 * @return 
		 */
		override protected function generateValveQuery():SQLDataQuery
		{
			var query:SQLDataQuery		= super.generateValveQuery();
			var form:ValveQueryModel	= token.valveQueryModel.valve;
			
			var excludeThreeWay:Boolean	= false;
			
			if (form.designFlow > 0)
			{
				var flowGroup:QueryGroup	= new QueryGroup();
				flowGroup.predicate			= QueryPredicates.AND;
				
				// Small PICV Valves
				var smallPICVFlow:QueryGroup	= new QueryGroup();
				smallPICVFlow.predicate			= QueryPredicates.AND;
				
				smallPICVFlow.addItem(SQLQueryItem.Build("size", QueryOperations.LESS_THAN + QueryOperations.EQUAL_TO, 2, "t"));
				
				var maxGPM:Number	= form.designFlow + form.requiredFlowTolerance;
				var minGPM:Number	= form.designFlow - form.requiredFlowTolerance;
																
				var presetFlowGroup:QueryGroup	= new QueryGroup();
				presetFlowGroup.predicate		= QueryPredicates.AND;
			
				var presetSubGroup:QueryGroup	= new QueryGroup();
				presetSubGroup.predicate		= QueryPredicates.AND;
				presetSubGroup.addItem(SQLQueryItem.Build("presetGPM", QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, minGPM, "t"));
				presetSubGroup.addItem(SQLQueryItem.Build("presetGPM", QueryOperations.LESS_THAN + QueryOperations.EQUAL_TO, maxGPM, "t"));
				presetFlowGroup.addItem(presetSubGroup);
				
				smallPICVFlow.addItem(presetFlowGroup);
				
				flowGroup.addItem(smallPICVFlow);
				
				// Large PICV Valves
				var largePICVFlow:QueryGroup	= new QueryGroup();
				largePICVFlow.predicate			= QueryPredicates.OR;
				largePICVFlow.addItem(SQLQueryItem.Build("size", QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, 2.5, "t"));
				
				var largeGPMRangeSubGroup:QueryGroup	= new QueryGroup();
				largeGPMRangeSubGroup.predicate			= QueryPredicates.AND;
				largeGPMRangeSubGroup.addItem(SQLQueryItem.Build("minGPM", QueryOperations.LESS_THAN + QueryOperations.EQUAL_TO, form.designFlow, "t"));
				largeGPMRangeSubGroup.addItem(SQLQueryItem.Build("maxGPM", QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, Math.max(110, minGPM), "t"));
				
				largePICVFlow.addItem(largeGPMRangeSubGroup);
				
				flowGroup.addItem(largePICVFlow);
				
				query.addItem(flowGroup);
			}
			
			if (form.medium > 0)
			{
				query.addItem(SQLQueryItem.Build("medium & " + form.medium, QueryOperations.GREATER_THAN, 0, "t"));
				if (form.medium == ValveMedium.STEAM)
				{ 
					if (form.minSteamSupplyPressure > 0)
					{
						query.addItem(SQLQueryItem.Build("maxIPSteam", QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, form.minSteamSupplyPressure, "t"));
					}
					excludeThreeWay	= true;
				}
			} 
			
			if (form.connection > 0)
			{
				if ((form.connection == ValveConnection.NPT) || (form.connection == ValveConnection.FxF))
				{
					var connectionGroup:QueryGroup	= new QueryGroup();
					connectionGroup.predicate		= QueryPredicates.AND;
					
					connectionGroup.addItem(SQLQueryItem.Build("connection", QueryOperations.EQUAL_TO, ValveConnection.NPT, "t", QueryPredicates.OR));
					connectionGroup.addItem(SQLQueryItem.Build("connection", QueryOperations.EQUAL_TO, ValveConnection.FxF, "t", QueryPredicates.OR));
					
					query.addItem(connectionGroup);
				}
				else
				{
					query.addItem(SQLQueryItem.Build("connection", QueryOperations.EQUAL_TO, form.connection, "t"));
				}
			}
			
			if (form.flowCharacteristic > 0)
			{
				if (form.flowCharacteristic == FlowCharacteristics.EQUAL_PERCENT)
				{
					var flowGroupChar:QueryGroup	= new QueryGroup();
					flowGroupChar.predicate			= QueryPredicates.AND;
					flowGroupChar.addItem(SQLQueryItem.Build("flowChar", QueryOperations.EQUAL_TO, FlowCharacteristics.MODIFIED_EQUAL_PERCENT, "t", QueryPredicates.OR));
					flowGroupChar.addItem(SQLQueryItem.Build("flowChar", QueryOperations.EQUAL_TO, form.flowCharacteristic, "t", QueryPredicates.OR));
					query.addItem(flowGroupChar);	
				}
				else
					query.addItem(SQLQueryItem.Build("flowChar", QueryOperations.EQUAL_TO, form.flowCharacteristic, "t"));
			}
			
			if (form.lineSize)
				query.addItem(SQLQueryItem.Build("size", QueryOperations.EQUAL_TO, form.lineSize, "t"));
			
			if (form.packingMaterial > 0)
				query.addItem(SQLQueryItem.Build("packingMaterial", QueryOperations.EQUAL_TO, form.packingMaterial, "t"));
			
			if (form.pattern > 0)
			{
				if (excludeThreeWay)
				{
					var patternGroup:QueryGroup	= new QueryGroup();
					patternGroup.predicate		= QueryPredicates.AND;
					patternGroup.addItem(SQLQueryItem.Build("pattern", QueryOperations.NOT, ValvePattern.THREE_WAY, "t"));
					patternGroup.addItem(SQLQueryItem.Build("pattern", QueryOperations.EQUAL_TO, form.pattern, "t"));
					
					query.addItem(patternGroup);
				}
				else
				{
					query.addItem(SQLQueryItem.Build("pattern", QueryOperations.EQUAL_TO, form.pattern, "t"));
				}
			}
			else if (excludeThreeWay)
			{
				query.addItem(SQLQueryItem.Build("pattern", QueryOperations.NOT, ValvePattern.THREE_WAY, "t"));
			}
			
			if (form.pressureClass > 0)
				query.addItem(SQLQueryItem.Build("maxPress", QueryOperations.EQUAL_TO, form.pressureClass, "t"));
			
			if (form.trim > 0)
			{
				if (form.trim == ValveTrim.BRASS__BRONZE)
				{
					var trimGroup:QueryGroup	= new QueryGroup();
					trimGroup.predicate			= QueryPredicates.AND;
					trimGroup.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.BRASS, "t", QueryPredicates.OR));
					trimGroup.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.BRONZE, "t", QueryPredicates.OR));
					
					//Added on 3-8-2011 for Bug #1360
					trimGroup.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.BRASS_NICKEL, "t", QueryPredicates.OR));
					trimGroup.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.CHROME_BRASS, "t", QueryPredicates.OR));
					
					query.addItem(trimGroup);
				}
				else
				{
					query.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, form.trim, "t"));
				}
			}
			
			if (form.maximumTemperature)
				query.addItem(SQLQueryItem.Build("maxTemp", QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, form.maximumTemperature, "t"));
			
			if (form.application > 0)
				query.addItem(SQLQueryItem.Build("mixingDiverting & " + form.application, QueryOperations.EQUAL_TO, form.application, "t"));
			
			if (form.normalState > 0)
			{
				if (form.pattern == ValvePattern.TWO_WAY && form.normalState == ValveNormalState.NORMALLY_CLOSED)
				{
					// Show All, Do not filter when its 2-Way NC (Determined by actuator)
					// Remove Small Two-Way valves. the actuator determination is only for large valves
					var normalStateGroup:QueryGroup			= new QueryGroup();
					normalStateGroup.predicate				= QueryPredicates.AND;
					
					var smallPICVNormalState:QueryGroup		= new QueryGroup();
					smallPICVNormalState.predicate			= QueryPredicates.AND;
					
					smallPICVNormalState.addItem(SQLQueryItem.Build("size", QueryOperations.LESS_THAN + QueryOperations.EQUAL_TO, 2, "t"));
					smallPICVNormalState.addItem(SQLQueryItem.Build("normalState", QueryOperations.EQUAL_TO, form.normalState, "t"));
										
					normalStateGroup.addItem(smallPICVNormalState);
					
					normalStateGroup.addItem(SQLQueryItem.Build("size", QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, 2.5, "t", QueryPredicates.OR));
					
					query.addItem(normalStateGroup);
				}
				else
				{
					query.addItem(SQLQueryItem.Build("normalState", QueryOperations.EQUAL_TO, form.normalState, "t"));
				}
			}
						
			LogUtils.ValveSQL("PICV SQL: " + query.toSql());
			return query;
		}
		
		
		/**
		 * @inherit 
		 * @return 
		 */
		override protected function generateActuatorQuery():SQLDataQuery
		{
			var query:SQLDataQuery		= super.generateActuatorQuery();
			var queryModel:ValveSystemQueryModel = token.valveQueryModel;
			var form:ActuatorQueryModel	= queryModel.actuator;
			var valve:ValveQueryModel	= queryModel.valve;
			
			var assemblyTable:SubTable	= query.getTable("Assemblies");
					
			if (assemblyTable)
			{
				if (assemblyTable.projection)
					assemblyTable.projection.push(["controlSignal as controlSignalAsm"]);
				else
					assemblyTable.projection	= ["controlSignal as controlSignalAsm"];
			}
			
			if (form.motor > -1)
				query.addItem(SQLQueryItem.Build("motorType", QueryOperations.EQUAL_TO, form.motor, "t"));
			
			if (form.signal > -1)
			{
				var queryTable:String	= "t";
													
				if (form.signal == ActuatorControlSignal.s0_10_VDC || form.signal == ActuatorControlSignal.s3_POS || form.signal == ActuatorControlSignal.s4_20mA)
				{
					var signalGroup:QueryGroup	= new QueryGroup();
					signalGroup.predicate		= QueryPredicates.AND;
					signalGroup.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, form.signal, queryTable, QueryPredicates.OR));
					signalGroup.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, ActuatorControlSignal.Floating__0_10V__4_20MA, queryTable, QueryPredicates.OR));
					if (form.signal == ActuatorControlSignal.s0_10_VDC || form.signal == ActuatorControlSignal.s4_20mA)
					{
						signalGroup.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, ActuatorControlSignal.s0_10VDC__4_20MA, queryTable, QueryPredicates.OR));
					}
					query.addItem(signalGroup);	
				}
				else
				{
					query.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, form.signal, queryTable));
				}
			}
			
			if (form.safetyFunction > -1)
				query.addItem(SQLQueryItem.Build("safetyFunction", QueryOperations.EQUAL_TO, form.safetyFunction, "t"));
			
			if (form.hasManualOverride > -1)
				query.addItem(SQLQueryItem.Build("hasManualOverride", QueryOperations.EQUAL_TO, form.hasManualOverride, "t"));
			
			if (form.supplyVoltage > 0)
				query.addItem(SQLQueryItem.Build("supplyVoltage", QueryOperations.EQUAL_TO, form.supplyVoltage, "t"));			
			
			if (valve.application > 0)
				query.addItem(SQLQueryItem.Build("threeWayFunction & " + valve.application, QueryOperations.EQUAL_TO, valve.application, "a"));
			
			if (valve.pattern == ValvePattern.TWO_WAY)
			{
				if (valve.normalState > 0)
					query.addItem(SQLQueryItem.Build("normalState", QueryOperations.EQUAL_TO, valve.normalState, "a"));
			}
			
			return query;
		}
	}
}