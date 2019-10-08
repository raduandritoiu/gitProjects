package Swizzard.System.Mediators.Dampers
{
	import flash.desktop.ClipboardFormats;
	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import flash.events.NativeDragEvent;
	import flash.filesystem.File;
	
	import mx.collections.ArrayCollection;
	import mx.collections.HierarchicalCollectionView;
	import mx.events.DragEvent;
	import mx.events.IndexChangedEvent;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensDamper;
	import Swizzard.Data.Models.Enumeration.ExtendedProductType;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.System.Mediators.BaseScheduleMediator;
	import Swizzard.System.Models.BaseScheduleItem;
	import Swizzard.System.Models.DamperScheduleItem;
	import Swizzard.System.Models.VSSTProject;
	import Swizzard.System.Persistence.IO.ScheduleItemReader;
	import Swizzard.System.Persistence.Proxies.PersistenceProxy;
	import Swizzard.System.Utils.DamperTorqueUtil;
	import Swizzard.System.Utils.MessageConstants;
	import Swizzard.UI.Components.DataGridClasses.GridColumnsUtil;
	
	import org.puremvc.as3.interfaces.INotification;
	
	
	public class DamperScheduleMediator extends BaseScheduleMediator
	{
		public static const NAME:String	= "damperScheduleMediator_2if9";
		
		
		public function DamperScheduleMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
			fileExtension = DamperScheduleItem.FILE_EXTENSION_NAME;
			productType = ProductType.DAMPER;
		}
		
		
		// the new item will be added to this dataProvider (not the hierarhical one)
		override public function addProduct(product:AbstractSiemensProduct, quantity:int = 1, copyFormParams:Boolean = true):void {
			// search for an equivalent product
			var foundItem:Boolean = false;
			for each(var item:BaseScheduleItem in dataProvider) {
				if (item.product.partNumber == product.partNumber) {
					foundItem = true;
					item.quantity = Math.max(item.quantity + 1, quantity);
					break;
				}
			}
			
			if (!foundItem) {
				// create the a new item
				var scheduleItem:BaseScheduleItem	= new DamperScheduleItem();
				scheduleItem.product			= product;
				scheduleItem.priceMultiplier	= preferences.globalMultiplier;
				scheduleItem.quantity 			= Math.max(1, quantity);
				dataProvider.addItem(scheduleItem);				
			}
			
			sendNotification(MessageConstants.PROJECT_DIRTY);
		}
		
		
		// --------------- Mediator Functions
		
		override protected function loadGridColumns():void {
			var scheduleColumns:Array = preferences.damperScheduleGridColumns;
			GridColumnsUtil.setPreferencesToMXColumns(scheduleColumns, GridColumnsUtil.getDamperScheduleColumn());
			if (scheduleColumns == null) {
				scheduleColumns = GridColumnsUtil.getDamperScheduleColumn();
			}
			preferences.damperScheduleGridColumns 	= scheduleColumns;
			scheduleGrid.columns 					= scheduleColumns;
			view.gridColumnOptions.columns 			= new ArrayCollection(scheduleColumns);
		}
		
		
		override protected function gridHeaderShiftHandler(event:IndexChangedEvent):void {
			preferences.damperScheduleGridColumns = scheduleGrid.columns;
		}
		
		
		override protected function scheduleRemoveClickHandler(event:MouseEvent):void {
			var hCollection:HierarchicalCollectionView = scheduleGrid.dataProvider as HierarchicalCollectionView;
			if (event.target == view.scheduleRemoveButton) {
				for each (var scheduleItem:BaseScheduleItem in scheduleGrid.selectedItems) {
					if (scheduleItem.parent != null) {
						// Remove at location
						hCollection.removeChild(scheduleItem.parent, scheduleItem);	
					}
					else {
						hCollection.removeChild(null, scheduleItem);
					}
				}
			}
			else if (event.target == view.scheduleRemoveAllButton) {
				(hCollection.source.getRoot() as ArrayCollection).removeAll();
			}
		}
		
		
		// ---------- Context Menu functions
		
		override protected function deleteMenuItemHandler(event:ContextMenuEvent):void {
			var scheduleItem:BaseScheduleItem = scheduleGrid.selectedItem as BaseScheduleItem;
			var hCollection:HierarchicalCollectionView = scheduleGrid.dataProvider as HierarchicalCollectionView;
			
			if (scheduleItem == null)
				return;
			
			if (scheduleItem.parent) {
				// Remove at location
				hCollection.removeChild(scheduleItem.parent, scheduleItem);
			}
			else {
				hCollection.removeChild(null, scheduleItem);
			}
		}
		
		
		// ------------- Notification Handle Functions
		
		override public function listNotificationInterests():Array {
			return [MessageConstants.MULTIPLIER_CHANGED,
				MessageConstants.RESET_COLUMNS, 
				PersistenceProxy.PROJECT_CREATED,
				PersistenceProxy.PROJECT_LOADED];
		}
		
		
		override public function handleNotification(notification:INotification):void {
			switch (notification.getName()) {
				case MessageConstants.MULTIPLIER_CHANGED:
					updateMultiplier(notification.getBody() as Number);					
					break;
				
				case MessageConstants.RESET_COLUMNS:
					var scheduleColumns:Array 				= GridColumnsUtil.getDamperScheduleColumn();
					scheduleGrid.columns 					= scheduleColumns;
					preferences.damperScheduleGridColumns 	= scheduleColumns;
					view.gridColumnOptions.columns = new ArrayCollection(scheduleColumns);
					break;
				
				case PersistenceProxy.PROJECT_CREATED:
				case PersistenceProxy.PROJECT_LOADED:
					var project:VSSTProject	= notification.getBody() as VSSTProject;
					dataProvider			= project.damperSchedule;
					break;
			}
		}
		
		
		// -------- Native drag-drop function: to save/load .svf files
		
		override protected function onScheduleNativeDrop(e:NativeDragEvent):void  {
			e.target.removeEventListener(NativeDragEvent.NATIVE_DRAG_DROP, onScheduleNativeDrop);
			e.target.removeEventListener(NativeDragEvent.NATIVE_DRAG_EXIT, onScheduleNativeDragExit);
			
			var items:Array = [];
			var fileList:Array = e.clipboard.getData(ClipboardFormats.FILE_LIST_FORMAT) as Array;
			if (fileList && fileList.length > 0) {
				for each(var f:File in fileList) {
					var scheduleItem:BaseScheduleItem = ScheduleItemReader.readScheduleItem(f) as BaseScheduleItem;
					if (!dataProvider) {
						dataProvider = new ArrayCollection();
					}
					items.push(scheduleItem);
				}
			}
			
			continueDropItem(items, dataProvider.length, false);
		}
		
		
		// ------------ Drag-drop Function - to add items to the list
		
		override protected function isCompatibleDropObject(item:Object, index:int, arr:Array):Boolean {
			if (item is DamperScheduleItem) return true;
			if (item is AbstractSiemensProduct) {
				var absProduct:AbstractSiemensProduct = item as AbstractSiemensProduct;
				if (absProduct.extendedProductType & ExtendedProductType.DAMPER_ACTUATOR) return true;
				if (absProduct.extendedProductType & ExtendedProductType.ACCESSORY) return true;
			}
			return false;
		}
		
		
		override protected function dragDropHandler(event:DragEvent):void {
			dragExitHandler(event);
			
			// test to see if we need to adjust the quantity
			var fromProducts:Boolean = false;
			if (event.dragSource.hasFormat("dragSourceName")) {
				if (event.dragSource.dataForFormat("dragSourceName").toString() == "ProductDataGrid") {
					fromProducts = true;
				}
			}
			
			// get the dragged items
			var items_raw:* = event.dragSource.dataForFormat(event.dragSource.formats[0]);
			if (items_raw == null) {
				return;
			}
			var items:Array = [];
			for (var i:int = 0; i < items_raw.length; i++) {
				items.push(items_raw[i]);
			}
			
			// get the drop index
			var dropIndex:uint = scheduleGrid.calculateDropIndex(event);
			if (dropIndex > scheduleGrid.dataProvider.length) {
				dropIndex = Math.max(scheduleGrid.dataProvider.length, 0);
			}
			
			// continue with the drop
			continueDropItem(items, dropIndex, fromProducts);
		}
		
		
		protected function continueDropItem(items:Array, dropIndex:uint, fromProducts:Boolean):void {
			var selectedTorque:Number = 0;
			if (fromProducts) {
				var damperParams:DamperParametersFormMediator = null;
				damperParams = facade.retrieveMediator(DamperParametersFormMediator.NAME) as DamperParametersFormMediator;
				selectedTorque = damperParams.selectedTorque;
			}
			
			for each (var droppedItem:Object in items) {
				// create the new schedule item
				var scheduleItem:DamperScheduleItem;
				if (droppedItem is DamperScheduleItem) {
					scheduleItem = (droppedItem as DamperScheduleItem).clone(false) as DamperScheduleItem;
				}
				else {
					scheduleItem 					= new DamperScheduleItem();
					scheduleItem.product			= droppedItem as AbstractSiemensProduct;
					scheduleItem.priceMultiplier	= preferences.globalMultiplier; 
				}
				
				// calculate quantity
				var quantity:int = 1;
				if (fromProducts && scheduleItem.product is SiemensDamper) {
					var damper:SiemensDamper = scheduleItem.product as SiemensDamper;
					quantity = DamperTorqueUtil.AdjustQuantity(damper.info.type, damper.info.torque, selectedTorque);
				}
				
				// search for an equivalent product
				var foundItem:Boolean = false;
				for each(var item:DamperScheduleItem in dataProvider) {
					if (item.product.partNumber == scheduleItem.product.partNumber) {
						foundItem = true;
						item.quantity = Math.max(item.quantity + 1, quantity);
						break;
					}
				}
				
				if (!foundItem) {
					scheduleItem.quantity = Math.max(1, quantity);
					dataProvider.addItemAt(scheduleItem, dropIndex);
					dropIndex ++;
				}
			}
			
			sendNotification(MessageConstants.PROJECT_DIRTY);
		}
	}
}