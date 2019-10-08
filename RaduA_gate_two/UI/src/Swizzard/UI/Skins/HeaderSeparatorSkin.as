package Swizzard.UI.Skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;
	
	public class HeaderSeparatorSkin extends ProgrammaticSkin
	{
		public function HeaderSeparatorSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);
			var g:Graphics = this.graphics;
			g.clear();
			g.lineStyle(1,0xb1b1b1,0.8);
			g.moveTo(w+1,0);
			g.lineTo(w+1,h);
		}
		
		
	}
}