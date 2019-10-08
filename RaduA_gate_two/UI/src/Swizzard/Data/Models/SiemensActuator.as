package Swizzard.Data.Models
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.utils.flash_proxy;
	
	import Swizzard.Data.Models.SiemensInfo.ActuatorInfo;
	
	import utils.LogUtils;
	
	use namespace flash_proxy;
	
	[RemoteClass]
	public class SiemensActuator extends AbstractSiemensProduct implements IExternalizable
	{
		private var _info:ActuatorInfo;		// Info
		
		public function SiemensActuator() {
			super();
			_info	= new ActuatorInfo();
		}
		
		
		override public function writeExternal(output:IDataOutput):void {
			super.writeExternal(output);
			output.writeObject(_info);
		}
		
		
		override public function readExternal(input:IDataInput):void {
			super.readExternal(input);
			_info	= input.readObject() as ActuatorInfo;
		}
		
		
		public function set info(value:ActuatorInfo):void {
			_info = value;
		}
		
		public function get info():ActuatorInfo {
			return _info;
		}
		
		
		override public function set partNumber(value:String):void {
			super.partNumber	= value;
			info.partNumber		= value;
		}
		
		
		override flash_proxy function getProperty(name:*):* {
			if (name.localName == "null")
				return null;
				
			if (this.info.hasOwnProperty(name.localName)) {
				return this.info[name.localName];
			}
						
			return "N/A";
		}
		
		
		override flash_proxy function setProperty(name:*, value:*):void {
			if (this.info.hasOwnProperty(name.localName)) {
				this.info[name.localName]	= value;
			}
			else {
				LogUtils.Debug("Actuator Property Doesn;t Exist: " + name.localName);
			}	
			
			/*
			// Product Vars
			for each (var prodVar:XML in AbstractSiemensProduct.productInfo.accessor) {
				if (prodVar.@name.toString() == name.localName) {
					this[name.localName] = value;
					return;
				}
			}
			// Info Vars 
			for each (var infoVar:XML in valveInfo.accessor) {
				if (infoVar.@name.toString() == name.localName) {
					this.info[name.localName] = value;
					break;
				}
			}
			*/
		}
		
		
		override flash_proxy function hasProperty(name:*):Boolean {
			if (info.hasOwnProperty(name))
				return true;
			return false;
		}
	}
}