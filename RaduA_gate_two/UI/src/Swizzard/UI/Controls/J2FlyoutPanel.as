package Swizzard.UI.Controls
{
	import flash.display.Sprite;
	import flash.filters.DropShadowFilter;
	import flash.geom.Point;
	
	import mx.core.UIComponent;

	// RADU_TOD: let this be a UIComponent - it doesn't upset anyone
	
	public class J2FlyoutPanel extends UIComponent
	{
		public static const TOP:String = "top";
		public static const BOTTOM:String = "bottom";
		public static const LEFT:String = "left";
		public static const RIGHT:String = "right";
		
		
		private var bg:Sprite;
		private var shadow:Sprite;
		
		private var _triangleWidth:Number = 30;
		private var _cornerRadius:Number = 10;
		private var _triangleSide:String = TOP;
		private var _centerInset:Number = 0;
		private var _trianglePointsInside:Boolean = false;
		private var _triangleOffset:Number = cornerRadius;
		

		public function J2FlyoutPanel()
		{
			super();
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (shadow == null) {
				shadow = new Sprite();
				var d:DropShadowFilter = new DropShadowFilter(0, 90, 0, 0.5, 25, 25, 1, 3, false, true, true);
				shadow.filters = [d];
				addChild(shadow);
	    	}
			
			if (bg == null) {
				bg = new Sprite();
				addChild(bg);
			}
		}

		
		override protected function updateDisplayList(w:Number, h:Number):void {
			super.updateDisplayList(w, h);
			
			graphics.clear();
		
			shadow.x = bg.x = 0;
			shadow.y = bg.y = 10;
			
			if ((w & h) > 0) // top it from drawing triangles into negative coordinates.
			{
				shadow.graphics.clear();
				centerInset = 0;
				shadow.graphics.beginFill(0);
				drawRoundRectWithTriangle(shadow);
				shadow.graphics.endFill();
				
				bg.graphics.clear();
				centerInset = 2;
				bg.graphics.beginFill(0, 0.65);
				drawRoundRectWithTriangle(bg);
				bg.graphics.endFill();
			}
		}
		
		
		/**
		 * Buncha props for controlling the sexyness of the flyout
		 */		
		public function set triangleWidth(value:Number):void {
			_triangleWidth = value;
			invalidateDisplayList();
		}

		public function get triangleWidth():Number {
			return _triangleWidth;
		}


		public function set cornerRadius(value:Number):void {
			_cornerRadius = value;
			invalidateDisplayList();
		}

		public function get cornerRadius():Number {
			return _cornerRadius;
		}

		
		public function set triangleSide(value:String):void {
			_triangleSide = value;
			invalidateDisplayList();
		}

		public function get triangleSide():String {
			return _triangleSide;
		}
		
		
		public function set centerInset(value:Number):void {
			_centerInset = value;
		}

		public function get centerInset():Number {
			return _centerInset;
		}
		
		
		public function set trianglePointsInside(value:Boolean):void {
			_trianglePointsInside = value;
			invalidateDisplayList();
		}

		public function get trianglePointsInside():Boolean {
			return _trianglePointsInside;
		}
		
		
		public function set triangleOffset(value:Number):void {
			_triangleOffset = value;
			if (value < 0) _triangleOffset = 0;
			
			if (triangleSide == TOP || triangleSide == BOTTOM) {
			 	if (value >= unscaledWidth - cornerRadius*2 - triangleWidth) 
					_triangleOffset = unscaledWidth - cornerRadius*2 - triangleWidth - 1 ;
			 	if (value <= cornerRadius + triangleWidth/2 - 5 ) 
					_triangleOffset = cornerRadius + triangleWidth/2;
			}
			 
			if (triangleSide == LEFT) { 
				if (value >= unscaledHeight - cornerRadius - triangleWidth) 
					_triangleOffset = unscaledHeight - cornerRadius - triangleWidth - 1 ;
			 	if (value <= 0) 
					_triangleOffset = 3;
			} 
			
			invalidateDisplayList();
		}
		
		
		public function get triangleOffset():Number {
			return _triangleOffset;
		}


		
		private function drawRoundRectWithTriangle(target:Sprite):void {
			var w:Number = unscaledWidth;
			var h:Number = unscaledHeight;
			
			var topLeftCorner:Point = new Point(0, 0);
			var bottomLeftCorner:Point = new Point(0, h);
			var bottomRightCorner:Point = new Point(w, h);
			var topRightCorner:Point = new Point(w, 0);
			
			//account for the inset
			topLeftCorner.offset(centerInset, centerInset);
			bottomLeftCorner.offset(centerInset, -centerInset);
			bottomRightCorner.offset(-centerInset, -centerInset);
			topRightCorner.offset(-centerInset, centerInset);
			
			with (target) {
				graphics.moveTo(topLeftCorner.x + cornerRadius, topLeftCorner.y);
				
				//top left corner
				graphics.curveTo(topLeftCorner.x,   topLeftCorner.y,   topLeftCorner.x,  topLeftCorner.y + cornerRadius); 
				if (triangleSide == LEFT) {
					// draw the triangle on ze left
					triangleOffset -= centerInset;
					graphics.lineTo(topLeftCorner.x, topLeftCorner.y + triangleOffset);
					var leftIn:Number = (trianglePointsInside) ? 1 : -1;
					graphics.lineTo(topLeftCorner.x + triangleWidth/2*leftIn, topLeftCorner.y + triangleOffset + triangleWidth/2);
					graphics.lineTo(topLeftCorner.x, topLeftCorner.y + triangleOffset + triangleWidth );
			    	graphics.lineTo(  bottomLeftCorner.x, bottomLeftCorner.y - cornerRadius);
			    	triangleOffset += centerInset;
				} 
				else {
					//draw a straight line to the next corner
				    graphics.lineTo(  bottomLeftCorner.x,   bottomLeftCorner.y - cornerRadius  );  // left side line
				}
				
				//bottom left corner
				graphics.curveTo( bottomLeftCorner.x,  bottomLeftCorner.y,  bottomLeftCorner.x + cornerRadius, bottomLeftCorner.y );  
				if (triangleSide == BOTTOM) {
					// draw the triangle on ze bottom
					triangleOffset -= centerInset;
					graphics.lineTo(bottomLeftCorner.x + triangleOffset, bottomLeftCorner.y);
					var botIn:Number = (trianglePointsInside) ? -1 : 1;
					graphics.lineTo( bottomLeftCorner.x  + triangleOffset + triangleWidth/2, bottomLeftCorner.y + triangleWidth/2*botIn);
					graphics.lineTo( bottomLeftCorner.x + triangleOffset + triangleWidth, bottomLeftCorner.y);
			    	graphics.lineTo( bottomRightCorner.x - cornerRadius,   bottomRightCorner.y);
			    	triangleOffset += centerInset;
				} 
				else {
					//draw a straight line to the next corner
				    graphics.lineTo(  bottomRightCorner.x  - cornerRadius ,   bottomRightCorner.y  ); //bottom side line
				}
				
				//bottom right corner
				graphics.curveTo(bottomRightCorner.x, bottomRightCorner.y, bottomRightCorner.x, bottomRightCorner.y - cornerRadius);  
				if (triangleSide == RIGHT) {
					// draw the triangle on ze right
					triangleOffset -= centerInset;
					graphics.lineTo(bottomRightCorner.x, bottomRightCorner.y  - triangleOffset);
					var rightIn:Number = (trianglePointsInside) ? -1 : 1;
					graphics.lineTo(bottomRightCorner.x + triangleWidth/2*rightIn , (bottomRightCorner.y  - triangleWidth - triangleOffset) - triangleWidth/2);
					graphics.lineTo(bottomRightCorner.x, (bottomRightCorner.y  - triangleWidth - triangleOffset) - triangleWidth);
			    	graphics.lineTo(  topRightCorner.x, topRightCorner.y + cornerRadius);
				} 
				else {
					//draw a straight line to the next corner
				    graphics.lineTo(  topRightCorner.x,   topRightCorner.y + cornerRadius); //right side line
				}
				
				// top right corner
				graphics.curveTo( topRightCorner.x, topRightCorner.y, topRightCorner.x - cornerRadius, topRightCorner.y);
				if (triangleSide == TOP) {
					// draw the triangle on ze top
					triangleOffset -= centerInset;
					graphics.lineTo(topLeftCorner.x + triangleWidth + triangleOffset, topLeftCorner.y);
					var topIn:Number = (trianglePointsInside) ? 1 : -1;
					graphics.lineTo( (topLeftCorner.x  + triangleWidth + triangleOffset) - triangleWidth/2, topLeftCorner.y + triangleWidth/2*topIn);
					graphics.lineTo( (topLeftCorner.x  + triangleWidth + triangleOffset) - triangleWidth , topLeftCorner.y);
			    	graphics.lineTo(  topLeftCorner.x + cornerRadius,   topLeftCorner.y);
			    	triangleOffset += centerInset;
				} 
				else {
					//draw a straight line to the starting corner
				    graphics.lineTo(  topLeftCorner.x + cornerRadius,   topLeftCorner.y); //top side line
				}
			} 
		}
	}
}