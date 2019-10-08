package Swizzard.System.Search.Commands
{
	import flash.data.SQLResult;
	import flash.net.Responder;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.CrossReferenceProduct;
	import Swizzard.Data.Proxies.DataProxy;
	
	import j2inn.Data.Query.QueryGroup;
	import j2inn.Data.Query.QueryOrderBy;
	import j2inn.Data.Query.SQLDataQuery;
	import j2inn.Data.Query.SQLQueryItem;
	import j2inn.Data.Query.SubTable;
	import j2inn.Data.Query.Enumeration.QueryOperations;
	import j2inn.Data.Query.Enumeration.QueryPredicates;
	import j2inn.Data.Query.Enumeration.QueryType;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;

	/**
	 * Search Box Command
	 * 
	 * this command is used by the search box to search for products by their part number
	 *  
	 * @author michael
	 * 
	 */	
	
	public class SearchBoxCommand extends SimpleCommand
	{
		public static const SEARCH_PART_NUMBER:String		= "searchPartNumber";
		public static const SEARCH_BOX_RESULTS:String		= "searchBoxResults";
		public static const SEARCH_CROSSREF_RESULTS:String	= "searchReferenceResults";
		
		
		public function SearchBoxCommand()
		{
			super();
		}
		
		
		override public function execute(notification:INotification):void
		{
			switch (notification.getName())
			{
				case SEARCH_PART_NUMBER:
				{
					var partNumber:String	= notification.getBody() as String;
					
					var query:SQLDataQuery	= new SQLDataQuery();
					query.tableName			= "Products";
					query.queryType			= QueryType.SELECT;
					query.resultType		= AbstractSiemensProduct;
					query.order				= QueryOrderBy.Build("partNumber");
					query.projection		= ["partNumber", "actualPartNumber", "productType", "extendedProductType", "subProductPartNumber", "subProductPrice", "price"];
					query.addItem(SQLQueryItem.Build("partNumber", QueryOperations.LIKE, partNumber, "t"));
					
					// Don't Show Obsolete Parts
					query.addItem(SQLQueryItem.Build("isObsolete", QueryOperations.EQUAL_TO, 0, "t"));
					
					// Do not show assembly only products that are not assemblies (Fixes: SVST-11)
					var assemblyOnly:QueryGroup	= new QueryGroup();
					assemblyOnly.predicate		= QueryPredicates.AND;
					assemblyOnly.addItem(SQLQueryItem.Build("assemblyOnly", QueryOperations.EQUAL_TO, 0, "t"));
					assemblyOnly.addItem(SQLQueryItem.Build("productType", QueryOperations.EQUAL_TO, 3, "t", QueryPredicates.OR));
					query.addItem(assemblyOnly);
					
					var proxy:DataProxy	= facade.retrieveProxy(DataProxy.NAME) as DataProxy;
					proxy.queryData(query, new Responder(partsResultHandler, statusHandler));
					
					if (partNumber && partNumber.length > 1)
					{
						// Only Query Cross Reference if we have more than 1 character.
						// Cross Reference
						var referenceQuery:SQLDataQuery	= new SQLDataQuery();
						referenceQuery.tableName		= "CrossReference";
						referenceQuery.queryType		= QueryType.SELECT;
						referenceQuery.resultType		= CrossReferenceProduct;
						referenceQuery.order			= QueryOrderBy.Build("competitorPartNumber");
						//referenceQuery.order			= QueryOrderBy.Build("partNumber", "ASC", "p");
																
						var productTable:SubTable	= new SubTable();
						productTable.tableName		= "Products";
						productTable.tableColumn	= "partNumber";
						productTable.tableAlias		= "p";
						productTable.foreignColumn	= "actualPartNumber";
						productTable.projection		= ["partNumber", "actualPartNumber", "productType", "extendedProductType"];
						
						referenceQuery.addTable(productTable);
						referenceQuery.addItem(SQLQueryItem.Build("competitorPartNumber", QueryOperations.LIKE, partNumber, "t"));
						
						proxy.queryData(referenceQuery, new Responder(crossReferenceItemsHandler, statusHandler));
					}
					else
					{
						// Send Empty List
						sendNotification(SEARCH_CROSSREF_RESULTS, new ArrayCollection());
					}
					break;
				}
			}
		}
		
		
		private function crossReferenceItemsHandler(result:SQLResult):void
		{
			var partTable:Dictionary		= new Dictionary();
			var resultList:ArrayCollection	= new ArrayCollection();
			
			// Process Parts
			for each (var part:CrossReferenceProduct in result.data)
			{
				if (!partTable.hasOwnProperty(part.competitorPartNumber))
				{
					partTable[part.competitorPartNumber]	= part;
					resultList.addItem(part);
					
					part.siemensParts.addItem(part.toAbstract());
				}
				else
				{
					var competitorItem:CrossReferenceProduct	= partTable[part.competitorPartNumber];
					competitorItem.siemensParts.addItem(part.toAbstract());
				}
			}
			
			sendNotification(SEARCH_CROSSREF_RESULTS, resultList);
		}
		
		
		private function partsResultHandler(result:SQLResult):void
		{
			sendNotification(SEARCH_BOX_RESULTS, new ArrayCollection(result.data));
		} 
		
		
		private function statusHandler(event:Object):void
		{}
	}
}