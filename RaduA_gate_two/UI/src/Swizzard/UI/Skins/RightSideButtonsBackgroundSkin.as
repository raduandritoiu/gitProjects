package Swizzard.UI.Skins
{
	import mx.skins.ProgrammaticSkin;
	
	public class RightSideButtonsBackgroundSkin extends ProgrammaticSkin
	{
		public function RightSideButtonsBackgroundSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w,h);
			
			graphics.clear();
			
			//border
			graphics.beginFill(0xb7babc);
			graphics.drawRoundRectComplex(0,0,w,h, 0, 3, 0, 3);
			graphics.endFill();
			
			//fill
			graphics.beginGradientFill("linear", [0xe5e5e5, 0xf4f4f4, 0xf4f4f4, 0xededed, 0xffffff], [1,1,1,1,1], [0, 40, 127, 128, 255], horizontalGradientMatrix(0, 1, w-1, h-2));
			graphics.drawRoundRectComplex(0, 1, w-1, h-2, 0, 3, 0, 3);
			graphics.endFill();
			
		}
	}
}