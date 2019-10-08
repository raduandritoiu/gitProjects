package Swizzard.Ordering.Export
{
	import com.as3xls.xls.ExcelFile;
	import com.as3xls.xls.Sheet;
	
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.utils.ByteArray;
	
	import mx.controls.AdvancedDataGrid;
	import mx.controls.Alert;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
	
	import Swizzard.Data.Utils.SynchronousDataUtility;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.UI.Windows.ExportConfirmWindow;
	
	import utils.LogUtils;
	
	
	public class ExcelExporter implements IExportTarget
	{
		private static var instance:ExcelExporter;
		
		private var pendingValveGrid:AdvancedDataGrid;
		private var pendingDamperGrid:AdvancedDataGrid;
		private var pendingPneumGrid:AdvancedDataGrid;
		private var exportType:uint;
		
		
		public function ExcelExporter()
		{}
		
		
		public function exportToExcelFile(valveGrid:AdvancedDataGrid, damperGrid:AdvancedDataGrid, pneumGrid:AdvancedDataGrid):void {
			pendingValveGrid 	= valveGrid;
			pendingDamperGrid 	= damperGrid;
			pendingPneumGrid 	= pneumGrid;
			ExportConfirmWindow.Show(this, ExportAction.TO_EXCEL);
		}
		
		
		public function exportResult(exportFormat:String, type:uint):void {
			if (exportFormat != ExportAction.TO_EXCEL) {
				return;
			}
			exportType = type;
			
			var file:File = new File();
			file.addEventListener(Event.SELECT,	exportFileSelectedHandler);
			file.browseForSave("Save Excel File.");	
		}
		
		
		private function exportFileSelectedHandler(event:Event):void {
			var file:File	= event.target as File;
			file.removeEventListener(Event.SELECT,	exportFileSelectedHandler);
			
			if (file.extension != "xls") {
				file.nativePath += ".xls";
			}			
			
			var bytes:ByteArray		= exportToExcelBytes();
			pendingValveGrid		= null;
			pendingDamperGrid 		= null;
			pendingPneumGrid 		= null;
			var stream:FileStream	= new FileStream();
			
			try {
				stream.open(file, FileMode.WRITE);
				stream.writeBytes(bytes);
			}
			catch (err:Error) {
				Alert.show("Error Writing Excel File");	
			}
			finally {
				stream.close();
			}
		}
		
		
		
		/**
		 * Function to export/write the two schedules on a single Sheet.
		 * It seems that the ExcelFile code is not finished
		 */
		private function exportToExcelBytes():ByteArray {
			var file:ExcelFile		= new ExcelFile();
			var xlsSheet:Sheet 		= new Sheet();
			xlsSheet.name			= "products";
			
			var numRows:uint = 3;
			var numCols:uint = 0;
			var row:uint = 0;
			
			// determin the number of rows and columns for valves
			if (exportType & ExportAction.VALVE_SCHEDULE) {
				var schedules:Array	= expandSchedules(pendingValveGrid.dataProvider.source.source.toArray());
				var columns:Array	= pendingValveGrid.columns.filter(visibleColumnFilter);
				numRows = numRows + 1 + schedules.length;
				numCols = (numCols > columns.length) ? numCols  : columns.length;
			}
			
			// determin the number of rows and columns for dampers
			if (exportType & ExportAction.DAMPER_SCHEDULE) {
				var schedules:Array	= expandSchedules(pendingDamperGrid.dataProvider.source.source.toArray());
				var columns:Array	= pendingDamperGrid.columns.filter(visibleColumnFilter);
				numRows = numRows + 4 + schedules.length;
				numCols = (numCols > columns.length) ? numCols  : columns.length;
			}
			
			// determin the number of rows and columns for dampers
			if (exportType & ExportAction.PNEUMATIC_SCHEDULE) {
				var schedules:Array	= expandSchedules(pendingPneumGrid.dataProvider.source.source.toArray());
				var columns:Array	= pendingPneumGrid.columns.filter(visibleColumnFilter);
				numRows = numRows + 4 + schedules.length;
				numCols = (numCols > columns.length) ? numCols  : columns.length;
			}
			
			// resize the XLS sheet
			xlsSheet.resize(numRows, numCols);
			
			// write the valve schedule
			if (exportType & ExportAction.VALVE_SCHEDULE) {
				var schedules:Array	= expandSchedules(pendingValveGrid.dataProvider.source.source.toArray());
				var columns:Array	= pendingValveGrid.columns.filter(visibleColumnFilter);
				row	= writeToSheet(xlsSheet, schedules, columns, row);
			}
			
			// leave some spaces between them
			if (exportType & ExportAction.VALVE_SCHEDULE) {
				row += 3;
			}
			
			// write the damper schedule
			if (exportType & ExportAction.DAMPER_SCHEDULE) {
				var schedules:Array	= expandSchedules(pendingDamperGrid.dataProvider.source.source.toArray());
				var columns:Array	= pendingDamperGrid.columns.filter(visibleColumnFilter);
				row = writeToSheet(xlsSheet, schedules, columns, row);
			}
			
			// leave some spaces between them
			if (exportType & (ExportAction.VALVE_SCHEDULE || ExportAction.DAMPER_SCHEDULE)) {
				row += 3;
			}
			
			// write the damper schedule
			if (exportType & ExportAction.PNEUMATIC_SCHEDULE) {
				var schedules:Array	= expandSchedules(pendingPneumGrid.dataProvider.source.source.toArray());
				var columns:Array	= pendingPneumGrid.columns.filter(visibleColumnFilter);
				row = writeToSheet(xlsSheet, schedules, columns, row);
			}
			
			file.sheets.addItem(xlsSheet);
			return file.saveToByteArray();
		}
		
		
		/**
		 * Function to export/write the two schedules on a single Sheet.
		 * It seems that the ExcelFile code is not finished
		 */
//		private function exportToExcelBytes_1():ByteArray {
//			var file:ExcelFile		= new ExcelFile();
//			var valveSheet:Sheet 	= new Sheet();
//			valveSheet.name			= "valves";
//			var damperSheet:Sheet 	= new Sheet();
//			damperSheet.name 		= "dampers";
//			var pneumSheet:Sheet 	= new Sheet();
//			pneumSheet.name 		= "pneumatics";
//			
//			var numRows:uint = 3;
//			var numCols:uint = 0;
//			var row:uint = 0;
//			
//			// determin the number of rows and columns for valves
//			if (exportType & ExportAction.VALVE_SCHEDULE) {
//				var schedules:Array	= expandSchedules(pendingValveGrid.dataProvider.source.source.toArray());
//				var columns:Array	= pendingValveGrid.columns.filter(visibleColumnFilter);
//				numRows = numRows + 1 + schedules.length;
//				numCols = (numCols > columns.length) ? numCols  : columns.length;
//			}
//			
//			// determin the number of rows and columns for dampers
//			if (exportType & ExportAction.DAMPER_SCHEDULE) {
//				var schedules:Array	= expandSchedules(pendingDamperGrid.dataProvider.source.source.toArray());
//				var columns:Array	= pendingDamperGrid.columns.filter(visibleColumnFilter);
//				numRows = numRows + 1 + schedules.length;
//				numCols = (numCols > columns.length) ? numCols  : columns.length;
//			}
//			
//			// determin the number of rows and columns for dampers
//			if (exportType & ExportAction.PNEUMATIC_SCHEDULE) {
//				var schedules:Array	= expandSchedules(pendingPneumGrid.dataProvider.source.source.toArray());
//				var columns:Array	= pendingPneumGrid.columns.filter(visibleColumnFilter);
//				numRows = numRows + 1 + schedules.length;
//				numCols = (numCols > columns.length) ? numCols  : columns.length;
//			}
//			
//			// resize the XLS sheet
//			valveSheet.resize(numRows, numCols);
//			damperSheet.resize(numRows, numCols);
//			pneumSheet.resize(numRows, numCols);
//			
//			// write the valve schedule
//			if (exportType & ExportAction.VALVE_SCHEDULE) {
//				var schedules:Array	= expandSchedules(pendingValveGrid.dataProvider.source.source.toArray());
//				var columns:Array	= pendingValveGrid.columns.filter(visibleColumnFilter);
//				row	= writeToSheet(valveSheet, schedules, columns, row);
//			}
//			
//			// leave some spaces between them
//			if (exportType & ExportAction.VALVE_SCHEDULE) {
//				row += 3;
//				row = 0;
//			}
//			
//			// write the damper schedule
//			if (exportType & ExportAction.DAMPER_SCHEDULE) {
//				var schedules:Array	= expandSchedules(pendingDamperGrid.dataProvider.source.source.toArray());
//				var columns:Array	= pendingDamperGrid.columns.filter(visibleColumnFilter);
//				row = writeToSheet(damperSheet, schedules, columns, row);
//			}
//			
//			// leave some spaces between them
//			if (exportType & (ExportAction.VALVE_SCHEDULE || ExportAction.DAMPER_SCHEDULE)) {
//				row += 3;
//				row = 0;
//			}
//			
//			// write the damper schedule
//			if (exportType & ExportAction.PNEUMATIC_SCHEDULE) {
//				var schedules:Array	= expandSchedules(pendingPneumGrid.dataProvider.source.source.toArray());
//				var columns:Array	= pendingPneumGrid.columns.filter(visibleColumnFilter);
//				row = writeToSheet(pneumSheet, schedules, columns, row);
//			}
//			
//			file.sheets.addItem(valveSheet);
//			file.sheets.addItem(damperSheet);
//			file.sheets.addItem(pneumSheet);
//			return file.saveToByteArray();
//		}
		
		
		/**
		 * Function writes the rows and column into a sheet, from the given row/offset.
		 */
		private function writeToSheet(sheet:Sheet, rows:Array, columns:Array, row:uint):uint {
			setHeaderRow(sheet, columns , row);
			row++;
			
			for (var i:uint = 0; i < rows.length; i++) {
				var schedule:IScheduleItem	= rows[i] as IScheduleItem;
				if (schedule.product && !schedule.product.documents) {
					var util:SynchronousDataUtility	= new SynchronousDataUtility();
					util.hydrateProductDocuments(schedule.product);
					util.dispose();
				}
				
				setSheetItem(sheet, row++, schedule, columns);
			}
			
			return row;
		}
		
		
		/**
		 * Function to flattenthe hierarhical tree-like data schedule into an array.
		 */
		private function expandSchedules(schedules:Array):Array {
			var expanded:Array	= new Array();
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
		
		
		private function visibleColumnFilter(item:AdvancedDataGridColumn, index:int, arr:Array):Boolean {
			if (item.dataField.indexOf("dataSheetLink") > -1) {
				return false;
			}
			return item.visible;
		}
		
		
		private function setHeaderRow(sheet:Sheet, columns:Array, row:uint):void {
			for (var i:uint = 0; i < columns.length; i++) {
				var column:AdvancedDataGridColumn	= columns[i] as AdvancedDataGridColumn;
				sheet.setCell(row, i, column.headerText.replace(new RegExp("Δ", "gi"), "Delta"));  
			}
		}
		
		
		private function setSheetItem(sheet:Sheet, row:uint, item:IScheduleItem, columns:Array):void {
			for (var i:uint = 0; i < columns.length; i++) {
				var column:AdvancedDataGridColumn	= columns[i] as AdvancedDataGridColumn;
				trace ("  header: " + column.headerText + "  - field" + column.dataField);
				try {
					var value:String;
					if (column.labelFunction != null) {
						value	= column.labelFunction.apply(null, [item, column]);
					}
					else {
						switch (column.headerText) {
							case "gpm":
								value	= item["formattedGpm"];
								break;
							
							case "requiredPressureDrop":
								value	= item["formattedRequiredPressureDrop"];
								break;
							
							case "actualPressureDrop":
								value	= item["formattedActualPressureDrop"];
								break;
							
							case "calculatedCv":
								value	= item["formattedCalculatedCv"];
								break;

//							column = createMXColumn("CvB", "formattedCv_B", "alignCenter", false, 50);
//							newColumns.push(column);
//
//							column = createMXColumn("Calculated Cv B", "formattedCalculatedCv_B", "alignCenter", false, 120);
//							newColumns.push(column);
//							
//							column = createMXColumn("Required ΔP B", "formattedRequiredPressureDrop_B", "alignCenter", false);
//							newColumns.push(column);
//							
//							column = createMXColumn("Actual ΔP B", "formattedActualPressureDrop_B", "alignCenter", false);
//							newColumns.push(column);
//							
							default:
								value	= item[column.dataField];
						}
					}
					
					if (value) {
						sheet.setCell(row, i, value);
					}
				}
				catch (err:Error) {
					LogUtils.Error("Error in setting cell");
				}
			} 
			
			/* sheet.setCell(row, 0, 	item.partNumber);
			sheet.setCell(row, 1, 	item.quantity);
			sheet.setCell(row, 2, 	item.tags);
			sheet.setCell(row, 3,	item.mediumString);
			sheet.setCell(row, 4, 	item.patternString);
			sheet.setCell(row, 5, 	item.size);
			sheet.setCell(row, 6, 	item.cvu);	
			sheet.setCell(row, 7, 	item.notes);
			sheet.setCell(row, 8,	item.price); */
		}
		
		
		public static function getInstance():ExcelExporter {
			if (!instance) {
				instance = new ExcelExporter();
			}
			return instance;
		}
	}
}