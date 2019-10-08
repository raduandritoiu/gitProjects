package Swizzard.UI.Skins
{
	import flash.filters.DropShadowFilter;
	
	import mx.skins.ProgrammaticSkin;
	
	public class CloseButtonSkin extends ProgrammaticSkin
	{
		private var upDropshadow:DropShadowFilter;
		
		public function CloseButtonSkin()
		{
			super();
			
			upDropshadow = new DropShadowFilter(1, 270, 0xffffff, 1, 0, 0, 1, 3);
			
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w,h);
			
			graphics.clear();
			
			
			switch(name) {
				
				case "upSkin":
					
					graphics.lineStyle(1, 0xececec, 1, true);
					graphics.moveTo(0,0);
					
					graphics.lineTo(w,h);
					
					graphics.moveTo(w,0);
					graphics.moveTo(0,h);
					
					this.filters = [upDropshadow];
					
					
					break;
					
				case "overSkin":
					
					
					break;
				
				case "downSkin":
					
					break;
				
			}
		}
		
	}
}