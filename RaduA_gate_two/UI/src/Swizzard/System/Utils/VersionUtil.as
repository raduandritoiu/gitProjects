package Swizzard.System.Utils
{
	import flash.desktop.NativeApplication;

	/**
	 * Class that helps with versioning when sving/loading files
	 */
	public class VersionUtil
	{
		
		/** 
		 * Marker stating the saved file contains versioning info.
		 * Every saved file will have at the beginning this CONSTANT string followed by the version
		 * this is done because the first 1.*.* files did not save this info, and we need a constant
		 * to verify the existance of the version 
		 * Stands for SWizzard Versioning
		 */
		public static const USE_VERS:String = "SWV";
		
		/**
		 * the current version used for loading the object
		 */
		public static var LOADING_VERSION:String;
		
		/**
		 * The current application version, retrieved from SimpleSelect-app.xml
		 */
		private static var _currentVersion:String;
		public static function CURRENT_VERSION():String {
			if (_currentVersion == null) {
				var desc:XML 	= NativeApplication.nativeApplication.applicationDescriptor;
				var ns:Namespace = desc.namespaceDeclarations()[0];
				_currentVersion = desc.ns::versionNumber.toString();	
			}
			return _currentVersion;
		}
		
		
		/** 
		 * Compares two version numbers and returns -1, 0 or 1.
		 *  -1 - vers1 is higher
		 *   0 - the two versions are equal
		 *   1 - vers2 is higher
		 * Do not test for null input on purpose; because there needs to be valid input.
		 */
		public static function Compare(vers1:String, vers2:String):int {
			// split the versions into part numbers
			var va1:Array = vers1.split(".");
			var va2:Array = vers2.split(".");
			
			// compare parts of the versioning string
			for (var i:int = 0; i < va1.length; i++) {
				// "*" is equivalend to any version part number
				if (va1[i] == "*") continue;
				
				// the version part number
				var nr1:Number = parseFloat(va1[i]);
				if (isNaN(nr1)) nr1 = 0;
				
				// we have reached the end of the second version
				if (i >= va2.length) return -1;
				
				// "*" is equivalend to any version part number
				if (va2[i] == "*") continue;
				
				// the version part number
				var nr2:Number = parseFloat(va2[i]);
				if (isNaN(nr2)) nr2 = 0;
				
				// first version is greater
				if (nr1 > nr2) return -1;
				
				// second version is greater
				if (nr1 < nr2) return 1;
			}
			
			// we have reached the end of the second version
			if (i < va2.length) return 1;
			
			// the versions are equal
			return 0;
		}
	}
}