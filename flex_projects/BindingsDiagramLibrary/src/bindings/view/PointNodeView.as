package bindings.view
{
	import bindings.model.PointNode;
	
	import diagram.view.DiagramNodeView;
	
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;
	
	import spark.components.HGroup;
	import spark.components.Label;
	import spark.layouts.VerticalAlign;
	import spark.primitives.BitmapImage;
	
	
	public class PointNodeView extends DiagramNodeView
	{
		private var viewContainer:HGroup;
		private var bmpImg:BitmapImage;
		private var pointName:Label;
		
		
		public function PointNodeView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		public function get pointNode():PointNode {
			return diagramModel as PointNode;
		}
		
			
		override protected function createChildren():void {
			super.createChildren();
			
			if (viewContainer == null) {
				viewContainer = new HGroup();
				viewContainer.verticalAlign = VerticalAlign.MIDDLE;
				viewContainer.verticalCenter = 0;
				container.addElement(viewContainer);
			}
			
			if (bmpImg == null) {
				bmpImg = new BitmapImage();
				bmpImg.height = 28;
				bmpImg.width = 28;
				bmpImg.smooth = true;
				bmpImg.source = pointNode.point.icon;
				viewContainer.addElement(bmpImg);
			}
			
			if (pointName == null) {
				pointName = new Label;
				pointName.text = pointNode.point.name;
				viewContainer.addElement(pointName);
			}
		}
	}
}