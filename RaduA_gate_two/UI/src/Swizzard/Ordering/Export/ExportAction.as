package Swizzard.Ordering.Export
{
	public class ExportAction
	{
		public static const TO_EXCEL:String 		= "exportToExcel";
		public static const TO_PDF:String 			= "exportToPDF";
		public static const TO_CSV:String 			= "exportToCSV";
		public static const TO_PROPRIETARY:String 	= "exportToCSV";
		
		public static const NO:uint 				= 0;
		public static const CANCEL:uint 			= 1;
		public static const VALVE_SCHEDULE:uint 	= 2;
		public static const DAMPER_SCHEDULE:uint 	= 4;
		public static const PNEUMATIC_SCHEDULE:uint = 8;
		public static const ALL_SCHEDULE:uint 		= 14;
		
		
		public static function getExportString(action:String):String {
			switch (action) {
				case TO_EXCEL:
					return "to Excel";
					break;
				case TO_PDF:
					return "to PDF";
					break;
				case TO_CSV:
					return "to Webshop";
					break;
				case TO_PROPRIETARY:
					return "to custom format";
					break;
			}
			return "Unknown export";
		}
	}
}