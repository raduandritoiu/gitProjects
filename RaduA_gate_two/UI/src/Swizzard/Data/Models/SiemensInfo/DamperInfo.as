package Swizzard.Data.Models.SiemensInfo
{
	import Swizzard.Data.Models.Enumeration.Dampers.DamperAuxilarySwitch;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperControlSignal;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperPlenum;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperPositionFeedback;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperScalableSignal;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperSystemSupply;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;

	[RemoteClass]
	public class DamperInfo
	{
		public var id:Number;
		private var _partNumber:String;		// Part Number
		private var _type:int;
		private var _torque:Number;
		private var _controlSignal:int;
		private var _systemSupply:int;
		private var _plenumRating:int;
		private var _positionFeedback:int;
		private var _auxilarySwitch:int;
		private var _scalableSignal:int;
		
		// for pretty string formating
		private var _typeString:String;
		private var _controlSignalString:String;
		private var _systemSupplyString:String;
		private var _plenumRatingString:String;
		private var _auxilarySwitchString:String;
		private var _positionFeedbackString:String;
		private var _scalableSignalString:String;
		
		
		public function DamperInfo()
		{}
		
		
		public function set partNumber(val:String):void {
			_partNumber = val;
		}
		public function get partNumber():String {
			return _partNumber;
		}
		
		
		public function set type(val:int):void {
			_type = val;
			_typeString = DamperType.toString(val);
		}
		public function get type():int {
			return _type;
		}
		public function get typeString():String {
			return _typeString;
		}
		
		
		public function set torque(val:Number):void {
			_torque = val;
		}
		public function get torque():Number {
			return _torque;
		}
		
		
		public function set controlSignal(val:int):void {
			_controlSignal = val;
			_controlSignalString = DamperControlSignal.toString(val);
		}
		public function get controlSignal():int {
			return _controlSignal;
		}
		public function get controlSignalString():String {
			return _controlSignalString;
		}
		
		
		public function set systemSupply(val:int):void {
			_systemSupply = val;
			_systemSupplyString = DamperSystemSupply.toString(val);
		}
		public function get systemSupply():int {
			return _systemSupply;
		}
		public function get systemSupplyString():String {
			return _systemSupplyString;
		}
		
		
		public function set plenumRating(val:int):void {
			_plenumRating = val;
			_plenumRatingString = DamperPlenum.toString(val);
		}
		public function get plenumRating():int {
			return _plenumRating;
		}
		public function get plenumRatingString():String {
			return _plenumRatingString;
		}
		
		
		public function set positionFeedback(val:int):void {
			_positionFeedback = val;
			_positionFeedbackString = DamperPositionFeedback.toString(val);
		}
		public function get positionFeedback():int {
			return _positionFeedback;
		}
		public function get positionFeedbackString():String {
			return _positionFeedbackString;
		}
		
		
		public function set auxilarySwitch(val:int):void {
			_auxilarySwitch = val;
			_auxilarySwitchString = DamperAuxilarySwitch.toString(val);
		}
		public function get auxilarySwitch():int {
			return _auxilarySwitch;
		}
		public function get auxilarySwitchString():String {
			return _auxilarySwitchString;
		}
			
		
		public function set scalableSignal(val:int):void {
			_scalableSignal = val;
			_scalableSignalString = DamperScalableSignal.toString(val);
		}
		public function get scalableSignal():int {
			return _scalableSignal;
		}
		public function get scalableSignalString():String {
			return _scalableSignalString;
		}
	}
}