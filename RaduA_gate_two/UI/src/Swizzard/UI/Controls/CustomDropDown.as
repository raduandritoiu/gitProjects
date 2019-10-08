package Swizzard.UI.Controls
{
	import spark.components.DropDownList;
	
	public class CustomDropDown extends DropDownList
	{
		public function CustomDropDown() {
			super();
		}
		
		
		public function set text(val:String):void {
			if (labelDisplay != null) labelDisplay.text = val;
		}
		
		public function get text():String {
			if (labelDisplay != null) return labelDisplay.text;
			return null;
		}
	}
}