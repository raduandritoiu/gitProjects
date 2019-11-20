package com.joeberkovitz.moccasin.document
{
    import mx.utils.ObjectUtil;
    
    /**
     * A clipboard implementation that consists of a simple flat collection of model objects. 
     */
    public class CollectionClipboard implements IClipboard
    {
        private var _models:Array;
        
        public function CollectionClipboard(models:Array)
        {
            _models = models;
        }
        
        public function get models():Array
        {
            return _models;
        }
    }
}