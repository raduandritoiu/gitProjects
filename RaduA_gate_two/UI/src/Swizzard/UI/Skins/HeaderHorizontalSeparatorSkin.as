package Swizzard.UI.Skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;
	
	public class HeaderHorizontalSeparatorSkin extends ProgrammaticSkin
	{
		public function HeaderHorizontalSeparatorSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);
			var g:Graphics = this.graphics;
			g.clear();
			g.lineStyle(1,0xb1b1b1,0.8);
			g.moveTo(0,h+1);
			g.lineTo(w,h+1);
		}
		
	}
}