package com.joeberkovitz.moccasin.service
{
    import com.joeberkovitz.moccasin.model.ModelRoot;
    
    /**
     * Interface definition for a service that handles the loading and saving of documents. 
     */
    public interface IMoccasinDocumentService
    {
        /**
         * Return an operation that will load a document from the given documentId when executed.
         * The result of the operation, when completed, is a MoccasinDocumentData object which includes
         * the document model along with other descriptive information.
         * 
         * @param documentUri the URI of a document to be loaded
         */
        function loadDocument(documentUri:String):IOperation;
        
        /**
         * Return an operation that will save a document from the given data.  The result of
         * this operation, when completed, is a new MoccasinDocumentData instance that may contain additional
         * descriptive information about the document such as its ID.
         *  
         * @param documentData a MoccasinDocumentData object describing a persisted document.
         */
        function saveDocument(documentData:MoccasinDocumentData):IOperation;

        /**
         * Return a URL that can load some asset from the given assetUri when executed.
         * 
         * @param assetUri the relative URI of an asset to be loaded
         * @return a physical URL that can be loaded to retrieve this asset
         */
        function getAssetURL(assetUri:String):String;
    }
}
