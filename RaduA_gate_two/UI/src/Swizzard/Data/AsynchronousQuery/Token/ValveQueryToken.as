package Swizzard.Data.AsynchronousQuery.Token
{
	import Swizzard.Data.AsynchronousQuery.Enumeration.ProductQueryType;
	import Swizzard.Data.Models.SiemensValve;
	import Swizzard.Data.Models.Query.ValveSystemQueryModel;
	
	/**
	 * Query Token
	 *  
	 * @author michael
	 * 
	 */	
	public class ValveQueryToken extends BaseQueryToken
	{
		// the results and resultsChanged  will refer to Valve Results
		
		private var _valve:SiemensValve;				// Valve used for actuator query
		
		private var _isValveQuery:Boolean;				// Makes difference between valve or actuator query
		private var _actuatorQueryPending:Boolean;		// True if actuator query needs to be done after the valve query.

		private var _actuatorResults:Array;				// Actuator Results
		private var _actuatorResultsChanged:Boolean;
		private var _requeryAllValves:Boolean;
		
		
		public function ValveQueryToken() {
			_numQueries = 0;
		}
		
		
		public function get valveQueryModel():ValveSystemQueryModel {
			return _queryModel as ValveSystemQueryModel;
		}
		
		
		public function set requeryAllValves(value:Boolean):void {
			_requeryAllValves = value;
		}
		
		public function get requeryAllValves():Boolean {
			if (valveQueryModel.valve.changed.hasOwnProperty("valveTypes"))
				return true;
			return _requeryAllValves;
		}
		
		
		public function set actuatorQueryPending(value:Boolean):void {
			_actuatorQueryPending = value;
		}
		
		public function get actuatorQueryPending():Boolean {
			return _actuatorQueryPending;
		}
		
		
		public function set actuatorResults(value:Array):void {
			_actuatorResults 		= value;
			_actuatorResultsChanged	= true;
		}
		
		public function get actuatorResults():Array {
			return _actuatorResults;
		}
		
		public function get actuatorResultsChanged():Boolean {
			return _actuatorResultsChanged;
		}
		
		
		public function get hasValvesAndActuators():Boolean {
			return (resultsChanged && actuatorResultsChanged);
		}
		
		
		/**
		 * Selected Valve 
		 * 
		 * used for actuator query
		 * 
		 * @param value
		 * 
		 */		
		public function set valve(value:SiemensValve):void {
			_valve = value;
		}
		
		public function get valve():SiemensValve {
			return _valve;
		}
		
		
		/**
		 * Is Valve Query Flag
		 *  
		 * @param value
		 * 
		 */		
		public function set isValveQuery(value:Boolean):void {
			if (!_valve)
				value	= true;
			_isValveQuery	= value;
			_target		= (value) ? ProductQueryType.VALVES : ProductQueryType.ACTUATORS;
		}
		
		public function get isValveQuery():Boolean {
			return _isValveQuery;
		}
	}
}