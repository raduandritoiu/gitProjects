package Swizzard.UI.Components.BoxSlider
{
	import com.greensock.TweenMax;
	import com.greensock.easing.Circ;
	import com.greensock.easing.Quint;
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	import flash.utils.setTimeout;
	
	import mx.core.UIComponent;
	import mx.effects.easing.Quintic;
	
	import spark.components.Group;
	import spark.components.HGroup;
	import spark.components.Image;
	import spark.components.Label;
	import spark.components.supportClasses.SkinnableComponent;
	
	
	// RADU TODO - look over this class once again before commiting
	// some of the effects do not work
	
	[DefaultProperty("mxmlChildren")]
	public class SliderBox extends SkinnableComponent
	{
		private static const DEFAULT_LABEL:String  = "Unknown";
		
		[SkinPart(required="true")]
		public var contentGroup:HGroup;
		
		[SkinPart(required="true")]
		public var leftArrow:Image;
		
		[SkinPart(required="true")]
		public var leftArrowLabel:Label;
		
		[SkinPart(required="true")]
		public var rightArrow:Image;
		
		[SkinPart(required="true")]
		public var rightArrowLabel:Label;
		
		[SkinPart(required="true")]
		public var scrollThumb:Group;
		
		[SkinPart(required="true")]
		public var scrollTrack:Group;
		
		[SkinPart(required="true")]
		public var labelBox:HGroup;
		
		private var _mxmlChildren:Array;
		private var _childrenCreated:Boolean;
		
		private var labelArray:Array;
		private var currentIndex:int;
		private var timer:Timer;
		

		public function SliderBox()
		{
			super();
			
			_childrenCreated = false;
			currentIndex = 0;
			
			timer = new Timer(500, 1);
			timer.addEventListener(TimerEvent.TIMER_COMPLETE, animateSlider, false, 0, true);
		}
		
		
		public function set mxmlChildren(value:Array):void {
			_mxmlChildren = value;
		}
		
		public function get mxmlChildren():Array {
			return _mxmlChildren;
		} 
		

		public function gotoToFirst():void {
			changeIndex(0);
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
		}

		
		override protected function partAdded(partName:String, instance:Object):void {
			super.partAdded(partName, instance);
			
			// I have the certanty that the mxml children are here
			// (beacuse of the FLEX frfamework)

			if (instance == leftArrow) {
				leftArrow.addEventListener(MouseEvent.ROLL_OVER, onArrowButtonRollOver);
				leftArrow.addEventListener(MouseEvent.ROLL_OUT, onArrowButtonRollOut);
				leftArrow.addEventListener(MouseEvent.CLICK, scrollLeft);
				// hide left arrow if insufficient children
				if (_mxmlChildren.length < 2) {
					leftArrow.visible = false;
				}
			}
			
			if (instance == leftArrowLabel) {
				var schild:ISliderGroup;
				if (_mxmlChildren.length > 0) {
					schild = _mxmlChildren[0] as ISliderGroup;
				}
				if (schild != null) {
					leftArrowLabel.text = schild.label;
				}
				else {
					leftArrowLabel.text = DEFAULT_LABEL + " 0";
				}
				// hide left label if insufficient children
				if (_mxmlChildren.length < 2) {
					leftArrowLabel.visible = false;
				}
			}
			
			if (instance == rightArrow) {
				rightArrow.addEventListener(MouseEvent.ROLL_OVER, onArrowButtonRollOver);
				rightArrow.addEventListener(MouseEvent.ROLL_OUT, onArrowButtonRollOut);
				rightArrow.addEventListener(MouseEvent.CLICK, scrollRight);
				// hide right arrow if insufficient children
				if (_mxmlChildren.length < 2) {
					rightArrow.visible = false;
				}
			}
			
			if (instance == rightArrowLabel) {
				var schild:ISliderGroup;
				if (_mxmlChildren.length > 1) {
					schild = _mxmlChildren[1] as ISliderGroup;
				}
				if (schild != null) {
					rightArrowLabel.text = schild.label;
				}
				else {
					rightArrowLabel.text = DEFAULT_LABEL + " 1";
				}
				// hide right label if insufficient children
				if (_mxmlChildren.length < 2) {
					rightArrowLabel.visible = false;
				}
			}
					
			if (instance == contentGroup) {
				labelArray = [];
				for (var i:int = 0; i < _mxmlChildren.length; i++) {
					var child:UIComponent = _mxmlChildren[i] as UIComponent;
					contentGroup.addElement(child);
					if (child is ISliderGroup) {
						labelArray.push((child as ISliderGroup).label);
					}
					else {
						labelArray.push(DEFAULT_LABEL + " " + i);
					}
				}
				_childrenCreated = true;
			}
			
			if (instance == labelBox) {
				// insert all the labels
				for (var i:int = 0; i < _mxmlChildren.length; i++) {
					// add spacer to the label container
					var spacer:UIComponent= new UIComponent();
					spacer.percentWidth = 100;
					labelBox.addElement(spacer);
					
					//add the Label to the label container
					var label:Label = new Label();
					var schild:ISliderGroup = _mxmlChildren[i] as ISliderGroup;
					if (schild != null) {
						label.text = schild.label;
					}
					else {
						label.text = DEFAULT_LABEL + " " + i;
					}
					
					label.setStyle("textAlign", "center");
					label.addEventListener(MouseEvent.ROLL_OVER, onLabelRollOver);
					label.addEventListener(MouseEvent.ROLL_OUT, onLabelRollOut);
					label.addEventListener(MouseEvent.CLICK, onLabelClick);
					label.buttonMode = true;
					label.useHandCursor = true;
					label.mouseChildren = false;
					labelBox.addElement(label);
				}
				
				var spacer:UIComponent= new UIComponent();
				spacer.percentWidth = 100;
				labelBox.addElement(spacer);
				
				// hide label box if insufficient children
				if (_mxmlChildren.length > 2) {
					labelBox.visible = true;
				}
			}
			
			if (instance == scrollThumb) {
				if (_mxmlChildren.length > 2) {
					scrollThumb.visible = true;
				}
			}
			
			if (instance == scrollTrack) {
				if (_mxmlChildren.length > 2) {
					scrollTrack.visible = true;
				}
			}
		}
		
		
		override protected function updateDisplayList(w:Number, h:Number):void {
			super.updateDisplayList(w, h);
			
			// this is to center the children inside the content group
			if (_childrenCreated) {
				callLater(changeIndex, [currentIndex]);
			}
		}
		
		
		private function onLabelRollOver(e:MouseEvent):void {
			TweenMax.to(e.target, 1, { glowFilter:{color:0xffffff, blurX:10, blurY:10, quality:3, strength:4, alpha:1} });
		}
		
		private function onLabelRollOut(e:MouseEvent):void {
			TweenMax.to(e.target, 1, { glowFilter:{color:0xffffff, blurX:10, blurY:10, quality:3, strength:4, alpha:0.15} });
		}
		
		
		private function scrollLeft(e:MouseEvent):void {
			changeIndex(currentIndex - 1);
		}
		
		
		private function scrollRight(e:MouseEvent):void {
			changeIndex(currentIndex + 1);
		}
		
		
		private function onArrowButtonRollOver(e:MouseEvent):void {
			if (e.target == rightArrow) {
				TweenMax.to(rightArrowLabel, 0.5, {autoAlpha:1, ease:Circ.easeOut});
			} 
			else if(e.target == leftArrow) {
				TweenMax.to(leftArrowLabel, 0.5, {autoAlpha:1, ease:Circ.easeOut});
			}
		}
		
		
		private function onArrowButtonRollOut(e:MouseEvent = null, direction:DisplayObject = null):void {
			if (TweenMax.isTweening(contentGroup) || (timer && timer.running))
				return; // Do not animate when we're about to tween.
			
			if (e != null) {
				if (e.target == rightArrow) {
					TweenMax.to(rightArrowLabel, 0.75, {autoAlpha: (rightArrow.alpha == 0) ? 0 : 0.15, ease:Circ.easeOut});
				}
				else if(e.target == leftArrow) {
					TweenMax.to(leftArrowLabel, 0.75, {autoAlpha: (leftArrow.alpha == 0) ? 0 : 0.15, ease:Circ.easeOut});
				}
			} 
			else {
				if (direction == rightArrow) {
					TweenMax.to(rightArrowLabel, 0.75, {autoAlpha: ((rightArrow.alpha == 0) ? 0 : 0.15), ease:Circ.easeOut});
				}
				else if(direction == leftArrow) {
					TweenMax.to(leftArrowLabel, 0.75, {autoAlpha: ((leftArrow.alpha == 0) ? 0 : 0.15), ease:Circ.easeOut});
				}
			}
		}
		
		
		private function onLabelClick(e:MouseEvent):void {
			var idx:int = labelBox.getChildIndex(e.target as DisplayObject);
			idx = idx / 2; // because I add a spacer between each label and in front and back
			changeIndex(idx);
		}
		
		
		private function changeIndex(newIndex:int):void {
			newIndex = (newIndex < 0) ? 0 : newIndex;
			newIndex = (newIndex >= labelArray.length) ?  (labelArray.length - 1) : newIndex;
			
			if (newIndex != currentIndex) {
				// only do this is the index chnaged
				// tdo the other things to reposition (using tweens) the box when resizing
				currentIndex = newIndex;
				
				// set left arrow and text
				if (newIndex == 0) {
					// hide the left arrow and text
					TweenMax.allTo([leftArrow, leftArrowLabel], 0.75, {autoAlpha: 0});
				}
				else {
					// show the left arrow and text
					leftArrowLabel.text = labelArray[newIndex - 1];
					TweenMax.allTo([leftArrow, leftArrowLabel], 0.75, {autoAlpha: 1});
					// asta face labelul fadeout - setTimeout() nu merge cand se creaza componenta
					// aparent nu merge nici cand fac update din updateDisplayList()
					setTimeout(onArrowButtonRollOut, 2000, null, leftArrow);
				}
				
				
				// set rigth arrow and text
				if (newIndex == (contentGroup.numChildren - 1)){
					// hide the right arrow and text
					TweenMax.allTo([rightArrow, rightArrowLabel], 0.75, {autoAlpha: 0});
				}
				else {
					// show the right arrow and text
					rightArrowLabel.text = labelArray[newIndex + 1];
					TweenMax.allTo([rightArrow, rightArrowLabel], 0.75, {autoAlpha:1});
					// asta face labelul fadeout - setTimeout() nu merge cand se creaza componenta
					// aparent nu merge nici cand fac update din updateDisplayList()
					setTimeout(onArrowButtonRollOut, 2000, null, rightArrow);
				}
			}
			
			// calculate positions for  current view (set viewport horrizontal value)
			// and for the scroll thumb
			var pos:Number = contentGroup.getChildAt(newIndex).x - ((contentGroup.width - contentGroup.getChildAt(newIndex).width) * 0.5);
			a = pos;
			
			var labelGlobalX:Number = labelBox.getElementAt(newIndex * 2 + 1).x + scrollTrack.x;
			var pos2:Number = labelGlobalX + labelBox.getElementAt(newIndex * 2 + 1).width*0.5 - scrollThumb.width*0.5;
			b = pos2;
			
			// aici e o chestie foarte dubioasa - daca change vine de la Button Click (si de regula de acolo vine ) 
			// foloseste un timer (pt delay0 la slide box content - delay cat sa se faca faca textul si sagetile vizibile
			// NU STIU DE CE - MI SE PARE O PROSTIE - GATA, acum stiu de ce, 
			// aceasta miscare se face si la resize si ca sa amane efectul pana cand utilizatorul s-a saturat sa faca resize
			// dar aparent nu merge daca fac update din updateDisplayList()
			// timer.reset();
			// timer.start();
			// else do Tweens
			TweenMax.to(contentGroup, 1.5, {horizontalScrollPosition:pos, ease:Quintic.easeOut});
			if (contentGroup.numChildren > 2) {
				TweenMax.to(scrollThumb, 1.5, {x:pos2, ease:Quint.easeOut});
			}
		}
		
		
		private var a:Number; //storage for var pos in onLabelClick
		private var b:Number; //storage for var pos2 in onLabelClick
		private function animateSlider(e:TimerEvent):void {
			TweenMax.to(contentGroup, 1.5, {horizontalScrollPosition:a, ease:Quintic.easeOut});
			TweenMax.to(scrollThumb, 1.5, {x:b, ease:Quint.easeOut});
		}
	}
}