package Swizzard.UI.Skins
{
	import flash.filters.DropShadowFilter;
	
	import mx.skins.ProgrammaticSkin;
	
	public class MinimizeButtonSkin extends ProgrammaticSkin
	{
		public function MinimizeButtonSkin()
		{
			super();
			this.filters = [new DropShadowFilter(1, 90, 0xffffff, 1, 0,0)];
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w,h);
			
			var offset:Number = 3;
			
			graphics.clear();
			
			//hit area
			graphics.beginFill(0, 0);
			graphics.drawRect(0, 0, w, h);
			graphics.endFill();
		
			
			switch(name) {
				
				case "upSkin":
					
					graphics.beginFill(0x757575);
					
					graphics.drawRect(offset,h-5,w-offset*2, 2);
					
					graphics.endFill();
					
					break;
				
				
				case "overSkin":
					
					graphics.beginFill(0x999999);
					
					graphics.drawRect(offset,h-5,w-offset*2, 2);
					
					graphics.endFill();
					
					break;
				
				case "downSkin":
					
					graphics.beginFill(0x454545);
					
					graphics.drawRect(offset,h-5,w-offset*2, 2);
					
					graphics.endFill();
					
					break;
				
				
			}
			
			
			
			
		}
	}
}