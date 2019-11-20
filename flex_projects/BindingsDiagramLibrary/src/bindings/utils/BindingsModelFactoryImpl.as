package bindings.utils
{
	import bindings.model.PointNode;
	import bindings.model.ShapeNode;
	import bindings.model.TranslatorNode;
	
	import diagram.model.DiagramBaseModel;
	import diagram.utils.DiagramModelFactoryImpl;
	import diagram.utils.IDataModel;
	
	import j2inn.builder.mapping.interfaces.IPropertyTranslator;
	import j2inn.builder.mapping.interfaces.IVirtualPoint;
	import j2inn.builder.model.J2BaseModel;
	
	import mx.collections.ArrayCollection;
	
	public class BindingsModelFactoryImpl extends DiagramModelFactoryImpl
	{
		public static const POINT_NODE:String = "diagramPointNode";
		public static const SHAPE_NODE:String = "diagramShapeNode";
		public static const TRANSLATOR_NODE:String = "diagramTranslatorNode";
		
		
		public function BindingsModelFactoryImpl() {
			super();
		}
		
		
		override public function createModel(dataModel:IDataModel):DiagramBaseModel {
			var newModel:DiagramBaseModel = super.createModel(dataModel);

			switch (dataModel.type) {
				case POINT_NODE:
					newModel = new PointNode((dataModel as BindingsDataModel).instance as IVirtualPoint);
					break;
				
				case SHAPE_NODE:
					newModel = new ShapeNode((dataModel as BindingsDataModel).instance as J2BaseModel);
					break;
				
				case TRANSLATOR_NODE:
					newModel = new TranslatorNode((dataModel as BindingsDataModel).instance as IPropertyTranslator);
					break;
			}
			
			return newModel;
		}
	}
}