package com.joeberkovitz.moccasin.controller
{
    import com.joeberkovitz.moccasin.document.CollectionClipboard;
    import com.joeberkovitz.moccasin.document.IClipboard;
    import com.joeberkovitz.moccasin.document.ISelection;
    import com.joeberkovitz.moccasin.document.MoccasinDocument;
    import com.joeberkovitz.moccasin.document.ObjectSelection;
    import com.joeberkovitz.moccasin.event.ControllerEvent;
    import com.joeberkovitz.moccasin.event.SelectEvent;
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    import com.joeberkovitz.moccasin.model.ModelRoot;
    
    import flash.events.EventDispatcher;
    
    import mx.collections.ArrayCollection;

    [Event(name="documentChange",type="com.joeberkovitz.moccasin.event.ControllerEvent")]
    [Event(name="addSelection",type="com.joeberkovitz.moccasin.event.SelectEvent")]
    [Event(name="removeSelection",type="com.joeberkovitz.moccasin.event.SelectEvent")]
    [Event(name="changeSelection",type="com.joeberkovitz.moccasin.event.SelectEvent")]
    
    /**
     * The MoccasinController is an abstract superclass of application controllers.  Its role is to
     * isolate other parts of the architecture from the implementation of modifications to the model,
     * and to act as a central dispatch point for common handling of modifications. 
     */
    public class MoccasinController extends EventDispatcher implements IMoccasinController
    {
        private var _document:MoccasinDocument;
        private var _clipboard:IClipboard = null;
                
        public function MoccasinController(document:MoccasinDocument)
        {
            this.document = document;
        }
        
        /**
         * The root model object of for the document.
         */
        public function get root():ModelRoot
        {
            return _document.root;
        }
        
        /**
         * The IClipboard instance representing the clipboard state, or null if there is none.
         */        
        public function get clipboard():IClipboard
        {
            return _clipboard;
        }

        
        /**
         * The ISelection instance representing the current selection within the document, or null
         * if nothing is selected.
         */
        public function get selection():ISelection
        {
            return _document.selection;
        }
        
        /**
         * The MoccasinDocument which holds the model root, selection state and undo history for
         * the application.
         */
        [Bindable("documentChange")]
        public function get document():MoccasinDocument
        {
            return _document;
        }
        
        public function set document(d:MoccasinDocument):void
        {
            if (_document != null)
            {
                // When a document is replaced by another, kill its selection first to get rid
                // of any selection feedback that may be out there.
                _document.selection = null;
                
                _document.removeEventListener(SelectEvent.ADD_SELECTION, handleSelectEvent);
                _document.removeEventListener(SelectEvent.REMOVE_SELECTION, handleSelectEvent);
                _document.removeEventListener(SelectEvent.CHANGE_SELECTION, handleSelectEvent);
            }
            
            _document = d;
            
            if (_document != null)
            {
                // A new document always has no selection.
                _document.selection = null;
                
                _document.addEventListener(SelectEvent.ADD_SELECTION, handleSelectEvent, false, 0, true);
                _document.addEventListener(SelectEvent.REMOVE_SELECTION, handleSelectEvent, false, 0, true);
                _document.addEventListener(SelectEvent.CHANGE_SELECTION, handleSelectEvent, false, 0, true);
            }
            
            dispatchEvent(new ControllerEvent(ControllerEvent.DOCUMENT_CHANGE));
        }
        
        /**
         * Copy the current selection to the clipboard.
         */
        public function copyClipboard():void
        {
            if (_document.selection != null && !_document.selection.empty)
            {
                _clipboard = _document.selection.createClipboard();
            }
        }
        
        /**
         * Cut the current selection from the document and place it in the clipboard.
         */
        public function cutClipboard():void
        {
            if (_document.selection != null && !_document.selection.empty)
            {
                _clipboard = _document.selection.createClipboard();
                removeSelection();
            }
        }
        
        /**
         * Paste the clipboard contents into the document.  This is a template implementation
         * which requires extension for more complex document models and selection types.
         */
        public function pasteClipboard():void
        {
            if (_clipboard is CollectionClipboard)
            {
                var pastedModels:Array = [];
                for each (var m:MoccasinModel in CollectionClipboard(_clipboard).models)
                {
                    var m2:MoccasinModel = transformPastedModel(m.clone());
                    pastedModels.push(m2);
                    addPastedModel(m2);
                }
                
                document.selection = new ObjectSelection(root, pastedModels);
            }
        }
        
        /**
         * Template method for adding model objects cloned from the clipboard to the document.
         */
        protected function addPastedModel(model:MoccasinModel):void
        {
            root.valueChildren.addItem(model.value);
        }
        
        /**
         * Template method for transforming model objects cloned from the clipboard.  The
         * default implementation does not modify the object.
         */
        protected function transformPastedModel(model:MoccasinModel):MoccasinModel
        {
            return model;
        }
        
        /**
         * Undo the most recent event in the undo history.
         */
        public function undo():void
        {
            _document.undoHistory.undo();
        }
        
        /**
         * Redo the most recent event in the undo history.
         */
        public function redo():void
        {
            _document.undoHistory.redo();
        }
        
        private function get selectedModels():Array
        {
            if (selection != null)
            {
                return selection.selectedModels;
            }
            else
            {
                return [];
            }
        }
        
        /**
         * Select exactly one object in the document 
         * @param m the object to be selected.
         */
        public function selectSingleModel(m:MoccasinModel):void
        {
            document.selection = new ObjectSelection(root, [m]);
        }
        
        /**
         * Select multiple objects in the document 
         * @param ms array of objects to be selected.
         */
        public function selectModels(ms:Array):void
        {
            document.selection = new ObjectSelection(root, ms);
        }

        /**
         * Extend an existing selection to include a model object 
         * @param n the object whose selected status is to be modified.
         */
        public function modifySelection(m:MoccasinModel):void
        {
            if (selection == null || selection.empty)
            {
                selectSingleModel(m);
            }
            else
            {
                // NOTE: didn't use polymorphism here because in the ObjectSelection case, we're
                // modifying the document; would prefer selection objects not to know about documents.
                //             
                if (selection is ObjectSelection)
                {
                    if (selection.contains(m))
                    {
                        _document.deselect(new ObjectSelection(root, [m]));
                    }
                    else
                    {
                        _document.select(new ObjectSelection(root, [m]));
                    }
                }
            }
        }

        /**
         * Extend an existing selection to include multiple model objects 
         * @param ms the array of objects whose selected status is to be modified.
         */
        public function modifyMultiSelection(ms:Array):void
        {
            if (selection == null || selection.empty)
            {
                selectModels(ms);
            }
            else
            {
                var selModels:ArrayCollection = new ArrayCollection();
                var unselModels:ArrayCollection = new ArrayCollection();
                if (selection is ObjectSelection)
                {
                    for each (var m:MoccasinModel in ms)
                    {
                        if (selection.contains(m))
                            unselModels.addItem(m);
                        else
                            selModels.addItem(m);
                    }
                }
                if (selModels.length!=0)
                    _document.select(new ObjectSelection(root, selModels.source));
                if (unselModels.length!=0)
                    _document.deselect(new ObjectSelection(root, unselModels.source));
            }
        }

        /**
         * Clear any current selection for the document.  
         * 
         */
        public function clearSelection():void
        {
            document.selection = null;
        }
        
        /**
         * Remove all objects in the current selection. 
         */
        public function removeSelection():void
        {
            if (selection is ObjectSelection)
            {
                applyToSelection(function(m:MoccasinModel):void {
                    m.parent.removeValueChild(m.value);
                });
            }
        }
        
        /**
         * Apply some function to all selected objects.
         */
        private function applyToSelection(f:Function):void
        {
            for each (var m:MoccasinModel in selectedModels)
            {
                f(m);
            }
        }
        

        /**
         * Handle all SelectEvents by redispatching, for convenience to our clients which would
         * rather not be bothered tracking changes in the MoccasinDocument. 
         */
        private function handleSelectEvent(e:SelectEvent):void
        {
            dispatchEvent(e);
        }
    }
}
