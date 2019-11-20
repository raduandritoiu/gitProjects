package petri.model
{
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.events.CollectionEvent;
	import mx.events.CollectionEventKind;
	
	import petri.utils.PetriModels;

	public class PetriContainerModel extends PetriBaseModel
	{
		private var _children:IList;
		
		
		public function PetriContainerModel() {
			super();
			
			name = "Container " + cnt;
			_children = new ArrayCollection();
			_children.addEventListener(CollectionEvent.COLLECTION_CHANGE, childrenChanged, false, 0, true);
		}
		
		
		[Bindable]
		public function set children(value:IList):void {
			if (_children != null) {
				_children.removeEventListener(CollectionEvent.COLLECTION_CHANGE, childrenChanged, false);
			}
			_children = value;
			if (_children != null) {
				_children.addEventListener(CollectionEvent.COLLECTION_CHANGE, childrenChanged, false, 0, true);
			}
		}
		
		public function get children():IList {
			return _children;
		}
		
		
		protected function childrenChanged(evt:CollectionEvent):void {
			switch (evt.kind) {
				case CollectionEventKind.ADD:
					world.addToCollectors(evt.items);
					break;
				
				case CollectionEventKind.REMOVE:
					world.removeFromCollectors(evt.items);
					break;
			}
		}
		
		
		override public function fromXML(xmlModel:XML, model:PetriBaseModel):PetriBaseModel {
			var newModel:PetriContainerModel = super.fromXML(xmlModel, model) as PetriContainerModel;
			
			if (xmlModel.childrenModels.length() > 0) {
				for each (var childXML:XML in xmlModel.childrenModels.children()) {
					var child:PetriBaseModel = PetriModels.createModelInstance(childXML);
					child.world = world;
					child.fromXML(childXML, child);
					children.addItem(child);
				}
			}
			
			return newModel;
		}
		
		
		override public function toXML():XML {
			var xmlModel:XML = super.toXML();
			
			var childrenXML:XML = new XML("<childrenModels/>");
			for each (var model:PetriBaseModel in children) {
				var node:XML = model.toXML();
				childrenXML.appendChild(node);
			}
			xmlModel.appendChild(childrenXML);
			
			return xmlModel;
		}
	}
}