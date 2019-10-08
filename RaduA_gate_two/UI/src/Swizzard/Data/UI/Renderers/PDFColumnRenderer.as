package Swizzard.Data.UI.Renderers
{
	import flash.display.Bitmap;
	import flash.events.MouseEvent;
	import flash.geom.Matrix;
	import flash.geom.Point;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.controls.Menu;
	import mx.controls.dataGridClasses.DataGridListData;
	import mx.controls.listClasses.BaseListData;
	import mx.controls.listClasses.IDropInListItemRenderer;
	import mx.controls.listClasses.IListItemRenderer;
	import mx.core.UIComponent;
	import mx.events.MenuEvent;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensAccessory;
	import Swizzard.Data.Models.SiemensActuator;
	import Swizzard.Data.Models.SiemensAssembly;
	import Swizzard.Data.Models.SiemensDamper;
	import Swizzard.Data.Models.SiemensDocument;
	import Swizzard.Data.Models.SiemensValve;
	import Swizzard.Data.Models.Enumeration.SiemensDocumentType;
	import Swizzard.Data.Utils.SynchronousDataUtility;
	import Swizzard.Images.EmbeddedImages;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.System.Utils.ContentUtility;
	import Swizzard.System.Utils.MenuItem;
	import Swizzard.System.Utils.MenuItemDataDescriptor;
	
	
	[RemoteClass]
	public class PDFColumnRenderer extends UIComponent implements IDropInListItemRenderer, IListItemRenderer
	{
		private var _listData:BaseListData;
		private var _data:Object;
		private var product:AbstractSiemensProduct;
		private var dataChanged:Boolean;
		
		private var bmp:Bitmap;
		private var menu:Menu;
		
		private var menuData:ArrayCollection;
		private var submittalSheets:MenuItem;
		private var technicalInstructions:MenuItem;
		private var installationInstructions:MenuItem;
		
				
		public function PDFColumnRenderer() {
			super();
			
			submittalSheets				= new MenuItem();
			submittalSheets.name		= "Submittal Sheets";
			
			technicalInstructions		= new MenuItem();
			technicalInstructions.name	= "Technical Instructions";
			
			installationInstructions		= new MenuItem();
			installationInstructions.name	= "Installation Instructions";
									
			useHandCursor	= true;
			buttonMode		= true;
			addEventListener(MouseEvent.CLICK, clickHandler, false, 100, true);
		}
		
		
		public function set data(value:Object):void {
			if (_data == value) return;
			_data = value;
			dataChanged	= true;
			invalidateProperties();
		}
		
		public function get data():Object {
			return _data;
		}
		
		
		public function set listData(value:BaseListData):void {
			_listData	= value;
		}
		
		public function get listData():BaseListData {
			return _listData;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (bmp == null) {
				bmp = new EmbeddedImages.acrobat16_img;
			}
			
			if (menu == null) {
				menu = new Menu();
				menu.labelField	= "name";
				menu.dataDescriptor	= new MenuItemDataDescriptor();
				menu.addEventListener(MenuEvent.ITEM_CLICK, itemClickHandler, false, 0, true);
				
				menuData	= new ArrayCollection(	[
					submittalSheets,
					technicalInstructions,
					installationInstructions /* ,
					otherDocuments */
				]);
				Menu.popUpMenu(menu, this, menuData);
			}
		}
		
		
		override protected function commitProperties():void {
			super.commitProperties();
			
			if (dataChanged) {
				dataChanged	= false;
				
				if ((data != null) && ((data is AbstractSiemensProduct) || (data is IScheduleItem))) {
					var product:AbstractSiemensProduct = (data is IScheduleItem) ? (data as IScheduleItem).product : data as AbstractSiemensProduct;
					if (this.parent != null) {
						if (data is IScheduleItem) {
							// If this renderer is used for a schedule
							if (product is SiemensValve) {
								if (DataGridListData(listData).dataField != "valve:dataSheetLink") {
									enabled	= false;
								}
								else {
									enabled	= true;
								}
							}
							else if (product is SiemensActuator) {
								if (DataGridListData(listData).dataField != "actuator:dataSheetLink") {
									enabled	= false;
								}
								else {
									enabled	= true;
								}
							}
							else if (product is SiemensDamper) {
								enabled	= true;
							}
							else if (product is SiemensAccessory) {
								enabled = true;
							}
							else {
								enabled	= true;
							}
						}
					}
					else {
						// If this renderer is not parented do not show it.
						enabled	= false;
					}
				}
			}
		}
		
		
		override protected function measure():void {
			super.measure();
			if (bmp) {
				measuredMinHeight	= bmp.height;
				measuredHeight		= bmp.height;
				measuredMinWidth	= bmp.width;
				measuredWidth		= bmp.width;
			}
		}
		
		
		override protected function updateDisplayList(w:Number, h:Number):void {
			super.updateDisplayList(w, h);
			
			with (this.graphics) {
				clear();
				// draw the bitmap
				if (this.enabled) {
					var matrix:Matrix = new Matrix();
					matrix.translate((w - bmp.width) / 2,  (h - bmp.height) / 2);
					beginBitmapFill(bmp.bitmapData, matrix, false, true);
					drawRect((w - bmp.width) / 2, (h - bmp.height) / 2, bmp.width, bmp.height);
					endFill();
				}
			}
		}
		
		
		override public function set enabled(value:Boolean):void {
			super.enabled = value;
			invalidateDisplayList();
		}
		
		
		private function clickHandler(event:MouseEvent):void {
			event.stopPropagation();
			event.stopImmediatePropagation();
			if ((data != null) && ((data is AbstractSiemensProduct) || (data is IScheduleItem))) {
				var product:AbstractSiemensProduct = (data is IScheduleItem) ? (data as IScheduleItem).product : data as AbstractSiemensProduct;
				if (product is SiemensAssembly) {
					switch (DataGridListData(listData).dataField) {
						case "valve:dataSheetLink":
							if (SiemensAssembly(product).valve) {
								product	= SiemensAssembly(product).valve;
							}
							break;
						
						case "actuator:dataSheetLink":
							if (SiemensAssembly(product).actuator) {
								product = SiemensAssembly(product).actuator;
							}
							break;
					}
				}
				
				if (!product.documents) {
					var util:SynchronousDataUtility	= new SynchronousDataUtility();
					util.hydrateProductDocuments(product);
					util.dispose();
				}
				
				var docs:Array	= product.documents.filter(nonImageDocumentFilter);
				
				submittalSheets.children.removeAll();
				installationInstructions.children.removeAll();
				technicalInstructions.children.removeAll();
				/* otherDocuments.children.removeAll(); */
				
				for each (var doc:SiemensDocument in docs) {
					switch (doc.documentType) {
						case SiemensDocumentType.SUBMITTAL_SHEET:
							submittalSheets.children.addItem(doc);
							break;
						
						case SiemensDocumentType.TECHNICAL_BULLETIN:
							technicalInstructions.children.addItem(doc);
							break;
						
						case SiemensDocumentType.INSTALLATION_INSTRUCTIONS:
							installationInstructions.children.addItem(doc);
							break;
												
						/* default:
							otherDocuments.children.addItem(doc); */
					}
				}
				
				menuData.refresh();
				
				var globalMouse:Point	= localToGlobal(new Point(this.mouseX, this.mouseY));
				if (this.stage) {
					// If its going off the right side of the screen, move it to the other side.
					if ((globalMouse.x + 150) > this.stage.nativeWindow.width)
						globalMouse.x	-= 160;
				}
				
				menu.show(globalMouse.x, globalMouse.y);
			}
		}
		
		
		private function nonImageDocumentFilter(item:SiemensDocument, index:int, arr:Array):Boolean {
			return !item.isImage;
		}
		
		
		private function itemClickHandler(event:MenuEvent):void {
			var document:SiemensDocument;
			
			if (event.item is MenuItem) {
				document = MenuItem(event.item).children.getItemAt(0) as SiemensDocument;
			}
			else if (event.item is SiemensDocument) {
				document = event.item as SiemensDocument;
			}
			
			if (document)
				downloadFile(document.location);	
		}
		
		
		private function downloadFile(url:String):void {
			var fileUrl:String	= ContentUtility.getContent(url);
			
			if (fileUrl.substr(0, 4) != "http") {
				navigateToURL(new URLRequest(fileUrl));
			} 
			else {
				try {
					navigateToURL(new URLRequest(url));
				}
				catch (err:Error) {
					Alert.show("Content has not been downloaded");
				}	
			}
			/* var file:File	= new File();
			file.download(new URLRequest(url), url.substr(url.lastIndexOf("/") + 1)); */ 
		}
	}
}