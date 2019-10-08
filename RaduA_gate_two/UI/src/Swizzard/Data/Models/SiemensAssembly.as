package Swizzard.Data.Models
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.utils.flash_proxy;
	
	use namespace flash_proxy;
	
	[RemoteClass]
	public class SiemensAssembly extends AbstractSiemensProduct implements IExternalizable
	{
		
		private var _valvePartNumber:String;	// Valve Part Number
		private var _actuatorPartNumber:String;	// Actuator Part Number
		private var _normalState:uint		= 0;
		private var _threeWayFunction:uint	= 0;
		private var _controlSignal:uint		= 0;
		private var _closeOff:Number		= 0;
		
		// Object References
		private var _valve:SiemensValve;		// Siemens Valve
		private var _actuator:SiemensActuator;	// Siemens Actuator
		
		
				
		public function SiemensAssembly() {
			super();
		}
		
		
		override public function writeExternal(output:IDataOutput):void {
			super.writeExternal(output);
			
			output.writeUTF(_valvePartNumber);
			output.writeUTF(_actuatorPartNumber);
			output.writeUnsignedInt(_normalState);
			output.writeUnsignedInt(_threeWayFunction);
			output.writeUnsignedInt(_controlSignal);
			output.writeDouble(_closeOff);
			
			output.writeObject(_valve);
			output.writeObject(_actuator);
		}
		
		override public function readExternal(input:IDataInput):void {
			super.readExternal(input);
			
			_valvePartNumber		= input.readUTF();
			_actuatorPartNumber		= input.readUTF();
			_normalState			= input.readUnsignedInt();
			_threeWayFunction		= input.readUnsignedInt();
			_controlSignal			= input.readUnsignedInt();
			_closeOff				= input.readDouble();
						
			_valve			= input.readObject() as SiemensValve;
			_actuator		= input.readObject() as SiemensActuator;
		}
		
		
		public function set valve(value:SiemensValve):void {
			_valve = value;
		}

		public function get valve():SiemensValve {
			return _valve;
		}
		
		
		public function set actuator(value:SiemensActuator):void {
			_actuator = value;
			
			if (value && value.info)
				value.info.closeOff	= closeOff;
		}

		public function get actuator():SiemensActuator {
			return _actuator;
		}
		
		
		public function set valvePartNumber(value:String):void {
			_valvePartNumber = value;
		}
		
		public function get valvePartNumber():String {
			return _valvePartNumber;
		}

		
		public function set actuatorPartNumber(value:String):void {
			_actuatorPartNumber = value;
		}

		public function get actuatorPartNumber():String {
			return _actuatorPartNumber;
		}
		
		
		public function set normalState(value:uint):void {
			_normalState	= value;
		}
		
		public function get normalState():uint {
			return _normalState;
		}
		
		
		public function set threeWayFunction(value:uint):void {
			_threeWayFunction	= value;
		}
		
		public function get threeWayFunction():uint {
			return _threeWayFunction;
		}
		
		
		public function set controlSignal(value:uint):void {
			_controlSignal	= value;
		}
		
		public function get controlSignal():uint {
			return _controlSignal;
		}
		
		
		public function set closeOff(value:Number):void {
			_closeOff	= value;
			if (actuator && actuator.info)
				actuator.info.closeOff	= value;
		}
		
		public function get closeOff():Number {
			return _closeOff;
		}
		
		
		override flash_proxy function getProperty(name:*):* {
			if (name.localName == "null")
				return null;
			
			var accessor:Array	= name.localName.split(":");
			
			var discriminant:String	= accessor[0];
			var propertyName:String	= accessor[1];
			
			switch (discriminant) {
				case "valve":
				{
					if (!valve)
						return "N/A";
		
					return valve[propertyName];
					
					break;
				}
				
				case "actuator":
				{
					if (!actuator)
						return "N/A";
					
					return actuator[propertyName];
					
					break;
				}
			}
			
			return null;
		}
		
		
		override flash_proxy function hasProperty(name:*):Boolean {
			return false;
		}
	}
}