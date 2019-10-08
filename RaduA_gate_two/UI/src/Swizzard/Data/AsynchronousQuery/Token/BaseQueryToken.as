package Swizzard.Data.AsynchronousQuery.Token
{
	import Swizzard.Data.Models.Query.BaseQueryModel;
	
	
	public class BaseQueryToken
	{
		protected var _target:String;
		protected var _queryModel:BaseQueryModel;
		protected var _completeNotification:String;
		
		protected var _results:Array;
		protected var _resultsChanged:Boolean;
		
		protected var _numQueries:uint;
		protected var _isSubQuery:Boolean;
		

		public function BaseQueryToken() {
			_numQueries = 0;
		}
		
		
		public function set target(value:String):void {
			_target = value;
		}
		
		public function get target():String {
			return _target;
		}
		
		
		public function set queryModel(value:BaseQueryModel):void {
			_queryModel = value;
		}
		
		public function get queryModel():BaseQueryModel {
			return _queryModel;
		}
		
		
		public function set completeNotification(value:String):void {
			_completeNotification = value;
		}
		
		public function get completeNotification():String {
			return _completeNotification;
		}
		
		
		public function set results(val:Array):void {
			_results = val;
			_resultsChanged	= true;
		}
		
		public function get results():Array {
			return _results;
		}
		
		public function get resultsChanged():Boolean {
			return _resultsChanged;
		}
		
		
		public function set isSubQuery(value:Boolean):void {
			_isSubQuery = value;
		}
		
		public function get isSubQuery():Boolean {
			return _isSubQuery;
		}
		
		
		public function set numQueries(value:uint):void {
			_numQueries = value;
		}
		
		public function get numQueries():uint {
			return _numQueries;
		}
	}
}