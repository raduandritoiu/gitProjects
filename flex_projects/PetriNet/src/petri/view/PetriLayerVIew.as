package petri.view
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	public class PetriLayerVIew extends PetriContainerView
	{
		public function PetriLayerVIew(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			removeChild(_backGr);
		}
	}
}