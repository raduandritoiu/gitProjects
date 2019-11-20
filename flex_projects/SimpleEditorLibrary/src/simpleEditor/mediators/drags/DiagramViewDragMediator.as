package diagram.mediators.drags
{
	import diagram.controller.DiagramController;
	import diagram.mediators.IViewMediator;
	import diagram.model.DiagramBaseModel;
	import diagram.view.DiagramBaseView;
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import moccasin.controller.DragMediator;
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;
	
	
	public class DiagramViewDragMediator extends DragMediator implements IViewMediator
	{
		protected var diagramView:DiagramBaseView;
		protected var startPosition:Point;
		
		
		public function DiagramViewDragMediator(context:ViewContext, view:DiagramBaseView)	{
			super(context);
			diagramView = view;
		}
		
		
		protected function get diagramController():DiagramController {
			return context.controller as DiagramController;
		}
		
		
		public function handleViewEvents():void {
			diagramView.addEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown, false, 0, true);
		}
		
		
		public function removeViewEvents():void {
			diagramView.removeEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown, false);
		}
		
		
		override protected function handleDragStart(e:MouseEvent):void {
			setStartPosition(new Point(diagramView.diagramModel.x, diagramView.diagramModel.y));
			
			for each(var model:MoccasinModel in context.controller.selection.selectedModels) {
				var diagramModel:DiagramBaseModel = model.value as DiagramBaseModel;
				var mediator:DiagramViewDragMediator = diagramController.getView(diagramModel.uid).getDragMediator();
				if (mediator != null && mediator != this) {
					mediator.setStartPosition(new Point(diagramModel.x, diagramModel.y));
				}
			}
		}
		
		
		override protected function handleDragMove(e:MouseEvent):void {
			if (startPosition == null)
				return;
			
			var newPos:Point = getDragPosition(dragDelta);
			diagramView.diagramModel.x = newPos.x;
			diagramView.diagramModel.y = newPos.y;
			
			for each(var model:MoccasinModel in context.controller.selection.selectedModels) {
				var diagramModel:DiagramBaseModel = model.value as DiagramBaseModel;
				var mediator:DiagramViewDragMediator = diagramController.getView(diagramModel.uid).getDragMediator();
				if (mediator != null && mediator != this) {
					var newPos:Point = mediator.getDragPosition(dragDelta);
					diagramModel.x = newPos.x;
					diagramModel.y = newPos.y;
				}
			}
		}
		
		
		override protected function handleDragEnd(e:MouseEvent):void {
			startPosition = null;
			
			for each(var model:MoccasinModel in context.controller.selection.selectedModels) {
				var mediator:DiagramViewDragMediator = diagramController.getView(model.value.uid).getDragMediator();
				if (mediator != null && mediator != this) {
					mediator.startPosition = null;
				}
			}
		}
		
		
		protected function setStartPosition(point:Point):void {
			startPosition = point;
		}
		
		
		protected function getDragPosition(delta:Point):Point {
			return startPosition.add(delta);
		}
	}
}