package diagram.mediators.tools
{
	import diagram.model.DiagramContainerModel;
	import diagram.model.DiagramLink;
	import diagram.model.DiagramSlot;
	import diagram.view.DiagramLinkView;
	import diagram.view.DiagramWorldView;
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;
	
	
	public class DrawLinksTool extends SimpleTool
	{
		protected var fromSlot:DiagramSlot;
		protected var toSlot:DiagramSlot;
		protected var newLink:DiagramLink;
		protected var newLinkView:DiagramLinkView;
		
		
		public function DrawLinksTool(context:ViewContext, view:DiagramWorldView) {
			super(context, view);
		}
		
		
		override public function handleMouseDownCapture(e:MouseEvent):void {
			e.stopImmediatePropagation();
			e.stopPropagation();
		}
		
		
		override public function handleDragStartCapture(e:MouseEvent):void {
			var point:Point = new Point(worldView.mouseX, worldView.mouseY);
			fromSlot = controller.currentSlot;
			
			if (fromSlot != null && fromSlot.acceptLink()) {
				newLink = new DiagramLink();
				
				newLink.fromPoint = point;
//				point.x++;
//				point.y++;
				newLink.toPoint = point;
				newLink.from = fromSlot;
				
				newLinkView = new DiagramLinkView(context, MoccasinModel.forValue(newLink));
				newLinkView.mouseEnabled = false;
				newLinkView.mouseChildren = false;
				
//				addVisualElement(newLinkView);
				worldView.addChild(newLinkView);
			}
			
			e.stopImmediatePropagation();
			e.stopPropagation();
		}
		
		
		override public function handleDragMoveCapture(e:MouseEvent):void {
			if (newLink != null) {
				var point:Point = new Point(worldView.mouseX, worldView.mouseY);
				newLink.toPoint = point;
				newLink.redraw = !newLink.redraw;
			}
			
			e.stopImmediatePropagation();
			e.stopPropagation();
		}
		
		
		override public function handleDragEndCapture(e:MouseEvent):void {
			if (newLink != null) {
//				removeVisualElement(newLinkView);
				worldView.removeChild(newLinkView);
				
				var proposedParent:DiagramContainerModel;
				toSlot = controller.currentSlot;
				if (toSlot != null && toSlot.acceptLink()) {
					if (fromSlot.parent.parent == toSlot.parent.parent) {
						proposedParent = fromSlot.parent.parent;
					}
					if (fromSlot.parent == toSlot.parent.parent) {
						proposedParent = fromSlot.parent;
					}
					if (fromSlot.parent.parent == toSlot.parent) {
						proposedParent = toSlot.parent;
					}
				}
				
				if (proposedParent != null && proposedParent.acceptChild(newLink)) {
					newLink.from = fromSlot
					newLink.to = toSlot;
					proposedParent.addChild(newLink);
				}
			}
			
			newLink = null;
			fromSlot = null;
			toSlot = null;
			
			e.stopImmediatePropagation();
			e.stopPropagation();
		}
	}
}