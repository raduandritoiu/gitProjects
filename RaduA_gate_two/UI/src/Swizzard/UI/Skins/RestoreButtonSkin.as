package Swizzard.UI.Skins
{
	
	import flash.display.BitmapData;
	import flash.display.GraphicsPath;
	import flash.display.GraphicsPathWinding;
	import flash.display.IGraphicsData;
	import flash.display.Shape;
	import flash.filters.DropShadowFilter;
	import flash.geom.Matrix;
	
	import mx.skins.ProgrammaticSkin;
	
	public class RestoreButtonSkin extends ProgrammaticSkin
	{
		public function RestoreButtonSkin()
		{
			super();
			this.filters = [new DropShadowFilter(1, 90, 0xffffff, 1, 0,0)];
			
			
		}
	
	
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w,h);
			
			var offset:Number = 0;
			graphics.clear();
			
			
			var shape:Shape = new Shape();
			var bmd:BitmapData = new BitmapData(w, h, true, 0);
			
			var drawing:Vector.<IGraphicsData> = Vector.<IGraphicsData>([
				new GraphicsPath(Vector.<int>([1,2,2,2,2,1,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2]),
					Vector.<Number>([1,5,6,5,6,7,1,7,1,5,7,5,7,3,4,3,4,2,9,2,9,5,7,5,0,3,0,8,7,8,7,6,10,6,10,0,3,0,3,3,0,3]),
					GraphicsPathWinding.EVEN_ODD)
			]);
			
			switch (name) {
				case "upSkin":
					with(shape) {
						graphics.beginFill(0x757575);
						graphics.drawGraphicsData(drawing);
						graphics.endFill();
					}
					
					bmd.draw(shape);
					graphics.beginBitmapFill(bmd, new Matrix(1, 0, 0, 1, 2, 3));
					graphics.drawRect(0, 0,	w, h);
					graphics.endFill();
					break;
				
				case "overSkin":
					with(shape) {
						graphics.beginFill(0x999999);
						graphics.drawGraphicsData(drawing);
						graphics.endFill();
					}
					bmd.draw(shape);
					graphics.beginBitmapFill(bmd, new Matrix(1, 0, 0, 1, 2, 3));
					graphics.drawRect(0, 0,	w, h);
					graphics.endFill();
					break;
				
				case "downSkin":
					with(shape) {
						graphics.beginFill(0x454545);
						graphics.drawGraphicsData(drawing);
						graphics.endFill();
					}
					
					bmd.draw(shape);
					graphics.beginBitmapFill(bmd, new Matrix(1, 0, 0, 1, 2, 3));
					graphics.drawRect(0, 0,	w, h);
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