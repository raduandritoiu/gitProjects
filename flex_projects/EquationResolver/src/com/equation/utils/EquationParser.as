package com.equation.utils
{
	import com.equation.controller.EquationSystem;
	import com.equation.interfaces.IEquationItem;
	import com.equation.model.Equation;
	import com.equation.model.Operation;
	import com.equation.model.Value;
	import com.equation.model.Variable;
	import com.equation.types.ItemTypes;
	import com.equation.types.OperationTypes;
	import com.equation.types.ValueTypes;
	import com.equation.values.NumericValue;
	
	import mx.collections.ArrayCollection;

	public class EquationParser
	{
		public function EquationParser() {
		}
		
		
		
		public function parse(system:EquationSystem, eqStr:String):Equation {
			
			var oldArr:ArrayCollection = parseString(eqStr);
			var newArr:ArrayCollection = new ArrayCollection();
			
			for each (var eqItem:IEquationItem in oldArr) {
				var convertedItem:IEquationItem = convertItem(system, eqItem);
				if (convertedItem != null) 
					newArr.addItem(convertedItem);
			}
				
			var eq:Equation = new Equation();
			eq.items = newArr;
			
			return eq;
		}
		
		
		
		protected function parseString(eqStr:String):ArrayCollection {
			// strip string of WHITE SPACES
			eqStr = eqStr.replace(/\s+/g, "");
			
			// add string to the array of items 
			var eqItem:IEquationItem = new StringItem(eqStr);
			var oldArr:ArrayCollection;			
			var newArr:ArrayCollection = new ArrayCollection([eqItem]);
			
			// find all operators and parse them
			for each (var opObj:Object in OperationTypes.OPERATORS) {
				// retrieve operator name and char codification (how it's wrote)
				var opSign:String = opObj["sign"];
				var opName:String = opObj["name"];
				
				// update item vectors
				oldArr = newArr;
				newArr = new ArrayCollection();
				
				// search into all items
				for each (var item:IEquationItem in oldArr) {
					// if the equation item is string parse it, 
					// if not just add it as it is
					if (item.itemType == ItemTypes.STRING) {
						var subEq:String = (item as StringItem).string;
						var strArr:Array = subEq.split(opSign);
						var notFirst:Boolean = false;
						
						// add the new strings and the operator that separates them
						for each (var term:String in strArr) {
							var opItem:IEquationItem = new Operation(opName);
							if (notFirst)
								newArr.addItem(opItem);
							notFirst = true;
							
							eqItem = new StringItem(term);
							newArr.addItem(eqItem);
						}
					}
					else {
						newArr.addItem(item);
					}
				}
			}
			
			// find all open brackets and parse them
			for each (var bracetObj:Object in BracetItem.BRACET_SIGNS) {
				// retrieve operator name and char codification (how it's wrote)
				var brSign:String = bracetObj["sign"];
				var brName:String = bracetObj["name"];
				
				// update item vectors
				oldArr = newArr;
				newArr = new ArrayCollection();
				
				// search into all items
				for each (var item:IEquationItem in oldArr) {
					// if the equation item is string parse it, 
					// if not just add it as it is
					if (item.itemType == ItemTypes.STRING) {
						var subEq:String = (item as StringItem).string;
						var strArr:Array = subEq.split(brSign);
						var notFirst:Boolean = false;
						
						// add the new strings and the operator that separates them
						for each (var term:String in strArr) {
							var brItem:IEquationItem = new BracetItem(brName);
							if (notFirst)
								newArr.addItem(brItem);
							notFirst = true;
							
							eqItem = new StringItem(term);
							newArr.addItem(eqItem);
						}
					}
					else {
						newArr.addItem(item);
					}
				}
			}
			
			return newArr;
		}
		
		
		private function convertItem(system:EquationSystem, item:IEquationItem):IEquationItem {
			if (item.itemType != ItemTypes.STRING) 
				return item;
			
			var strItem:StringItem = item as StringItem;
			if (strItem.string == "")
				return null;
			if (strItem.string == null)
				return null;
			if (strItem.string == "null")
				return null;
			
			var newItem:IEquationItem;
			
			switch (system.valueType) {
				case ValueTypes.NUMERIC:
					var newVal:Number = parseFloat(strItem.string);
					if (!isNaN(newVal)) {
						newItem = new Value(new NumericValue(newVal));
					}
					else {
						newItem = system.getVariable(strItem.string);
						if (newItem == null) {
							// this is fucked up;
							newItem = new Variable(strItem.string);
						}
					}
					break;
			}
			
			return newItem;
		}
	}
}