package Swizzard.Data.AsynchronousQuery.Commands.Dampers
{
	import Swizzard.Data.AsynchronousQuery.Cashing.QueryCache;
	import Swizzard.Data.AsynchronousQuery.Commands.Generic.GenericQueryAsyncCommand;
	import Swizzard.Data.Models.SiemensDamper;
	import Swizzard.Data.Models.Query.DamperQueryModel;
	import Swizzard.System.Utils.DamperTorqueUtil;
	
	import j2inn.Data.Query.QueryOrderBy;
	import j2inn.Data.Query.SQLDataQuery;
	import j2inn.Data.Query.SQLQueryItem;
	import j2inn.Data.Query.SubTable;
	import j2inn.Data.Query.Enumeration.QueryOperations;
	import j2inn.Data.Query.Enumeration.QueryOrderDirection;
	import j2inn.Data.Query.Enumeration.QueryPredicates;
	import j2inn.Data.Query.Enumeration.QueryType;
	import j2inn.Data.QueryExtend.Enumeration.QueryOperationsExtended;
	
	
	public class BaseDamperQueryCommand extends GenericQueryAsyncCommand
	{
		public static const EXTRA:uint = 452345;
		
		
		protected var damperType:uint;
		
		
		public function BaseDamperQueryCommand() {
			super();
		}
		
		
		override protected function shouldQuery():Boolean {
			// test if the query model has the product type associated with this command
			// maybe test for cahnged properties
			var damperTypes:Array = (token.queryModel as DamperQueryModel).damperTypes;
			for each (var type:uint in damperTypes) {
				if (type == damperType) {
					return true;
				}
			}
			return false;
		}

		
		override protected function getCachedResults():Array {
			return QueryCache.getInstance().getDamperQueryResult(lastQuery, damperType);
		}
		
		override protected function setCachedResults(queryResults:Array):void {
			QueryCache.getInstance().setDamperQueryResult(lastQuery, damperType, queryResults);
		}
		
		
		/**
		 * This function is used to generate the Damper SQL Query
		 */		
		override protected function generateSQLQuery():SQLDataQuery {
			var queryModel:DamperQueryModel = token.queryModel as DamperQueryModel;
			// hard code ordering according to partNumber and torque (just how they appera in the XLS table file)
			var order:QueryOrderBy			= QueryOrderBy.Build("t.torque DESC, p.partNumber", QueryOrderDirection.ASCENDING);
			
			// generate the sql query
			var query:SQLDataQuery	= new SQLDataQuery();
			query.queryType			= QueryType.SELECT;
			query.tableName			= "Dampers";
			query.resultType		= SiemensDamper;
			query.itemsPerPage		= 8000;
			query.order				= order;
						
			// filter for obsolete products
			 query.addItem(SQLQueryItem.Build("isObsolete", QueryOperations.EQUAL_TO, 0, "p"));
			
			// Intersect with Products Table over partNumber
			var subTable:SubTable = SubTable.Build("Products", QueryOperations.EQUAL_TO, "partNumber", "partNumber");
			query.addTable(subTable);
			
			// filter for this specific damper Type
			query.addItem(SQLQueryItem.Build("type", QueryOperations.EQUAL_TO, damperType, "t", QueryPredicates.AND));
			
			// filter for Price different than 0 (this means greater)
			query.addItem(SQLQueryItem.Build("price", QueryOperations.NOT, 0, "p", QueryPredicates.AND));
			
			
			// because (for the moment) all dampers have the same properties: torque and control signal
			
			// filter for torque
			if (queryModel.torque > 0) {
				var queryTorque:Number = DamperTorqueUtil.AdjustSearchTorque(damperType, queryModel.torque);
				query.addItem(SQLQueryItem.Build("torque", QueryOperationsExtended.GREATER_OR_EQUAL_THAN, queryTorque, "t", QueryPredicates.AND));
			}
			
			// filter for control signal
			if (queryModel.controlSignal > 0) {
				query.addItem(SQLQueryItem.Build("controlSignal & " + queryModel.controlSignal, QueryOperations.EQUAL_TO, queryModel.controlSignal, "t", QueryPredicates.AND));
			}
			
			// filter for system supply
			if (queryModel.systemSupply > 0) {
				query.addItem(SQLQueryItem.Build("systemSupply & " + queryModel.systemSupply, QueryOperations.EQUAL_TO, queryModel.systemSupply, "t", QueryPredicates.AND));
			}
			
			// filter for plenum rating
			if (queryModel.plenumRating > 0) {
				query.addItem(SQLQueryItem.Build("plenumRating", QueryOperations.EQUAL_TO, queryModel.plenumRating, "t", QueryPredicates.AND));
			}
			
			// filter for auxilary switch
			if (queryModel.auxilarySwitch > 0) {
				query.addItem(SQLQueryItem.Build("auxilarySwitch", QueryOperations.EQUAL_TO, queryModel.auxilarySwitch, "t", QueryPredicates.AND));
			}
			
			// filter for position feedback
			var positionFeedback:int = getPositionFeedBack(queryModel);
			if (positionFeedback > 0) {
				query.addItem(SQLQueryItem.Build("positionFeedback", QueryOperations.EQUAL_TO, positionFeedback, "t", QueryPredicates.AND));
			}
			
			// filter for system supply
			if (queryModel.scalableSignal > 0) {
				query.addItem(SQLQueryItem.Build("scalableSignal", QueryOperations.EQUAL_TO, queryModel.scalableSignal, "t", QueryPredicates.AND));
			}
			
			return query;	
		}
		
		
		protected function getPositionFeedBack(queryModel:DamperQueryModel):int {
			return queryModel.positionFeedback;
		}
	}
}