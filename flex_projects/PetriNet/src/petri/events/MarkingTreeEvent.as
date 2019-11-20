package petri.events
{
	import flash.events.Event;
	
	import petri.model.Marking;
	
	public class MarkingTreeEvent extends Event 
	{
		public static const MARKING_ADDED:String = "New_Marking_Added";
		public static const TRANSITION_ADDED:String = "New_Transition_Added";
		public static const RESET_TREE:String = "Reset_Marking_Tree";
		
		private var _newMarking:Marking;
		private var _oldMarking:Marking;
		
		
		public function MarkingTreeEvent(type:String, nMarking:Marking, oMarking:Marking = null, bubbles:Boolean = false, cancelable:Boolean = false) {
			super(type, bubbles, cancelable);
			_newMarking = nMarking;
			_oldMarking = oMarking;
		}
		
		
		public function get newMarking():Marking {
			return _newMarking;
		}
		
		
		public function get oldMarking():Marking {
			return _oldMarking;
		}
	}
}