package Swizzard.UI.Skins
{
	import flash.filters.DropShadowFilter;
	
	import mx.skins.ProgrammaticSkin;
	
	public class AddButtonSkin extends ProgrammaticSkin
	{
		public function AddButtonSkin()
		{
			super();
	
		}
		
		private static var dsf:DropShadowFilter = new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 1, 3, true);
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w,h);
			
			graphics.clear();
			
			filters = [dsf];
			
			
			switch(name)
			{
				
				case "upSkin":
					
					
					graphics.beginGradientFill("linear", [0xf8f8f8, 0xe5e5e5, 0xebebeb, 0xf8f8f8], [1,1,1,1], [0, 127, 128, 255], horizontalGradientMatrix(0,0,w,h));
					graphics.drawRoundRect(0,0,w,h, 6, 6);
					graphics.endFill();
					
					break;
				
				case "overSkin":
					
					
					graphics.beginGradientFill("linear", [0xf8f8f8, 0xe5e5e5, 0xebebeb, 0xf8f8f8], [1,1,1,1], [0, 127, 128, 255], horizontalGradientMatrix(0,0,w,h));
					graphics.drawRoundRect(0,0,w,h, 6, 6);
					graphics.endFill();
					
					graphics.beginFill(0xffffff, 0.4);
					graphics.drawRoundRect(0,0,w,h, 6, 6);
					graphics.endFill();
					
					break;
				
				case "downSkin":
					
					graphics.beginGradientFill("linear", [0xf8f8f8, 0xe5e5e5, 0xebebeb, 0xf8f8f8], [1,1,1,1], [0, 127, 128, 255], horizontalGradientMatrix(0,0,w,h));
					graphics.drawRoundRect(0,0,w,h, 6, 6);
					graphics.endFill();
					
					graphics.beginGradientFill("linear", [0, 0], [0.1, 0], [0,255], verticalGradientMatrix(0,0,w,h));
					graphics.drawRoundRect(0,0,w,h, 6, 6);
					graphics.endFill();
					
					break;
				
				case "disabledSkin":
					break;
			}
		}
	}
}