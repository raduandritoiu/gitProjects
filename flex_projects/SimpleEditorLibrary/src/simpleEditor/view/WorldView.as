package diagram.view
{
	import diagram.model.DiagramWorld;
	
	import flash.display.DisplayObject;
	
	import moccasin.event.ModelEvent;
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;
	import diagram.utils.Grid2D;
	
	
	public class WorldView extends DiagramContainerView
	{
		private var grid:Grid2D;
		
		
		public function WorldView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
			backgroundColor = 0xDDDDDD;
		}
		
		
		public function get world():DiagramWorld {
			return model.value as DiagramWorld;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (grid == null) {
				grid = new Grid2D();
				grid.visible = world.showGrid;
				grid.width = world.width;
				grid.height = world.height;
				container.addElement(grid);
			}
		}
		
		
		override protected function createFeedbackView():DisplayObject {
			return null;
		}
		
		
		override protected function updateModelProperty(property:Object, oldValue:Object, newValue:Object):Boolean {
			var flag:Boolean = super.updateModelProperty(property, oldValue, newValue);
			if (flag) {
				return flag;
			}
			
			switch (property.toString()) {
				case "showGrid":
					grid.visible = world.showGrid;
					flag = true;
					break;
			}
			
			return flag;
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			grid.width = unscaledWidth;
			grid.height = unscaledHeight;
		}
	}
}