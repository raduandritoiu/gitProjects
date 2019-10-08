package Swizzard.Data.Models
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.utils.flash_proxy;
	
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Data.Models.SiemensInfo.PneumaticInfo;
	import Swizzard.Images.EmbeddedImages;
	
	import utils.LogUtils;

	use namespace flash_proxy;
	
	
	[RemoteClass]
	public class SiemensPneumatic extends AbstractSiemensProduct implements IExternalizable
	{
		private var _info:PneumaticInfo;
		
		
		public function SiemensPneumatic() {
			super();
			_info = new PneumaticInfo();
		}
		
		
		public function set info(val:PneumaticInfo):void {
			_info = val;
		}
		
		public function get info():PneumaticInfo {
			return _info;
		}
		
		
		override public function set partNumber(val:String):void {
			super.partNumber	= val;
			info.partNumber		= val;
		}
		
		
		override public function get icon():Class {
			return EmbeddedImages.pneumatic16_img;
		}
		
		
		override public function get productType():uint {
			return ProductType.PNEUMATIC;
		}
		
		
		// - From flash proxy
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
				LogUtils.Debug("Pneumatic Property Doesn't Exist: " + name.localName);
			}	
		}
		
		
		// - From IExternalizable
		override public function writeExternal(output:IDataOutput):void {
			super.writeExternal(output);
			output.writeObject(_info);
		}
		
		override public function readExternal(input:IDataInput):void {
			super.readExternal(input);
			_info = input.readObject() as PneumaticInfo;
		}
	}
}