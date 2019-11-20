package framework.view
{
	import framework.events.PointEvent;
	import framework.model.MobilityBindings;
	import framework.model.PointParticle;
	
	import mx.graphics.IStroke;
	import mx.graphics.SolidColorStroke;
	
	import spark.components.Group;
	import spark.primitives.Path;
	
	public class RectangleComp extends TriangleComp
	{
		private var _fourthPoint:PointParticle;
		
		
		public function RectangleComp() {
			super();
		}
		
		
		public function set fourthPoint(value:PointParticle):void {
			if (_fourthPoint != null) {
				return;
			}
			_fourthPoint = value;
			addPointParticle(value);
		}
		
		public function get fourthPoint():PointParticle {
			return _fourthPoint;
		}
		
		
		override protected function setAutoBindings():void {
			if (firstPoint == null || secondPoint == null || thirdPoint == null || fourthPoint == null) {
				return;
			}
			
			super.setAutoBindings();
			
			/* continue with autobinding */
			MobilityBindings.triangleBinding(secondPoint, thirdPoint, fourthPoint);
		}
		
		
		override protected function drawShape():void {
			if (firstPoint == null || secondPoint == null || thirdPoint == null || fourthPoint == null) {
				return;
			}
			x = firstPoint.x;
			y = firstPoint.y;
			line.data = "L " + (fourthPoint.x - firstPoint.x) + " " + (fourthPoint.y - firstPoint.y) + 
				" L " + (thirdPoint.x - firstPoint.x) + " " + (thirdPoint.y - firstPoint.y) + 
				" L " + (secondPoint.x - firstPoint.x) + " " + (secondPoint.y - firstPoint.y) + " Z";
		}
	}
}