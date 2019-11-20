package com.equation.utils
{
	import com.equation.interfaces.IEquationItem;
	import com.equation.types.ItemTypes;
	
	public class StringItem implements IEquationItem
	{
		private var str:String;
		
		
		public function StringItem(newStr:String) {
			str = newStr;
		}
		
		
		public function get itemType():String {
			return ItemTypes.STRING;
		}
		
		
		public function get string():String {
			return str;
		}
	}
}