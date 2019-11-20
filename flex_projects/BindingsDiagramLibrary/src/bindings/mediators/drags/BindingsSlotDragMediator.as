package bindings.mediators.drags
{
	import diagram.mediators.drags.SlotViewDragMediator;
	import diagram.view.DiagramBaseView;
	
	import flash.events.MouseEvent;
	
	import moccasin.view.ViewContext;
	
	
	public class BindingsSlotDragMediator extends SlotViewDragMediator
	{
		public function BindingsSlotDragMediator(context:ViewContext, view:DiagramBaseView) {
			super(context, view);
		}
		
		
		override public function handleMouseDown(e:MouseEvent):void {
		 // prevent moving the slot by dragging	
		}
	}
}