package diagram.factory
{
	public class DiagramDataFactory
	{
		private static var impl:IDiagramDataFactoryImpl = new DiagramDataFactoryImpl();
		
		
		public function DiagramDataFactory() 
		{}
		
		
		public static function setImplementation(value:IDiagramDataFactoryImpl):void {
			impl = value;
		}
		
		
		public static function createData(item:Object):IDataModel {
			return impl.createData(item);
		}
	}
}