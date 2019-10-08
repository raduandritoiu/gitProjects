package extra_tools
{
	import flash.filesystem.File;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Utils.SynchronousDataUtility;
	
	
	public class CompareBacwardsProducts
	{
		public static function compareObsolete():void
		{
			var dbFile_1:File = File.applicationStorageDirectory.resolvePath("C:/radua/work_related_projects/simple_select/comparable_bds/last_parts.db");
			var dbConn_1:SynchronousDataUtility = new SynchronousDataUtility(true, dbFile_1);
			var prods_1:Array = dbConn_1.getProducts();
			
			var dbFile_2:File = File.applicationStorageDirectory.resolvePath("C:/radua/work_related_projects/simple_select/comparable_bds/parts.db");
			var dbConn_2:SynchronousDataUtility = new SynchronousDataUtility(true, dbFile_2);
			var prods_2:Array = dbConn_2.getProducts();
			
			if (prods_1.length != prods_2.length)
				trace("FUCK!");
			
			var i:int = 0;
			var prod_1:AbstractSiemensProduct;
			var prod_2:AbstractSiemensProduct;
			
			trace("Products that were NOT absolete, but NOW became absolete:");
			for (i = 0; i < prods_1.length; i++)
			{
				prod_1 = prods_1[i] as AbstractSiemensProduct;
				prod_2 = findProduct(prod_1.partNumber, prods_2);
				if (prod_2 == null)
					trace("FUCK!");
				if (!prod_1.isObsolete && prod_2.isObsolete)
					trace(prod_1.partNumber);
			}
			
			trace("Products that were absolete, but NOW became NOT absolete:");
			for (i = 0; i < prods_1.length; i++)
			{
				prod_1 = prods_1[i] as AbstractSiemensProduct;
				prod_2 = findProduct(prod_1.partNumber, prods_2);
				if (prod_2 == null)
					trace("FUCK!");
				if (prod_1.isObsolete && !prod_2.isObsolete)
					trace(prod_1.partNumber);
			}
			
			trace("End");
		}
		
		
		public static function findProduct(partNumber:String, prods:Array):AbstractSiemensProduct
		{
			for (var i:int = 0; i < prods.length; i++)
				if (partNumber == (prods[i] as AbstractSiemensProduct).partNumber)
					return prods[i] as AbstractSiemensProduct;
			return null;
		}
	}
}