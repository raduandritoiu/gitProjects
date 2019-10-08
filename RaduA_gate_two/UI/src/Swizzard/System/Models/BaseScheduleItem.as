package Swizzard.System.Models
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.Proxy;
	import flash.utils.flash_proxy;
	
	import mx.collections.ArrayCollection;
	import mx.utils.UIDUtil;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	
	
	public class BaseScheduleItem extends Proxy implements IScheduleItem
	{
		protected var _uid:String;
		protected var _parent:IScheduleItem;
		protected var _subitems:ArrayCollection;		// SubItems
		
		protected var _product:AbstractSiemensProduct;	// Product 
		protected var _priceMultiplier:Number	= 1;	// Price Multiplier
		protected var _quantity:int				= 1;	// Quantity
		protected var _notes:String				= "";
		
		protected var dispatcher:EventDispatcher;
		

		public function BaseScheduleItem() {
			_uid		= UIDUtil.createUID();
			_subitems	= new ArrayCollection();
			dispatcher	= new EventDispatcher(this);
		}
		
		
		public function set uid(value:String):void {
			_uid = value;
		}
		
		public function get uid():String {
			return _uid;
		}
		
		
		public function get fileExtension():String {
			return "";
		}
		
		public function get subitems():ArrayCollection {
			return _subitems;
		}
		
		public function get parent():IScheduleItem {
			return _parent;
		}
		
		
		/**
		 * Usually this must be overritten.
		 */
		public function set product(value:AbstractSiemensProduct):void {
			_product = value;
		}
		
		public function get product():AbstractSiemensProduct {
			return _product;
		}

		
		public function set quantity(value:int):void {
			_quantity = value;
		}
		
		public function get quantity():int {
			return _quantity;
		}
		
		[Bindable]
		public function set priceMultiplier(value:Number):void {
			if (isNaN(value)) value = 1;
			_priceMultiplier = value;
			dispatchEvent(new Event("priceChanged"));
		}
		
		public function get priceMultiplier():Number {
			return _priceMultiplier;
		}
		
		[Bindable("priceChanged")]
		public function get price():Number {
			if (!product) return 0;
			return quantity * (product.price * priceMultiplier);
		}
		
		public function get formattedPrice():String {
			return "";
		}
		
		
		public function set notes(value:String):void {
			_notes	= value;
		}
		
		public function get notes():String {
			return _notes;
		}
		
		
		public function clone(withParent:Boolean=true):IScheduleItem {
			return null;
		}
		
		
		// Flash Proxy IMPLEMENTATION
		
		override flash_proxy function hasProperty(name:*):Boolean {
			return true;
		}
		
		override flash_proxy function nextNameIndex(index:int):int {
			return -1;
		}
		
		override flash_proxy function nextName(index:int):String {
			return null;
		}
		
		override flash_proxy function setProperty(name:*, value:*):void
		{ /* don't do nothing */ }
		
		/**
		 * This must be overritten.
		 */
		override flash_proxy function getProperty(name:*):* {
			return null;
		}

		
		// Event Dispatched IMPLEMENTATION
		
		public function addEventListener(type:String, listener:Function, useCapture:Boolean=false, priority:int=0, useWeakReference:Boolean=false):void {
			dispatcher.addEventListener(type, listener, useCapture, priority, useWeakReference);
		}
		
		public function removeEventListener(type:String, listener:Function, useCapture:Boolean=false):void {
			dispatcher.removeEventListener(type, listener, useCapture);
		}
		
		public function dispatchEvent(event:Event):Boolean {
			return dispatcher.dispatchEvent(event);
		}
		
		public function hasEventListener(type:String):Boolean {
			return dispatcher.hasEventListener(type);
		}
		
		public function willTrigger(type:String):Boolean {
			return dispatcher.willTrigger(type);
		}
		
		
		// IExternalizable IMPLEMENTATION

		/**
		 * Function to serialize class to binary.
		 * This must be overritten.
		 */		
		public function writeExternal(output:IDataOutput):void
		{}
		
		
		/**
		 * Function to deserialize class from binary.
		 * This must be overritten.
		 */
		public function readExternal(input:IDataInput):void
		{ }
	}
}