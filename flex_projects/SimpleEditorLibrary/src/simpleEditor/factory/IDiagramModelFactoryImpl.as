package diagram.factory
{
	import diagram.model.DiagramBaseModel;
	
	import mx.collections.ArrayCollection;
	
	
	public interface IDiagramModelFactoryImpl
	{
		function get dataModels():ArrayCollection;
		function createModel(dataModel:IDataModel):DiagramBaseModel;
		function loadModel(xmlModel:XML):DiagramBaseModel;
	}
}