package com.joeberkovitz.moccasin.service
{
    import com.joeberkovitz.moccasin.model.ModelRoot;
    
    /**
     * Value object describing a document in terms of its displayable or indexable content on the website. 
     */
    [Bindable]
    public class MoccasinDocumentDescriptor
    {
        public var title:String = "";
        public var description:String = "";
        public var tags:String = "";
        
        public function MoccasinDocumentDescriptor()
        {
        }
    }
}
