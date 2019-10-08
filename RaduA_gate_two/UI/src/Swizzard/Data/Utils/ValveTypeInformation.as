package Swizzard.Data.Utils
{
	/**
	 * Valve Type Information
	 * 
	 * An instance of this class is created to define the selectable parameters for each valve type
	 *  
	 * @author michael
	 * 
	 * @see Swizzard.System.Utils.FormUtil
	 */	
	public class ValveTypeInformation
	{
		public var valveType:uint;				// Valve Type
		public var valveMedium:uint;			// Valve Medium
		public var selectableCV:Array;			// Selectable CV
		public var selectablePorts:Array;		// Selectable Pattern
		public var selectableTrim:Array;		// Selectable Trim
		public var selectableConnections:Array;	// Selectable Connections
		public var selectableApplication:Array;	// Selectable Application
		public var selectableSizes:Array;		// Selectable Sizes
		public var selectableFlowChar:Array;	// Selectable Flow Characteristics
		public var selectableMedium:Array;		// Selectable Medium
		public var selectablePortConfig:Array;	// Selectable Port Config
		public var selectablePressureClass:Array;	// Selectable Pressure Class (MAXIPRESS)
		public var selectablePositioner:Array;	// Selectable Positioner Values
		
		public var actuatorTypes:Array;			// Actuator Types 
		public var supplyVoltage:Array;			// Supply Voltage 
		public var electricControlSignal:Array;	// Control Signal 
		public var failureModes:Array;			// Failure Modes
		
		public var disableCVForm:Boolean;
		public var disablePositioner:Boolean;		// Disable Positioner 
		public var pneumaticControlSignal:Array	= [];
		public var endSwitchSelection:Array;
				
		
		public function ValveTypeInformation()
		{}
	}
}