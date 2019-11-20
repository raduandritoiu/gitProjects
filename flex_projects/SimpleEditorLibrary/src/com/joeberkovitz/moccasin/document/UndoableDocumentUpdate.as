package com.joeberkovitz.moccasin.document
{
    import com.joeberkovitz.moccasin.event.DocumentUpdateEvent;
    
    /**
     * An undoable edit representing a change to some property of the MoccasinDocument (as opposed
     * to the model within that document).
     */
    public class UndoableDocumentUpdate implements IUndoableEdit
    {
        // Set of edits that constitute the group 
        private var _event:DocumentUpdateEvent;
        
        public function UndoableDocumentUpdate(e:DocumentUpdateEvent)
        {
            _event = e;
        }
        
        public function get document():MoccasinDocument
        {
            return _event.document;
        }
        
        public function redo():void
        {
            document[_event.property] = _event.newValue;
        }

        public function undo():void
        {
            document[_event.property] = _event.oldValue;
       }
    }
}
