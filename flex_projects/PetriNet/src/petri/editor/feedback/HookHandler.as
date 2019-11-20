package petri.editor.feedback
{
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import flash.geom.Point;
	
	import petri.view.PetriView;
	import petri.view.PetriViewWithHooks;
	
	
	public class HookHandler extends PointSelectionHandle
	{
		protected var _hookedView:PetriViewWithHooks
		
		public var orientation:String;
		
		public function HookHandler(context:ViewContext, view:PetriViewWithHooks) {
			super(context);
			_hookedView = view;
		}
		
		
		public function get hookedView():PetriViewWithHooks {
			return _hookedView;
		}
		
		
		public function get centerPos():Point {
			var pos:Point = new Point(_hookedView.x, _hookedView.y);
			pos.x += x;
			pos.y += y;
			return pos;
		}
	}
}