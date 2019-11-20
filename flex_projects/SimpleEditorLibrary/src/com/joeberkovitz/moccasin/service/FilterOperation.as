package com.joeberkovitz.moccasin.service
{
    import com.joeberkovitz.moccasin.event.OperationFaultEvent;
    import com.joeberkovitz.moccasin.event.ProgressSourceEvent;
    
    import flash.events.Event;
    
    [Event(name="complete",type="flash.events.Event")]
    [Event(name="fault",type="com.joeberkovitz.moccasin.event.OperationFaultEvent")]

    /**
     * Abstract class representing a filter operating around an underlying operation,
     * for instance to parse its result or handle its outcome in some specialized way.
     */
    public class FilterOperation extends AbstractOperation
    {
        private var _operation:IOperation;
        
        public function FilterOperation(operation:IOperation)
        {
            _operation = operation;
            _operation.addEventListener(Event.COMPLETE, handleComplete);
            _operation.addEventListener(ProgressSourceEvent.PROGRESS_START, handleProgressStart);
            _operation.addEventListener(OperationFaultEvent.FAULT, handleOperationFault);
        }
        
        /**
         * Initiate this Operation.  An event may be dispatched during
         * the execution of this function, or at any point afterwards.
         */
        override public function execute():void
        {
            _operation.execute();
        }
        
        /**
         * On success, return the result of this operation.
         */
        override public function get result():*
        {
            return _operation.result;
        }
        
        /**
         * The underlying operation for this FilterOperation. 
         */
        public function get operation():IOperation
        {
            return _operation;
        }

        protected function handleProgressStart(e:ProgressSourceEvent):void
        {
            dispatchProgressSourceEvent(e.source);
        }
        
        protected function handleOperationFault(e:OperationFaultEvent):void
        {
            dispatchEvent(e);
        }

        override protected function handleComplete(e:Event):void
        {
            dispatchEvent(e);
        }
   }
}
