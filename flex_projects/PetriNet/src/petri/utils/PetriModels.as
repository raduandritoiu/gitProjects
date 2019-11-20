package petri.utils
{
	import flash.utils.getDefinitionByName;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
	
	import petri.model.PetriArc;
	import petri.model.PetriBaseModel;
	import petri.model.PetriContainerModel;
	import petri.model.PetriLayer;
	import petri.model.PetriState;
	import petri.model.PetriTransition;
	import petri.model.PetriWorld;

	public class PetriModels
	{
		public static const STATE:String = "petriStateModel";
		public static const TRANSITION:String = "petriTransitionModel";
		public static const ARC:String = "petriArcModel";
		
		private static var modelClasses:Array = [PetriArc, PetriBaseModel, PetriState, 
			PetriTransition, PetriWorld,	PetriLayer, PetriContainerModel, PetriBaseModel];
			
		
		public function PetriModels()
		{}
		
		
		public static function modelData():ArrayCollection {
			var modelData:ArrayCollection = new ArrayCollection();
			modelData.addItem(new ModelData("State", STATE));
			modelData.addItem(new ModelData("Transition", TRANSITION));
			return modelData;
		}
		
		
		public static function createModel(modelData:ModelData):PetriBaseModel {
			var newModel:PetriBaseModel;
			switch (modelData.type) {
				case STATE:
					newModel = new PetriState();
					break;
				
				case TRANSITION:
					newModel = new PetriTransition();
					break;
				
				case ARC:
					newModel = new PetriArc();
					break;
			}
			
			return newModel;
		}
		
		
		public static function createModelInstance(xmlModel:XML):PetriBaseModel {
			var className:String = xmlModel.@type.toString();
			var modelClass:Class = getDefinitionByName(className) as Class;
			var modelInstance:PetriBaseModel = new modelClass() as PetriBaseModel;
			
			return modelInstance;
		}
	}
}