package com.joeberkovitz.moccasin.view
{
    import com.joeberkovitz.moccasin.event.ModelEvent;
    import com.joeberkovitz.moccasin.event.ModelStatusEvent;
    import com.joeberkovitz.moccasin.event.ModelUpdateEvent;
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    
    import flash.display.DisplayObject;
    import flash.utils.getQualifiedClassName;
    
    import mx.containers.Canvas;
    import mx.events.FlexEvent;

    /**
     * MoccasinCanvas is the superclass of all Flex UIComponent-based view objects in Moccasin.  Using MoccasinCanvas
     * at the top of the view hierarchy requires that it and/or MoccasinComponent be used as the superclass for all descendant views as well,
     * unless UIComponent instances are "spliced in" to allow MoccasinView-derived views.
     */
    public class MoccasinCanvas extends Canvas implements IMoccasinView
    {
        private var _context:ViewContext;
        private var _model:MoccasinModel;
        
        /**
         * Create a new MoccasinView. 
         * @param context the shared ViewContext to which this View belongs.
         * @param model the MoccasinModel that this view presents.
         * 
         */
        public function MoccasinCanvas(context:ViewContext, model:MoccasinModel)
        {
            _context = context;
            _model = model;
            
            // Do not clip content for items that cross the bounds of this Canvas.  Also
            // prevents unhealthy scrollbars!
            //
            clipContent = false;
            
            // Set up follow-on initialization of view after construction, but before rendering
            addEventListener(FlexEvent.PREINITIALIZE, handlePreinitialize);
        }
        
        protected function handlePreinitialize(e:FlexEvent):void
        {
            initializeView();
            if (model != null)
            {
                model.addEventListener(ModelStatusEvent.STATUS_CHANGE, handleStatusChange, false, 0, true);
                model.addEventListener(ModelEvent.MODEL_CHANGE, handleModelChange, false, 0, true);
                model.addEventListener(ModelUpdateEvent.MODEL_UPDATE, handleModelUpdate, false, 0, true);
            }
        }
        
        protected function initializeView():void
        {
            updateView();
            updateStatus();
        }
        
        public function get context():ViewContext
        {
            return _context;
        }
        
        public function get model():MoccasinModel
        {
            return _model;
        }
        
        /**
         * Factory method to create the appropriate MoccasinView for a new child model.
         */
        public function createChildView(child:MoccasinModel):IMoccasinView
        {
            throw new Error("createChildView not overridden by " + getQualifiedClassName(this));
        }
        
        /**
         * Handle a specific property change by some incremental adjustment and return true,
         * otherwise return false to reinitialize the view. 
         */
        protected function updateModelProperty(property:Object, oldValue:Object, newValue:Object):Boolean
        {
            return false;
        }

        /**
         * Update the status of this object with respect to selection status, etc. 
         */
        protected function updateStatus():void
        {
        }
        
        /**
         * Update the view of this object with respect to its model 
         */
        protected function updateView():void
        {
        }
        
        /**
         * Override Flex child-creation hook to create child views of this MoccasinCanvas.
         */
        override protected function createChildren():void
        {
            super.createChildren();
            
            // Preemptively create all child views
            for (var i:int = 0; i < model.numChildren; i++)
            {
                addChild(createChildView(model.getChildAt(i)) as DisplayObject);
            }
        }
        
        private function handleStatusChange(e:ModelStatusEvent):void
        {
            if (stage != null)
            {
                updateStatus();
            }
        }
        
        /**
         * Handle structural changes in the model by adding or removing views.  The z-order of views
         * corresponds to the order in the model's children.
         */
        private function handleModelChange(e:ModelEvent):void
        {
            if (e.parent != model || stage == null)
            {
                return;
            }

            switch (e.kind)
            {
                case ModelEvent.ADD_CHILD_MODEL:
                    addChildAt(createChildView(e.child) as DisplayObject, e.index);
                    break;
    
                case ModelEvent.REMOVE_CHILD_MODEL:
                    removeChildAt(e.index);
                    break;
            }
        }
        
        /**
         * Handle property changes to the model by attempting some sort of incremental change
         * via updateModelProperty(), then falling back to reinitializing the view completely.
         */
        private function handleModelUpdate(e:ModelUpdateEvent):void
        {
            if (e.source != model || stage == null)
            {
                return;
            }
 
            if (!updateModelProperty(e.property, e.oldValue, e.newValue))
            {
                updateView();
            }
        }
    }
}
