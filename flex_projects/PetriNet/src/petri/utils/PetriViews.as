package petri.utils
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import petri.mediators.PetriViewDragMediator;
	import petri.model.PetriArc;
	import petri.model.PetriBaseModel;
	import petri.model.PetriLayer;
	import petri.model.PetriState;
	import petri.model.PetriTransition;
	import petri.model.PetriWorld;
	import petri.view.PetriArcView;
	import petri.view.PetriLayerVIew;
	import petri.view.PetriStateView;
	import petri.view.PetriTransitionView;
	import petri.view.PetriView;
	import petri.view.PetriWorldView;

	public class PetriViews
	{
		public function PetriViews()
		{}
		
		
		public static function createView(context:ViewContext, model:MoccasinModel):PetriView {
			var newView:PetriView;
			
			if (model.value is PetriArc) {
				newView = new PetriArcView(context, model);
				newView.addMediator(new PetriViewDragMediator(context, newView));
			}
			else if (model.value is PetriState) {
				newView = new PetriStateView(context, model);
				newView.addMediator(new PetriViewDragMediator(context, newView));
			}
			else if (model.value is PetriTransition) {
				newView = new PetriTransitionView(context, model);
				newView.addMediator(new PetriViewDragMediator(context, newView));
			}
			else if (model.value is PetriWorld) {
				newView = new PetriWorldView(context, model);
				newView.addMediator(new PetriViewDragMediator(context, newView));
			}
			else if (model.value is PetriLayer) {
				newView = new PetriLayerVIew(context, model);
				newView.addMediator(new PetriViewDragMediator(context, newView));
			}
			// default base model
			else if (model.value is PetriBaseModel) {
				newView = new PetriView(context, model);
				newView.addMediator(new PetriViewDragMediator(context, newView));
			}
			
			return newView;
		}
	}
}