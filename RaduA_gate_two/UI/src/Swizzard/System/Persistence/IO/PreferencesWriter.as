package Swizzard.System.Persistence.IO
{
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	
	import Swizzard.System.Preferences.UserPreferences;
	import Swizzard.System.Utils.VersionUtil;
	
	import utils.LogUtils;
	
	
	public class PreferencesWriter
	{
		public function PreferencesWriter()
		{}
		
		
		public static function writePreferences(preferences:UserPreferences, prefFile:File):void {
			var stream:FileStream	= new FileStream();
			
			try {
				stream.open(prefFile, FileMode.WRITE);
				stream.writeUTFBytes(VersionUtil.USE_VERS);
				stream.writeUTF(VersionUtil.CURRENT_VERSION());
				stream.writeObject(preferences);
			}
			catch (err:Error) {
				LogUtils.Debug(err);
			}
			finally {
				stream.close();
			}
		}
	}
}