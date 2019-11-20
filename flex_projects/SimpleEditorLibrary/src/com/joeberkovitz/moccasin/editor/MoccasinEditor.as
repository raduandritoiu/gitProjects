package com.joeberkovitz.moccasin.editor
{
    import com.joeberkovitz.moccasin.controller.IMoccasinController;
    import com.joeberkovitz.moccasin.document.MoccasinDocument;
    import com.joeberkovitz.moccasin.event.EditorEvent;
    import com.joeberkovitz.moccasin.event.OperationFaultEvent;
    import com.joeberkovitz.moccasin.event.ProgressSourceEvent;
    import com.joeberkovitz.moccasin.service.IConfigurationService;
    import com.joeberkovitz.moccasin.service.IMoccasinDocumentService;
    import com.joeberkovitz.moccasin.service.IOperation;
    import com.joeberkovitz.moccasin.service.MoccasinDocumentData;
    import com.joeberkovitz.moccasin.view.IMoccasinView;
    import com.joeberkovitz.moccasin.view.ViewContext;
    import com.joeberkovitz.moccasin.view.ViewInfo;
    
    import flash.display.DisplayObject;
    import flash.display.Stage;
    import flash.events.Event;
    import flash.events.KeyboardEvent;
    import flash.printing.PrintJob;
    
    import mx.containers.Canvas;
    import mx.core.Application;
    import mx.core.UIComponent;
    import mx.managers.IFocusManagerComponent;
    import mx.managers.PopUpManager;
    
    import spark.components.Group;
    
    [Event(type="com.joeberkovitz.moccasin.event.EditorEvent",name="documentChanged")]
    [Event(type="com.joeberkovitz.moccasin.event.EditorEvent",name="documentLayoutChange")]
    [Event(type="com.joeberkovitz.moccasin.event.EditorEvent",name="displayScaleChange")]

    /**
     * MoccasinEditor is the top-level UI component in the Moccasin framework.  An application need only
     * include and initialize the editor properly in order to utilize Moccasin.  An instance of this object
     * contains:
     * 
     * <ul>
     *   <li>an instance of MoccasinDocument containing the current selection state and undo history
     *   <li>an MVC triad based on the root model object in that document
     *   <li>a family of superimposed layers for feedback and overlays on top of the base view of the model
     *   <li>service objects and document state for managing persistence
     * </ul>
     * 
     * Note that menu bars, tool bars, and so forth are completely separate from the editor.   
     */
    public class MoccasinEditor extends Group implements IFocusManagerComponent
    {
        /**
         * Flag indicating that the editor is completely loaded.  
         */
        [Bindable]
        public var complete:Boolean = false;
        
        /**
         * ViewInfo object representing the viewing state of this editor.
         */        
        [Bindable]
        public var viewInfo:ViewInfo = new ViewInfo();
        
        /**
         * Magnification scale in use for this editor's view. 
         */        
        [Bindable]
        public var viewScale:Number = 1;

        /**
         * Configuration service instance used by this editor. 
         */
        [Bindable]
        public var configurationService:IConfigurationService = null;
        
        /**
         * Document service used by this editor to load and save documents. 
         */        
        [Bindable]
        public var documentService:IMoccasinDocumentService = null;

        /**
         * Service-related document state retrieved from the service layer. 
         */        
        [Bindable]
        public var documentData:MoccasinDocumentData = null;
        
        protected var _document:MoccasinDocument;
        private var _controller:IMoccasinController;
        private var _documentView:IMoccasinView;
        private var _viewContext:ViewContext;

        // Current pointer tool to be used by ViewContext -- not in use at the moment
        // but will be handy for apps with notion of different cursor tools.      
        private var _pointerTool:String = ViewContext.SELECT_TOOL;
        
        /**
         * UIComponent containing all views of the document model, appropriately scaled. 
         */        
        public var documentLayer:UIComponent;
        
        /**
         * Flag indicating whether Flex component hierarchy should be used for document views.
         */
        public var flexDocumentView:Boolean = false;
        
        /**
         * Transparent layer on top of the documentLayer, scaled and offset identically.  Contains temporary
         * visual feedback objects.
         */        
        public var feedbackLayer:UIComponent;  // scaled feedback aligned with document view

        protected var viewLayer:Canvas;            // container to apply scaling and scroll offset to document and feedback layers
        private var overlayLayer:UIComponent;    // unscaled feedback for global overlays
        private var loadingPopup:LoadingPopup = null;

        public function MoccasinEditor()
        {
            super();
        }
        
        /**
         * Global controller instance for this application (although multiple controllers are allowed).
         */
        public function get controller():IMoccasinController
        {
            return _controller;
        }

        /**
         * Top level view of document's root model.
         */
        public function get documentView():IMoccasinView
        {
            return _documentView;
        }
        
        /**
         * The ViewContext shared by all views of model objects in this editor.
         */
        public function get viewContext():ViewContext
        {
            return _viewContext;
        }
        
        /**
         * Name of a cursor mode that affects how mouse gestures are interpreted. 
         */
        [Bindable]
        public function get pointerTool():String
        {
            return _pointerTool;
        } 
        
        public function set pointerTool(tool:String):void
        {
            _pointerTool = tool;
            if (_viewContext != null)
            {
                _viewContext.pointerTool = tool;
            }
        }
        
        /**
         * Initialize this editor. 
         * 
         */
        public function initializeEditor():void
        {
            _controller = createController();
            var keyMediator:EditorKeyMediator = createKeyMediator(_controller);
            documentService = createDocumentService();

            // aggressively funnel keystrokes into our key mediator
            Application.application.addEventListener(KeyboardEvent.KEY_DOWN, keyMediator.handleKey);
            addEventListener(KeyboardEvent.KEY_DOWN, keyMediator.handleKey);

            viewLayer.scaleX = viewLayer.scaleY = viewScale;
            
            setFocus();
            
            if (configurationService.documentUri != null)
            {
                loadDocument(configurationService.documentUri);
            }
        }
        
        /**
         * Initialize the child components of this editor.
         */
        override protected function createChildren():void
        {
            super.createChildren();
            
            viewLayer = new Canvas();
			addElement(viewLayer);
//			addChild(viewLayer);
            
            documentLayer = createDocumentLayer();
            viewLayer.addChild(documentLayer);

            feedbackLayer = new UIComponent();
            viewLayer.addChild(feedbackLayer);
            
            overlayLayer = new UIComponent();
			addElement(overlayLayer);
//			addChild(overlayLayer);
        }
        
        /**
         * Abstract factory method to create this application's controller instance.
         */
        protected function createController():IMoccasinController
        {
            throw new Error("createController() must be overridden");
        }
        
        /**
         * Factory method to create a document layer to contain the top level IMoccasinView. 
         */
        protected function createDocumentLayer():UIComponent
        {
            if (flexDocumentView)
            {
                var canvas:Canvas = new Canvas();
                canvas.percentWidth = canvas.percentHeight = 100;
                canvas.clipContent = false;
                return canvas;
            }
            else
            {
                return new UIComponent();
            }
        }
        
        /**
         * Abstract factory method to create this application's top level view.
         */
        protected function createDocumentView(context:ViewContext):IMoccasinView
        {
            throw new Error("createDocumentView() must be overridden");
        } 
        
        /**
         * Abstract factory method to create this application's document service
         */
        protected function createDocumentService():IMoccasinDocumentService
        {
            throw new Error("createDocumentService() must be overridden");
        } 
        
        /**
         * Abstract factory method to create this application's keystroke mediator.
         */
        protected function createKeyMediator(controller:IMoccasinController):EditorKeyMediator
        {
            return new EditorKeyMediator(controller, this);
        }
        
        /**
         * Abstract factory method to create this application's view context
         */
        protected function createViewContext(info:ViewInfo, controller:IMoccasinController, editor:MoccasinEditor, stage:Stage):ViewContext
        {
            return new ViewContext(info, controller, editor, stage);
        }
        

        /**
         * Override default key down handling in Container that would affect scroll bar position, etc.  
         */
        override protected function keyDownHandler(e:KeyboardEvent):void
        {
        }

        private function adjustCursor(ctrlKey:Boolean):void
        {
            // Placeholder for code to dynamically adjust cursor appearance
            // based on mouse position.
        }
        
        /**
         * Load a document, given its ID. 
         */
        public function loadDocument(documentId:String):void
        {
            var operation:IOperation = documentService.loadDocument(documentId);
            operation.addEventListener(Event.COMPLETE, documentLoaded);
            operation.addEventListener(OperationFaultEvent.FAULT, documentFault);
            operation.addEventListener(ProgressSourceEvent.PROGRESS_START, handleProgressStart);
            operation.displayName = "Loading document..."
            operation.execute();
        }
        
        /**
         * Handler for the completion of a document's load operation.
         */
        protected function documentLoaded(e:Event):void
        {
            loadFromDocumentData(MoccasinDocumentData(IOperation(e.target).result));
        }
        
        /**
         * Load the contents of this editor from the given MoccasinDocumentData object. 
         */
        protected function loadFromDocumentData(data:MoccasinDocumentData):void
        {
            documentData = data;
            moccasinDocument = new MoccasinDocument(documentData.root);
        }
        
        /**
         * The MoccasinDocument being viewed by this editor. 
         */
        public function get moccasinDocument():MoccasinDocument
        {
            return _document;
        }
        
        public function set moccasinDocument(d:MoccasinDocument):void
        { 
            _document = d;
            _controller.document = _document;
            updateLayout();
            
            dispatchEvent(new EditorEvent(EditorEvent.DOCUMENT_CHANGED));
        }
        
        /**
         * Save this document, using the same ID with which it was loaded.
         */
        public function saveDocument():void
        {
            documentData.root = _document.root;
            
            var operation:IOperation = documentService.saveDocument(documentData);
            operation.addEventListener(Event.COMPLETE, documentSaved);
            operation.addEventListener(OperationFaultEvent.FAULT, documentFault);
            operation.addEventListener(ProgressSourceEvent.PROGRESS_START, handleProgressStart);
            operation.displayName = "Saving document..."
            operation.execute();
        }
    
        /**
         * Handler for the completion of a document's save operation.
         */
        protected function documentSaved(e:Event):void
        {
        }
        
        /**
         * Handler for faults occurring during the save or load of a document.
         */
        protected function documentFault(e:OperationFaultEvent):void
        {
        }
        
        /**
         * Adjust the document's current layout and recreate its view from scratch.
         */
        public function updateLayout():void
        {
            validateDocumentLayout();
        }
        
        /**
         * Print the current document on the printer. 
         */
        public function printDocument():void
        {
        }
        
        /**
         * Obtain an array of PageView instances laid out correctly for printing with respect
         * to some particular PrintJob instance.  
         */
        private function getPageViews(printJob:PrintJob):Array
        {
            return [];
        }
        
        /**
         * Recreate the current top-level view and position it within the editor as meeded/ 
         * 
         */
        private function validateDocumentLayout():void
        {
            while (documentLayer.numChildren > 0)
                documentLayer.removeChildAt(0);
                
            viewInfo.displayScale = viewScale;
            _viewContext = createViewContext(viewInfo, _controller, this, stage);
            _viewContext.pointerTool = _pointerTool;
            _documentView = createDocumentView(_viewContext);
            documentLayer.addChild(_documentView as DisplayObject);
            updateDimensions();

            dispatchEvent(new EditorEvent(EditorEvent.DOCUMENT_LAYOUT_CHANGE));
        }
        
        private function updateDimensions():void
        {
            documentLayer.height = _documentView.height;
            documentLayer.width = _documentView.width;
        }

        private function handleProgressStart(e:ProgressSourceEvent):void
        {
            if (loadingPopup == null)
            {
                loadingPopup = PopUpManager.createPopUp(this, LoadingPopup, true) as LoadingPopup;
                PopUpManager.centerPopUp(loadingPopup);
            }
            loadingPopup.addEventListener(Event.REMOVED, handleLoadingPopupRemoved);
            loadingPopup.addProgressSource(e.source, e.sourceName);
        }
        
        private function handleLoadingPopupRemoved(e:Event):void
        {
            loadingPopup = null;
        }
        
        /**
         * Override to force the document layer to a vertically centered position if it occupies
         * less than the full visible height of this component.  
         */
        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
        {
            super.updateDisplayList(unscaledWidth, unscaledHeight);
            
            if (_document == null)
            {
                return;
            }
        }        
        
        /**
         * Set a view offset in scaled document coordinates. 
         */
        public function setViewOffset(x:Number, y:Number):void
        {
            documentLayer.x = feedbackLayer.x = x;
            documentLayer.y = feedbackLayer.y = y;
        }
        
        /**
         * Set the display scale and adjust the scrollbars as needed. 
         */
        public function setScale(s:Number):void
        {
            var oldScale:Number = viewScale;
            viewScale = s;
            var hScroll:Number = horizontalScrollPosition;
            var vScroll:Number = verticalScrollPosition;
            viewLayer.scaleX = viewLayer.scaleY = viewScale;
            
            viewInfo.displayScale = viewScale;

            var factor:Number = viewScale / oldScale;
            horizontalScrollPosition = (hScroll + width/2) * factor - width/2;
            verticalScrollPosition = (vScroll + height/2) * factor - height/2;
            
            dispatchEvent(new EditorEvent(EditorEvent.DISPLAY_SCALE_CHANGE));
        }
    }
}
