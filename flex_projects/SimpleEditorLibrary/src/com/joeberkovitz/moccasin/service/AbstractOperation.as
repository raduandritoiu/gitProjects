package com.joeberkovitz.moccasin.service
{
    import com.joeberkovitz.moccasin.event.OperationFaultEvent;
    import com.joeberkovitz.moccasin.event.ProgressSourceEvent;
    
    import flash.events.ErrorEvent;
    import flash.events.Event;
    import flash.events.EventDispatcher;
    import flash.events.IEventDispatcher;
    
    [Event(name="complete",type="flash.events.Event")]
    [Event(name="fault",type="com.joeberkovitz.moccasin.event.OperationFaultEvent")]

    /**
     * Abstract class representing an operation of some sort,
     * probably but not necessarily asynchronous.  Its execute() method
     * initiates it, after which its contract requires that it
     * eventually dispatch either Event.COMPLETE or ErrorEvent.ERROR.
     * 
     */
    public class AbstractOperation extends EventDispatcher implements IOperation
    {
        private var _displayName:String;

        /**
         * A name for this operation to be shown by the Controller to be shown
         * to indicate its progress or status.
         */
        [Bindable]
        public function get displayName():String
        {
            return _displayName;
        }

        public function set displayName(value:String):void
        {
            _displayName = value;
        }
        
        /**
         * Initiate this Operation.  An event may be dispatched during
         * the execution of this function, or at any point afterwards.
         */
        public function execute():void
        {
        }

        
        /**
         * On success, return the result of this operation.
         */
        public function get result():*
        {
            return null;
        }

        /**
         * Subclasses may override to determine the disposition
         * of an error-related event.
         */
        protected function handleError(e:ErrorEvent):void
        {
            dispatchEvent(new OperationFaultEvent(OperationFaultEvent.FAULT, e));
        }

        /**
         * Subclasses may override to determine disposition of a success event. 
         */
        protected function handleComplete(e:Event):void
        {
            dispatchEvent(new Event(Event.COMPLETE));
        }

        protected function dispatchProgressSourceEvent(source:IEventDispatcher):void
        {
            dispatchEvent(new ProgressSourceEvent(ProgressSourceEvent.PROGRESS_START, source, displayName));
        }
   }
}
