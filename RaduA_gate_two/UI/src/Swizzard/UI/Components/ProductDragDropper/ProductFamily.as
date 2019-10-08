package Swizzard.UI.Components.ProductDragDropper
{
	import flash.events.MouseEvent;
	
	import mx.core.UIComponent;
	import mx.graphics.BitmapFillMode;
	
	import spark.components.Image;
	import spark.components.Label;
	
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Images.EmbeddedImages;
	
	import flashx.textLayout.formats.TextAlign;
	
	
	public class ProductFamily extends UIComponent
	{
		public var isDragging:Boolean;
		public var pos:int;
		
		private var _isAdded:Boolean;
		private var addedChanged:Boolean = false;
		
		private var _source:String;
		private var sourceChanged:Boolean = false;
		
		private var _label:String;
		private var labelChanged:Boolean = false;
		
		private var _productType:int;
		private var _familyType:int;
		
		
		// the UI components
		public var productImage:Image;
		private var productBG:Image;
		private var productLabel:Label;
		private var pmIcon:Image;

		
		public function ProductFamily()
		{
			super();
			
			isDragging = false;
			_isAdded = false;
			
			width = 70;
			height = 84;
			useHandCursor 	= true;
			buttonMode 		= true;
			addEventListener(MouseEvent.ROLL_OVER, onRollOver);
			addEventListener(MouseEvent.ROLL_OUT, onRollOut);
		}
		
		
		public function set isAdded(value:Boolean):void {
			if (_isAdded == value) return;
			_isAdded = value;
			addedChanged = true;
			invalidateProperties();
		}
		
		public function get isAdded():Boolean {
			return _isAdded;
		}
		
		
		public function set source(value:String):void {
			if (_source == value) return;
			_source = value;
			sourceChanged = true;
			invalidateProperties();
		}
		
		public function get source():String {
			return _source;
		}
		
		
		public function set label(value:String):void {
			if (label == value) return;
			_label = value;
			labelChanged = true;
			invalidateProperties();
		}
		
		public function get label():String {
			return _label;
		}
		
		
		public function set familyType(value:int):void {
			_productType = value;
			if (label == null) {
				label = ProductType.toSubProductString(_productType);
			}
		}
		
		public function get familyType():int {
			return _productType;
		}
		
		
		public function set productType(value:int):void {
			_familyType = value;
		}
		
		public function get productType():int {
			return _familyType;
		}
		
		
		private function onRollOver(e:MouseEvent):void {
			graphics.clear();
			graphics.beginFill(0xDBE8F0, 0.8);
			graphics.drawRoundRect(0, 0, 70, 70, 6, 6);
			graphics.endFill();	
		}
		
		
		private function onRollOut(e:MouseEvent):void {
			graphics.clear();
		}
		
		
		override protected function createChildren():void
		{
			super.createChildren();
			
			if (productImage == null) {
				productImage = new Image();
				productImage.x = 0;
				productImage.y = 0;
				productImage.width = 70;
				productImage.height = 70;
				productImage.fillMode = BitmapFillMode.SCALE;
				productImage.alpha = 0.8;
				productImage.source = source;
				addChild(productImage);
			}
			
			if (productBG == null) {
				productBG = new Image();				
				productBG.x = 0;
				productBG.y = 0;
				productBG.width = 70;
				productBG.height = 70;
				productBG.fillMode = BitmapFillMode.SCALE;
				productBG.source = EmbeddedImages.product_bg_img;
				addChild(productBG);
			}
			
			if (productLabel == null) {
				productLabel = new Label();
				productLabel.x = 0;
				productLabel.y = 72;
				productLabel.width = 70;
				productLabel.height = 24;
				productLabel.alpha = 0.5;
				productLabel.setStyle("textAlign", TextAlign.CENTER);
				productLabel.setStyle("verticalAlign", "top");
				productLabel.setStyle("fontWeight", "normal");
				productLabel.setStyle("color", "#000000");
				addChild(productLabel);	
			}
			
			if (pmIcon == null) {
				pmIcon = new Image();
				pmIcon.x = 65;
				pmIcon.y = 30;
				pmIcon.width = 11;
				pmIcon.height = 10;
				pmIcon.fillMode = BitmapFillMode.SCALE;
				pmIcon.source = EmbeddedImages.plusIcon_img;
				addChild(pmIcon);
			}
		}
		
		
		override protected function commitProperties():void {
			super.commitProperties();
			
			if (sourceChanged) {
				sourceChanged = false;
				productImage.source = source;
			}
			
			if (labelChanged) {
				labelChanged = false;
				productLabel.text = label;
				if (productLabel.measureText(label).width > 70) {
					height = 96;
				}
				else {
					height = 84;
				}
			}
			
			if (addedChanged) {
				addedChanged = false;
				if (isAdded) {
					pmIcon.source = EmbeddedImages.minusIcon_img;
				}
				else {
					pmIcon.source = EmbeddedImages.plusIcon_img;
				}
			}	
		}
	}
}