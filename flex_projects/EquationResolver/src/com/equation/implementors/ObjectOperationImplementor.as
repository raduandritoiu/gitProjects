package com.equation.implementors
{
	import com.equation.interfaces.IOperationImplementor;
	import com.equation.model.Operation;
	import com.equation.values.ResultValue;
	
	public class ObjectOperationImplementor implements IOperationImplementor
	{
		public function ObjectOperationImplementor() {
		}
		
		public function calculateUnaryOperation(op:ResultValue, operation:Operation):ResultValue {
			return op;
		}
		
		public function calculateBinaryOperation(op1:ResultValue, op2:ResultValue, operation:Operation):ResultValue {
			return op1;
		}
	}
}