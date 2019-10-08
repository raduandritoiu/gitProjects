package Swizzard.UI.Controls
{
	import com.greensock.TweenLite;
	import com.greensock.TweenMax;
	
	import flash.events.Event;
	import flash.filters.DropShadowFilter;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	
	import spark.components.SkinnableContainer;
	
	/**
	 * This class is not used directly. Please see SlidingPopUpButton.
	 * 
	 * @see SlidingPopUpButton
	 * 
	 * @author Jonathan Dumaine
	 * 
	 */	
	public class SlidingPopUp extends SkinnableContainer
	{
		private var parentButton:SlidingPopUpButton;
		private var measuredLocation:Point;
		
		private var _arrowInset:Number = 15;
		private var _side:String;
		private var _onStage:Boolean;
		
		
		public function SlidingPopUp(parentReference:SlidingPopUpButton)
		{
			super();
			//requires the parent to give this component a reference
			parentButton = parentReference;
			filters = [new DropShadowFilter(4, 90, 0, 0.4, 10, 10, 1, 3)];
			addEventListener(Event.ADDED_TO_STAGE, addHandler, false, 0, true);
		}
		
		
		
		public function get onStage():Boolean {
			return _onStage;
		}


		public function set side(value:String):void {
			_side = value;
			invalidateSize();
			invalidateDisplayList();
		}
		
		public function get side():String {
			return _side;
		}
		
		/**
		 * 
		 * This component will try to calculate where
		 * to put the arrow based off the bounds of
		 * it's parent button. If you need to try do
		 * a little adjustment do so after measure().
		 * 
		 * @private 
		 * @param value
		 * 
		 */		
		public function set arrowInset(value:Number):void {
			if (_arrowInset == value) 
				return;
			
			if(value < 5)  {
				_arrowInset = 5
			} 
			else if (value > (this.getExplicitOrMeasuredWidth() - 25)) {
				// Don't allow it to go lower than 25
				_arrowInset = Math.max(25, this.getExplicitOrMeasuredWidth() - 25);
			} 
			else {
				_arrowInset = value;
			}
			
			invalidateDisplayList();
		}
		
		public function get arrowInset():Number {
			return _arrowInset;
		}
		
		
		protected function addHandler(e:Event):void {
			stage.addEventListener(Event.RESIZE, resizeHandler, false, -500, true);
			// gotta wait for the correct position to be set before we can play the intro thing
			callLater(animateIn);
		}
		
		
		private function resizeHandler(e:Event):void {
			invalidatePosition();
		}
		
		
		private function invalidatePosition():void {			
			invalidateSize();
			invalidateDisplayList();
		}
		
		
		protected function animateIn():void {
			if (!TweenMax.isTweening(this)) {
				TweenLite.from(this, 0.75, {y:"-5"});
				TweenLite.from(this, 0.5, {alpha:0});
			}
		}
		
		/**
		 * Moves and resizes the popup to fit the application. 
		 * Should only be called from measure()
		 */		
		private function measurePopUp():void
		{
			var b:Rectangle = parentButton.getBounds(stage);
			//Temp Point --	test the position to see if it's adequate
			//				if not, make changes to the temp point				
			var tp:Point; 
			
			switch(side.toLowerCase()) {
				case "above":
					tp = new Point(b.x, b.y - this.getExplicitOrMeasuredHeight() - 25);
					
					//see if placing the popup near the button will put it
					//outside of the stage, if so move it further to the left
					//and then adjust the arrow
					if (tp.x + this.getExplicitOrMeasuredWidth() > stage.stageWidth)
					{	
						tp.x = stage.stageWidth - this.getExplicitOrMeasuredWidth() - 15;
						arrowInset = b.x - tp.x - b.width*0.5;
					}
					//
					//  TODO:  do some checks to see if the popup will go outside
					// 		   the top edge of the stage.
					//
					//		   probably would just want to set this to "below" if
					//		   this conditional is triggered
					//move(tp.x, tp.y);
					break;
				
				case "below":
					tp = new Point(b.x - 20, b.y + 25);
					//see if placing the popup near the button will put it
					//outside of the stage, if so move it further to the left
					//and then adjust the arrow
					if(tp.x + this.getExplicitOrMeasuredWidth() > stage.stageWidth)
						tp.x = stage.stageWidth - this.getExplicitOrMeasuredWidth() - 15;
					// Always set the arrow inset
					arrowInset = b.x - tp.x - b.width * 0.5;
					//same deal as above but instead of moving it up,
					//reduce the popup's height.
					if(tp.y + this.getExplicitOrMeasuredHeight() > stage.stageHeight)
					{
						measuredMinHeight 	= 
						measuredHeight 		= stage.stageHeight - tp.y - 50;	
					}
					//move(tp.x, tp.y);
					break;
				
				case "left":
					tp = new Point(b.x - this.getExplicitOrMeasuredWidth() - 10, b.y - 25);	
					break;
				
				case "right":
					tp = new Point(b.x + 20, b.y - 25);	
					break;
			}
			measuredLocation	= tp.clone();
		}
		
		
		public function dispose():void {
			//remove any event listeners
			if (stage)
				stage.removeEventListener(Event.RESIZE, resizeHandler);
			//remove from display list
		}
		
		
		override protected function measure():void {
			super.measure();
			measurePopUp();
			//set the size of this component to it's children's size
			setActualSize(getExplicitOrMeasuredWidth(), getExplicitOrMeasuredHeight());
		}
		
		
		override protected function updateDisplayList(w:Number, h:Number):void {
			super.updateDisplayList(w,h);
			
			// Move to measured location
			move(measuredLocation.x, measuredLocation.y);
			graphics.clear();
			
			switch(side.toLowerCase()) {
				case "above":
					graphics.beginGradientFill("linear", [0xffffff, 0xf0f0f0], [1,1], [200, 255], verticalGradientMatrix(0,0,w,h));
					graphics.drawRoundRectComplex(0,0,w,h, 5,5,5,5);
					graphics.moveTo(5 + arrowInset, h);
					graphics.lineTo(5 + arrowInset + 10, h+10);
					graphics.lineTo(5 + arrowInset + 20, h);
					graphics.endFill();
					break;
				
				case "below":
					graphics.beginGradientFill("linear", [0xffffff, 0xf0f0f0], [1,1], [200, 255], verticalGradientMatrix(0,0,w,h));
					graphics.drawRoundRectComplex(0,0,w,h, 5,5,5,5);
					graphics.moveTo(5 + arrowInset, 0);
					graphics.lineTo(5 + arrowInset + 10, -10);
					graphics.lineTo(5 + arrowInset + 20, 0);
					graphics.endFill();
					break;
				
				case "left":
					graphics.beginGradientFill("linear", [0xffffff, 0xf0f0f0], [1,1], [200, 255], verticalGradientMatrix(0,0,w,h));
					graphics.drawRoundRectComplex(0,0,w,h, 5,5,5,5);
					graphics.moveTo(w,5 + arrowInset);
					graphics.lineTo(+-10,5 + arrowInset + 10);
					graphics.lineTo(w,5 + arrowInset + 20);
					break;
				
				case "right":
					graphics.beginGradientFill("linear", [0xffffff, 0xf0f0f0], [1,1], [200, 255], verticalGradientMatrix(0,0,w,h));
					graphics.drawRoundRectComplex(0,0,w,h, 5,5,5,5);
					graphics.moveTo(0,5 + arrowInset);
					graphics.lineTo(-10,5 + arrowInset + 10);
					graphics.lineTo(0,5 + arrowInset + 20);
					break;
			}
		}
	}
}