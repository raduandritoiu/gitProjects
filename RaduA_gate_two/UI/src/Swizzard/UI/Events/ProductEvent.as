package Swizzard.UI.Events
{
	import flash.events.Event;
	
	public class ProductEvent extends Event
	{
		static public const ADD_ALL:String = "addAllProducts";
		static public const REMOVE_ALL:String = "removeAllProducts";
		
		
		public function ProductEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}
	}
}