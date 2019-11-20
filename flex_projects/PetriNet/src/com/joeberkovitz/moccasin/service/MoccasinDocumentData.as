package com.joeberkovitz.moccasin.service
{
    import com.joeberkovitz.moccasin.model.ModelRoot;
    
    /**
     * Document-level model object representing all persisted data concerning a document, including the
     * documentitself. 
     */

    [Bindable]
    public class MoccasinDocumentData
    {
        public var root:ModelRoot;
        public var documentId:String;
        public var saveDocumentURI:String;
        public var documentDescriptor:MoccasinDocumentDescriptor = null;
        
        public function MoccasinDocumentData(root:ModelRoot, documentId:String)
        {
            this.root = root;
            this.documentId = documentId;
        }
    }
}
