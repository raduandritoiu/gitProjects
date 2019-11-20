package diagram.mediators.tools
{
	import diagram.mediators.IViewMediator;
	
	import flash.events.MouseEvent;
	
	import moccasin.controller.DragMediator;
	import moccasin.view.ViewContext;
	
	
	public class DiagramWorldDragProxy extends DragMediator implements IViewMediator
	{
		private var _mediator:DiagramWorldToolMediator;
		
		
		public function DiagramWorldDragProxy(context:ViewContext, mediator:DiagramWorldToolMediator, newUeCapture:Boolean = false) {
			super(context, newUeCapture);
			_mediator = mediator;
		}
		
		
		public function handleViewEvents():void  {
			_mediator.view.addEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown, useCapture, 0, true); 
		}
		
		
		public function removeViewEvents():void  {
			_mediator.view.removeEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown, useCapture); 
		}
		
		
		override public function handleMouseDown(e:MouseEvent):void  {
			super.handleMouseDown(e);
			_mediator.handleMouseDown(e);
		}
		
		
		override protected function handleClick(e:MouseEvent):void {
			_mediator.handleClick(e);
		}
		
		
		override protected function handleDragStart(e:MouseEvent):void  {
			_mediator.handleDragStart(e);
		}
		
		
		override protected function handleDragMove(e:MouseEvent):void {
			_mediator.handleDragMove(e);
		}
		
		
		override protected function handleDragEnd(e:MouseEvent):void {
			_mediator.handleDragEnd(e);
		}
	}
}