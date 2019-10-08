package Swizzard.Data.Models.Query
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.utils.Dictionary;
	
	import mx.events.PropertyChangeEvent;
	import mx.events.PropertyChangeEventKind;

	public class BaseQueryModel extends EventDispatcher
	{
		public var resetPending:Boolean;
		public var suppressEvents:Boolean;
		protected var changedFields:Dictionary;
		
		// Just add parameters to query products after. 
		// The properties should be bindable and each setter should set a 
		// flag for that property name in changedFields dictionary.
		
		
		public function BaseQueryModel() {
			suppressEvents = false;
			changedFields = new Dictionary();
		}
		
		
		// this function is only for extending and overriding, to reset 
		// the custom query properties to their default values 
		protected function customReset():void 
		{}
		
		
		// -------- Standard--------
		public function get changed():Dictionary {
			return changedFields;
		}
		
		
		public function clearChanged():void {
			changedFields = new Dictionary();
		}
		
		
		public function reset(notify:Boolean = true):void {
			suppressEvents = true;
			clearChanged();
			
			customReset();
			
			suppressEvents = false;
			if (notify) {
				dispatchChangedEvent(null);
			}
		}
		
		
		public function dispatchChangedEvent(propertyName:String = "*"):void {
			dispatchEvent(new PropertyChangeEvent(PropertyChangeEvent.PROPERTY_CHANGE, false, false, PropertyChangeEventKind.UPDATE, propertyName));
		}
		
		
		override public function dispatchEvent(event:Event):Boolean {
			if (!suppressEvents) {
				return super.dispatchEvent(event);
			}
			return false;
		}
	}
}