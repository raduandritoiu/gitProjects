package com.joeberkovitz.moccasin.event
{
    import com.joeberkovitz.moccasin.model.MoccasinModel;
    
    import flash.events.Event;
    
    /**
     * Event representing a structural change to a model's parent/child relationships.
     * Dispatched by MoccasinModel in response to model topology changes.
     */
    public class ModelEvent extends Event
    {
        public static const MODEL_CHANGE:String = "modelChange";

        public static const ADD_CHILD_MODEL:String = "addChildModel";
        public static const REMOVING_CHILD_MODEL:String = "removingChildModel";
        public static const REMOVE_CHILD_MODEL:String = "removeChildModel";
        
        public var kind:String;
        public var parent:MoccasinModel;
        public var child:MoccasinModel;
        public var index:int;
        
        public function ModelEvent(type:String, kind:String, parent:MoccasinModel, child:MoccasinModel, index:int)
        {
            super(type);
            this.kind = kind;
            this.parent = parent;
            this.child = child;
            this.index = index;
        }
        
        override public function clone():Event
        {
            return new ModelEvent(type, kind, parent, child, index);
        }
    }
}
