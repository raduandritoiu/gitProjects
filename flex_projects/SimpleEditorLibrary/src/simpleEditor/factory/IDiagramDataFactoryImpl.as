package diagram.factory
{
	public interface IDiagramDataFactoryImpl
	{
		function createData(item:Object):IDataModel;
	}
}