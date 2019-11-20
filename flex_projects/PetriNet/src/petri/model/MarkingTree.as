package petri.model
{
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;
	
	import petri.events.MarkingTreeEvent;
	
	
	public class MarkingTree extends EventDispatcher
	{
		private var _root:Marking;
		private var _current:Marking;
		private var _markings:ArrayCollection;
		private var _transitionNum:Number;
		private var _transitions:ArrayCollection;
		
		private var _possibleTransitions:ArrayCollection;
		private var _possibledTransitionsCode:ArrayCollection;
		
		
		public function MarkingTree() {
		}
		
		
		public function set root(value:Marking):void {
			_root = value;
			current = value;
			current.current = true;
			reset();
		}
		
		public function get root():Marking {
			return _root;
		}
		
		
		public function set current(value:Marking):void {
			_current = value;
		}
		
		public function get current():Marking {
			return _current ;
		}
		
		
		public function set transitions(value:ArrayCollection):void {
			_transitions = value;
			calculateTransitionPossibilities();
		}
		
		public function get transitions():ArrayCollection {
			return _transitions;
		}
		
		
		public function get possibleTransitions():ArrayCollection {
			return _possibleTransitions;
		}
		
		
		public function get possibleTransitionsCode():ArrayCollection {
			return _possibledTransitionsCode;
		}
		
		
		public function get markings():ArrayCollection {
			return _markings;
		}
		
		
		public function reset():void {
			_markings = new ArrayCollection([_root]);
			var evt:MarkingTreeEvent = new MarkingTreeEvent(MarkingTreeEvent.RESET_TREE, _root);
			dispatchEvent(evt);
		}
		
		
		public function addMarking(marking:Marking, transitions:ArrayCollection):void {
			var markingFound:Boolean = false;
			if (getMarking(marking) != null) {
				marking = getMarking(marking);
				var idx:int = markings.getItemIndex(marking);
				markings.removeItemAt(idx);
				markingFound = true;
			}
			
			var markingTrans:MarkingTransition = new MarkingTransition();
			markingTrans.nextMarking = marking;
			markingTrans.prevMarking = current;
			markingTrans.transitions = transitions;
			markingTrans.code = codeTransitions(transitions);
			
			markings.addItem(marking);
			current.addNextTrans(markingTrans);
			marking.addPrevTrans(markingTrans);
			
			current.current = false;
			current = marking;
			current.current = true;
			
			var evt:MarkingTreeEvent;
			if (markingFound) {
				evt = new MarkingTreeEvent(MarkingTreeEvent.TRANSITION_ADDED, current);
			}
			else {
				evt = new MarkingTreeEvent(MarkingTreeEvent.MARKING_ADDED, current);
			}
			dispatchEvent(evt);
		}
		
		
		public function getMarking(searchMarking:Marking):Marking {
			for each (var marking:Marking in markings) {
				if (marking.code == searchMarking.code) {
					return marking;
				}
			}
			return null;
		}
		
		
		public function calculateTransitionPossibilities():void {
			_possibleTransitions = new ArrayCollection();
			_possibledTransitionsCode = new ArrayCollection();
			
			var total:Number = transitions.length;
			total = Math.pow(2, total);
			for (var i:int = 1; i < total; i++) {
				_possibleTransitions.addItem(decodeTransitions(i));
				_possibledTransitionsCode.addItem(i);
			}
		}
		
		
		public function decodeTransitions(code:int):ArrayCollection {
			var possibleTrans:ArrayCollection = new ArrayCollection();
			var idx:int = 0;
			var curr:int = code;
			while (curr > 0) {
				if (curr % 2)
					possibleTrans.addItem(transitions.getItemAt(idx));
				curr = curr / 2;
				idx ++;
			}
			return possibleTrans;
		}
		
		
		public function codeTransitions(possibleTrans:ArrayCollection):int {
			var code:int = 0;
			for each (var transition:PetriTransition in possibleTrans) {
				var idx:int = transitions.getItemIndex(transition);
				code += Math.pow(2, idx);
			}
			return code;
		}
		
		
		public function calculateTree():void {
		}
	}
}