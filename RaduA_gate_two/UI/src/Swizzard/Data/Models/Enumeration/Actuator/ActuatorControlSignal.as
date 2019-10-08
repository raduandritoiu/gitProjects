package Swizzard.Data.Models.Enumeration.Actuator
{
	import flash.utils.describeType;
	
	
	public class ActuatorControlSignal
	{
		private static var _dataProvider:Array;
		
		public static const	s0_10_VDC:uint	= 1;	// Electric 0 - 10 Voltage DC
		public static const	s0_20_VDC:uint	= 2;	// Electric 0 - 20 Voltage DC
		public static const	s4_20mA:uint	= 3;	// Electric 4 - 20 MilliAmps
		public static const	s2_POS:uint		= 4;	// Electric 2 Position  
		public static const	ON_OFF:uint		= 4;	// Electric ON / OFF (On/Off and 2 Position are the same)
		public static const	s3_POS:uint		= 5;	// Electric 3 Position
		//public static const	ON_OFF:uint		= 6;	// Electric ON / OFF
		public static const	s0_20_PSI:uint	= 7;	// Pneumatic 0 - 20 PSI
		public static const	s0_60_PSI:uint	= 8;	// Pneumatic 0 - 60 PSI
		public static const	s2_6_PSI:uint	= 9;	// Pneumatic 2 - 6 PSI
		public static const	s2_12_PSI:uint	= 10;	// Pneumatic 2 - 12 PSI
		public static const	s3_8_PSI:uint	= 11;	// Pneumatic 3 - 8 PSI
		public static const	s3_13_PSI:uint	= 12;	// Pneumatic 3 - 13 PSI
		public static const	s5_10_PSI:uint	= 13;	// Pneumatic 5 - 10 PSI
		public static const	s8_13_PSI:uint	= 14;	// Pneumatic 8 - 13 PSI
		public static const	s10_14_PSI:uint	= 15;	// Pneumatic 10 - 14 PSI
		public static const	s10_15_PSI:uint	= 16;	// Pneumatic 10 - 15 PSI
		public static const	s10_20_PSI:uint	= 17;	// Pneumatic 10 - 20 PSI
		public static const ON_OFF__FLOATING:uint	= 18;		// 0ON/OFF & Floating
		public static const s0_10VDC__4_20MA:uint	= 19;		// 0-10VDC/4-20mA
		public static const s2_10_VDC:uint			= 21; // Electric 2-10 VDC
		public static const Floating__0_10V__4_20MA:uint 	= 22; // Floating/0-10V/4-20mA
		
		// Butterfly
		public static const DOUBLE_ACTING_60PSI:uint	= 20; // Double Acting 60 PSI
		public static const s20PSISTD:uint				= 23;
		public static const s60PSIHighPress:uint		= 24;
		
		
		public static function get DATA_PROVIDER():Array
		{
			if (!_dataProvider)
				createDataProvider();
			return _dataProvider;
		}		
		
		
		public static function IsPneumaticSignal(value:int):Boolean
		{
			var result:Boolean	= false;
			switch (value)
			{
				case s0_20_PSI:
				case s0_60_PSI:
				case s2_6_PSI:
				case s2_12_PSI:
				case s3_8_PSI:
				case s3_13_PSI:
				case s5_10_PSI:
				case s8_13_PSI:
				case s10_14_PSI:
				case s10_15_PSI:
				case s10_20_PSI:
				case s20PSISTD:
				case s60PSIHighPress:
				case DOUBLE_ACTING_60PSI:
				{
					result	= true;
					break;
				}
			}
			return result;
		}
		
		
		private static function createDataProvider():void
		{
			var typeInfo:XML 	= describeType(ActuatorControlSignal);
			_dataProvider		= new Array();
			
			for each (var prop:XML in typeInfo.constant)
			{
				var value:uint	= ActuatorControlSignal[prop.@name.toString()] as uint;
				_dataProvider.push({label: toString(value), value: value});
			}
			
			_dataProvider.sortOn("label");
		}
		
		
		public static function toString(flag:uint):String
		{
			switch (flag)
			{
				case s0_10_VDC:
					return "0-10 VDC";
					break;
				
				case s0_20_VDC:
					return "0-20 VDC";
					break;
				
				case s2_10_VDC:
					return "2-10 VDC";
					break;
				
				case s4_20mA:
					return "4-20 mA";
					break;
				
				case ON_OFF:
					return "On/Off";
					break;
				
				case s2_POS:
					return "2 Position";
					break;
				
				case s3_POS:
					return "Floating";
					break;
				
				case s0_20_PSI:
					return "0-20 psi";
					break;
				
				case s0_60_PSI:
					return "0-60 psi";
					break;
				
				case s2_6_PSI:
					return "2-6 psi";
					break;
				
				case s2_12_PSI:
					return "2-12 psi";
					break;
				
				case s3_8_PSI:
					return "3-8 psi";
					break;
				
				case s3_13_PSI:
					return "3-13 psi";
					break;
				
				case s5_10_PSI:
					return "5-10 psi";
					break;
				
				case s8_13_PSI:
					return "8-13 psi";
					break;
				
				case s10_14_PSI:
					return "10-14 psi";
					break;
				
				case s10_15_PSI:
					return "10-15 psi";
					break;
				
				case s10_20_PSI:
					return "10-20 psi";
					break;
				
				case ON_OFF__FLOATING:
					return "On/Off & Floating";
					break;
				
				case s0_10VDC__4_20MA:
					return "0-10VDC & 4-20mA";
					break;
				
				case DOUBLE_ACTING_60PSI:
					return "60 PSI Double Acting";
					break;
				
				case Floating__0_10V__4_20MA:
					return "Floating/0-10 VDC/4-20 MA"
					break;
					
				case s20PSISTD:
					return "20 PSI STD";
					break;
				
				case s60PSIHighPress:
					return "60 PSI High Press";
					break;
			}
			return "N/A";
		}
	}
}