package Swizzard.UI.Skins
{
	import mx.skins.ProgrammaticSkin;
	
	public class VerticalLockedSeparatorSkin extends ProgrammaticSkin
	{
		public function VerticalLockedSeparatorSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w,h);
			
			graphics.clear();
			
			graphics.beginFill(0, 0.3);
			graphics.drawRect(0,0,1,h);
			graphics.endFill();
		}
	}
}