package com.equation.implementors
{
	import com.equation.interfaces.IOperationImplementor;
	import com.equation.model.Operation;
	import com.equation.types.OperationTypes;
	import com.equation.values.NumericValue;
	import com.equation.values.ResultValue;
	

	public class NumericOperationImplementor implements IOperationImplementor
	{
		private static var _instance:NumericOperationImplementor;
		
		
		public function NumericOperationImplementor() {
		}
		
		
		public static function get instance():NumericOperationImplementor {
			if (_instance == null)
				_instance = new NumericOperationImplementor();
			
			return _instance;
		}
		
		
		public function calculateUnaryOperation(op:ResultValue, operation:Operation):ResultValue {
			var op_cast:NumericValue = op as NumericValue;
			var retValue:NumericValue = new NumericValue(-1);
			
			switch (operation.type) {
				case OperationTypes.INV:
					retValue.numVal = 1/op_cast.numVal;
					break;
				
				case OperationTypes.SQRT:
					retValue.numVal = Math.sqrt(op_cast.numVal);
					break;
			}
			
			return retValue;
		}
		
		
		public function calculateBinaryOperation(op1:ResultValue, op2:ResultValue, operation:Operation):ResultValue {
			var op1_cast:NumericValue = op1 as NumericValue;
			var op2_cast:NumericValue = op2 as NumericValue;
			var retValue:NumericValue = new NumericValue(-1);
			
			switch (operation.type) {
				case OperationTypes.ADD:
					retValue.numVal = op1_cast.numVal + op2_cast.numVal;
					break;
				
				case OperationTypes.SUBSTRACT:
					retValue.numVal = op1_cast.numVal - op2_cast.numVal;
					break;
				
				case OperationTypes.MULTIPLY:
					retValue.numVal = op1_cast.numVal * op2_cast.numVal;
					break;
				
				case OperationTypes.DIVIDE:
					retValue.numVal = op1_cast.numVal / op2_cast.numVal;
					break;
				
				case OperationTypes.EQUAL:
					retValue.numVal = Number(op1_cast.numVal == op2_cast.numVal);
					break;
				
				case OperationTypes.DIFFERENT:
					retValue.numVal = Number(op1_cast.numVal != op2_cast.numVal);
					break;
				
				case OperationTypes.GREATER:
					retValue.numVal = Number(op1_cast.numVal > op2_cast.numVal);
					break;
				
				case OperationTypes.GREAT_EQUAL:
					retValue.numVal = Number(op1_cast.numVal >= op2_cast.numVal);
					break;
				
				case OperationTypes.LOWER:
					retValue.numVal = Number(op1_cast.numVal < op2_cast.numVal);
					break;
				
				case OperationTypes.LOWER_EQUAL:
					retValue.numVal = Number(op1_cast.numVal <= op2_cast.numVal);
					break;
				
				case OperationTypes.POW:
					retValue.numVal = Math.pow(op1_cast.numVal, op2_cast.numVal);
					break;
			}
			
			return retValue;
		}
	}
}