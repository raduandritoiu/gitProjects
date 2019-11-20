package petri.model
{
	import mx.collections.ArrayCollection;

	public class PetriWorld extends PetriContainerModel
	{
		public var models:ArrayCollection = new ArrayCollection();
		public var states:ArrayCollection = new ArrayCollection();
		public var transitions:ArrayCollection = new ArrayCollection();
		
		
		public function PetriWorld() {
			super();
			
			name = "World";
			height = 500;
			width = 600;
			world = this;
		}
		
		
		override public function get editorClass():String {
			return "petri.PropertiesEditors.WorldPropertiesEditor";
		}
		

		public function addToCollectors(arr:Array):void {
			for (var i:int = 0; i < arr.length; i++) {
				var model:PetriBaseModel = arr[i] as PetriBaseModel;
				
				models.addItem(model);
				
				if (model is PetriState) {
					states.addItem(model);
				}
				
				if (model is PetriTransition) {
					transitions.addItem(model);
				}
			}
		}
		
		
		public function removeFromCollectors(arr:Array):void {
			for (var i:int = 0; i < arr.length; i++) {
				var model:PetriBaseModel = arr[i] as PetriBaseModel;
				
				var index:int = models.getItemIndex(model);
				if (index > -1) {
					models.removeItemAt(index);
				}
				
				index = states.getItemIndex(model);
				if (index > -1) {
					states.removeItemAt(index);
				}
				
				index = transitions.getItemIndex(model);
				if (index > -1) {
					transitions.removeItemAt(index);
				}
			}
		}
		
		
		public function getModelByUID(uid:String):PetriBaseModel {
			var model:PetriBaseModel;
			
			for each (model in models) {
				if (model.uid == uid) {
					return model;
				} 
			}
			
			return model;
		}
		
		
		public function createMarking():Marking {
			var marking:Marking = new Marking(states);
			return marking;
		}
		
		
		public function createFutureMarking():Marking {
			var marking:Marking = new Marking();
			for each (var state:PetriState in states) {
				marking.states.addItem(state.createFutureMarkingState());
			}
			marking.codeStates();
			return marking;
		}
	}
}