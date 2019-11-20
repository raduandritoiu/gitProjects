package com.joeberkovitz.moccasin.view
{
    import com.joeberkovitz.moccasin.event.EditorEvent;
    
    import flash.display.Sprite;
    import flash.events.MouseEvent;
    import flash.geom.Point;

    /**
     * A graphic used for manipulating a control point of some object, whose apparent size must remain constant
     * as the editor zoom scale is changed.
     */
    public class AbstractHandle extends Sprite
    {
        /**
         * Size of the handle in screen pixels, regardless of scaling. 
         */
        public var apparentSize:Number = 8;
        
        private var _context:ViewContext;
        private var _handleSize:Number;
        private var _rolled:Boolean = false;
        
        /**
         * Create a new AbstractHandle instance. 
         */
        public function AbstractHandle(context:ViewContext)
        {
            _context = context;
            _context.editor.addEventListener(EditorEvent.DISPLAY_SCALE_CHANGE, handleScaleChange, false, 0, true);
            addEventListener(MouseEvent.ROLL_OVER, addRollHighlight);
            addEventListener(MouseEvent.ROLL_OUT, removeRollHighlight);
            updateHandleSize();
            updateGraphics();
        }
        
        /**
         * Override this method to render the handle. 
         */
        protected function updateGraphics():void
        {
        }
        
        /**
         * Called to update the size of the handle if the scale changes. 
         */
        protected function updateHandleSize():void
        {
            // Compensate for zoom factor in view to keep handle sizes constant
            _handleSize = apparentSize / _context.info.displayScale;
        }
        
        /**
         * The calculated size of this handle base, for use in actual drawing. 
         */
        public function get handleSize():Number
        {
            return _handleSize;
        }

        /**
         * The ViewContext to which this handle belongs. 
         */
        protected function get context():ViewContext
        {
            return _context;
        } 

        /**
         * The position of this handle in document coordinates. 
         */
        public function get position():Point
        {
            return new Point(x, y);
        }
        
        public function set position(p:Point):void
        {
            x = p.x;
            y = p.y;
        }
        
        /**
         * Flag indicating that this handle is in a rollover state. 
         */
        public function get rolled():Boolean
        {
            return _rolled;
        }

        private function handleScaleChange(e:EditorEvent):void
        {
            updateHandleSize();
            updateGraphics();
        }
        
        protected function addRollHighlight(e:MouseEvent):void
        {
            _rolled = true;
            updateGraphics();
            e.stopPropagation();
        }
        
        protected function removeRollHighlight(e:MouseEvent):void
        {
            _rolled = false;
            updateGraphics();
            e.stopPropagation();
        }
    }
}