package framework.view
{
	import framework.events.PointEvent;
	import framework.model.PointParticle;
	
	import mx.graphics.IStroke;
	import mx.graphics.SolidColorStroke;
	
	import spark.components.Group;
	import spark.primitives.Path;
	
	public class TriangleComp extends JointComp
	{
		public function TriangleComp() {
			super();
		}
		
		
		override protected function drawShape():void {
			if (firstPoint == null || secondPoint == null || thirdPoint == null) {
				return;
			}
			x = firstPoint.x;
			y = firstPoint.y;
			line.data = "L " + (thirdPoint.x - firstPoint.x) + " " + (thirdPoint.y - firstPoint.y) + 
				" L " + (secondPoint.x - firstPoint.x) + " " + (secondPoint.y - firstPoint.y) + " Z";
		}
	}
}