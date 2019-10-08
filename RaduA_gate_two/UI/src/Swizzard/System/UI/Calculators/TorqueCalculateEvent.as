package Swizzard.System.UI.Calculators
{
	import flash.events.Event;
	
	public class TorqueCalculateEvent extends Event
	{
		public static const CHANGED:String = "TorqueChanged";
		
		private var _newTorque:Number;
		
		
		public function TorqueCalculateEvent(type:String, torque:Number=0, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
			_newTorque = torque;
		}
		
		
		public function get newTorque():Number {
			return _newTorque;
		}
	}
}