package Swizzard.UI.Controls
{
	import flash.events.FocusEvent;
	
	import mx.core.mx_internal;
	
	import spark.components.ComboBox;
	import spark.events.TextOperationEvent;
	
	
	use namespace mx_internal;
	
	public class CustomComboBox extends ComboBox
	{
		protected var customUserTypedText:Boolean;
		
		
		public function CustomComboBox() {
			super();
		}
		
		
		override mx_internal function applySelection():void {
			super.applySelection();
			customUserTypedText = false;
		}
		
		
		override protected function focusInHandler(event:FocusEvent):void {
			super.focusInHandler(event);
			customUserTypedText = false;
		}
		
		
		override protected function textInput_changeHandler(event:TextOperationEvent):void {
			customUserTypedText = true;
			super.textInput_changeHandler(event);
		}
		
		
		override mx_internal function updateLabelDisplay(displayItem:* = undefined):void {
			if (customUserTypedText && selectedIndex == -3 && userProposedSelectedIndex == -3) {
				// do not update the label because this means the user has typed another value
				return;
			}
			super.updateLabelDisplay(displayItem);
		}
	}
}