package Swizzard.System.Models
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.flash_proxy;
	
	import mx.collections.ArrayCollection;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensAccessory;
	import Swizzard.Data.Models.SiemensPneumatic;
	import Swizzard.Data.Utils.SynchronousDataUtility;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.UI.Components.DataGridClasses.GridColumnsUtil;
	
	use namespace flash_proxy;
	
	
	[Bindable]
	[RemoteClass]
	public class PneumaticScheduleItem extends BaseScheduleItem
	{
		public static const FILE_EXTENSION_NAME:String = "spf";
		

		public function PneumaticScheduleItem() {
			super();
		}
		
		
		override public function get fileExtension():String {
			return FILE_EXTENSION_NAME;
		}
		
		
		override public function set product(value:AbstractSiemensProduct):void {
			if ((value is SiemensPneumatic) || (value is SiemensAccessory)) {
				// Got Item
				_product = value;
			}
			else {
				// Got Abstact Item. Retrieve full object
				var util:SynchronousDataUtility	= new SynchronousDataUtility();
				var productType:uint = value.productType; 
				_product = util.getFullItem(value, false, productType);
				util.dispose();
			}
		}
		
		override public function get product():AbstractSiemensProduct {
			return _product;
		}
		
		
		override public function set quantity(val:int):void {
			_quantity = val;
		}
		
		override public function get quantity():int {
			if (_parent != null) 
				return _parent.quantity;
			return _quantity;
		}
		
		
		override public function get formattedPrice():String {
			return GridColumnsUtil.currencyFormatter.format(price);
		}
		
		
		override public function clone(withParent:Boolean = true):IScheduleItem {
			var c:PneumaticScheduleItem = new PneumaticScheduleItem();
			c.product			= product;
			c.notes				= notes;
			c.quantity			= quantity;
			c.priceMultiplier	= priceMultiplier;
			
			if (withParent) {
				c._parent 		= _parent ;
			}
			
			return c;
		}
		
		
		public function addSubItem(item:PneumaticScheduleItem):void {
			item._parent = this;
			_subitems.addItem(item);
		}
		
		
		// Flash Proxy IMPLEMENTATION
		
		override flash_proxy function getProperty(name:*):* {
			if (name.localName == "null")
				return null;
			
			if (product == null) 
				return null;
			
			var accessor:Array = name.localName.split(":");
			if (accessor.length < 2) 
				return null;
			
			var discriminant:String = accessor[0];
			var propertyName:String = accessor[1];
			if (discriminant == "product") {
				return product[propertyName];
			}
			else if (discriminant == "pneumatic" && product is SiemensPneumatic) {
				return product[propertyName];
			}
			else if (discriminant == "accessory" && product is SiemensAccessory) {
				return product[propertyName];
			}
			
			return null;
		}
		
		
		// IExternalizable IMPLEMENTATION
		
		/**
		 * Function to serialize class to binary.
		 */		
		override public function writeExternal(output:IDataOutput):void {
			output.writeObject(_product);
			output.writeInt(_quantity);
			output.writeUTF(_notes);
			output.writeDouble(_priceMultiplier);
			output.writeObject(_subitems);
		}
		
		
		/**
		 * Function to deserialize class from binary.
		 */		
		override public function readExternal(input:IDataInput):void {
			_product			= input.readObject() as AbstractSiemensProduct;
			_quantity			= input.readInt();
			_notes				= input.readUTF();
			_priceMultiplier	= input.readDouble();
			_subitems			= input.readObject() as ArrayCollection;
			
			for each (var item:PneumaticScheduleItem in subitems) {
				item._parent = this;
			}
		}
	}
}