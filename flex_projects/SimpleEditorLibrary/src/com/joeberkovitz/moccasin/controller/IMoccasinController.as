package com.joeberkovitz.moccasin.controller
{
    import com.joeberkovitz.moccasin.document.ISelection;
    import com.joeberkovitz.moccasin.document.MoccasinDocument;
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    import com.joeberkovitz.moccasin.model.ModelRoot;
    
    import flash.events.IEventDispatcher;
    
    [Event(name="documentChange",type="com.joeberkovitz.moccasin.event.ControllerEvent")]
    [Event(name="addSelection",type="com.joeberkovitz.moccasin.event.SelectEvent")]
    [Event(name="removeSelection",type="com.joeberkovitz.moccasin.event.SelectEvent")]
    [Event(name="changeSelection",type="com.joeberkovitz.moccasin.event.SelectEvent")]

    /**
     * Basic controller interface for operations supported by all Moccasin apps. 
     */
    public interface IMoccasinController extends IEventDispatcher
    {
        /**
         * The MoccasinDocument that manages this controller's root model. 
         */
        [Bindable("documentChange")]
        function get document():MoccasinDocument;
        function set document(d:MoccasinDocument):void;
        
        /**
         * The root model which is modified by this controller. 
         */
        function get root():ModelRoot;

        /**
         * The current ISelection object if there is a selection, or null if there is not. 
         */
        function get selection():ISelection;

        /**
         * Copy the current selection's contents to the clipboard. 
         */
        function copyClipboard():void;
        
        /**
         * Copy the current selection to the clipboard and then remove this selection from the document. 
         */
        function cutClipboard():void;
        
        /**
         * Paste the clipboard's contents into the document relative to the current selection. 
         */
        function pasteClipboard():void;

        /**
         * Undo the most recent action group in the document's undo history.
         */
        function undo():void;
        
        /**
         * Redo the most recent action group in the document's undo history.
         */
        function redo():void;
                 
        /**
         * Select exactly one model. 
         */
        function selectSingleModel(m:MoccasinModel):void;
        
        /**
         * Select mocassin models.
         */
        function selectModels(ms:Array):void;
        
        /**
         * Extend an existing selection to include/exclude a model. 
         * @param n the model object whose selected status is to be modified.
         */
        function modifySelection(m:MoccasinModel):void;
        
        /**
         * Extend an existing selection to include/exclude multiple models. 
         * @param n the array of model objects whose selected status are to be modified.
         */
        function modifyMultiSelection(ms:Array):void;

        /**
         * Deselect all selected objects. 
         */
        function clearSelection():void

        /**
         * Remove all models in the current selection from the document. 
         */
        function removeSelection():void;
        
    }
}
