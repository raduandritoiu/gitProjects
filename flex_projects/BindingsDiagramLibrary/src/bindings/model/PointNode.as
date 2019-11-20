package bindings.model
{
	import diagram.model.DiagramNode;
	import diagram.model.DiagramSlot;
	
	import j2inn.builder.mapping.interfaces.IVirtualPoint;
	
	
	public class PointNode extends DiagramNode
	{
		private var _point:IVirtualPoint;
		
		
		public function PointNode(value:IVirtualPoint) {
			super();
			_point = value;
			width = 120;
			height = 40
			
			// add an output slot - this should be the only one
			var slot:DiagramSlot = new DiagramSlot();
			slot.x = width - slot.width / 2;
			slot.y = (height - slot.height) / 2;
			slot.output = true;
			children.addItem(slot);
		}
		
		
		public function get point():IVirtualPoint {
			return _point;
		}
		
		
		public function get slot():DiagramSlot {
			return slots.getItemAt(0) as DiagramSlot;
		}
	}
}