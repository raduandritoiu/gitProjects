package diagram.mediators.tools
{
	import diagram.model.DiagramBaseModel;
	import diagram.utils.components.DashedLine;
	import diagram.view.DiagramBaseView;
	import diagram.view.DiagramWorldView;
	
	import flash.display.BlendMode;
	import flash.display.Shape;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.utils.Dictionary;
	
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;
	
	
	public class MultipleSelectionTool extends SimpleTool
	{
		private var selection:Shape;
		private var startPoint:Point;
		private var dshl:DashedLine;
		
		public function MultipleSelectionTool(viewContext:ViewContext, view:DiagramWorldView) {
			super(viewContext, view);
			
			selection = new Shape();
			selection.blendMode = BlendMode.DIFFERENCE;
			dshl = new DashedLine(selection, 10, 5);
			dshl.lineStyle(1, 0xFFFFFF, 1);
		}
		
		
		override public function handleDragStartBubble(e:MouseEvent):void {
			startPoint = new Point(worldView.mouseX, worldView.mouseY);
			selection.x = startPoint.x;
			selection.y = startPoint.y;
			addVisualElement(selection);
		}
		
		
		override public function handleDragMoveBubble(e:MouseEvent):void {
			if (startPoint == null)
				return;
			
			var point:Point = new Point(worldView.mouseX, worldView.mouseY).subtract(startPoint);
			dshl.clear();
			dshl.lineStyle(1, 0xFFFFFF, 1);
			dshl.moveTo(0, 0);
			dshl.lineTo(point.x, 0);
			dshl.lineTo(point.x, point.y);
			dshl.lineTo(0, point.y);
			dshl.lineTo(0, 0);
		}
		
		
		override public function handleDragEndBubble(e:MouseEvent):void {
			if (startPoint == null)
				return;
			
			var point:Point = new Point(worldView.mouseX, worldView.mouseY);
			var selectionRect:Rectangle = new Rectangle(Math.min(startPoint.x, point.x),
														Math.min(startPoint.y, point.y),
														Math.abs(startPoint.x - point.x),
														Math.abs(startPoint.y - point.y));
			
			startPoint = null;
			removeVisualElement(selection);
			
			var modelRect:Rectangle;
			var modelView:DiagramBaseView;
			var selectedShapes:Array = [];
			var selectedRefs:Dictionary = new Dictionary();
			for each (var model:DiagramBaseModel in worldView.world.models) {
				modelView = controller.getView(model.uid);
				if (modelView) {
					modelRect = modelView.getBounds(worldView);
					if (selectionRect.intersects(modelRect))
						selectedRefs[model.uid] = model;
				}
			}
			
			for each (var model:DiagramBaseModel in selectedRefs) {
				if (!selectedRefs.hasOwnProperty(model.parent.uid))
					selectedShapes.push(MoccasinModel.forValue(model));
			}
			
			controller.clearSelection();
			controller.modifyMultiSelection(selectedShapes);
		}
	}
}