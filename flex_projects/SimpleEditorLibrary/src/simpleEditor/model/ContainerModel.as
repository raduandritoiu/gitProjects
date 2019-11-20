package simpleEditor.model
{
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.events.CollectionEvent;
	import mx.events.CollectionEventKind;
	
	
	public class ContainerModel extends BaseModel
	{
		private var _children:IList;
		
		
		public function ContainerModel() {
			super();
			
			name = "Container " + cnt;
			
			_children = new ArrayCollection();
			_children.addEventListener(CollectionEvent.COLLECTION_CHANGE, childrenChangedBefore, false, 200, true);
			_children.addEventListener(CollectionEvent.COLLECTION_CHANGE, childrenChangedAfter, false, 20, true);
		}
		
		
		override public function set world(value:SimpleWorld):void {
			super.world = value;
			for each (var child:BaseModel in children) {
				child.world = value;
			}
		}
		
		
		public function get children():IList {
			return _children;
		}
		
		
		public function acceptChild(child:BaseModel):Boolean {
			return true;
		}
		
		
		public function addChild(child:BaseModel):void {
			if (!acceptChild(child))
				return;
			var idx:int = children.getItemIndex(child);
			if (idx != -1)
				return;
			children.addItem(child);
		}
		
		
		public function removeChild(child:BaseModel):void {
			var idx:int = children.getItemIndex(child);
			if (idx == -1)
				return;
			children.removeItemAt(idx);
		}
		
		
		/* This function has priority 200 and it is called before the handler that adds views,
		 * which has a riority of 100. It is mainly used when adding components. */
		protected function childrenChangedBefore(evt:CollectionEvent):void {
			if (evt.kind == CollectionEventKind.ADD) {
				for each (var child:BaseModel in evt.items) {
					childAdded(child);
				}
			}
		}
		
		
		/* This function has priority 20 and it is called after the handler that adds views,
		* which has a riority of 100. It is mainly used when removing components. */
		protected function childrenChangedAfter(evt:CollectionEvent):void {
			if (evt.kind == CollectionEventKind.REMOVE) {
				for each (var child:BaseModel in evt.items) {
					childRemoved(child);
				}
			}
		}
		
		
		protected function childAdded(child:BaseModel):void {
			child.world = world;
			child.parent = this;
			if (world != null)
				world.addToCollectors(child);
		}
		
		
		protected function childRemoved(child:BaseModel):void {
			child.world = null;
			child.parent = null;
			if (world != null)
				world.removeFromCollectors(child);
			child.removed();
		}
		
		
		override public function removed():void {
			for each (var child:BaseModel in children) {
				child.removed();
			}
			super.removed();
		}
		
		
		override public function clone():BaseModel {
			var clone:ContainerModel = super.clone() as ContainerModel;
			for each(var child:BaseModel in children) {
				clone.addChild(child.clone());
			}
			return clone;
		}
	}
}