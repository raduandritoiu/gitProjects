package Swizzard.UI.Components.DataGridClasses
{
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.core.DragSource;
	import mx.managers.DragManager;
	import mx.managers.PopUpManager;
	
	import spark.components.CheckBox;
	import spark.components.DataGrid;
	import spark.components.Image;
	import spark.components.Label;
	import spark.events.GridEvent;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.UI.Controls.ProductToolTip;
	
	
	public class ProductDataGrid extends DataGrid
	{
		[SkinPart(required="true")]
		public var headerIcon:Image;
		
		[SkinPart(required="true")]
		public var titleLabel:Label;
		
		[SkinPart(required="true")]
		public var toolTipCheckBox:CheckBox;
		
		[SkinPart(required="true")]
		public var statusLabel:Label;
		
		[SkinPart(required="true")]
		public var editColumnButton:ColumnsPopUpButton;
		
		[SkinPart(required="true")]
		public var centerLabel:Label;
		
		
		private var _toolTip:ProductToolTip;
		private var toolTipAdded:Boolean = false;
		
		
		private var _icon:Class;
		private var iconChanged:Boolean;
		
		private var _title:String;
		private var titleChanged:Boolean;
		
		private var _statusText:String;
		private var statusTextChanged:Boolean;
		
		private var _centerText:String;
		private var centerTextChanged:Boolean;
		
		private var columnsChanged:Boolean;

		
		public function ProductDataGrid() {
			super();
			setStyle("alternatingRowColors", [0xf7f7f7, 0xffffff]);
			setStyle("selectionColor", 0x7fceff);
			setStyle("rollOverColor", 0xb2e1ff);
		}
		
		
		public function set title(value:String):void {
			_title = value;
			titleChanged = true;
			invalidateProperties();	
		}
		
		public function get title():String {
			return _title;
		}
		
		
		public function set statusText(value:String):void {
			_statusText	= value;
			statusTextChanged	= true;
			invalidateProperties();
		}
		
		public function get statusText():String {
			return _statusText;
		}
		
		
		public function set centerText(value:String):void {
			_centerText = value;
			centerTextChanged = true;
			invalidateProperties();		
		}
		
		public function get centerText():String {
			return _centerText;
		}
		
		
		public function set icon(value:Class):void {
			_icon = value;
			iconChanged = true;
			invalidateProperties();
		}
		
		public function get icon():Class {
			return _icon;
		}
		
		
		public function get shouldShowToolTip():Boolean {
			return toolTipCheckBox.selected;
		}
		
		
		public function get datapArray():ArrayCollection {
			return dataProvider as ArrayCollection;
		}
		
		
		public function get columnsArray():ArrayCollection {
			return columns as ArrayCollection;
		}
		
		
		override public function set columns(value:IList):void {
			super.columns	= value;
			columnsChanged	= true;
			invalidateProperties();
		}
		
		
		public function selectProduct(partNumber:String):void {
			for (var idx:int = 0; idx < dataProvider.length; idx++) {
				var product:AbstractSiemensProduct = dataProvider.getItemAt(idx) as AbstractSiemensProduct;
				if (product.partNumber == partNumber) {
					setSelectedIndex(idx);
					return;
				}
			}
		}
		

		override protected function commitProperties():void {
			super.commitProperties();
			
			if (titleChanged) {
				titleChanged = false;
				titleLabel.text = title;
			}
			
			if (statusTextChanged) {
				statusTextChanged = false;
				statusLabel.text = statusText;
			}
			
			if (iconChanged) {						
				iconChanged = false;
				headerIcon.source = icon;
			}
			
			if (columnsChanged) {
				columnsChanged	= false;
				editColumnButton.columns = columns;
			}

			if (centerTextChanged) {
				centerTextChanged = false;
				centerLabel.text = centerText;
				centerLabel.visible = (centerText != null && centerText!= "");
			}	
		}
		
		
		override protected function partAdded(partName:String, instance:Object):void {
			super.partAdded(partName, instance);
			
			if (instance == titleLabel) {
				titleLabel.text = title;
			}
			
			if (instance == statusLabel) {
				statusLabel.text = statusText;
			}
			
			if (instance == headerIcon) {						
				headerIcon.source = icon;
			}
			
			if (instance == centerLabel) {
				centerLabel.text = centerText;
				centerLabel.visible = (centerText != null && centerText!= "");
			}
			
			if (instance == editColumnButton) {
				editColumnButton.columns = columns;
			}
		}
		
		
		override protected function grid_rollOverHandler(event:GridEvent):void {
			super.grid_rollOverHandler(event);
			
			if (event == null) return;
			if (event.item == null) return;
			if (event.buttonDown) return;
			var product:AbstractSiemensProduct = event.item as AbstractSiemensProduct;
			if (product == null) return;
			if (!shouldShowToolTip) return;
			
			// lazy create the ToolTip
			if (_toolTip == null) {
				_toolTip = new ProductToolTip();
			}
			_toolTip.setProduct(product);
			_toolTip.setTarget(event.itemRenderer as DisplayObject);
			if (!toolTipAdded) {
				PopUpManager.addPopUp(_toolTip, this);
			} 
			else {
				PopUpManager.bringToFront(_toolTip);
			}
			toolTipAdded = true;
		}
		
		
		override protected function grid_rollOutHandler(event:GridEvent):void {
			super.grid_rollOutHandler(event);
			
			if (event == null) return;
			if (!shouldShowToolTip) return;
			if (toolTipAdded) {
				PopUpManager.removePopUp(_toolTip);
			}
			toolTipAdded = false;
		}
		
		
		private var currentProduct:AbstractSiemensProduct = null;
		

		override protected function grid_mouseDownHandler(event:GridEvent):void {
			super.grid_mouseDownHandler(event);
			
			if (event == null) return;
			if (event.item == null) return;
			var product:AbstractSiemensProduct = event.item as AbstractSiemensProduct;
			if (product == null) return;
			if (event.itemRenderer == null) return;
			
			currentProduct = product;
			
			stage.addEventListener(MouseEvent.MOUSE_MOVE, stageMouseMove, false, 0, true);
			stage.addEventListener(MouseEvent.MOUSE_UP, stageMouseUp, false, 0, true);
		}
		
		
		private function stageMouseUp(event:MouseEvent):void {
			currentProduct = null;
			stage.removeEventListener(MouseEvent.MOUSE_MOVE, stageMouseMove);
			stage.removeEventListener(MouseEvent.MOUSE_UP, stageMouseUp);
		}
		
		
		private function stageMouseMove(event:MouseEvent):void {
			if (currentProduct == null) return;
			
			var dragSource:DragSource = new DragSource();
			dragSource.addData([currentProduct], "test_drag");
			dragSource.addData("ProductDataGrid", "dragSourceName");
			var image:Image = new Image();
			image.source = currentProduct.icon;
			
			currentProduct = null;
			stage.removeEventListener(MouseEvent.MOUSE_MOVE, stageMouseMove);
			stage.removeEventListener(MouseEvent.MOUSE_UP, stageMouseUp);
			
			// Let the drag manager take care of the rest - with a visual indicator of what its dragging...
			var p:Point = globalToLocal(new Point(event.stageX, event.stageY));
			DragManager.doDrag(this, dragSource, event, image, p.x-20, p.y-20, 0.8);
		}
	}
}