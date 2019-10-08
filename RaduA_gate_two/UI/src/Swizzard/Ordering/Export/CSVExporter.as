package Swizzard.Ordering.Export
{
	import com.shortybmc.data.parser.CSV;
	
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.utils.ByteArray;
	
	import mx.controls.Alert;
	
	import Swizzard.System.Models.DamperScheduleItem;
	import Swizzard.System.Models.PneumaticScheduleItem;
	import Swizzard.System.Models.VSSTProject;
	import Swizzard.System.Models.ValveScheduleItem;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.UI.Windows.ExportConfirmWindow;
	
	
	public class CSVExporter implements IExportTarget
	{
		private static var instance:CSVExporter;
		
		private var pendingProject:VSSTProject;
		private var exportType:uint;
		
		
		public function CSVExporter()
		{}
		
		
		public function exportToCSVFile(project:VSSTProject):void {
			pendingProject = project;
			ExportConfirmWindow.Show(this, ExportAction.TO_CSV);
		}
		
		
		public function exportResult(exportFormat:String, type:uint):void {
			if (exportFormat != ExportAction.TO_CSV) {
				return;
			}
			exportType = type;
			
			var file:File = new File();
			file.addEventListener(Event.SELECT,	exportFileSelectedHandler);
			file.browseForSave("Save Excel File.");	
		}
		
		
		private function exportFileSelectedHandler(event:Event):void {
			var file:File = event.target as File;
			file.removeEventListener(Event.SELECT,	exportFileSelectedHandler);
			
			if (file.extension != "csv") {
				file.nativePath += ".csv";
			}			
			
			var bytes:ByteArray		= exportToCSVBytes(pendingProject);
			pendingProject 			= null;
			var stream:FileStream	= new FileStream();
			
			try {
				stream.open(file, FileMode.WRITE);
				stream.writeBytes(bytes);
			}
			catch (err:Error) {
				Alert.show("Error Writing File");
			}
			finally {
				stream.close();
			}
		}
		
		
		private function exportToCSVBytes(project:VSSTProject):ByteArray {
			var csv:CSV				= new CSV();
			csv.fieldSeperator		= ";";
			csv.recordsetDelimiter	= "\r\n";
			csv.headerOverwrite		= true;
			
			// export valves
			if (exportType & ExportAction.VALVE_SCHEDULE) {
				var schedules:Array = expandSchedules(project.valveSchedule.source);
				for each (var vschedule:ValveScheduleItem in schedules) {
					csv.addRecordSet([vschedule.actualPartNumber, vschedule.quantity, vschedule.tags]);//, (schedule.mediumString) ? schedule.mediumString.replace(",", " ") : "", schedule.patternString, schedule.size, schedule.cvu, schedule.notes, schedule.price]);
				}
			}
			
			// export dampers
			if (exportType & ExportAction.DAMPER_SCHEDULE) {
				var schedules:Array = expandSchedules(project.damperSchedule.source);
				for each (var dschedule:DamperScheduleItem in schedules) {
					csv.addRecordSet([dschedule["product:partNumber"], dschedule.quantity, ""]);
				}
			}
			
			// export pneumatics
			if (exportType & ExportAction.PNEUMATIC_SCHEDULE) {
				var schedules:Array = expandSchedules(project.pneumaticSchedule.source);
				for each (var pschedule:PneumaticScheduleItem in schedules) {
					csv.addRecordSet([pschedule["product:partNumber"], pschedule.quantity, ""]);
				}
			}
			
			csv.encode();
						
			var bytes:ByteArray	= new ByteArray();
			bytes.writeUTFBytes(csv.data);
			return bytes;
		}
		
		
		private function expandSchedules(schedules:Array):Array {
			var expanded:Array = new Array();
			
			for each (var schedule:IScheduleItem in schedules) {
				if (schedule.subitems.length > 0) {
					expanded = expanded.concat(expandSchedules(schedule.subitems.toArray()));
				}
				else {
					expanded.push(schedule);
				}
			}
			
			return expanded;
		}
		
		
		public static function getInstance():CSVExporter {
			if (!instance) {
				instance = new CSVExporter();
			}
			return instance;
		}
	}
}