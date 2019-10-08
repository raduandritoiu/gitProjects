package Swizzard.System.Models.Interfaces
{
	import flash.events.IEventDispatcher;
	import flash.utils.IExternalizable;
	
	import mx.collections.ArrayCollection;
	import mx.core.IUID;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;

	public interface IScheduleItem extends IUID, IEventDispatcher, IExternalizable
	{
		function get parent():IScheduleItem;				
		function get subitems():ArrayCollection;				
		
		function get fileExtension():String;
		
		function set product(val:AbstractSiemensProduct):void;
		function get product():AbstractSiemensProduct;			
		
		function get price():Number;
		function get formattedPrice():String;
		
		function set priceMultiplier(val:Number):void;
		function get priceMultiplier():Number;
		
		function set quantity(val:int):void;
		function get quantity():int;
		
		function set notes(val:String):void;
		function get notes():String;
		
		function clone(withParent:Boolean = true):IScheduleItem;
	}
}