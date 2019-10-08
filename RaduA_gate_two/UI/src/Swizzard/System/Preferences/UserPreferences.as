package Swizzard.System.Preferences
{
	import flash.utils.Proxy;
	import flash.utils.flash_proxy;
	
	import Swizzard.System.Models.CompanyInformation;
	import Swizzard.System.Models.Backwards.BackwardsUtil;
	
	use namespace flash_proxy;
	
	
	[RemoteClass]
	public class UserPreferences extends Proxy 
	{
		private var _applicationVersion:String;	// Application Version
		private var _globalMultiplier:Number;	// Global Multiplier	
		private var _valveGridWidth:Number;		// Valve Grid Width 
		private var _damperGridWidth:Number;	// Damper Grid Width
		private var _pneumaticGridWidth:Number;	// Pneumatic Grid Width
		// for valves
		private var _valveGridColumns:Array;	// Valve Grid Columns
		private var _actuatorGridColumns:Array;	// Actuator Grid Columns
		// for dampers
		private var _damperGridColumns:Array;		// Damper Grid Columns
		private var _accsryDamperGridColumns:Array;	// Accessory (Damper) Grid Columns
		// for pneumatics
		private var _pneumaticGridColumns:Array;	// Pneumatic Grid Columns
		private var _accsryPneumGridColumns:Array;	// Accessory (Pneumatic) Grid Columns
		// for schedules
		private var _valveScheduleGridColumns:Array;	// Valve Schedule Grid Columns
		private var _damperScheduleGridColumns:Array;	// Damper Schedule Grid Columns
		private var _pneumaticScheduleGridColumns:Array;// Pneumatic Schedule Grid Columns
		
		private var _nativeWindowDisplayState:String;
		private var _nativeWindowX:Number;
		private var _nativeWindowY:Number;
		private var _nativeWindowWidth:Number;
		private var _nativeWindowHeight:Number;
		
		private var _company:CompanyInformation;// Company Information
		
		
		public function UserPreferences()
		{}
		

		public function set applicationVersion(value:String):void {
			_applicationVersion	= value;
		}
		
		public function get applicationVersion():String {
			return _applicationVersion;
		}
		
		
		public function set valveGridWidth(value:Number):void {
			_valveGridWidth = value;
		}
		
		public function get valveGridWidth():Number {
			return _valveGridWidth;
		}
		
		
		public function set damperGridWidth(value:Number):void {
			_damperGridWidth = value;
		}
		
		public function get damperGridWidth():Number {
			return _damperGridWidth;
		}
		
		
		public function set pneumaticGridWidth(value:Number):void {
			_pneumaticGridWidth = value;
		}
		
		public function get pneumaticGridWidth():Number {
			return _pneumaticGridWidth;
		}
		
		
		public function set valveGridColumns(value:Array):void {
			_valveGridColumns = value;
		}
		
		public function get valveGridColumns():Array {
			return _valveGridColumns;
		}
		
		
		public function set actuatorGridColumns(value:Array):void {
			_actuatorGridColumns = value;
		}
		
		public function get actuatorGridColumns():Array {
			return _actuatorGridColumns;
		}
		
		
		public function set damperGridColumns(value:Array):void {
			_damperGridColumns = value;
		}
		
		public function get damperGridColumns():Array {
			return _damperGridColumns;
		}
		
		
		public function set accsryDamperGridColumns(value:Array):void {
			_accsryDamperGridColumns = value;
		}
		
		public function get accsryDamperGridColumns():Array {
			return _accsryDamperGridColumns;
		}
		
		
		public function set pneumaticGridColumns(value:Array):void {
			_pneumaticGridColumns = value;
		}
		
		public function get pneumaticGridColumns():Array {
			return _pneumaticGridColumns;
		}
		
		
		public function set accsryPneumGridColumns(value:Array):void {
			_accsryPneumGridColumns = value;
		}
		
		public function get accsryPneumGridColumns():Array {
			return _accsryPneumGridColumns;
		}
		
		
		public function set valveScheduleGridColumns(value:Array):void {
			_valveScheduleGridColumns = value;
		}
		
		public function get valveScheduleGridColumns():Array {
			return _valveScheduleGridColumns;
		}
		
		
		public function set damperScheduleGridColumns(value:Array):void {
			_damperScheduleGridColumns = value;
		}
		
		public function get damperScheduleGridColumns():Array {
			return _damperScheduleGridColumns;
		}
		
		
		public function set pneumaticScheduleGridColumns(value:Array):void {
			_pneumaticScheduleGridColumns = value;
		}
		
		public function get pneumaticScheduleGridColumns():Array {
			return _pneumaticScheduleGridColumns;
		}
		
		
		public function set globalMultiplier(value:Number):void {
			_globalMultiplier = value;
		}

		public function get globalMultiplier():Number {
			return _globalMultiplier;
		}
		

		public function set nativeWindowY(value:Number):void {
			_nativeWindowY = Math.max(-10, value);
		}
		
		public function get nativeWindowY():Number {
			return _nativeWindowY;
		}
		
		
		public function set nativeWindowX(value:Number):void {
			_nativeWindowX = Math.max(-10, value);
		}
		
		public function get nativeWindowX():Number {
			return _nativeWindowX;
		}
		
		
		public function set nativeWindowHeight(value:Number):void {
			_nativeWindowHeight = value;
		}
		
		public function get nativeWindowHeight():Number {
			return _nativeWindowHeight;
		}
		
		
		public function set nativeWindowWidth(value:Number):void {
			_nativeWindowWidth = value;
		}
		
		public function get nativeWindowWidth():Number {
			return _nativeWindowWidth;
		}
		
		
		public function set nativeWindowDisplayState(value:String):void {
			_nativeWindowDisplayState = value;
		}
		
		public function get nativeWindowDisplayState():String {
			return _nativeWindowDisplayState;
		}
		
		
		public function set companyInfo(value:CompanyInformation):void {
			_company = value;
		}
		
		public function get companyInfo():CompanyInformation {
			return _company;
		}
		
		
		// this was added for backwards compatibility
		override flash_proxy function hasProperty(name:*):Boolean {
			return false;
		}
		
		override flash_proxy function getProperty(name:*):* {
			return null;
		}
		
		override flash_proxy function setProperty(name:*, value:*):void {
			if (name.toString() == "company") {
				companyInfo = BackwardsUtil.BackwardsCompanyInformations(value);
			}
			else if (name.toString() == "scheduleGridColumns") {
				valveScheduleGridColumns = value as Array;
			}
		}
	}
}