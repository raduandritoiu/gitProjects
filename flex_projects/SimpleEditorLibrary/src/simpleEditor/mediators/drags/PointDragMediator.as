package diagram.mediators.drags
{
	import diagram.editors.feedback.PointResizeHandle;
	import diagram.mediators.IViewMediator;
	import diagram.model.DiagramBaseModel;
	import diagram.utils.Grid2D;
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import moccasin.controller.DragMediator;
	import moccasin.view.ViewContext;
	
	
	public class PointDragMediator extends DragMediator implements IViewMediator
	{
		protected var pointView:PointResizeHandle;
		protected var diagramModel:DiagramBaseModel;
		protected var property:String;
		protected var startPosition:Point;
		
		
		public function PointDragMediator(context:ViewContext, point:PointResizeHandle, model:DiagramBaseModel, propertyName:String) {
			super(context);
			pointView = point;
			diagramModel = model;
			property = propertyName;
		}
		
		
		public function handleViewEvents():void {
			pointView.addEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown, false, 0, true);
		}
		
		
		public function removeViewEvents():void {
			pointView.removeEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown, false);
		}
		
		
		override protected function handleDragStart(e:MouseEvent):void {
			if (diagramModel.hasOwnProperty(property))
				startPosition = diagramModel[property];
		}
		
		
		override protected function handleDragMove(e:MouseEvent):void {
			if (startPosition == null)
				return;
			
			var newPosition:Point = dragPosition;
			if (diagramModel.hasOwnProperty(property))
				diagramModel[property] = newPosition;
		}
		
		
		override protected function handleDragEnd(e:MouseEvent):void {
			startPosition = null;
		}
		
		
		protected function get dragPosition():Point {
			return startPosition.add(dragDelta);
		}
	}
}