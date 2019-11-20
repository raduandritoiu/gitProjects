package com.joeberkovitz.moccasin.controller
{
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    import com.joeberkovitz.moccasin.view.IMoccasinView;
    
    import flash.events.MouseEvent;
    import flash.utils.getTimer;

    /**
     * This Mediator-type object takes on responsibility for all mouse interaction
     * involving click-based selection.
     */
    public class SelectionMediator implements IMoccasinMediator
    {
        private var _view:IMoccasinView;

        private var _lastClickMillis:Number = 0;
        
        public function SelectionMediator()
        {
        }

        public function handleViewEvents(view:IMoccasinView):void
        {
            _view = view;
                  
            _view.addEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown);
        }
        
        private function handleMouseDown(e:MouseEvent):void
        {
            var now:Number = getTimer();
            var doubleClick:Boolean = (now - _lastClickMillis) <= _view.context.info.doubleClickMillis;
            _lastClickMillis = now;

            if (!_view.model.enabled)
            {
                // some models don't permit interaction
                return;
            }
            
            var model:MoccasinModel = _view.model;

            // TODO: add hook for appropriate drag mediators, e.g.
            //
            //      new XYZDragMediator(_view.context, model, "XYZ Operation").handleMouseDown(e);

            if (e.ctrlKey)
            {
                // Ctrl-click extends an object selection to include the clicked model.
                _view.context.document.undoHistory.openGroup("Select Object");
                _view.context.controller.modifySelection(model);
            }
            else if (doubleClick)
            {
                // TODO: add hook for editing
            }
            else if (_view.context.document.selection == null
                     || !_view.context.document.selection.includes(model))
            {
                // Regular click: select the single model that was just clicked
                //
                _view.context.document.undoHistory.openGroup("Select Object");
                _view.context.controller.selectSingleModel(model);
            }
            e.stopPropagation();
            
            _view.context.editor.setFocus();
        }
    }
}
