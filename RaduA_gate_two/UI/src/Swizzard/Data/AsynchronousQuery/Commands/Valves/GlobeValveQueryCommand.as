package Swizzard.Data.AsynchronousQuery.Commands.Valves
{
	import Swizzard.Data.AsynchronousQuery.Token.ValveQueryToken;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorControlSignal;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorMotorType;
	import Swizzard.Data.Models.Enumeration.Valves.FlowCharacteristics;
	import Swizzard.Data.Models.Enumeration.Valves.ValveConnection;
	import Swizzard.Data.Models.Enumeration.Valves.ValveMedium;
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
	import j2inn.Data.QueryExtend.Enumeration.QueryOperationsExtended;
	
	import org.puremvc.as3.interfaces.INotification;
	
	import utils.LogUtils;
	

	public class GlobeValveQueryCommand extends BaseValveQueryCommand
	{
		private static const ACTUATOR_REQUERY_FIELDS:Array	= ["signal", "supplyVoltage", "endSwitch", "positioner", "hasHeater"];
		
		
		public function GlobeValveQueryCommand()
		{
			super();
			valveType	= ValveType.GLOBE;
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
			
			return false; // (token.model.actuator && token.model.actuator.changed.hasOwnProperty("signal"));	
		}
						
		
		override public function execute(notification:INotification):void
		{
			var t:ValveQueryToken = notification.getBody() as ValveQueryToken;
			var queryModel:ValveSystemQueryModel = t.valveQueryModel;
			var shouldSkip:Boolean = false;
			
			if (queryModel.actuator.signal == ActuatorControlSignal.s2_10_VDC)
				shouldSkip	= true;
			
			if (queryModel.actuator.supplyVoltage == 24240)
				shouldSkip	= true;
			
			if (queryModel.actuator.hasHeater == 1)
				shouldSkip	= true;
			
			if (queryModel.actuator.endSwitch > 0)
				shouldSkip	= true;
			
			if (ActuatorControlSignal.IsPneumaticSignal(queryModel.actuator.signal))
			{
				switch (queryModel.actuator.signal)
				{
					case ActuatorControlSignal.s20PSISTD:
					case ActuatorControlSignal.s60PSIHighPress:
					case ActuatorControlSignal.DOUBLE_ACTING_60PSI:
						shouldSkip	= true;
						break;
				}
			}
			
			if (shouldSkip || (queryModel.valve.butterflyConfig) || (queryModel.valve.discType > -1) || 
				(queryModel.actuator.positioner > 1) || (queryModel.actuator.supplyVoltage == 120))
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
			var queryModel:ValveSystemQueryModel = token.valveQueryModel;
			var form:ValveQueryModel = queryModel.valve;
			var actuatorForm:ActuatorQueryModel	= queryModel.actuator;
			
			var excludeThreeWay:Boolean	= false;
			
			if (actuatorForm)
			{
				if (actuatorForm.signal > 0)
				{
					query.distinctColumns	= ["t.partNumber"];
										
					var assemblyTable:SubTable	= new SubTable();
					assemblyTable.tableName		= "Assemblies";
					assemblyTable.tableColumn	= "valvePartNumber";
					assemblyTable.tableAlias	= "a";
					assemblyTable.foreignColumn	= "partNumber"; 
					assemblyTable.projection	= ["controlSignal as controlSignal"];
					query.addTable(assemblyTable);
					
					if ((actuatorForm.signal == ActuatorControlSignal.s0_10_VDC) || (actuatorForm.signal == ActuatorControlSignal.s4_20mA))
					{
						var signalGroup:QueryGroup	= new QueryGroup();
						signalGroup.predicate		= QueryPredicates.AND;
						signalGroup.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, actuatorForm.signal, "a", QueryPredicates.OR));
						signalGroup.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, ActuatorControlSignal.s0_10VDC__4_20MA, "a", QueryPredicates.OR));
						query.addItem(signalGroup);	
					}
					else
					{
						query.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, actuatorForm.signal, "a"));
					}
				}
			}
			
			if (form.CV > 0)
			{
				var tolerance:Number	= form.cvTolerance;
				var cvGroup:QueryGroup	= new QueryGroup();
				cvGroup.predicate		= QueryPredicates.AND;
				
				cvGroup.addItem(SQLQueryItem.Build("cvu", QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, form.CV - tolerance, "t"));
				cvGroup.addItem(SQLQueryItem.Build("cvu", QueryOperations.LESS_THAN + QueryOperations.EQUAL_TO, form.CV + tolerance, "t"));
				
				query.addItem(cvGroup);
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
					var flowGroup:QueryGroup	= new QueryGroup();
					flowGroup.predicate			= QueryPredicates.AND;
					flowGroup.addItem(SQLQueryItem.Build("flowChar", QueryOperations.EQUAL_TO, FlowCharacteristics.MODIFIED_EQUAL_PERCENT, "t", QueryPredicates.OR));
					flowGroup.addItem(SQLQueryItem.Build("flowChar", QueryOperations.EQUAL_TO, form.flowCharacteristic, "t", QueryPredicates.OR));
					query.addItem(flowGroup);	
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
				query.addItem(SQLQueryItem.Build("normalState", QueryOperations.EQUAL_TO, form.normalState, "t"));
						
			LogUtils.ValveSQL("Globe SQL: " + query.toSql());
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
				
				if (form.motor == ActuatorMotorType.PNEUMATIC || ActuatorControlSignal.IsPneumaticSignal(form.signal))
					queryTable	= "a";
					
				if ((form.signal == ActuatorControlSignal.s0_10_VDC) || (form.signal == ActuatorControlSignal.s4_20mA))
				{
					var signalGroup:QueryGroup	= new QueryGroup();
					signalGroup.predicate		= QueryPredicates.AND;
					signalGroup.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, form.signal, queryTable, QueryPredicates.OR));
					signalGroup.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, ActuatorControlSignal.s0_10VDC__4_20MA, queryTable, QueryPredicates.OR));
					query.addItem(signalGroup);	
				}
				else
				{
					query.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, form.signal, queryTable));
				}
			}
			
			if (form.positioner > -1)
				query.addItem(SQLQueryItem.Build("positioner", QueryOperations.EQUAL_TO, form.positioner, "t"));
			
			if (form.safetyFunction > -1)
				query.addItem(SQLQueryItem.Build("safetyFunction", QueryOperations.EQUAL_TO, form.safetyFunction, "t"));
			
			if (form.hasHeater > -1)
				query.addItem(SQLQueryItem.Build("hasHeater", QueryOperations.EQUAL_TO, form.hasHeater, "t"));
			
			if (form.hasManualOverride > -1)
				query.addItem(SQLQueryItem.Build("hasManualOverride", QueryOperations.EQUAL_TO, form.hasManualOverride, "t"));
			
			if (form.supplyVoltage > 0)
				query.addItem(SQLQueryItem.Build("supplyVoltage", QueryOperations.EQUAL_TO, form.supplyVoltage, "t"));			
			
			if (form.endSwitch > -1)
				query.addItem(SQLQueryItem.Build("endSwitch", QueryOperations.EQUAL_TO, form.endSwitch, "t"));
			
			if (valve.application > 0)
				query.addItem(SQLQueryItem.Build("threeWayFunction & " + valve.application, QueryOperations.EQUAL_TO, valve.application, "a"));
			
			if (valve.minSteamSupplyPressure >= 5)
				query.addItem(SQLQueryItem.Build("partNumber", QueryOperationsExtended.NOT_LIKE, "SSC%", "t"));
			
			return query;
		}
	}
}