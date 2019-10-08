package Swizzard.Data.AsynchronousQuery.Commands.Valves
{
	import flash.data.SQLResult;
	
	import Swizzard.Data.AsynchronousQuery.Token.ValveQueryToken;
	import Swizzard.Data.Models.SiemensValve;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorControlSignal;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorPositioner;
	import Swizzard.Data.Models.Enumeration.Valves.FlowCharacteristics;
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
	
	
	public class ButterflyValveQueryCommand extends BaseValveQueryCommand
	{
		private static const ACTUATOR_REQUERY_FIELDS:Array	= ["signal", "positioner"];
		
		
		public function ButterflyValveQueryCommand()
		{
			super();
			valveType = ValveType.BUTTERFLY;
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
			var shouldSkip:Boolean = false;
			var actuator:ActuatorQueryModel = t.valveQueryModel.actuator;

			if (actuator.signal == ActuatorControlSignal.s2_10_VDC)
				shouldSkip	= true;
			
			if (actuator.positioner == ActuatorPositioner.STANDARD)
				shouldSkip	= true;
			
			if (ActuatorControlSignal.IsPneumaticSignal(actuator.signal))
			{
				switch (actuator.signal)
				{
					case ActuatorControlSignal.s20PSISTD:
					case ActuatorControlSignal.s60PSIHighPress:
					case ActuatorControlSignal.DOUBLE_ACTING_60PSI:
						break;
						
					default:
						shouldSkip	= true;
				}
			}
			
			if (shouldSkip)
			{
				skip();
				return;
			}
			
			super.execute(notification);
			
			adjustCvField(lastValveResults);
		}
		
		
		/**
		 * @inherit 
		 * @return 
		 */
		override protected function generateValveQuery():SQLDataQuery
		{
			var query:SQLDataQuery			= super.generateValveQuery();
			var queryModel:ValveSystemQueryModel = token.valveQueryModel;
			var form:ValveQueryModel		= queryModel.valve;
			var actuator:ActuatorQueryModel	= queryModel.actuator;

			if (form.CV > 0)
			{
				var cvField:String	= (actuator.signal == ActuatorControlSignal.ON_OFF) ? "cvu" : "cvl";
				
				var tolerance:Number	= form.cvTolerance;
				var cvGroup:QueryGroup	= new QueryGroup();
				cvGroup.predicate		= QueryPredicates.AND;
				
				cvGroup.addItem(SQLQueryItem.Build(cvField, QueryOperations.GREATER_THAN + QueryOperations.EQUAL_TO, form.CV - tolerance, "t"));
				cvGroup.addItem(SQLQueryItem.Build(cvField, QueryOperations.LESS_THAN + QueryOperations.EQUAL_TO, form.CV + tolerance, "t"));
				
				query.addItem(cvGroup);
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
				query.addItem(SQLQueryItem.Build("connection", QueryOperations.EQUAL_TO, form.connection, "t"));
			
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
			
			if (form.discType > -1)
				query.addItem(SQLQueryItem.Build("discType", QueryOperations.EQUAL_TO, form.discType, "t"));
			
			LogUtils.ValveSQL("Butterfly SQL: " + query.toSql());
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
			{
				if ((form.signal == ActuatorControlSignal.ON_OFF) || (form.signal == ActuatorControlSignal.s3_POS))
				{
					var signalGroup:QueryGroup	= new QueryGroup();
					signalGroup.predicate		= QueryPredicates.AND;
					signalGroup.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, form.signal, "t", QueryPredicates.OR));
					signalGroup.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, ActuatorControlSignal.ON_OFF__FLOATING, "t", QueryPredicates.OR));
					query.addItem(signalGroup);	
				}
				else
				{
					query.addItem(SQLQueryItem.Build("controlSignal", QueryOperations.EQUAL_TO, form.signal, "t"));
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
			{
				if (form.supplyVoltage != 24240 && (form.supplyVoltage > 24))
				{
					// Must add 24-240 vac to every result
					var voltageGroup:QueryGroup	= new QueryGroup();
					voltageGroup.predicate		= QueryPredicates.AND;
					voltageGroup.addItem(SQLQueryItem.Build("supplyVoltage", QueryOperations.EQUAL_TO, form.supplyVoltage, "t", QueryPredicates.OR));
					voltageGroup.addItem(SQLQueryItem.Build("supplyVoltage", QueryOperations.EQUAL_TO, 24240, "t", QueryPredicates.OR));
					query.addItem(voltageGroup);					
				}
				else
				{
					query.addItem(SQLQueryItem.Build("supplyVoltage", QueryOperations.EQUAL_TO, form.supplyVoltage, "t"));
				}
			}
			
			if (valve.normalState > 0)
				query.addItem(SQLQueryItem.Build("normalState", QueryOperations.EQUAL_TO, valve.normalState, "a"));
			
			if (form.endSwitch > -1)
				query.addItem(SQLQueryItem.Build("endSwitch", QueryOperations.EQUAL_TO, form.endSwitch, "t"));
			
			if (valve.butterflyConfig)
				query.addItem(SQLQueryItem.Build("butterflyConfig", QueryOperations.EQUAL_TO, valve.butterflyConfig, "t"));
			
			return query;
		}
		
		
		/**
		 * @private
		 * 
		 * This function is used to control whether the model is to use the CVU or CVL Field for CV
		 *  
		 * @param collection
		 */		
		private function adjustCvField(collection:Array):void
		{
			var actuator:ActuatorQueryModel	= token.valveQueryModel.actuator;
			
			for each (var valve:SiemensValve in collection)
			{
				valve.useCVLField = (actuator.signal != ActuatorControlSignal.ON_OFF);
			}
		}
		
		
		/**
		 * @inherit 
		 * @return 
		 */
		override protected function queryResultHandler(result:SQLResult):void
		{
			if (token.isValveQuery)
				adjustCvField(result.data);
			super.queryResultHandler(result);
		}
	}
}