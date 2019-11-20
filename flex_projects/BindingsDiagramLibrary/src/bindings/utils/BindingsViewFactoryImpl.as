package bindings.utils
{
	import bindings.mediators.drags.BindingsSlotDragMediator;
	import bindings.model.PointNode;
	import bindings.model.ShapeNode;
	import bindings.model.TranslatorNode;
	import bindings.view.PointNodeView;
	import bindings.view.ShapeNodeView;
	import bindings.view.TranslatorNodeView;
	
	import diagram.mediators.drags.DiagramViewDragMediator;
	import diagram.model.DiagramSlot;
	import diagram.utils.DiagramViewFactoryImpl;
	import diagram.view.DiagramBaseView;
	import diagram.view.DiagramSlotView;
	
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;
	
	
	public class BindingsViewFactoryImpl extends DiagramViewFactoryImpl
	{
		public function BindingsViewFactoryImpl() {
			super();
		}
		
		
		override public function createView(context:ViewContext, model:MoccasinModel):DiagramBaseView {
			var newView:DiagramBaseView;
			
			if (model.value is PointNode) {
				newView = new PointNodeView(context, model);
				newView.addMediator(new DiagramViewDragMediator(context, newView));
			}
			else if (model.value is ShapeNode) {
				newView = new ShapeNodeView(context, model);
				newView.addMediator(new DiagramViewDragMediator(context, newView));
			}
			else if (model.value is TranslatorNode) {
				newView = new TranslatorNodeView(context, model);
				newView.addMediator(new DiagramViewDragMediator(context, newView));
			}
//			else if (model.value is DiagramSlot) {
//				newView = new DiagramSlotView(context, model);
//				newView.addMediator(new BindingsSlotDragMediator(context, newView));
//			}
			
			// default super
			else {
				newView = super.createView(context, model);
			}
			
			return newView;
		}
	}
}