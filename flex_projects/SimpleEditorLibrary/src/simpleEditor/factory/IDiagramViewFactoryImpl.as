package diagram.factory
{
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;
	
	import diagram.view.DiagramBaseView;
	
	
	public interface IDiagramViewFactoryImpl
	{
		function createView(context:ViewContext, model:MoccasinModel):DiagramBaseView;
	}
}