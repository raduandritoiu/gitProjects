package Swizzard.Data.AsynchronousQuery.Commands.Pneumatics
{
	import Swizzard.Data.AsynchronousQuery.Cashing.QueryCache;
	import Swizzard.Data.AsynchronousQuery.Commands.Generic.GenericQueryAsyncCommand;
	import Swizzard.Data.Models.SiemensPneumatic;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticSelection;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticType;
	import Swizzard.Data.Models.Query.PneumaticQueryModel;
	
	import j2inn.Data.Query.QueryGroup;
	import j2inn.Data.Query.QueryOrderBy;
	import j2inn.Data.Query.SQLDataQuery;
	import j2inn.Data.Query.SQLQueryItem;
	import j2inn.Data.Query.SubTable;
	import j2inn.Data.Query.Enumeration.QueryOperations;
	import j2inn.Data.Query.Enumeration.QueryPredicates;
	import j2inn.Data.Query.Enumeration.QueryType;
	
	import utils.LogUtils;
	
	
	public class BasePneumaticQueryCommand extends GenericQueryAsyncCommand
	{
		protected var pneumaticType:int;
		

		public function BasePneumaticQueryCommand() {
			super();
		}
		
		
		override protected function shouldQuery():Boolean {
			var queryModel:PneumaticQueryModel = token.queryModel as PneumaticQueryModel;
			return queryModel.selection != PneumaticSelection.NONE;
		}
		
		
		override protected function getCachedResults():Array {
			return QueryCache.getInstance().getDamperQueryResult(lastQuery, pneumaticType);
		}
		
		
		override protected function setCachedResults(queryResults:Array):void {
			QueryCache.getInstance().setDamperQueryResult(lastQuery, pneumaticType, queryResults);
		}

		
		/**
		 * Generate Pneumatic Query
		 * This function is used to generate the Pneumatic SQL Query 
		 */		
		override protected function generateSQLQuery():SQLDataQuery {
			var queryModel:PneumaticQueryModel = token.queryModel as PneumaticQueryModel;
			// hard code ordering according to partNumber and torque (just how they appera in the XLS table file)
			var order:QueryOrderBy	= QueryOrderBy.Build("p.partNumber");
			
			// generate the sql query
			var query:SQLDataQuery	= new SQLDataQuery();
			query.queryType			= QueryType.SELECT;
			query.tableName			= "Pneumatics";
			query.resultType		= SiemensPneumatic;
			query.itemsPerPage		= 8000;
			query.order				= order;
			
			// filter for obsolete products
			query.addItem(SQLQueryItem.Build("isObsolete", QueryOperations.EQUAL_TO, 0, "p"));
			
			// Intersect with Products Table over partNumber
			var subTable:SubTable = SubTable.Build("Products", QueryOperations.EQUAL_TO, "partNumber", "partNumber");
			query.addTable(subTable);
			
			// filter for Price different than 0 (this means greater)
			query.addItem(SQLQueryItem.Build("price", QueryOperations.NOT, 0, "p", QueryPredicates.AND));
			
			// filter for penumatic Type
			if (queryModel.pneumType > 0) {
				query.addItem(SQLQueryItem.Build("type", QueryOperations.EQUAL_TO, queryModel.pneumType, "t", QueryPredicates.AND));
			}
			else if (queryModel.selection == PneumaticSelection.HIGH_FORCE){
				var highGroup:QueryGroup = new QueryGroup();
				highGroup.predicate	= QueryPredicates.AND;
				highGroup.addItem(SQLQueryItem.Build("type", QueryOperations.EQUAL_TO, PneumaticType.HIGH_FORCE, "t", QueryPredicates.OR));
				highGroup.addItem(SQLQueryItem.Build("type", QueryOperations.EQUAL_TO, PneumaticType.TANDEM, "t", QueryPredicates.OR));
				query.addItem(highGroup);
			}
			
			// filter for stroke
			if (queryModel.stroke > 0) {
				query.addItem(SQLQueryItem.Build("stroke", QueryOperations.EQUAL_TO, queryModel.stroke, "t", QueryPredicates.AND));
			}
			
			// filter for spring range
			if (queryModel.springRange > 0) {
				query.addItem(SQLQueryItem.Build("springRange", QueryOperations.EQUAL_TO, queryModel.springRange, "t", QueryPredicates.AND));
			}
			
			// filter for positioning relay
			if (queryModel.maxThrust > 0) {
				query.addItem(SQLQueryItem.Build("maxThrust_18", QueryOperations.EQUAL_TO, queryModel.maxThrust, "t", QueryPredicates.AND));
			}
			
			// filter for ULCert
			if (queryModel.ULCert > 0) {
				query.addItem(SQLQueryItem.Build("ULCert", QueryOperations.EQUAL_TO, queryModel.ULCert, "t", QueryPredicates.AND));
			}
			
			// filter for positioning relay
			if (queryModel.posRelay > 0) {
				query.addItem(SQLQueryItem.Build("posRelay", QueryOperations.EQUAL_TO, queryModel.posRelay, "t", QueryPredicates.AND));
			}
			
			// filter for mounting style
			if (queryModel.mountingStyle > 0) {
				query.addItem(SQLQueryItem.Build("mountingStyle", QueryOperations.EQUAL_TO, queryModel.mountingStyle, "t", QueryPredicates.AND));
			}
			
			LogUtils.Debug(queryModel);
			return query;	
		}
	}
}