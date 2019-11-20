package com.joeberkovitz.moccasin.service
{
    import com.joeberkovitz.moccasin.model.ModelRoot;
    import com.joeberkovitz.moccasin.persistence.IDocumentDecoder;
    
    [Event(name="complete",type="flash.events.Event")]
    [Event(name="fault",type="com.joeberkovitz.moccasin.event.OperationFaultEvent")]

    /**
     * FilterOperation subclass that wraps some other operation which loads the information
     * in a MoccasinDocumentData.  This class assumes that the wrapped operation's result is the encoded form
     * of the document. 
     */
    public class BasicDocumentFilterOperation extends FilterOperation
    {
        public var documentId:String;
        public var decoder:IDocumentDecoder;
        
        public function BasicDocumentFilterOperation(operation:IOperation, documentUri:String, decoder:IDocumentDecoder)
        {
            super(operation);
            this.documentId = documentUri; // in this basic implementation, the URI is always the id
            this.decoder = decoder;
        }

        /**
         * On success, return the result of this operation.
         */
        override public function get result():*
        {
            var root:ModelRoot = decoder.decodeDocument(operation.result);
            var data:MoccasinDocumentData = new MoccasinDocumentData(root, documentId);
            data.documentDescriptor = new MoccasinDocumentDescriptor();
            return data;
        }
   }
}
