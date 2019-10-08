package Swizzard.System.Utils
{
	public class SelectableItem
	{
		// RADU_TODO: not used yet, maybe in the future
		
		public var label:String;
		public var value:*;
		
		public function SelectableItem(newValue:* = null, newLabel:String = "All") {
			value = newValue;
			label = newLabel;
		}
	}
}