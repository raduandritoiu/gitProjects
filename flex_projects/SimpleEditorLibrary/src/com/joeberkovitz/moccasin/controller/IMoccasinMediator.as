package com.joeberkovitz.moccasin.controller
{
    import com.joeberkovitz.moccasin.view.IMoccasinView;
    
    /**
     * An IMoccasinMediator brokers events between a view, its model and its layout.  Different
     * implementations provide different support for gestures on the view and on how it responds to
     * model and layout changes.
     */
    public interface IMoccasinMediator
    {
        /**
         * Inform this mediator of the view, notation and layout for which it is to mediate events
         *  
         * @param view an IMoccasinView
         * 
         */
        function handleViewEvents(view:IMoccasinView):void
    }
}
