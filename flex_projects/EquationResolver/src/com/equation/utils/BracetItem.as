package com.equation.utils
{
	import com.equation.interfaces.IEquationItem;
	import com.equation.types.ItemTypes;
	
	import mx.collections.ArrayCollection;
	
	public class BracetItem implements IEquationItem
	{
		public static const OPEN:String = "open_bracet";
		public static const CLOSE:String = "close_bracet";
		public static const BRACET_SIGNS:ArrayCollection = new ArrayCollection([
			{name: OPEN, sign:"("}, 
			{name: OPEN, sign:"["}, 
			{name: OPEN, sign:"{"}, 
			{name: CLOSE, sign:")"}, 
			{name: CLOSE, sign:"]"}, 
			{name: CLOSE, sign:"}"}
		]);
		
		
		
		private var _bracetType:String;
		
		
		public function BracetItem(newType:String) {
			_bracetType = newType;
		}
		
		
		public function get itemType():String {
			return ItemTypes.BRACET;
		}
		
		
		public function get type():String {
			return _bracetType;
		}
	}
}