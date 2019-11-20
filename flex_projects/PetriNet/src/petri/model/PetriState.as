package petri.model
{
	public class PetriState extends PetriModelWithArcs
	{
		public var currentTokens:Number;
		public var futureTokens:Number;
		
		
		public function PetriState() {
			super();
			name = "State " + cnt;
			width = 100;
			height = 100;
		}
		
		
		override public function get editorClass():String {
			return "petri.PropertiesEditors.StandardPropertiesEditor";
		}
		
		
		public function initStep():void {
			currentTokens = tokens;
			futureTokens = 0;
		}
		
		
		public function commitStep():void {
			tokens = currentTokens + futureTokens;
		}
		
		
		public function createMarkingState():MarkingState {
			return new MarkingState(this);
		}
		
		
		public function createFutureMarkingState():MarkingState {
			return new MarkingState(null, uid, currentTokens + futureTokens);
		}
	}
}