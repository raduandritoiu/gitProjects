package petri.model
{
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;
	
	
	public class Marking extends EventDispatcher
	{
		private static var ID_CNT:uint = 0;
		
		private var _id:uint;
		private var _initial:Boolean;
		private var _current:Boolean;
		private var _states:ArrayCollection;
		private var _code:String;
		private var _prevTrans:ArrayCollection;
		private var _nextTrans:ArrayCollection;
		private var _prevMarkings:ArrayCollection;
		private var _nextMarkings:ArrayCollection;
		
		
		public function Marking(petriStates:ArrayCollection = null) {
			_id = ID_CNT++;
			
			_prevTrans = new ArrayCollection();
			_nextTrans = new ArrayCollection();
			_prevMarkings = new ArrayCollection();
			_nextMarkings = new ArrayCollection();
			
			_states = new ArrayCollection();
			if (petriStates != null) {
				for each (var petriState:PetriState in petriStates) {
					_states.addItem(petriState.createMarkingState());
				}
			}
			codeStates();
		}
		
		
		public function get id():uint {
			return _id;
		}
		
		
		public function get states():ArrayCollection {
			return _states;
		}
		
		
		public function set nextTrans(value:ArrayCollection):void {
			_nextTrans = value;
		}
		
		public function get nextTrans():ArrayCollection {
			return _nextTrans;
		}
		
		
		public function set prevTrans(value:ArrayCollection):void {
			_prevTrans = value;
		}
		
		public function get prevTrans():ArrayCollection {
			return _prevTrans;
		}
		
		
		public function set initial(value:Boolean):void {
			_initial = value;
		}
		
		public function get initial():Boolean {
			return _initial;
		}
		
		
		[Bindable]
		public function set current(value:Boolean):void {
			_current = value;
		}
		
		public function get current():Boolean {
			return _current;
		}
		
		
		public function get prevMarkings():ArrayCollection {
			return _prevMarkings;
		} 
		
		
		public function get nextMarkings():ArrayCollection {
			return _nextMarkings;
		}
		
		
		public function addPrevTrans(markingTransition:MarkingTransition):void {
			prevTrans.addItem(markingTransition);
			prevMarkings.addItem(markingTransition.prevMarking);
		}
		
		
		public function addNextTrans(markingTransition:MarkingTransition):void {
			nextTrans.addItem(markingTransition);
			nextMarkings.addItem(markingTransition.nextMarking);
		}
		
		
		public function get code():String {
			if (_code == null || _code == "")
				codeStates();
			
			return _code;
		}
		
		
		public function hasNextTransitions(transitionsCode:int):Boolean {
			for each (var trans:MarkingTransition in nextTrans) {
				if (trans.code == transitionsCode) {
					return true;
				}
			}
			return false;
		}
		
		
		public function codeStates():void {
			if (states.length == 0)
				return;
			
			_code = "(";
			for each (var state:MarkingState in states) {
				_code += state.tokens + ", ";
			}
			_code = _code.substr(0, _code.length - 2) + ")";
		}
	}
}