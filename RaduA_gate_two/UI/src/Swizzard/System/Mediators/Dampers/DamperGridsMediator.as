package Swizzard.System.Mediators.Dampers
{
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.events.DividerEvent;
	import mx.events.IndexChangedEvent;
	
	import spark.collections.Sort;
	import spark.events.GridEvent;
	import spark.events.GridSelectionEvent;
	
	import Swizzard.Data.AsynchronousQuery.Commands.Accessory.AccessoryQueryCommand;
	import Swizzard.Data.AsynchronousQuery.Commands.Dampers.DamperQueryCommand;
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensAccessory;
	import Swizzard.Data.Models.SiemensDamper;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Data.Models.Query.AccessoryQueryModel;
	import Swizzard.Data.Models.Query.DamperQueryModel;
	import Swizzard.Effects.TextOverlayPlacard;
	import Swizzard.Favorites.UI.FavoritesWindow;
	import Swizzard.System.ApplicationFacade;
	import Swizzard.System.Preferences.UserPreferences;
	import Swizzard.System.Preferences.proxies.UserPreferencesProxy;
	import Swizzard.System.Utils.DamperTorqueUtil;
	import Swizzard.System.Utils.MessageConstants;
	import Swizzard.System.Utils.SwizzardGlobals;
	import Swizzard.UI.Components.DataGridClasses.GridColumnsUtil;
	import Swizzard.UI.Components.GridsBox.DamperGridsBox;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	import utils.LogUtils;
	
	
	public class DamperGridsMediator extends Mediator
	{
		static public const NAME:String = "DamperGridsMediator";
		
		public var damperQueryModel:DamperQueryModel;
		public var accessoryQueryModel:AccessoryQueryModel;
		
		private var lastProductUid:String;
		private var preferences:UserPreferences;
		private var damperPlacard:TextOverlayPlacard;
		private var accessoryPlacard:TextOverlayPlacard;
		
		
		public function DamperGridsMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
			
			lastProductUid = "";
			damperPlacard = new TextOverlayPlacard();
			accessoryPlacard = new TextOverlayPlacard();
		}
		
		
		public function get view():DamperGridsBox {
			return viewComponent as DamperGridsBox;
		}
		
		
		override public function onRegister():void {
			super.onRegister();
			
			// add UI listenters
			// damper and accessory grid item
			// RADU_TODO: must make up my mind which one to chose GridEvent.GRID_MOUSE_DOWN or GridEvent.GRID_CLICK for itemChanged
			view.damperGrid.addEventListener(GridEvent.GRID_CLICK, 						damperItemClicked, false, 0, true);
			view.damperGrid.addEventListener(GridSelectionEvent.SELECTION_CHANGE, 		productSelectionChanged, false, 0, true);
			view.accessoryGrid.addEventListener(GridSelectionEvent.SELECTION_CHANGE, productSelectionChanged, false, 0, true);
			
			// button handlers
			view.addDamperButton.addEventListener(MouseEvent.CLICK, 		addDamperButtonClickHandler, false, 0, true);
			view.addAccessoryButton.addEventListener(MouseEvent.CLICK, 	addAccessoryButtonClickHandler, false, 0, true);
			view.addAssemblyButton.addEventListener(MouseEvent.CLICK, 		addAssemblyButtonClickHandler, false, 0, true);
			view.viewFavoritesButton.addEventListener(MouseEvent.CLICK, 	showFavoritesButtonClickHandler, false, 0, true);
			
			// grid columns switch
			// RADU_TODO_CRISIS: these do not work anymore
			view.damperGrid.addEventListener(IndexChangedEvent.HEADER_SHIFT, 	gridHeaderShiftHandler, false, 0, true);
			view.accessoryGrid.addEventListener(IndexChangedEvent.HEADER_SHIFT, gridHeaderShiftHandler, false, 0, true);
			
			// some other components
			view.hDividedBox.addEventListener(DividerEvent.DIVIDER_RELEASE, gridDividerReleaseHandler, false, 0, true);
			
			
			// set the user preference
			var prefProxy:UserPreferencesProxy = facade.retrieveProxy(UserPreferencesProxy.NAME) as UserPreferencesProxy;
			preferences = prefProxy.preferences;
			
			// set the Damper Grid width
			if (!isNaN(preferences.damperGridWidth)) {
				view.damperGrid.width = preferences.damperGridWidth;
			}
			
			// set the Damper Grid Columns
			var damperColumns:Array = GridColumnsUtil.getDamperGridColumn();
			GridColumnsUtil.setPreferencesToColumns(damperColumns, preferences.damperGridColumns);
			preferences.damperGridColumns = damperColumns;
			view.damperGrid.columns = new ArrayCollection(damperColumns);
			
			// set the Grid Accessory Columns
			var accessoryColumns:Array = GridColumnsUtil.getAccessoryGridColumn();
			GridColumnsUtil.setPreferencesToColumns(accessoryColumns, preferences.actuatorGridColumns);
			preferences.accsryDamperGridColumns = accessoryColumns;
			view.accessoryGrid.columns = new ArrayCollection(accessoryColumns);
		}
		
		
		private function damperItemClicked(event:GridEvent):void {
			var damper:SiemensDamper = event.item as SiemensDamper;
			if (damper == null) return;
			
			var uid:String = damper.partNumber + damper.actualPartNumber;
			if (uid == lastProductUid) return;
			lastProductUid = uid;
			
			LogUtils.Debug("************* query request --- beacuse item clicked");
			accessoryQueryModel.specialPneumaticPart = AccessoryQueryModel.VOID_PART_NUMBER;
			accessoryQueryModel.productPartNumber = damper.partNumber;
			accessoryQueryModel.crossTable = AccessoryQueryModel.DAMPER_CROSS_TABLE;
		}
		
		
		private function productSelectionChanged(event:GridSelectionEvent):void {
			var damper:SiemensDamper 		= view.damperGrid.selectedItem as SiemensDamper;
			var accessory:SiemensAccessory 	= view.accessoryGrid.selectedItem as SiemensAccessory;
			var damperNum:int 	= (view.damperGrid.selectedItems != null) ? view.damperGrid.selectedItems.length : 0;
			var accNum:int 		= (view.accessoryGrid.selectedItems != null) ? view.accessoryGrid.selectedItems.length : 0;
			
			view.addDamperButton.enabled 	= (damper != null);
			view.addAccessoryButton.enabled = (accessory != null);
			view.addAssemblyButton.enabled 	= (damper != null && damperNum == 1 && accessory != null && accNum == 1);
		}
		
		
		private function addDamperButtonClickHandler(event:MouseEvent):void {
			var scheduleMediator:DamperScheduleMediator	= facade.retrieveMediator(DamperScheduleMediator.NAME) as DamperScheduleMediator;
			var damperParams:DamperParametersFormMediator = facade.retrieveMediator(DamperParametersFormMediator.NAME) as DamperParametersFormMediator;
			for each (var item:SiemensDamper in view.damperGrid.selectedItems) {
				var adjustedQty:int = DamperTorqueUtil.AdjustQuantity(item.info.type, item.info.torque, damperParams.selectedTorque);
				scheduleMediator.addProduct(item, adjustedQty);
			}
		}
		
		
		private function addAccessoryButtonClickHandler(event:MouseEvent):void {
			var scheduleMediator:DamperScheduleMediator	= facade.retrieveMediator(DamperScheduleMediator.NAME) as DamperScheduleMediator;
			for each (var item:SiemensAccessory in view.accessoryGrid.selectedItems) {
				scheduleMediator.addProduct(item);
			}
		}
		
		
		private function addAssemblyButtonClickHandler(event:MouseEvent):void {
			var damper:SiemensDamper 				= view .damperGrid.selectedItem as SiemensDamper;
			var accessory:SiemensAccessory 	= view.accessoryGrid.selectedItem as SiemensAccessory;
			var scheduleMediator:DamperScheduleMediator = facade.retrieveMediator(DamperScheduleMediator.NAME) as DamperScheduleMediator;
			var damperParams:DamperParametersFormMediator = facade.retrieveMediator(DamperParametersFormMediator.NAME) as DamperParametersFormMediator;
			if (damper != null && accessory != null) {
				var adjustedQty:int = DamperTorqueUtil.AdjustQuantity(damper.info.type, damper.info.torque, damperParams.selectedTorque);
				scheduleMediator.addProduct(damper, adjustedQty);
				scheduleMediator.addProduct(accessory, adjustedQty);
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
				case view.damperGrid:
					preferences.damperGridColumns = view.damperGrid.columnsArray.source;
					break;
				
				case view.accessoryGrid:
					preferences.accsryDamperGridColumns = view.accessoryGrid.columnsArray.source;
					break;
			}	
		}
		
		
		private function gridDividerReleaseHandler(event:DividerEvent):void {
			preferences.damperGridWidth = view.damperGrid.width;
		}
		
		
		// --------- Notification Handler Functions ---------------
		
		override public function listNotificationInterests():Array {
			return [MessageConstants.MULTIPLIER_CHANGED, MessageConstants.RESET_COLUMNS,
				DamperQueryCommand.DAMPERS_RECEIVED, AccessoryQueryCommand.ACCESSORIES_RECEIVED,
				ApplicationFacade.RESET_QUERY];
		}
		
		
		override public function handleNotification(notification:INotification):void {
			switch (notification.getName()) {
				case MessageConstants.MULTIPLIER_CHANGED:
				{
					if (view.damperGrid.dataProvider)
						view.damperGrid.datapArray.refresh();
					if (view.accessoryGrid.dataProvider)
						view.accessoryGrid.datapArray.refresh();
					break;
				}
				
				case ApplicationFacade.RESET_QUERY:
				{
					view.addDamperButton.enabled = false;
					view.addAccessoryButton.enabled = false;
					view.addAssemblyButton.enabled = false;
					break;
				}
					
				case MessageConstants.RESET_COLUMNS:
				{
					var damperColumns:Array 		= GridColumnsUtil.getDamperGridColumn();
					preferences.damperGridColumns 	= damperColumns;
					view.damperGrid.columns 		= new ArrayCollection(damperColumns);
					
					var accessoryColumns:Array 		= GridColumnsUtil.getAccessoryGridColumn();
					preferences.accsryDamperGridColumns = accessoryColumns;
					view.accessoryGrid.columns 		= new ArrayCollection(accessoryColumns);
					break;
				}
					
				case DamperQueryCommand.DAMPERS_RECEIVED:
				{
					// set the new damper data provider, keepind the old sort funtion and selected item (if any and possible)
					var oldSelectedItem:AbstractSiemensProduct = view.damperGrid.selectedItem as AbstractSiemensProduct;
					var existingDamperSort:Sort = (view.damperGrid.datapArray) ? (view.damperGrid.datapArray.sort as Sort) : null;
					var resultArr:ArrayCollection = new ArrayCollection(notification.getBody() as Array);
					view.damperGrid.dataProvider = resultArr;
					
					// sort the result
					if (view.damperGrid.dataProvider && existingDamperSort != null) {
						view.damperGrid.datapArray.sort	= existingDamperSort;
						view.damperGrid.datapArray.refresh();
					}
					if (oldSelectedItem) {
						view.damperGrid.selectProduct(oldSelectedItem.partNumber);
					}				
					
					// update the status texts
					var dampersFoundText:String = "0 found";
					if (view.damperGrid.dataProvider) {
						dampersFoundText = GridColumnsUtil.usNumberFormatter.format(view.damperGrid.dataProvider.length) + " found";
					}
					view.damperGrid.statusText = dampersFoundText;
					
					if (SwizzardGlobals.CURRENT_PRODUCT_TYPE == ProductType.DAMPER) 
						damperPlacard.show(dampersFoundText, view.damperGrid);
					
					// if there is a selected damper update the actuators for it
					if (view.damperGrid.selectedItem) {
						var damper:SiemensDamper = view.damperGrid.selectedItem as SiemensDamper;
						lastProductUid = damper.partNumber + damper.actualPartNumber;
						accessoryQueryModel.specialPneumaticPart = AccessoryQueryModel.VOID_PART_NUMBER;
						accessoryQueryModel.productPartNumber = damper.partNumber;
						accessoryQueryModel.crossTable = AccessoryQueryModel.DAMPER_CROSS_TABLE;
					}
					else {
						view.accessoryGrid.dataProvider = null;
					}
					break;
				}
					
				case AccessoryQueryCommand.ACCESSORIES_RECEIVED:
				{
					//because I use the same notifications for dampers and pneumatics, I must test
					if (SwizzardGlobals.CURRENT_PRODUCT_TYPE != ProductType.DAMPER) break;
					
					// set the new actuator data provider, keeping the old sorting function
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
					var centeredText:String = (view.damperGrid.selectedItem == null) ? "Please select a damper" : "No accessory kit found";
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