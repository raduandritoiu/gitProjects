package Swizzard.System.Utils
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.utils.getDefinitionByName;
	import flash.utils.getQualifiedClassName;
	
	import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
	import mx.core.ClassFactory;
	import mx.core.IFactory;
	import mx.core.mx_internal;
	
	import utils.LogUtils;

	[RemoteClass]
	public class SaveableAdvancedGridColumn extends AdvancedDataGridColumn implements IExternalizable
	{
		private var _stretchColumn:Boolean;
		
		
		public function SaveableAdvancedGridColumn(columnName:String=null) {
			super(columnName);
		}
		
		
		public function set stretchColumn(value:Boolean):void {
			_stretchColumn	= value;
		}
		
		public function get stretchColumn():Boolean {
			return _stretchColumn;
		}
		
		
		override public function set itemRenderer(value:IFactory):void {
			if ((value is ClassFactory) && (!ClassFactory(value).generator))
				return;
			super.itemRenderer	= value;					
		}
		
		
		public function writeExternal(output:IDataOutput):void {
			output.writeUTF((dataField) ? dataField : "");
			output.writeUTF((headerText) ? headerText : "");
			output.writeUTF((dataTipField) ? dataTipField : "");
			output.writeDouble(width);
			output.writeDouble(this.mx_internal::explicitWidth); // Fixes: Issue #1800
			output.writeDouble(this.mx_internal::preferredWidth); // Fixes: Issue #1800
			output.writeBoolean(visible);
			output.writeBoolean(editable);
			output.writeBoolean(resizable);
			output.writeBoolean(this._stretchColumn);
			output.writeBoolean(rendererIsEditor);
			output.writeObject(overrides);
						
			if (itemRenderer && (itemRenderer is ClassFactory)) {
				var classPath:String = getQualifiedClassName(ClassFactory(itemRenderer).generator);
				output.writeUTF(classPath);
			}
			else {
				output.writeUTF("");
			}
			
			// Label Function
			// Sort Compare Function
			
			/* output.writeUTF((dataField) ? dataField : "");
			output.writeUTF((dataField) ? dataField : ""); */
		}
		
		
		public function readExternal(input:IDataInput):void
		{
			this.dataField			= input.readUTF();
			this.headerText			= input.readUTF();
			this.dataTipField		= input.readUTF();
			//this.width				= input.readDouble();
			mx_internal::setWidth(input.readDouble()); // Fixes: Issue #1800
			mx_internal::explicitWidth	= input.readDouble(); // Fixes: Issue #1800
			mx_internal::preferredWidth	= input.readDouble(); // Fixes: Issue #1800
			this.visible			= input.readBoolean();
			this.editable			= input.readBoolean();
			this.resizable			= input.readBoolean();
			this._stretchColumn		= input.readBoolean();
			this.rendererIsEditor	= input.readBoolean();
			this.overrides			= input.readObject();
			
			var rendererClassName:String	= input.readUTF();
			if (rendererClassName) {
				try {
					var rendererClass:Class				= getDefinitionByName(rendererClassName) as Class;
					var rendererFactory:ClassFactory	= new ClassFactory(rendererClass);
					this.itemRenderer	= rendererFactory;	
				}
				catch (err:Error) {
					LogUtils.Debug("Error Instantiating Renderer");
				}
			}
		}
		
		
		override public function clone():AdvancedDataGridColumn {
			var c:SaveableAdvancedGridColumn	= new SaveableAdvancedGridColumn();
			c.headerText	= this.headerText;
			c.editable		= this.editable;
			c.dataField		= this.dataField;
			c.width			= this.width;
			c.sortable		= this.sortable;
			c.itemRenderer	= this.itemRenderer;
			c.itemEditor	= this.itemEditor;
			c.labelFunction	= this.labelFunction;
			
			c.sortCompareFunction	= this.sortCompareFunction;
			
			c.setStyle("textAlign",			this.getStyle("textAlign"));
			c.setStyle("fontWeight",		this.getStyle("fontWeight"));
			c.setStyle("headerStyleName",	this.getStyle("headerStyleName"));
			
			return c;
		}
		
		
		public function toColumn(col:AdvancedDataGridColumn):void {
			// just copied the label, sort and dataTip functions (this is all I need for now)
			col.labelFunction 		= labelFunction;
			col.sortCompareFunction = sortCompareFunction;
			col.dataTipFunction 	= dataTipFunction;
		}
	}
}