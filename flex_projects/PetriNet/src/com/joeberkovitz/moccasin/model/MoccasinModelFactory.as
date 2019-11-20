package com.joeberkovitz.moccasin.model
{
    /**
     * <p>A MoccasinModelFactory is an object that can turn a "naked model" into a subclass of
     * MoccasinModel.  Alternative MoccasinModelFactory subclasses may be created and plugged into
     * the system.  This is the way to control what is actually instantiated by the MoccasinModel.forValue()
     * function.
     */
    public class MoccasinModelFactory
    {
        /** The factory used to create new MoccasinModels. */
        public static var instance:MoccasinModelFactory = new MoccasinModelFactory();
        
        /**
         * Create a MoccasinModel for the given value object.  Override this function
         * in subclasses to create an alternate factory implementation.
         */
        public function createModel(value:Object):MoccasinModel
        {
            return new MoccasinModel(value);
        }
    }
}
