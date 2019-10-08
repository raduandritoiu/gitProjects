package Swizzard.System.Mediators.Valves
{
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.DividerEvent;
	import mx.events.IndexChangedEvent;
	
	import spark.collections.Sort;
	import spark.events.GridEvent;
	import spark.events.GridSelectionEvent;
	
	import Swizzard.Data.AsynchronousQuery.Commands.Valves.ValveQueryCommand;
	import Swizzard.Data.AsynchronousQuery.Enumeration.ProductQueryType;
	import Swizzard.Data.AsynchronousQuery.Token.ValveQueryToken;
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensActuator;
	import Swizzard.Data.Models.SiemensAssembly;
	import Swizzard.Data.Models.SiemensValve;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Data.Models.Enumeration.Valves.ValvePattern;
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;
	import Swizzard.Data.Models.Query.ValveSystemQueryModel;
	import Swizzard.Data.Utils.SynchronousDataUtility;
	import Swizzard.Effects.TextOverlayPlacard;
	import Swizzard.Favorites.UI.FavoritesWindow;
	import Swizzard.System.ApplicationFacade;
	import Swizzard.System.Preferences.UserPreferences;
	import Swizzard.System.Preferences.proxies.UserPreferencesProxy;
	import Swizzard.System.Utils.MessageConstants;
	import Swizzard.System.Utils.SwizzardGlobals;
	import Swizzard.UI.Components.DataGridClasses.GridColumnsUtil;
	import Swizzard.UI.Components.GridsBox.ValveGridsBox;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	import utils.LogUtils;
	
	
	public class ValveGridsMediator extends Mediator
	{
		static public const NAME:String = "ValveGridsMediator";
		
		public var queryModel:ValveSystemQueryModel;
		
		private var lastProductUid:String;
		private var preferences:UserPreferences;
		private var valvePlacard:TextOverlayPlacard;
		private var actuatorPlacard:TextOverlayPlacard;
		
		
		public function ValveGridsMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
			
			lastProductUid = "";
			
			valvePlacard = new TextOverlayPlacard();
			actuatorPlacard = new TextOverlayPlacard();
		}
		
		
		public function get view():ValveGridsBox {
			return viewComponent as ValveGridsBox;
		}
		
		
		override public function onRegister():void {
			super.onRegister();
			
			// add UI listenters
			// valve and actuator grid item
			// RADU_TODO: must make up my mind which one to chose GridEvent.GRID_MOUSE_DOWN or GridEvent.GRID_CLICK for itemChanged
			view.valveGrid.addEventListener(GridEvent.GRID_CLICK, 					valveItemClicked, false, 0, true);
			view.valveGrid.addEventListener(GridSelectionEvent.SELECTION_CHANGE, 	productSelectionChanged, false, 0, true);
			view.actuatorGrid.addEventListener(GridSelectionEvent.SELECTION_CHANGE, productSelectionChanged, false, 0, true);
			
			// button handlers
			view.addValveButton.addEventListener(MouseEvent.CLICK, 		addValveButtonClickHandler, false, 0, true);
			view.addActuatorButton.addEventListener(MouseEvent.CLICK, 	addActuatorButtonClickHandler, false, 0, true);
			view.addAssemblyButton.addEventListener(MouseEvent.CLICK, 	addAssemblyButtonClickHandler, false, 0, true);
			view.viewFavoritesButton.addEventListener(MouseEvent.CLICK, showFavoritesButtonClickHandler, false, 0, true);

			// grid columns switch
			// RADU_TODO_CRISIS: these do not work anymore
			view.valveGrid.addEventListener(IndexChangedEvent.HEADER_SHIFT, 	gridHeaderShiftHandler, false, 0, true);
			view.actuatorGrid.addEventListener(IndexChangedEvent.HEADER_SHIFT, 	gridHeaderShiftHandler, false, 0, true);

			// some other components
			view.hDividedBox.addEventListener(DividerEvent.DIVIDER_RELEASE, gridDividerReleaseHandler, false, 0, true);
			
			
			// set the user preference
			var prefProxy:UserPreferencesProxy = facade.retrieveProxy(UserPreferencesProxy.NAME) as UserPreferencesProxy;
			preferences = prefProxy.preferences;
			
			// set the Valve Grid width
			if (!isNaN(preferences.valveGridWidth)) {
				view.valveGrid.width = preferences.valveGridWidth;
			}
			
			// set the Valve Grid Columns
			var valveColumns:Array = GridColumnsUtil.getValveGridColumns();
			GridColumnsUtil.setPreferencesToColumns(valveColumns, preferences.valveGridColumns);
			preferences.valveGridColumns = valveColumns;
			view.valveGrid.columns = new ArrayCollection(valveColumns);
			
			// set the Grid Actuator Columns
			var actuatorColumns:Array = GridColumnsUtil.getActuatorGridColumn();
			GridColumnsUtil.setPreferencesToColumns(actuatorColumns, preferences.actuatorGridColumns);
			preferences.actuatorGridColumns = actuatorColumns;
			view.actuatorGrid.columns = new ArrayCollection(actuatorColumns);
		}
		
		
		private function valveItemClicked(event:GridEvent):void {
			var valve:SiemensValve	= event.item as SiemensValve;
			if (valve == null) return;
			
			var uid:String = valve.partNumber + valve.actualPartNumber;
			if (uid == lastProductUid) return;
			lastProductUid = uid;
			
			var token:ValveQueryToken	= new ValveQueryToken();
			token.target			= ProductQueryType.ACTUATORS;
			token.valve				= valve;
			token.queryModel		= queryModel;
			token.isValveQuery		= false;
			
			LogUtils.Debug("************* query request *** beacuse item clicked");
			sendNotification(ValveQueryCommand.EXECUTE_VALVE_QUERY, token);
		}
		
		
		private function productSelectionChanged(event:GridSelectionEvent):void {
			var valve:SiemensValve = view.valveGrid.selectedItem as SiemensValve;
			var actuator:SiemensActuator = view.actuatorGrid.selectedItem as SiemensActuator;
			var valveNum:int = (view.valveGrid.selectedItems != null) ? view.valveGrid.selectedItems.length : 0;
			var actNum:int = (view.actuatorGrid.selectedItems != null) ? view.actuatorGrid.selectedItems.length : 0;
			
			view.addValveButton.enabled = (valve != null && !valve.assemblyOnly && valve.info.type != ValveType.MAGNETIC)
			view.addActuatorButton.enabled = (actuator != null && !actuator.assemblyOnly);
			view.addAssemblyButton.enabled = (valve != null && valveNum == 1 && (actNum == 1 || valve.info.type == ValveType.MAGNETIC));
		}
		
		
		private function addValveButtonClickHandler(event:MouseEvent):void {
			var scheduleMediator:ValveScheduleMediator	= facade.retrieveMediator(ValveScheduleMediator.NAME) as ValveScheduleMediator;
			for each (var item:AbstractSiemensProduct in view.valveGrid.selectedItems) {
				scheduleMediator.addProduct(item);
			}
		}
		
		
		private function addActuatorButtonClickHandler(event:MouseEvent):void {
			var scheduleMediator:ValveScheduleMediator	= facade.retrieveMediator(ValveScheduleMediator.NAME) as ValveScheduleMediator;
			for each (var item:AbstractSiemensProduct in view.actuatorGrid.selectedItems) {
				scheduleMediator.addProduct(item);
			}
		}
		
		
		private function addAssemblyButtonClickHandler(event:MouseEvent):void {
			var valve:SiemensValve						= view.valveGrid.selectedItem as SiemensValve;
			var actuator:SiemensActuator				= view.actuatorGrid.selectedItem as SiemensActuator;
			var scheduleMediator:ValveScheduleMediator	= facade.retrieveMediator(ValveScheduleMediator.NAME) as ValveScheduleMediator;
			
			if (valve && actuator) {
				var util:SynchronousDataUtility	= new SynchronousDataUtility();
				var assembly:SiemensAssembly	= util.getAssembly(valve, actuator);
				util.dispose();
				
				if (assembly) {
					scheduleMediator.addProduct(assembly);
				}
				else {
					Alert.show("This combination does not come as an assembly");
				}
			}
			else if (valve && valve.info.type == ValveType.MAGNETIC) {
				scheduleMediator.addProduct(valve);
			}
		}
		
		
		private function showFavoritesButtonClickHandler(event:MouseEvent):void {
			FavoritesWindow.Show();
		}
		
		
		// RADU_TODO_CRISIS: these do not work anymore
		private function gridHeaderShiftHandler(event:IndexChangedEvent):void {
			// Created because adobe copies the array internally when items are shifted
			// this function remembers (in preferences) the order of the columns of the grids
			switch (event.target) {
				case view.valveGrid:
					preferences.valveGridColumns = view.valveGrid.columnsArray.source;
					break;
				
				case view.actuatorGrid:
					preferences.actuatorGridColumns	= view.actuatorGrid.columnsArray.source;
					break;
			}	
		}
		
		
		private function gridDividerReleaseHandler(event:DividerEvent):void {
			preferences.valveGridWidth = view.valveGrid.width;
		}
		
		
		// ********* Notification Handler Functions
		
		override public function listNotificationInterests():Array {
			return [MessageConstants.MULTIPLIER_CHANGED, MessageConstants.RESET_COLUMNS,
				ValveQueryCommand.VALVES_RECEIVED, ValveQueryCommand.ACTUATORS_RECEIVED,
				ValveQueryCommand.QUERY_RESULTS_RECEIVED, ApplicationFacade.RESET_QUERY];
		}
		
		
		override public function handleNotification(notification:INotification):void {
			switch (notification.getName()) {
				case MessageConstants.MULTIPLIER_CHANGED:
				{
					if (view.valveGrid.dataProvider)
						view.valveGrid.datapArray.refresh();
					if (view.actuatorGrid.dataProvider)
						view.actuatorGrid.datapArray.refresh();
					break;
				}
				
				case ApplicationFacade.RESET_QUERY:
				{
					view.addValveButton.enabled 	= false;
					view.addActuatorButton.enabled 	= false;
					view.addAssemblyButton.enabled 	= false;
					break;
				}
					
				case MessageConstants.RESET_COLUMNS:
				{
					var valveColumns:Array 			= GridColumnsUtil.getValveGridColumns();
					preferences.valveGridColumns 	= valveColumns;
					view.valveGrid.columns 			= new ArrayCollection(valveColumns);
					
					var actuatorColumns:Array 		= GridColumnsUtil.getActuatorGridColumn();
					preferences.actuatorGridColumns = actuatorColumns;
					view.actuatorGrid.columns 		= new ArrayCollection(actuatorColumns);
					break;
				}
				
				case ValveQueryCommand.VALVES_RECEIVED:
				{
					// set the new valve data provider, keepind the old sort funtion and selected item (if any and possible)
					var resultArr:ArrayCollection = new ArrayCollection(notification.getBody() as Array);
					LogUtils.SQLResults("***************- valve query results ("+resultArr.length+")*********************- ");
					
					var oldSelectedItem:AbstractSiemensProduct = view.valveGrid.selectedItem as AbstractSiemensProduct;
					var existingValveSort:Sort = (view.valveGrid.datapArray) ? (view.valveGrid.datapArray.sort as Sort) : null;
					view.valveGrid.dataProvider = resultArr;
					
					if (view.valveGrid.dataProvider) {
						view.valveGrid.datapArray.sort	= existingValveSort;
						view.valveGrid.datapArray.refresh();
					}
					if (oldSelectedItem) {
						view.valveGrid.selectProduct(oldSelectedItem.partNumber);
					}				
					
					// update the status texts
					var valvesFoundText:String = "0 found";
					if (view.valveGrid.dataProvider) {
						valvesFoundText = GridColumnsUtil.usNumberFormatter.format(view.valveGrid.dataProvider.length) + " found";
					}
					view.valveGrid.statusText = valvesFoundText;
					
					if (SwizzardGlobals.CURRENT_PRODUCT_TYPE == ProductType.VALVE) 
						valvePlacard.show(valvesFoundText, view.valveGrid);
					
					// if there is a selected valve update the actuators for it
					if (view.valveGrid.selectedItem) {
						var token:ValveQueryToken	= new ValveQueryToken();
						token.target				= ProductQueryType.ACTUATORS;
						token.valve					= view.valveGrid.selectedItem as SiemensValve;
						token.queryModel			= queryModel;
						
						LogUtils.SQLResults("********************* query request *** beacuse valve received and item was selected");
						sendNotification(ValveQueryCommand.EXECUTE_VALVE_QUERY, token);
					}
					else {
						view.actuatorGrid.dataProvider = null;
						view.actuatorGrid.centerText = "Please select a valve.";
					}
					break;
				}
					
				case ValveQueryCommand.ACTUATORS_RECEIVED:
				{
					// set the new actuator data provider, keeping the old sorting function
					var resultArr:ArrayCollection = new ArrayCollection(notification.getBody() as Array);
					LogUtils.SQLResults("***************- actuator query results ("+resultArr.length+") *********************- ");
					
					var oldSelectedItem:AbstractSiemensProduct = view.actuatorGrid.selectedItem as AbstractSiemensProduct;
					var existingActuatorSort:Sort = (view.actuatorGrid.datapArray) ? (view.actuatorGrid.datapArray.sort as Sort) : null;
					view.actuatorGrid.dataProvider = resultArr;
					
					// sort and set selection
					if (view.actuatorGrid.dataProvider) {
						view.actuatorGrid.datapArray.sort = existingActuatorSort;
						view.actuatorGrid.datapArray.refresh();
						
						var actFoundText:String = GridColumnsUtil.usNumberFormatter.format(view.actuatorGrid.dataProvider.length) + " found";
						view.actuatorGrid.statusText = actFoundText;
						if (view.actuatorGrid.dataProvider.length > 0) {
							actuatorPlacard.show(actFoundText, view.actuatorGrid);
						}
					}
					if (oldSelectedItem) {
						view.actuatorGrid.selectProduct(oldSelectedItem.partNumber);
					}
					
					// set the status strings accordingly
					var centeredText:String = null;
					if (view.valveGrid.selectedItem == null) 
						centeredText = "Please select a valve.";
					else if(SiemensValve(view.valveGrid.selectedItem).info.type == ValveType.MAGNETIC) 
						centeredText = "Actuator Included";
					else if(SiemensValve(view.valveGrid.selectedItem).info.type == ValveType.BALL && SiemensValve(view.valveGrid.selectedItem).info.pattern == ValvePattern.SIX_WAY) 
						centeredText = "If ordering 6-Way valve and actuator separately, for mounting you must contact customer support to order ASK77.3 bracket (sold separately)";
					else if (resultArr != null && resultArr.length > 0)
						centeredText = null;
					else
						centeredText = "No Actuators Found.";
					view.actuatorGrid.centerText = centeredText;
					
					break;
				}
					
				case ValveQueryCommand.QUERY_RESULTS_RECEIVED:
				{
					// Both valves and actuators results it's the same code as for the two above
					var resultToken:ValveQueryToken	= notification.getBody() as ValveQueryToken;
					LogUtils.SQLResults("***************- entire query results *********************- ");
					
					if (resultToken.resultsChanged) {
						// Update valves
						var oldSelectedItem:AbstractSiemensProduct	= view.valveGrid.selectedItem as AbstractSiemensProduct;
						var existingValveSort:Sort = (view.valveGrid.dataProvider) ? (view.valveGrid.datapArray.sort as Sort) : null;
						var resultArr:ArrayCollection = new ArrayCollection(resultToken.results);
						view.valveGrid.dataProvider = resultArr;
						
						if (view.valveGrid.dataProvider) {
							view.valveGrid.datapArray.sort	= existingValveSort;
							view.valveGrid.datapArray.refresh();
						}
						if (oldSelectedItem) {
							view.valveGrid.selectProduct(oldSelectedItem.partNumber);
						}
						
						var valvesFoundText:String = "0 found";
						if (view.valveGrid.dataProvider) {
							valvesFoundText = GridColumnsUtil.usNumberFormatter.format(view.valveGrid.dataProvider.length) + " found";
						}
						view.valveGrid.statusText = valvesFoundText;
						valvePlacard.show(valvesFoundText, view.valveGrid);
					}
					
					if (resultToken.actuatorResultsChanged) {
						// Update Actuator Results
						var resultArr:ArrayCollection = null;
						if ((resultToken.resultsChanged && view.valveGrid.selectedItem) || 
							(!resultToken.resultsChanged && resultToken.actuatorResultsChanged && resultToken.actuatorResults && resultToken.actuatorResults.length > 0)) {
							var existingActuatorSort:Sort = (view.actuatorGrid.datapArray) ? (view.actuatorGrid.datapArray.sort as Sort) : null;
							resultArr = new ArrayCollection(resultToken.actuatorResults);
							view.actuatorGrid.dataProvider = resultArr;
							view.actuatorGrid.datapArray.sort = existingActuatorSort;
							view.actuatorGrid.datapArray.refresh();
							
							var actFoundText:String = GridColumnsUtil.usNumberFormatter.format(view.actuatorGrid.dataProvider.length) + " found";
							view.actuatorGrid.statusText = actFoundText;
							if (resultArr.length > 0) {
								actuatorPlacard.show(actFoundText, view.actuatorGrid);
							}
						}
						else {
							view.actuatorGrid.dataProvider = null;
						}
						
						// set the status strings accordingly
						var centeredText:String = null;
						if (view.valveGrid.selectedItem == null)
							centeredText = "Please select a valve.";
						else if(SiemensValve(view.valveGrid.selectedItem).info.type == ValveType.MAGNETIC)
							centeredText = "Actuator Included";
						else if(SiemensValve(view.valveGrid.selectedItem).info.type == ValveType.BALL && SiemensValve(view.valveGrid.selectedItem).info.pattern == ValvePattern.SIX_WAY) 
							centeredText = "If ordering 6-Way valve and actuator separately, for mounting you must contact customer support to order ASK77.3 bracket (sold separately)";
						else if (resultArr != null && resultArr.length > 0)
							centeredText = null;
						else
							centeredText = "No Actuators Found.";
						view.actuatorGrid.centerText = centeredText;
					}
					break;
				}
			}
		}
	}
}