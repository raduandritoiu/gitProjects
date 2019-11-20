package com.joeberkovitz.moccasin.service
{
    import com.joeberkovitz.moccasin.persistence.IDocumentDecoder;
    import com.joeberkovitz.moccasin.persistence.IDocumentEncoder;
    
    import flash.net.URLRequestMethod;
    
    /**
     * Simple implementation of IMoccasinDocumentService that persists data using XML/HTTP.
     * 
     * <p>Documents are posted with the XML body of the document either as a POST document (the default)
     * or as a POST request parameter (if documentPostVariable is set to that parameter's name). 
     */
    public class HttpDocumentService implements IMoccasinDocumentService
    {
        /** Overall host prefix for all service traffic. */
        [Bindable]
        public var hostPrefix:String = "";
        
        /** location URI for REST-style load service. */
        [Bindable]
        public var loadLocation:String = "";
        
        /** location URI for REST-style save service. */
        [Bindable]
        public var saveLocation:String = "";
        
        /** optional variable determining a POST request parameter for a saved document's XML. */
        [Bindable]
        public var documentPostVariable:String = null;
        
        private var _decoder:IDocumentDecoder;
        private var _encoder:IDocumentEncoder;
        
        public function HttpDocumentService(decoder:IDocumentDecoder, encoder:IDocumentEncoder)
        {
            _decoder = decoder;
            _encoder = encoder;
        }

        /**
         * @inheritDoc 
         */
        public function loadDocument(documentUri:String):IOperation
        {
            var xmlHttpOp:XmlHttpOperation = new XmlHttpOperation(hostPrefix + loadLocation + documentUri);
            return new BasicDocumentFilterOperation(xmlHttpOp, documentUri, _decoder);
        }
        
        /**
         * @inheritDoc 
         */
        public function saveDocument(documentData:MoccasinDocumentData):IOperation
        {
            var documentXml:XML = _encoder.encodeDocument(documentData.root) as XML;
            if (documentPostVariable != null)
            {
                var data:Object = {};
                data[documentPostVariable] = documentXml;
                var xmlHttpOp:XmlHttpOperation = new XmlHttpOperation(hostPrefix + saveLocation + documentData.documentId, data);
                xmlHttpOp.method = URLRequestMethod.POST;
                return xmlHttpOp;
            }
            else
            {
                return new XmlHttpOperation(hostPrefix + saveLocation + documentData.documentId, documentXml);
            }
        }
        
        /**
         * @inheritDoc 
         */        
        public function getAssetURL(assetUri:String):String
        {
            return hostPrefix + loadLocation + assetUri;
        }
    }
}
