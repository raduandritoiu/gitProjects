package Swizzard.Data.AsynchronousQuery.Commands.Accessory
{
	import Swizzard.Data.AsynchronousQuery.Cashing.QueryCache;
	import Swizzard.Data.AsynchronousQuery.Commands.Generic.GenericQueryAsyncCommand;
	import Swizzard.Data.Models.SiemensAccessory;
	import Swizzard.Data.Models.Query.AccessoryQueryModel;
	
	import j2inn.Data.Query.QueryOrderBy;
	import j2inn.Data.Query.SQLDataQuery;
	import j2inn.Data.Query.SQLQueryItem;
	import j2inn.Data.Query.SubTable;
	import j2inn.Data.Query.Enumeration.QueryOperations;
	import j2inn.Data.Query.Enumeration.QueryOrderDirection;
	import j2inn.Data.Query.Enumeration.QueryPredicates;
	import j2inn.Data.Query.Enumeration.QueryType;
	
	
	public class BaseAccessoryQueryCommand extends GenericQueryAsyncCommand
	{
		public function BaseAccessoryQueryCommand() {
			super();
		}
		
		
		override protected function getCachedResults():Array {
			return QueryCache.getInstance().getAccessoryQueryResult(token.queryModel as AccessoryQueryModel);
		}
		
		
		override protected function setCachedResults(queryResults:Array):void {
			QueryCache.getInstance().setAccessoryQueryResult(token.queryModel as AccessoryQueryModel, queryResults);
		}
		
		
		/**
		 * This function is used to generate the Accessory SQL Query 
		 */		
		override protected function generateSQLQuery():SQLDataQuery {
			var queryModel:AccessoryQueryModel = token.queryModel as AccessoryQueryModel;
			// hard code ordering according to partNumber and torque (just how they appera in the XLS table file)
			var order:QueryOrderBy			= QueryOrderBy.Build("partNumber, p.price", QueryOrderDirection.ASCENDING, "t");
			
			// generate the sql query
			var query:SQLDataQuery	= new SQLDataQuery();
			query.queryType			= QueryType.SELECT;
			query.tableName			= "AccessoryKits";
			query.resultType		= SiemensAccessory;
			query.itemsPerPage		= 8000;
			query.order				= order;
			
			// Intersect with Crossreference Table over partNumber
			subTable = SubTable.Build(queryModel.crossTable, QueryOperations.EQUAL_TO, "accessoryPartNumber", "partNumber");
			subTable.tableAlias = "c";
			query.addTable(subTable);

			// Intersect with Products Table over partNumber
			var subTable:SubTable = SubTable.Build("Products", QueryOperations.EQUAL_TO, "partNumber", "partNumber");
			query.addTable(subTable);
					
			// filter for this specific damper part number
			query.addItem(SQLQueryItem.Build("productPartNumber", QueryOperations.EQUAL_TO, queryModel.productPartNumber, "c", QueryPredicates.AND));
			
			return query;	
		}
	}
}