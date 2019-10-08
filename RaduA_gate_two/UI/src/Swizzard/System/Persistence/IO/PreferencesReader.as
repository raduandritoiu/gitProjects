package Swizzard.System.Persistence.IO
{
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	
	import Swizzard.System.Preferences.UserPreferences;
	import Swizzard.System.Utils.VersionUtil;
	
	import utils.LogUtils;

	public class PreferencesReader
	{
		public function PreferencesReader()
		{}
		
		
		public static function readPreferences(prefFile:File):UserPreferences {
			var preferences:UserPreferences = null;
				
			try {
				if (prefFile.exists) {
					var stream:FileStream = new FileStream();
					stream.open(prefFile, FileMode.READ);
					
					// get the version this file was saved with
					var savedVersion:String;
					var hasVersioning:String = stream.readUTFBytes(3);
					if (hasVersioning != VersionUtil.USE_VERS) {
						// if it doesn't have version info means it was pre 2.0
						// also reset the reading position in the stream
						savedVersion = "1.999.999";
						stream.position = 0;
					}
					else {
						savedVersion = stream.readUTF();
					}
					
					// set loading version
					VersionUtil.LOADING_VERSION = savedVersion;
					
					// if this is a 1.*.* version file
					if (VersionUtil.Compare(savedVersion, "2.0.0") == 1) {
						preferences = stream.readObject() as UserPreferences;
					}
					else {
						preferences = stream.readObject() as UserPreferences;
					}
					
					// reset loading version
					VersionUtil.LOADING_VERSION = VersionUtil.CURRENT_VERSION();
					
					stream.close();
				}
			}
			catch (err:Error) {
				// Error Reading File
				LogUtils.Debug(err);
			}
			
			return preferences;
		}
	}
}