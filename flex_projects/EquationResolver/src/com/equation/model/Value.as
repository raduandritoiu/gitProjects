package com.equation.model
{
	import com.equation.interfaces.IEquationItem;
	import com.equation.interfaces.IValueItem;
	import com.equation.types.ItemTypes;
	import com.equation.values.ResultValue;
	
	public class Value implements IEquationItem, IValueItem
	{
		private var internalValue:ResultValue;
		
		
		public function Value(newValue:ResultValue) {
			internalValue = newValue;
		}
		
		
		public function get itemType():String {
			return ItemTypes.VALUE;
		}
		
		
		public function getValue():ResultValue {
			return internalValue;
		}
	}
}