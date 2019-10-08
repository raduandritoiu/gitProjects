package Swizzard.UI.Skins
{
	import mx.skins.ProgrammaticSkin;
	
	public class HeaderSkin extends ProgrammaticSkin
	{
		public function HeaderSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);
			
			with(graphics)
			{
				clear();
				
				beginGradientFill("linear", [0x73787a, 0x212121], [1,1], [0,255], verticalGradientMatrix(0,0,w,h));
				
				drawRect(0,0,w,h);
				endFill();
			}
		}
	}
}