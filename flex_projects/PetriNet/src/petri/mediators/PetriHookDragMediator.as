package petri.mediators
{
	import com.joeberkovitz.moccasin.controller.DragMediator;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.sampler.startSampling;
	
	import petri.controller.PetriController;
	import petri.editor.feedback.HookHandler;
	import petri.model.PetriArc;
	import petri.model.PetriModelWithArcs;
	
	public class PetriHookDragMediator extends DragMediator implements IViewMediator
	{
		protected var hook:HookHandler;
		protected var startPosition:Point;
		
		private var newArc:PetriArc;
		
		
		public function PetriHookDragMediator(context:ViewContext, view:HookHandler) {
			super(context);
			hook = view;
		}
		
		
		public function handleViewEvents():void {
			hook.addEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown, false, 0, true);
			hook.addEventListener(MouseEvent.ROLL_OVER, handleMouseOver, false, 10, true);
			hook.addEventListener(MouseEvent.ROLL_OUT, handleMouseOut, false, 10, true);
		}
		
		
		public function removeViewEvents():void {
		}
		
		
		protected function get controller():PetriController {
			return context.controller as PetriController;
		}
		
		
		protected function handleMouseOver(e:MouseEvent):void {
			controller.activeHook = hook;
		}
		
		
		protected function handleMouseOut(e:MouseEvent):void {
			controller.activeHook = null;
		}
		
		
		override protected function handleDragStart(e:MouseEvent):void {
			startPosition = hook.centerPos;
			
			newArc = new PetriArc();
			newArc.fromPos = startPosition;
			newArc.from = hook.hookedView.petriModelWithArcs;
			hook.hookedView.petriModelWithArcs.addOutterArc(newArc);
			newArc.toPos = startPosition;
			controller.addModelToActiveContainer(newArc);
			newArc.fromOrient = hook.orientation;
		}
		
		
		override protected function handleDragMove(e:MouseEvent):void {
			if (startPosition == null)
				return;
			
			var newPos:Point = startPosition.add(dragDelta);
			newArc.toPos = newPos;
		}
		
		
		override protected function handleDragEnd(evt:MouseEvent):void {
			startPosition = null;
			
			var posibleHook:HookHandler = controller.activeHook;
			if (posibleHook == null) {
				posibleHook = controller.getHookByPosition(evt.stageX, evt.stageY);
			}
			
			if (posibleHook == null || !newArc.acceptsTo(posibleHook.hookedView.petriModelWithArcs)) {
				newArc.remove();
				controller.removeModel(newArc);
			}
			else {
				newArc.toPos = posibleHook.centerPos;
				newArc.to = posibleHook.hookedView.petriModelWithArcs;
				newArc.toOrient = posibleHook.orientation;
				posibleHook.hookedView.petriModelWithArcs.addInnerArc(newArc);
			}
			
			newArc = null;
		}
	}
}