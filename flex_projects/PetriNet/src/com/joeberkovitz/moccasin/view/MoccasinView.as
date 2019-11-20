package com.joeberkovitz.moccasin.view
{
    import com.joeberkovitz.moccasin.event.ModelEvent;
    import com.joeberkovitz.moccasin.event.ModelStatusEvent;
    import com.joeberkovitz.moccasin.event.ModelUpdateEvent;
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    
    import flash.display.DisplayObject;
    import flash.display.Sprite;
    import flash.geom.ColorTransform;
    import flash.utils.getQualifiedClassName;

    /**
     * MoccasinView is the superclass of all DisplayObject-based views in Moccasin.  It provides
     * basic hookups to the events that drive view refresh based on model changes,
     * and a very basic implementation of selection feedback based on color transforms. 
     */
    public class MoccasinView extends Sprite implements IMoccasinView
    {
        private var _context:ViewContext;
        private var _model:MoccasinModel;
        
        /**
         * Create a new MoccasinView. 
         * @param context the shared ViewContext to which this View belongs.
         * @param model the MoccasinModel that this view presents.
         * 
         */
        public function MoccasinView(context:ViewContext, model:MoccasinModel)
        {
            _context = context;
            _model = model;
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
         * Call this function in every constructor after the object is built out, to set up the view and
         * add event listeners. 
         */
        protected function initialize():void
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
            createChildren();
            updateView();
            updateStatus();
        }
        
        // Abstract methods
        
        protected function createChildren():void
        {
            for (var i:int = 0; i < model.numChildren; i++)
            {
                addChild(createChildView(model.getChildAt(i)) as DisplayObject);
            }
        }

        /**
         * Called to update the view's contents, typically by consulting its layout and constructing its children. 
         */
        protected function updateView():void
        {
            graphics.clear();
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
        
        /** Flag that determines whether an element in the view appears selected or not. */
        public function get selected():Boolean
        {
            return false;
        }

        /**
         * A ColorTransform dependent on this object's status; used to affect how it looks
         * depending on whether it's selected, a feedback object, or just plain normal.
         */
        protected function getColorTransform():ColorTransform
        {
            if (selected)
            {
                return lightenTransform(context.info.selectionColors[0]);
            }
            else
            {
                return new ColorTransform();
            }
        }
        
        public static function lightenTransform(color:uint):ColorTransform
        {
            return new ColorTransform(1, 1, 1, 1,
                                      color >> 16, (color >> 8) & 0xFF, color & 0xFF);
        }

        public static function darkenTransform(color:uint):ColorTransform
        {
            return new ColorTransform((color >> 16) / 255.0, ((color >> 8) & 0xFF) / 255.0, (color & 0xFF) / 255.0);
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
