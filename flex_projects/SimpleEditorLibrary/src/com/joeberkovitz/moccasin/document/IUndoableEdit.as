package com.joeberkovitz.moccasin.document
{
    /**
     * Abstraction of an edit that can be undone and redone. 
     */
    public interface IUndoableEdit
    {
        /**
         * Undo this edit. 
         */
        function undo():void;
        
        /**
         * Redo this edit.
         */        
        function redo():void;
    }
}
