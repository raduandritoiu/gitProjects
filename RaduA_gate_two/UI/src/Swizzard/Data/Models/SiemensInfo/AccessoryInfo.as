package Swizzard.Data.Models.SiemensInfo
{
	[RemoteClass]
	public class AccessoryInfo
	{
		public var id:Number;
		private var _partNumber:String;
		private var _fromDamper:int;
		private var _fromPneumatic:int;
		private var _extraInfo:Number;
		
		
		public function AccessoryInfo()
		{}
		
		
		public function set partNumber(val:String):void {
			_partNumber = val;
		}
		
		public function get partNumber():String {
			return _partNumber;
		}
		
		
		public function get typeString():String {
			return "Accessory Kit";
		}
		
		
		public function set fromDamper(val:int):void {
			_fromDamper = val;
		}
		
		public function get fromDamper():int {
			return _fromDamper;
		}
		
		
		public function set fromPneumatic(val:int):void {
			_fromPneumatic = val;
		}
		
		public function get fromPneumatic():int {
			return _fromPneumatic;
		}
		
		
		public function set extraInfo(val:Number):void {
			_extraInfo = val;
		}
		
		public function get extraInfo():Number {
			return _extraInfo;
		}
	}
}