package diagram.mediators.tools
{
	import diagram.controller.DiagramController;
	import diagram.editors.DiagramEditor;
	import diagram.view.DiagramWorldView;
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	
	import moccasin.view.ViewContext;

	public class SimpleTool implements IToolEventHandler
	{
		protected var context:ViewContext;
		protected var worldView:DiagramWorldView;
		
		
		public function SimpleTool(viewContext:ViewContext, view:DiagramWorldView) {
			context	= viewContext;
			worldView = view;
		}
		
		
		protected function get controller():DiagramController {
			return context.controller as DiagramController;
		}
		
		
		protected function get editor():DiagramEditor {
			return context.editor as DiagramEditor;
		}
		
		
		protected function addVisualElement(view:DisplayObject):void {
			context.editor.feedbackLayer.addChild(view);
		}
		
		
		protected function removeVisualElement(view:DisplayObject):void {
			context.editor.feedbackLayer.removeChild(view);
		}
		
		
		public function handleMouseDownCapture(e:MouseEvent):void
		{}
		
		
		public function handleMouseDownBubble(e:MouseEvent):void
		{}
		
		
		public function handleClickCapture(e:MouseEvent):void
		{}
		
		
		public function handleClickBubble(e:MouseEvent):void
		{}
		
		
		public function handleDragStartCapture(e:MouseEvent):void
		{}
		
		
		public function handleDragStartBubble(e:MouseEvent):void
		{}
		
		
		public function handleDragMoveCapture(e:MouseEvent):void
		{}
		
		
		public function handleDragMoveBubble(e:MouseEvent):void
		{}
		
		
		public function handleDragEndCapture(e:MouseEvent):void
		{}
		
		
		public function handleDragEndBubble(e:MouseEvent):void
		{}
		
		
		public function handleMouseMove(e:MouseEvent):void
		{}
		
		
		public function handleDoubleClick(e:MouseEvent):void
		{}
		
		
		public function handleRollOver(e:MouseEvent):void
		{}
		
		
		public function handleRollOut(e:MouseEvent):void
		{}
		
		
		public function handleMouseWheel(e:MouseEvent):void 
		{}
		
		
		public function closeTool():void
		{}
	}
}