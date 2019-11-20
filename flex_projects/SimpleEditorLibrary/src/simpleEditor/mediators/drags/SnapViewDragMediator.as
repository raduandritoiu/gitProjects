package diagram.mediators.drags
{
	import diagram.view.DiagramBaseView;
	import diagram.utils.Grid2D;
	
	import flash.geom.Point;
	
	import moccasin.view.ViewContext;
	
	public class SnapViewDragMediator extends DiagramViewDragMediator
	{
		public function SnapViewDragMediator(context:ViewContext, view:DiagramBaseView) {
			super(context, view);
		}
		
		
		override protected function getDragPosition(delta:Point):Point {
			var newPosition:Point = startPosition.add(delta);
			
			// snapp to grid
			newPosition.x = Math.round(newPosition.x / Grid2D.gridStep) * Grid2D.gridStep;
			newPosition.y = Math.round(newPosition.y / Grid2D.gridStep) * Grid2D.gridStep;
			
			return  newPosition;
		}
	}
}