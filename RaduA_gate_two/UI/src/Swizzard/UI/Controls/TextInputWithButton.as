package Swizzard.UI.Controls
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import spark.components.Button;
	
	import Swizzard.UI.Forms.TextInputWithDefault;
	
	
	[Event(name="buttonClick", type="flash.events.Event")]
	public class TextInputWithButton extends TextInputWithDefault
	{
		[SkinPart(required="true")]
		public var button:Button
		
		private var _icon:Class;
		private var iconChanged:Boolean;
		
		private var _showButton:Boolean;
		private var showButtonChanged:Boolean;
		
		
		public function TextInputWithButton() {
			super();
		}
		
		
		public function set icon(value:Class):void {
			_icon = value;
			iconChanged	= true;
			invalidateProperties();
		}
		
		public function get icon():Class {
			return _icon;
		}
		
		
		public function set showButton(value:Boolean):void {
			if (_showButton == value) {
				return;
			}
			_showButton	= value;
			invalidateSkinState();
		}
		
		public function get showButton():Boolean {
			return _showButton;
		}
		
		
		override protected function commitProperties():void {
			super.commitProperties();
			
			if (iconChanged) {
				iconChanged	= false;
				button.setStyle("icon",	icon);
			}
		}
		
		
		override protected function partAdded(partName:String, instance:Object):void {
			super.partAdded(partName, instance);
			
			if (partName == "button") {
				button.addEventListener(MouseEvent.CLICK, buttonClickHandler, false, 0, true);
			}
		}
		
		
		override protected function getCurrentSkinState():String {
			if (showButton) {
				return "showButton";
			}
			else {
				return "hideButton";
			}
		}
		
		
		private function buttonClickHandler(event:MouseEvent):void {
			dispatchEvent(new Event("buttonClick"));
		}
	}
}