package com.joeberkovitz.moccasin.event
{
    import flash.events.Event;
    
    /**
     * Signals a change in the state of the controller, as seen by the editor.  
     */
    public class ControllerEvent extends Event
    {
        /** Event type indicating that the controller's active document instance changed. */
        public static const DOCUMENT_CHANGE:String = "documentChange";
        
        public function ControllerEvent(type:String)
        {
            super(type);
        }
        
        override public function clone():Event
        {
            return new ControllerEvent(type);
        }
    }
}
