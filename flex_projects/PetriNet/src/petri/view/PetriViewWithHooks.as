package petri.view
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import petri.components.utils.CurvedArrow;
	import petri.editor.feedback.HookHandler;
	import petri.mediators.PetriHookDragMediator;
	import petri.model.PetriModelWithArcs;

	public class PetriViewWithHooks extends PetriView
	{
		protected var leftHook:HookHandler;
		protected var rightHook:HookHandler;
		protected var topHook:HookHandler;
		protected var bottomHook:HookHandler;
		
		protected var leftHookMediator:PetriHookDragMediator;
		protected var rightHookMediator:PetriHookDragMediator;
		protected var topHookMediator:PetriHookDragMediator;
		protected var bottomHookMediator:PetriHookDragMediator;
		

		public function PetriViewWithHooks(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		public function get petriModelWithArcs():PetriModelWithArcs {
			return petriModel as PetriModelWithArcs;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (leftHook == null) {
				leftHook = new HookHandler(context, this);
				leftHook.orientation = CurvedArrow.LEFT;
				addChild(leftHook);
				
				leftHookMediator = new PetriHookDragMediator(context, leftHook);
				leftHookMediator.handleViewEvents();
			}
			
			if (rightHook == null) {
				rightHook = new HookHandler(context, this);
				rightHook.orientation = CurvedArrow.RIGHT;
				addChild(rightHook);
				
				rightHookMediator = new PetriHookDragMediator(context, rightHook);
				rightHookMediator.handleViewEvents();
			}
			
			if (topHook == null) {
				topHook = new HookHandler(context, this);
				topHook.orientation = CurvedArrow.UP;
				addChild(topHook);
				
				topHookMediator = new PetriHookDragMediator(context, topHook);
				topHookMediator.handleViewEvents();
			}
			
			if (bottomHook == null) {
				bottomHook = new HookHandler(context, this);
				bottomHook.orientation = CurvedArrow.DOWN;
				addChild(bottomHook);
				
				bottomHookMediator = new PetriHookDragMediator(context, bottomHook);
				bottomHookMediator.handleViewEvents();
			}
			
			controller.addViewHooks(this);
		}
		
		
		public function getLeftHook():HookHandler {
			return leftHook;
		}
		
		
		public function getRightHook():HookHandler {
			return rightHook;
		}
		
		
		public function getTopHook():HookHandler {
			return topHook;
		}
		
		
		public function getBottomHook():HookHandler {
			return bottomHook;
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			leftHook.x = 0;
			leftHook.y = unscaledHeight / 2;
			
			rightHook.x = unscaledWidth;
			rightHook.y = unscaledHeight / 2;
			
			topHook.x = unscaledWidth / 2;
			topHook.y = 0;
			
			bottomHook.x = unscaledWidth / 2;
			bottomHook.y = unscaledHeight;
		}
	}
}