package Swizzard.System.Preferences.proxies
{
	import flash.desktop.NativeApplication;
	import flash.display.NativeWindowDisplayState;
	import flash.filesystem.File;
	
	import mx.core.FlexGlobals;
	
	import Swizzard.System.Persistence.IO.PreferencesReader;
	import Swizzard.System.Persistence.IO.PreferencesWriter;
	import Swizzard.System.Preferences.UserPreferences;
	import Swizzard.System.Utils.VersionUtil;
	
	import org.puremvc.as3.patterns.proxy.Proxy;
	
	
	public class UserPreferencesProxy extends Proxy
	{
		public static const NAME:String	 = "UserPreferenesProxy";
		
		private static const prefFile:File = File.applicationStorageDirectory.resolvePath("settings.vsst");
		
		
		private var _preferences:UserPreferences;
		
		
		public function UserPreferencesProxy(data:Object=null) {
			super(NAME, data);
		}
		
		
		public function get preferences():UserPreferences {
			return _preferences;
		}
		
		
		override public function onRegister():void {
			var currentAppVersion:String = VersionUtil.CURRENT_VERSION();
			
			// read the preferences from fie system 
			_preferences = PreferencesReader.readPreferences(prefFile);
			
			// initialize some of the components
			if (preferences) {
				//set the nativeWindow back to the saved position
				if (preferences.nativeWindowDisplayState == NativeWindowDisplayState.MAXIMIZED) {
					FlexGlobals.topLevelApplication.windowControls.setMaximize(true); //This line is EPIC.
				} 
				else {
					if (!isNaN(preferences.nativeWindowX))
						NativeApplication.nativeApplication.openedWindows[0].x = preferences.nativeWindowX;
					
					if (!isNaN(preferences.nativeWindowY))
						NativeApplication.nativeApplication.openedWindows[0].y = preferences.nativeWindowY;
					
					if (!isNaN(preferences.nativeWindowHeight))
						NativeApplication.nativeApplication.openedWindows[0].width = preferences.nativeWindowWidth;
					
					if (!isNaN(preferences.nativeWindowWidth))
						NativeApplication.nativeApplication.openedWindows[0].height = preferences.nativeWindowHeight;
				}
				
				if (preferences.applicationVersion != currentAppVersion) {
					preferences.valveGridColumns		= null;
					preferences.actuatorGridColumns		= null;
					preferences.valveScheduleGridColumns = null;
					
					preferences.damperGridColumns 		= null;
					preferences.accsryDamperGridColumns = null;
					preferences.damperScheduleGridColumns = null;
					
					preferences.pneumaticGridColumns 	= null;
					preferences.accsryPneumGridColumns 	= null;
					preferences.pneumaticScheduleGridColumns = null;
					
					preferences.applicationVersion	= currentAppVersion;
				}
			}
			
			//create new preferences
			if (!_preferences) {
				_preferences = new UserPreferences();
				preferences.applicationVersion = currentAppVersion;
			}
			
			if (isNaN(_preferences.globalMultiplier)) {
				_preferences.globalMultiplier = 1;
			}
		}
		
		
		public function gatherData():void {
			var windowDisplayState:String			= NativeApplication.nativeApplication.openedWindows[0].displayState;
			preferences.nativeWindowDisplayState	= windowDisplayState;
			
			// Only Store Normal Boundaries.
			if (windowDisplayState == NativeWindowDisplayState.NORMAL) {
				preferences.nativeWindowDisplayState = NativeWindowDisplayState.NORMAL;
				
				preferences.nativeWindowX 			= NativeApplication.nativeApplication.openedWindows[0].x;
				preferences.nativeWindowY 			= NativeApplication.nativeApplication.openedWindows[0].y;
				
				preferences.nativeWindowWidth		= NativeApplication.nativeApplication.openedWindows[0].width;
				preferences.nativeWindowHeight		= NativeApplication.nativeApplication.openedWindows[0].height;
			}
			else if (windowDisplayState == NativeWindowDisplayState.MINIMIZED) {
				preferences.nativeWindowDisplayState = NativeWindowDisplayState.NORMAL;
			}
		}
		
		
		override public function onRemove():void {
			// commit();
			PreferencesWriter.writePreferences(preferences, prefFile);
		}
		
//		private function commit():void {
//			PreferencesWriter.writePreferences(preferences, prefFile);
//		}
	}
}