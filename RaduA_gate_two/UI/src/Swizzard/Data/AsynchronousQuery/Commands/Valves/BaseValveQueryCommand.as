package Swizzard.Data.AsynchronousQuery.Commands.Valves
{
	import com.adobe.crypto.MD5;
	
	import flash.data.SQLResult;
	import flash.errors.SQLError;
	import flash.net.Responder;
	
	import Swizzard.Data.AsynchronousQuery.Enumeration.ProductQueryType;
	import Swizzard.Data.AsynchronousQuery.Token.ValveQueryToken;
	import Swizzard.Data.Models.SiemensActuator;
	import Swizzard.Data.Models.SiemensValve;
	import Swizzard.Data.Models.Query.ValveQueryModel;
	import Swizzard.Data.Proxies.DataProxy;
	
	import j2inn.Data.Query.QueryOrderBy;
	import j2inn.Data.Query.SQLDataQuery;
	import j2inn.Data.Query.SQLQueryItem;
	import j2inn.Data.Query.SubTable;
	import j2inn.Data.Query.Enumeration.QueryOperations;
	import j2inn.Data.Query.Enumeration.QueryOrderDirection;
	import j2inn.Data.Query.Enumeration.QueryPredicates;
	import j2inn.Data.Query.Enumeration.QueryType;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.AsyncCommand;
	
	import utils.LogUtils;
	
	/**
	 * Valve Query Command
	 *  
	 * @author michael
	 * 
	 */	
	public class BaseValveQueryCommand extends AsyncCommand
	{
		protected var token:ValveQueryToken;
		protected var valveType:uint;
		
		protected var lastQuery:SQLDataQuery;
		
		protected var lastValveResults:Array;
		protected var lastActuatorResults:Array;
		
		
		public function BaseValveQueryCommand()
		{
			super();
		}
		
		
		override public function execute(notification:INotification):void
		{
			token = notification.getBody() as ValveQueryToken;
			
			if (!token.valveQueryModel.valve.valveTypes.some(isInType))
			{
				commandComplete();
				return;
			}
												
			switch (token.target)
			{
				case ProductQueryType.VALVES:
					startValueQuery();
					break;
				
				case ProductQueryType.ACTUATORS:
					// Do not requery valves. requested by byran.
					if (token.valve.info.type != valveType)
					{
						commandComplete();
						break;
					}
					startActuatorQuery();
					break;
			}
		}
		
		
		/**
		 * @private
		 *  
		 * @param type
		 * @param index
		 * @param arr
		 * @return 
		 */		
		private function isInType(type:uint, index:uint, arr:Array):Boolean
		{
			return (type == valveType);
		}

		
		private function hasValues(item:*, index:int, coll:*):Boolean
		{
			return (item is SiemensValve);
		}
		

		protected function startValueQuery():void
		{			
			var valveQuery:SQLDataQuery	= generateValveQuery();
			lastQuery = valveQuery;
			
			// Check Cache
			var queryHash:String = MD5.hash(valveQuery.toSql());
			var valveQueryModel:ValveQueryModel = token.valveQueryModel.valve;
			
			if (valveQueryModel.getQueryCache().hasOwnProperty(valveType + ":" + queryHash))
			{
				var isActuatorQuery:Boolean	= (token.target == ProductQueryType.ACTUATORS);
				
				LogUtils.Debug(" *************** Cashed (VALVE) QUERY - (" + queryHash + ") ***************");
				LogUtils.Debug(valveQuery.toSql());
				LogUtils.Debug(" *********************************************************************");
				
				// Query didn't change. use cached
				lastValveResults = valveQueryModel.getQueryCache()[valveType + ":" + queryHash] as Array;
				concatenateResultsAndComplete(lastValveResults, ProductQueryType.VALVES, !isActuatorQuery);
				
				// Actuator Query required valves. Now execute actuator query.
				if (isActuatorQuery)
					startActuatorQuery();
			}
			else
			{
				var proxy:DataProxy		= facade.retrieveProxy(DataProxy.NAME) as DataProxy;
				var responder:Responder	= new Responder(queryResultHandler, queryErrorHandler);
				
				LogUtils.Debug(" *************** Preform (VALVE) QUERY - (" + queryHash + ") ***************");
				LogUtils.Debug(valveQuery.toSql());
				LogUtils.Debug(" *********************************************************************");
				
				proxy.queryData(valveQuery, responder);
				token.numQueries++;	
			}
		}
		
		
		protected function startActuatorQuery():void
		{
			var valve:SiemensValve = token.valve;
			if (this.valveType != valve.info.type)
			{
				// Invalid valve for actuator query
				skip();
				return;
			}
			
			var proxy:DataProxy		= facade.retrieveProxy(DataProxy.NAME) as DataProxy;
			var responder:Responder	= new Responder(queryResultHandler, queryErrorHandler);
			var actuatorQuery:SQLDataQuery = generateActuatorQuery();
			
			LogUtils.Debug(" *************** Preform (ACTUATOR) QUERY - ***************");
			LogUtils.Debug(actuatorQuery.toSql());
			LogUtils.Debug(" *********************************************************************");

			proxy.queryData(actuatorQuery, responder);
		}
		
		
		protected function shouldRequeryValues():Boolean
		{
			return token.requeryAllValves;
		}
		
		
		/**
		 * 
		 */		
		protected function skip():void
		{
			commandComplete();
		}
		
		
		/**
		 * Query Error Handler
		 *  
		 * @param error
		 */		
		protected function queryErrorHandler(error:SQLError):void
		{
			// Error Querying Type
			LogUtils.Error("Query Error: " + error.details);
			commandComplete();
		}
		
		
		/**
		 * Query Result Handler
		 * 
		 * Asynchronous function which handles the results from the SQL Query
		 *  
		 * @param result
		 */		
		protected function queryResultHandler(result:SQLResult):void
		{			
			// Only cache valve queries			
			if (result.data && result.data.some(hasValues))
			{
				var isActuatorQuery:Boolean	= (token.target == ProductQueryType.ACTUATORS);
				var valveQueryModel:ValveQueryModel	= token.valveQueryModel.valve;
				
				// Remove last cache for type
				for (var key:String in valveQueryModel.getQueryCache())
				{
					var parts:Array	= key.split(":");
					
					if (parts[0] == valveType)
						delete valveQueryModel.getQueryCache()[key];
				}
				
				// Cache last result
				var queryHash:String	= MD5.hash(lastQuery.toSql());
				lastValveResults		= result.data;
				valveQueryModel.getQueryCache()[valveType + ":" + queryHash] = lastValveResults;
				
				concatenateResultsAndComplete(lastValveResults, ProductQueryType.VALVES, !isActuatorQuery);
				
				if (isActuatorQuery)
				{
					// Actuator Query required valves. Now execute actuator query.
					startActuatorQuery();
				}
			}
			else
			{
				// Actuators
				lastActuatorResults	= result.data;
				concatenateResultsAndComplete(result.data, ProductQueryType.ACTUATORS);
			}
		}
		
		
		/**
		 * Concatenate Results and Complete
		 * 
		 * This function concatenates the query's results with the existing results and completes the command
		 *  
		 * @param results
		 */		
		protected function concatenateResultsAndComplete(results:Array, resultType:String, complete:Boolean = true):void
		{
			switch (resultType)
			{
				case ProductQueryType.VALVES:
					if (!token.results)
						token.results	= results;
					else if (results)
						token.results	= token.results.concat(results);
					break;
					
				case ProductQueryType.ACTUATORS:
					if (!token.actuatorResults)
						token.actuatorResults	= results;
					else if (results)
						token.actuatorResults	= token.actuatorResults.concat(results);
					break;
			}
			
			if (complete)
				commandComplete();
		}
		
		
		/**
		 * Generate Valve Query
		 * 
		 * This function is used to generate the Valve SQL Query 
		 * @return 
		 */		
		protected function generateValveQuery():SQLDataQuery
		{
			var form:ValveQueryModel	= token.valveQueryModel.valve;
			var order:QueryOrderBy		= form.order;
			
			if (!order)
				order	= QueryOrderBy.Build("cvu, p.price", QueryOrderDirection.ASCENDING, "t");
				
			var query:SQLDataQuery	= new SQLDataQuery();
			query.queryType			= QueryType.SELECT;
			query.tableName			= "Valves";
			query.resultType		= SiemensValve;
			query.itemsPerPage		= 8000;
			query.order				= order;
						
			// Don't Show Obsolete Parts
			query.addItem(SQLQueryItem.Build("isObsolete", QueryOperations.EQUAL_TO, 0, "p"));
			
			// Intersect with Products Table @ partNumber
			var subTable:SubTable	= SubTable.Build("Products", QueryOperations.EQUAL_TO, "partNumber", "partNumber");
			query.addTable(subTable);
			
			// Price Not 0
			query.addItem(SQLQueryItem.Build("price", QueryOperations.NOT, 0, "p", QueryPredicates.AND));
			
			// Valve Type
			query.addItem(SQLQueryItem.Build("type", QueryOperations.EQUAL_TO, valveType, "t", QueryPredicates.AND));
			
			return query;	
		}
		
		
		/**
		 * Generate Actuator Query
		 * 
		 * This function is used to generate the Actuator SQL Query
		 *  
		 * @return 
		 */		
		protected function generateActuatorQuery():SQLDataQuery
		{
			var valve:SiemensValve		= token.valve;
						
			var query:SQLDataQuery	= new SQLDataQuery();
			query.tableName			= "Actuators";
			query.queryType			= QueryType.SELECT;
			query.resultType		= SiemensActuator;
			query.order				= QueryOrderBy.Build("partNumber", "ASC", "p");
			
			var assemblyTable:SubTable	= new SubTable();
			assemblyTable.tableName		= "Assemblies";	
			assemblyTable.tableColumn	= "actuatorPartNumber";
			assemblyTable.tableAlias	= "a";
			assemblyTable.foreignColumn	= "partNumber";
			assemblyTable.projection	= ["closeOff as closeOff"];
			query.addTable(assemblyTable);
			
			var productsTable:SubTable	= new SubTable();
			productsTable.tableName		= "Products";
			productsTable.tableColumn	= "partNumber";
			productsTable.tableAlias	= "p";
			productsTable.foreignColumn	= "partNumber";
			//productsTable.projection	= ["price", "productType"];
			query.addTable(productsTable);
			
			// Don't Show Obsolete Parts
			query.addItem(SQLQueryItem.Build("isObsolete", QueryOperations.EQUAL_TO, 0, "p"));
			query.addItem(SQLQueryItem.Build("valvePartNumber", QueryOperations.EQUAL_TO, valve.partNumber, "a"));
			
			return query;
		}
	}
}