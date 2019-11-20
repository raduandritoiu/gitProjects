package bindings.view
{
	import bindings.model.TranslatorNode;
	
	import diagram.view.DiagramNodeView;
	
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;
	
	import spark.components.Label;
	
	public class TranslatorNodeView extends DiagramNodeView
	{
		private var transType:Label;
		
		
		public function TranslatorNodeView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
			doubleClickEnabled = true;
		}
		
		
		public function get translatorNode():TranslatorNode {
			return diagramModel as TranslatorNode;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (transType == null) {
				transType = new Label();
				transType.text = translatorNode.translatorType;
				transType.verticalCenter = 0;
				transType.horizontalCenter = 0;
				container.addElement(transType);
			}
		}
	}
}