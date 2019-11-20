package com.joeberkovitz.moccasin.event
{
    import flash.events.Event;
    
    /**
     * Informs interested parties to refresh history-specific state such as undo history affordances.
     */
    public class UndoEvent extends Event
    {
        public static const UNDO_HISTORY_CHANGE:String = "undoHistoryChange";
        
        public function UndoEvent(type:String)
        {
            super(type);
        }
        
        override public function clone():Event
        {
            return new UndoEvent(type);
        }
    }
}
