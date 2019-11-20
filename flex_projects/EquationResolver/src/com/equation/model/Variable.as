package com.equation.model
{
	import com.equation.interfaces.IEquationItem;
	import com.equation.interfaces.IValueItem;
	import com.equation.types.ItemTypes;
	import com.equation.values.NumericValue;
	import com.equation.values.ResultValue;
	
	
	public class Variable implements IValueItem, IEquationItem
	{
		private var calculatedValue:ResultValue;
		private var topNode:IValueItem;
		
		private var _stringEquation:String;
		private var _id:String;
		private var _recalculate:Boolean = true;
		
		
		public function Variable(newId:String) {
			_id = newId;
			topNode = new Value(new NumericValue(0));
		}
		
		
		public function get itemType():String {
			return ItemTypes.VARIABLE;
		}
		
		
		public function get id():String {
			return _id;
		}
		
		
		public function set recalculate(value:Boolean):void {
			_recalculate = value;
		}
		
		
		public function getValue():ResultValue {
			if (_recalculate) {
				calculatedValue = topNode.getValue();
			}
			return calculatedValue;
		}
		
		
		public function setTopNode(nod:IValueItem):void {
			topNode = nod;
		}
	}
}