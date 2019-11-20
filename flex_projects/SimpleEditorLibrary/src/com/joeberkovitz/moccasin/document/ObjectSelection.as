package com.joeberkovitz.moccasin.document
{
    import com.joeberkovitz.moccasin.event.ModelStatusEvent;
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    import com.joeberkovitz.moccasin.model.ModelRoot;
    
    import flash.utils.Dictionary;
    
    /**
     * A simple type of selection consisting of an unordered set of selected model objects.
     */
    public class ObjectSelection implements ISelection
    {
        private var _root:ModelRoot;
        private var _selectedModels:Array;
        private var _dictionary:Dictionary;
        
        public function ObjectSelection(root:ModelRoot, selectedModels:Array)
        {
            super();
            _root = root;
            _selectedModels = [];
            _dictionary = new Dictionary();
            for each (var obj:Object in selectedModels)
            {
                if (! (obj in _dictionary))
                {
                    _dictionary[obj] = obj;
                    _selectedModels.push(obj);
                }
            }
        }
        
        public function get selectedModels():Array
        {
            return _selectedModels;
        }
        
        public function get root():ModelRoot
        {
            return _root;
        }
        
        public function includes(obj:Object):Boolean
        {
            return contains(obj);
        }
        
        public function contains(obj:Object):Boolean
        {
            return obj in _dictionary;
        }
        
        /** return the union of this selection with another ObjectSelection */
        public function union(sel:ISelection):ISelection
        {
            if (sel is ObjectSelection)
            {
                var objSel:ObjectSelection = sel as ObjectSelection;
                return new ObjectSelection(root, _selectedModels.concat(objSel._selectedModels));
            }
            else
            {
                return super.union(sel);
            }
        }
        
        /** return all objects in this selection that are not in some other selection */
        public function difference(sel:ObjectSelection):ISelection
        {
            var diff:Array = [];
            for each (var obj:Object in _selectedModels)
            {
                if (!sel.contains(obj))
                {
                    diff.push(obj);
                }
            }
            return new ObjectSelection(root, diff);
        }

        public function dispatchStatusChange():void
        {
            // For an object selection, dispatch status change events off the actual model objects
            // in the selection so that they will be redrawn in their changed selection status.
            // 
            for each (var obj:MoccasinModel in _selectedModels)
            {
                obj.dispatchEvent(new ModelStatusEvent(ModelStatusEvent.STATUS_CHANGE));
            }
        }
        
        public function createClipboard():IClipboard
        {
            return new CollectionClipboard(selectedModels);
        }

        /** return true if empty */
        public function get empty():Boolean
        {
            return (_selectedModels.length == 0);
        }
    }
}
