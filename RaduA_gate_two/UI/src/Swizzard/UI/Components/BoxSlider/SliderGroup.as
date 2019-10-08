package Swizzard.UI.Components.BoxSlider
{
	import flash.filters.DropShadowFilter;
	
	import spark.components.Group;
	

	public class SliderGroup extends Group implements ISliderGroup
	{
		private var _label:String;
		
		
		public function SliderGroup() {
			super();
			filters = [new DropShadowFilter(4, 90, 0, 0.4, 7, 7, 1, 3)];
		}
		
		
		public function set label(val:String):void { 
			_label = val;
		}
		
		public function get label():String {
			return _label;
		}
	}
}