package Swizzard.System.Search.Events
{
	import Swizzard.Data.Models.AbstractSiemensProduct;
	
	import flash.events.Event;
	
	public class SearchBoxEvent extends Event
	{
		public static const ADD_BUTTON_CLICKED:String = "SearchBoxListADDButtonClicked";
		
		
		public var item:AbstractSiemensProduct;
		
		
		public function SearchBoxEvent(type:String, item:AbstractSiemensProduct)
		{
			this.item = item;
			super(type, true, false);
		}
		
		override public function clone():Event
		{
			return new SearchBoxEvent(type, item);
		}
	}
}