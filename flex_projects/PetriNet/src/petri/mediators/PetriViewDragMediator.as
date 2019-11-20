package petri.mediators
{
	import com.joeberkovitz.moccasin.controller.DragMediator;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import petri.view.PetriView;
	
	public class PetriViewDragMediator extends DragMediator implements IViewMediator
	{
		protected var petriView:PetriView;
		protected var startPosition:Point;
		
		public function PetriViewDragMediator(context:ViewContext, view:PetriView)	{
			super(context);
			petriView = view;
		}
		
		
		public function handleViewEvents():void {
			petriView.addEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown, false, 0, true);
			petriView.addEventListener(MouseEvent.ROLL_OVER, handleMouseRollOver, false, 0, true);
			petriView.addEventListener(MouseEvent.ROLL_OUT, handleMouseRollOut, false, 0, true);
		}
		
		
		public function removeViewEvents():void {
		}
		
		
		protected function handleMouseRollOver(e:MouseEvent):void {
			petriView.rolledOver();
		}
		
		
		protected function handleMouseRollOut(e:MouseEvent):void {
			petriView.rolledOut();
		}
		
		
		override protected function handleDragStart(e:MouseEvent):void {
			startPosition = new Point(petriView.petriModel.x, petriView.petriModel.y);
		}
		
		
		override protected function handleDragMove(e:MouseEvent):void {
			if (startPosition == null)
				return;
			
			var newPos:Point = startPosition.add(dragDelta);
			petriView.petriModel.x = newPos.x;
			petriView.petriModel.y = newPos.y;
		}
		
		
		override protected function handleDragEnd(e:MouseEvent):void {
			startPosition = null;
		}
	}
}