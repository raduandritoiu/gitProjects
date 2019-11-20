package com.joeberkovitz.moccasin.view
{
    import com.joeberkovitz.moccasin.controller.SelectionMediator;
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    
    import flash.display.DisplayObject;
    import flash.events.MouseEvent;
    import flash.filters.BitmapFilter;
    import flash.filters.GlowFilter;
    
    /**
     * Abstract class providing a range of functionality for selectable views and objects,
     * supporting mouse-based selection and rollover feedback.  The Flex UIComponent-based version
     * of SelectableView.
     * 
     * @author joeb
     */
    public class SelectableComponent extends MoccasinComponent
    {
        // flag tracking rollover state
        private var _rolled:Boolean = false;
        
        // associated feedback view, if any
        private var _feedback:DisplayObject;

        public function SelectableComponent(context:ViewContext, model:MoccasinModel)
        {
            super(context, model);
            new SelectionMediator().handleViewEvents(this);
        }
        
        /**
         * Determine whether this object is selected or not by consulting the document's selection.
         */
        public function get selected():Boolean
        {
            if (context.document == null || context.document.selection == null)
            {
                return false;
            }
            
            return context.document.selection.includes(model);
        }
        
        override protected function updateStatus():void
        {
            super.updateStatus();
            
            // Manage creation/destruction of a specialized feedback view on a different layer.
            //
            if (selected && _feedback == null)
            {
                _feedback = createFeedbackView();
                if (_feedback != null)
                {
                    context.editor.feedbackLayer.addChild(_feedback);
                }
            }
            else if (!selected && _feedback != null)
            {
                context.editor.feedbackLayer.removeChild(_feedback);
                _feedback = null;
            }
        }
        
        protected function createFeedbackView():DisplayObject
        {
            return null;
        }
    }
}
