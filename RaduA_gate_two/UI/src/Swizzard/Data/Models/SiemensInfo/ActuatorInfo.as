package Swizzard.Data.Models.SiemensInfo
{
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorControlSignal;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorMotorType;
	import Swizzard.Data.Models.Enumeration.Actuator.ActuatorSafetyFunction;
	
	
	[RemoteClass]
	public class ActuatorInfo
	{
		private var _partNumber:String;		// Part Number
		private var _positioner:uint;		// Positioner
		private var _motorType:uint;		// Motor Type
		private var _controlSignal:uint;	// Control Signal
		private var _controlSignalAsm:int;	// Control Signal Assembly
		private var _closeOff:Number;		// Close Off
		private var _safetyFunction:uint;	// Safety Function
			
		private var _motorTypeString:String; 	// Motor Type
		private var _supplyVoltage:uint;		// Supply Voltage
		private var _endSwitch:uint;			// End Switch
		private var _hasHeater:Boolean;			// Has Heater
		private var _hasManualOverride:Boolean;	// Has Manual Override
		
		private var _butterflyConfig:String;	// Butterfly 3-Way configuration
				
		private var _controlSignalString:String;
		private var _controlSignalAsmString:String;
		private var _safetyFunctionString:String;
		//private var _normalState:uint;		// Normal State
						
		
		public function ActuatorInfo()
		{
			this.controlSignalAsm	= -1;
		}
		
		
		public function set partNumber(value:String):void {
			this._partNumber = value;
		}

		public function get partNumber():String {
			return this._partNumber;
		}

		
		public function set positioner(value:uint):void {
			this._positioner = value;
		}

		public function get positioner():uint {
			return this._positioner;
		}

		
		public function set motorType(value:uint):void {
			this._motorType 		= value;
			this._motorTypeString	= ActuatorMotorType.toString(value);
		}

		public function get motorType():uint {
			return this._motorType;
		}
		
		
		public function get motorTypeString():String {
			return this._motorTypeString;
		}

		
		public function set controlSignal(value:uint):void {
			this._controlSignal 		= value;
			this._controlSignalString	= ActuatorControlSignal.toString(value);
		}
		
		public function get controlSignal():uint {
			if (this.motorType == ActuatorMotorType.PNEUMATIC)
			{
				if (controlSignalAsm > -1)
					return controlSignalAsm;
			}
			return this._controlSignal;
		}
		
		
		public function get controlSignalString():String {
			if (this.motorType == ActuatorMotorType.PNEUMATIC)
			{
				if (controlSignalAsm > -1)
					return this._controlSignalAsmString;
			}
			
			return this._controlSignalString;
		}
		
		
		public function set controlSignalAsm(value:int):void {
			this._controlSignalAsm			= value;
			this._controlSignalAsmString	= ActuatorControlSignal.toString(value);
		}
		
		public function get controlSignalAsm():int {
			return this._controlSignalAsm;
		}
		
		
		public function get controlSignalAsmString():String {
			return this._controlSignalAsmString;
		}
	
		
		public function set closeOff(value:Number):void {
			this._closeOff = value;
		}

		public function get closeOff():Number {
			return this._closeOff;
		}
		
		
		public function set safetyFunction(value:uint):void {
			this._safetyFunction		= value;
			this._safetyFunctionString	= ActuatorSafetyFunction.toString(value);
		}
		
		public function get safetyFunction():uint {
			return this._safetyFunction;
		}
		
		
		public function get safetyFunctionString():String {
			return this._safetyFunctionString;
		}
		
		
		public function set supplyVoltage(value:uint):void {
			this._supplyVoltage = value;
		}
		
		public function get supplyVoltage():uint {
			return this._supplyVoltage;
		}

		
		public function set endSwitch(value:uint):void {
			this._endSwitch = value;
		}

		public function get endSwitch():uint {
			return this._endSwitch;
		}

		
		public function set hasHeater(value:Boolean):void {
			this._hasHeater = value;
		}

		public function get hasHeater():Boolean {
			return this._hasHeater;
		}

		
		public function set hasManualOverride(value:Boolean):void {
			this._hasManualOverride = value;
		}

		public function get hasManualOverride():Boolean {
			return this._hasManualOverride;
		}
		
		
		public function set butterflyConfig(value:String):void {
			this._butterflyConfig = value;
		}

		public function get butterflyConfig():String {
			return this._butterflyConfig;
		}
		
		
		/* public function get normalState():uint
		{
			return this._normalState;
		}
		public function set normalState(value:uint):void
		{
			this._normalState	= value;
		} */
	}
}