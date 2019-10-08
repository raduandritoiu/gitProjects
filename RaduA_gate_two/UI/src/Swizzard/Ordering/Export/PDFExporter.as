package Swizzard.Ordering.Export
{
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.html.HTMLLoader;
	import flash.net.URLRequest;
	import flash.system.Capabilities;
	import flash.utils.ByteArray;
	
	import mx.controls.AdvancedDataGrid;
	import mx.controls.Alert;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
	import mx.core.UIComponent;
	
	import Swizzard.Ordering.UI.PDFPreviewWindow;
	import Swizzard.System.Models.CompanyInformation;
	import Swizzard.System.Models.ValveScheduleItem;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.UI.Windows.ExportConfirmWindow;
	
	import org.alivepdf.colors.RGBColor;
	import org.alivepdf.data.Grid;
	import org.alivepdf.data.GridColumn;
	import org.alivepdf.drawing.Joint;
	import org.alivepdf.fonts.CoreFont;
	import org.alivepdf.fonts.FontFamily;
	import org.alivepdf.layout.Orientation;
	import org.alivepdf.layout.Size;
	import org.alivepdf.layout.Unit;
	import org.alivepdf.pdf.PDF;
	import org.alivepdf.saving.Method;
	
	import utils.LogUtils;
	
	
	public class PDFExporter implements IExportTarget
	{
		private static var instance:PDFExporter;
		
		private var pendingValveSchedule:AdvancedDataGrid;
		private var pendingDamperSchedule:AdvancedDataGrid;
		private var pendingPneumSchedule:AdvancedDataGrid;
		private var companyInfos:Array;
		private var exportType:uint;
		private var openAfterSave:Boolean;
		
		
		public function PDFExporter()
		{}
		
		
		private function generatePDFPreview(valveSchedule:AdvancedDataGrid, damperSchedule:AdvancedDataGrid, pneumSchedule:AdvancedDataGrid, newInfos:Array):void {
			/* - this feature has been disabled, for now - */
			//	checking if the PDF reader is installed on the system
			if (false) //HTMLLoader.pdfCapability == HTMLPDFCapability.STATUS_OK)
			{
				//pendingSchedule = schedule;
				var bytes:ByteArray	= exportToPDFBytes(valveSchedule);
				//pendingSchedule = null;
				
				//	create temp folder and file to
				//	keep the temp PDF
				var folder:File		= File.createTempDirectory();
				var tempFile:File	= folder.resolvePath("Untitled.pdf");
				
				//	writing PDF to the temp FILE
				var stream:FileStream = new FileStream();
				try {
					stream.open(tempFile, FileMode.WRITE);
					stream.writeBytes(bytes);
				}
				catch (err:Error) {
					Alert.show("Error Writing PDF File");
					return;
				}
				finally {
					stream.close();
				}
				
				//	invoking the PDF preview
				var htmlLdr:HTMLLoader	= new HTMLLoader();
				var urlReq:URLRequest	= new URLRequest(tempFile.nativePath);
				htmlLdr.width			= 680;
				htmlLdr.height			= 500;
				htmlLdr.load(urlReq);
				
				//	wrapping the content in a UIComponent
				var pdfHolder:UIComponent = new UIComponent();
				pdfHolder.addChild(htmlLdr);
				
				//	displaying PDF preview on Stage.
				var pw:PDFPreviewWindow	= PDFPreviewWindow.Show();
				pw.pdf = pdfHolder;
			}
			/* - this feature is all that is left, for now - */
			else {
				//	if PDF reader is not installed on the system
				//	ask user to just save the PDF
				//	after you let him/her/it know what has happened
				exportToPDFFile(valveSchedule, damperSchedule, pneumSchedule, newInfos, true);
			}
		}
		
		
		public function exportToPDFFile(valveSchedule:AdvancedDataGrid, damperSchedule:AdvancedDataGrid, pneumSchedule:AdvancedDataGrid, newInfos:Array, openAfter:Boolean = false):void {
			pendingValveSchedule 	= valveSchedule;
			pendingDamperSchedule 	= damperSchedule;
			pendingPneumSchedule 	= pneumSchedule;
			companyInfos 			= newInfos;
			openAfterSave 			= openAfter;
			ExportConfirmWindow.Show(this, ExportAction.TO_PDF);
		}
		
		
		public function exportResult(exportFormat:String, type:uint):void {
			if (exportFormat != ExportAction.TO_PDF) {
				return;
			}
			exportType = type;
			
			var file:File = new File();
			file.addEventListener(Event.SELECT,	exportFileSelectedHandler);
			file.browseForSave("Save PDF File.");	
		}
		
		
		private function exportFileSelectedHandler(event:Event):void {
			var file:File = event.target as File;
			file.removeEventListener(Event.SELECT,	exportFileSelectedHandler);
			
			if (file.extension != "pdf") {
				file.nativePath += ".pdf";
			}			
			
			var bytes:ByteArray		= exportToPDFBytes(pendingValveSchedule, pendingDamperSchedule, pendingPneumSchedule);
			pendingValveSchedule	= null;
			var stream:FileStream 	= new FileStream();
			
			try {
				stream.open(file, FileMode.WRITE);
				stream.writeBytes(bytes);
			}
			catch (err:Error) {
				Alert.show("Error Writing PDF File");
			}
			finally {
				stream.close();
			}
			
			if (openAfterSave) {
				openAfterSave = false;
				try {
					Object(file).openWithDefaultApplication();
				}
				catch (err:Error) {
					// Open Failed
					LogUtils.Error("Open Failed");
				}
			}
		}
		
		
		private function exportToPDFBytes(valveSchedule:AdvancedDataGrid, damperSchedule:AdvancedDataGrid, pneumSchedule:AdvancedDataGrid):ByteArray {
			var baseFont:CoreFont	= new CoreFont(FontFamily.ARIAL);
			var textColor:RGBColor	= new RGBColor(0x000000);
			
			// calculate the size of the bigger grid
			var totalMM:Number = 0;
			var tmpMM:Number = 0;
			
			
			// first the valve grid
			for each (var c:AdvancedDataGridColumn in valveSchedule.columns) {
				if  ((!c.visible) || (c.headerText.indexOf("Data Sheet") > -1))
					continue;
				// We end up calculating this twice because of the read only property
				var mm:Number = (c.width / Capabilities.screenDPI) * 25.4;
				tmpMM += mm; 
			}
			if ((exportType & ExportAction.VALVE_SCHEDULE) && (totalMM < tmpMM)) {
				totalMM = tmpMM;
			}
			// then the damper grid
			tmpMM = 0;
			for each (var c:AdvancedDataGridColumn in damperSchedule.columns) {
				if  ((!c.visible) || (c.headerText.indexOf("Data Sheet") > -1))
					continue;
				// We end up calculating this twice because of the read only property
				var mm:Number = (c.width / Capabilities.screenDPI) * 25.4;
				tmpMM += mm; 
			}
			if ((exportType & ExportAction.DAMPER_SCHEDULE) && (totalMM < tmpMM)) {
				totalMM = tmpMM;
			}
			// then the pneumatic grid
			tmpMM = 0;
			for each (var c:AdvancedDataGridColumn in pneumSchedule.columns) {
				if  ((!c.visible) || (c.headerText.indexOf("Data Sheet") > -1))
					continue;
				// We end up calculating this twice because of the read only property
				var mm:Number = (c.width / Capabilities.screenDPI) * 25.4;
				tmpMM += mm; 
			}
			if ((exportType & ExportAction.PNEUMATIC_SCHEDULE) && (totalMM < tmpMM)) {
				totalMM = tmpMM;
			}
			
			
			totalMM	+= 20;	// for the margins which are default to 1cm
			var totalIN:Number	= (11.7 / 297) * totalMM;
			var totalPX:Number	= (841.89 / 297) * totalMM;
			var pageSize:Size	= new Size([1190.55, totalPX], "J2", [16.5, totalIN], [420, totalMM]);
			
			// create the PDF
			var pdf:PDF	= new PDF(Orientation.LANDSCAPE, Unit.MM, pageSize);
			pdf.setAutoPageBreak(true, 0);
			pdf.setMargins(16.8, 16.8, 16.8, 16.8);
			pdf.addPage();
			pdf.setFont(baseFont);
			pdf.textStyle(textColor);
			
			// write User Info
			var userInfo:CompanyInformation	= companyInfos[0] as CompanyInformation;
			if (userInfo) {
				pdf.writeText(6, userInfo.toString());
				pdf.newLine(15);
			}
			
			// write Customer Info
			var customerInfo:CompanyInformation	= companyInfos[1] as CompanyInformation;				
			if (customerInfo) {
				pdf.writeText(6, customerInfo.toString());
				pdf.newLine(6);
			}
			
			pdf.setFont(new CoreFont(), 9);
			
			
			// write Valve Schedule Grid
			if (exportType & ExportAction.VALVE_SCHEDULE) {
				var grid:Grid = generateSchedulePDFGrid(valveSchedule, pdf.getCurrentPage().width, pdf.getCurrentPage().height);
				pdf.addGrid(grid, 0, 20);
			}
			// write Damper Schedule Grid
			if (exportType & ExportAction.DAMPER_SCHEDULE) {
				var grid:Grid = generateSchedulePDFGrid(damperSchedule, pdf.getCurrentPage().width, pdf.getCurrentPage().height);
				pdf.addGrid(grid, 0, 20);
			}
			// write Pneumatic Schedule Grid
			if (exportType & ExportAction.PNEUMATIC_SCHEDULE) {
				var grid:Grid = generateSchedulePDFGrid(pneumSchedule, pdf.getCurrentPage().width, pdf.getCurrentPage().height);
				pdf.addGrid(grid, 0, 20);
			}
			
			
			pdf.end();
			return pdf.save(Method.LOCAL) as ByteArray;
		}
		
		
		private function generateSchedulePDFGrid(schedule:AdvancedDataGrid, pageWidth:Number, pageHeight:Number):Grid {
			var headerColor:RGBColor		= new RGBColor(0xACACAC);
			var cellColor:RGBColor			= new RGBColor(0xCFCFCF);
			var borderColor:RGBColor		= new RGBColor(0x999999);
			
			var columns:Array		= new Array();
			
			/* var totalMM:Number	= 0;
			// Calculate Width Scale Factor to fit page.
			// Had to do this first since GridColumn.width is read only #Useless
			for each (var c:AdvancedDataGridColumn in schedule.columns) {
				if  ((!c.visible) || (c.headerText.indexOf("Data Sheet") > -1))
					continue;
				// We end up calculating this twice because of the read only property
				var mm:Number = (c.width / Capabilities.screenDPI) * 25.4;
				totalMM	+= mm; 
			}
			var totalWidth:Number	= (841.89 / 297) * totalMM;

			// Recalculate widths to fit page
			var scaleFactor_tst:Number = (pageWidth - 20) / totalMM; */
			
//			var scaleFactor:Number = 1;
			
			for (var i:uint = 0; i < schedule.columns.length; i++) {
				var gridColumn:AdvancedDataGridColumn = schedule.columns[i] as AdvancedDataGridColumn;
				if (!gridColumn.visible)
					continue;
				
				// ---------------------------- Pixels Per Inch (width in inches) x MM per Inch
				var widthInMM:Number = (gridColumn.width / Capabilities.screenDPI) * 25.4;
//				widthInMM			*= scaleFactor;
				
				var headerText:String		= gridColumn.headerText.replace(new RegExp("Δ", "gi"), "Delta");
				var headerTextWidth:Number 	= gridColumn.headerText.length;
				var column:GridColumn;
				
				switch (gridColumn.dataField) {
					case "price":
						column	= new GridColumn(headerText, "formattedPrice", widthInMM, "C", "R");
						break;
					
					case "calculatedCv":
						column	= new GridColumn(headerText, "formattedCalculatedCv", widthInMM, "C");
						break;
					
					case "actualPressureDrop":
						column	= new GridColumn(headerText, "formattedActualPressureDrop", widthInMM, "C");
						break;
					
					case "gpm":
						column	= new GridColumn(headerText, "formattedGpm", widthInMM, "C");
						break;
					
					case "requiredPressureDrop":
						column	= new GridColumn(headerText, "formattedRequiredPressureDrop", widthInMM, "C");
						break;
					
					case "dataSheetLink":
					case "actuator:dataSheetLink":
					case "valve:dataSheetLink":
					case "product:dataSheetLink":
						continue;
						break;
					
					default:
						column	= new GridColumn(headerText, gridColumn.dataField, widthInMM, "C");
				}
				
				columns.push(column);
			}
												
			var schedules:Array	= expandSchedule(schedule.dataProvider.source.source.toArray());
			var grid:Grid = new Grid(schedules, pageWidth, pageHeight, headerColor, cellColor, true, borderColor, 1, Joint.ROUND, columns);
			
			return grid; 
		}
		
		
		private function createPDFGridColumn(gridColumn:AdvancedDataGridColumn):GridColumn {
			return null;
		}
		
		
		private function expandSchedule(schedule:Array):Array {
			var expanded:Array = new Array();
			
			for each (var scheduleItem:IScheduleItem in schedule) {
				if ((scheduleItem is ValveScheduleItem) && (scheduleItem.quantity > 1)) {
					expanded = expanded.concat(expandSchedule(scheduleItem.subitems.toArray()));
				}
				else {
					expanded.push(scheduleItem);
				}
			}
			return expanded;
		}
		
		
		
		
		public static function getInstance():PDFExporter {
			if (!instance) {
				instance = new PDFExporter();
			}
			return instance;
		}
	}
}