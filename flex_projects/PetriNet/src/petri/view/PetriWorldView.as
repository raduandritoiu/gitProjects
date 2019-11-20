package petri.view
{
	import com.joeberkovitz.moccasin.event.ModelEvent;
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import flash.display.DisplayObject;
	
	
	public class PetriWorldView extends PetriContainerView
	{
		public function PetriWorldView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
			
			_color = 0xDDDDDD;
		}
		
		
		override protected function handleModelChange(e:ModelEvent):void {
			if (e.parent != model || stage == null) {
				return;
			}
			
			switch (e.kind) {
				case ModelEvent.ADD_CHILD_MODEL:
					addChildAt(createChildView(e.child) as DisplayObject, e.index + 1);
					break;
				
				case ModelEvent.REMOVE_CHILD_MODEL:
					removeChildAt(e.index + 1);
					break;
			}
		}
		
		
		override protected function createFeedbackView():DisplayObject {
			return null;
		}
	}
}