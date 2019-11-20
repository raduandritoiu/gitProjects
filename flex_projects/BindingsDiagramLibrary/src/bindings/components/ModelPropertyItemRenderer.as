package bindings.components
{
	import spark.components.Label;
	import spark.components.supportClasses.ItemRenderer;
	
	public class ModelPropertyItemRenderer extends ItemRenderer
	{
		private var nameLbl:Label;
		
		
		public function ModelPropertyItemRenderer() {
			super();
			width = 20;
		}
		
		
		override public function set data(value:Object):void {
			super.data = value;
			label = data.displayName;
			if (nameLbl != null) {
				nameLbl.text = data.displayName;
			}
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			if (nameLbl == null) {
				nameLbl = new Label();
				if (data != null) {
					nameLbl.text = data.displayName;
				}
				addElement(nameLbl);
			}
		}
	}
}