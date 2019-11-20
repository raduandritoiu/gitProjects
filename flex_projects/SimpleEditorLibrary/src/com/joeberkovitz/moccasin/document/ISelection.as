package com.joeberkovitz.moccasin.document
{
    import com.joeberkovitz.moccasin.model.ModelRoot;
    
    /**
     * Interface exposed by any object representing a selection of some kind.  Some applications
     * have rule-based or geometric selections that are defined in terms of more than just a
     * set of objects.  
     * 
     */
    public interface ISelection
    {
        /**
         * Return the union of this selection with another. 
         */
        function union(sel:ISelection):ISelection;

        /**
         * Return true if this selection properly contains the given model object.
         */
        function contains(obj:Object):Boolean;

        /**
         * Return true if this selection includes the given model object as part of itself. 
         */
        function includes(obj:Object):Boolean;

        /**
         * Coerce this selection to an array of individual models. 
         */
        function get selectedModels():Array;

        /**
         * The ModelRoot object to which this selection applies. 
         */
        function get root():ModelRoot;

        /**
         * True if this selection contains no content or has a null extent. 
         */
        function get empty():Boolean;

        /**
         * Dispatch a status change event from objects in this selection to update the views.
         */
        function dispatchStatusChange():void;

        /**
         * Create and return an IClipboard instance reflecting the content of this selection
         * within the MoccasinModel to which it applies. 
         */
        function createClipboard():IClipboard;
    }
}
