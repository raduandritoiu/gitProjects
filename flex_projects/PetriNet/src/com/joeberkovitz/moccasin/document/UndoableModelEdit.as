package com.joeberkovitz.moccasin.document
{
    import com.joeberkovitz.moccasin.event.ModelEvent;
    
    /**
     * An undoable edit representing a structural change to the model as represented by a ModelEvent.
     */
    public class UndoableModelEdit implements IUndoableEdit
    {
        private var _event:ModelEvent;
        
        public function UndoableModelEdit(e:ModelEvent)
        {
            _event = e;
        }
        
        public function redo():void
        {
            switch (_event.kind)
            {
            case ModelEvent.ADD_CHILD_MODEL:
                _event.parent.valueChildren.addItemAt(_event.child.value, _event.index);
                break;
            
            case ModelEvent.REMOVE_CHILD_MODEL:
                _event.parent.valueChildren.removeItemAt(_event.index);
                break;
            }
        }

        public function undo():void
        {
            switch (_event.kind)
            {
            case ModelEvent.ADD_CHILD_MODEL:
                _event.parent.valueChildren.removeItemAt(_event.index);
                break;

            case ModelEvent.REMOVE_CHILD_MODEL:
                _event.parent.valueChildren.addItemAt(_event.child.value, _event.index);
                break;
            }
        }
    }
}
