package Swizzard.Data.Models.SiemensInfo
{
	import Swizzard.Data.Models.Enumeration.Valves.FlowCharacteristics;
	import Swizzard.Data.Models.Enumeration.Valves.MixingDiverting;
	import Swizzard.Data.Models.Enumeration.Valves.ValveMedium;
	import Swizzard.Data.Models.Enumeration.Valves.ValveNormalState;
	import Swizzard.Data.Models.Enumeration.Valves.ValvePattern;
	import Swizzard.Data.Models.Enumeration.Valves.ValveTrim;
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;
	
	[RemoteClass]
	public class ValveInfo
	{
		public var id:Number;
		private var _partNumber:String;
		private var _medium:uint;
		private var _mixingDiverting:uint;
		private var _size:Number;
		private var _cvu:Number;
		private var _cvl:Number;
		private var _pattern:uint;
		private var _failSafeU:String;
		private var _failSafeL:String;
		private var _connection:uint;
		private var _type:uint;
		private var _seatStyle:String;
		private var _flowChar:uint;
		private var _maxTemp:Number;
		private var _maxPress:Number;
		private var _maxDPWater:Number;
		private var _maxDPSteam:Number;
		private var _maxIPWater:Number;
		private var _maxIPSteam:Number;
		private var _maxPCTGly:Number;
		private var _closeSlope:Number;
		private var _closeInt:Number;
		private var _motorType:uint;
		private var _controlSignal:uint;
		private var _ambientTem:String;
		private var _model:String;
		private var _bodyMaterial:String;
		private var _packingMaterial:uint;
		private var _stemMaterial:String;
		private var _plugMaterial:uint;
		private var _discMaterial:String;
		private var _seatMaterial:String;
		private var _closeOff:String;
		private var _thread:String;
		private var _kvsu:Number;
		private var _kvsl:Number;
		private var _lastModified:Date;
		private var _normalState:uint;
		private var _butterflyConfig:String;	// Butterfly Configuration
		private var _discType:uint;				// Disc Type
		private var _minGPM:Number;
		private var _maxGPM:Number;
		private var _presetGPM:Number;
		
		private var _mediumString:String			= "Unknown";
		private var _typeString:String				= "Unknown";
		private var _patternString:String			= "Unknown";
		private var _plugMaterialString:String		= "Unknown";
		private var _flowCharString:String			= "Unknown";
		private var _mixingDivertingString:String	= "Unknown";
		private var _normalStateString:String		= "Unknown";
		
		private var _useCVLField:Boolean;		// Use C V L Field 
		
		
		public function ValveInfo()
		{}
		
		
		public function set useCVLField(value:Boolean):void {
			this._useCVLField = value;
		}
		public function get useCVLField():Boolean {
			return this._useCVLField;
		}
		
		
		public function set partNumber(value:String):void {
			this._partNumber = value;
		}
		public function get partNumber():String {
			return this._partNumber;
		}
		
		
		public function set medium(value:uint):void {
			this._medium		= value;
			this._mediumString	= ValveMedium.flagToString(value);
		}
		public function get medium():uint {
			return this._medium;
		}
		public function get mediumString():String {
			return this._mediumString;
		}
		
		
		public function set mixingDiverting(value:uint):void {
			this._mixingDiverting		= value;
			this._mixingDivertingString	= MixingDiverting.toString(value);
		}
		public function get mixingDiverting():uint {
			return this._mixingDiverting;
		}
		public function get mixingDivertingString():String {
			return this._mixingDivertingString;
		}
		
		
		public function get portConfigurationString():String {
			return patternString + " " + ((pattern == ValvePattern.TWO_WAY) ? normalStateString : mixingDivertingString);
		}
		
		
		public function set size(value:Number):void {
			this._size = value;
		}
		public function get size():Number {
			return this._size;
		}

		
		public function get cvu():Number 
		{
			return this._cvu;
		}
		public function set cvu(value:Number):void 
		{
			this._cvu = value;
		}
		

		public function get cvl():Number 
		{
			return this._cvl;
		}
		public function set cvl(value:Number):void 
		{
			this._cvl = value;
		}
		

		public function set pattern(value:uint):void {
			this._pattern = value;
			this._patternString	= ValvePattern.toString(value); 
		}
		public function get pattern():uint {
			return this._pattern;
		}
		public function get patternString():String {
			return this._patternString;
		}

		
		public function get failSafeU():String {
			return this._failSafeU;
		}
		public function set failSafeU(value:String):void {
			this._failSafeU = value;
		}

		
		public function get failSafeL():String 
		{
			return this._failSafeL;
		}

		public function set failSafeL(value:String):void 
		{
			this._failSafeL = value;
		}

		public function get connection():uint 
		{
			return this._connection;
		}

		public function set connection(value:uint):void 
		{
			this._connection = value;
		}

		public function get type():uint 
		{
			return this._type;
		}

		public function set type(value:uint):void 
		{
			this._type 			= value;
			this._typeString	= ValveType.toString(value);
		}
		
		public function get typeString():String
		{
			return this._typeString;
		}

		public function get seatStyle():String 
		{
			return this._seatStyle;
		}

		public function set seatStyle(value:String):void 
		{
			this._seatStyle = value;
		}

		public function get flowChar():uint 
		{
			return this._flowChar;
		}

		public function set flowChar(value:uint):void 
		{
			this._flowChar = value;
			
			this._flowCharString = FlowCharacteristics.toString(value);
		}
		
		public function get flowCharString():String
		{
			return this._flowCharString;
		}

		public function get maxTemp():Number 
		{
			return this._maxTemp;
		}

		public function set maxTemp(value:Number):void 
		{
			this._maxTemp = value;
		}

		public function get maxPress():Number 
		{
			return this._maxPress;
		}

		public function set maxPress(value:Number):void 
		{
			this._maxPress = value;
		}

		public function get maxDPWater():Number 
		{
			return this._maxDPWater;
		}

		public function set maxDPWater(value:Number):void 
		{
			this._maxDPWater = value;
		}

		public function get maxDPSteam():Number 
		{
			return this._maxDPSteam;
		}

		public function set maxDPSteam(value:Number):void 
		{
			this._maxDPSteam = value;
		}

		public function get maxIPWater():Number {
			return this._maxIPWater;
		}
		public function set maxIPWater(value:Number):void {
			this._maxIPWater = value;
		}
		

		public function get maxIPSteam():Number {
			return this._maxIPSteam;
		}
		public function set maxIPSteam(value:Number):void {
			this._maxIPSteam = value;
		}

		
		public function get maxPCTGly():Number {
			return this._maxPCTGly;
		}
		public function set maxPCTGly(value:Number):void {
			this._maxPCTGly = value;
		}
		

		public function get closeSlope():Number {
			return this._closeSlope;
		}
		public function set closeSlope(value:Number):void {
			this._closeSlope = value;
		}

		
		public function get closeInt():Number {
			return this._closeInt;
		}
		public function set closeInt(value:Number):void {
			this._closeInt = value;
		}

		
		public function get motorType():uint {
			return this._motorType;
		}
		public function set motorType(value:uint):void {
			this._motorType = value;
		}

		
		public function get controlSignal():uint {
			return this._controlSignal;
		}
		public function set controlSignal(value:uint):void {
			this._controlSignal = value;
		}
		
		public function get ambientTemp():String {
			return this._ambientTem;
		}
		public function set ambientTemp(value:String):void {
			this._ambientTem = value;
		}
		

		public function set model(value:String):void {
			this._model = value;
		}
		public function get model():String {
			return this._model;
		}
		

		public function set bodyMaterial(value:String):void {
			this._bodyMaterial = value;
		}
		public function get bodyMaterial():String {
			return this._bodyMaterial;
		}
		

		public function set packingMaterial(value:uint):void {
			this._packingMaterial = value;
		}
		public function get packingMaterial():uint {
			return this._packingMaterial;
		}


		public function set stemMaterial(value:String):void {
			this._stemMaterial = value;
		}
		public function get stemMaterial():String {
			return this._stemMaterial;
		}


		public function set plugMaterial(value:uint):void {
			this._plugMaterial = value;
			this._plugMaterialString = ValveTrim.toString(value);
		}
		public function get plugMaterial():uint {
			return this._plugMaterial;
		}
		public function get plugMaterialString():String {
			return this._plugMaterialString;
		}
		

		public function set discMaterial(value:String):void {
			this._discMaterial = value;
		}
		public function get discMaterial():String {
			return this._discMaterial;
		}

		
		public function set seatMaterial(value:String):void {
			this._seatMaterial = value;
		}
		public function get seatMaterial():String {
			return this._seatMaterial;
		}


		public function set closeOff(value:String):void {
			this._closeOff = value;
		}
		public function get closeOff():String {
			return this._closeOff;
		}


		public function set thread(value:String):void {
			this._thread = value;
		}
		public function get thread():String {
			return this._thread;
		}


		public function set kvsu(value:Number):void {
			this._kvsu = value;
		}
		public function get kvsu():Number {
			return this._kvsu;
		}
		

		public function set kvsl(value:Number):void {
			this._kvsl = value;
		}
		public function get kvsl():Number {
			return this._kvsl;
		}

		
		public function set lastModified(value:Date):void {
			this._lastModified = value;
		}
		public function get lastModified():Date {
			return this._lastModified;
		}
		
		
		public function set normalState(value:uint):void {
			this._normalState		= value;
			this._normalStateString	= ValveNormalState.toString(value);
		}
		public function get normalState():uint {
			return this._normalState;
		}
		public function get normalStateString():String {
			return this._normalStateString;
		}
		
		
		public function set butterflyConfig(value:String):void {
			this._butterflyConfig = value;
		}
		public function get butterflyConfig():String {
			return this._butterflyConfig;
		}

		
		public function set discType(value:uint):void {
			this._discType = value;
		}
		public function get discType():uint {
			return this._discType;
		}

		
		public function set minGPM(value:Number):void {
			_minGPM = value;
		}
		public function get minGPM():Number {
			return _minGPM;
		}

		
		public function set maxGPM(value:Number):void {
			_maxGPM = value;
		}
		public function get maxGPM():Number {
			return _maxGPM;
		}
		
		
		public function set presetGPM(value:Number):void {
			_presetGPM = value;
		}
		public function get presetGPM():Number {
			return _presetGPM;
		}
		

// ------------------------------------------------------
		public function get cv():Number {
			if (_useCVLField)			
				return _cvl;
			return _cvu;
		}

		public function get cva():Number {
			if (_type == ValveType.BALL && _pattern == ValvePattern.SIX_WAY)
				return _cvu;
			return -1;
		}
		
		public function get cvb():Number {
			if (_type == ValveType.BALL && _pattern == ValvePattern.SIX_WAY)
				return _cvl;
			return -1;
		}
	}
}