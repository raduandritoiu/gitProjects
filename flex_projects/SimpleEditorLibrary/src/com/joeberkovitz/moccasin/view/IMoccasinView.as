package com.joeberkovitz.moccasin.view
{
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    
    import flash.events.IEventDispatcher;

    /**
     * IMoccasinView is an interface common to all Moccasin views, regardless of their concrete superclass
     * (which may be any one of a number of various Flash or Flex classes).
     */
    public interface IMoccasinView extends IEventDispatcher
    {
        //
        // DisplayObject METHODS
        //
        
        /**
         * X position of this view relative to its parent.
         */
        function get x():Number;
        
        /**
         * Y position of this view relative to its parent.
         */        
        function get y():Number;

        /**
         * Width of this view. 
         */
        function get width():Number;

        /**
         * Height of this view. 
         */
        function get height():Number;
        
        //
        // MoccasinView/MoccasinCanvas METHODS
        //
        
        /**
         * The ViewContext used by this IMoccasinView.
         */
        function get context():ViewContext;
        
        /**
         * THe underlying MoccasinModel object displayed by this view.
         */        
        function get model():MoccasinModel;
        
        /**
         * Create a new child view corresponding to a child model object. 
         * @param child the child MoccasinModel for which some appropriate new child view should be created
         * @return a new IMoccasinView instance.
         */
        function createChildView(child:MoccasinModel):IMoccasinView;
    }
}
