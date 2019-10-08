package Swizzard.UI.Skins
{
	import flash.display.CapsStyle;
	import flash.display.JointStyle;
	import flash.filters.DropShadowFilter;
	
	import mx.skins.ProgrammaticSkin;
	
	import org.alivepdf.drawing.Caps;
	
	public class AppCloseButtonSkin extends ProgrammaticSkin
	{
		public function AppCloseButtonSkin()
		{
			super();
			this.filters = [new DropShadowFilter(1, 90, 0xffffff, 1, 0,0)];
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w,h);
			
			var offset:Number = 4;
			
			graphics.clear();
			
			//hit area
			graphics.beginFill(0,0);
			graphics.drawRect(0,0,w,h);
			
			switch(name) {
				
				case "upSkin":
					graphics.lineStyle(2, 0x757575, 1, true, "normal");
					
					graphics.moveTo(offset, offset);
					graphics.lineTo(w-offset, h-offset);
					
					graphics.moveTo(w-offset, offset);
					graphics.lineTo(offset, h-offset);
					break;
				
				case "overSkin":
					graphics.lineStyle(2, 0x999999, 1, true, "normal");
					
					graphics.moveTo(offset, offset);
					graphics.lineTo(w-offset, h-offset);
					
					graphics.moveTo(w-offset, offset);
					graphics.lineTo(offset, h-offset);
					break;
				
				case "downSkin":
					graphics.lineStyle(2, 0x454545, 1, true, "normal");
					
					graphics.moveTo(offset, offset);
					graphics.lineTo(w-offset, h-offset);
					
					graphics.moveTo(w-offset, offset);
					graphics.lineTo(offset, h-offset);
					break;
			}
			graphics.endFill();
		}
	}
}