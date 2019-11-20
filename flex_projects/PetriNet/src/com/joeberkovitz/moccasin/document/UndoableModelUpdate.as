package com.joeberkovitz.moccasin.document
{
    import com.joeberkovitz.moccasin.event.ModelUpdateEvent;
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    
    /**
     * An undoable edit representing an update to a model object's property.
     */
    public class UndoableModelUpdate implements IUndoableEdit
    {
        private var _event:ModelUpdateEvent;
        
        public function UndoableModelUpdate(e:ModelUpdateEvent)
        {
            _event = e;
        }
        
        public function get model():MoccasinModel
        {
            return MoccasinModel(_event.source);
        }
        
        public function redo():void
        {
            model.value[_event.property] = _event.newValue;
        }

        public function undo():void
        {
            model.value[_event.property] = _event.oldValue;
       }
    }
}
