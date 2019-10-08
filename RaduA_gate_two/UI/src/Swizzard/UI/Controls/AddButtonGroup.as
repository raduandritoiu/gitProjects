package Swizzard.UI.Controls
{
	import spark.components.VGroup;
	
	// RADU: Thought of making this class and adding the drawing directly inside the updateDisplayList()
	// instead of making a Group or a SkinnableComponent with a Rectangle and a VGroup inside
	public class AddButtonGroup extends VGroup
	{
		public function AddButtonGroup() {
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void {
			super.updateDisplayList(w, h);
			
			graphics.clear();
			
			//border
			graphics.beginFill(0xb7babc);
			graphics.drawRoundRectComplex(0, 0, w, h, 0, 3, 0, 3);
			graphics.endFill();
			
			//fill
			graphics.beginGradientFill("linear", [0xe5e5e5, 0xf4f4f4, 0xf4f4f4, 0xededed, 0xffffff], [1,1,1,1,1], [0, 40, 127, 128, 255], horizontalGradientMatrix(0, 1, w-1, h-2));
			graphics.drawRoundRectComplex(0, 1, w-1, h-2, 0, 3, 0, 3);
			graphics.endFill();
		}
	}
}