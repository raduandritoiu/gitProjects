package diagram.mediators.drags
{
	import diagram.model.DiagramSlot;
	import diagram.view.DiagramBaseView;
	
	import flash.events.MouseEvent;
	
	import moccasin.view.ViewContext;
	
	public class SlotViewDragMediator extends SnapViewDragMediator
	{
		public function SlotViewDragMediator(context:ViewContext, view:DiagramBaseView) {
			super(context, view);
		}
		
		
		override public function handleViewEvents():void {
			super.handleViewEvents();
			diagramView.addEventListener(MouseEvent.ROLL_OVER, handleMouseOver, true, 0, true);
			diagramView.addEventListener(MouseEvent.ROLL_OUT, handleMouseOut, true, 0, true);
		}
		
		
		override public function removeViewEvents():void {
			super.removeViewEvents();
			diagramView.removeEventListener(MouseEvent.MOUSE_DOWN, handleMouseOver, true);
			diagramView.removeEventListener(MouseEvent.MOUSE_DOWN, handleMouseOut, true);
		}
		
		
		protected function handleMouseOver(evt:MouseEvent):void {
			diagramController.currentSlot = diagramView.diagramModel as DiagramSlot;
		}
		
		
		protected function handleMouseOut(evt:MouseEvent):void {
			if (diagramController.currentSlot == diagramView.diagramModel) {
				diagramController.currentSlot = null;
			}
		}
	}
}