package Swizzard.UI.Components.BoxSlider
{
	import mx.core.IUIComponent;
	
	public interface ISliderGroup extends IUIComponent
	{
		function set label(val:String):void;
		function get label():String;
	}
}