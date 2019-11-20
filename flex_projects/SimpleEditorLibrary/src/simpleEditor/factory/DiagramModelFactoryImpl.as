package diagram.factory
{
	import diagram.model.DiagramBaseModel;
	import diagram.model.DiagramBorderSlot;
	import diagram.model.DiagramContainerModel;
	import diagram.model.DiagramLink;
	import diagram.model.DiagramMasterNode;
	import diagram.model.DiagramNode;
	import diagram.model.DiagramSlot;
	import diagram.model.DiagramWorld;
	
	import flash.utils.getDefinitionByName;
	
	import mx.collections.ArrayCollection;

	public class DiagramModelFactoryImpl implements IDiagramModelFactoryImpl
	{
		public static const MASTER_NODE:String 	= "diagramMasterNodeModel";
		public static const NODE:String 		= "diagramNodeModel";
		public static const LINK:String 		= "diagramLinkModel";
		public static const BORDER_SLOT:String 	= "diagarmBorderSlotModel";
		public static const SLOT:String 		= "diagarmSlotModel";
		
		
		public function DiagramModelFactoryImpl() {
		}
		
		
		public function get dataModels():ArrayCollection {
			var dataModelsArr:ArrayCollection = new ArrayCollection();
			dataModelsArr.addItem(new DataModel("Master Node", MASTER_NODE));
			dataModelsArr.addItem(new DataModel("Node", NODE));
			dataModelsArr.addItem(new DataModel("Border Slot", BORDER_SLOT));
			dataModelsArr.addItem(new DataModel("Slot", SLOT));
			dataModelsArr.addItem(new DataModel("Link", LINK));
			return dataModelsArr;
		}
		
		
		public function createModel(dataModel:IDataModel):DiagramBaseModel {
			var newModel:DiagramBaseModel;
			
			switch (dataModel.type) {
				case MASTER_NODE:
					newModel = new DiagramMasterNode();
					break;
				
				case MASTER_NODE:
					newModel = new DiagramMasterNode();
					break;
				
				case NODE:
					newModel = new DiagramNode();
					break;
				
				case BORDER_SLOT:
					newModel = new DiagramBorderSlot();
					break;
				
				case SLOT:
					newModel = new DiagramSlot();
					break;
				
				case LINK:
					newModel = new DiagramLink();
					break;
			}
			
			return newModel;
		}
		
		
		public function loadModel(xmlModel:XML):DiagramBaseModel {
			var className:String = xmlModel.@type.toString();
			var modelClass:Class = getDefinitionByName(className) as Class;
			var modelInstance:DiagramBaseModel = new modelClass() as DiagramBaseModel;
			return modelInstance;
		}
	}
}