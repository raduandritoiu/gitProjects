package Swizzard.UI.Controls
{
	import spark.primitives.Rect;
	
	public class HorizontalRect extends Rect
	{
		public function HorizontalRect() {
			super();
		}
		
		override public function get radiusX():Number {
			return height/2;
		}
		override public function get radiusY():Number {
			return height/2;
		}
	}
}