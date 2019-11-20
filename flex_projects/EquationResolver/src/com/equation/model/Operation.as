package com.equation.model
{
	import com.equation.interfaces.IEquationItem;
	import com.equation.types.ItemTypes;
	import com.equation.types.OperationTypes;
	
	
	public class Operation implements IEquationItem
	{
		private var _proprity:int = 0;
		private var _type:String
		
		
		public function Operation(newType:String) {
			_type = newType;
			_proprity = OperationTypes.getPriority(newType);
		}
		
		
		public function get itemType():String {
			return ItemTypes.OPERATION;
		}
		
		
		public function get type():String {
			return _type;
		}
		
		
		public function get priority():int {
			return _proprity;
		}
	}
}