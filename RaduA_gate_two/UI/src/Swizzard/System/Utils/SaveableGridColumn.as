package Swizzard.System.Utils
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	
	import spark.components.gridClasses.GridColumn;
	
	
	[RemoteClass]
	public class SaveableGridColumn extends GridColumn implements IExternalizable
	{
		// these are -- not supported anymore; but used for backwards compatibility
		private var draggable:Boolean;
		private var stretchColumn:Boolean;
		private var overrides:Object;
		private var rendererClassName:String;
		
		
		public function SaveableGridColumn(columnName:String = null) {
			super(columnName);
			overrides = new Object();
			rendererClassName = "rendererClassName";
		}
		
		
		public function writeExternal(output:IDataOutput):void {
			output.writeUTF((dataField) ? dataField : "");
			output.writeUTF((headerText) ? headerText : "");
			output.writeUTF((dataTipField) ? dataTipField : "");
			output.writeDouble(width);
			output.writeBoolean(visible);
			output.writeBoolean(editable);
			output.writeBoolean(resizable);
			
			// these are -- not supported anymore; but used for backwards compatibility
			output.writeBoolean(draggable);
			output.writeBoolean(stretchColumn);
			output.writeBoolean(rendererIsEditable); // check this out
			output.writeObject(overrides);
			output.writeUTF(rendererClassName);
		}
		
		
		public function readExternal(input:IDataInput):void {
			dataField			= input.readUTF();
			headerText			= input.readUTF();
			dataTipField		= input.readUTF();
			width				= input.readDouble();
			visible				= input.readBoolean();
			editable			= input.readBoolean();
			resizable			= input.readBoolean();
			
			// these are -- not supported anymore; but used for backwards compatibility
			draggable			= input.readBoolean();
			stretchColumn		= input.readBoolean();
			rendererIsEditable	= input.readBoolean(); // check this out
			overrides			= input.readObject();
			rendererClassName 	= input.readUTF();
		}
		
		
		public function toColumn(col:GridColumn):void {
			//col.dataField			= dataField; 	// -- redundant to set property
			col.headerText			= headerText;
			col.dataTipField		= dataTipField;
			col.width				= width;
			col.visible				= visible;
			col.editable			= editable;
			col.resizable			= resizable;
			//col.draggable			= draggable; 	// -- not supported anymore
			//col.stretchColumn		= stretchColumn; // -- not supported anymore
			col.rendererIsEditable	= rendererIsEditable; // -- not supported anymore
			//col.overrides			= overrides; 		// -- not supported anymore
			//col.rendererClassName = rendererClassName; // -- not supported anymore
		}
		
		
		public function fromColumn(col:GridColumn):void {
			dataField			= col.dataField;
			headerText			= col.headerText;
			dataTipField		= col.dataTipField;
			width				= col.width;
			visible				= col.visible;
			editable			= col.editable;
			resizable			= col.resizable;
			draggable			= false; 			// col.draggable; 	// -- not supported anymore; but must set a value
			stretchColumn		= false; 			// col.stretchColumn; // -- not supported anymore; but must set a value
			rendererIsEditable	= col.rendererIsEditable; // check this out
			overrides			= new Object();				// col; 			// -- not supported anymore; but must set a value
			rendererClassName 	= "rendererClassName"; // col.itemRenderer; // -- not supported anymore; but must set a value
		}
	}
}