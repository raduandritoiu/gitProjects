package simpleEditor.model
{
	import mx.collections.ArrayCollection;
	
	
	public class SimpleWorld extends ContainerModel
	{
		private var _models:ArrayCollection = new ArrayCollection();
		private var _showGrid:Boolean;
		
		public var points:Array;
		public var metaPoints:Array;
		
		public function SimpleWorld() {
			super();
			_showGrid = true;
			
			name = "World";
			width = 800;
			height = 600;
			world = this;
		}
		
		
		public function set showGrid(value:Boolean):void {
			_showGrid = value;
		}
		
		public function get showGrid():Boolean {
			return _showGrid;
		}
		
		
		public function get models():ArrayCollection {
			return _models;
		}
		
		
		public function addToCollectors(model:BaseModel):void {
			var idx:int = models.getItemIndex(model);
			if (idx != -1)
				return;
			
			models.addItem(model);
			
			if (model is ContainerModel) {
				for each (var child:BaseModel in (model as ContainerModel).children) {
					addToCollectors(child);
				}
			}
		}
		
		
		public function removeFromCollectors(model:BaseModel):void {
			var idx:int = models.getItemIndex(model);
			if (idx == -1) 
				return;
			
			models.removeItemAt(idx);
			
			if (model is ContainerModel) {
				for each (var child:BaseModel in (model as ContainerModel).children) {
					removeFromCollectors(child);
				}
			}
		}
		
		
		public function getModelByUID(uid:String):BaseModel {
			for each (var model:BaseModel in models) {
				if (model.uid == uid) {
					return model;
				} 
			}
			return null;
		}
		
		
		override public function clone():BaseModel {
			var clone:SimpleWorld = super.clone() as SimpleWorld;
			clone.showGrid = showGrid;
			
			return clone;
		}
	}
}