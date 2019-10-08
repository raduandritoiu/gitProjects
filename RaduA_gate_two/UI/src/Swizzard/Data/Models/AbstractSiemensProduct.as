package Swizzard.Data.Models
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.utils.Proxy;
	import flash.utils.describeType;
	import flash.utils.flash_proxy;
	
	import mx.core.IUID;
	import mx.utils.UIDUtil;
	
	import Swizzard.Data.Models.Enumeration.ExtendedProductType;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Data.Models.Enumeration.SiemensDocumentType;
	import Swizzard.Images.EmbeddedImages;
	import Swizzard.System.Utils.NumericUtils;
	import Swizzard.System.Utils.VersionUtil;
	
	use namespace flash_proxy;
	
	
	[Bindable]
	public class AbstractSiemensProduct extends Proxy implements IUID, IExternalizable, IEventDispatcher
	{
		public static var productInfo:XML;
		
		public var id:Number					= 0;
		private var _id:Number 					= 0;
		private var _partNumber:String			= "";
		private var _actualPartNumber:String	= "";
		private var _productType:uint			= 0;
		private var _extendedProductType:uint	= 0;
		private var _sapPartNumber:String		= "";
		private var _description:String			= "";
		private var _manufacturer:String		= "";
		private var _model:String				= "";
		private var _vendor:String				= "";
		private var _isObsolete:Boolean			= false;
		private var _lastModified:Date			= new Date();
		private var _price:Number				= 0;
		private var _subProductPartNumber:String	= "";	// Sub Product Part Number 
		private var _subProductPrice:Number			= 0;	// Sub Product Price 
		private var _dataSheetLink:String;
		
		private var _documents:Array;
		
		private var _isVirtualPart:Boolean;
		private var _assemblyOnly:Boolean;
		
		private var _uid:String;
		
		private var dispatcher:EventDispatcher;
		
		
		public function AbstractSiemensProduct()
		{
			_subProductPartNumber = "";
			dispatcher = new EventDispatcher(this);
			
			if (!productInfo)
				productInfo	= describeType(this);
			
			_uid = UIDUtil.createUID();
		}
		
		
		protected function writeFixes():void
		{
			if (_sapPartNumber == null)
				_sapPartNumber = "";
			if (_description == null)
				_description = "";
			if (_manufacturer == null)
				_manufacturer = "";
			if (_vendor == null)
				_vendor = "";	
		}
		
		
		public function writeExternal(output:IDataOutput):void 
		{
          	output.writeDouble(id);
          	output.writeUTF(_partNumber);
			output.writeUTF(_actualPartNumber);
          	output.writeInt(_productType);
          	output.writeUTF(_sapPartNumber);
          	output.writeUTF(_description);
          	output.writeUTF(_manufacturer);
          	output.writeUTF(_model);
          	output.writeUTF(_vendor);
          	output.writeBoolean(_isObsolete);
          	output.writeObject(_lastModified);
          	output.writeDouble(_price);
			output.writeUTF(_subProductPartNumber);
			output.writeDouble(_subProductPrice);
			
			output.writeBoolean(_isVirtualPart);
			output.writeBoolean(_assemblyOnly);
			output.writeInt(_extendedProductType);
      	}
  		
		
      	public function readExternal(input:IDataInput):void 
      	{
  			id				= input.readDouble();
          	_partNumber		= input.readUTF();
			_actualPartNumber	= input.readUTF();
          	_productType	= input.readInt();
          	_sapPartNumber	= input.readUTF();
          	_description	= input.readUTF();
          	_manufacturer	= input.readUTF();
          	_model			= input.readUTF();
          	_vendor			= input.readUTF();
          	_isObsolete		= input.readBoolean();
          	_lastModified	= input.readObject() as Date;
          	_price			= input.readDouble();
			_subProductPartNumber	= input.readUTF();
			_subProductPrice		= input.readDouble();
			
			_isVirtualPart	= input.readBoolean();
			_assemblyOnly	= input.readBoolean();
			
			if (VersionUtil.Compare(VersionUtil.LOADING_VERSION, "2") == 1) {
				// use from Valve dimensions, because only valves were in version 1.
				_extendedProductType = ExtendedProductType.fromValveDimensionType(_productType);
			}
			else {
				_extendedProductType = input.readInt();
			}
      	}

		
		public function set productType(value:uint):void {
			_productType	= value;
		}
		
		public function get productType():uint {
			return _productType;
		}
		
		
		public function set extendedProductType(value:uint):void {
			_extendedProductType = value;
		}
		
		public function get extendedProductType():uint {
			return _extendedProductType;
		}
		
		
		public function get icon():Class
		{
			switch (productType)
			{
				case ProductType.VALVE:
					if (NumericUtils.isDigit(partNumber.charAt(0))) {
						return EmbeddedImages.valve16_img;	
					}
					else {
						return EmbeddedImages.valve_assembly16_img;
					}
				
				case ProductType.ACTUATOR:
					return EmbeddedImages.actuator16_img;
				
				case ProductType.ASSEMBLY:
					return EmbeddedImages.valve_assembly16_img;
				
				case ProductType.MISC:
					return EmbeddedImages.misc16_img;
					
				case ProductType.DAMPER:
					return EmbeddedImages.damper16_img;
					
				case ProductType.ACCESSORY:
					return EmbeddedImages.accessory16_img;
					
				case ProductType.PNEUMATIC:
					return EmbeddedImages.pneumatic16_img;
			}
			
			return EmbeddedImages.valve16_img;
		}
		
		
		public function set uid(value:String):void {
			_uid	= value;
		}
		
		public function get uid():String {
			return _uid;
		}
		
		
		public function set partNumber(value:String):void {
			_partNumber = value;
		}
		
		public function get partNumber():String {
			return _partNumber;
		}
		
		
		public function set actualPartNumber(value:String):void {
			_actualPartNumber	= value;
		}
		
		public function get actualPartNumber():String {
			return _actualPartNumber;
		}
		
		
		public function get sapPartNumber():String 
		{
			return _sapPartNumber;
		}

		public function set sapPartNumber(value:String):void 
		{
			_sapPartNumber = value;
		}

		public function get description():String 
		{
			return _description;
		}

		public function set description(value:String):void 
		{
			_description = value;
		}

		public function get manufacturer():String 
		{
			return _manufacturer;
		}

		public function set manufacturer(value:String):void 
		{
			_manufacturer = value;
		}

		public function get model():String 
		{
			return _model;
		}

		public function set model(value:String):void 
		{
			if (!value)
				value	= "";
				
			_model = value;
		}

		public function get vendor():String 
		{
			return _vendor;
		}

		public function set vendor(value:String):void 
		{
			_vendor = value;
		}

		public function get isObsolete():Boolean 
		{
			return _isObsolete;
		}

		public function set isObsolete(value:Boolean):void 
		{
			_isObsolete = value;
		}

		public function get lastModified():Date 
		{
			return _lastModified;
		}

		public function set lastModified(value:Date):void 
		{
			_lastModified = value;
		}

		public function get price():Number 
		{
			return _price;
		}

		public function set price(value:Number):void 
		{
			_price = value;
		}
		
		public function get totalPrice():Number
		{
			return price + subProductPrice;
		}
		
		public function set subProductPartNumber(value:String):void 
		{
			_subProductPartNumber = value;
		}

		public function get subProductPartNumber():String 
		{
			return _subProductPartNumber;
		}

		public function set subProductPrice(value:Number):void 
		{
			_subProductPrice = value;
		}

		public function get subProductPrice():Number 
		{
			if (isNaN(_subProductPrice))
				return 0;
				
			return _subProductPrice;
		}
		
		public function get documents():Array
		{
			return _documents;
		}
		
		[Bindable("documentsChanged")]
		public function set documents(value:Array):void
		{
			_documents	= value;
			
			if (value)
			{
				 for each (var doc:SiemensDocument in value)
				 {
				 	if (doc.documentType == SiemensDocumentType.DATASHEET)
				 	{
				 		_dataSheetLink	= "<a href=\"" + doc.location + "\">" + doc.location.substr(doc.location.lastIndexOf("/")) + "</a>";  
				 				
				 	}
				 }
			}
		}
		
		public function set isVirtualPart(value:Boolean):void
		{
			_isVirtualPart	= value;
		}
		public function get isVirtualPart():Boolean
		{
			return _isVirtualPart;
		}
		
		public function set assemblyOnly(value:Boolean):void
		{
			_assemblyOnly	= value;
		}
		public function get assemblyOnly():Boolean
		{
			return _assemblyOnly;
		}
		
		public function get dataSheetLink():String
		{
			return _dataSheetLink;
		}
		
		
		
		override flash_proxy function getProperty(name:*):*
		{
			return null;
		}
		
		override flash_proxy function setProperty(name:*, value:*):void
		{}
		
		override flash_proxy function hasProperty(name:*):Boolean
		{
			return false;
		}
		
		
		
		public function addEventListener(type:String, listener:Function, useCapture:Boolean=false, priority:int=0, useWeakReference:Boolean=false):void
		{
			dispatcher.addEventListener(type, listener, useCapture, priority, useWeakReference);
		}
		
		public function removeEventListener(type:String, listener:Function, useCapture:Boolean=false):void
		{
			dispatcher.removeEventListener(type, listener, useCapture);
		}
		
		public function dispatchEvent(event:Event):Boolean
		{
			return dispatcher.dispatchEvent(event);
		}
		
		public function hasEventListener(type:String):Boolean
		{
			return dispatcher.hasEventListener(type);
		}
		
		public function willTrigger(type:String):Boolean
		{
			return dispatcher.willTrigger(type);
		}
	}
}