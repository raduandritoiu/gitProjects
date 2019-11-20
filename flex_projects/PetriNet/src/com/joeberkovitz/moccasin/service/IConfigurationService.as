package com.joeberkovitz.moccasin.service
{
    /**
     * Interface definition for a service that provides configuration. session and environment information. 
     */
    public interface IConfigurationService
    {
        /**
         * Obtain a URL prefix for loading fixed application assets such as its style SWF,
         * glyph foundries and instrument factories.
         */
        function get assetLocation():String;
     
        /**
         * The URI of the document to be initially loaded. 
         */
        function get documentUri():String;
        
        /**
         * The initial view scale to be used by the editor. 
         */
        function get viewScale():Number;
        
    }
}
