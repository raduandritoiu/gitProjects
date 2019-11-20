package com.joeberkovitz.moccasin.persistence
{
    import com.joeberkovitz.moccasin.model.ModelRoot;
    
    /**
     * Interface implemented by service-layer helper objects that convert from a service operation's
     * result into a root model object. 
     */
    public interface IDocumentDecoder
    {
        function decodeDocument(data:*):ModelRoot
    }
}
