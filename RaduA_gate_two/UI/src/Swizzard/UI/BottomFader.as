package Swizzard.UI
{
	import mx.core.UIComponent;
	import flash.geom.Matrix;
	

	public class BottomFader extends UIComponent
	{
		public function BottomFader()
		{
			super();
		}
		
		
		override protected function updateDisplayList(w:Number, h:Number):void 
		{
			super.updateDisplayList(w,h);
			
			graphics.clear();
			
			var fadeHeight:Number = 150;
			
			var m:Matrix = new Matrix();
			m.createGradientBox(w,h,Math.PI/2);
			
			graphics.beginGradientFill("linear", [0xdbe8f0, 0xdbe8f0], [0,1], [235,253], m);
			graphics.drawRoundRectComplex(0, 0, w, h, 0, 0, 10, 10);
		}
	}
}