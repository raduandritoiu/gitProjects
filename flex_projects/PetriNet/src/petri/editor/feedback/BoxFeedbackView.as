package petri.editor.feedback
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.MoccasinComponent;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import mx.graphics.SolidColorStroke;
	
	import petri.model.PetriBaseModel;
	
	import spark.components.Group;
	import spark.primitives.Rect;

	public class BoxFeedbackView extends MoccasinComponent
	{
		protected var boundingBox:Rect;
		private var padding:Number = 3;
		
		public function BoxFeedbackView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		public function get petriModel():PetriBaseModel {
			return model.value as PetriBaseModel;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (boundingBox == null) {
				var gr:Group = new Group();
				addChild(gr);
				
				boundingBox = new Rect();
				boundingBox.x = -padding - 1;
				boundingBox.y = -padding - 1;
				boundingBox.width = petriModel.width + 2 * padding;
				boundingBox.height = petriModel.height + 2 * padding;
				boundingBox.stroke = new SolidColorStroke(0x00A8FF, 1);
				gr.addElement(boundingBox);
			}
		}
		
		
		override protected function updateView():void {
			super.updateView();
			
			move(petriModel.x , petriModel.y);
			width = petriModel.width;
			height = petriModel.height;
			
			invalidateDisplayList();
			invalidateParentSizeAndDisplayList();
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			boundingBox.width = unscaledWidth + 2 * padding;
			boundingBox.height = unscaledHeight + 2 * padding;
		}
	}
}