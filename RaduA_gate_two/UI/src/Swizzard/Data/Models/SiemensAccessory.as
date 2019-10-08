package Swizzard.Data.Models
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.utils.flash_proxy;
	
	import Swizzard.Data.Models.SiemensInfo.AccessoryInfo;
	
	import utils.LogUtils;
	
	use namespace flash_proxy;

	
	[RemoteClass]
	public class SiemensAccessory extends AbstractSiemensProduct implements IExternalizable
	{
		private var _info:AccessoryInfo;
		
		
		public function SiemensAccessory() {
			super();
			_info = new AccessoryInfo();
		}
		
		
		public function set info(val:AccessoryInfo):void {
			_info = val;
		}
		
		public function get info():AccessoryInfo {
			return _info;
		}
		
		
		override public function set partNumber(val:String):void {
			super.partNumber	= val;
			info.partNumber		= val;
		}
		
		
		override flash_proxy function hasProperty(name:*):Boolean {
			if (info.hasOwnProperty(name))
				return true;
			return false;
		}
		
		
		override flash_proxy function getProperty(name:*):* {
			if (name.localName == "null") {
				return null;
			}
			var propertyName:String	= name.localName.toString();
			if (info.hasOwnProperty(propertyName)) {
				return info[propertyName];
			}
			return "N/A";
		}
		
		
		override flash_proxy function setProperty(name:*, value:*):void {
			if (info.hasOwnProperty(name.localName)) {
				info[name.localName] = value;
			}
			else {
				LogUtils.Debug("Accessory Property Doesn't Exist: " + name.localName);
			}	
		}
		
		
		override public function writeExternal(output:IDataOutput):void {
			// fix some of the properties and make them not-null for writing
			// this is because some accessories (MORE EXATLY ONE 333-071 FOR PNEUMATICS)
			// is not found in the Products.dbf so some of its values are set to null 
			// when reading from DB
			writeFixes();
			super.writeExternal(output);
			output.writeObject(_info);
		}
		
		
		override public function readExternal(input:IDataInput):void {
			super.readExternal(input);
			_info = input.readObject() as AccessoryInfo;
		}
	}
}