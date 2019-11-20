package com.equation.types
{
	public class OperationTypes
	{
		// binary operations
		public static const ADD:String = "add";
		public static const SUBSTRACT:String = "substract";
		public static const MULTIPLY:String = "multiply";
		public static const DIVIDE:String = "divide";
		
		public static const EQUAL:String = "equal";
		public static const DIFFERENT:String = "different";
		public static const GREATER:String = "greater";
		public static const LOWER:String = "lower";
		public static const GREAT_EQUAL:String = "greater_or_equal";
		public static const LOWER_EQUAL:String = "lower_or_equal";
		
		public static const POW:String = "power";
		
		
		// unary operations
		public static const INV:String = "invers";
		public static const SQRT:String = "square_root";
		
		
		
		// priorities sigs and priorities
		public static const OPERATORS:Object = {
			ADD: 		{name: ADD			, priority: 20, 	sign: "+"},
			SUBSTRACT: 	{name: SUBSTRACT	, priority: 20, 	sign: "-"},
			MULTIPLY: 	{name: MULTIPLY		, priority: 30, 	sign: "*"},
			DIVIDE: 	{name: DIVIDE		, priority: 30, 	sign: "/"},
			
			EQUAL: 		{name: EQUAL		, priority: 10, 	sign: "=="},
			DIFFERENT: 	{name: DIFFERENT	, priority: 10, 	sign: "!="},
			GREATER: 	{name: GREATER		, priority: 10, 	sign: ">"},
			LOWER: 		{name: LOWER		, priority: 10, 	sign: "<"},
			GREAT_EQUAL:{name: GREAT_EQUAL	, priority: 10, 	sign: ">="},
			LOWER_EQUAL:{name: LOWER_EQUAL	, priority: 10, 	sign: "<="},
			
			POW: 		{name: POW			, priority: 40, 	sign: "^"},
			
			INV: 		{name: INV			, priority: 50, 	sign: "inv"},
			SQRT: 		{name: SQRT			, priority: 50, 	sign: "sqrt"}
		};

		
		public static function getPriority(operation:String):int {
			for each (var opItem:Object in OPERATORS) {
				if (opItem.name == operation)
					return opItem.priority;
			}
			return 0;
		}
		
			
		public function OperationTypes() {}
	}
}