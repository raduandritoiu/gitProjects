package bindings.mediators.tools
{
	import bindings.mediators.drags.BindingsSlotDragMediator;
	
	import diagram.enums.DiagramWorldTools;
	import diagram.mediators.tools.DiagramWorldToolMediator;
	import diagram.mediators.tools.IToolEventHandler;
	import diagram.model.DiagramSlot;
	import diagram.view.DiagramWorldView;
	
	import flash.events.EventPhase;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import moccasin.view.ViewContext;
	
	
	public class BindingsWorldToolMediator extends DiagramWorldToolMediator
	{
		protected var startSlotDraw:Boolean = false;
		
		public function BindingsWorldToolMediator(newContext:ViewContext, newView:DiagramWorldView) {
			super(newContext, newView);
//			tools[DiagramWorldTools.LINKS_TOOL] = new BindingsDrawLinksTool(context, worldView);
		}
		
		
		
		override public function handleDragStart(e:MouseEvent):void {
			if (controller.currentSlot == null) {
				super.handleDragStart(e);
			}
			else {
				startSlotDraw = true;
				var handler:IToolEventHandler = tools[DiagramWorldTools.LINKS_TOOL] as IToolEventHandler;
				if (handler == null) return;
				if (e.eventPhase == EventPhase.CAPTURING_PHASE)
					handler.handleDragStartCapture(e);
				else
					handler.handleDragStartBubble(e);
			}
		}
		
		
		override public function handleDragMove(e:MouseEvent):void {
			if (!startSlotDraw) {
				super.handleDragMove(e);
			}
			else {
				var handler:IToolEventHandler = tools[DiagramWorldTools.LINKS_TOOL] as IToolEventHandler;
				if (handler == null)
					return;
				if (e.eventPhase == EventPhase.CAPTURING_PHASE)
					handler.handleDragMoveCapture(e);
				else
					handler.handleDragMoveBubble(e);
			}
		}
		
		
		override public function handleDragEnd(e:MouseEvent):void {
			if (!startSlotDraw) {
				super.handleDragEnd(e);
			}
			else {
				startSlotDraw = false;
				var handler:IToolEventHandler = tools[DiagramWorldTools.LINKS_TOOL] as IToolEventHandler;
				if (handler == null)
					return;
				if (e.eventPhase == EventPhase.CAPTURING_PHASE)
					handler.handleDragEndCapture(e);
				else
					handler.handleDragEndBubble(e);
			}
		}
	}
}