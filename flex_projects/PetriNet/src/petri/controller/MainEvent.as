package petri.controller
{
	import flash.events.Event;
	
	public class MainEvent extends Event
	{
		public static const RUN:String = "Run_Simulation";
		public static const STEP:String = "Step_Simulation";
		public static const STOP:String = "Stop_Simulation";
		
		public static const SELECTION_CHANGE:String = "Selection_Change";
		
		public static const SAVE:String = "Save_model_tree";
		
		private var _info:Object;
		
		
		public function MainEvent(type:String, info:Object, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
			_info = info;
		}
		
		
		public function get info():Object {
			return _info;
		}
	}
}