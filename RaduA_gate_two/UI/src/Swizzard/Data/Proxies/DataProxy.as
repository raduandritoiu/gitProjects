package Swizzard.Data.Proxies
{
	import com.adobe.utils.DateUtil;
	
	import flash.data.SQLConnection;
	import flash.data.SQLMode;
	import flash.data.SQLResult;
	import flash.data.SQLStatement;
	import flash.events.SQLErrorEvent;
	import flash.events.SQLEvent;
	import flash.filesystem.File;
	import flash.net.Responder;
	
	import mx.controls.Alert;
	
	import Swizzard.Data.Models.SiemensDocument;
	import Swizzard.System.Updater.Commands.UpdateCommand;
	
	import j2inn.Data.Query.SQLDataQuery;
	
	import org.puremvc.as3.patterns.proxy.Proxy;
	
	import utils.LogUtils;
	
	
	/**
	 * Database Proxy
	 * 
	 * This proxy handles communication with the SQL Lite database file 
	 * @author michael
	 * 
	 */	
	public class DataProxy extends Proxy {
		public static const NAME:String				= "dataProxy";
		
		public static const QUERY_RESULTS:String	= "queryResults";
		public static const DATA_PROXY_ERROR:String	= "dataProxyError";
		
		public static const RELEASE_DATABASE:String	= "releaseDatabase";
		public static const ATTACH_DATABASE:String	= "attachDatabase";
		
		
		// Database File
		public static const DATA_FILE:File	= File.applicationStorageDirectory.resolvePath("parts.db");
		
		
		private var database:SQLConnection;
		private var requestQueue:Array;
		private var optimizePending:Boolean;
		
		
		public function DataProxy(optimizeDatabase:Boolean = false) {
			super(NAME);
			requestQueue	= new Array();
			optimizePending	= optimizeDatabase;
		}
		
		
		// should make to private function
		public static function DestroyUserDatabase():Boolean {
			var destroyed:Boolean;
			
			try {
				DATA_FILE.deleteFile();
				destroyed	= true;
			}
			catch (err:Error) {
				throw new Error("Data File in use, Deregister Data Proxy before calling this function.");
				// File In Use, Must Deregister Proxy Before Calling this function
			}
			
			return destroyed;
		}
		
		
		override public function onRegister():void {
			var defaultDataFile:File = File.applicationDirectory.resolvePath("parts.db");
			var testData:File = DATA_FILE;
			
			if (!DATA_FILE.exists) {
				sendNotification(RELEASE_DATABASE);
				
				// if no data file. copy default data file to storage
				defaultDataFile.copyTo(DATA_FILE);
				optimizePending	= true;
				
				// reset database version
				facade.sendNotification(UpdateCommand.RESET_DB_VERSION);
			}
			else if (DateUtil.compareDates(defaultDataFile.modificationDate, DATA_FILE.modificationDate) < 0) {
				try {
					sendNotification(RELEASE_DATABASE);
					
					// Remove Old File
					DATA_FILE.deleteFile();
					
					// if included file is newer. copy default data file to storage
					defaultDataFile.copyTo(DATA_FILE);
					optimizePending	= true;
					
					// reset database version
					facade.sendNotification(UpdateCommand.RESET_DB_VERSION);
				}
				catch (err:Error) {
					// Error Updating Database
					LogUtils.Error("Can't Update Database: " + err.message);
				}
			}
			
			connectToDatabase();
		}
		
		
		private function connectToDatabase():void {
			sendNotification(ATTACH_DATABASE);
			
			database	= new SQLConnection();
			database.addEventListener(SQLEvent.OPEN, 		connectionOpenHandler, false, 0, true);
			database.addEventListener(SQLErrorEvent.ERROR,	connectionErrorHandler, false, 0, true);
			
			try {
				database.openAsync(DATA_FILE, (optimizePending) ? SQLMode.UPDATE : SQLMode.READ);	
			}
			catch (err:Error) {
				// Proxy Initialization Failed (Remove It)
				facade.removeProxy(NAME);
			}
		}
		
		
		override public function onRemove():void {
			database.close();
			sendNotification(RELEASE_DATABASE);
		}
		
		
		/**
		 * @private 
		 * 
		 * Optimize SQL Lite Database
		 */		
		private function optimize():void {
			database.analyze(null, new Responder(optimizeComplete));
		}
		
		
		/**
		 * Optimize Complete Handler 
		 * @param event
		 */		
		private function optimizeComplete(event:SQLEvent):void {
			closeAsync(new Responder(optimizeClosed));	
		}
		
		
		/**
		 * Closed for Optimization 
		 * @param event
		 */		
		private function optimizeClosed(event:SQLEvent):void {
			database.openAsync(DATA_FILE, SQLMode.READ);
		}
		
		
		/**
		 * Close Async Handler 
		 * @param responder
		 */		
		public function closeAsync(responder:Responder):void {
			database.close(responder);
		}
		
		
		private function connectionErrorHandler(event:SQLErrorEvent):void {
			Alert.show("Error Accessing Product Database, Rebuilding DB");
			
			try {
				DestroyUserDatabase();
												
				// if no data file. copy default data file to storage
				var defaultDataFile:File	= File.applicationDirectory.resolvePath("parts.db");
				defaultDataFile.copyTo(DATA_FILE);
				
				optimizePending	= true;
				
				// reset database version
				facade.sendNotification(UpdateCommand.RESET_DB_VERSION);
			}
			catch (err:Error) { }
			
			connectToDatabase();
		}
		
		
		private function connectionOpenHandler(event:SQLEvent):void {
			if (optimizePending) {
				optimizePending	= false;
				optimize();
			}
			if (requestQueue.length > 0) {
				queryData(requestQueue.unshift() as SQLDataQuery);	
			}	
		}
		
		
		/**
		 * Query Data 
		 *  
		 * @param query
		 * @param responder
		 */		
		public function queryData(query:SQLDataQuery, responder:Responder = null):void {
			if (!database.connected) {
				requestQueue.push(query);
				return;	
			}
			
			var statement:SQLStatement	= new SQLStatement();
			statement.sqlConnection		= database;
			statement.text				= query.toSql();
			statement.itemClass			= query.resultType;
			
			if (responder == null) {
				statement.addEventListener(SQLEvent.RESULT, 	searchResultHandler);	
				statement.addEventListener(SQLErrorEvent.ERROR, searchErrorHandler, false, 0, true);	
			}
			
			statement.execute(-1, responder);
		}
		
		
		/**
		 * Get Documents for Product by Part Number 
		 * 		 *  
		 * @param partNumber
		 * @return 
		 */		
		public function getDocuments():Array {
			var statement:SQLStatement	= new SQLStatement();
			statement.sqlConnection		= database;
			statement.text				= "SELECT * FROM Documents";
			statement.itemClass			= SiemensDocument;
			statement.execute();
			
			var result:SQLResult	= statement.getResult();
			return result.data;
		}
		
		
		private function searchResultHandler(event:SQLEvent):void {
			event.target.removeEventListener(SQLEvent.RESULT, 		searchResultHandler);
			event.target.removeEventListener(SQLErrorEvent.ERROR,	searchErrorHandler);
			
			var statement:SQLStatement	= event.target as SQLStatement;
			var result:SQLResult		= statement.getResult();	
			
			// Send Notification
			sendNotification(QUERY_RESULTS, result.data);
		}
		
		
		private function searchErrorHandler(event:SQLErrorEvent):void {
			event.target.removeEventListener(SQLEvent.RESULT, 		searchResultHandler);
			event.target.removeEventListener(SQLErrorEvent.ERROR,	searchErrorHandler);
			
			// Send Error Notification
			sendNotification(DATA_PROXY_ERROR, event);
		}
	}
}