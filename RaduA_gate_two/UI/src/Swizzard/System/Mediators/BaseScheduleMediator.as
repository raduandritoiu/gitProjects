package Swizzard.System.Mediators
{
	import flash.desktop.Clipboard;
	import flash.desktop.ClipboardFormats;
	import flash.desktop.NativeDragManager;
	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import flash.events.NativeDragEvent;
	import flash.filesystem.File;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	
	import mx.collections.ArrayCollection;
	import mx.collections.HierarchicalData;
	import mx.controls.AdvancedDataGrid;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
	import mx.controls.listClasses.IListItemRenderer;
	import mx.core.mx_internal;
	import mx.events.AdvancedDataGridEvent;
	import mx.events.CollectionEvent;
	import mx.events.CollectionEventKind;
	import mx.events.DragEvent;
	import mx.events.IndexChangedEvent;
	import mx.managers.DragManager;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.System.Persistence.IO.ScheduleItemWriter;
	import Swizzard.System.Preferences.UserPreferences;
	import Swizzard.System.Preferences.proxies.UserPreferencesProxy;
	import Swizzard.System.Utils.MessageConstants;
	import Swizzard.System.Utils.SwizzardGlobals;
	import Swizzard.UI.Components.ScheduleBox.ScheduleGridBox;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	import utils.LogUtils;
	
	
	public class BaseScheduleMediator extends Mediator
	{
		protected var productType:uint;
		protected var fileExtension:String;
		protected var preferences:UserPreferences;
		protected var _dataProvider:ArrayCollection;
		
		
		public function BaseScheduleMediator(mediatorName:String, viewComponent:Object) {
			// use the give name
			super(mediatorName, viewComponent);
			productType = ProductType.UNKNOWN;
		}
		
		
		public function get view():ScheduleGridBox {
			return viewComponent as ScheduleGridBox;
		}
		
		
		public function get scheduleGrid():AdvancedDataGrid {
			return viewComponent.scheduleGrid as AdvancedDataGrid;
		}
		
		
		protected function set dataProvider(value:ArrayCollection):void {
			if (_dataProvider) {
				_dataProvider.removeEventListener(CollectionEvent.COLLECTION_CHANGE, dataProviderChangedHandler);
				_dataProvider.removeAll();
			}
			
			_dataProvider = value;
			
			if (_dataProvider) {
				_dataProvider.addEventListener(CollectionEvent.COLLECTION_CHANGE, dataProviderChangedHandler, false, 0, true);
				var data:HierarchicalData	= new HierarchicalData(dataProvider);
				data.childrenField			= "subitems";
				scheduleGrid.dataProvider	= data;
				_dataProvider.refresh();
			}
			else {
				scheduleGrid.dataProvider = null;
			}
		}
		
		protected function get dataProvider():ArrayCollection {
			return _dataProvider;
		}
		
		public function getRawDataProvider():ArrayCollection {
			return _dataProvider;
		}
		
		public function getAllSchedules():Array {
			return _dataProvider.toArray();
		}
		
		
		// the new item will be added to this dataProvider (not the hierarhical one)
		public function addProduct(product:AbstractSiemensProduct, quantity:int = 1, copyFormParams:Boolean = true):void {
			LogUtils.Debug("------- THIS FUNCTION MUST BE OVERRIDEN -------");
		}
		
		
		// --------------- Mediator Functions
		
		override public function onRegister():void {
			dataProvider = new ArrayCollection();
			
			// Set stuff
			scheduleGrid.dragEnabled		= true;
			scheduleGrid.lockedColumnCount	= 1;
			
			// set UI listeners
			scheduleGrid.addEventListener(DragEvent.DRAG_ENTER,	dragEnterHandler, false, 0, true);
			scheduleGrid.addEventListener(DragEvent.DRAG_COMPLETE, dragCompleteHandler, false, 0, true);
			scheduleGrid.addEventListener(NativeDragEvent.NATIVE_DRAG_START, scheduleNativeDragStartHandler, false, 0, true);
			scheduleGrid.addEventListener(NativeDragEvent.NATIVE_DRAG_ENTER, onScheduleNativeDragEnter, false, 0, true);
			scheduleGrid.addEventListener(MouseEvent.RIGHT_MOUSE_DOWN, scheduleRightClickHandler, false, 0, true);
			scheduleGrid.addEventListener(AdvancedDataGridEvent.ITEM_EDIT_END, onItemEditEnd, false, 0, true);
			scheduleGrid.addEventListener(IndexChangedEvent.HEADER_SHIFT, gridHeaderShiftHandler, false, 0, true);
			scheduleGrid.iconFunction = scheduleIconFunction;
			
			// schedule header 
			view.scheduleRemoveButton.addEventListener(MouseEvent.CLICK, scheduleRemoveClickHandler, false, 0, true);
			view.scheduleRemoveAllButton.addEventListener(MouseEvent.CLICK, scheduleRemoveClickHandler, false, 0, true);
			
			// set up preferences
			var prefs:UserPreferencesProxy = facade.retrieveProxy(UserPreferencesProxy.NAME) as UserPreferencesProxy;
			preferences = prefs.preferences;
			
			// set grid Schedule Columns
			loadGridColumns();
			
			// create right click contex menu 
			createContextMenu();
			
			// update the multiplier
			updateMultiplier(preferences.globalMultiplier, true);
		}
		
		
		protected function loadGridColumns():void {
			LogUtils.Debug("------- THIS FUNCTION MUST BE OVERRIDEN -------");
		}
		
		
		protected function scheduleIconFunction(item:IScheduleItem):Class {
			return item.product.icon;
		}
		
		
		protected function onItemEditEnd(e:AdvancedDataGridEvent):void {
			var col:AdvancedDataGridColumn	= scheduleGrid.columns[e.columnIndex];
			var old:Object					= e.itemRenderer.data[e.dataField];
			var nObj:Object					= scheduleGrid.itemEditorInstance[col.editorDataField];
			
			if (old == null || nObj == null)
				return;
			
			if (old.toString() != nObj.toString())
				sendNotification(MessageConstants.PROJECT_DIRTY);
		}
		
		
		protected function scheduleRightClickHandler(event:MouseEvent):void {
			var renderer:IListItemRenderer = scheduleGrid.mx_internal::getItemRendererForMouseEvent(event);
			if (renderer) {
				scheduleGrid.selectedItem = renderer.data;
			}
		}
		
		
		protected function gridHeaderShiftHandler(event:IndexChangedEvent):void {
			LogUtils.Debug("------- THIS FUNCTION MUST BE OVERRIDEN -------");
		}
		
		
		protected function scheduleRemoveClickHandler(event:MouseEvent):void {	
			LogUtils.Debug("------- THIS FUNCTION MUST BE OVERRIDEN -------");
		}
		
		
		protected function dataProviderChangedHandler(event:CollectionEvent):void {
			// make the project dirty
			if (event.kind != CollectionEventKind.REFRESH){
				sendNotification(MessageConstants.PROJECT_DIRTY);
			}
			// make export buttons available
			switch (event.kind) {
				case CollectionEventKind.ADD:
				case CollectionEventKind.REMOVE:
				case CollectionEventKind.RESET:
					sendNotification(MessageConstants.SCHEDULE_CHANGED_LEN, dataProvider.length);
					break;
			}
		}
		
		
		// ---------- Context Menu functions
		
		protected function createContextMenu():void {
			var menu:ContextMenu = new ContextMenu();
			menu.hideBuiltInItems();
			var deleteMenuItem:ContextMenuItem	= new ContextMenuItem("Delete");
			deleteMenuItem.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, deleteMenuItemHandler, false, 0, true);
			menu.customItems = [deleteMenuItem];
			scheduleGrid.contextMenu = menu;
		}
		
		
		protected function deleteMenuItemHandler(event:ContextMenuEvent):void {
			LogUtils.Debug("------- THIS FUNCTION MUST BE OVERRIDEN -------");
		}
		
		
		// ------------- Notification Handle Functions
		
		protected function updateMultiplier(newMultiplier:Number, overrideLabel:Boolean = false):void {
			if (isNaN(newMultiplier)) {
				newMultiplier = 1;
			}
			if (productType != SwizzardGlobals.CURRENT_PRODUCT_TYPE || overrideLabel) {
				view.multiplierInput.text = newMultiplier + "";
			}
			for each (var scheduleItem:IScheduleItem in dataProvider) {
				scheduleItem.priceMultiplier = newMultiplier;
			}	
		}
		
		
		override public function listNotificationInterests():Array {
			LogUtils.Debug("------- THIS FUNCTION MUST BE OVERRIDEN -------");
			return null;
		}
		
		
		override public function handleNotification(notification:INotification):void {
			LogUtils.Debug("------- THIS FUNCTION MUST BE OVERRIDEN -------");
		}
		
		
		// -------- Native drag-drop function: to save/load .svf/.sdf files
		
		protected function scheduleNativeDragStartHandler(event:NativeDragEvent):void {
			event.preventDefault();
			var items:Array		= event.clipboard.getData(event.clipboard.formats[0]) as Array;
			var cb:Clipboard 	= event.clipboard;
			var tempDir:File	= File.createTempDirectory();
			var allFiles:Array	= [];
			var fileName:String = "";
			
			for each (var sch:IScheduleItem in scheduleGrid.selectedItems) {
				var f:File = ScheduleItemWriter.writeScheduleItem(sch, tempDir);
				allFiles.push(f);
			}
			
			cb.setData(ClipboardFormats.FILE_LIST_FORMAT, allFiles);
		}
		
		
		protected function dragCompleteHandler(event:DragEvent):void {
			event.preventDefault();
		}
		
		
		protected function onScheduleNativeDragEnter(e:NativeDragEvent):void {
			if (e.relatedObject)
				return; // Do not drag from within flex. only from the OS
			
			var fileList:Array = e.clipboard.getData(ClipboardFormats.FILE_LIST_FORMAT) as Array;
			if (fileList && fileList.length > 0 && (fileList[0] as File).extension.toUpperCase() == fileExtension.toLocaleUpperCase()) {
				NativeDragManager.acceptDragDrop(scheduleGrid);
				scheduleGrid.addEventListener(NativeDragEvent.NATIVE_DRAG_DROP, onScheduleNativeDrop, false, 0, true);
				scheduleGrid.addEventListener(NativeDragEvent.NATIVE_DRAG_EXIT, onScheduleNativeDragExit, false, 0, true);
			}
		}
		
		
		protected function onScheduleNativeDragExit(e:NativeDragEvent):void {
			e.target.removeEventListener(NativeDragEvent.NATIVE_DRAG_DROP, onScheduleNativeDrop);
			e.target.removeEventListener(NativeDragEvent.NATIVE_DRAG_EXIT, onScheduleNativeDragExit);
		}
		
		
		protected function onScheduleNativeDrop(e:NativeDragEvent):void {
			LogUtils.Debug("------- THIS FUNCTION MUST BE OVERRIDEN -------");
		}
		
		
		// ------------ Drag-drop Function - to add items to the list
		
		protected function dragEnterHandler(event:DragEvent):void {
			var items_raw:* = event.dragSource.dataForFormat(event.dragSource.formats[0]);
			if (items_raw == null) return;
			
			var items:Array = [];
			for (var i:int = 0; i < items_raw.length; i++) {
				items.push(items_raw[i]);
			}
			
			if (event.dragInitiator == scheduleGrid) return;			
			
			if (items.every(isCompatibleDropObject)) {
				scheduleGrid.addEventListener(DragEvent.DRAG_OVER,	dragOverHandler, 	false, 0, true);
				scheduleGrid.addEventListener(DragEvent.DRAG_DROP,	dragDropHandler, 	false, 0, true);
				scheduleGrid.addEventListener(DragEvent.DRAG_EXIT,	dragExitHandler,	false, 0, true);
				DragManager.acceptDragDrop(scheduleGrid);					
			}
		}
		
		protected function isCompatibleDropObject(item:Object, index:int, arr:Array):Boolean {
			LogUtils.Debug("------- THIS FUNCTION MUST BE OVERRIDEN -------");
			return false;
		}
		
		
		protected function dragOverHandler(event:DragEvent):void {
			scheduleGrid.showDropFeedback(event);
		}
		
		
		// this one is pretty much a copy of addItem - only the index where to add the items differ
		protected function dragDropHandler(event:DragEvent):void {
			LogUtils.Debug("------- THIS FUNCTION MUST BE OVERRIDEN -------");
		}
		
		
		protected function dragExitHandler(event:DragEvent):void {
			scheduleGrid.hideDropFeedback(event);
			scheduleGrid.removeEventListener(DragEvent.DRAG_OVER, dragOverHandler);
			scheduleGrid.removeEventListener(DragEvent.DRAG_DROP, dragDropHandler);
			scheduleGrid.removeEventListener(DragEvent.DRAG_EXIT, dragExitHandler);
		}
	}
}