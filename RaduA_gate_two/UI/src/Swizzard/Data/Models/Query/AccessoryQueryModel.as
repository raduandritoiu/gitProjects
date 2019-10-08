package Swizzard.Data.Models.Query
{
	public class AccessoryQueryModel extends BaseQueryModel
	{
		public static const VOID_PART_NUMBER:String = "no_partNumber_defined_d94j4";
		public static const DAMPER_CROSS_TABLE:String = "CrossAccessoriesDampers"; // the actual name of the table
		public static const PNEUMATIC_CROSS_TABLE:String = "CrossAccessoriesPneumatics"; // the actual name of the table
		
		
		// parameters to query accessories after
		private var _productPartNumber:String;
		private var _crossTable:String;
		
		private var _specialPneumaticPart:String;
		
		
		public function AccessoryQueryModel() {
			super();
			_productPartNumber = VOID_PART_NUMBER;
		}
		
		
		[Bindable]
		public function set productPartNumber(val:String):void {
			changedFields["productPartNumber"] = true;
			_productPartNumber = val;
		}
		
		public function get productPartNumber():String {
			return _productPartNumber;
		}
		
		
		[Bindable]
		public function set crossTable(val:String):void {
			changedFields["crossTable"] = true;
			_crossTable = val;
		}
		
		public function get crossTable():String {
			return _crossTable;
		}
		
		
		[Bindable]
		public function set specialPneumaticPart(val:String):void {
			changedFields["specialPneumaticPart"] = true;
			_specialPneumaticPart = val;
		}
		
		public function get specialPneumaticPart():String {
			return _specialPneumaticPart;
		}
		
		
		override protected function customReset():void {
			_productPartNumber 		= VOID_PART_NUMBER;
			_crossTable 			= DAMPER_CROSS_TABLE;
			_specialPneumaticPart 	= VOID_PART_NUMBER;
		}
	}
}