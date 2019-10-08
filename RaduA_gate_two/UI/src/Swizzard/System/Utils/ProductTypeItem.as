package Swizzard.System.Utils
{
	import Swizzard.Data.Models.Enumeration.ProductType;
	
	
	public class ProductTypeItem
	{
		public var label:String;
		public var type:uint
		
		public function ProductTypeItem(newType:uint = 0, newLabel:String = null) {
			type = newType;
			if (newLabel == null) {
				label = ProductType.toString(type);
			}
			else {
				label = newLabel;
			}
		}
	}
}