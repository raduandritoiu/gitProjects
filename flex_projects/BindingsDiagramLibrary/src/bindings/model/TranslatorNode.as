package bindings.model
{
	import diagram.model.DiagramNode;
	import diagram.model.DiagramSlot;
	
	import j2inn.builder.mapping.interfaces.IPropertyTranslator;

	public class TranslatorNode extends DiagramNode
	{
		private var _translator:IPropertyTranslator;
		
		
		public function TranslatorNode(value:IPropertyTranslator) {
			super();
			_translator = value;
			
			width = 160;
			height = 40;
			
			// add an input and output slots - this should be the only ones
			var slot:DiagramSlot = new DiagramSlot();
			slot.x = - slot.width / 2;
			slot.y = (height - slot.height) / 2;
			children.addItem(slot);
			
			slot = new DiagramSlot();
			slot.x = width - slot.width / 2;
			slot.y = (height - slot.height) / 2;
			slot.output = true;
			children.addItem(slot);
		}
		
		
		public function get translator():IPropertyTranslator {
			return _translator;
		}
		
		
		public function get inputSlot():DiagramSlot {
			if (slots.length > 0) 
				return slots.getItemAt(0) as DiagramSlot;
			return null;
		}
		
		
		public function get outputSlot():DiagramSlot {
			if (slots.length > 1) 
				return slots.getItemAt(1) as DiagramSlot;
			return null;
		}
		
		
		public function get translatorType():String {
			var str:String = translator.editorClassName;
			var pos:int = str.lastIndexOf(".");
			str = str.substr(pos+1);
			return str;
		}
	}
}