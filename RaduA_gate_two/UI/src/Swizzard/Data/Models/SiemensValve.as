package Swizzard.Data.Models
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.utils.flash_proxy;
	
	import mx.events.PropertyChangeEvent;
	
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;
	import Swizzard.Data.Models.SiemensInfo.ValveInfo;
	
	import utils.LogUtils;
	
	use namespace flash_proxy;
	
	
	[RemoteClass]
	public class SiemensValve extends AbstractSiemensProduct implements IExternalizable
	{		
		private var _info:ValveInfo;		// Info : Valve
		
		
		public function SiemensValve() {
			super();
			_info = new ValveInfo();
		}
		
		
		public function set info(value:ValveInfo):void  {
			_info = value;
		}
		
		public function get info():ValveInfo  {
			return _info;
		}
		
		
		public function set useCVLField(value:Boolean):void {
			if (info) {
				info.useCVLField	= value;
			}
			dispatchEvent(new PropertyChangeEvent(PropertyChangeEvent.PROPERTY_CHANGE));
		}
		
		public function get useCVLField():Boolean {
			if (info) {
				return info.useCVLField;
			}
			return false;
		}
		
		
		override public function set partNumber(value:String):void {
			super.partNumber	= value;
			info.partNumber		= value;
		}
		
		
		override public function set lastModified(value:Date):void {
			super.lastModified	= value;
			info.lastModified	= value;
		}
		
		
		override public function writeExternal(output:IDataOutput):void {
			super.writeExternal(output);
			output.writeObject(_info);
		}
		
		
		override public function readExternal(input:IDataInput):void {
			super.readExternal(input);
			_info = input.readObject() as ValveInfo;
		}
			
		
		override flash_proxy function getProperty(name:*):* {
			if (name.localName == "null")
				return null;
			
			var propertyName:String	= name.localName.toString();
			switch (propertyName) {
				case "cv":
				case "actualDeltaP":
				case "actualDeltaPB":
				{
					if (info.type == ValveType.PICV) {
						return "N/A";
					}
					else if (info.hasOwnProperty(propertyName)) {
						return info[propertyName];
					}
					break;
				}
				case "minGPM":
				case "maxGPM":
				case "presetGPM":
				{
					if (info.type != ValveType.PICV) {
						return "N/A";
					}
					else if (info.hasOwnProperty(propertyName)) {
						return info[propertyName];
					}
					
					break;
				}
				default:
				{
					if (info.hasOwnProperty(propertyName)) {
						return info[propertyName];
					}
				}
			}
			return "N/A";
		}
		
		
		override flash_proxy function setProperty(name:*, value:*):void {
			if (info.hasOwnProperty(name.localName)) {
				info[name.localName] = value;
			}
			else {
				LogUtils.Debug("Valve Property Doesn't Exist: " + name.localName);
			}	
		}
		
		
		override flash_proxy function hasProperty(name:*):Boolean {
			if (info.hasOwnProperty(name))
				return true;
			return false;
		}
	}
}