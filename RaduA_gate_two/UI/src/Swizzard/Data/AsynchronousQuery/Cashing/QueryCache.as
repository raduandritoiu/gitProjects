package Swizzard.Data.AsynchronousQuery.Cashing
{
	import com.adobe.crypto.MD5;
	
	import flash.utils.Dictionary;
	
	import Swizzard.Data.Models.Query.AccessoryQueryModel;
	
	import j2inn.Data.Query.SQLDataQuery;

	public class QueryCache
	{
		private var damperCacheMap:Dictionary;
		private var accessoryCacheMap:Dictionary;
		private var accessoryCacheSize:int = 15;
		
		
		public function QueryCache() {
			damperCacheMap = new Dictionary();
			accessoryCacheMap = new Dictionary();
		}
		
		
		public function getAccessoryQueryResult(queryModel:AccessoryQueryModel):Array {
			var queryHash:String = queryModel.productPartNumber;
			
			if (accessoryCacheMap.hasOwnProperty(queryHash)) {
				return accessoryCacheMap[queryHash] as Array;
			}
			
			return null;
		}
		
		
		public function setAccessoryQueryResult(queryModel:AccessoryQueryModel, queryResult:Array):void {
			// Remove cache for product part number
			var cnt:int = 0;
			for (var key:String in accessoryCacheMap) {
				cnt ++;
				if (cnt > accessoryCacheSize) {
					delete accessoryCacheMap[key];
				}
			}
			
			var queryHash:String = queryModel.productPartNumber;
			accessoryCacheMap[queryHash] = queryResult;
		}
		
		
		public function getDamperQueryResult(sqlQuery:SQLDataQuery, damperType:uint):Array {
			var queryHash:String = MD5.hash(sqlQuery.toSql());
			queryHash = damperType + ":" + queryHash;
			
			if (damperCacheMap.hasOwnProperty(queryHash)) {
				return damperCacheMap[queryHash] as Array;
			}
			
			return null;
		}
		
		
		public function setDamperQueryResult(sqlQuery:SQLDataQuery, damperType:uint, queryResult:Array):void {
			// Remove all cache for type
			for (var key:String in damperCacheMap) {
				var parts:Array	= key.split(":");
				if (parts[0] == damperType) {
					delete damperCacheMap[key];
				}
			}
			
			var queryHash:String = MD5.hash(sqlQuery.toSql());
			queryHash = damperType + ":" + queryHash;
			damperCacheMap[queryHash] = queryResult;
		}
		
		
		// Make it a Singleton
		private static var _instance:QueryCache;
		public static function getInstance():QueryCache {
			if (_instance == null) {
				_instance = new QueryCache();
			}
			return _instance;
		}
	}
}