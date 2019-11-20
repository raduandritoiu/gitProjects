package com.joeberkovitz.moccasin.controller
{
    import com.joeberkovitz.moccasin.view.ViewContext;
    
    import flash.events.MouseEvent;
    import flash.geom.Point;

    /**
     * An abstract mediator handling the body of a drag gesture, which calls various
     * abstract methods to handle events within the drag.
     */    
    public class DragMediator
    {
        private var _context:ViewContext;
        private var _dragPoint:Point;
        private var _dragStarted:Boolean = false;
        
        /**
         * A distance that the mouse must move from the starting point to be considered an actual drag gesture,
         * as opposed to a click. 
         */
        public var minimumDrag:Number = 3;
        
        public function DragMediator(context:ViewContext)
        {
            _context = context;
        }
        
        /**
         * Handle the event at the start of the drag. 
         * All coordinates used are Stage coordinates, so it doesn't matter what the mouse event's
         * target is.
         */
        public function handleMouseDown(e:MouseEvent):void
        {
            _dragStarted = false;
            
            _dragPoint = new Point(_context.stage.mouseX, _context.stage.mouseY);
            _context.stage.addEventListener(MouseEvent.MOUSE_MOVE, handleMouseMove);
            _context.stage.addEventListener(MouseEvent.MOUSE_UP, handleMouseUp);
            e.stopPropagation();
        }

        protected function get context():ViewContext
        {
            return _context;
        }        
        
        /**
         * Point at which the drag or click began, in stage coordinates.
         */
        protected function get dragPoint():Point
        {
            return _dragPoint;
        }
        
        /**
         * The vector from the start of the drag to its endpoint in stage units. 
         */        
        protected function get dragDelta():Point
        {
            return new Point(_context.stage.mouseX, _context.stage.mouseY).subtract(_dragPoint);
        }
        
        /**
         * The vector from the start of the drag to its endpoint in documentViewUnits. 
         */        
        protected function get documentDragDelta():Point
        {
            return new Point((_context.stage.mouseX - _dragPoint.x) / context.info.displayScale,
                             (_context.stage.mouseY - _dragPoint.y) / context.info.displayScale);
        }
        
        /**
         * Handle mouse motion during the drag by initiating it if necessary when the mouse
         * exceeds the distance threshold, and then calling the move-handling function. 
         */
        protected function handleMouseMove(e:MouseEvent):void
        {
            if (!_dragStarted
                && e.buttonDown
                && _dragPoint != null
                && (Math.abs(_context.stage.mouseX -_dragPoint.x) >= minimumDrag
                    || Math.abs(_context.stage.mouseY -_dragPoint.y) >= minimumDrag))
            {
                _dragStarted = true;
                handleDragStart(e);
            }
            
            if (_dragStarted)
            {
                handleDragMove(e);
            }
//            e.stopPropagation();
        }
        
        /**
         * Handle the end of the drag or click gesture. 
         */
        protected function handleMouseUp(e:MouseEvent):void
        {
            if (_dragStarted)
            {
                handleDragMove(e);
                handleDragEnd(e);
            }
            else
            {
                handleClick(e);
            }
            _context.stage.removeEventListener(MouseEvent.MOUSE_MOVE, handleMouseMove);
            _context.stage.removeEventListener(MouseEvent.MOUSE_UP, handleMouseUp);
//            e.stopPropagation();
            
            // force focus back to the editor
            _context.editor.setFocus();
        }
        
        ///////////////////////
        // ABSTRACT METHODS
        ///////////////////////

        /**
         * Handle the start of a drag gesture.
         */
        protected function handleDragStart(e:MouseEvent):void
        {
        }

        /**
         * Handle an intermediate position of a drag gesture. 
         */
        protected function handleDragMove(e:MouseEvent):void
        {
        }

        /**
         * Handle the end of a drag gesture. 
         */
        protected function handleDragEnd(e:MouseEvent):void
        {
        }
        
        /**
         * Handle the termination of a gesture during which the mouse effectively did not move. 
         */
        protected function handleClick(e:MouseEvent):void
        {
        }
    }
}
