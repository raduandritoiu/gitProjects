package com.joeberkovitz.moccasin.view
{
    import com.joeberkovitz.moccasin.model.ModelRoot;
    
    /**
     * A ViewInfo encapsulates all information that globally drives presentation.  It is available
     * to views via their owning ViewContext.
     */
    [Bindable]
    public class ViewInfo
    {
        /** Color used for providing feedback selection */
        public var feedbackColor:uint = 0x666666;
        
        /** Array of selection colors for various purposes. */
        public var selectionColors:Array = [0x3333FF, 0x88CC88, 0xCCCC11, 0x11CCCC];
        
        /** maximum delay between clicks to be considered a double click */
        public var doubleClickMillis:uint = 300;
        
        /** scale at which display is currently being rendered */
        public var displayScale:Number = 1.0;

        /** Flag indicating that this view is intended for printing -- gates display of some objects. */
        public var printView:Boolean = false;
        
        /** Flag indicating that this view is part of an automated test suite with screen captures. */
        public var testView:Boolean = false;
        
        public function ViewInfo()
        {
            super();
        }
        
        public function clone():ViewInfo
        {
            var info:ViewInfo = new ViewInfo();
            info.feedbackColor = feedbackColor;
            info.selectionColors = selectionColors.slice();
            info.doubleClickMillis = doubleClickMillis;
            info.displayScale = displayScale;
            info.printView = printView;
            info.testView = testView;
            return info;
        }

    }
}
