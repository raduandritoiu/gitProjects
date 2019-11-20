package com.joeberkovitz.moccasin.event
{
    import com.joeberkovitz.moccasin.document.MoccasinDocument;
    
    import flash.events.Event;
    
    /**
     * A property change event with respect to a MoccasinDocument. 
     * @author joeb
     * 
     */
    public class DocumentUpdateEvent extends Event
    {
        public static const DOCUMENT_UPDATE:String = "documentUpdate";

        public var property:Object;
        public var oldValue:Object;
        public var newValue:Object;
        public var document:MoccasinDocument;
        
        public function DocumentUpdateEvent(type:String, property:Object, oldValue:Object, newValue:Object, document:MoccasinDocument)
        {
            super(type);
            this.property = property;
            this.oldValue = oldValue;
            this.newValue = newValue;
            this.document = document;
        }
        
        override public function clone():Event
        {
            return new DocumentUpdateEvent(type, property, oldValue, newValue, document);
        }
    }
}
