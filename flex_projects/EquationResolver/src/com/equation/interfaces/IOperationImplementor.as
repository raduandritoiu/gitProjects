package com.equation.interfaces
{
	import com.equation.model.Operation;
	import com.equation.values.ResultValue;
	

	public interface IOperationImplementor
	{
		function calculateUnaryOperation(op:ResultValue, operation:Operation):ResultValue;
		function calculateBinaryOperation(op1:ResultValue, op2:ResultValue, operation:Operation):ResultValue;
	}
}