package petri.editor.feedback
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import petri.mediators.PetriPointDragMediator;
	import petri.model.PetriArc;
	
	
	public class ArcFeedbackView extends BoxFeedbackView
	{
		protected var fromPoint:PointSelectionHandle;
		protected var toPoint:PointSelectionHandle;
		protected var fromMediator:PetriPointDragMediator;
		protected var toMediator:PetriPointDragMediator;
		
		
		public function ArcFeedbackView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (fromPoint == null) {
				fromPoint = new PointSelectionHandle(context);
				addChild(fromPoint);
				
				fromMediator = new PetriPointDragMediator(context, fromPoint);
				fromMediator.changeProperty(model.value, PetriPointDragMediator.FROM);
				fromMediator.handleViewEvents();
			}
			
			if (toPoint == null) {
				toPoint = new PointSelectionHandle(context);
				addChild(toPoint);
				
				toMediator = new PetriPointDragMediator(context, toPoint);
				toMediator.changeProperty(model.value, PetriPointDragMediator.TO);
				toMediator.handleViewEvents();
			}

		}
		
		
		protected function get arc():PetriArc {
			return model.value as PetriArc;
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			fromPoint.x	= arc.fromPos.x - arc.x; 	
			fromPoint.y	= arc.fromPos.y - arc.y; 	
			
			toPoint.x	= arc.toPos.x - arc.x; 	
			toPoint.y	= arc.toPos.y - arc.y; 	
		}

	}
}