package framework.view
{
	import framework.model.MobilityBindings;
	import framework.model.PointParticle;
	
	import mx.graphics.SolidColorStroke;
	
	import spark.primitives.Path;
	
	public class JointComp extends LineComp
	{
		private var _thirdPoint:PointParticle;
		
		
		public function JointComp() {
			super();
			stroke = new SolidColorStroke(0x00ff00, 2);
		}
		
		
		public function set thirdPoint(value:PointParticle):void {
			if (_thirdPoint != null) {
				return;
			}
			_thirdPoint = value;
			addPointParticle(value);
		}
		
		public function get thirdPoint():PointParticle {
			return _thirdPoint;
		}
		
		
		override protected function setAutoBindings():void {
			if (firstPoint == null || thirdPoint == null || secondPoint == null) {
				return;
			}
			
			/* to set autobinding */
			MobilityBindings.triangleBinding(firstPoint, secondPoint, thirdPoint);
		}
		

		override protected function drawShape():void {
			if (firstPoint == null || _thirdPoint == null || secondPoint == null) {
				return;
			}
			x = firstPoint.x;
			y = firstPoint.y;
			line.data = "L " + (secondPoint.x - firstPoint.x) + " " + (secondPoint.y - firstPoint.y) + 
				" L " + (_thirdPoint.x - firstPoint.x) + " " + (thirdPoint.y - firstPoint.y);
		}
		
		
		override protected function applyStroke():void {
			if (line != null) {
				line.stroke = stroke;
			}
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (line == null) {
				line = new Path();
				line.stroke = stroke;
				addElement(line);
			}
		}
	}
} 