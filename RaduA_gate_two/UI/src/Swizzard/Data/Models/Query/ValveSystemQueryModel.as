package Swizzard.Data.Models.Query
{
	import flash.events.Event;
	
	import mx.events.PropertyChangeEvent;
	
	
	[Bindable]
	public class ValveSystemQueryModel extends BaseQueryModel
	{
		private var _actuator:ActuatorQueryModel;		// Acutator  Actuator Query
		private var _valve:ValveQueryModel;				// Valve  Valve Query
		
		
		public function ValveSystemQueryModel() {
			super();
			valve		= new ValveQueryModel();
			actuator 	= new ActuatorQueryModel();
		}
		
		
		public function set actuator(value:ActuatorQueryModel):void {
			if (_actuator)
				_actuator.removeEventListener(PropertyChangeEvent.PROPERTY_CHANGE, redispatchEvent);
			_actuator = value;
			if (_actuator)
				_actuator.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, redispatchEvent, false, 0, true);
		}
		
		public function get actuator():ActuatorQueryModel {
			return _actuator;
		}
		

		public function set valve(value:ValveQueryModel):void {
			if (_valve)
				_valve.removeEventListener(PropertyChangeEvent.PROPERTY_CHANGE, redispatchEvent);
			_valve = value;
			if (_valve)
				_valve.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, redispatchEvent, false, 0, true);
		}
		
		public function get valve():ValveQueryModel {
			return _valve;
		}
		
		
		private function redispatchEvent(event:Event):void {
			if (!suppressEvents) {
				dispatchEvent(event);
			}
		}
		
		
		override protected function customReset():void {
			valve.reset();
			actuator.reset();
			resetPending	= true;
		}
	}
}