package Swizzard.System.Models
{
	import flash.events.Event;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.flash_proxy;
	
	import mx.collections.ArrayCollection;
	import mx.formatters.CurrencyFormatter;
	import mx.formatters.NumberBaseRoundType;
	import mx.formatters.NumberFormatter;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensActuator;
	import Swizzard.Data.Models.SiemensAssembly;
	import Swizzard.Data.Models.SiemensDocument;
	import Swizzard.Data.Models.SiemensMiscProduct;
	import Swizzard.Data.Models.SiemensValve;
	import Swizzard.Data.Models.Enumeration.ExtendedProductType;
	import Swizzard.Data.Models.Enumeration.SiemensDocumentType;
	import Swizzard.Data.Models.Enumeration.Valves.ValveMedium;
	import Swizzard.Data.Models.Enumeration.Valves.ValvePattern;
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;
	import Swizzard.Data.Utils.SynchronousDataUtility;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.System.Utils.VersionUtil;
	
	import utils.LogUtils;
	
	use namespace flash_proxy;
	
	
	[Bindable]
	[RemoteClass]
	public dynamic class ValveScheduleItem extends BaseScheduleItem
	{
		public static const FILE_EXTENSION_NAME:String = "svf";
		
		protected var _tags:String				= "";	// Tags
		protected var _calculatedCv:Number		= 0;	// Calculated Cv
		protected var _calculatedCv_B:Number	= 0;	// Calculated Cv B
		protected var _gpm:Number;						// required Flow - WTF, WHO NAMES THIS VARIABLE THIS WAY
		protected var _actualPressureDrop:Number;		// Actual Pressure Drop
		protected var _actualPressureDrop_B:Number;	// Actual Pressure Drop B
		protected var _formParams:SelectedFormParameters;
		
		private var cf:CurrencyFormatter;
		private var cvFormatter:NumberFormatter;
		private var pressureDropFormatter:NumberFormatter;

		
		public function ValveScheduleItem() {
			super();
		}
		
		
		override public function get fileExtension():String {
			return FILE_EXTENSION_NAME;
		}
		
		
		public function get dataSheetLink():String {
			var link:String;
			for each(var d:SiemensDocument in product.documents) {
				if (d.documentType == SiemensDocumentType.DATASHEET) {
					link = d.location;
					break;
				}
			}
			return link != null ? link : "N/A";
		}
		
		
		override public function set product(value:AbstractSiemensProduct):void {
			if ((value is SiemensValve) || (value is SiemensActuator) || (value is SiemensAssembly)) {
				// Got Item
			} 
			else {
				// Got Abstact Item. Retrieve full object
				var util:SynchronousDataUtility	= new SynchronousDataUtility();
				var productType:uint = ExtendedProductType.toValveDimensionType(value.extendedProductType);
				value = util.getFullItem(value, false, productType);
				util.dispose();
			}
			
			_product = value;
		}

		override public function get product():AbstractSiemensProduct {
			return _product;
		}
		
		
		public function set tags(value:String):void {
			if (quantity > 1) {
				for each (var subItem:ValveScheduleItem in this.subitems) {
					if (subItem.tags == _tags) {
						subItem.tags = value;
					}
				}				
			}
			_tags = value;
		}
		
		public function get tags():String {
			return _tags;
		}
		
		
		public function set formParams(value:SelectedFormParameters):void {
			_formParams = value;
			if (value) {
				calculateActualPressureDrop();
				calculateActualPressureDrop_B();
			}
		}
		
		public function get formParams():SelectedFormParameters {
			return _formParams;
		}
		
		
		override public function set priceMultiplier(value:Number):void {
			if (isNaN(value))
				value = 1;
			_priceMultiplier	= value;
			dispatchEvent(new Event("priceChanged"));
		}
		
		override public function get priceMultiplier():Number {
			if (parent)
				return parent.priceMultiplier;
			return _priceMultiplier;
		}
		
		
		[Bindable("priceChanged")]
		override public function get price():Number {
			if (!product)
				return 0;
			
			if (quantity > 1) {
				var price:Number = 0;
				for each (var subItem:ValveScheduleItem in subitems)
				price += subItem.price;
				return price;	
			}
			return quantity * (product.price * priceMultiplier);
		}
		
		
		override public function get formattedPrice():String {
			if (!cf) {
				cf							= new CurrencyFormatter();
				cf.currencySymbol			= "$";
				cf.thousandsSeparatorTo		= ",";
				cf.useThousandsSeparator	= true;
				cf.precision				= 2;
			}
			return cf.format(price);
		}
		
		
		override public function set quantity(value:int):void {
			setQuantity(value, null, 0, 0, 0, true);
		}
		
		override public function get quantity():int {
			if (_parent)
				return 1;
			return _quantity;
		}
		

		/** Change Quantity of this product */
		public function setQuantity(value:uint, newParams:SelectedFormParameters, 
									newCalculatedCv:Number, newCalculatedCv_B:Number, newGpm:Number, 
									cloneFirstParameters:Boolean):void {
			if (parent)	
				return;
			
			_quantity = value;
			
			// if we're changing the quantity to 1, copy last subitem value to here							
			if (value == 1) {
				if (subitems.length > 0) {
					var lastSubItem:ValveScheduleItem	= subitems.getItemAt(0) as ValveScheduleItem;
					if (lastSubItem) {
						this.tags			= lastSubItem.tags;
						this.notes			= lastSubItem.notes;
						this.calculatedCv	= lastSubItem.calculatedCv;
						this.calculatedCv_B	= lastSubItem.calculatedCv_B;
						this.gpm			= lastSubItem.gpm;
						this.formParams		= lastSubItem.formParams;
					}
					
					// Remove all Subitems
					subitems.removeAll();
				}					
			}
			else {
				// Truncate subitems to quantiy value
				while (subitems.length > value) {
					subitems.removeItemAt(subitems.length - 1); // LIFO
				}
				// If we're adding items
				if (value > subitems.length) {
					while (value > subitems.length) {
						var newSubItem:ValveScheduleItem = this.internalClone();
						newSubItem._parent = this;
						
						if (subitems.length == 0) {
							// First item takes this items properties
							newSubItem.calculatedCv		= this.calculatedCv;
							newSubItem.calculatedCv_B 	= this.calculatedCv_B;
							newSubItem.formParams		= this.formParams;
							newSubItem.gpm				= this.gpm;
							
						}
						else if (cloneFirstParameters) {
							newSubItem.calculatedCv		= (newCalculatedCv > 0) ? newCalculatedCv : this.calculatedCv;
							newSubItem.calculatedCv_B 	= isNaN(newCalculatedCv_B) ? this.calculatedCv_B : newCalculatedCv_B;
							newSubItem.formParams		= (newParams != null) ? newParams : this.formParams;
							newSubItem.gpm				= (newGpm > 0) ? newGpm : this.gpm;
						}
						else if (!cloneFirstParameters) {
							newSubItem.calculatedCv		= 0;
							newSubItem.calculatedCv_B	= 0;
							newSubItem.formParams		= null;
						}
						subitems.addItem(newSubItem);
					}
				}
			}
		}
		
		
		public function set gpm(value:Number):void {
			_gpm = value;
			calculateActualPressureDrop();
			calculateActualPressureDrop_B();
		}
		
		public function get gpm():Number {
			if (isNaN(_gpm))
				return 0;
			return _gpm;
		}
		
		
		public function set formattedGpm(value:String):void {
			gpm = parseFloat(value);	
		}
		
		[Transient]
		public function get formattedGpm():String {
			if (product is SiemensMiscProduct)
				return "N/A";
			// this change was requested by siemen's 03-08-2011 listed in bug #1898 (https://bugs.j2inn.com/view.php?id=1898)
			if (this.product is SiemensActuator)
				return "N/A";
			if (this.product is SiemensValve && SiemensValve(this.product).info.type == ValveType.PICV)
				return "N/A";
			else if (this.product is SiemensAssembly && SiemensAssembly(this.product).valve.info.type == ValveType.PICV)
				return "N/A";
			
			// If this is the parent item show multiple
			if (subitems.length > 0)
				return "Multiple";
			
			if (formParams && formParams.selectedMedium == ValveMedium.STEAM)
				return "N/A";
			
			if (!cvFormatter) {
				cvFormatter				= new NumberFormatter();
				cvFormatter.precision	= 2;
				cvFormatter.rounding	= NumberBaseRoundType.NEAREST;
			}
			
			return cvFormatter.format(gpm);
		}
		
		
		
		// =============================================================================
		// ================ Waht I am interested in ====================================
		// =============================================================================
		
		public function set calculatedCv(value:Number):void {
			if (isNaN(value))
				value = 0;
			_calculatedCv = value;
		}
		
		public function get calculatedCv():Number {
			return _calculatedCv;
		}
		
		public function get formattedCalculatedCv():String {
			if (product is SiemensMiscProduct)
				return "N/A";
			
			// this change was requested by siemen's 03-08-2011 listed in bug #1898 (https://bugs.j2inn.com/view.php?id=1898)
			if (product is SiemensActuator)
				return "N/A";
			
			if (product is SiemensValve && SiemensValve(product).info.type == ValveType.PICV)
				return "N/A";
			else if (product is SiemensAssembly && SiemensAssembly(product).valve.info.type == ValveType.PICV)
				return "N/A";
			
			// If this is the parent item show multiple
			if (subitems.length > 0)
				return "Multiple";
					
			if (!cvFormatter) {
				cvFormatter				= new NumberFormatter();
				cvFormatter.precision	= 2;
				cvFormatter.rounding	= NumberBaseRoundType.NEAREST;
			}
			
			return cvFormatter.format(calculatedCv);
		}
		
		
		public function get formattedCv_B():String {
			if (_product is SiemensValve && (_product as SiemensValve).info.type == ValveType.BALL 
				&& (_product as SiemensValve).info.pattern == ValvePattern.SIX_WAY)
				return (_product as SiemensValve).info.cvb + "";
			else if (_product is SiemensAssembly && (_product as SiemensAssembly).valve.info.type == ValveType.BALL 
				&& (_product as SiemensAssembly).valve.info.pattern == ValvePattern.SIX_WAY)
				return (_product as SiemensAssembly).valve.info.cvb + "";
			return "N/A";
		}
		
		
		public function set calculatedCv_B(value:Number):void {
			if (isNaN(value))
				value = 0;
			_calculatedCv_B = value;
		}
		
		public function get calculatedCv_B():Number {
			return _calculatedCv_B;
		}
		
		public function get formattedCalculatedCv_B():String {
			if ((_product is SiemensValve && (_product as SiemensValve).info.type == ValveType.BALL && (_product as SiemensValve).info.pattern == ValvePattern.SIX_WAY) || 
				(_product is SiemensAssembly && (_product as SiemensAssembly).valve.info.type == ValveType.BALL && (_product as SiemensAssembly).valve.info.pattern == ValvePattern.SIX_WAY)) 
			{
				// If this is the parent item show multiple
				if (subitems.length > 0)
					return "Multiple";
				
				if (!cvFormatter) {
					cvFormatter				= new NumberFormatter();
					cvFormatter.precision	= 2;
					cvFormatter.rounding	= NumberBaseRoundType.NEAREST;
				}
				return cvFormatter.format(calculatedCv_B);
			}
			
			return "N/A";
		}
		
		
		public function get requiredPressureDrop():Number {
			if ((!formParams) || isNaN(formParams.pressureDrop))
				return 0;
			return formParams.pressureDrop;
		}
		
		public function get formattedRequiredPressureDrop():String {
			if (product is SiemensMiscProduct)
				return "N/A";
			
			// this change was requested by siemen's 03-08-2011 listed in bug #1898 (https://bugs.j2inn.com/view.php?id=1898)
			if (this.product is SiemensActuator)
				return "N/A";
			
			if (this.product is SiemensValve && SiemensValve(this.product).info.type == ValveType.PICV)
				return "N/A";
			else if (this.product is SiemensAssembly && SiemensAssembly(this.product).valve.info.type == ValveType.PICV)
				return "N/A";
			
			// If this is the parent item show multiple
			if (subitems.length > 0)
				return "Multiple";
			
			if (!cvFormatter) {
				cvFormatter				= new NumberFormatter();
				cvFormatter.precision	= 2;
				cvFormatter.rounding	= NumberBaseRoundType.NEAREST;
			}
			
			return cvFormatter.format(requiredPressureDrop);
		}
		
		
		public function get formattedRequiredPressureDrop_B():String {
			if ((_product is SiemensValve && (_product as SiemensValve).info.type == ValveType.BALL && (_product as SiemensValve).info.pattern == ValvePattern.SIX_WAY) || 
				(_product is SiemensAssembly && (_product as SiemensAssembly).valve.info.type == ValveType.BALL && (_product as SiemensAssembly).valve.info.pattern == ValvePattern.SIX_WAY)) 
			{
				// If this is the parent item show multiple
				if (subitems.length > 0)
					return "Multiple";
				
				if (!cvFormatter) {
					cvFormatter				= new NumberFormatter();
					cvFormatter.precision	= 2;
					cvFormatter.rounding	= NumberBaseRoundType.NEAREST;
				}
				var _tmpReqPDrop:Number = 0;
				if (formParams != null && !isNaN(formParams.pressureDrop_B))
					_tmpReqPDrop = formParams.pressureDrop_B;
				return cvFormatter.format(_tmpReqPDrop);
			}
			return "N/A";
		}
		
		
		private function calculateActualPressureDrop():void {
			if (!formParams) {
				_actualPressureDrop	= 0;
				return;
			}
			// Calculate actual Pressure Drop
			if ((this.product is SiemensValve) || (this.product is SiemensAssembly)) {
				var valve:SiemensValve	= ((this.product is SiemensAssembly) ? SiemensAssembly(this.product).valve : this.product) as SiemensValve;	
				var cv:Number	= valve.info.cv;
				var specificGravity:Number	= 1;
				var pressureDrop:Number 	= 0;
				
				switch (formParams.selectedMedium)
				{
					case ValveMedium.GLYCOL:
						switch (formParams.percentGlycol)
						{
							case 25:
								specificGravity	= 1.048;
								break;
							case 30:
								specificGravity	= 1.057;
								break;
							case 40:
								specificGravity	= 1.07;
								break;
							case 50:
								specificGravity	= 1.088;
								break;
							case 60:
								specificGravity	= 1.1;
								break;
							case 65:
								specificGravity	= 1.11;
								break;
							case 100:
								specificGravity	= 1.145;
								break;
						}
						
					case ValveMedium.WATER:
						pressureDrop	= Math.pow(gpm/cv, 2);
						break;
					
					case ValveMedium.STEAM:
						pressureDrop	= (Math.pow(formParams.steamQuantity, 2) * formParams.steamSpecificVolume) / (Math.pow(63.5, 2) * Math.pow(cv, 2));
						break;
				}
														
				if (!isNaN(pressureDrop))
					_actualPressureDrop	= pressureDrop;
				
				dispatchEvent(new Event("calculationComplete"));
			}
		}
		
		[Bindable("calculationComplete")]
		public function get actualPressureDrop():Number {
			if (isNaN(_actualPressureDrop))
				return 0;
			return _actualPressureDrop;
		}
		
		public function get formattedActualPressureDrop():String {
			if (product is SiemensMiscProduct)
				return "N/A";
			// this change was requested by siemen's 03-08-2011 listed in bug #1898 (https://bugs.j2inn.com/view.php?id=1898)
			if (this.product is SiemensActuator)
				return "N/A";
			if (this.product is SiemensValve && SiemensValve(this.product).info.type == ValveType.PICV)
				return "N/A";
			else if (this.product is SiemensAssembly && SiemensAssembly(this.product).valve.info.type == ValveType.PICV)
				return "N/A";
			
			// If this is the parent item show multiple
			if (subitems.length > 0)
				return "Multiple";
			
			if (!pressureDropFormatter) {
				pressureDropFormatter			= new NumberFormatter();
				pressureDropFormatter.precision	= 2;
				pressureDropFormatter.rounding	= NumberBaseRoundType.NEAREST;
			}
			
			return pressureDropFormatter.format(actualPressureDrop);
		}
		
		
		private function calculateActualPressureDrop_B():void {
			if (!formParams) {
				_actualPressureDrop_B = 0;
				return;
			}
			// Calculate actual Pressure Drop
			if ((_product is SiemensValve && (_product as SiemensValve).info.type == ValveType.BALL && (_product as SiemensValve).info.pattern == ValvePattern.SIX_WAY) || 
				(_product is SiemensAssembly && (_product as SiemensAssembly).valve.info.type == ValveType.BALL && (_product as SiemensAssembly).valve.info.pattern == ValvePattern.SIX_WAY)) 
			{
				var valve:SiemensValve	= ((this.product is SiemensAssembly) ? SiemensAssembly(this.product).valve : this.product) as SiemensValve;	
				var cvb:Number = valve.info.cvb;
				var pressureDrop:Number = 0;
				
				switch (formParams.selectedMedium)
				{
					// WEIRD_TODO: these formulas do not seelm OK: for GLYCOL it was just setting up "specificGravity", 
					// which was not ised so I removed it; and it seems it is using "gpm" which is only "Require Flow"
					// and shouldn;t I use the one from B
					case ValveMedium.GLYCOL:
					case ValveMedium.WATER:
						pressureDrop	= Math.pow(gpm/cvb, 2);
						break;
					
					case ValveMedium.STEAM:
						pressureDrop	= 0;
						break;
				}
				
				if (!isNaN(pressureDrop))
					_actualPressureDrop_B = pressureDrop;
				else
					_actualPressureDrop_B = 0;
			}
			else {
				_actualPressureDrop_B	= 0;
			}
		}
		
		public function get formattedActualPressureDrop_B():String {
			if ((_product is SiemensValve && (_product as SiemensValve).info.type == ValveType.BALL && (_product as SiemensValve).info.pattern == ValvePattern.SIX_WAY) || 
				(_product is SiemensAssembly && (_product as SiemensAssembly).valve.info.type == ValveType.BALL && (_product as SiemensAssembly).valve.info.pattern == ValvePattern.SIX_WAY)) 
			{
				// If this is the parent item show multiple
				if (subitems.length > 0)
					return "Multiple";
				
				if (!pressureDropFormatter) {
					pressureDropFormatter			= new NumberFormatter();
					pressureDropFormatter.precision	= 2;
					pressureDropFormatter.rounding	= NumberBaseRoundType.NEAREST;
				}
				return pressureDropFormatter.format(_actualPressureDrop_B);
			}
			
			return "N/A";
		}
		
		
		// =============================================================================
		// ==================== Flash Proxy IMPLEMENTATION =============================
		// =============================================================================
		
		override flash_proxy function getProperty(name:*):* {
			if (name.localName == "null")
				return null;
			
			if ((quantity > 1) && (name.localName != "actualPartNumber"))
				return "Multiple";
				
			if (product) {
				if (product is SiemensAssembly) {
					return product[name];
				}
				else {
					//var propertyName:String	= name.localName;
					//propertyName		 	= propertyName.substr(propertyName.indexOf(":") + 1);
					
					var accessor:Array	= name.localName.split(":");
					var discriminant:String;
					var propertyName:String;
					
					if (accessor.length > 1) {
						discriminant	= accessor[0];
						propertyName	= accessor[1];
					}
					else {
						propertyName	= accessor[0];
					}
					
					if (product is SiemensMiscProduct) {
						if (discriminant) {
							return "N/A";
						}
					}
					if (product is SiemensValve) {
						if (!discriminant || (discriminant == "valve")) {
							return product[propertyName];
						}
					}
					else if (product is SiemensActuator) {
						// Attempting to fix display issue of close off on actuators with minimal
						// impact to the application.
						if (propertyName == "closeOff") {
							return "N/A";
						}
						if (!discriminant || (discriminant == "actuator")) {
							return product[propertyName];
						}
					}
					else if (product is AbstractSiemensProduct) {
						return product[propertyName];
					}
				}
			}
			
			return "N/A";
		}
		
		
		// =============================================================================
		// ================ Cloning related Stuff ======================================
		// =============================================================================
		
		private function internalClone():ValveScheduleItem {
			var c:ValveScheduleItem	= new ValveScheduleItem();
			c.product			= this.product;
			c.tags				= this._tags;
			c.calculatedCv		= this.calculatedCv;
			c.calculatedCv_B	= this.calculatedCv_B;
			c.formParams		= this.formParams;
			c.priceMultiplier	= this.priceMultiplier;
						
			return c;
		}
		
		
		override public function clone(withParent:Boolean = true):IScheduleItem {
			var c:ValveScheduleItem	= new ValveScheduleItem();
			c.product			= this.product;
			c.notes				= this.notes;
			c.tags				= this.tags;
			c._quantity			= this.quantity;
			c.calculatedCv		= this.calculatedCv;
			c.calculatedCv_B	= this.calculatedCv_B;
			c.formParams		= this.formParams;
			c.gpm				= this.gpm;
			c.priceMultiplier	= this.priceMultiplier;
			
			if (withParent) {
				c._parent		= this.parent;
			}
			
			if (c.quantity > 1) {
				for (var i:uint = 0; i < this.subitems.length; i++) {
					var subItem:ValveScheduleItem	= this.subitems[i] as ValveScheduleItem;
					var clonedItem:IScheduleItem	= subItem.clone();
					c.subitems.addItem(clonedItem);
					
					/*var clonedSub:ScheduleItem	= c.subitems[i] as ScheduleItem;
					clonedSub.notes				= subItem.notes;
					clonedSub.priceMultiplier	= subItem.priceMultiplier;
					clonedSub.tags				= subItem.tags;
					clonedSub.formParams		= subItem.formParams;*/
				}
			}
			
			return c;
		}
		
		
		// =============================================================================
		// ================ IExternalizable IMPLEMENTATION =============================
		// =============================================================================
		
		/**
		 * Function to serialize class to binary.
		 */		
		override public function writeExternal(output:IDataOutput):void {
			LogUtils.Schedule(" ====== ======= write external");
			
			output.writeObject(_product);
			output.writeUTF(_tags);
			output.writeInt(_quantity);
			output.writeUTF(_notes);
			
			output.writeDouble(_calculatedCv);
			output.writeDouble(_gpm);
			output.writeDouble(_actualPressureDrop);
			output.writeObject(_formParams);
			
			output.writeObject(_subitems);
			output.writeDouble(_priceMultiplier);
			
			output.writeDouble(_calculatedCv_B);
			output.writeDouble(_actualPressureDrop_B);
		}
		
		
		/**
		 * Function to deserialize class from binary.
		 */		
		override public function readExternal(input:IDataInput):void {
			LogUtils.Schedule(" ====== ======= read external");
			
			_product			= input.readObject() as AbstractSiemensProduct;
			_tags				= input.readUTF();
			_quantity			= input.readInt();
			_notes				= input.readUTF();
			
			_calculatedCv		= input.readDouble();
			_gpm				= input.readDouble();
			_actualPressureDrop	= input.readDouble();
			_formParams			= input.readObject() as SelectedFormParameters;
			
			_subitems			= input.readObject() as ArrayCollection;
			_priceMultiplier	= input.readDouble();

			// do backwards compatibility
			var ldv:String = VersionUtil.LOADING_VERSION;
			if (VersionUtil.Compare(VersionUtil.LOADING_VERSION, "2.1.8") == 1) {
				_calculatedCv_B 		= 0;
				_actualPressureDrop_B 	= 0;
			}
			else {
				_calculatedCv_B 		= input.readDouble();
				_actualPressureDrop_B 	= input.readDouble();
			}

			
			LogUtils.Schedule(" ====== ======= read external");
			LogUtils.Schedule(" ====== part : " + _product.actualPartNumber);
			LogUtils.Schedule(" ====== aa: " + _formParams.selectedMedium);
			LogUtils.Schedule(" ====== aa: " + _formParams.pressureDrop);
			LogUtils.Schedule(" ====== aa: " + _formParams.pressureDrop_B);
			LogUtils.Schedule(" ====== aa: " + _formParams.requiredFlow);
			LogUtils.Schedule(" ====== aa: " + _formParams.steamQuantity);
			LogUtils.Schedule(" ====== aa: " + _formParams.steamSpecificVolume);
			LogUtils.Schedule(" ====== aa: " + _formParams.percentGlycol);
			
			
			for each (var item:ValveScheduleItem in subitems) {
				item._parent 	= this;
			}
		}
	}
}