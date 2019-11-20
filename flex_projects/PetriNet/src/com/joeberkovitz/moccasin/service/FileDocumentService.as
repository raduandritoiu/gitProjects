package com.joeberkovitz.moccasin.service
{
    import com.joeberkovitz.moccasin.persistence.IDocumentDecoder;
    import com.joeberkovitz.moccasin.persistence.IDocumentEncoder;
    
    /**
     * Simple implementation of IMoccasinDocumentService that persists data using the filesystem.
     */
    public class FileDocumentService implements IMoccasinDocumentService
    {
        private var _decoder:IDocumentDecoder;
        private var _encoder:IDocumentEncoder;
        
        public function FileDocumentService(decoder:IDocumentDecoder, encoder:IDocumentEncoder)
        {
            _decoder = decoder;
            _encoder = encoder;
        }

        /**
         * @inheritDoc 
         */
        public function loadDocument(documentUri:String):IOperation
        {
            var xmlHttpOp:XmlHttpOperation = new XmlHttpOperation(documentUri);
            return new BasicDocumentFilterOperation(xmlHttpOp, documentUri, _decoder);
        }
        
        /**
         * @inheritDoc 
         */
        public function saveDocument(documentData:MoccasinDocumentData):IOperation
        {
            return new XmlFileWriteOperation(documentData.documentId, _encoder.encodeDocument(documentData.root));
        }

        /**
         * @inheritDoc 
         */
        public function getAssetURL(assetUri:String):String
        {
            return assetUri;
        }
        
    }
}
