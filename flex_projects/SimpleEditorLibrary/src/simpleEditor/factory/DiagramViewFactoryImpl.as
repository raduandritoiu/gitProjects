package diagram.factory
{
	import diagram.mediators.drags.DiagramViewDragMediator;
	import diagram.mediators.drags.SnapViewDragMediator;
	import diagram.mediators.drags.SlotViewDragMediator;
	import diagram.model.DiagramBaseModel;
	import diagram.model.DiagramBorderSlot;
	import diagram.model.DiagramLink;
	import diagram.model.DiagramMasterNode;
	import diagram.model.DiagramNode;
	import diagram.model.DiagramSlot;
	import diagram.model.DiagramWorld;
	import diagram.view.DiagramBaseView;
	import diagram.view.DiagramBorderSlotView;
	import diagram.view.DiagramLinkView;
	import diagram.view.DiagramMasterNodeView;
	import diagram.view.DiagramNodeView;
	import diagram.view.DiagramSlotView;
	import diagram.view.DiagramWorldView;
	
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;
	
	
	public class DiagramViewFactoryImpl implements IDiagramViewFactoryImpl
	{
		public function DiagramViewFactoryImpl() {
		}
		
		
		public function createView(context:ViewContext, model:MoccasinModel):DiagramBaseView {
			var newView:DiagramBaseView;
			
			if (model.value is DiagramBorderSlot) {
				newView = new DiagramBorderSlotView(context, model);
				newView.addDragMediator(new SlotViewDragMediator(context, newView));
			}
			
			else if (model.value is DiagramMasterNode) {
				newView = new DiagramMasterNodeView(context, model);
				newView.addDragMediator(new SnapViewDragMediator(context, newView));
			}
			
			else if (model.value is DiagramNode) {
				newView = new DiagramNodeView(context, model);
				newView.addDragMediator(new SnapViewDragMediator(context, newView));
			}
			
			else if (model.value is DiagramSlot) {
				newView = new DiagramSlotView(context, model);
				newView.addDragMediator(new SlotViewDragMediator(context, newView));
			}
			
			else if (model.value is DiagramLink) {
				newView = new DiagramLinkView(context, model);
			}
			
			else if (model.value is DiagramWorld) {
				newView = new DiagramWorldView(context, model);
				newView.addDragMediator(new DiagramViewDragMediator(context, newView));
			}
			
			// default base model
			else if (model.value is DiagramBaseModel) {
				newView = new DiagramBaseView(context, model);
				newView.addDragMediator(new DiagramViewDragMediator(context, newView));
			}
			
			return newView;
		}
	}
}