package Swizzard.UI.Components.SplashScreen
{
	import flash.desktop.NativeApplication;
	import flash.display.Bitmap;
	import flash.display.NativeWindow;
	import flash.display.NativeWindowInitOptions;
	import flash.display.NativeWindowSystemChrome;
	import flash.display.NativeWindowType;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.system.Capabilities;
	import flash.text.TextField;
	import flash.text.TextFormat;
	import flash.utils.setTimeout;
	
	import Swizzard.Images.EmbeddedImages;
	import Swizzard.System.Utils.VersionUtil;
	
	
	public class SplashScreen extends NativeWindow
	{
		private static var instance:SplashScreen;
		
		private var bringToFrontAttempts:uint = 0;
		
		
		public function SplashScreen(alwaysFront:Boolean, centerToScreen:Boolean)
		{		
			var o:NativeWindowInitOptions = new NativeWindowInitOptions();
			o.maximizable 	= false;
			o.minimizable 	= false;
			o.resizable 	= false;
			o.systemChrome 	= NativeWindowSystemChrome.NONE;
			o.transparent 	= true;
			o.type			= NativeWindowType.UTILITY;
			
			super(o);
			
			alwaysInFront 	= true;
			title			= "About";
			stage.scaleMode = StageScaleMode.NO_SCALE;
			stage.align 	= StageAlign.TOP_LEFT;
			
			
			var bm:Bitmap = new EmbeddedImages.splashScreen;
			bm.x = 0
			bm.y = 0;
			width 	= bm.width;
			height 	= bm.height;
			if (centerToScreen) {
				x = (Capabilities.screenResolutionX - width) * 0.5;
				y = (Capabilities.screenResolutionY - height) * 0.5;
			} 
			else 
			{
				x = NativeApplication.nativeApplication.activeWindow.x + (NativeApplication.nativeApplication.activeWindow.width - bm.width) *0.5;
				y = NativeApplication.nativeApplication.activeWindow.y + (NativeApplication.nativeApplication.activeWindow.height - bm.height) *0.5;
			}
			stage.addChild(bm);
			
			
			var versionText:TextField 		= new TextField();
			versionText.selectable 			= false;
			versionText.defaultTextFormat 	= new TextFormat("Arial", 11, 0x556A73);
			versionText.embedFonts 			= false;
			versionText.alpha 	   			= 1;
			versionText.width				= 300;
			
			versionText.text 	= "Version " + VersionUtil.CURRENT_VERSION() + " © Siemens Industry, Inc. 2011, 2019 \rPatent Pending.";
			
			versionText.x 		= 257;
			versionText.y 		= bm.height - (33 + 12 + versionText.textHeight);
			stage.addChild(versionText);
			
			// Ken Stull states that this window goes behind other windows on his PC
			// His PC contains the Siemen's CAT Client. I am concerned that there may be
			// undesirable window interaction with the Siemen's CAT Client and Windows.
			// We cannot reproduce this issue so we are attempting to write a patch for
			// Siemen's Related computers.
			setTimeout(this.orderToFront, 250); // Attempting to move this window to the front in 1/4 of second from when it was opened
			addEventListener(Event.DEACTIVATE, windowDeactivatedHandler, false, 0, true);
		}
		
		
		private function windowDeactivatedHandler(event:Event):void
		{
			if (bringToFrontAttempts++ > 10)
				return;
									
			// Attempting to Fix Splash Screen Behind Application Issue seen on CAT Client Machines
			try
			{
				orderToFront();
			}
			catch (err:Error)
			{
				// Catch exception in case ordering to front causes error.
			}
		}
		
		
		public static function Show(alwaysInFront:Boolean = false, centerToScreen:Boolean = true):SplashScreen
		{
			if (instance == null) 
			{
				instance = new SplashScreen(alwaysInFront, centerToScreen);
				addCloseListener();
				instance.activate();
			}			
			return instance;
		}
		
		
		public static function close(e:Event = null):void
		{
			if (instance) 
			{
				instance.stage.removeEventListener(MouseEvent.MOUSE_UP, SplashScreen.close, false);
				instance.close();
				instance = null;
			}
		}
		
		
		public static function addCloseListener():void
		{
			if (instance) 
			{
				instance.stage.addEventListener(MouseEvent.MOUSE_UP, SplashScreen.close, false, 0, true);
			}
		}
	}
}