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
     * supporting mouse-based selection and rollover feedback.
     * @author joeb
     * 
     */
    public class SelectableView extends MoccasinView
    {
        // flag tracking rollover state
        private var _rolled:Boolean = false;
        
        // associated feedback view, if any
        private var _feedback:DisplayObject;

        /**
         * Flag controlling whether selection is shown using a default color transform. 
         */
        public var useSelectionTransform:Boolean = false;
        
        /**
         * Flag controlling use of rollover glow. 
         */
        public var useRolloverGlow:Boolean = true;
        
        public function SelectableView(context:ViewContext, model:MoccasinModel)
        {
            super(context, model);
            addEventListener(MouseEvent.ROLL_OVER, addRollHighlight);
            addEventListener(MouseEvent.ROLL_OUT, removeRollHighlight);
        }
        
        /**
         * Use a SelectionMediator to provide handling of selection gestures.
         */
        override protected function initialize():void
        {
            super.initialize();
            new SelectionMediator().handleViewEvents(this);
        }

        /**
         * Determine whether this object is selected or not by consulting the document's selection.
         */
        override public function get selected():Boolean
        {
            if (context.document == null || context.document.selection == null)
            {
                return false;
            }
            
            return context.document.selection.includes(model);
        }

        /**
         * Update the appearance of this object with respect to selection and rollover.  If useSelectionTransform
         * is set, then a default color transform will be applied to the hue of the object.
         */
        override protected function updateStatus():void
        {
            super.updateStatus();

            // Display default selection highlighting if requested
            //
            if (useSelectionTransform)
            {
                transform.colorTransform = getColorTransform();
            }            

            // Display default rollover feedback if requested
            //
            if (useRolloverGlow && _rolled && !selected && model.enabled)
            {
                var f:BitmapFilter = new GlowFilter(context.info.selectionColors[0], 0.25, 10, 10, 8);
                filters = [f];
            }
            else
            {
                filters = null;
            }
            
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
        
        protected function addRollHighlight(e:MouseEvent):void
        {
            _rolled = true;
            updateStatus();
            e.stopPropagation();
        }
        
        protected function removeRollHighlight(e:MouseEvent):void
        {
            _rolled = false;
            if (stage == null)
            {
                // Disregard roll highlight removal for off-stage views
                return;
            }
            updateStatus();
            e.stopPropagation();
        }
    }
}
