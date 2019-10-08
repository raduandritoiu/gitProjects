package Swizzard.Data.Models.Query
{
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticMaxThrust;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticMountingStyle;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticPosRelay;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticSpringRange;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticStroke;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticType;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticULCerts;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticSelection;

	public class PneumaticQueryModel extends BaseQueryModel
	{
		// parameters to query dampers after
		private var _selection:int;
		private var _pneumType:int;
		private var _stroke:int;
		private var _springRange:int;
		private var _ULCert:int;
		private var _posRelay:int;
		private var _maxThrust:Number;  // this is for Max Thrust full Strioke @ 18 PSI
		private var _mountingStyle:int;
		
		
		public function PneumaticQueryModel() {
			super();
			_selection = PneumaticSelection.NONE;
		}
		
		
		[Bindable]
		public function set selection(val:int):void {
			changedFields["selection"] = true;
			_selection = val;
		}
		public function get selection():int {
			return _selection;
		}
		
		
		[Bindable]
		public function set pneumType(val:int):void {
			_pneumType = val;
			changedFields["pneumType"] = true;
		}
		public function get pneumType():int {
			return _pneumType;
		}
		
		
		[Bindable]
		public function set stroke(val:int):void {
			_stroke = val;
			changedFields["stroke"] = true;
		}
		public function get stroke():int {
			return _stroke;
		}
		
		
		[Bindable]
		public function set springRange(val:int):void {
			_springRange = val;
			changedFields["springRange"] = true;
		}
		public function get springRange():int {
			return _springRange;
		}
		
		
		[Bindable]
		public function set ULCert(val:int):void {
			_ULCert = val;
			changedFields["ULCert"] = true;
		}
		public function get ULCert():int {
			return _ULCert;
		}
		
		
		[Bindable]
		public function set posRelay(val:int):void {
			_posRelay = val;
			changedFields["posRelay"] = true;
		}
		public function get posRelay():int {
			return _posRelay;
		}
		
		
		[Bindable]
		public function set maxThrust(val:Number):void {
			_maxThrust = val;
			changedFields["maxThrust"] = true;
		}
		public function get maxThrust():Number {
			return _maxThrust;
		}
		
		
		[Bindable]
		public function set mountingStyle(val:int):void {
			_mountingStyle = val;
			changedFields["mountingStyle"] = true;
		}
		public function get mountingStyle():int {
			return _mountingStyle;
		}
		
		
		override protected function customReset():void {
			_selection 		= PneumaticSelection.NONE;
			_pneumType 		= 0;
			_stroke 		= 0;
			_springRange 	= 0;
			_ULCert 		= 0;
			_posRelay 		= 0;
			_maxThrust 		= 0; 
			_mountingStyle 	= 0;
		}
		
		
		override public function toString():String {
			var str:String = "Pneumatic Query Model: "; 
			str += "\n   _selection     =    (" + _selection + ") \t " 	+ PneumaticSelection.toString(_selection);
			str += "\n   _pneumType     =    (" + _pneumType + ") \t " 	+ PneumaticType.toString(_pneumType);
			str += "\n   _stroke        =    (" + _stroke + ") \t " 	+ PneumaticStroke.toString(_stroke);
			str += "\n   _springRange   =    (" + _springRange + ") \t " 	+ PneumaticSpringRange.toString(_springRange);
			str += "\n   _ULCert        =    (" + _ULCert + ") \t " 	+ PneumaticULCerts.toString(_ULCert);
			str += "\n   _posRelay      =    (" + _posRelay + ") \t " 	+ PneumaticPosRelay.toString(_posRelay);
			str += "\n   _maxThrust     =    (" + _maxThrust + ") \t " 	+ PneumaticMaxThrust.toString(_maxThrust);
			str += "\n   _mountingStyle =    (" + _mountingStyle + ") \t " 	+ PneumaticMountingStyle.toString(_mountingStyle);
			return str;
		}
	}
}