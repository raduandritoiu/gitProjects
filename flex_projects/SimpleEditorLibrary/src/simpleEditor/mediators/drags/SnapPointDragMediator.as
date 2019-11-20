package diagram.mediators.drags
{
	import diagram.controller.DiagramController;
	import diagram.editors.feedback.PointResizeHandle;
	import diagram.model.DiagramBaseModel;
	import diagram.utils.Grid2D;
	
	import flash.geom.Point;
	
	import moccasin.view.ViewContext;
	
	public class SnapPointDragMediator extends PointDragMediator
	{
		public function SnapPointDragMediator(context:ViewContext, point:PointResizeHandle, model:DiagramBaseModel, propertyName:String) {
			super(context, point, model, propertyName);
		}
		
		
		override protected function get dragPosition():Point {
			var newPosition:Point = startPosition.add(dragDelta);
			
			newPosition.x = Math.round(newPosition.x / Grid2D.gridStep) * Grid2D.gridStep;
			newPosition.y = Math.round(newPosition.y / Grid2D.gridStep) * Grid2D.gridStep;
			
			return  newPosition;
		}
	}
}