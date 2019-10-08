package Swizzard.System.Mediators.Pneumatics
{
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.events.DividerEvent;
	import mx.events.IndexChangedEvent;
	
	import spark.collections.Sort;
	import spark.events.GridEvent;
	import spark.events.GridSelectionEvent;
	
	import Swizzard.Data.AsynchronousQuery.Commands.Accessory.AccessoryQueryCommand;
	import Swizzard.Data.AsynchronousQuery.Commands.Pneumatics.PneumaticQueryCommand;
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensPneumatic;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Data.Models.Query.AccessoryQueryModel;
	import Swizzard.Data.Models.Query.PneumaticQueryModel;
	import Swizzard.Effects.TextOverlayPlacard;
	import Swizzard.Favorites.UI.FavoritesWindow;
	import Swizzard.System.ApplicationFacade;
	import Swizzard.System.Preferences.UserPreferences;
	import Swizzard.System.Preferences.proxies.UserPreferencesProxy;
	import Swizzard.System.Utils.MessageConstants;
	import Swizzard.System.Utils.SwizzardGlobals;
	import Swizzard.UI.Components.DataGridClasses.GridColumnsUtil;
	import Swizzard.UI.Components.GridsBox.PneumaticGridBox;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	import utils.LogUtils;
	
	
	public class PneumaticGridsMediator extends Mediator
	{
		static public const NAME:String = "PneumaticGridsMediator_o34kw";
		
		
		public var pneumaticQueryModel:PneumaticQueryModel;
		public var accessoryQueryModel:AccessoryQueryModel;

		private var lastProductUid:String;
		private var preferences:UserPreferences;
		private var pneumaticPlacard:TextOverlayPlacard;
		private var accessoryPlacard:TextOverlayPlacard;
		

		public function PneumaticGridsMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
			
			lastProductUid = "";
			pneumaticPlacard = new TextOverlayPlacard();
			accessoryPlacard = new TextOverlayPlacard();
		}
		
		
		public function get view():PneumaticGridBox {
			return viewComponent as PneumaticGridBox;
		}
		
		
		override public function onRegister():void {
			super.onRegister();
			
			// add UI listenters
			// pneumatic and accessory kits grid item
			// RADU_TODO: must make up my mind which one to chose GridEvent.GRID_MOUSE_DOWN or GridEvent.GRID_CLICK for itemChanged
			view.pneumaticGrid.addEventListener(GridEvent.GRID_CLICK, 				pneumaticItemClicked, false, 0, true);
			view.pneumaticGrid.addEventListener(GridSelectionEvent.SELECTION_CHANGE, productSelectionChanged, false, 0, true);
			view.accessoryGrid.addEventListener(GridSelectionEvent.SELECTION_CHANGE, productSelectionChanged, false, 0, true);
			
			// button handlers
			view.addPneumaticButton.addEventListener(MouseEvent.CLICK, 	addPneumaticButtonClickHandler, false, 0, true);
			view.addAccessoryButton.addEventListener(MouseEvent.CLICK, 	addAccessoryButtonClickHandler, false, 0, true);
			view.addAssemblyButton.addEventListener(MouseEvent.CLICK, 	addAssemblyButtonClickHandler, false, 0, true);
			view.viewFavoritesButton.addEventListener(MouseEvent.CLICK, showFavoritesButtonClickHandler, false, 0, true);
			
			// grid columns switch
			// RADU_TODO_CRISIS: these do not work anymore
			view.pneumaticGrid.addEventListener(IndexChangedEvent.HEADER_SHIFT, gridHeaderShiftHandler, false, 0, true);
			view.accessoryGrid.addEventListener(IndexChangedEvent.HEADER_SHIFT, gridHeaderShiftHandler, false, 0, true);
			
			// some other components
			view.hDividedBox.addEventListener(DividerEvent.DIVIDER_RELEASE, gridDividerReleaseHandler, false, 0, true);
			
			
			// set the user preference
			var prefProxy:UserPreferencesProxy = facade.retrieveProxy(UserPreferencesProxy.NAME) as UserPreferencesProxy;
			preferences = prefProxy.preferences;
			
			// set the Pneumatic Grid width
			if (!isNaN(preferences.pneumaticGridWidth)) {
				view.pneumaticGrid.width = preferences.pneumaticGridWidth;
			}
			
			// set the Pneumatic Grid Columns
			var pneumaticColumns:Array = GridColumnsUtil.getPneumaticGridColumn();
			GridColumnsUtil.setPreferencesToColumns(pneumaticColumns, preferences.pneumaticGridColumns);
			preferences.pneumaticGridColumns = pneumaticColumns;
			view.pneumaticGrid.columns = new ArrayCollection(pneumaticColumns);
			
			// set the Grid Accessory Kits Columns
			var accessoryColumns:Array = GridColumnsUtil.getAccessoryGridColumn();
			GridColumnsUtil.setPreferencesToColumns(accessoryColumns, preferences.actuatorGridColumns);
			preferences.accsryPneumGridColumns = accessoryColumns;
			view.accessoryGrid.columns = new ArrayCollection(accessoryColumns);
		}
		
		
		private function pneumaticItemClicked(event:GridEvent):void {
			var pneumatic:SiemensPneumatic = event.item as SiemensPneumatic;
			if (pneumatic == null) return;
			
			var uid:String = pneumatic.partNumber + pneumatic.actualPartNumber;
			if (uid == lastProductUid) return;
			lastProductUid = uid;
			
			LogUtils.Debug("************* query request --- beacuse item clicked");
			accessoryQueryModel.specialPneumaticPart = pneumatic.info.baseReplacement;
			accessoryQueryModel.productPartNumber = pneumatic.partNumber;
			accessoryQueryModel.crossTable = AccessoryQueryModel.PNEUMATIC_CROSS_TABLE;
		}
		
		
		private function productSelectionChanged(event:GridSelectionEvent):void {
			var pneumatic:AbstractSiemensProduct = view.pneumaticGrid.selectedItem as AbstractSiemensProduct;
			var accessory:AbstractSiemensProduct = view.accessoryGrid.selectedItem as AbstractSiemensProduct;
			var pneumaticNum:int = (view.pneumaticGrid.selectedItems != null) ? view.pneumaticGrid.selectedItems.length : 0;
			var accNum:int 		= (view.accessoryGrid.selectedItems != null) ? view.accessoryGrid.selectedItems.length : 0;
			
			view.addPneumaticButton.enabled 	= (pneumatic != null);
			view.addAccessoryButton.enabled 	= (accessory != null);
			view.addAssemblyButton.enabled 		= (pneumatic != null && pneumaticNum == 1 && accessory != null && accNum == 1);
		}
		
		
		private function addPneumaticButtonClickHandler(event:MouseEvent):void {
			var scheduleMediator:PneumaticScheduleMediator = facade.retrieveMediator(PneumaticScheduleMediator.NAME) as PneumaticScheduleMediator;
			for each (var item:SiemensPneumatic in view.pneumaticGrid.selectedItems) {
				scheduleMediator.addProduct(item);
			}
		}
		
		
		private function addAccessoryButtonClickHandler(event:MouseEvent):void {
			var scheduleMediator:PneumaticScheduleMediator = facade.retrieveMediator(PneumaticScheduleMediator.NAME) as PneumaticScheduleMediator;
			for each (var item:AbstractSiemensProduct in view.accessoryGrid.selectedItems) {
				scheduleMediator.addProduct(item);
			}
		}
		
		
		private function addAssemblyButtonClickHandler(event:MouseEvent):void {
			var pneumatic:AbstractSiemensProduct = view.pneumaticGrid.selectedItem as AbstractSiemensProduct;
			var accessory:AbstractSiemensProduct  = view.accessoryGrid.selectedItem as AbstractSiemensProduct;
			var scheduleMediator:PneumaticScheduleMediator = facade.retrieveMediator(PneumaticScheduleMediator.NAME) as PneumaticScheduleMediator;
			if (pneumatic != null && accessory != null) {
				scheduleMediator.addProduct(pneumatic);
				scheduleMediator.addProduct(accessory);
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
				case view.pneumaticGrid:
					preferences.pneumaticGridColumns = view.pneumaticGrid.columnsArray.source;
					break;
				
				case view.accessoryGrid:
					preferences.accsryPneumGridColumns = view.accessoryGrid.columnsArray.source;
					break;
			}	
		}
		
		
		private function gridDividerReleaseHandler(event:DividerEvent):void {
			preferences.pneumaticGridWidth = view.pneumaticGrid.width;
		}
		
		
		// --------- Notification Handler Functions ---------------
		
		override public function listNotificationInterests():Array {
			return [MessageConstants.MULTIPLIER_CHANGED, MessageConstants.RESET_COLUMNS,
				PneumaticQueryCommand.PNEUMATICS_RECEIVED, AccessoryQueryCommand.ACCESSORIES_RECEIVED,
				ApplicationFacade.RESET_QUERY];
		}
		
		
		override public function handleNotification(notification:INotification):void {
			switch (notification.getName()) {
				case MessageConstants.MULTIPLIER_CHANGED:
				{
					if (view.pneumaticGrid.dataProvider)
						view.pneumaticGrid.datapArray.refresh();
					if (view.accessoryGrid.dataProvider)
						view.accessoryGrid.datapArray.refresh();
					break;
				}
					
				case ApplicationFacade.RESET_QUERY:
				{
					view.addPneumaticButton.enabled = false;
					view.addAccessoryButton.enabled = false;
					view.addAssemblyButton.enabled = false;
					break;
				}
					
				case MessageConstants.RESET_COLUMNS:
				{
					var pneumaticColumns:Array 	= GridColumnsUtil.getPneumaticGridColumn();
					preferences.pneumaticGridColumns = pneumaticColumns;
					view.pneumaticGrid.columns 	= new ArrayCollection(pneumaticColumns);
					
					var accessoryColumns:Array 	= GridColumnsUtil.getAccessoryGridColumn();
					preferences.accsryPneumGridColumns = accessoryColumns;
					view.accessoryGrid.columns 	= new ArrayCollection(accessoryColumns);
					break;
				}
					
				case PneumaticQueryCommand.PNEUMATICS_RECEIVED:
				{
					// set the new pneumatic data provider, keepind the old sort funtion and selected item (if any and possible)
					var oldSelectedItem:AbstractSiemensProduct = view.pneumaticGrid.selectedItem as AbstractSiemensProduct;
					var existingPneumaticSort:Sort = (view.pneumaticGrid.datapArray) ? (view.pneumaticGrid.datapArray.sort as Sort) : null;
					
					var resultArr:ArrayCollection = new ArrayCollection(notification.getBody() as Array);
					view.pneumaticGrid.dataProvider = resultArr;
					
					// sort the result
					if (view.pneumaticGrid.dataProvider && existingPneumaticSort != null) {
						view.pneumaticGrid.datapArray.sort = existingPneumaticSort;
						view.pneumaticGrid.datapArray.refresh();
					}
					if (oldSelectedItem) {
						view.pneumaticGrid.selectProduct(oldSelectedItem.partNumber);
					}				
					
					// update the status texts
					var pneumaticFoundText:String = "0 found";
					if (view.pneumaticGrid.dataProvider) {
						pneumaticFoundText = GridColumnsUtil.usNumberFormatter.format(view.pneumaticGrid.dataProvider.length) + " found";
					}
					view.pneumaticGrid.statusText = pneumaticFoundText;
					
					if (SwizzardGlobals.CURRENT_PRODUCT_TYPE == ProductType.PNEUMATIC) 
						pneumaticPlacard.show(pneumaticFoundText, view.pneumaticGrid);
					
					// if there is a selected pneumatic update the accessories for it
					if (view.pneumaticGrid.selectedItem) {
						var pneumatic:SiemensPneumatic = view.pneumaticGrid.selectedItem as SiemensPneumatic;
						lastProductUid = pneumatic.partNumber + pneumatic.actualPartNumber;
						accessoryQueryModel.specialPneumaticPart = pneumatic.info.baseReplacement;
						accessoryQueryModel.productPartNumber = pneumatic.partNumber;
						accessoryQueryModel.crossTable = AccessoryQueryModel.PNEUMATIC_CROSS_TABLE;
					}
					else {
						view.accessoryGrid.dataProvider = null;
					}
					break;
				}
					
				case AccessoryQueryCommand.ACCESSORIES_RECEIVED:
				{
					//because I use the same notifications for dampers and pneumatics, I must test
					if (SwizzardGlobals.CURRENT_PRODUCT_TYPE != ProductType.PNEUMATIC) break;
					
					// set the new accessory data provider, keeping the old sorting function
					var oldSelectedItem:AbstractSiemensProduct = view.accessoryGrid.selectedItem as AbstractSiemensProduct;
					var existingAccessorySort:Sort = (view.accessoryGrid.datapArray) ? (view.accessoryGrid.datapArray.sort as Sort) : null;
					
					var resultArr:ArrayCollection = new ArrayCollection(notification.getBody() as Array);
					view.accessoryGrid.dataProvider = resultArr;
					
					// sort the result 
					if (view.accessoryGrid.dataProvider && existingAccessorySort != null) {
						view.accessoryGrid.datapArray.sort = existingAccessorySort;
						view.accessoryGrid.datapArray.refresh();
					}
					if (oldSelectedItem) {
						view.accessoryGrid.selectProduct(oldSelectedItem.partNumber);
					}				
					
					// set the status texts
					var accessoryFoundText:String = "0 found";
					if (view.accessoryGrid.dataProvider) {
						accessoryFoundText = GridColumnsUtil.usNumberFormatter.format(view.accessoryGrid.dataProvider.length) + " found";
					}
					view.accessoryGrid.statusText = accessoryFoundText;
					
					// set centered text and placard
					var centeredText:String = (view.pneumaticGrid.selectedItem == null) ? "Please select a pneumatic" : "No accessory kit found";
					if (view.accessoryGrid.dataProvider && view.accessoryGrid.dataProvider.length > 0) {
						view.accessoryGrid.centerText = null;
						accessoryPlacard.show(accessoryFoundText, view.accessoryGrid);
					}
					else {
						view.accessoryGrid.centerText = centeredText;
					}
					break;
				}
			}
		}
	}
}