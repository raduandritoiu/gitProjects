package Swizzard.UI.Skins
{
	import mx.core.UIComponent;
	import mx.skins.ProgrammaticSkin;
	
	public class JSeperatorSkin extends ProgrammaticSkin
	{
		
		override public function get measuredHeight():Number
		{
			return 3;
		}
		
		override public function get measuredWidth():Number
		{
			return UIComponent.DEFAULT_MEASURED_MIN_WIDTH;
		}	
		
		public function JSeperatorSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);
			
			graphics.clear();
			
			graphics.lineStyle(1, 0xffffff, 0.6);
			graphics.moveTo(0, 0);
			graphics.lineTo(w, 0);
			graphics.lineStyle(1, 0x98A2A7, 0.8);
			graphics.moveTo(0, 1);
			graphics.lineTo(w, 1);
			graphics.lineStyle(1, 0xffffff, 0.6);
			graphics.moveTo(0, 2);
			graphics.lineTo(w, 2);
		}
	}
}