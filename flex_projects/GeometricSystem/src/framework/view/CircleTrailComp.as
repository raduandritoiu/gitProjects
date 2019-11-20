package framework.view
{
	import mx.core.IVisualElement;
	import mx.core.IVisualElementContainer;
	
	import spark.primitives.Ellipse;

	public class CircleTrailComp extends CircleComp
	{
		public function CircleTrailComp() {
			super();
		}
		
		
		public function trail():IVisualElement {
			var newCircle:Ellipse = new Ellipse();
			newCircle.width = radius * 2;
			newCircle.height = radius * 2;
			newCircle.stroke = stroke;
			newCircle.x = x - radius;
			newCircle.y = y - radius;
			
			return newCircle;
		}
		
		override protected function drawShape():void {
			if (centerPoint == null) {
				return;
			}
			var newTrail:IVisualElement = trail();
			(parent as IVisualElementContainer).addElement(newTrail);
			super.drawShape();
		}
		
		
	}
}