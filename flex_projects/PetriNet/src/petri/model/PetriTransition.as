package petri.model
{
	public class PetriTransition extends PetriModelWithArcs
	{
		public function PetriTransition() {
			super();
			
			name = "Transition " + cnt;
			width = 30;
			height = 100;
		}
		
		
		override public function get editorClass():String {
			return "petri.PropertiesEditors.StandardPropertiesEditor";
		}
		
		
		public function get valid():Boolean {
			if (innerArcs == null || outterArcs == null)
				return false;
			if (innerArcs.length > 0 && outterArcs.length > 0)
				return true;
			return false
		}
		
		
		public function get enabled():Boolean {
			var test:Boolean = true;
			
			for each (var arc:PetriArc in innerArcs) {
				if (arc.tokens > (arc.from as PetriState).currentTokens) {
					return false;
				}
			}
			return test;
		}
		
		
		public function execute():void {
			for each (var arc:PetriArc in innerArcs) {
				(arc.from as PetriState).currentTokens -= arc.tokens;
			}
			
			for each (var arc:PetriArc in outterArcs) {
				(arc.to as PetriState).futureTokens += arc.tokens;
			}
		}
	}
}