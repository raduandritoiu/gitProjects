package bindings.model
{
	import diagram.model.DiagramLink;
	import diagram.model.DiagramNode;
	import diagram.model.DiagramSlot;
	
	import flash.geom.Transform;
	
	import j2inn.builder.mapping.bind.BindingProperty;
	import j2inn.builder.mapping.bind.ComponentBindings;
	import j2inn.builder.mapping.interfaces.IPropertyTranslator;
	import j2inn.builder.mapping.interfaces.IVirtualPoint;
	import j2inn.builder.model.J2BaseModel;
	
	import mx.collections.ArrayCollection;
	
	
	public class ShapeNode extends DiagramNode
	{
		private var _shape:J2BaseModel;
		private var _bindedProps:ArrayCollection;
		
		
		public function ShapeNode(value:J2BaseModel) {
			super();
			
			_shape = value;
			if (_shape != null) {
				name = _shape.name;
			}
			bindedProps = new ArrayCollection();
			
			width = 180;
			height = 80;
		}
		
		
		public function get shape():J2BaseModel {
			return _shape;
		}
		
		
		public function set bindedProps(value:ArrayCollection):void {
			_bindedProps = value;
		}
		
		public function get bindedProps():ArrayCollection {
			return _bindedProps;
		}
		
		
		public function addBindedProp(prop:Object):void {
			bindedProps.addItem(prop);

			var pos:Number = 55 + 20 * bindedProps.length;
			
			var slot:DiagramSlot = new DiagramSlot();
			slot.x = - slot.width / 2;
			slot.y = pos;
			children.addItem(slot);
			
			height = pos + 25;
		}
		
		
		public function getSlotProperty(slot:DiagramSlot):Object {
			var idx:int = slots.getItemIndex(slot);
			if (idx == -1)
				return null;
			return bindedProps.getItemAt(idx);
		}
		
		
		public function getPropertySlot(property:Object):DiagramSlot {
			var idx:int = bindedProps.getItemIndex(property);
			if (idx == -1)
				return null;
			return slots.getItemAt(idx) as DiagramSlot;
		}
		
		
		public function saveBindings():void {
			for each (var property:Object in bindedProps) {
				var pointNode:PointNode;
				var translatorNode:TranslatorNode;
				
				var node:DiagramNode;
				var link:DiagramLink;
				var slot:DiagramSlot = getPropertySlot(property);
				if (slot == null)
					return;
				
				link = slot.links.getItemAt(0) as DiagramLink;
				if (slot == link.to) {
//					node = link.from.node;
				}
				if (slot == link.from) {
//					node = link.to.node;
				}
				if (node is PointNode) {
					pointNode = node as PointNode;
				}
				else if (node is TranslatorNode) {
					translatorNode = node as TranslatorNode;
					slot = (node as TranslatorNode).inputSlot;
					link = slot.links.getItemAt(0) as DiagramLink;
					if (slot == link.to) {
//						node = link.from.node;
					}
					if (slot == link.from) {
//						node = link.to.node;
					}
					if (node is PointNode) {
						pointNode = node as PointNode;
					}
				}
				
				if (pointNode == null)
					return;
				
				shape.bindingsX = x;
				shape.bindingsY = y;
				
				// add the component binding
				var binding:ComponentBindings = new ComponentBindings();
				binding.parent = shape;
				binding.bindingSource 	= pointNode.point;
				binding.bindingSourceX 	= pointNode.x;
				binding.bindingSourceY 	= pointNode.y;
				
				var bp:BindingProperty = new BindingProperty();
				bp.targetProperty = property.name;
				binding.property = bp;
				
				if (translatorNode != null) {
					bp.valueTranslator 	= translatorNode.translator;
					bp.translatorX 		= translatorNode.x;
					bp.translatorY 		= translatorNode.y;
				}	
				
				shape.addOrUpdateBinding(binding);
			}
		}
	}
}