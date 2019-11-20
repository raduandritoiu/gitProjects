package com.joeberkovitz.moccasin.view
{
    import com.joeberkovitz.moccasin.controller.IMoccasinController;
    import com.joeberkovitz.moccasin.document.MoccasinDocument;
    import com.joeberkovitz.moccasin.editor.MoccasinEditor;
    
    import flash.display.Bitmap;
    import flash.display.BitmapData;
    import flash.display.Stage;
    import flash.events.EventDispatcher;
    
    /**
     * A ViewContext houses data and references to objects that must be accessible
     * to all views relating to some MoccasinDocument. 
     */
    public class ViewContext extends EventDispatcher
    {
        public var info:ViewInfo;
        public var controller:IMoccasinController;
        public var editor:MoccasinEditor;
        public var stage:Stage;

        public static const SELECT_TOOL:String = "select";
        public var pointerTool:String = SELECT_TOOL; 
        
        public function ViewContext(info:ViewInfo,
                                    controller:IMoccasinController,
                                    editor:MoccasinEditor,
                                    stage:Stage)
        {
            this.info = info;
            this.controller = controller;
            this.editor = editor;
            this.stage = stage;
        }
        
        public function get document():MoccasinDocument
        {
            return (controller != null) ? controller.document : null;
        }
    }
}
