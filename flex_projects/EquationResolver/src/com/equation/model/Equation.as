package com.equation.model
{
	import com.equation.interfaces.IEquationItem;
	
	import mx.collections.ArrayCollection;

	public class Equation
	{
		public var items:ArrayCollection;
		
		
		public function Equation() {
			items = new ArrayCollection();
		}
		
		
		public function getItemAt(idx:int):IEquationItem {
			return items.getItemAt(idx) as IEquationItem;
		}
	}
}