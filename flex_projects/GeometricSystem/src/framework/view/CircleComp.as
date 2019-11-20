package framework.view
{
	import framework.events.PointEvent;
	import framework.model.PointParticle;
	
	import mx.graphics.IStroke;
	import mx.graphics.SolidColorStroke;
	
	import spark.components.Group;
	import spark.primitives.Ellipse;
	
	public class CircleComp extends AbstractComp
	{
		private var _centerPoint:PointParticle;
		private var _radius:Number = 10;
		
		private var radiusChanged:Boolean = false;
		
		private var circle:Ellipse;
		
		
		public function CircleComp() {
			super();
		}
		
		
		public function set radius(value:Number):void {
			_radius = value;
			radiusChanged = true;
			invalidateProperties();
		}
		
		public function get radius():Number {
			return _radius;
		}
		
		
		public function set centerPoint(value:PointParticle):void {
			if (_centerPoint != null) {
				return;
			}
			_centerPoint = value;
			addPointParticle(value);
		}
		
		public function get centerPoint():PointParticle {
			return _centerPoint;
		}
		
		
		override protected function drawShape():void {
			if (centerPoint == null) {
				return;
			}
			x = centerPoint.x;
			y = centerPoint.y;
		}
		
		
		override protected function commitProperties():void {
			super.commitProperties();
			
			if (radiusChanged) {
				radiusChanged = false;
				circle.x = -radius;
				circle.y = -radius;
				circle.width = 2 * radius;
				circle.height = 2 * radius;
				invalidateDisplayList();
			}
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (circle == null) {
				circle = new Ellipse();
				circle.x = -radius;
				circle.y = -radius;
				circle.width = 2 * radius;
				circle.height = 2 * radius;
				circle.stroke = stroke;
				addElement(circle);
			}
		}
	}
}