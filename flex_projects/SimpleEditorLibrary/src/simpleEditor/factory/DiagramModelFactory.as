package diagram.factory
{
	import diagram.model.DiagramBaseModel;
	import diagram.model.DiagramContainerModel;
	import diagram.model.DiagramLink;
	import diagram.model.DiagramNode;
	import diagram.model.DiagramSlot;
	import diagram.model.DiagramWorld;
	import diagram.factory.DataModel;
	
	import flash.utils.getDefinitionByName;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
	
	
	public class DiagramModelFactory
	{
		private static var impl:IDiagramModelFactoryImpl = new DiagramModelFactoryImpl();
		
		
		public function DiagramModelFactory()
		{}
		
		
		public static function setImplementation(newImpl:IDiagramModelFactoryImpl):void {
			impl = newImpl;
		}
		
		
		public static function get dataModels():ArrayCollection {
			return impl.dataModels;
		}
		
		
		public static function createModel(dataModel:IDataModel):DiagramBaseModel {
			return impl.createModel(dataModel);
		}
		
		
		public static function loadModel(xmlModel:XML):DiagramBaseModel {
			return impl.loadModel(xmlModel);
		}
	}
}