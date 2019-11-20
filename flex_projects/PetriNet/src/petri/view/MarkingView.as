package petri.view
{
	import flash.geom.Point;
	
	import mx.events.PropertyChangeEvent;
	import mx.graphics.SolidColor;
	import mx.graphics.SolidColorStroke;
	
	import petri.model.Marking;
	
	import spark.components.Group;
	import spark.components.Label;
	import spark.primitives.Rect;
	
	public class MarkingView extends Group
	{
		private var _model:Marking;
		
		private var bgRect:Rect;
		private var currentRect:Rect;
		private var initialRect:Rect;
		private var label:Label;
		
		private static const W:Number = 80;
		private static const H:Number = 30;
		
		
		public function MarkingView(model:Marking) {
			super();
			_model = model;
			_model.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, markingChanged, false, 0, true);
		}
		
		
		public function get marking():Marking {
			return _model;
		}
		
		
		protected function markingChanged(evt:PropertyChangeEvent):void {
			switch (evt.property.toString()) {
				case "current":
					currentRect.visible = marking.current;
					break;
			}
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (bgRect == null) {
				bgRect = new Rect();
				bgRect.width = W;
				bgRect.height = H;
				bgRect.radiusX = 10;
				bgRect.radiusY = 10;
				bgRect.fill = new SolidColor(0xf0fff0);
				bgRect.stroke = new SolidColorStroke(0, 1.5);
				addElement(bgRect);
			}
			
			if (currentRect == null) {
				currentRect = new Rect();
				currentRect.width = W;
				currentRect.height = H;
				currentRect.radiusX = 10;
				currentRect.radiusY = 10;
				currentRect.fill = new SolidColor(0, 0);
				currentRect.stroke = new SolidColorStroke(0xff0000, 2.5);
				currentRect.visible = marking.current;
				addElement(currentRect);
			}
			
			if (label == null) {
				label = new Label();
				label.horizontalCenter = 0;
				label.verticalCenter = 0;
				label.text = marking.code;
				addElement(label);
			}
			
		}
		
		
		public function get up():Point {
			return new Point(x + W/2, y);
		}
		
		
		public function get down():Point {
			return new Point(x + W/2, y + H);
		}
		
		
		public function get rightUp():Point {
			return new Point(x + W, y + 4);
		}
		
		
		public function get rightDown():Point {
			return new Point(x + W, y + H-4);
		}
		
		
		public function get leftUp():Point {
			return new Point(x, y + 4);
		}
		
		
		public function get leftDown():Point {
			return new Point(x, y + H-4);
		}
	}
}