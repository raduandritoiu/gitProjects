package diagram.mediators.tools
{
	import diagram.controller.DiagramController;
	import diagram.editors.DiagramEditor;
	import diagram.enums.DiagramWorldTools;
	import diagram.mediators.IViewMediator;
	import diagram.view.DiagramWorldView;
	
	import flash.events.EventPhase;
	import flash.events.MouseEvent;
	import flash.utils.Dictionary;
	
	import moccasin.view.ViewContext;
	
	
	public class DiagramWorldToolMediator implements IViewMediator
	{
		protected var worldView:DiagramWorldView;
		protected var context:ViewContext;
		
		protected var tools:Dictionary;
		protected var captureMediatorProxy:DiagramWorldDragProxy;		
		protected var bubbleMediatorProxy:DiagramWorldDragProxy;		
		
		
		public function DiagramWorldToolMediator(newContext:ViewContext, newView:DiagramWorldView) {
			context = newContext;
			worldView = newView;
			
			captureMediatorProxy = new DiagramWorldDragProxy(context, this, true);
			bubbleMediatorProxy = new DiagramWorldDragProxy(context, this, false);

			//initiate tools
			tools = new Dictionary(true);
			tools[DiagramWorldTools.SIMPLE_TOOL] 	= new SimpleTool(context, worldView);
			tools[DiagramWorldTools.SELECTION_TOOL] = new MultipleSelectionTool(context, worldView);
			tools[DiagramWorldTools.LINKS_TOOL] 	= new DrawLinksTool(context, worldView);
			tools[DiagramWorldTools.HYBRIDE_TOOL] 	= new HybridLinksTool(context, worldView);
		}
		
		
		public function get view():DiagramWorldView {
			return worldView;
		}
		
		
		public function get controller():DiagramController {
			return context.controller as DiagramController;
		}
		
		
		public function handleViewEvents():void {
			captureMediatorProxy.handleViewEvents();
			bubbleMediatorProxy.handleViewEvents();
			
			worldView.addEventListener(MouseEvent.DOUBLE_CLICK, handleDoubleClick, 	false, 0, true);
			worldView.addEventListener(MouseEvent.MOUSE_MOVE, handleMouseMove, 		false, 0, true);
			worldView.addEventListener(MouseEvent.ROLL_OVER, handleRollOver, 		false, 0, true);
			worldView.addEventListener(MouseEvent.ROLL_OUT, handleRollOut, 			false, 0, true);
			worldView.addEventListener(MouseEvent.MOUSE_WHEEL, handleMouseWheel, 	false, 0, true);
		}
		
		
		public function removeViewEvents():void {
			captureMediatorProxy.removeViewEvents();
			bubbleMediatorProxy.removeViewEvents();
			
			worldView.removeEventListener(MouseEvent.DOUBLE_CLICK, handleDoubleClick, false);
			worldView.removeEventListener(MouseEvent.MOUSE_MOVE, handleMouseMove, 	false);
			worldView.removeEventListener(MouseEvent.ROLL_OVER, handleRollOver, 	false);
			worldView.removeEventListener(MouseEvent.ROLL_OUT, handleRollOut, 		false);
			worldView.removeEventListener(MouseEvent.MOUSE_WHEEL, handleMouseWheel, false);
		}
		
		
		public function handleMouseDown(e:MouseEvent):void {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			if (e.eventPhase == EventPhase.CAPTURING_PHASE)
				handler.handleMouseDownCapture(e);
			else
				handler.handleMouseDownBubble(e);
		}
		
		
		public function handleClick(e:MouseEvent):void {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			if (e.eventPhase == EventPhase.CAPTURING_PHASE)
				handler.handleClickCapture(e);
			else
				handler.handleClickBubble(e);
		}
		
		
		public function handleDragStart(e:MouseEvent):void {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			if (e.eventPhase == EventPhase.CAPTURING_PHASE)
				handler.handleDragStartCapture(e);
			else
				handler.handleDragStartBubble(e);
		}
		
		
		public function handleDragMove(e:MouseEvent):void {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			if (e.eventPhase == EventPhase.CAPTURING_PHASE)
				handler.handleDragMoveCapture(e);
			else
				handler.handleDragMoveBubble(e);
		}
		
		
		public function handleDragEnd(e:MouseEvent):void {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			if (e.eventPhase == EventPhase.CAPTURING_PHASE)
				handler.handleDragEndCapture(e);
			else
				handler.handleDragEndBubble(e);
		}
		
		
		public function handleMouseMove(e:MouseEvent):void {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			handler.handleMouseMove(e);
		}
		
		
		public function handleDoubleClick(e:MouseEvent):void {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			handler.handleDoubleClick(e);
		}
		
		
		public function handleRollOver(e:MouseEvent):void {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			handler.handleRollOver(e);
		}
		
		
		public function handleRollOut(e:MouseEvent):void {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			handler.handleRollOut(e);
		}
		
		
		public function handleMouseWheel(e:MouseEvent):void  {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			handler.handleDoubleClick(e);
		}
		
		
		public function closeTool():void {
			var handler:IToolEventHandler = tools[currentTool] as IToolEventHandler;
			if (handler == null)
				return;
			handler.closeTool();
		}
		
		
		protected function get currentTool():int {
			return (context.controller as DiagramController).selectedTool;
		}
	}
}