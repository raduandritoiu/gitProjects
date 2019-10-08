package Swizzard.UI.Windows
{
	import flash.display.NativeWindowSystemChrome;
	import flash.display.NativeWindowType;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.system.Capabilities;
	
	import mx.core.FlexGlobals;
	import mx.events.AIREvent;
	import mx.events.EffectEvent;
	import mx.graphics.SolidColor;
	
	import spark.components.HGroup;
	import spark.components.Label;
	import spark.components.Window;
	import spark.effects.Fade;
	import spark.effects.Scale;
	import spark.effects.easing.Power;
	import spark.primitives.Rect;
	
	
	public class GeneralWindow extends Window
	{
		[SkinPart(required="true")]
		public var backgroundRect:Rect;
		
		[SkinPart(required="true")]
		public var header:HGroup;
		
		[SkinPart(required="true")]
		public var titleLabel:Label;
		
		private var doAnimation:Boolean;
		private var alwaysInFrontSetting:Boolean;
		private var titleUpdated:Boolean;
		private var appActive:Boolean;
		
		private var scaleAnimation:Scale;
		private var fadeAnimation:Fade;
		
		private var _backgroundColor:uint;
		private var backgroundChanged:Boolean;
		
		
		public function GeneralWindow() {
			super();
			
			systemChrome	= NativeWindowSystemChrome.NONE;
			type			= NativeWindowType.NORMAL;
			transparent		= true;
			resizable		= false;
			maximizable		= false;
			doAnimation 	= true;
			alwaysInFront	= false;
			setStyle("showFlexChrome", false);
			
			FlexGlobals.topLevelApplication.addEventListener(AIREvent.APPLICATION_DEACTIVATE, 	appWindowChangeHandler, false, 0, true);
			FlexGlobals.topLevelApplication.addEventListener(AIREvent.APPLICATION_ACTIVATE, 	appWindowChangeHandler, false, 0, true);
		}
		
		
		public function set backgroundColor(value:uint):void {
			if (_backgroundColor == value) return;
			_backgroundColor = value;
			backgroundChanged = true;
			invalidateProperties();
		}
		
		public function get backgroundColor():uint {
			return _backgroundColor;
		}
		
		
		override public function set alwaysInFront(value:Boolean):void {
			super.alwaysInFront		= value;
			alwaysInFrontSetting	= value;
		}
		
		
		override public function set title(value:String):void {
			super.title		= value;
			titleUpdated	= true;
			invalidateProperties();
		}
		
		
		//	to center the window
		override public function open(openWindowActive:Boolean=true):void {
			super.open(openWindowActive);
			nativeWindow.x = (Capabilities.screenResolutionX - width) * 0.5;
			nativeWindow.y = (Capabilities.screenResolutionY - height) * 0.5;
		}
		
		
		// for the sake of animation :)
		override public function close():void {
			dispose();
		}

		
		override protected function commitProperties():void {
			super.commitProperties();
			
			if (titleUpdated) {
				titleUpdated = false;
				titleLabel.text	= title;
			}
			
			if (backgroundChanged) {
				backgroundChanged = false;
				(backgroundRect.fill as SolidColor).color = backgroundColor;
			}
		}
		
		
		override protected function partAdded(partName:String, instance:Object):void {
			super.partAdded(partName, instance);
			
			if (instance == header) {
				header.addEventListener(MouseEvent.MOUSE_DOWN, onHeaderMouseDown, false, 0, true);
			}
			
			if (instance == titleLabel) {
				titleLabel.text	= title;
			}
		}
		
		
		protected function onHeaderMouseDown(e:MouseEvent):void {
			nativeWindow.startMove();
		}
		
		
		protected function appWindowChangeHandler(event:AIREvent):void {
			try {
				switch (event.type) {
					case AIREvent.APPLICATION_ACTIVATE:
					{
						if (!FlexGlobals.topLevelApplication.nativeWindow.active) {
							FlexGlobals.topLevelApplication.activate();
						}
						nativeWindow.orderInFrontOf(FlexGlobals.topLevelApplication.nativeWindow);
						super.alwaysInFront	= alwaysInFrontSetting; 
						// Make this window active
						activate();
						break;
					}
						
					case AIREvent.APPLICATION_DEACTIVATE:
					{
						orderToFront();
						super.alwaysInFront	= false; 
						break;
					}
				}
			}
			catch(err:Error) {
				// Window Must be closing or is closed.
			}
		}

		
		public function dispose():void {
			FlexGlobals.topLevelApplication.removeEventListener(AIREvent.APPLICATION_DEACTIVATE, appWindowChangeHandler);
			FlexGlobals.topLevelApplication.removeEventListener(AIREvent.APPLICATION_ACTIVATE, appWindowChangeHandler);
			
			// close imediatly without animation
			if (!doAnimation) {
				super.close();
				return
			}
			
			// delay the creation of the effects
			if (scaleAnimation == null) {
				scaleAnimation = new Scale();
				scaleAnimation.autoCenterTransform = true;
				scaleAnimation.scaleXFrom = 1;
				scaleAnimation.scaleXTo = 0.8;
				scaleAnimation.scaleYFrom = 1;
				scaleAnimation.scaleYTo = 0.8;
				scaleAnimation.duration = 400;
				scaleAnimation.easer = new Power(1, 3);
			}
			if (fadeAnimation == null) {
				fadeAnimation = new Fade();
				fadeAnimation.alphaFrom = 1;
				fadeAnimation.alphaTo = 0;
				fadeAnimation.duration = 400;
				fadeAnimation.startDelay = 100;
				fadeAnimation.easer = new Power(0, 3);
				fadeAnimation.addEventListener(EffectEvent.EFFECT_END, fadeOutEnd, false, 0, true);
			}
			scaleAnimation.play([this]);
			fadeAnimation.play([this]);
		}
		
		
		protected function fadeOutEnd(e:Event):void {
			fadeAnimation.removeEventListener(EffectEvent.EFFECT_END, fadeOutEnd, false);
			scaleAnimation.end();
			fadeAnimation = null;
			scaleAnimation = null;
			super.close();
		}
	}
}