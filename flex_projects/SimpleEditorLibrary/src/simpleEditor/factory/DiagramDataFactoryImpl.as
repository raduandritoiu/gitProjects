package diagram.factory
{
	import diagram.model.DiagramNode;

	public class DiagramDataFactoryImpl implements IDiagramDataFactoryImpl
	{
		public function DiagramDataFactoryImpl() {
		}
		
		public function createData(item:Object):IDataModel {
			if (item is IDataModel) {
				return item as IDataModel;
			}
			return new DataModel("", DiagramModelFactoryImpl.NODE);
		}
	}
}