package petri.view
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.IMoccasinView;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import petri.utils.PetriViews;
	
	public class PetriContainerView extends PetriView
	{
		public function PetriContainerView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		override public function createChildView(child:MoccasinModel):IMoccasinView {
			var newView:PetriView = PetriViews.createView(context, child);
			controller.viewAdded(newView);
			return newView;
		}
	}
}