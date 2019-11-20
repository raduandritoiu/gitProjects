package framework.view
{
	import framework.events.PointEvent;
	import framework.model.PointParticle;
	
	import mx.graphics.IStroke;
	import mx.graphics.SolidColorStroke;
	
	import spark.components.Group;
	import spark.primitives.Ellipse;
	
	public class AbstractComp extends Group
	{
		private var _stroke:IStroke = new SolidColorStroke();
		
		private var strokeChanged:Boolean = false;
		private var pointsChanged:Boolean = false;
		
		
		public function AbstractComp() {
			super();
		}
		
		
		public function set stroke(value:IStroke):void {
			_stroke = value;
			strokeChanged = true;
			invalidateProperties();
		}
		
		public function get stroke():IStroke {
			return _stroke;
		}
		
		
		protected function addPointParticle(point:PointParticle):void {
			point.addEventListener(PointEvent.MOVED, pointMoved);
			setAutoBindings();
			pointsChanged = true;
			invalidateProperties();
		}
			
		
		protected function pointMoved(evt:PointEvent):void {
			pointsChanged = true;
			invalidateProperties();
		}
		
		
		protected function setAutoBindings():void {
		}
		
		
		protected function drawShape():void {
		}
		
		
		protected function applyStroke():void {
		}
		
		
		override protected function commitProperties():void {
			super.commitProperties();
			
			if (pointsChanged) {
				pointsChanged = false;
				drawShape();
			}
			
			if (strokeChanged) {
				strokeChanged = false;
				applyStroke();
			}
		}
	}
}