package Swizzard.System.Mediators.Valves
{
	import flash.desktop.ClipboardFormats;
	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import flash.events.NativeDragEvent;
	import flash.filesystem.File;
	
	import mx.collections.ArrayCollection;
	import mx.collections.HierarchicalCollectionView;
	import mx.controls.Alert;
	import mx.events.DragEvent;
	import mx.events.IndexChangedEvent;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.Enumeration.ExtendedProductType;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Data.Models.Enumeration.Valves.ValveMedium;
	import Swizzard.Data.Utils.SynchronousDataUtility;
	import Swizzard.System.ApplicationMediator;
	import Swizzard.System.Models.SelectedFormParameters;
	import Swizzard.System.Models.VSSTProject;
	import Swizzard.System.Models.ValveScheduleItem;
	import Swizzard.System.Persistence.IO.ScheduleItemReader;
	import Swizzard.System.Persistence.Proxies.PersistenceProxy;
	import Swizzard.System.Utils.MessageConstants;
	import Swizzard.UI.Components.DataGridClasses.GridColumnsUtil;
	
	import org.puremvc.as3.interfaces.INotification;
	import Swizzard.System.Mediators.BaseScheduleMediator;
	

	public class ValveScheduleMediator extends BaseScheduleMediator
	{
		public static const NAME:String	= "valveScheduleMediator_asd80w";
		
		
		public function ValveScheduleMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
			fileExtension = ValveScheduleItem.FILE_EXTENSION_NAME;
			productType = ProductType.VALVE;
		}
		
		
		// the new item will be added to this dataProvider (not the hierarhical one)
		override public function addProduct(product:AbstractSiemensProduct, quantity:int = 1, copyFormParams:Boolean = true):void {
			if ((product.assemblyOnly) && (product.productType != ProductType.ASSEMBLY)) {
				Alert.show("this item can only be added as an assembly.");
				return;
			}
			
			var appMediator:ApplicationMediator				= facade.retrieveMediator(ApplicationMediator.NAME) as ApplicationMediator;
			var formMediator:ValveParametersFormMediator	= facade.retrieveMediator(ValveParametersFormMediator.NAME) as ValveParametersFormMediator;
			
			sendNotification(MessageConstants.PROJECT_DIRTY);
			
			var scheduleItem:ValveScheduleItem	= new ValveScheduleItem();
			scheduleItem.product			= product;
			scheduleItem.priceMultiplier	= preferences.globalMultiplier; 
			
			// gather form params from the current Form
			var formParams:SelectedFormParameters	= new SelectedFormParameters();
			formParams.pressureDrop		= formMediator.pressureDrop;
			formParams.pressureDrop_B	= formMediator.pressureDrop_B;
			formParams.requiredFlow		= formMediator.requiredFlow;
			formParams.selectedMedium	= formMediator.selectedMedium;
			if (formParams.selectedMedium == ValveMedium.STEAM) {
				formParams.steamQuantity		= formMediator.steamQuantity;
				formParams.steamSpecificVolume	= formMediator.lastSpecificVolume;
			}
			else if (formParams.selectedMedium == ValveMedium.GLYCOL) {
				formParams.percentGlycol = formMediator.percentGlycol;
			}
			
			// override schedule item properties with those from the current Form
			if (copyFormParams) {
				scheduleItem.calculatedCv 	= appMediator.valveQueryModel.valve.CV;
				scheduleItem.calculatedCv_B = appMediator.valveQueryModel.valve.CVB;
				scheduleItem.gpm 			= formMediator.requiredFlow;
				scheduleItem.formParams 	= formParams; 
			}
			
			var foundExistingProduct:Boolean; 
			var foundExistingSubProduct:Boolean	= (product.subProductPartNumber == null); // Don't search if null
			for each(var item:ValveScheduleItem in dataProvider) {
				if (!foundExistingProduct) {
					if (item.product.actualPartNumber == product.actualPartNumber) {
						if (copyFormParams) {
							var gpmValue:Number = formMediator.requiredFlow;
							item.setQuantity(item.quantity + 1, formParams, 
								appMediator.valveQueryModel.valve.CV, appMediator.valveQueryModel.valve.CVB, gpmValue, true);
						}
						else {
							item.setQuantity(item.quantity + 1, null, 0, 0, 0, false);
						}
						scheduleGrid.expandItem(item, true);
						foundExistingProduct = true;
						
						if (foundExistingSubProduct)
							break;
					}
				}
				
				if (!foundExistingSubProduct) {
					if (item.product.actualPartNumber == product.subProductPartNumber) {
						// Sub Products don't have additional data
						item.setQuantity(item.quantity + 1, null, 0, 0, 0, true);
						scheduleGrid.expandItem(item, true);
						foundExistingSubProduct	= true;
						
						if (foundExistingProduct)
							break;
					}
				}
			}
			
			if (!foundExistingProduct) {
				dataProvider.addItem(scheduleItem);
			}
			
			if (!foundExistingSubProduct) {
				// Accessory Part
				if (product.subProductPartNumber) {
					var util:SynchronousDataUtility			= new SynchronousDataUtility();
					var subProduct:AbstractSiemensProduct	= util.getMiscProductByPartNumber(product.subProductPartNumber);
					if (subProduct) {
						var subSchedule:ValveScheduleItem	= new ValveScheduleItem();
						subSchedule.product				= subProduct;
						subSchedule.priceMultiplier		= scheduleItem.priceMultiplier;
						subSchedule.calculatedCv		= appMediator.valveQueryModel.valve.CV;
						subSchedule.calculatedCv_B		= appMediator.valveQueryModel.valve.CVB;
						dataProvider.addItem(subSchedule);
					}
				}
			}
			
			HierarchicalCollectionView(scheduleGrid.dataProvider).refresh();
		}

		
		// --------------- Mediator Functions
		
		override protected function loadGridColumns():void {
			var scheduleColumns:Array = preferences.valveScheduleGridColumns;
			GridColumnsUtil.setPreferencesToMXColumns(scheduleColumns, GridColumnsUtil.getValveScheduleColumn());
			if (scheduleColumns == null) {
				scheduleColumns = GridColumnsUtil.getValveScheduleColumn();
			}
			preferences.valveScheduleGridColumns 	= scheduleColumns;
			scheduleGrid.columns 					= scheduleColumns;
			view.gridColumnOptions.columns 			= new ArrayCollection(scheduleColumns);
		}
		
		
		override protected function gridHeaderShiftHandler(event:IndexChangedEvent):void {
			preferences.valveScheduleGridColumns = scheduleGrid.columns;
		}
		
		
		override protected function scheduleRemoveClickHandler(event:MouseEvent):void {	
			var hCollection:HierarchicalCollectionView = scheduleGrid.dataProvider as HierarchicalCollectionView;
			if (event.target == view.scheduleRemoveButton) {
				for each (var scheduleItem:ValveScheduleItem in scheduleGrid.selectedItems) {
					if (scheduleItem.parent && scheduleItem.parent.quantity > 1) {
						// Remove at location
						hCollection.removeChild(scheduleItem.parent, scheduleItem);	
						scheduleItem.parent.quantity = scheduleItem.parent.quantity - 1;
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
			var scheduleItem:ValveScheduleItem = scheduleGrid.selectedItem as ValveScheduleItem;
			var hCollection:HierarchicalCollectionView	= scheduleGrid.dataProvider as HierarchicalCollectionView;
			
			if (scheduleItem == null)
				return;
			
			if (scheduleItem.parent && scheduleItem.parent.quantity > 1) {
				// Remove at location
				hCollection.removeChild(scheduleItem.parent, scheduleItem);
				scheduleItem.parent.quantity = scheduleItem.parent.quantity - 1;
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
					var scheduleColumns:Array 		= GridColumnsUtil.getValveScheduleColumn();
					scheduleGrid.columns 			= scheduleColumns;
					preferences.valveScheduleGridColumns	= scheduleColumns;
					view.gridColumnOptions.columns = new ArrayCollection(scheduleColumns);
					break;
				
				case PersistenceProxy.PROJECT_CREATED:
				case PersistenceProxy.PROJECT_LOADED:
					var project:VSSTProject	= notification.getBody() as VSSTProject;
					dataProvider			= project.valveSchedule;
					break;
			}
		}
		
		
		// -------- Native drag-drop function: to save/load .svf files ---------------------------
		
		override protected function onScheduleNativeDrop(e:NativeDragEvent):void  {
			e.target.removeEventListener(NativeDragEvent.NATIVE_DRAG_DROP, onScheduleNativeDrop);
			e.target.removeEventListener(NativeDragEvent.NATIVE_DRAG_EXIT, onScheduleNativeDragExit);
			
			var items:Array = [];
			var fileList:Array	= e.clipboard.getData(ClipboardFormats.FILE_LIST_FORMAT) as Array;
			if (fileList && fileList.length > 0) {
				for each(var f:File in fileList) {
					var schItem:ValveScheduleItem = ScheduleItemReader.readScheduleItem(f) as ValveScheduleItem;
					if (!dataProvider) {
						dataProvider = new ArrayCollection();
					}
					items.push(schItem);
				}
			}
			
			continueDropItems(items, dataProvider.length, false);
		}
		
		
		// ------------ Drag-drop Function - to add items to the list ---------------------------
		
		override protected function isCompatibleDropObject(item:Object, index:int, arr:Array):Boolean {
			if (item is ValveScheduleItem) return true;
			if (item is AbstractSiemensProduct) {
				var absProduct:AbstractSiemensProduct = item as AbstractSiemensProduct;
				if (absProduct.extendedProductType & ExtendedProductType.VALVE) return true;
				if (absProduct.extendedProductType & ExtendedProductType.ACTUATOR_VALVE) return true;
				if (absProduct.extendedProductType & ExtendedProductType.ASSEMBLY) return true;
			}
			return false;
		}
		
		
		// this one is pretty much a copy of addItem - only the index where to add the items differ
		override protected function dragDropHandler(event:DragEvent):void {
			dragExitHandler(event);	
			
			// get info from event
			var copyFormParameters:Boolean = ((event.dragInitiator.name != "searchInputBox") && (event.dragInitiator.name != "searchBoxList") && (event.dragInitiator.name != "favoritesList"));
			
			// get dragged items
			var items_raw:* = event.dragSource.dataForFormat(event.dragSource.formats[0]);
			if (items_raw == null) {
				return;
			}
			var items:Array = [];
			for (var i:int = 0; i < items_raw.length; i++) {
				items.push(items_raw[i]);
			}
			
			// get index dropped at
			var dropIndex:uint = scheduleGrid.calculateDropIndex(event);
			if (dropIndex > scheduleGrid.dataProvider.length) {
				dropIndex = Math.max(scheduleGrid.dataProvider.length, 0);
			}
			
			// continue with the drop
			continueDropItems(items, dropIndex, copyFormParameters);
		}
		
		
		protected function continueDropItems(items:Array, dropIndex:uint, copyFormParams:Boolean):void {
			var appMediator:ApplicationMediator				= facade.retrieveMediator(ApplicationMediator.NAME) as ApplicationMediator;
			var formMediator:ValveParametersFormMediator	= facade.retrieveMediator(ValveParametersFormMediator.NAME) as ValveParametersFormMediator;
			
			sendNotification(MessageConstants.PROJECT_DIRTY);
			
			for each (var droppedItem:Object in items) {
				var scheduleItem:ValveScheduleItem;
				if (droppedItem is ValveScheduleItem) {
					scheduleItem	= (droppedItem as ValveScheduleItem).clone(false) as ValveScheduleItem;
				}
				else {
					scheduleItem					= new ValveScheduleItem();
					scheduleItem.product			= droppedItem as AbstractSiemensProduct;
					scheduleItem.priceMultiplier	= preferences.globalMultiplier;
				}
				
				var product:AbstractSiemensProduct	= scheduleItem.product;	
				if ((product.assemblyOnly) && (product.productType != ProductType.ASSEMBLY)) {
					Alert.show("this item can only be added as an assembly.");
					continue;
				}											
				
				// gather form params from the current Form
				var formParams:SelectedFormParameters	= new SelectedFormParameters();
				formParams.pressureDrop		= formMediator.pressureDrop;
				formParams.pressureDrop_B	= formMediator.pressureDrop_B;
				formParams.requiredFlow		= formMediator.requiredFlow;
				formParams.selectedMedium	= formMediator.selectedMedium;
				if (formParams.selectedMedium == ValveMedium.STEAM) {
					formParams.steamQuantity		= formMediator.steamQuantity;
					formParams.steamSpecificVolume	= formMediator.lastSpecificVolume;
				}
				else if (formParams.selectedMedium == ValveMedium.GLYCOL) {
					formParams.percentGlycol = formMediator.percentGlycol;
				}
				
				// override schedule item properties with those from the current Form
				if (copyFormParams) {
					scheduleItem.calculatedCv 	= appMediator.valveQueryModel.valve.CV;
					scheduleItem.calculatedCv_B = appMediator.valveQueryModel.valve.CVB;
					scheduleItem.gpm 			= formMediator.requiredFlow;
					scheduleItem.formParams 	= formParams; 
				}
				
				var foundExistingProduct:Boolean; 
				var foundExistingSubProduct:Boolean	= (product.subProductPartNumber == null); // Don't search if null
				for each(var item:ValveScheduleItem in dataProvider) {
					if (!foundExistingProduct) {
						if (item.product.actualPartNumber == product.actualPartNumber) {
							if (copyFormParams) {
								var gpmValue:Number = formMediator.requiredFlow;
								item.setQuantity(item.quantity + 1, formParams, 
									appMediator.valveQueryModel.valve.CV, appMediator.valveQueryModel.valve.CVB, gpmValue, true);
							}
							else {
								item.setQuantity(item.quantity + 1, null, 0, 0, 0, false);
							}
							scheduleGrid.expandItem(item, true);
							foundExistingProduct = true;
							
							if (foundExistingSubProduct)
								break;
						}
					}
					
					if (!foundExistingSubProduct) {
						if (item.product.actualPartNumber == product.subProductPartNumber) {
							// Sub Products don't have additional data
							item.setQuantity(item.quantity + 1, null, 0, 0, 0, true);
							scheduleGrid.expandItem(item, true);
							foundExistingSubProduct	= true;
							
							if (foundExistingProduct)
								break;
						}
					}
				}
				
				if (!foundExistingProduct) {
					if (dropIndex > dataProvider.length)
						dropIndex	= dataProvider.length;
					dataProvider.addItemAt(scheduleItem, dropIndex);
					dropIndex++;
				}
				
				if (!foundExistingSubProduct) {
					// Accessory Part
					if (product.subProductPartNumber) {
						var util:SynchronousDataUtility			= new SynchronousDataUtility();
						var subProduct:AbstractSiemensProduct	= util.getMiscProductByPartNumber(product.subProductPartNumber);
						if (subProduct) {
							var subSchedule:ValveScheduleItem	= new ValveScheduleItem();
							subSchedule.product				= subProduct;
							subSchedule.priceMultiplier		= scheduleItem.priceMultiplier;
							subSchedule.calculatedCv		= appMediator.valveQueryModel.valve.CV;
							subSchedule.calculatedCv_B		= appMediator.valveQueryModel.valve.CVB;
							dataProvider.addItemAt(subSchedule, dropIndex);
						}
					}
				}
				
				HierarchicalCollectionView(scheduleGrid.dataProvider).refresh();
			}
		}
	}
}