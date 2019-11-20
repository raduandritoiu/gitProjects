package framework.view
{
	import framework.model.MobilityBindings;
	import framework.model.PointParticle;
	
	import spark.primitives.Path;
	
	
	public class LineComp extends AbstractComp
	{
		private var _firstPoint:PointParticle;
		private var _secondPoint:PointParticle;
		
		protected var line:Path;
		
		
		public function LineComp() {
			super();
		}
		
		
		public function set firstPoint(value:PointParticle):void {
			if (_firstPoint != null) {
				return;
			}
			_firstPoint = value;
			addPointParticle(value);
		}
		
		public function get firstPoint():PointParticle {
			return _firstPoint;
		}
		

		public function set secondPoint(value:PointParticle):void {
			if (_secondPoint != null) {
				return;
			}
			_secondPoint = value;
			addPointParticle(value);
		}
		
		public function get secondPoint():PointParticle {
			return _secondPoint;
		}
		
		
		override protected function setAutoBindings():void {
			if (firstPoint == null || secondPoint == null) {
				return;
			}
			
			/* to set autobinding */
			MobilityBindings.lineBinding(firstPoint, secondPoint);
		}
		
		
		override protected function drawShape():void {
			if (firstPoint == null || secondPoint == null) {
				return;
			}
			x = firstPoint.x;
			y = firstPoint.y;
			line.data = "L " + (secondPoint.x - firstPoint.x) + " " + (secondPoint.y - firstPoint.y);
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