package Swizzard.Data.Models.SiemensInfo
{
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticBallJoint;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticBracket;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticClevis;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticDiaphArea;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticDiaphMaterial;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticFwdTravelStops;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticHousing;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticIsActuator;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticMountingStyle;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticOperTemp;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticPivot;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticPosRelay;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticSpringRange;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticStem;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticStroke;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticType;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticULCerts;

	[RemoteClass]
	public class PneumaticInfo
	{
		public var id:Number;
		private var _partNumber:String;
		private var _type:int;
		private var _stroke:int;
		private var _housing:int;
		private var _stem:int;
		private var _diaphArea:int;
		private var _diaphMaterial:int;
		private var _operatingTemp:int;
		// TODO: these could be booleans, but made them ints (with backed enums) - just for fun and for extension in the future
		private var _isActuator:int;
		private var _clevis:int;
		private var _bracket:int;	
		private var _ballJoint:int;
		private var _pivot:int;
		private var _posRelay:int;
		private var _fwdTravelStops:int;
		// these seem to be important
		private var _mountingStyle:int;
		private var _ULCert:int;
		private var _springRange:int;
		private var _baseReplacement:String;
		
		private var _maxThrust_18:Number; // this is for: Maximum Thrust lb (N) - "Full Stroke Forward 18 psi (124 kPa)"
		private var _maxThrust_no:Number; // this is for: Maximum Thrust lb (N) - "Spring Return no stroke 0 psi (0 kPa)"
		
		// for pretty string formating
		private var _typeString:String;
		private var _strokeString:String;
		private var _housingString:String;
		private var _stemString:String;
		private var _diaphAreaString:String;
		private var _diaphMaterialString:String;
		private var _operatingTempString:String;
		
		private var _isActuatorString:String;
		private var _clevisString:String;
		private var _bracketString:String;	
		private var _ballJointString:String;
		private var _pivotString:String;
		private var _posRelayString:String;
		private var _fwdTravelStopsString:String;
		
		private var _mountingStyleString:String;
		private var _ULCertString:String;
		private var _springRangeString:String;
		
		
		public function PneumaticInfo()
		{}
		
		
		public function set partNumber(val:String):void {
			_partNumber = val;
		}
		public function get partNumber():String {
			return _partNumber;
		}
		
		
		public function set type(val:int):void {
			_type = val;
			_typeString = PneumaticType.toString(val);
		}
		public function get type():int {
			return _type;
		}
		public function get typeString():String {
			return _typeString;
		}
		
		
		public function set stroke(val:int):void {
			_stroke = val;
			_strokeString = PneumaticStroke.toString(val);
		}
		public function get stroke():int {
			return _stroke;
		}
		public function get strokeString():String {
			return _strokeString;
		}
		
		
		public function set housing(val:int):void {
			_housing = val;
			_housingString = PneumaticHousing.toString(val);
		}
		public function get housing():int {
			return _housing;
		}
		public function get housingString():String {
			return _housingString;
		}
		
		
		public function set stem(val:int):void {
			_stem = val;
			_stemString = PneumaticStem.toString(val);
		}
		public function get stem():int {
			return _stem;
		}
		public function get stemString():String {
			return _stemString;
		}

		
		public function set diaphArea(val:int):void {
			_diaphArea = val;
			_diaphAreaString = PneumaticDiaphArea.toString(val);
		}
		public function get diaphArea():int {
			return _diaphArea;
		}
		public function get diaphAreaString():String {
			return _diaphAreaString;
		}
		
		
		public function set diaphMaterial(val:int):void {
			_diaphMaterial = val;
			_diaphMaterialString = PneumaticDiaphMaterial.toString(val);
		}
		public function get diaphMaterial():int {
			return _diaphMaterial;
		}
		public function get diaphMaterialString():String {
			return _diaphMaterialString;
		}
		
		
		public function set operatingTemp(val:int):void {
			_operatingTemp = val;
			_operatingTempString = PneumaticOperTemp.toString(val);
		}
		public function get operatingTemp():int {
			return _operatingTemp;
		}
		public function get operatingTempString():String {
			return _operatingTempString;
		}
		
		
		public function set isActuator(val:int):void {
			_isActuator = val;
			_isActuatorString = PneumaticIsActuator.toString(val);
		}
		public function get isActuator():int {
			return _isActuator;
		}
		public function get isActuatorString():String {
			return _isActuatorString;
		}
		
		
		public function set clevis(val:int):void {
			_clevis = val;
			_clevisString = PneumaticClevis.toString(val);
		}
		public function get clevis():int {
			return _clevis;
		}
		public function get clevisString():String {
			return _clevisString;
		}
		
		
		public function set bracket(val:int):void {
			_bracket = val;
			_bracketString = PneumaticBracket.toString(val);
		}
		public function get bracket():int {
			return _bracket;
		}
		public function get bracketString():String {
			return _bracketString;
		}
		
		
		public function set ballJoint(val:int):void {
			_ballJoint = val;
			_ballJointString = PneumaticBallJoint.toString(val);
		}
		public function get ballJoint():int {
			return _ballJoint;
		}
		public function get ballJointString():String {
			return _ballJointString;
		}
		
		
		public function set pivot(val:int):void {
			_pivot = val;
			_pivotString = PneumaticPivot.toString(val);
		}
		public function get pivot():int {
			return _pivot;
		}
		public function get pivotString():String {
			return _pivotString;
		}
		
		
		public function set posRelay(val:int):void {
			_posRelay = val;
			_posRelayString = PneumaticPosRelay.toString(val);
		}
		public function get posRelay():int {
			return _posRelay;
		}
		public function get posRelayString():String {
			return _posRelayString;
		}
		
		
		public function set fwdTravelStops(val:int):void {
			_fwdTravelStops = val;
			_fwdTravelStopsString = PneumaticFwdTravelStops.toString(val);
		}
		public function get fwdTravelStops():int {
			return _fwdTravelStops;
		}
		public function get fwdTravelStopsString():String {
			return _fwdTravelStopsString;
		}
		
		
		// these seem to be important
		public function set mountingStyle(val:int):void {
			_mountingStyle = val;
			_mountingStyleString = PneumaticMountingStyle.toString(val);
		}
		public function get mountingStyle():int {
			return _mountingStyle;
		}
		public function get mountingStyleString():String {
			return _mountingStyleString;
		}
		
		
		public function set ULCert(val:int):void {
			_ULCert = val;
			_ULCertString = PneumaticULCerts.toString(val);
		}
		public function get ULCert():int {
			return _ULCert;
		}
		public function get ULCertString():String {
			return _ULCertString;
		}
		
		
		public function set springRange(val:int):void {
			_springRange = val;
			_springRangeString = PneumaticSpringRange.toString(val);
		}
		public function get springRange():int {
			return _springRange;
		}
		public function get springRangeString():String {
			return _springRangeString;
		}
		
		
		public function set baseReplacement(val:String):void {
			_baseReplacement = val;
		}
		public function get baseReplacement():String {
			return _baseReplacement;
		}
		
		
		public function set maxThrust_18(val:Number):void {
			_maxThrust_18 = val;
		}
		public function get maxThrust_18():Number {
			return _maxThrust_18;
		}
		
		
		public function set maxThrust_no(val:Number):void {
			_maxThrust_no = val;
		}
		public function get maxThrust_no():Number {
			return _maxThrust_no;
		}
	}
}