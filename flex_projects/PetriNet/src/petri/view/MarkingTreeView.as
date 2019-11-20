package petri.view
{
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	
	import petri.components.utils.CurvedArrow;
	import petri.events.MarkingTreeEvent;
	import petri.model.Marking;
	import petri.model.MarkingTree;
	
	import spark.components.Group;

	
	public class MarkingTreeView extends Group
	{
		private static const GAP:int = 60;
		private static const START:int = 20;
		
		private var _model:MarkingTree;
		private var _markingViews:ArrayCollection;
		
		private var yPos:Number = START;
		private var lastView:MarkingView;
		private var viewLayer:Group;
		
		
		
		public function MarkingTreeView(model:MarkingTree) {
			_model = model;
			_model.addEventListener(MarkingTreeEvent.MARKING_ADDED, markingTreeChanged, false, 0, true);
			_model.addEventListener(MarkingTreeEvent.TRANSITION_ADDED, markingTreeChanged, false, 0, true);
			_model.addEventListener(MarkingTreeEvent.RESET_TREE, markingTreeChanged, false, 0, true);
			_markingViews = new ArrayCollection;
		}
		
		
		public function get markingTree():MarkingTree {
			return _model;
		}
		
		
		public function get markingViews():ArrayCollection {
			return _markingViews;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (viewLayer == null) {
				viewLayer = new Group();
				viewLayer.right = 0;
				viewLayer.percentWidth = 70;
				addElement(viewLayer);
			}
			
			for each (var marking:Marking in markingTree.markings) {
				createView(marking);
			}
		}
		
		
		private function markingTreeChanged(evt:MarkingTreeEvent):void {
			var newMarking:Marking = evt.newMarking;
			var oldView:MarkingView = lastView;
			var view:MarkingView;
			
			switch (evt.type) {
				case MarkingTreeEvent.MARKING_ADDED:
					view = createView(newMarking);
					break;
				
				case MarkingTreeEvent.TRANSITION_ADDED:
					view = getViewForMarking(newMarking);
					break;
				
				case MarkingTreeEvent.RESET_TREE:
					reset();
					view = createView(newMarking);
					break;
					
			}
			
			lastView = view;
			createArc(oldView, view);
		}
		
		
		private function createView(marking:Marking):MarkingView {
			var newView:MarkingView = new MarkingView(marking);
			markingViews.addItem(newView);
			viewLayer.addElement(newView);
			newView.y = yPos;
			
			yPos += GAP;
			return newView;
		}
		
		
		private function createArc(oldView:MarkingView, newView:MarkingView):void {
			if (oldView == null) 
				return;
			if (newView == null) 
				return;
			
			var arc:CurvedArrow = new CurvedArrow();
			var oldIdx:int = markingViews.getItemIndex(oldView);
			var newIdx:int = markingViews.getItemIndex(newView);
			
			// going up - use right
			if (oldIdx > newIdx) {
				arc.minCurve = 40 + oldIdx * 10;
				arc.startPoint = oldView.rightUp;
				arc.startOrient = CurvedArrow.RIGHT;
				arc.endPoint = newView.rightDown;
				arc.endOrient = CurvedArrow.RIGHT;
			}
			// going straight down - use middle 
			else if (newIdx - oldIdx == 1) {
				arc.startPoint = oldView.down;
				arc.startOrient = CurvedArrow.DOWN;
				arc.endPoint = newView.up;
				arc.endOrient = CurvedArrow.UP;
			}
			// going down - use left
			else {
				arc.minCurve = 40 + oldIdx * 10;
				arc.startPoint = oldView.leftDown;
				arc.startOrient = CurvedArrow.LEFT;
				arc.endPoint = newView.leftUp;
				arc.endOrient = CurvedArrow.LEFT;
			}
			
			viewLayer.addElement(arc);
		}
		
		
		public function getViewForMarking(marking:Marking):MarkingView {
			for each (var testView:MarkingView in markingViews) {
				if (testView.marking == marking) {
					return testView;
				}
			}
				
			return null;
		}
		
		
		public function reset():void {
			viewLayer.removeAllElements();
			markingViews.removeAll();
			yPos = START;
		}
	}
}