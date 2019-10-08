package Swizzard.UI.Forms
{
	import flash.events.Event;
	import flash.events.FocusEvent;
	
	import mx.graphics.SolidColor;
	import mx.graphics.SolidColorStroke;
	
	import spark.components.TextInput;
	import spark.primitives.Rect;
	
	
	public class TextInputWithDefault extends TextInput
	{
		[SkinPart(required="false")]
		public var background:Rect;
		
		[SkinPart(required="false")]
		public var border:Rect;
		
		private var normalBgColor:uint;
		private var normalBgStroke:uint;
		private var disableBgColor:uint;
		private var disableBgStroke:uint;
		
		
		private var _defaultText:String;
		private var defaultTextChanged:Boolean;
		private var showDefaultText:Boolean;
		
		private var _defaultTextColor:uint;
		private var defaultColorChanged:Boolean;
		private var tmpColor:uint;
		
		
		public function TextInputWithDefault() {
			super();
			
			normalBgColor 	= 0xffffff;
			normalBgStroke 	= 0x696969;
			disableBgColor	= 0xdadada;
			disableBgStroke = 0x8d8d8d;
			
			height = 22;
			tmpColor = 0x000000;
			_defaultTextColor = 0x888888;
			addEventListener(FocusEvent.FOCUS_IN, onFocusIn, false, 0, true);
			addEventListener(FocusEvent.FOCUS_OUT, onFocusOut, false, 0, true);
			addEventListener(Event.CHANGE, changeHandler, false, 0, true);
		}
		
		
		public function set defaultText(value:String):void {
			_defaultText = value;
			defaultTextChanged = true;
			invalidateProperties();
		}
		
		public function get defaultText():String {
			return _defaultText;
		}
		
		
		public function set defaultTextColor(value:uint):void {
			_defaultTextColor = value;
			defaultColorChanged = true;
			invalidateProperties();
		}
		
		public function get defaultTextColor():uint {
			return _defaultTextColor;
		}
		
		
		override public function set text(value:String):void {
			super.text = value;
			// switch to normal text
			if (showDefaultText) {
				showDefaultText	= false;
				this.setStyle("color", tmpColor);
			}
			defaultTextChanged = true;
			invalidateProperties();
		}
		
		
		override public function get text():String {
			if (showDefaultText) return "";
			return super.text;
		}
		
		
		override protected function commitProperties():void {
			super.commitProperties();
			
			if (defaultTextChanged) {
				defaultTextChanged = false;
				updateDefaultText();					
			}
			
			if (defaultColorChanged) {
				defaultColorChanged	= false;
				if (showDefaultText) {
					this.setStyle("color", _defaultTextColor);
				}
			}				
		}
		
		
		//This hack (all  3 functions) is to fix the problem with refresh for the enabled/disabled skin state
		override public function set enabled(value:Boolean):void {
			super.enabled = value;
			updateBackground(value);
		}
		
		
		override protected function partAdded(partName:String, instance:Object):void {
			super.partAdded(partName, instance);
			
			if (partName == "background") {
				updateBackground(enabled);
			}
			if (partName == "border") {
				updateBackground(enabled);
			}
		}
		
		
		private function updateBackground(en:Boolean):void {
			if (background != null && background.fill is SolidColor) {
				(background.fill as SolidColor).color = en ? normalBgColor : disableBgColor;
			}
			if (border != null && border.stroke is SolidColorStroke) {
				(border.stroke as SolidColorStroke).color = en ? normalBgStroke : disableBgStroke;
			}
		}
		
		
		private function onFocusIn(e:FocusEvent):void {
			// switch to normal text
			if (showDefaultText) {
				super.text = "";
				showDefaultText	= false;
				this.setStyle("color", tmpColor);
			}
		}
		
		
		private function onFocusOut(e:FocusEvent):void {
			updateDefaultText();
		}
		
		
		private function changeHandler(event:Event):void {
			// switch to normal text
			if (showDefaultText) {
				showDefaultText	= false;
				this.setStyle("color", tmpColor);
			}
		}
		
		
		private function updateDefaultText():void {
			// switch to default text
			if (!showDefaultText && (super.text == null || super.text == "")) {
				showDefaultText	= true;
				tmpColor = this.getStyle("color");
				this.setStyle("color", _defaultTextColor);
			}
			
			if (showDefaultText) {
				super.text = defaultText;
			}
		}
	}
}