package Swizzard.UI.Controls
{
	import com.senocular.drawing.DashedLine;
	import com.tonp.utils.Draw;
	
	import mx.core.UIComponent;
	
	
	/** 
	 * Because the drawn boxes have hardcoded sizes we recomend 
	 * setting width to 470, height to 70 and center this component.
	 */
	public class DaskedBoxes extends UIComponent
	{
		private var dl:DashedLine;
		private var oldH:Number;
		private var oldW:Number;
		
		
		public function DaskedBoxes() {
			dl = new DashedLine(this, 5, 10);
			dl.lineStyle(2, 0, 0.1);
			super();
		}
		
		
		protected function drawLines():void {
			var boxCount:uint		= 5;
			var boxSize:Number		= 70;
			var boxPadding:Number 	= 30;
			var boxStart:Number	= (width - (((boxSize + boxPadding) * boxCount) - boxPadding)) / 2; 
			
			for (var i:uint = 0; i < boxCount; i++)	{
				var currentX:Number	= boxStart + (i * (boxSize + boxPadding));
				Draw.curvedBox(dl, currentX, (height - boxSize) / 2, boxSize, boxSize, 10);
			}
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