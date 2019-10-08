package Swizzard.Data.Utils
{
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;
	import Swizzard.Data.Models.Query.ActuatorQueryModel;
	import Swizzard.Data.Models.Query.ValveSystemQueryModel;
	
	import flash.utils.Dictionary;
	
	import mx.events.PropertyChangeEvent;

	
	public class QueryUtility
	{
		private static const actuatorColumnsCausingValveQuery:Dictionary = initializeColumns();
		
		
		private static function initializeColumns():Dictionary
		{
			// These columns are columns of the ActuatorQueryModel that when changed should
			// cause the valves to be requeried.
			var d:Dictionary	= new Dictionary();
			
			// BALL
			var ballActuatorColumns:Array = new Array();
			ballActuatorColumns.push("supplyVoltage");
			d[ValveType.BALL] = ballActuatorColumns;
			return d;
		}
		
		
		public static function ChangeRequiresValveRefresh(event:PropertyChangeEvent):Boolean
		{		
			var systemModel:ValveSystemQueryModel	= event.target as ValveSystemQueryModel;
			if (event.source is ActuatorQueryModel)
			{
				return false; // Do not refresh valves when actuator is selected (Requested By Byran)
				
				if (systemModel && systemModel.valve)
				{
					for each (var valveType:uint in systemModel.valve.valveTypes)
					{
						if (actuatorColumnsCausingValveQuery.hasOwnProperty(valveType))
						{
							var invalidatingColumns:Array	= actuatorColumnsCausingValveQuery[valveType];
							
							for each (var prop:String in invalidatingColumns)
								if (prop == event.property)
									return true;
						}
					}
				}
			}
						
			return false;
		}
	}
}