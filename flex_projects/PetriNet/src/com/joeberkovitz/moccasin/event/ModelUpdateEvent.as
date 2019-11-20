package com.joeberkovitz.moccasin.event
{
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    
    import flash.events.Event;
    
    /**
     * Event representing a property change on some value object associated with a MoccasinModel.
     * Dispatched by MoccasinModel automatically.
     */
    public class ModelUpdateEvent extends Event
    {
        public static const MODEL_UPDATE:String = "modelUpdate";

        public var property:Object;
        public var oldValue:Object;
        public var newValue:Object;
        public var source:MoccasinModel;
        
        public function ModelUpdateEvent(type:String, property:Object, oldValue:Object, newValue:Object, source:MoccasinModel)
        {
            super(type);
            this.property = property;
            this.oldValue = oldValue;
            this.newValue = newValue;
            this.source = source;
        }
        
        override public function clone():Event
        {
            return new ModelUpdateEvent(type, property, oldValue, newValue, source);
        }
    }
}
