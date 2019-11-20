package com.equation.controller
{
	import com.equation.model.Equation;
	import com.equation.model.EquationNode;
	import com.equation.model.Value;
	import com.equation.model.Variable;
	import com.equation.utils.EquationParser;
	import com.equation.values.NumericValue;

	public class EquationSystem
	{
		protected var variables:Object;
		protected var parser:EquationParser;
		
		private var _valueType:String
		
		
		public function EquationSystem(type:String) {
			_valueType = type;
			
			parser = new EquationParser();
			variables = {};
		}
		
		
		public function get valueType():String {
			return _valueType;
		}
		
		
		public function getVariable(varName:String):Variable {
			return variables[varName] as Variable;
		}
		
		
		public function addVariable(varName:String, eqStr:String):void {
			var newVar:Variable = new Variable(varName);
			var newEq:Equation = parser.parse(this, eqStr);
			
			var node:EquationNode = new EquationNode();
			node.create(newEq.items, newEq.items.length - 1);
			if (node.operation == null)
				newVar.setTopNode(node.rightNode);
			else
				newVar.setTopNode(node);
			
			if (variables[varName] != null)
				recalculateSystem();
			
			variables[varName] = newVar;
			
			trace (newVar.getValue().value);
		}
		
		
		public function updateVariable(varName:String, eqStr:String):void {
			if (variables[varName] == null) {
				// there is nothing to update
				return;
			}
			
			addVariable(varName, eqStr);
		}
		
		
		protected function recalculateSystem():void {
			for each (var variable:Variable in variables)
				variable.recalculate = true;
		}
	}
}