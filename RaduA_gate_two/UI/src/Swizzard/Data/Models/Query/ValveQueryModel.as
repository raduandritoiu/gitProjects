package Swizzard.Data.Models.Query
{
	import flash.utils.Dictionary;
	import flash.utils.describeType;
	
	import j2inn.Data.Query.QueryOrderBy;
	
	
	[Bindable]
	public class ValveQueryModel extends BaseQueryModel
	{
		public static const TYPE_INFO:XML	= describeType(ValveQueryModel);
		
		private var _queryCache:Dictionary;
		
		private var _valveTypes:Array;				// Valve Types
		private var _order:QueryOrderBy;			// Order By
		private var _medium:uint;					// Medium
		private var _pattern:uint;					// Action (Port Configuration)
		private var _designFlow:Number;				// Design Flow 					- used only by PICV
		private var _percentGlycol:Number;			// Percent Glycol
		private var _CV:Number;						// CV
		private var _CVB:Number;					// CVB							- used only by BALL
		private var _lineSize:Number;				// Line
		private var _trim:uint;						// Trim
		private var _flowCharacteristic:uint;		// Flow Characteristics
		private var _ANSIPressureClass:String;		// ANSI Pressure
		private var _connection:uint;				// Connections
		private var _packingMaterial:uint;			// Packing Material
		private var _actuatorType:String;			// Actuator Type
		private var _supplyVoltage:Number;			// Supply Voltage
		private var _controlSignal:uint;			// Control Signal
		private var _failureMode:String;			// Failure Mode
		private var _application:uint;				// Mixing Or Diverting
		private var _maximumTemperature:Number;		// Maximum Temperature
		private var _cvTolerance:Number;			// CV Tolerance
		private var _cvTolerance_B:Number;			// CV Tolerance B 				- used only by BALL
		private var _normalState:uint;				// Normal State (Normally Open, Normally Closed)
		private var _pressureClass:uint;			// Pressure Class
		private var _discType:int;					// Disc Type
		private var _minSteamSupplyPressure:int;	// Maximum Steam Supply Pressure
		private var _requiredFlowTolerance:Number;  // 								- used only by PICV
		
		
		public function ValveQueryModel() {
			super();
			
			_valveTypes				= new Array();
			_discType				= -1;
			_cvTolerance			= 4;
			_cvTolerance_B			= 4;
			_requiredFlowTolerance	= 4;
			_queryCache				= new Dictionary();
		}
		
		
		public function set valveTypes(value:Array):void {
			_valveTypes = value;
			changedFields["valveTypes"]	= value;
		}
		public function get valveTypes():Array {
			return _valveTypes;
		}
		
		public function set order(value:QueryOrderBy):void {
			_order = value;
			changedFields["order"] = value;
		}
		public function get order():QueryOrderBy {
			return _order;
		}
		
		public function set medium(value:uint):void {
			_medium = value;
			changedFields["medium"]	= value;
		}
		public function get medium():uint {
			return _medium;
		}
		
		public function set pattern(value:uint):void {
			_pattern = value;
			changedFields["pattern"] = value;
		}
		public function get pattern():uint {
			return _pattern;
		}
		
		public function set application(value:uint):void {
			_application	= value;
			changedFields["application"]	= value;
		}
		public function get application():uint {
			return _application;
		}
		
		public function set minSteamSupplyPressure(value:uint):void {
			_minSteamSupplyPressure	= value;
			changedFields["minSteamSupplyPressure"]	= value;
		}
		public function get minSteamSupplyPressure():uint {
			return _minSteamSupplyPressure;
		}
		
		public function set designFlow(value:Number):void {
			_designFlow = value;
			changedFields["designFlow"]	= value;
		}
		public function get designFlow():Number {
			return _designFlow;
		}
		
		public function set cvTolerance(value:Number):void {
			_cvTolerance	= value;
			changedFields["cvTolerance"]	= value;
		}
		public function get cvTolerance():Number {
			return _cvTolerance;
		}
		
		public function set cvTolerance_B(value:Number):void {
			_cvTolerance_B	= value;
			changedFields["cvTolerance_B"]	= value;
		}
		public function get cvTolerance_B():Number {
			return _cvTolerance_B;
		}
		
		public function set percentGlycol(value:Number):void {
			_percentGlycol = value;
			changedFields["percentGlycol"]	= value;
		}
		public function get percentGlycol():Number {
			return _percentGlycol;
		}

		public function set CV(value:Number):void {
			_CV = value;
			changedFields["CV"]	= value;
		}
		public function get CV():Number {
			return _CV;
		}
		
		public function set CVB(value:Number):void {
			_CVB = value;
			changedFields["CVB"] = value;
		}
		public function get CVB():Number {
			return _CVB;
		}
		
		public function set lineSize(value:Number):void {
			_lineSize = value;
			changedFields["lineSize"]	= value;
		}
		public function get lineSize():Number {
			return _lineSize;
		}

		public function set trim(value:uint):void {
			_trim = value;
			changedFields["trim"]	= value;
		}
		public function get trim():uint {
			return _trim;
		}

		public function set flowCharacteristic(value:uint):void {
			_flowCharacteristic = value;
			changedFields["flowCharacteristic"]	= value;
		}
		public function get flowCharacteristic():uint {
			return _flowCharacteristic;
		}
		
		public function set ANSIPressureClass(value:String):void {
			_ANSIPressureClass = value;
			changedFields["ANSIPressureClass"]	= value;
		}
		public function get ANSIPressureClass():String {
			return _ANSIPressureClass;
		}
		
		public function set connection(value:uint):void {
			_connection = value;
			changedFields["connection"]	= value;
		}
		public function get connection():uint {
			return _connection;
		}
		
		public function set packingMaterial(value:uint):void {
			_packingMaterial = value;
			changedFields["packingMaterial"]	= value;
		}
		public function get packingMaterial():uint {
			return _packingMaterial;
		}

		public function set actuatorType(value:String):void {
			_actuatorType = value;
			changedFields["actuatorType"]	= value;
		}
		public function get actuatorType():String {
			return _actuatorType;
		}

		public function set supplyVoltage(value:Number):void {
			_supplyVoltage = value;
			changedFields["supplyVoltage"]	= value;
		}
		public function get supplyVoltage():Number {
			return _supplyVoltage;
		}
		
		public function set controlSignal(value:uint):void {
			_controlSignal = value;
			changedFields["controlSignal"]	= value;
		}
		public function get controlSignal():uint {
			return _controlSignal;
		}

		public function set failureMode(value:String):void {
			_failureMode = value;
			changedFields["failureMode"]	= value;
		}
		public function get failureMode():String {
			return _failureMode;
		}
		
		public function set maximumTemperature(value:Number):void {
			_maximumTemperature	= value;
			changedFields["maximumTemperature"]	= value;
		}
		public function get maximumTemperature():Number {
			return _maximumTemperature;
		}
		
		public function set normalState(value:uint):void {
			_normalState = value;
			changedFields["normalState"] = value;
		}
		public function get normalState():uint {
			return _normalState;
		}
		
		public function set pressureClass(value:uint):void {
			_pressureClass = value;
			changedFields["pressureClass"] = value;
		}
		public function get pressureClass():uint {
			return _pressureClass;
		}
		
		public function set discType(value:int):void {
			_discType = value;
			changedFields["discType"] = value;
		}
		public function get discType():int {
			return _discType;
		}
		
		public function set requiredFlowTolerance(value:Number):void {
			_requiredFlowTolerance = value;
		}
		public function get requiredFlowTolerance():Number {
			return _requiredFlowTolerance;
		}
		
		public function setQueryCache(value:Dictionary):void {
			_queryCache	= value;
		}
		public function getQueryCache():Dictionary {
			return _queryCache;
		}
		
		
		override protected function customReset():void {
			for each (var accessor:XML in TYPE_INFO..accessor)
			{
				switch (accessor.@name.toString())
				{
					case "cvTolerance":
					case "cvTolerance_B":
						this["_" + accessor.@name.toString()]	= 4;
						break;
					
					case "valveTypes":
						this["_" + accessor.@name.toString()]	= new Array();
						break;
					
					case "discType":
						_discType	= -1;
						break;
					
					case "resetPending":
					case "changed":					
					case "suppressEvents":
					case "prototype":
						break;
					
					default:
						this["_" + accessor.@name.toString()] =	 null;
				}
			}
		}
	}
}