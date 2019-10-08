package Swizzard.System
{
	import flash.events.Event;
	import flash.events.IEventDispatcher;
	import flash.events.KeyboardEvent;
	import flash.ui.Keyboard;
	
	import mx.controls.Alert;
	
	import Swizzard.Data.AsynchronousQuery.Commands.Accessory.AccessoryQueryCommand;
	import Swizzard.Data.AsynchronousQuery.Commands.Dampers.DamperQueryCommand;
	import Swizzard.Data.AsynchronousQuery.Commands.Pneumatics.PneumaticQueryCommand;
	import Swizzard.Data.AsynchronousQuery.Commands.Valves.ValveQueryCommand;
	import Swizzard.System.Commands.Exit.ShutdownCommand;
	import Swizzard.System.Commands.Init.StartupCommand;
	import Swizzard.System.InfosWindows.commands.CompanyInfoCommand;
	import Swizzard.System.Persistence.Commands.PersistenceCommand;
	import Swizzard.System.Persistence.Proxies.PersistenceProxy;
	import Swizzard.System.Search.Commands.SearchBoxCommand;
	import Swizzard.System.Updater.Commands.UpdateCommand;
	
	import org.puremvc.as3.interfaces.IFacade;
	import org.puremvc.as3.patterns.facade.Facade;
	
	import utils.LogUtils;
	
	
	public class ApplicationFacade extends Facade implements IFacade
	{
		//Notification Name Constants
		public static const STARTUP:String 		= "startup";
		public static const PREP_VIEW:String 	= "prepView";
		
		public static const RESET_QUERY:String 	= "Reset Everything!";
		public static const PRODUCT_CHANGED:String = "productTypeChanged";
		
		
		public static function getInstance():ApplicationFacade
		{
			if (instance == null) 
				instance = new ApplicationFacade();
			return instance as ApplicationFacade;
		}
		
		
		/**
		 * Initialize the model 
		 */
		override protected function initializeModel():void
		{
			super.initializeModel();
			registerProxy(new PersistenceProxy());
		}
		
		
		/**
		 * Initialize the view.
		 */		
		override protected function initializeView():void
		{
			super.initializeView();
		}
		
		
		/**
		 * Initialize the controller. 
		 */		
		override protected function initializeController():void
		{
			super.initializeController();
			
			registerCommand(STARTUP, StartupCommand);
			registerCommand(ShutdownCommand.SHUTDOWN, ShutdownCommand);
			
			/** Update **/
			registerCommand(UpdateCommand.INITIATE_UPDATE,	 	UpdateCommand);
			registerCommand(UpdateCommand.CHECK_FOR_UPDATE, 	UpdateCommand);
			registerCommand(UpdateCommand.UPDATE_PENDING, 		UpdateCommand);
			registerCommand(UpdateCommand.RESET_DB_VERSION, 	UpdateCommand);
			registerCommand(UpdateCommand.LOG_CONTENT_DOWNLOAD, UpdateCommand);
			
			/** Preferences **/
			registerCommand(CompanyInfoCommand.GET_COMPANY_INFO,	CompanyInfoCommand);
			registerCommand(CompanyInfoCommand.STORE_COMPANY_INFO, 	CompanyInfoCommand);
			
			/** DATA **/
			registerCommand(SearchBoxCommand.SEARCH_PART_NUMBER,	SearchBoxCommand);
			registerCommand(ValveQueryCommand.EXECUTE_VALVE_QUERY,	ValveQueryCommand);
			registerCommand(DamperQueryCommand.EXECUTE_DAMPER_QUERY, DamperQueryCommand);
			registerCommand(AccessoryQueryCommand.EXECUTE_ACCESSORY_QUERY, AccessoryQueryCommand);
			registerCommand(PneumaticQueryCommand.EXECUTE_PNEUMATIC_QUERY, PneumaticQueryCommand);
			
			registerCommand(PersistenceCommand.LOAD_PROJECT,	PersistenceCommand);
			registerCommand(PersistenceCommand.SAVE_PROJECT,	PersistenceCommand);
			registerCommand(PersistenceCommand.SAVE_AS_PROJECT, PersistenceCommand);
			registerCommand(PersistenceCommand.NEW_PROJECT, 	PersistenceCommand);
		}
		
		
		public function startup(app:*):void
		{
			sendNotification(STARTUP, app);
			
			app.stage.addEventListener(KeyboardEvent.KEY_DOWN, keyboardDownHandler, false, 0, true);
			if (app.stage.loaderInfo.hasOwnProperty("uncaughtErrorEvents")) 
			{
				IEventDispatcher(app.stage.loaderInfo["uncaughtErrorEvents"]).addEventListener("uncaughtError", 		globalExceptionHandler);
				IEventDispatcher(app.loaderInfo["uncaughtErrorEvents"]).addEventListener("uncaughtError", 				globalExceptionHandler);
				IEventDispatcher(app.systemManager.loaderInfo["uncaughtErrorEvents"]).addEventListener("uncaughtError", globalExceptionHandler);
			}
		}
		
		
		private function globalExceptionHandler(event:Event):void
		{
			var error:Error	= event['error'] as Error;
			Alert.show("An internal error has occurred, please save your work and restart the application.");
			LogUtils.Debug(error);
		}
		

		private function keyboardDownHandler(event:KeyboardEvent):void
		{
			switch (event.keyCode)
			{
				case Keyboard.F1:
				{
					Alert.show("Show Help F1");
					break;
				}
			}
		}
	}
}