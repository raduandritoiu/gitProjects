package com.joeberkovitz.moccasin.persistence
{
    import com.joeberkovitz.moccasin.model.ModelRoot;
    
    /**
     * Interface implemented by service-layer helper objects that convert from a root model object
     * into a data type that can be passed to a service operation.
     */
    public interface IDocumentEncoder
    {
        function encodeDocument(root:ModelRoot):*;
    }
}
