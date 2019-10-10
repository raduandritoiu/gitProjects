package Swizzard.Data.AsynchronousQuery.Commands.Valves
{
	import Swizzard.Data.AsynchronousQuery.Token.ValveQueryToken;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorControlSignal;
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
	import j2inn.Data.Query.Enumeration.QueryOperations;
	import j2inn.Data.Query.Enumeration.QueryPredicates;
	
	import org.puremvc.as3.interfaces.INotification;
	
	import utils.LogUtils;
	
	public class ZoneValveQueryCommand extends BaseValveQueryCommand
	{
		private static const ACTUATOR_REQUERY_FIELDS:Array	= ["signal", "supplyVoltage", "endSwitch", "positioner", "hasHeater", "motor"];
		
		
		public function ZoneValveQueryCommand()
		{
			super();
			valveType	= ValveType.ZONE;
		}
		
		
		override protected function shouldRequeryValues():Boolean
		{
			return super.shouldRequeryValues() || WillRequeryValves(token);
		}
		
		
		public static function WillRequeryValves(token:ValveQueryToken):Boolean
		{
			if (token.valve)
				return false; // Do not requery when valve is selected (Requested by Byran)
			
			var queryModel:ValveSystemQueryModel = token.valveQueryModel;
			if (queryModel.actuator)
			{
				for (var prop:String in queryModel.actuator.changed)
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
			var shouldSkip:Boolean	= false;
			
			if (queryModel.actuator.signal == ActuatorControlSignal.s2_10_VDC)
				shouldSkip	= true;
			else if (queryModel.actuator.signal == ActuatorControlSignal.s4_20mA)
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
			var query:SQLDataQuery	= super.generateValveQuery();
			var form:ValveQueryModel	= token.valveQueryModel.valve;
												
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
				if ((form.medium == ValveMedium.STEAM) && (form.minSteamSupplyPressure > 0))
				{
					query.addItem(SQLQueryItem.Build("maxIPSteam", QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, form.minSteamSupplyPressure, "t"));
				}
			} 
			
			if (form.connection > 0)
			{
				var fieldValue:String;
				
				switch (form.connection)
				{
					case ValveConnection.SWEAT: // Carries Sweat Value
						fieldValue	= "SWT";
						break;
					
					case ValveConnection.FxF:
					case ValveConnection.NPT: 
						fieldValue	= "NPT";
						break;
				}
				
				if (fieldValue == null)
					fieldValue = form.connection.toString();								
				// Zone Valve Connections are in the Thread Field
				
				query.addItem(SQLQueryItem.Build("thread", QueryOperations.EQUAL_TO, fieldValue, "t"));
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
				// Show All States for Two Way Valves (Don't Filter)
				if (form.pattern != ValvePattern.TWO_WAY)
					query.addItem(SQLQueryItem.Build("normalState", QueryOperations.EQUAL_TO, form.normalState, "t"));
			}
			
			LogUtils.ValveSQL("Zone SQL: " + query.toSql());
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