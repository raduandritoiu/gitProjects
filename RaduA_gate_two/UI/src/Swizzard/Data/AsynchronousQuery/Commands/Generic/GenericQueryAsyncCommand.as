package Swizzard.Data.AsynchronousQuery.Commands.Generic
{
	import flash.data.SQLResult;
	import flash.errors.SQLError;
	import flash.net.Responder;
	
	import Swizzard.Data.AsynchronousQuery.Token.BaseQueryToken;
	import Swizzard.Data.Proxies.DataProxy;
	
	import j2inn.Data.Query.SQLDataQuery;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.AsyncCommand;
	
	import utils.LogUtils;
	
	
	public class GenericQueryAsyncCommand extends AsyncCommand
	{
		protected var token:BaseQueryToken;
		protected var lastQuery:SQLDataQuery;
		
		
		public function GenericQueryAsyncCommand() {
			super();
		}
			
		
		/**
		 * This lauches the command.
		 */
		override public function execute(notification:INotification):void {
			token = notification.getBody() as BaseQueryToken;
			if (!shouldQuery()) {
				commandComplete();
				return;
			}
			startQuery();
		}
		
		
		/**
		 * Starts The query
		 */
		protected function startQuery():void {			
			var sqlQuery:SQLDataQuery = generateSQLQuery();
			lastQuery = sqlQuery;
			
			// Check Cache
			var queryResult:Array = getCachedResults();
			
			var ch:Boolean = (queryResult != null);
			LogUtils.Debug("=============== (GENERIC " + token.target + " ) query - (cached " + ch + ") ===============");
			if (ch) {
				LogUtils.Debug("\t Query was CACHED");
			}
			else {
				LogUtils.Debug("\t Query was NOT");
			}
			LogUtils.Debug(sqlQuery.toSql());
			LogUtils.Debug("=====================================================================");
			
			if (queryResult != null) {
				// Query didn't change. Use cached
				concatenateResultsAndComplete(queryResult);
			}
			else {
				var proxy:DataProxy	= facade.retrieveProxy(DataProxy.NAME) as DataProxy;
				var responder:Responder	= new Responder(queryResultHandler, queryErrorHandler);
				token.numQueries++;	
				proxy.queryData(sqlQuery, responder);
			}
		}
		
		
		/**
		 * Query Error Handler
		 */		
		protected function queryErrorHandler(error:SQLError):void {
			// Error Querying Type
			LogUtils.Error("Query Error: " + error.details);
			commandComplete();
		}
		
		
		/**
		 * Query Result Handler
		 * Asynchronous function which handles the results from the SQL Query
		 */		
		protected function queryResultHandler(result:SQLResult):void {			
			// Only cache valve queries			
			if (result.data != null) {
				// get results
				var queryResults:Array = result.data;
				// Cache last result - this (implicitly) removes other caches for this type
				setCachedResults(queryResults);
				// concatenate results
				concatenateResultsAndComplete(queryResults);
			}
			else {
				commandComplete();
			}
		}
		
		
		/**
		 * Concatenate Results and Complete
		 * This function concatenates the query's results with the existing results and completes the command
		 */		
		protected function concatenateResultsAndComplete(results:Array):void {
			if (!token.results) {
				token.results = results;
			}
			else if (results) {
				token.results = token.results.concat(results);
			}
			commandComplete();
		}
		
		
		/**
		 * This may be overritten.
		 */
		protected function shouldQuery():Boolean {
			return true;
		}
		
		
		/**
		 * This may be overritten.
		 */
		protected function getCachedResults():Array {
			return null;
		}
		
		
		/**
		 * This may be overritten.
		 */
		protected function setCachedResults(queryResults:Array):void {
			// do not cache
		}

		
		/**
		 * This must be overritten.
		 */
		protected function generateSQLQuery():SQLDataQuery {
			return null;	
		}
	}
}