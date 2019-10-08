package Swizzard.UI.Components.DataGridClasses
{
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.ClassFactory;
	
	import Swizzard.Data.UI.Renderers.PDFColumnRenderer;
	

	public class FavoritesColumnsUtil
	{
		public function FavoritesColumnsUtil() {}
		
		
		private static var valveCols:Array;
		private static var damperCols:Array;
		private static var pneumaticCols:Array;

		
		public static function getValveCols():Array {
			if (valveCols == null) {
				generateValveCols();
			}
			return valveCols;
		}
		
		public static function getDamperCols():Array {
			if (damperCols == null) {
				generateDamperCols();
			}
			return damperCols;
		}
		
		public static function getPneumaticCols():Array {
			if (pneumaticCols == null) {
				generatePneumaticCols();
			}
			return pneumaticCols;
		}
		
		
		private static function generateValveCols():void {
			valveCols = new Array();
			var col:DataGridColumn;
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Part Number";
			col.dataField = "partNumber";
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Valve Size";
			col.dataField = "valve:size";
			col.width = 100;
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Cv";
			col.dataField = "valve:cv";
			col.width = 50;
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Port Config";
			col.dataField = "valve:portConfigurationString";
			col.width = 120;
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Trim";
			col.dataField = "valve:plugMaterialString";
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Multiplier";
			col.dataField = "priceMultiplier";
			col.setStyle("textAlign", "center");
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Price";
			col.dataField = "price";
			col.setStyle("textAlign", "right");
			col.setStyle("fontWeight", "bold");
			col.labelFunction = GridColumnsUtil.priceLabelFunction;
			col.sortCompareFunction = GridColumnsUtil.priceSortFunction;
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Description";
			col.dataField = "description";
			col.width = 160;
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Assembly Data Sheet";
			col.dataField = "dataSheetLink";
			col.width = 160;
			col.sortable = false;
			col.resizable = false;
			col.itemRenderer = new ClassFactory(Swizzard.Data.UI.Renderers.PDFColumnRenderer);
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Pressure Class";
			col.dataField = "valve:maxPress";
			col.width = 120;
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Medium";
			col.dataField = "valve:mediumString";
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Flow Char";
			col.dataField = "valve:flowCharString";
			col.width = 100;
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Actuator Part#";
			col.dataField = "actuator:actualPartNumber";
			col.width = 120;
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Actuator Data Sheet";
			col.dataField = "actuator:dataSheetLink";
			col.width = 140;
			col.sortable = false;
			col.resizable = false;
			col.itemRenderer = new ClassFactory(Swizzard.Data.UI.Renderers.PDFColumnRenderer);
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Fail Position";
			col.dataField = "actuator:safetyFunctionString";
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Close Off Pressure";
			col.dataField = "actuator:closeOff";
			col.width = 150;
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Valve Part#";
			col.dataField = "valve:partNumber";
			
			col = new DataGridColumn("");
			valveCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Valve Data Sheet";
			col.dataField = "valve:dataSheetLink";
			col.width = 150;
			col.sortable = false;
			col.resizable = false;
			col.itemRenderer = new ClassFactory(Swizzard.Data.UI.Renderers.PDFColumnRenderer);
		}
		
		
		private static function generateDamperCols():void {
			damperCols = new Array();
			var col:DataGridColumn;
			
			col = new DataGridColumn("");
			damperCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Part Number";
			col.dataField = "product:partNumber";
			
			col = new DataGridColumn("");
			damperCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Type";
			col.dataField = "damper:typeString";
			col.width = 100;
			
			col = new DataGridColumn("");
			damperCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Torque";
			col.dataField = "damper:torque";
			col.width = 80;
			col.labelFunction = GridColumnsUtil.torqueLabelFunction;
			
			col = new DataGridColumn("");
			damperCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Control Signal";
			col.dataField = "damper:controlSignalString";
			col.width = 120; 
			
			col = new DataGridColumn("");
			damperCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Multiplier";
			col.dataField = "priceMultiplier";
			col.setStyle("textAlign", "center");
			
			col = new DataGridColumn("");
			damperCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Price";
			col.dataField = "price";
			col.setStyle("textAlign", "right");
			col.setStyle("fontWeight", "bold");
			col.labelFunction = GridColumnsUtil.priceLabelFunction;
			col.sortCompareFunction = GridColumnsUtil.priceSortFunction;
			
			col = new DataGridColumn("");
			damperCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Description";
			col.dataField = "product:description";
			col.width = 160;
			
			col = new DataGridColumn("");
			damperCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Tehnical Documents";
			col.dataField = "product:dataSheetLink";
			col.width = 110;
			col.sortable = false;
			col.resizable = false;
			col.itemRenderer = new ClassFactory(Swizzard.Data.UI.Renderers.PDFColumnRenderer);
		}
		
		
		private static function generatePneumaticCols():void {
			pneumaticCols = new Array();
			var col:DataGridColumn;
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Part Number";
			col.dataField = "product:partNumber";
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Type";
			col.dataField = "pneumatic:typeString";
			col.width = 80;
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Spring Range";
			col.dataField = "pneumatic:springRangeString";
			col.width = 100;
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Stroke Length";
			col.dataField = "pneumatic:strokeString";
			col.width = 100;
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Thrust @18psi";
			col.dataField = "pneumatic:maxThrust_18";
			col.labelFunction = GridColumnsUtil.thrust18LabelFunction;
			col.width = 100;
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Thrust @0psi";
			col.dataField = "pneumatic:maxThrust_no";
			col.labelFunction = GridColumnsUtil.thrust0LabelFunction;
			col.width = 100;
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "UL Listed";
			col.dataField = "pneumatic:ULCertString";
			col.width = 100;
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Multiplier";
			col.dataField = "priceMultiplier";
			col.setStyle("textAlign", "center");
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Price";
			col.dataField = "price";
			col.setStyle("textAlign", "right");
			col.setStyle("fontWeight", "bold");
			col.labelFunction = GridColumnsUtil.priceLabelFunction;
			col.sortCompareFunction = GridColumnsUtil.priceSortFunction;
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Description";
			col.dataField = "product:description";
			col.width = 160;
			
			col = new DataGridColumn("");
			pneumaticCols.push(col);
			col.setStyle("headerStyleName", "alignCenter");
			col.headerText = "Tehnical Documents";
			col.dataField = "product:dataSheetLink";
			col.width = 110;
			col.sortable = false;
			col.resizable = false;
			col.itemRenderer = new ClassFactory(Swizzard.Data.UI.Renderers.PDFColumnRenderer);
		}
		
		
		private static function generateCol():DataGridColumn {
			var col:DataGridColumn = new DataGridColumn();
			return col;
		}
	}
}