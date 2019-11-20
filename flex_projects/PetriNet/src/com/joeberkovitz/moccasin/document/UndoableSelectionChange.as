package com.joeberkovitz.moccasin.document
{
    import com.joeberkovitz.moccasin.event.SelectEvent;
    
    /**
     * An undoable edit representing a selection change as represented by a SelectEvent.
     */
    public class UndoableSelectionChange implements IUndoableEdit
    {
        private var _event:SelectEvent;
        
        public function UndoableSelectionChange(e:SelectEvent)
        {
            _event = e;
        }
        
        public function get document():MoccasinDocument
        {
            return MoccasinDocument(_event.target)
        }

        public function redo():void
        {
            if (_event.type == SelectEvent.ADD_SELECTION)
            {
                document.select(_event.selection);
            }
            else if (_event.type == SelectEvent.REMOVE_SELECTION)
            {
                document.deselect(_event.selection);
            }
        }

        public function undo():void
        {
            if (_event.type == SelectEvent.ADD_SELECTION)
            {
                document.deselect(_event.selection);
            }
            else if (_event.type == SelectEvent.REMOVE_SELECTION)
            {
                document.select(_event.selection);
            }
        }
    }
}
