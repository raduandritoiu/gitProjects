package com.equation.values
{
	import com.equation.implementors.NumericOperationImplementor;
	import com.equation.types.ValueTypes;
	
	
	public class NumericValue extends ResultValue
	{
		public function NumericValue(newValue:Object) {
			super(newValue);
			impl = new NumericOperationImplementor();
		}
		
		
		override public function get valueType():String {
			return ValueTypes.NUMERIC;
		}
		
		
		public function set numVal(val:Number):void {
			value = val;
		}
		
		
		public function get numVal():Number {
			return value as Number;
		}
	}
}