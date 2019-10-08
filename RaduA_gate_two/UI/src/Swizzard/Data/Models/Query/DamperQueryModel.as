package Swizzard.Data.Models.Query
{
	public class DamperQueryModel extends BaseQueryModel
	{
		// parameters to query dampers after
		private var _damperTypes:Array;
		private var _torque:Number;
		private var _controlSignal:int;
		private var _systemSupply:int;
		private var _plenumRating:int;
		private var _auxilarySwitch:int;
		private var _positionFeedback:int;
		private var _scalableSignal:int;
		
		
		public function DamperQueryModel() {
			super();
			_damperTypes = [];
		}
		
		
		[Bindable]
		public function set damperTypes(val:Array):void {
			changedFields["damperTypes"] = true;
			_damperTypes = val;
		}
		
		public function get damperTypes():Array {
			return _damperTypes;
		}
		
		
		[Bindable]
		public function set torque(val:Number):void {
			changedFields["torque"] = true;
			_torque = val;
		}
		
		public function get torque():Number {
			return _torque;
		}
		
		
		[Bindable]
		public function set controlSignal(val:int):void {
			changedFields["controlSignal"] = true;
			_controlSignal = val;
		}
		
		public function get controlSignal():int {
			return _controlSignal;
		}
		
		
		[Bindable]
		public function set systemSupply(val:int):void {
			changedFields["systemSupply"] = true;
			_systemSupply = val;
		}
		
		public function get systemSupply():int {
			return _systemSupply;
		}
		
		
		[Bindable]
		public function set plenumRating(val:int):void {
			changedFields["plenumRating"] = true;
			_plenumRating = val;
		}
		
		public function get plenumRating():int {
			return _plenumRating;
		}
		
		
		[Bindable]
		public function set auxilarySwitch(val:int):void {
			changedFields["auxilarySwitch"] = true;
			_auxilarySwitch = val;
		}
		
		public function get auxilarySwitch():int {
			return _auxilarySwitch;
		}
		
		
		[Bindable]
		public function set positionFeedback(val:int):void {
			changedFields["positionFeedback"] = true;
			_positionFeedback = val;
		}
		
		public function get positionFeedback():int {
			return _positionFeedback;
		}
		
		
		[Bindable]
		public function set scalableSignal(val:int):void {
			changedFields["scalableSignal"] = true;
			_scalableSignal = val;
		}
		
		public function get scalableSignal():int {
			return _scalableSignal;
		}
		
		
		override protected function customReset():void {
			_damperTypes = new Array();
			_torque 			= 0;
			_controlSignal 		= 0;
			_systemSupply 		= 0;
			_plenumRating 		= 0;
			_auxilarySwitch 	= 0;
			_positionFeedback 	= 0;
			_scalableSignal 	= 0;
		}
	}
}