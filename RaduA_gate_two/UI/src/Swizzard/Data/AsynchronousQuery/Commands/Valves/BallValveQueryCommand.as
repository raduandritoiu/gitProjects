package Swizzard.Data.AsynchronousQuery.Commands.Valves
{
	import Swizzard.Data.AsynchronousQuery.Token.ValveQueryToken;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorControlSignal;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorEndSwitch;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorMotorType;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorPositioner;
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
	
	import org.puremvc.as3.interfaces.INotification;
	
	import utils.LogUtils;
	

	public class BallValveQueryCommand extends BaseValveQueryCommand
	{		
		private static const ACTUATOR_REQUERY_FIELDS:Array	= ["signal", "supplyVoltage", "endSwitch", "positioner", "hasHeater"];
		
		
		public function BallValveQueryCommand()
		{
			super();
			valveType	= ValveType.BALL;
		}
		
		
		override protected function shouldRequeryValues():Boolean
		{
			return WillRequeryValves(token) || super.shouldRequeryValues();
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
			var shouldSkip:Boolean = false;
			var queryModel:ValveSystemQueryModel = t.valveQueryModel;
		
			if (queryModel.actuator.signal == ActuatorControlSignal.s4_20mA)
				shouldSkip	= true;
			
			if (queryModel.actuator.positioner == ActuatorPositioner.STANDARD)
				shouldSkip	= true;
			
			if (queryModel.actuator.supplyVoltage == 24240)
				shouldSkip	= true;
			
			if (queryModel.actuator.hasHeater == 1)
				shouldSkip	= true;
			
			if (queryModel.actuator.endSwitch > ActuatorEndSwitch.ELECTRIC)
				shouldSkip	= true;
			
			if (ActuatorControlSignal.IsPneumaticSignal(queryModel.actuator.signal))
				shouldSkip	= true;
			
			if (shouldSkip || (queryModel.valve.discType > -1) || 
				(queryModel.actuator.positioner > 1) || (queryModel.actuator.motor == ActuatorMotorType.PNEUMATIC))
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
			var query:SQLDataQuery				= super.generateValveQuery();
			var queryModel:ValveSystemQueryModel = token.valveQueryModel;
			var form:ValveQueryModel			= queryModel.valve;			
			var actuatorForm:ActuatorQueryModel	= queryModel.actuator;
			
			// Actuator Parameters that affect valves
			if (actuatorForm)
			{
				if (actuatorForm.supplyVoltage == 120 && actuatorForm.endSwitch < 0)
				{
					query.distinctColumns	= ["t.partNumber"];
					
					// Only Show valves that are 120V
					var assemblyTable:SubTable	= new SubTable();
					assemblyTable.tableName		= "Assemblies";
					assemblyTable.tableColumn	= "valvePartNumber";
					assemblyTable.tableAlias	= "a";
					assemblyTable.foreignColumn	= "partNumber"; 
					assemblyTable.projection	= null; //["closeOff as closeOff"];
					query.addTable(assemblyTable);
					
					var actuatorsTable:SubTable	= new SubTable();
					actuatorsTable.tableName			= "Actuators";
					actuatorsTable.tableColumn			= "partNumber";
					actuatorsTable.tableAlias			= "b";
					actuatorsTable.foreignTableAlias	= "a";
					actuatorsTable.foreignColumn		= "actuatorPartNumber";
					actuatorsTable.projection			= ["supplyVoltage as supplyVoltage"];
					query.addTable(actuatorsTable);
					
					query.addItem(SQLQueryItem.Build("supplyVoltage", QueryOperations.EQUAL_TO, actuatorForm.supplyVoltage, "b"));
				}
				
				if (actuatorForm.endSwitch > -1)
				{
					query.distinctColumns	= ["t.partNumber"];
					
					// Only Show valves that are 120V
					var assemblyTable:SubTable	= new SubTable();
					assemblyTable.tableName		= "Assemblies";
					assemblyTable.tableColumn	= "valvePartNumber";
					assemblyTable.tableAlias	= "a";
					assemblyTable.foreignColumn	= "partNumber"; 
					assemblyTable.projection	= null; //["closeOff as closeOff"];
					query.addTable(assemblyTable);
					
					var actuatorsTable:SubTable	= new SubTable();
					actuatorsTable.tableName			= "Actuators";
					actuatorsTable.tableColumn			= "partNumber";
					actuatorsTable.tableAlias			= "b";
					actuatorsTable.foreignTableAlias	= "a";
					actuatorsTable.foreignColumn		= "actuatorPartNumber";
					actuatorsTable.projection			= ["endSwitch as endSwitch"];
					query.addTable(actuatorsTable);
					
					query.addItem(SQLQueryItem.Build("endSwitch", QueryOperations.EQUAL_TO, actuatorForm.endSwitch, "b"));
					
					if (actuatorForm.supplyVoltage == 120)
						query.addItem(SQLQueryItem.Build("supplyVoltage", QueryOperations.EQUAL_TO, actuatorForm.supplyVoltage, "b"));
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
			
			if (form.CVB > 0)
			{
				var tolerance_B:Number		= form.cvTolerance_B;
				var cvGroup_B:QueryGroup	= new QueryGroup();
				cvGroup_B.predicate			= QueryPredicates.AND;
				cvGroup_B.addItem(SQLQueryItem.Build("cvl", QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, form.CVB - tolerance_B, "t"));
				cvGroup_B.addItem(SQLQueryItem.Build("cvl", QueryOperations.LESS_THAN + QueryOperations.EQUAL_TO, form.CVB + tolerance_B, "t"));
				query.addItem(cvGroup_B);
			}
						 			
			if (form.medium > 0)
			{
				query.addItem(SQLQueryItem.Build("medium & " + form.medium, QueryOperations.GREATER_THAN, 0, "t"));
				
				if ((form.medium == ValveMedium.STEAM) && 
					(form.minSteamSupplyPressure > 0))
				{
					query.addItem(SQLQueryItem.Build("maxIPSteam", QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, form.minSteamSupplyPressure, "t"));
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
				query.addItem(SQLQueryItem.Build("pattern", QueryOperations.EQUAL_TO, form.pattern, "t"));
			
			if (form.pressureClass > 0)
				query.addItem(SQLQueryItem.Build("maxPress", QueryOperations.EQUAL_TO, form.pressureClass, "t"));
			
			if (form.trim > 0)
			{
				if (form.trim == ValveTrim.PLATED_BRASS)
				{
					var trimGroup:QueryGroup	= new QueryGroup();
					trimGroup.predicate			= QueryPredicates.AND;
/* 					trimGroup.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.BRASS, "t", QueryPredicates.OR));
					trimGroup.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.BRONZE, "t", QueryPredicates.OR)); */
					trimGroup.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.BRASS_NICKEL, "t", QueryPredicates.OR));
					trimGroup.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.CHROME_BRASS, "t", QueryPredicates.OR));
					
					query.addItem(trimGroup);
				}
				else if (form.trim == ValveTrim.BRASS__BRONZE)
				{
					//Added on 3-8-2011 for Bug #1360
					var trimGroup2:QueryGroup	= new QueryGroup();
					trimGroup2.predicate		= QueryPredicates.AND;
										
					trimGroup2.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.BRASS, "t", QueryPredicates.OR));
					trimGroup2.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.BRONZE, "t", QueryPredicates.OR));
					trimGroup2.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.BRASS_NICKEL, "t", QueryPredicates.OR));
					trimGroup2.addItem(SQLQueryItem.Build("plugMaterial", QueryOperations.EQUAL_TO, ValveTrim.CHROME_BRASS, "t", QueryPredicates.OR));
					
					query.addItem(trimGroup2);
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
				// Show All States for Two Way Valves (Don't Filter)
				if (form.pattern != ValvePattern.TWO_WAY)
					query.addItem(SQLQueryItem.Build("normalState", QueryOperations.EQUAL_TO, form.normalState, "t"));
			}
			
			LogUtils.CvTesting("ball cv a: cv:"+form.CV+"\t cvTol:"+form.cvTolerance);
			LogUtils.CvTesting("ball cv a: cv:"+form.CVB+"\t cvTol:"+form.cvTolerance_B);
			LogUtils.ValveSQL("Ball SQL: " + query.toSql());
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
			
			if (form.motor > -1)
				query.addItem(SQLQueryItem.Build("motorType", QueryOperations.EQUAL_TO, form.motor, "t"));
			
			if (form.signal > -1)
				query.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, form.signal, "t"));
			
			if (form.positioner > -1)
				query.addItem(SQLQueryItem.Build("positioner", QueryOperations.EQUAL_TO, form.positioner, "t"));
			
			if (form.safetyFunction > -1)
				query.addItem(SQLQueryItem.Build("safetyFunction", QueryOperations.EQUAL_TO, form.safetyFunction, "t"));
			
			if (valve.normalState > 0)
				query.addItem(SQLQueryItem.Build("normalState", QueryOperations.EQUAL_TO, valve.normalState, "a"));
			
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
			
			return query;
		}
	}
}