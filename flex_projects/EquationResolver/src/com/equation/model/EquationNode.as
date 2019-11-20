package com.equation.model
{
	import com.equation.implementors.NumericOperationImplementor;
	import com.equation.interfaces.IEquationItem;
	import com.equation.interfaces.IValueItem;
	import com.equation.types.ItemTypes;
	import com.equation.utils.BracetItem;
	import com.equation.values.ResultValue;
	
	import mx.collections.ArrayCollection;
	
	
	public class EquationNode implements IValueItem
	{
		public var operation:Operation;
		public var leftNode:IValueItem;
		public var rightNode:IValueItem;
		
		
		public function EquationNode() {
		}
		
		
		public function getValue():ResultValue {
			var leftValue:ResultValue;
			var rightValue:ResultValue;
			
			if (leftNode == null && rightNode == null)
				return null;
			
			if (rightNode == null) {
				leftValue = leftNode.getValue();
				return calculateUnaryOperation(leftValue);
			}
			
			if (leftNode == null) {
				rightValue = rightNode.getValue();
				return calculateUnaryOperation(rightValue);
			}
			
			leftValue = leftNode.getValue();
			rightValue = rightNode.getValue();
			return calculateBinaryOperation(leftValue, rightValue);
		}
		
		
		private function calculateUnaryOperation(op:ResultValue):ResultValue {
			return op.implementor.calculateUnaryOperation(op, operation);
		}
		
		
		private function calculateBinaryOperation(op1:ResultValue, op2:ResultValue):ResultValue {
			return op1.implementor.calculateBinaryOperation(op1, op2, operation);
		}
		
		
		public function create(arr:ArrayCollection, pos:int):void {
			
			// create right node
			var eqItem:IEquationItem = arr.getItemAt(pos) as IEquationItem;
			pos--;
			
			if (eqItem.itemType == ItemTypes.BRACET) {
				if ((eqItem as BracetItem).type == BracetItem.OPEN) {
					// this shouldn't have happened 
					var error:Object; error.a.b = 1;
				}

				var bracetCnt:int = 1;
				var rightArr:ArrayCollection = new ArrayCollection();
				while (bracetCnt != 0) {						
					eqItem = arr.getItemAt(pos) as IEquationItem;
					pos --;
					if (eqItem.itemType == ItemTypes.BRACET) {
						if ((eqItem as BracetItem).type == BracetItem.CLOSE) {
							bracetCnt++;
						}
						else {
							bracetCnt--;
						}
					}
					else {
						rightArr.addItemAt(eqItem, 0);
					}
				}
				
				var node:EquationNode = new EquationNode();
				node.create(rightArr, rightArr.length - 1);
				if (node.operation == null)
					rightNode = node.rightNode;
				else
					rightNode = node;
			}
			else if (eqItem.itemType == ItemTypes.VALUE || eqItem.itemType == ItemTypes.VARIABLE) {
				rightNode = eqItem as IValueItem;
			}
			
			
			// create operator
			if (pos == -1) return;
			operation = arr.getItemAt(pos) as Operation;
			pos --;
			
			
			// create left node
			if (pos == -1) return;
			var node:EquationNode = new EquationNode();
			node.create(arr, pos);
			if (node.operation == null) {
				leftNode = node.rightNode;
			}
			else if (node.operation.priority < operation.priority) {
				var newNode:EquationNode = new EquationNode();
				newNode.rightNode = rightNode;
				newNode.leftNode = node.rightNode;
				newNode.operation = operation;
				
				rightNode = newNode;
				operation = node.operation;
				leftNode = node.leftNode;
			}
			else {
				leftNode = node;
			}
		}
	}
}