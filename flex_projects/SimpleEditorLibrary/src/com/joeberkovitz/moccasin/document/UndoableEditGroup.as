package com.joeberkovitz.moccasin.document
{
    /**
     * A Composite edit composed of other granular edits. 
     */
    public class UndoableEditGroup implements IUndoableEdit
    {
        // Set of edits that constitute the group 
        private var _edits:Array = [];
        
        /** Display name of this group */
        public var name:String;
        
        public function UndoableEditGroup(name:String)
        {
           this.name = name;
        }

        public function addEdit(edit:IUndoableEdit):void
        {
            _edits.push(edit);
        }
        
        public function redo():void
        {
            for (var i:int = 0; i < _edits.length; i++)
            {
                IUndoableEdit(_edits[i]).redo();
            }
        }

        public function undo():void
        {
            for (var i:int = _edits.length - 1; i >= 0; i--)
            {
                IUndoableEdit(_edits[i]).undo();
            }
        }
    }
}
