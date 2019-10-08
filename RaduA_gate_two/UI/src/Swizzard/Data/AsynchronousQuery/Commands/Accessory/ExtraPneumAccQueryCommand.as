package Swizzard.Data.AsynchronousQuery.Commands.Accessory
{
	import Swizzard.Data.AsynchronousQuery.Commands.Generic.GenericQueryAsyncCommand;
	import Swizzard.Data.Models.SiemensPneumatic;
	import Swizzard.Data.Models.Query.AccessoryQueryModel;
	import Swizzard.Data.Models.Query.PneumaticQueryModel;
	
	import j2inn.Data.Query.QueryOrderBy;
	import j2inn.Data.Query.SQLDataQuery;
	import j2inn.Data.Query.SQLQueryItem;
	import j2inn.Data.Query.SubTable;
	import j2inn.Data.Query.Enumeration.QueryOperations;
	import j2inn.Data.Query.Enumeration.QueryOrderDirection;
	import j2inn.Data.Query.Enumeration.QueryPredicates;
	import j2inn.Data.Query.Enumeration.QueryType;
	
	
	public class ExtraPneumAccQueryCommand extends GenericQueryAsyncCommand
	{
		public function ExtraPneumAccQueryCommand() {
			super();
		}
		
		
		override protected function shouldQuery():Boolean {
			var partNumber:String = (token.queryModel as AccessoryQueryModel).specialPneumaticPart;
			if (partNumber == AccessoryQueryModel.VOID_PART_NUMBER) return false;
			if (partNumber == "") return false;
			if (partNumber == null) return false;
			return true;
		}
		
		
		override protected function generateSQLQuery():SQLDataQuery {
			var queryModel:AccessoryQueryModel = token.queryModel as AccessoryQueryModel;
			// hard code ordering according to partNumber and torque (just how they appera in the XLS table file)
			var order:QueryOrderBy			= QueryOrderBy.Build("partNumber, p.price", QueryOrderDirection.ASCENDING, "t");
			
			// generate the sql query
			var query:SQLDataQuery	= new SQLDataQuery();
			query.queryType			= QueryType.SELECT;
			query.tableName			= "Pneumatics";
			query.resultType		= SiemensPneumatic;
			query.itemsPerPage		= 8000;
			query.order				= order;
						
			// Intersect with Products Table over partNumber
			var subTable:SubTable = SubTable.Build("Products", QueryOperations.EQUAL_TO, "partNumber", "partNumber");
			query.addTable(subTable);
			
			// filter for obsolete products
			query.addItem(SQLQueryItem.Build("isObsolete", QueryOperations.EQUAL_TO, 0, "p"));
			
			// filter for this specific damper part number
			query.addItem(SQLQueryItem.Build("partNumber", QueryOperations.EQUAL_TO, queryModel.specialPneumaticPart, "p", QueryPredicates.AND));
			
			return query;	
		}
	}
}