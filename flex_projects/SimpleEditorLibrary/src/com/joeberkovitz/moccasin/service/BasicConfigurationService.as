package com.joeberkovitz.moccasin.service
{
    import mx.core.Application;

    /**
     * A bare-bones implementation of IConfigurationService that gets the job done in a simple world. 
     */
    public class BasicConfigurationService implements IConfigurationService
    {
        private var _parameters:Object;
        private var _documentUri:String;
        
        public function BasicConfigurationService(app:Application, documentUri:String)
        {
            if (app != null)
            {
                _parameters = app.parameters;
            }
            else
            {
                _parameters = { hostPrefix: "", filePrefix: "" };
            }
            _documentUri = documentUri;
        }
        
        public function get documentUri():String
        {
            return _documentUri;
        }

        public function get assetLocation():String
        {
            return _parameters["hostPrefix"] + _parameters["filePrefix"];
        }
        
        public function get viewScale():Number
        {
            return 1;
        }
    }
}
