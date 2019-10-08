package Swizzard.UI.Skins
{
	import flash.geom.Matrix;
	
	import mx.skins.ProgrammaticSkin;

	public class SiemensButtonSkin extends ProgrammaticSkin
	{

		public function SiemensButtonSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void 
		{
			
			super.updateDisplayList(w,h);
			
			graphics.clear();
			
			if (isNaN(w) || isNaN(h))
				return;
			
			var roundedCorners:String	= getStyle("focusRoundedCorners");
			var cornerRadius:Number		= getStyle("cornerRadius");
			
			var m:Matrix = new Matrix();
			m.createGradientBox(w,h,-Math.PI/2);
			
			var cornerRadiuses:Object	= {tl: 0, tr: 0, bl: 0, br: 0};
			
			if (roundedCorners)
			{
				var corners:Array	= roundedCorners.split(" ");
				
				for each (var corner:String in corners)
					cornerRadiuses[corner] = cornerRadius;
			}
			else
			{
				cornerRadiuses	= {tl: cornerRadius, tr: cornerRadius, bl: cornerRadius, br: cornerRadius};
			}
			
			switch (name) 
			{
				
	           case "upSkin":
	           {
		            //border
					graphics.beginFill(0xb1b1b1);
					graphics.drawRoundRectComplex(0,0,w,h, cornerRadiuses["tl"], cornerRadiuses["tr"], cornerRadiuses["bl"], cornerRadiuses["br"]);
					graphics.endFill();
					
					//top highlight
					graphics.beginFill(0xffffff);
					graphics.drawRoundRectComplex(1,1,w-2,h-2,cornerRadiuses["tl"], cornerRadiuses["tr"], cornerRadiuses["bl"], cornerRadiuses["br"]);
					graphics.endFill();
					
					//fill
					graphics.beginGradientFill("linear", [0xffffff, 0xeaeaea, 0xf2f2f2, 0xffffff], [1,1,1,1], [20, 127,128, 235], m );
					graphics.drawRoundRectComplex(1,2,w-2,h-3,cornerRadiuses["tl"], cornerRadiuses["tr"], cornerRadiuses["bl"], cornerRadiuses["br"]);
		            graphics.endFill();
		            
		 
		            break;
	           }
	            
	            
	           case "overSkin":
	           {
	           
		        	graphics.beginGradientFill("linear", [0x999999,0x8a8a8a], [1,1], [0,255], m);
					graphics.drawRoundRectComplex(0,0,w,h,cornerRadiuses["tl"], cornerRadiuses["tr"], cornerRadiuses["bl"], cornerRadiuses["br"]);
					graphics.endFill();
					
					graphics.beginFill(0xf2f2f2);
					graphics.drawRoundRectComplex(1,1,w-2,h-2,cornerRadiuses["tl"], cornerRadiuses["tr"], cornerRadiuses["bl"], cornerRadiuses["br"]);
					graphics.endFill();
					
					//graphics.beginGradientFill("linear", [0xd7d7d7, 0xababab, 0xdadada], [1,1,1], [0,125,140], m); //darker
					//graphics.beginGradientFill("linear", [0xececec, 0xdadada, 0xf2f2f2], [1,1,1], [0,125,140], m); //lighter
					
					graphics.beginGradientFill("linear", [0xd2e6ed, 0xc1d2d9, 0xd8ebf2], [1,1,1], [0,125,140], m); //lighter
					graphics.drawRoundRectComplex(1,2,w-2,h-3,cornerRadiuses["tl"], cornerRadiuses["tr"], cornerRadiuses["bl"], cornerRadiuses["br"]);
		            graphics.endFill();
		            
		           /*  
		            graphics.beginGradientFill(GradientType.RADIAL, [0xe8f6fe, 0xe8f6fe], [0, 1], [0, 255], m);
		            graphics.drawRoundRectComplex(1,2,w-2,h-3,2,2,2,2);
		            graphics.endFill(); */
		            
		            break;
		       }
	            
	            
	            
	           case "downSkin":
	           {
					graphics.beginGradientFill("linear", [0x999999,0x7c7c7c], [1,1], [0,255], m);
					graphics.drawRoundRectComplex(0,0,w,h,cornerRadiuses["tl"], cornerRadiuses["tr"], cornerRadiuses["bl"], cornerRadiuses["br"]);
					graphics.endFill();
					
				/* 	graphics.beginFill(0xf2f2f2);
					graphics.drawRoundRectComplex(1,1,w-2,h-2,2,2,2,2); */
					
					graphics.beginGradientFill("linear", [0xcbcbcb,0xababab, 0xc5c5c5], [1,1,1], [0,125,140], m);
					graphics.drawRoundRectComplex(1,1,w-2,h-2,cornerRadiuses["tl"], cornerRadiuses["tr"], cornerRadiuses["bl"], cornerRadiuses["br"]);
					graphics.endFill();

		           	break;
		       }
	            
	            
	            
	           case "disabledSkin":
	           {

					//graphics.beginGradientFill("linear", [0x999999,0x7c7c7c], [1,1], [0,255], m);
					graphics.beginFill(0xadadad);
					graphics.drawRoundRectComplex(0,0,w,h,cornerRadiuses["tl"], cornerRadiuses["tr"], cornerRadiuses["bl"], cornerRadiuses["br"]);
					graphics.endFill();
				 	
					
					//graphics.beginGradientFill("linear", [0xcbcbcb,0xababab, 0xc5c5c5], [1,1,1], [0,125,140], m);
					graphics.beginFill(0xececec);
					graphics.drawRoundRectComplex(1,1,w-2,h-2,cornerRadiuses["tl"], cornerRadiuses["tr"], cornerRadiuses["bl"], cornerRadiuses["br"]); 
					graphics.endFill();
	
		       	    break;
		       }
      		}
		}
	}
}