package com.joeberkovitz.moccasin.service
{
    import com.joeberkovitz.moccasin.event.OperationFaultEvent;
    
    import flash.events.Event;
    import flash.events.IEventDispatcher;
    
    [Event(name="complete",type="flash.events.Event")]
    [Event(name="fault",type="com.joeberkovitz.moccasin.event.OperationFaultEvent")]

    /**
     * Interface representing an operation of some sort,
     * probably but not necessarily asynchronous.  Its execute() method
     * initiates it, after which its contract requires that it
     * eventually dispatch either Event.COMPLETE or ErrorEvent.ERROR.
     * 
     */
    public interface IOperation extends IEventDispatcher
    {
        /** Displayable name for this operation */
        function get displayName():String;
        function set displayName(value:String):void;
        
        /** Result object from this operation */
        function get result():*;
        
        /**
         * Initiate this operation.  An event may be dispatched during
         * the execution of this function, or at any point afterwards.
         */
        function execute():void;
    }
}
