package Swizzard.UI.Skins
{
	import flash.geom.Matrix;
	
	import mx.skins.ProgrammaticSkin;
	
	public class SiemensComboBoxSkin extends ProgrammaticSkin
	{
		public function SiemensComboBoxSkin()
		{
			super();
		}
		
		
		
		override protected function updateDisplayList(w:Number, h:Number):void 
		{
			
			super.updateDisplayList(w,h);
			
			graphics.clear();
			
			var m:Matrix = new Matrix();
			m.createGradientBox(w,h,-Math.PI/2);
			
			switch (name) {
				
				case "upSkin":
				case "editableUpSkin":
					
					//border
					graphics.beginFill(0xb1b1b1);
					graphics.drawRoundRectComplex(0,0,w,h,2,2,2,2);
					graphics.endFill();
					
					//top highlight
					graphics.beginFill(0xffffff);
					graphics.drawRoundRectComplex(1,1,w-2,h-2,2,2,2,2);
					graphics.endFill();
					
					//fill
					graphics.beginGradientFill("linear", [0xffffff, 0xeaeaea, 0xf2f2f2, 0xffffff], [1,1,1,1], [20, 127,128, 235], m );
					graphics.drawRoundRectComplex(1,2,w-2,h-3,2,2,2,2);
					graphics.endFill();
					break;
				
				
				case "editableOverSkin":
				case "overSkin":
					
					
					graphics.beginGradientFill("linear", [0x999999,0x8a8a8a], [1,1], [0,255], m);
					graphics.drawRoundRectComplex(0,0,w,h,2,2,2,2);
					graphics.endFill();
					
					graphics.beginFill(0xf2f2f2);
					graphics.drawRoundRectComplex(1,1,w-2,h-2,2,2,2,2);
					graphics.endFill();
					
					//graphics.beginGradientFill("linear", [0xd7d7d7, 0xababab, 0xdadada], [1,1,1], [0,125,140], m); //darker
					//graphics.beginGradientFill("linear", [0xececec, 0xdadada, 0xf2f2f2], [1,1,1], [0,125,140], m); //lighter
					graphics.beginGradientFill("linear", [0xd2e6ed, 0xc1d2d9, 0xd8ebf2], [1,1,1], [0,125,140], m);
					graphics.drawRoundRectComplex(1,2,w-2,h-3,2,2,2,2);
					graphics.endFill();
					
					break;
				
				
				case "editableDownSkin":
				case "downSkin":
					
					graphics.beginGradientFill("linear", [0x999999,0x7c7c7c], [1,1], [0,255], m);
					graphics.drawRoundRectComplex(0,0,w,h,2,2,2,2);
					graphics.endFill();
					/* 	graphics.beginFill(0xf2f2f2);
					graphics.drawRoundRectComplex(1,1,w-2,h-2,2,2,2,2); */
					
					graphics.beginGradientFill("linear", [0xcbcbcb,0xababab, 0xc5c5c5], [1,1,1], [0,125,140], m);
					graphics.drawRoundRectComplex(1,1,w-2,h-2,2,2,2,2);
					graphics.endFill();
					
					break;
				
				
				case "editableDisabledSkin":
				case "disabledSkin":
					
					
					//graphics.beginGradientFill("linear", [0x999999,0x7c7c7c], [1,1], [0,255], m);
					graphics.beginFill(0x999999);
					graphics.drawRoundRectComplex(0,0,w,h,2,2,2,2);
					graphics.endFill();
					
					
					//graphics.beginGradientFill("linear", [0xcbcbcb,0xababab, 0xc5c5c5], [1,1,1], [0,125,140], m);
					graphics.beginFill(0xdadada);
					graphics.drawRoundRectComplex(1,1,w-2,h-2,2,2,2,2); 
					graphics.endFill();
					break;
			}
			
			
			// Draw the triangle.
			graphics.beginFill(0x666666);
			graphics.moveTo(w - 11.5, h / 2 + 3);
			graphics.lineTo(w - 15, h / 2 - 2);
			graphics.lineTo(w - 8, h / 2 - 2);
			graphics.lineTo(w - 11.5, h / 2 + 3);
			graphics.endFill();
			
			
			
		}
		
	}
}