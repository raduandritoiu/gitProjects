package petri.view
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import mx.graphics.SolidColor;
	import mx.graphics.SolidColorStroke;
	
	import spark.primitives.Rect;
	
	public class PetriTransitionView extends PetriViewWithHooks
	{
		private var rectangle:Rect;
		
		
		public function PetriTransitionView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			_backGr.removeAllElements();
			
			if (rectangle == null) {
				rectangle = new Rect();
				rectangle.percentHeight = 100;
				rectangle.percentWidth = 100;
				rectangle.stroke = new SolidColorStroke(0, 3);
				rectangle.fill = new SolidColor(_color);
				
				_backGr.addElement(rectangle);
			}
		}
	}
}