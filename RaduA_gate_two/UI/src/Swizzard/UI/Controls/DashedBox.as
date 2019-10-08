package Swizzard.UI.Controls
{
	import com.senocular.drawing.DashedLine;
	import com.tonp.utils.Draw;
	
	import mx.core.UIComponent;
	
	
	public class DashedBox extends UIComponent
	{
		private var dl:DashedLine;
		private var oldH:Number;
		private var oldW:Number;


		public function DashedBox() {
			dl = new DashedLine(this, 5, 10);
			dl.lineStyle(2, 0, 0.1);
			
			super();
		}
		
		
		protected function drawLines():void {
			Draw.curvedBox(dl, width*0.3/2, height*0.1/2, width*0.7, height*0.87, 30);
		}
		
		
		override protected function updateDisplayList(w:Number, h:Number):void {
			super.updateDisplayList(w, h);
			
			if (oldW != w || oldH != h) {
				oldW = w;
				oldH = h;
				drawLines();
			}
		}
	}
}