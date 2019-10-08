package Swizzard.Data.Utils
{
	import flash.data.SQLConnection;
	import flash.data.SQLMode;
	import flash.data.SQLResult;
	import flash.data.SQLStatement;
	import flash.filesystem.File;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensAccessory;
	import Swizzard.Data.Models.SiemensActuator;
	import Swizzard.Data.Models.SiemensAssembly;
	import Swizzard.Data.Models.SiemensDamper;
	import Swizzard.Data.Models.SiemensDocument;
	import Swizzard.Data.Models.SiemensMiscProduct;
	import Swizzard.Data.Models.SiemensPneumatic;
	import Swizzard.Data.Models.SiemensValve;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Data.Proxies.DataProxy;
	
	import j2inn.Data.Query.SQLDataQuery;
	import j2inn.Data.Query.SQLQueryItem;
	import j2inn.Data.Query.SubTable;
	import j2inn.Data.Query.Enumeration.QueryOperations;
	import j2inn.Data.Query.Enumeration.QueryType;
	
	/**
	 * Synchronous Data Utility
	 * 
	 * This utility queries data for synchronous operations 
	 * @author michael
	 */	
	
	public class SynchronousDataUtility
	{
		private var database:SQLConnection;
		private var _dbFile:File;
		private var _isDisposed:Boolean;
		
		
		public function SynchronousDataUtility(init:Boolean = true, dbFile:File = null) {
			_dbFile = (dbFile != null) ? dbFile : DataProxy.DATA_FILE;
			if (init) {
				initialize();
			}
			else {
				_isDisposed = true;
			}
		}
		
		
		/**
		 * Try to connect to the database
		 */
		private function initialize():void {
			if (!_dbFile.exists)
				return;
			database = new SQLConnection();
			try {
				database.open(_dbFile, SQLMode.READ);	
			}
			catch (err:Error) {
				database = null;
			}
		}
		
		
		public function get isDisposed():Boolean {
			return _isDisposed;
		}
		
		
		/**
		 * Reconcile disposed instance 
		 */		
		public function reconcile():void {
			initialize();
		}
		
		
		/**
		 * Dispose of this instance of the synchronous utility 
		 */		
		public function dispose():void {
			if (database)
				database.close();
			database	= null;
			_isDisposed	= true;
		}

		
		/**
		 * Hydrate Product Documents
		 * 
		 * This function will take a product, query its documents, and populate its document collection 
		 * @param product
		 */		
		public function hydrateProductDocuments(product:AbstractSiemensProduct):void {
			if (!database)
				return;
				
			var statement:SQLStatement	= new SQLStatement();
			statement.sqlConnection		= database;
			statement.text				= "SELECT * FROM Documents WHERE partNumber = \"" + product.partNumber + "\"";
			statement.itemClass			= SiemensDocument;
			
			statement.execute();
			var result:SQLResult	= statement.getResult();
			product.documents		= result.data;
		}
		
		
		/**
		 * Get Assembly
		 * 
		 * this function will return an assembly record that matches the valve and actuator combination
		 *  
		 * @param valve
		 * @param actuator
		 * @return 
		 */		
		public function getAssembly(valve:SiemensValve, actuator:SiemensActuator):SiemensAssembly {
			if (!database)
				return null;
			
			var query:SQLDataQuery	= new SQLDataQuery();
			query.tableName			= "Assemblies";
			query.queryType			= QueryType.SELECT;
			query.resultType		= SiemensAssembly;
			
			var productsTable:SubTable	= new SubTable();
			productsTable.tableName		= "Products";
			productsTable.tableColumn	= "partNumber";
			productsTable.tableAlias	= "p";
			productsTable.foreignColumn	= "partNumber";
			query.addTable(productsTable);
						
			query.addItem(SQLQueryItem.Build("valvePartNumber", QueryOperations.EQUAL_TO, valve.partNumber, "t"));
			query.addItem(SQLQueryItem.Build("actuatorPartNumber", QueryOperations.EQUAL_TO, actuator.partNumber, "t"));
			
			var statement:SQLStatement	= new SQLStatement();
			statement.sqlConnection		= database;
			statement.text				= query.toSql();
			statement.itemClass			= query.resultType;
			
			statement.execute();
			var result:SQLResult		= statement.getResult();
			
			if (!result || !result.data)
				return null;
				
			var assembly:SiemensAssembly	= result.data[0] as SiemensAssembly;
			if (assembly) {
				assembly.valve		= valve;
				assembly.actuator	= actuator;
			}
			
			return assembly;
		}
		
		
		/**
		 * Get Misc Product By Part Number
		 * 
		 * This function will return a Misc Product by its part number and hydrate its documents
		 *  
		 * @param partNumber
		 * @return 
		 */		
		public function getMiscProductByPartNumber(partNumber:String):SiemensMiscProduct {
			if (!database)
				return null;
			
			var query:SQLDataQuery	= new SQLDataQuery();
			query.queryType			= QueryType.SELECT;
			query.tableName			= "Products";
			query.resultType		= SiemensMiscProduct;
			query.addItem(SQLQueryItem.Build("actualPartNumber", QueryOperations.EQUAL_TO, partNumber, "t"));
			
			// Query Data
			var statement:SQLStatement	= new SQLStatement();
			statement.sqlConnection		= database;
			statement.text				= query.toSql();
			statement.itemClass			= query.resultType;
			
			statement.execute();
			var result:SQLResult		= statement.getResult();
			
			if (!result || !result.data)
				return null;
			
			var resultProduct:SiemensMiscProduct	= result.data[0] as SiemensMiscProduct;
			hydrateProductDocuments(resultProduct);
			
			return resultProduct;
		}
			
		
		/**
		 * Get Full Item
		 * 
		 * This function will convert any abstract product into its full type with its data 
		 * @param abstract
		 * @return 
		 */		
		public function getFullItem(abstract:AbstractSiemensProduct, useActualPartNumber:Boolean = false, useProductType:uint = ProductType.UNKNOWN):AbstractSiemensProduct {
			if (!database)
				return null;
			
			var productType:uint 	= (useProductType == ProductType.UNKNOWN) ? abstract.productType : useProductType;
			var query:SQLDataQuery	= new SQLDataQuery();
			query.queryType			= QueryType.SELECT;
			
			switch (productType)
			{
				case ProductType.VALVE:
					query.tableName		= "Valves";
					query.resultType	= SiemensValve;
					break;
				
				case ProductType.ACTUATOR:
					query.tableName		= "Actuators";
					query.resultType	= SiemensActuator;
					break;
				
				case ProductType.ASSEMBLY:
					query.tableName		= "Assemblies";
					query.resultType	= SiemensAssembly;
					break;
				
				case ProductType.MISC:
					query.tableName		= "Products";
					query.resultType	= SiemensMiscProduct;
					break;
				
				case ProductType.DAMPER:
					query.tableName		= "Dampers";
					query.resultType	= SiemensDamper;
					break;
					
				case ProductType.ACCESSORY:
					query.tableName		= "AccessoryKits";
					query.resultType	= SiemensAccessory;
					break;
				
				case ProductType.PNEUMATIC:
					query.tableName		= "Pneumatics";
					query.resultType	= SiemensPneumatic;
					break;
				
				default:
					// Unable To Determine Product Type
					return abstract;
					break;
			}
			
			var productsTable:SubTable	= new SubTable();
			productsTable.tableName		= "Products";
			productsTable.tableColumn	= "partNumber";
			productsTable.tableAlias	= "p";
			productsTable.foreignColumn	= "partNumber";
			query.addTable(productsTable);
			
			query.addItem(SQLQueryItem.Build((useActualPartNumber) ? "actualPartNumber" : "partNumber", QueryOperations.EQUAL_TO, abstract.partNumber, "p"));
			
			// Query Data
			var statement:SQLStatement	= new SQLStatement();
			statement.sqlConnection		= database;
			statement.text				= query.toSql();
			statement.itemClass			= query.resultType;
			
			statement.execute();
			var result:SQLResult		= statement.getResult();
			
			if (!result || !result.data)
				return null;
			
			var resultProduct:AbstractSiemensProduct	= result.data[0] as AbstractSiemensProduct;
			
			if (abstract.subProductPartNumber) {
				resultProduct.subProductPartNumber	= abstract.subProductPartNumber;
				resultProduct.subProductPrice		= abstract.subProductPrice;
			}
			
			if (resultProduct is SiemensAssembly) {
				var absValve:AbstractSiemensProduct	= new AbstractSiemensProduct();
				absValve.productType				= ProductType.VALVE;
				absValve.partNumber					= SiemensAssembly(resultProduct).valvePartNumber;
				
				if (absValve.partNumber)
					SiemensAssembly(resultProduct).valve	= getFullItem(absValve) as SiemensValve;
				
				var absActuator:AbstractSiemensProduct	= new AbstractSiemensProduct();
				absActuator.productType					= ProductType.ACTUATOR;
				absActuator.partNumber					= SiemensAssembly(resultProduct).actuatorPartNumber;
				
				if (absActuator.partNumber)
					SiemensAssembly(resultProduct).actuator	= getFullItem(absActuator) as SiemensActuator;
			}
			
			return resultProduct;
		}
		
		
		/**
		 * Get all the producs
		 */
		public function getProducts():Array {
			if (!database)
				return null;
			
			var statement:SQLStatement	= new SQLStatement();
			statement.sqlConnection		= database;
			statement.text				= "SELECT * FROM Products";// WHERE partNumber = \"599-02039A\"";
			statement.itemClass			= AbstractSiemensProduct;
			
			statement.execute();
			var result:SQLResult = statement.getResult();
			return result.data;
		}
	}
}