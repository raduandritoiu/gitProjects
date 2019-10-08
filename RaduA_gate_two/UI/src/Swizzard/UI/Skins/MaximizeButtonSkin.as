package Swizzard.UI.Skins
{
	
	import flash.display.CapsStyle;
	import flash.display.JointStyle;
	import flash.filters.DropShadowFilter;
	
	import mx.skins.ProgrammaticSkin;
	
	public class MaximizeButtonSkin extends ProgrammaticSkin
	{
		public function MaximizeButtonSkin()
		{
			super();
			this.filters = [new DropShadowFilter(1, 90, 0xffffff, 1, 0,0)];
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w,h);
			
			var offset:Number = 3;
			
			graphics.clear();
			
			switch (name) {
				case "upSkin":
					graphics.lineStyle(1, 0x757575, 1, false);
					
					graphics.drawRect(offset, offset, w - offset*2, h - offset*2);
					
					graphics.lineStyle();
					
					graphics.beginFill(0x757575);
					graphics.drawRect(offset, offset+1, w - offset*2, 1);		
					graphics.endFill();
					break;
				
				case "overSkin":
					graphics.lineStyle(1, 0x999999, 1, false);
					
					graphics.drawRect(offset, offset, w - offset*2, h - offset*2);
					
					graphics.lineStyle();
					
					graphics.beginFill(0x999999);
					graphics.drawRect(offset, offset+1, w - offset*2, 1);		
					graphics.endFill();
					break;
				
				case "downSkin":
					graphics.lineStyle(1, 0x454545, 1, false);
					
					graphics.drawRect(offset, offset, w - offset*2, h - offset*2);
					
					graphics.lineStyle();
					
					graphics.beginFill(0x454545);
					graphics.drawRect(offset, offset+1, w - offset*2, 1);		
					graphics.endFill();
					break;
			}
			
			//hit area
			graphics.beginFill(0,0);
			graphics.drawRect(0,0,w,h);
			graphics.endFill();
		}
	}
}