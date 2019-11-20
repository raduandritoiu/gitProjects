package diagram.factory
{
	import diagram.view.DiagramBaseView;
	
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;

	public class DiagramViewFactory
	{
		private static var impl:IDiagramViewFactoryImpl = new DiagramViewFactoryImpl;
		
		
		public function DiagramViewFactory()
		{}
		
		
		public static function setImplementation(newImpl:IDiagramViewFactoryImpl):void {
			impl = newImpl;
		}
		
		
		public static function createView(context:ViewContext, model:MoccasinModel):DiagramBaseView {
			return impl.createView(context, model);
		}
	}
}