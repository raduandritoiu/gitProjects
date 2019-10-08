package Swizzard.Data.Models.Query
{
	[Bindable]
	public class ActuatorQueryModel extends BaseQueryModel
	{
		private var _signal:int;			// Signal
		private var _motor:int;				// Motor
		private var _closeOff:int;			// Close Off
		private var _positioner:int;		// Has Positioner 
		private var _safetyFunction:int;	// Safety Function
		private var _supplyVoltage:int;		// Supply
		private var _hasHeater:int;			// Has Heater
		private var _hasManualOverride:int;	// Has Manual Override
		private var _endSwitch:int;			// End Switch
		
		
		public function ActuatorQueryModel() {
			super();
			
			_signal				= -1;
			_motor				= -1;
			_closeOff			= -1;
			_positioner			= -1;
			_hasHeater			= -1;
			_hasManualOverride	= -1;
			_endSwitch			= -1;
			_safetyFunction		= -1;
			_supplyVoltage		= 0;
		}
		
		
		public function set signal(value:int):void {
			_signal = value;
			changedFields["signal"]	= value;
		}

		public function get signal():int {
			return _signal;
		}

		
		public function set motor(value:int):void {
			_motor = value;
			changedFields["motor"]	= value;
		}

		public function get motor():int {
			return _motor;
		}
		
		
		public function set closeOff(value:int):void {
			_closeOff = value;
			changedFields["closeOff"]	= value;
		}

		public function get closeOff():int {
			return _closeOff;
		}
		

		public function set positioner(value:int):void {
			_positioner = value;
			changedFields["positioner"]	= value;
		}

		public function get positioner():int {
			return _positioner;
		}

		
		public function set safetyFunction(value:int):void {
			_safetyFunction = value;
			changedFields["safetyFunction"]	= value;
		}

		public function get safetyFunction():int {
			return _safetyFunction;
		}
		
		
		public function set supplyVoltage(value:int):void {
			_supplyVoltage = value;
			changedFields["supplyVoltage"]	= value;
		}

		public function get supplyVoltage():int {
			return _supplyVoltage;
		}
		
		
		public function set hasHeater(value:int):void {
			_hasHeater = value;
			changedFields["hasHeater"]	= value;
		}

		public function get hasHeater():int {
			return _hasHeater;
		}
		
		
		public function set hasManualOverride(value:int):void {
			_hasManualOverride = value;
			changedFields["hasManualOverride"]	= value;
		}
		
		public function get hasManualOverride():int {
			return _hasManualOverride;
		}
		
		
		public function set endSwitch(value:int):void {
			_endSwitch = value;
			changedFields["endSwitch"]	= value;
		}

		public function get endSwitch():int {
			return _endSwitch;
		}
		

		override protected function customReset():void {
			_signal				= -1;
			_motor				= -1;
			_closeOff			= -1;
			_positioner			= -1;
			_hasHeater			= -1;
			_hasManualOverride	= -1;
			_endSwitch			= -1;
			_safetyFunction		= -1;
			_supplyVoltage		= 0;		
		}
	}
}