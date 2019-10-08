package Swizzard.Favorites.Mediators
{
	import flash.desktop.Clipboard;
	import flash.desktop.ClipboardFormats;
	import flash.desktop.NativeDragManager;
	import flash.events.MouseEvent;
	import flash.events.NativeDragEvent;
	import flash.filesystem.File;
	
	import mx.controls.Alert;
	import mx.events.DragEvent;
	import mx.managers.DragManager;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Favorites.Interfaces.IFavoritesWindow;
	import Swizzard.Favorites.Proxies.BaseFavoritesProxy;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.System.Persistence.IO.ScheduleItemWriter;
	
	import org.puremvc.as3.patterns.mediator.Mediator;
	

	public class BaseFavoritesMediator extends Mediator
	{
		protected var FAVORITES_PROXY_NAME:String;
		protected var FILE_EXTENSION:String;
		protected var COLUMNS:Array;
		protected var TITLE:String;
		protected var INOF_TEXT:String;
		
		
		protected var window:IFavoritesWindow;
		protected var registered:Boolean = false;
		
		
		public function BaseFavoritesMediator(mediatorName:String, newViewComponent:Object=null) {
			super(mediatorName);
			setWindowView(newViewComponent as IFavoritesWindow);
		}
		
		
		override public function onRegister():void {
			super.onRegister();
			registered = true;
			updateFavoritesWindow();
		}
		
		
		override public function onRemove():void {
			setWindowView(null);
			super.onRemove();
		}
		
		
		public function setWindowView(newView:IFavoritesWindow):void {
			if (window == newView)
				return;
			
			if (window) {
				window.list.removeEventListener(DragEvent.DRAG_ENTER, windowDragEnterHandler, false);
				window.list.removeEventListener(NativeDragEvent.NATIVE_DRAG_START, nativeDragStartHandler, false);
				window.list.removeEventListener(NativeDragEvent.NATIVE_DRAG_ENTER, nativeDragEnterHandler, false);
				window.removeButton.removeEventListener(MouseEvent.CLICK, removeButtonClickHandler, false);
			}
			
			window = newView;
			
			if (window) {
				updateFavoritesWindow();
				window.list.addEventListener(DragEvent.DRAG_ENTER, windowDragEnterHandler, false, 0, true);
				window.list.addEventListener(NativeDragEvent.NATIVE_DRAG_START, nativeDragStartHandler, false, 0, true);
				window.list.addEventListener(NativeDragEvent.NATIVE_DRAG_ENTER, nativeDragEnterHandler, false, 0, true);
				window.removeButton.addEventListener(MouseEvent.CLICK, removeButtonClickHandler, false, 0, true);
			}
		}
		
		
		private function removeButtonClickHandler(event:MouseEvent):void {
			var proxy:BaseFavoritesProxy = facade.retrieveProxy(FAVORITES_PROXY_NAME) as BaseFavoritesProxy;
			var items:Array	= window.list.selectedItems;
			for each (var item:IScheduleItem in items) {
				proxy.removeItem(item);
			}
		}
		
		
		protected function updateFavoritesWindow():void {
			if (registered && window != null) {
				var proxy:BaseFavoritesProxy = facade.retrieveProxy(FAVORITES_PROXY_NAME) as BaseFavoritesProxy;
				window.list.dataProvider = proxy.favorites;
				window.list.columns = COLUMNS;
				window.title = TITLE;
				window.infoText = INOF_TEXT;
			}
		}
		
		
		
		// ------------ Mediator implementation Functions --------------------
		// -------------------------------------------------------------------
		
		
		// ---------- Drag and Drop Functions ----------------
		
		/**
		 * This is only a stub and needs to be overriden.
		 */
		protected function areItemsCompatibleObjects(item:*, index:int, arr:Array):Boolean {
			return false;
		}
		
		
		private function windowDragEnterHandler(event:DragEvent):void {
			event.preventDefault();
			
			if (event.dragInitiator == window.list) return;
			
			var items_raw:* = event.dragSource.dataForFormat(event.dragSource.formats[0]);
			if (items_raw == null) return;
			
			var items:Array = [];
			for (var i:int = 0; i < items_raw.length; i++) {
				items.push(items_raw[i]);
			}
			
			if (items.every(areItemsCompatibleObjects)) {
				DragManager.acceptDragDrop(window.list);
				window.list.addEventListener(DragEvent.DRAG_OVER, windowDragOverHandler, false, 0, true);
				window.list.addEventListener(DragEvent.DRAG_DROP, windowDragDropHandler, false, 0, true);
				window.list.addEventListener(DragEvent.DRAG_EXIT, windowDragExitHandler, false, 0, true);
			}
		}
		
		
		private function windowDragOverHandler(event:DragEvent):void {
			DragManager.showFeedback(DragManager.COPY);
		}
		
		
		private function windowDragDropHandler(event:DragEvent):void {
			event.preventDefault();
			windowDragExitHandler(event);
			
			var items_raw:* = event.dragSource.dataForFormat(event.dragSource.formats[0]);
			if (items_raw == null) return;
			
			var items:Array = [];
			for (var i:int = 0; i < items_raw.length; i++) {
				items.push(items_raw[i]);
			}
			
			var proxy:BaseFavoritesProxy = facade.retrieveProxy(FAVORITES_PROXY_NAME) as BaseFavoritesProxy;
			
			for each (var item:Object in items) {
				if (item is AbstractSiemensProduct) {
					if (proxy.hasProduct(item as AbstractSiemensProduct)) {
						window.callLater(Alert.show, [AbstractSiemensProduct(item).partNumber + " already exists in favorites", "Already Exists", 4, window]);
					}
					else {
						proxy.addProduct(item as AbstractSiemensProduct);
					}
				}
				else if (item is IScheduleItem) {
					var prod:AbstractSiemensProduct	= IScheduleItem(item).product;
					if (proxy.hasProduct(prod)) {
						window.callLater(Alert.show, [prod.partNumber + " already exists in favorites", "Already Exists", 4, window]);
					}
					else {
						proxy.addScheduleItem(item as IScheduleItem, true);
					}
				}
			}
		}
		
		
		private function windowDragExitHandler(event:DragEvent):void {
			event.target.removeEventListener(DragEvent.DRAG_OVER, windowDragOverHandler);
			event.target.removeEventListener(DragEvent.DRAG_DROP, windowDragDropHandler);
			event.target.removeEventListener(DragEvent.DRAG_EXIT, windowDragExitHandler);
		}
		
		
		
		// ---------- Native Drag and Drop function ----------
		
		private function nativeDragStartHandler(event:NativeDragEvent):void {
			event.preventDefault();
			var items:Array	= event.clipboard.getData(event.clipboard.formats[0]) as Array;
			var cb:Clipboard	= event.clipboard;
			var tempDir:File	= File.createTempDirectory();
			var allFiles:Array	= [];
			
			for each(var sch:IScheduleItem in items) {
				var tempFile:File = ScheduleItemWriter.writeScheduleItem(sch, tempDir);
				allFiles.push(tempFile);
			}
			
			cb.setData(ClipboardFormats.FILE_LIST_FORMAT, allFiles);
		}
		
		
		private function nativeDragEnterHandler(event:NativeDragEvent):void {
			if (event.relatedObject)
				return; // Do not drag from within flex. only from the OS
				
			var fileList:Array	= event.clipboard.getData(ClipboardFormats.FILE_LIST_FORMAT) as Array;
			if (fileList && fileList.every(areFavoritesFiles)) {
				NativeDragManager.acceptDragDrop(window.list);
				window.list.addEventListener(NativeDragEvent.NATIVE_DRAG_DROP, nativeDragDropHandler, false, 0, true);
				window.list.addEventListener(NativeDragEvent.NATIVE_DRAG_EXIT, nativeDragExitHandler, false, 0, true); 
			}
		}
		
		
		protected function areFavoritesFiles(file:File, index:int, arr:Array):Boolean {
			return (file.extension.toLowerCase() == FILE_EXTENSION);
		}
		
		
		private function nativeDragDropHandler(event:NativeDragEvent):void {
			nativeDragExitHandler(event);
			var fileList:Array			= event.clipboard.getData(ClipboardFormats.FILE_LIST_FORMAT) as Array;
			var proxy:BaseFavoritesProxy	= facade.retrieveProxy(FAVORITES_PROXY_NAME) as BaseFavoritesProxy;
			
			for each (var file:File in fileList) {
				proxy.addFile(file);	
			}
			proxy.refresh();
		}
		
		
		private function nativeDragExitHandler(event:NativeDragEvent):void {
			event.target.removeEventListener(NativeDragEvent.NATIVE_DRAG_DROP, nativeDragDropHandler);
			event.target.removeEventListener(NativeDragEvent.NATIVE_DRAG_EXIT, nativeDragExitHandler);
		}
	}
}