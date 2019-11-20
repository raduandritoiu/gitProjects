package petri.model
{
	import mx.collections.ArrayCollection;

	public class MarkingTransition
	{
		private var _transitions:ArrayCollection;
		private var _code:int;
		private var _prevMarking:Marking;
		private var _nextMarking:Marking;
		
		
		public function MarkingTransition() {
		}
		
		
		public function set transitions(value:ArrayCollection):void {
			_transitions = value;
		}
		
		public function get transitions():ArrayCollection {
			return _transitions;
		}
		
		
		public function set code(value:int):void {
			_code = value;
		}
		
		public function get code():int {
			return _code;
		}
		
		
		public function set prevMarking(value:Marking):void {
			_prevMarking = value;
		}
		
		public function get prevMarking():Marking {
			return _prevMarking;
		}
		
		
		public function set nextMarking(value:Marking):void {
			_nextMarking = value;
		}
		
		public function get nextMarking():Marking {
			return _nextMarking;
		}
	}
}