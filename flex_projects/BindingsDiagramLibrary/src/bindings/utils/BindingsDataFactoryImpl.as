package bindings.utils
{
	import diagram.utils.DiagramDataFactoryImpl;
	import diagram.utils.IDataModel;
	
	import j2inn.builder.mapping.interfaces.IPropertyTranslator;
	import j2inn.builder.mapping.interfaces.IVirtualPoint;
	import j2inn.builder.model.J2BaseModel;
	
	
	public class BindingsDataFactoryImpl extends DiagramDataFactoryImpl
	{
		public function BindingsDataFactoryImpl() {
			super();
		}
		
		
		override public function createData(item:Object):IDataModel {
			if (item is J2BaseModel) {
				return new BindingsDataModel("test", BindingsModelFactoryImpl.SHAPE_NODE, item);
			}
			if (item is IVirtualPoint) {
				return new BindingsDataModel("test", BindingsModelFactoryImpl.POINT_NODE, item);
			}
			if (item is IPropertyTranslator) {
				return new BindingsDataModel("test", BindingsModelFactoryImpl.TRANSLATOR_NODE, item);
			}
			
			return super.createData(item);
		}
	}
}