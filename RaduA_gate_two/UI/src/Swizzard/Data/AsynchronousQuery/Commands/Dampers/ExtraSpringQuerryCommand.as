package Swizzard.Data.AsynchronousQuery.Commands.Dampers
{
	import Swizzard.Data.AsynchronousQuery.Cashing.QueryCache;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperControlSignal;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperPositionFeedback;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;
	import Swizzard.Data.Models.Query.DamperQueryModel;
	
	import j2inn.Data.Query.QueryGroup;
	import j2inn.Data.Query.SQLDataQuery;
	import j2inn.Data.Query.SQLQueryItem;
	import j2inn.Data.Query.Enumeration.QueryOperations;
	import j2inn.Data.Query.Enumeration.QueryPredicates;
	
	
	public class ExtraSpringQuerryCommand extends BaseDamperQueryCommand
	{
		public function ExtraSpringQuerryCommand()
		{
			super();
			damperType = DamperType.SPRING_RETURN;
		}
		
		
		override protected function shouldQuery():Boolean {
			var flag:Boolean = super.shouldQuery();
			if (!flag) {
				return false;
			}
			var queryModel:DamperQueryModel = token.queryModel as DamperQueryModel;
			if (queryModel.positionFeedback != DamperPositionFeedback.NO_FEED) {
				return false;
			}
			if (queryModel.controlSignal == DamperControlSignal.PT_2 || 
				queryModel.controlSignal == DamperControlSignal.Floating || 
				queryModel.controlSignal == DamperControlSignal.Floating_PT_2) {
				return false;
			}
			return true;
		}
		
		
		override protected function getCachedResults():Array {
			return QueryCache.getInstance().getDamperQueryResult(lastQuery, damperType + BaseDamperQueryCommand.EXTRA);
		}
		
		override protected function setCachedResults(queryResults:Array):void {
			QueryCache.getInstance().setDamperQueryResult(lastQuery, damperType + BaseDamperQueryCommand.EXTRA, queryResults);
		}
		
		
		override protected function getPositionFeedBack(queryModel:DamperQueryModel):int {
			return DamperPositionFeedback.WITH_FEED;
		}
		
		
		override protected function generateSQLQuery():SQLDataQuery {
			var query:SQLDataQuery = super.generateSQLQuery();
			var queryModel:DamperQueryModel = token.queryModel as DamperQueryModel;
			// this is for all of them
			if (queryModel.controlSignal <= 0) {
				var qGr:QueryGroup = new QueryGroup();
				qGr.predicate = QueryPredicates.AND;
				qGr.addItem(SQLQueryItem.Build("controlSignal & " + DamperControlSignal.VDC_0_10, QueryOperations.EQUAL_TO, DamperControlSignal.VDC_0_10, "t", QueryPredicates.OR));
				qGr.addItem(SQLQueryItem.Build("controlSignal & " + DamperControlSignal.VDC_2_10, QueryOperations.EQUAL_TO, DamperControlSignal.VDC_2_10, "t", QueryPredicates.OR));
				qGr.addItem(SQLQueryItem.Build("controlSignal & " + DamperControlSignal.VDC_0_2_10, QueryOperations.EQUAL_TO, DamperControlSignal.VDC_0_2_10, "t", QueryPredicates.OR));
				query.addItem(qGr);
			}
			return query;
		}
	}
}