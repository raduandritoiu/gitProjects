package com.equation.values
{
	import com.equation.implementors.ObjectOperationImplementor;
	import com.equation.interfaces.IOperationImplementor;
	import com.equation.types.ValueTypes;
	
	
	public class ResultValue
	{
		private var _value:Object;
		protected var impl:IOperationImplementor;
		
		
		public function ResultValue(newValue:Object) {
			_value = newValue;
			impl = new ObjectOperationImplementor();
		}
		
		
		public function get implementor():IOperationImplementor {
			return impl;
		}
		
		
		public function get valueType():String {
			return ValueTypes.OBJECT;
		}
		
		
		public function set value(val:Object):void {
			_value = val;
		}
		
		
		public function get value():Object {
			return _value;
		}
		
	}
}