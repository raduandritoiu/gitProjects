package Swizzard.UI.Components.DataGridClasses
{
	import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
	import mx.core.ClassFactory;
	import mx.formatters.NumberBaseRoundType;
	import mx.formatters.NumberFormatter;
	
	import spark.components.gridClasses.GridColumn;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.UI.Renderers.PDFColumnRenderer;
	import Swizzard.Data.UI.Renderers.PDFGridItemRenderer;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.System.Utils.SaveableAdvancedGridColumn;
	import Swizzard.System.Utils.SaveableGridColumn;
	import Swizzard.UI.Components.GridRenderers.ValveActualDeltaRenderer;
	import Swizzard.UI.Components.GridRenderers.ValveActualDeltaRenderer_B;
	import Swizzard.UI.Components.GridRenderers.ValveCvRenderer_B;
	
	
	public class GridColumnsUtil
	{
		static public var gridLabelMultiplier:Number = 1;
		static public var numberFormatter:NumberFormatter;
		static public var usNumberFormatter:NumberFormatter;
		static public var currencyFormatter:NumberFormatter;
		
		
		static public function generateColumnsInfo():void {
			currencyFormatter = new NumberFormatter();
			currencyFormatter.precision	= 2;
			currencyFormatter.useThousandsSeparator	= true;
			
			numberFormatter	= new NumberFormatter();
			numberFormatter.precision = 2;
			numberFormatter.rounding = NumberBaseRoundType.NEAREST;

			usNumberFormatter = new NumberFormatter();
			usNumberFormatter.precision	= 0;
			usNumberFormatter.useThousandsSeparator	= true;
		}
		
		
		static public function torqueLabelFunction(item:Object, column:Object):String {
			var torque:Number;
			if (item is IScheduleItem) {
				torque = item.product.torque;
			}
			else {
				torque = item.torque;
			}
			if (isNaN(torque)) {
				return null;
			}
			return torque + " lb-in";
		}
		
		static public function thrust18LabelFunction(item:Object, column:Object):String {
			var thrust:Number;
			if (item is IScheduleItem) {
				thrust = item.product.maxThrust_18
			}
			else {
				thrust = item.maxThrust_18;
			}
			if (isNaN(thrust)) {
				return null;
			}
			if (thrust < 0) {
				return "see data sheet"
			}
			return thrust + " lb";
		}
		
		static public function thrust0LabelFunction(item:Object, column:Object):String {
			var thrust:Number;
			if (item is IScheduleItem) {
				thrust = item.product.maxThrust_no
			}
			else {
				thrust = item.maxThrust_no;
			}
			if (isNaN(thrust)) {
				return null;
			}
			if (thrust < 0) {
				return "see data sheet"
			}
			return thrust + " lb";
		}
		
		static public function priceLabelFunction(item:Object, column:Object):String {
			var price:Number;
			if (item is IScheduleItem) {
				price = item.price;
			}
			// for everything else multiply
			else {
				price = item.totalPrice * gridLabelMultiplier;
			}
			if (price < 0) {
				return "Assembly";
			}
			return "$" + currencyFormatter.format(price);
		}
		
		
		static public function priceSortFunction(item1:Object, item2:Object, column:GridColumn = null):int {
			if (item1.price == item2.price) {
				return 0;
			}
			else if (item1.price > item2.price) {
				return 1;
			}
			return -1;
		}
		
		
		static public function dataSheetTipFunction(item:AbstractSiemensProduct, column:Object):String {
			return "Data Sheet for " + item.partNumber;
		}
		
		
		static public function setPreferencesToColumns(gridColumns:Array, columnInfos:Array):void {
			if (gridColumns == null) return;
			if (columnInfos == null) return;
			if (gridColumns.length == 0) return;
			if (columnInfos.length == 0) return;
			
			for each(var gridColumn:GridColumn in gridColumns) {
				for each(var columnInfo:SaveableGridColumn in columnInfos) {
					if (gridColumn.dataField == columnInfo.dataField) {
						columnInfo.toColumn(gridColumn);
					}
				}
			}
		}
		
		
		static public function setPreferencesToMXColumns(gridColumns:Array, columnInfos:Array):void {
			if (gridColumns == null) return;
			if (columnInfos == null) return;
			if (gridColumns.length == 0) return;
			if (columnInfos.length == 0) return;
			
			for each(var gridColumn:AdvancedDataGridColumn in gridColumns) {
				for each(var columnInfo:SaveableAdvancedGridColumn in columnInfos) {
					if (gridColumn.dataField == columnInfo.dataField) {
						columnInfo.toColumn(gridColumn);
					}
				}
			}
		}
		
		static public function getValveGridColumns():Array {
			var column:GridColumn;
			var newColumns:Array = new Array();
			
			column = createColumn("Part #", "partNumber", 75);
			newColumns.push(column);
			
			column = createColumn("Type", "typeString", 60);
			newColumns.push(column);
			
			column = createColumn("Size", "size", 40);
			newColumns.push(column);
			
			column = createColumn("Cv", "cv", 40);
			newColumns.push(column);
			
			column = createColumn("Actual ΔP", "actualDeltaP", 75);
			column.itemRenderer = new ClassFactory(ValveActualDeltaRenderer);
			newColumns.push(column);
			
			column = createColumn("CvB", "cvb", 40);
			column.itemRenderer = new ClassFactory(ValveCvRenderer_B);
			newColumns.push(column);
			
			column = createColumn("Actual ΔP B", "actualDeltaPB", 75);
			column.itemRenderer = new ClassFactory(ValveActualDeltaRenderer_B);
			newColumns.push(column);
			
			column = createColumn("Min GPM", "minGPM", 60);
			newColumns.push(column);
			
			column = createColumn("Max GPM", "maxGPM", 60);
			newColumns.push(column);
			
			column = createColumn("Preset GPM", "presetGPM", 75);
			newColumns.push(column);
			
			column = createColumn("Description", "description", -1);
			column.minWidth = 120;
			newColumns.push(column);
			
			column = createColumn("Port Config", "portConfigurationString", 120);
			newColumns.push(column);
			
			column = createColumn("Price", "price", 70);
			column.labelFunction = priceLabelFunction;
			column.sortCompareFunction = priceSortFunction;
			newColumns.push(column);
			
			column = createColumn("Data Sheet", "dataSheet", 75);
			column.sortable = false;
			column.dataTipFunction = dataSheetTipFunction;
			column.itemRenderer = new ClassFactory(PDFGridItemRenderer);
			newColumns.push(column);
			
			return newColumns;
		}
		
		
		static public function getActuatorGridColumn():Array {
			var column:GridColumn;
			var newColumns:Array = [];
			
			column = createColumn("Part #", "partNumber", 75);
			newColumns.push(column);
			
			column = createColumn("Signal", "controlSignalString", 70);
			newColumns.push(column);
			
			column = createColumn("Description", "description", -1);
			column.minWidth = 120;
			newColumns.push(column);
			
			column = createColumn("Close Off", "closeOff", 70);
			newColumns.push(column);
			
			column = createColumn("Price", "price", 70);
			column.labelFunction = priceLabelFunction;
			column.sortCompareFunction = priceSortFunction;
			newColumns.push(column);
			
			column = createColumn("Data Sheet", "", 75);
			column.sortable = false;
			column.dataTipFunction = dataSheetTipFunction;
			column.itemRenderer = new ClassFactory(PDFGridItemRenderer);
			newColumns.push(column);
			
			return newColumns;
		}
		
		
		static public function getDamperGridColumn():Array {
			var column:GridColumn;
			var newColumns:Array = [];
			
			column = createColumn("Part #", "partNumber", 90);
			newColumns.push(column);
			
			column = createColumn("Type", "typeString", 125);
			newColumns.push(column);
			
			column = createColumn("Torque", "torque", 70);
			column.labelFunction = torqueLabelFunction;
			newColumns.push(column);
			
			column = createColumn("Control Signal", "controlSignalString", 110);
			newColumns.push(column);
			
			column = createColumn("System Supply", "systemSupplyString", 70);
			newColumns.push(column);
			
			column = createColumn("Plenum Rating", "plenumRatingString", 70);
			newColumns.push(column);
			
			column = createColumn("Auxilary Switch", "auxilarySwitchString", 70);
			newColumns.push(column);
			
			column = createColumn("Position Feedback", "positionFeedbackString", 50);
			newColumns.push(column);
			
			column = createColumn("Scalable Ctrl Signal", "scalableSignalString", 50);
			newColumns.push(column);
			
			column = createColumn("Description", "description", -1);
			column.minWidth = 120;
			newColumns.push(column);
			
			column = createColumn("Price", "price", 70);
			column.labelFunction = priceLabelFunction;
			column.sortCompareFunction = priceSortFunction;
			newColumns.push(column);
			
			column = createColumn("Data Sheet", "dataSheet", 190);
			column.sortable = false;
			column.dataTipFunction = dataSheetTipFunction;
			column.itemRenderer = new ClassFactory(PDFGridItemRenderer);
			newColumns.push(column);
			
			return newColumns;
		}
		
		
		static public function getAccessoryGridColumn():Array {
			var column:GridColumn;
			var newColumns:Array = [];
			
			column = createColumn("Part #", "partNumber", 75);
			newColumns.push(column);
			
			column = createColumn("Description", "description", -1);
			column.minWidth = 120;
			newColumns.push(column);
			
			column = createColumn("Price", "price", 70);
			column.labelFunction = priceLabelFunction;
			column.sortCompareFunction = priceSortFunction;
			newColumns.push(column);
			
			column = createColumn("Data Sheet", "dataSheet", 75);
			column.sortable = false;
			column.dataTipFunction = dataSheetTipFunction;
			column.itemRenderer = new ClassFactory(PDFGridItemRenderer);
			newColumns.push(column);
			
			return newColumns;
		}
		
		
		static public function getPneumaticGridColumn():Array {
			var column:GridColumn;
			var newColumns:Array = [];
			
			column = createColumn("Part #", "partNumber", 75);
			newColumns.push(column);
			
			column = createColumn("Size", "typeString", 70);
			newColumns.push(column);
			
			column = createColumn("Description", "description", -1);
			column.minWidth = 120;
			newColumns.push(column);
			
			column = createColumn("Stroke Length", "strokeString", 70);
			newColumns.push(column);
			
			column = createColumn("Spring Range", "springRangeString", 70);
			newColumns.push(column);
			
			column = createColumn("Thrust @18psi", "maxThrust_18", 70);
			column.labelFunction = thrust18LabelFunction;
			newColumns.push(column);
			
			column = createColumn("Thrust @0psi", "maxThrust_no", 80);
			column.labelFunction = thrust0LabelFunction;
			newColumns.push(column);
			
			column = createColumn("UL Listed", "ULCertString", 70);
			newColumns.push(column);
			
			column = createColumn("Actuator", "isActuatorString", 70);
			newColumns.push(column);
			
			column = createColumn("Clevis", "clevisString", 70);
			newColumns.push(column);
			
			column = createColumn("Bracket", "bracketString", 70);
			newColumns.push(column);
			
			column = createColumn("Ball Joint", "ballJointString", 70);
			newColumns.push(column);
			
			column = createColumn("Pivot", "pivotString", 70);
			newColumns.push(column);
			
			column = createColumn("Positionning", "posRelayString", 70);
			newColumns.push(column);
			
			column = createColumn("Fwd Stops", "fwdTravelStopsString", 60);
			newColumns.push(column);
			
			
			column = createColumn("Mounting", "mountingStyleString", 80);
			newColumns.push(column);
			
			column = createColumn("Price", "price", 70);
			column.labelFunction = priceLabelFunction;
			column.sortCompareFunction = priceSortFunction;
			newColumns.push(column);
			
			column = createColumn("Data Sheet", "dataSheet", 75);
			column.sortable = false;
			column.dataTipFunction = dataSheetTipFunction;
			column.itemRenderer = new ClassFactory(PDFGridItemRenderer);
			newColumns.push(column);
			
			return newColumns;
		}
		
		
		// ----------- Schedules ----------------------------------------
		
		static public function getValveScheduleColumn():Array {
			var newColumns:Array = [];
			var column:AdvancedDataGridColumn;
			
			column = createMXColumn("Part Number", "actualPartNumber", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("Qty", "quantity", "alignCenter", true, 50);
			column.sortable = true;
			newColumns.push(column);
			
			column = createMXColumn("Type", "valve:typeString", "alignCenter", true, 60);
			newColumns.push(column);
			
			column = createMXColumn("Tag", "tags", "alignCenter");
			newColumns.push(column);
			
			column = createMXColumn("Valve Size", "valve:size", "alignCenter", false, 100);
			newColumns.push(column);
			
			column = createMXColumn("Cv", "valve:cv", "alignCenter", false, 50);
			newColumns.push(column);
			
			column = createMXColumn("CvB", "formattedCv_B", "alignCenter", false, 50);
			newColumns.push(column);
			
			column = createMXColumn("Min GPM", "valve:minGPM", "alignCenter", true, 75);
			newColumns.push(column);
			
			column = createMXColumn("Max GPM", "valve:maxGPM", "alignCenter", true, 75);
			newColumns.push(column);
			
			column = createMXColumn("Preset GPM", "valve:presetGPM", "alignCenter", true, 85);
			newColumns.push(column);
			
			column = createMXColumn("Port Config", "valve:portConfigurationString", "alignCenter", false, 120);
			newColumns.push(column);
			
			column = createMXColumn("Trim", "valve:plugMaterialString", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("Multiplier", "priceMultiplier", "alignCenter", true);
			column.setStyle("textAlign", "center");
			newColumns.push(column);
			
			column = createMXColumn("Price", "price", "alignCenter", false);
			column.labelFunction 		= priceLabelFunction;
			column.sortCompareFunction 	= priceSortFunction;
			column.setStyle("textAlign", "right");
			column.setStyle("fontWeight", "bold");
			newColumns.push(column);
			
			column = createMXColumn("Description", "description", "alignCenter", false, 160);
			newColumns.push(column);
			
			column = createMXColumn("Assembly Data Sheet", "dataSheetLink", "alignCenter", false, 160);
			column.itemRenderer = new ClassFactory(PDFColumnRenderer);
			column.dataTipFunction = dataSheetTipFunction;
			column.resizable = false;
			column.sortable = false;
			newColumns.push(column);
			
			column = createMXColumn("Pressure Class", "valve:maxPress", "alignCenter", false, 120);
			newColumns.push(column);
			
			column = createMXColumn("Medium", "valve:mediumString", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("Flow Char", "valve:flowCharString", "alignCenter", false, 100);
			newColumns.push(column);
			
			column = createMXColumn("Calculated Cv", "formattedCalculatedCv", "alignCenter", false, 120);
			newColumns.push(column);
			
			column = createMXColumn("Required ΔP", "formattedRequiredPressureDrop", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("Actual ΔP", "formattedActualPressureDrop", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("Calculated Cv B", "formattedCalculatedCv_B", "alignCenter", false, 120);
			newColumns.push(column);
			
			column = createMXColumn("Required ΔP B", "formattedRequiredPressureDrop_B", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("Actual ΔP B", "formattedActualPressureDrop_B", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("GPM", "formattedGpm", "alignCenter", true, 60);
			newColumns.push(column);
			
			column = createMXColumn("Actuator Part#", "actuator:actualPartNumber", "alignCenter", false, 120);
			newColumns.push(column);
			
			column = createMXColumn("Actuator Data Sheet", "actuator:dataSheetLink", "alignCenter", false, 140);
			column.itemRenderer = new ClassFactory(PDFColumnRenderer);
			column.dataTipFunction = dataSheetTipFunction;
			column.resizable = false;
			column.sortable = false;
			newColumns.push(column);
			
			column = createMXColumn("Fail Position", "actuator:safetyFunctionString", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("Close Off Pressure", "closeOff", "alignCenter", false, 150);
			newColumns.push(column);
			
			column = createMXColumn("Valve Part#", "valve:partNumber", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("Valve Data Sheet", "valve:dataSheetLink", "alignCenter", false, 150);
			column.itemRenderer = new ClassFactory(PDFColumnRenderer);
			column.dataTipFunction = dataSheetTipFunction;
			column.resizable = false;
			column.sortable = false;
			newColumns.push(column);
			
			column = createMXColumn("Notes", "notes", "alignCenter");
			newColumns.push(column);
			
			return newColumns;
		}
		
		
		static public function getDamperScheduleColumn():Array {
			var newColumns:Array = [];
			var column:AdvancedDataGridColumn;
			
			column = createMXColumn("Part Number", "product:partNumber", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("Qty", "quantity", "alignCenter", true, 50);
			newColumns.push(column);
			
			column = createMXColumn("Type", "product:typeString", "alignCenter", false, 130);
			newColumns.push(column);
			
			column = createMXColumn("Torque", "damper:torque", "alignCenter", false, 80);
			column.labelFunction = torqueLabelFunction;
			newColumns.push(column);
			
			column = createMXColumn("Control Signal", "damper:controlSignalString", "alignCenter", false, 120);
			newColumns.push(column);
			
			column = createMXColumn("System Supply", "damper:systemSupplyString", "alignCenter", false, 70);
			newColumns.push(column);
			
			column = createMXColumn("Plenum Rating", "damper:plenumRatingString", "alignCenter", false, 70);
			newColumns.push(column);
			
			column = createMXColumn("Auxilary Switch", "damper:auxilarySwitchString", "alignCenter", false, 70);
			newColumns.push(column);
			
			column = createMXColumn("Position Feedback", "damper:positionFeedbackString", "alignCenter", false, 50);
			newColumns.push(column);
			
			column = createMXColumn("Scalable Ctrl Signal", "damper:scalableSignalString", "alignCenter", false, 50);
			newColumns.push(column);
			
			column = createMXColumn("Multiplier", "priceMultiplier", "alignCenter", true);
			column.setStyle("textAlign", "center");
			newColumns.push(column);
			
			column = createMXColumn("Price", "price", "alignCenter", false);
			column.labelFunction 		= priceLabelFunction;
			column.sortCompareFunction 	= priceSortFunction;
			column.setStyle("textAlign", "right");
			column.setStyle("fontWeight", "bold");
			newColumns.push(column);
			
			column = createMXColumn("Description", "product:description", "alignCenter", false, 200);
			newColumns.push(column);
			
			column = createMXColumn("Data Sheet", "product:dataSheetLink", "alignCenter", false, 100);
			column.itemRenderer = new ClassFactory(PDFColumnRenderer);
			column.resizable = false;
			column.sortable = false;
			newColumns.push(column);
			
			column = createMXColumn("Notes", "notes", "alignCenter");
			newColumns.push(column);
			
			return newColumns;
		}
		
		
		static public function getPneumaticScheduleColumn():Array {
			var newColumns:Array = [];
			var column:AdvancedDataGridColumn;
			
			column = createMXColumn("Part Number", "product:partNumber", "alignCenter", false);
			newColumns.push(column);
			
			column = createMXColumn("Qty", "quantity", "alignCenter", true, 50);
			newColumns.push(column);
			
			column = createMXColumn("Type", "product:typeString", "alignCenter", false, 130);
			newColumns.push(column);
			
			column = createMXColumn("Description", "product:description", "alignCenter", false, 200);
			newColumns.push(column);
			
			
			column = createMXColumn("Stroke Length", "pneumatic:strokeString");
			newColumns.push(column);
			
			column = createMXColumn("Spring Range", "pneumatic:springRangeString");
			newColumns.push(column);
			
			column = createMXColumn("Thrust @18psi", "pneumatic:maxThrust_18");
			column.labelFunction = thrust18LabelFunction;
			newColumns.push(column);
			
			column = createMXColumn("Thrust @0psi", "pneumatic:maxThrust_no", "", false, 100);
			column.labelFunction = thrust0LabelFunction;
			newColumns.push(column);
			
			column = createMXColumn("UL Listed", "pneumatic:ULCertString");
			newColumns.push(column);
			
			
			column = createMXColumn("Actuator", "pneumatic:isActuatorString");
			newColumns.push(column);
			
			column = createMXColumn("Clevis", "pneumatic:clevisString");
			newColumns.push(column);
			
			column = createMXColumn("Bracket", "pneumatic:bracketString");
			newColumns.push(column);
			
			column = createMXColumn("Ball Joint", "pneumatic:ballJointString");
			newColumns.push(column);
			
			column = createMXColumn("Pivot", "pneumatic:pivotString");
			newColumns.push(column);
			
			column = createMXColumn("Positioning", "pneumatic:posRelayString");
			newColumns.push(column);
			
			column = createMXColumn("Fwd Stops", "pneumatic:fwdTravelStopsString");
			newColumns.push(column);
			
			
			column = createMXColumn("Mounting", "pneumatic:mountingStyleString");
			newColumns.push(column);
			
			column = createMXColumn("Multiplier", "priceMultiplier", "alignCenter", true);
			column.setStyle("textAlign", "center");
			newColumns.push(column);
			
			column = createMXColumn("Price", "price", "alignCenter", false);
			column.labelFunction 		= priceLabelFunction;
			column.sortCompareFunction 	= priceSortFunction;
			column.setStyle("textAlign", "right");
			column.setStyle("fontWeight", "bold");
			newColumns.push(column);
			
			column = createMXColumn("Data Sheet", "product:dataSheetLink", "alignCenter", false, 100);
			column.itemRenderer = new ClassFactory(PDFColumnRenderer);
			column.resizable = false;
			column.sortable = false;
			newColumns.push(column);
			
			column = createMXColumn("Notes", "notes", "alignCenter");
			newColumns.push(column);
			
			return newColumns;
		}
		
		
		
		
		static private function createColumn(headerText:String, dataField:String, w:Number = -1):GridColumn {
			var column:GridColumn = new SaveableGridColumn();
			column.headerText = headerText;
			if (dataField != null && dataField != "") {
				column.dataField = dataField;
			}
			if (w > 0) {
				column.width = w;
			}
			return column;
		}
		
		
		static private function createMXColumn(headerText:String, dataField:String, headerStyle:String = "", editable:Boolean = true, w:Number = -1):AdvancedDataGridColumn {
			var column:AdvancedDataGridColumn = new SaveableAdvancedGridColumn();
			column.headerText = headerText;
			if (dataField != null && dataField != "") {
				column.dataField = dataField;
			}
			if (headerStyle != null && headerStyle != "") {
				column.setStyle("headerStyleName", headerStyle);
			}
			column.editable = editable;
			if (w > 0) {
				column.width = w;
			}
			return column;
		}
	}
}