package petri.model
{
	import mx.collections.ArrayCollection;

	public class PetriModelWithArcs extends PetriBaseModel
	{
		protected var _innerArcs:ArrayCollection;
		protected var _outterArcs:ArrayCollection;
		
		private var innerArcsRef:ArrayCollection;
		private var outterArcsRef:ArrayCollection;
		

		public function PetriModelWithArcs() {
			super();
		}
		
		
		public function set innerArcs(value:ArrayCollection):void {
			_innerArcs = value;
		}
		
		public function get innerArcs():ArrayCollection {
			if (_innerArcs == null && innerArcsRef != null) {
				_innerArcs = new ArrayCollection();
				for each (var uid:String in innerArcsRef) {
					var arc:PetriArc = world.getModelByUID(uid) as PetriArc;
					if (arc != null) {
						_innerArcs.addItem(arc);
					}
				}
			}
			return _innerArcs;
		}
		
		
		public function set outterArcs(value:ArrayCollection):void {
			_outterArcs = value;
		}
		
		public function get outterArcs():ArrayCollection {
			if (_outterArcs == null && outterArcsRef != null) {
				_outterArcs = new ArrayCollection();
				for each (var uid:String in outterArcsRef) {
					var arc:PetriArc = world.getModelByUID(uid) as PetriArc;
					if (arc != null) {
						_outterArcs.addItem(arc);
					}
				}
			}
			return _outterArcs;
		}
		
		
		public function addInnerArc(arc:PetriArc):void {
			if (innerArcs == null) {
				innerArcs = new ArrayCollection();
			}
			innerArcs.addItem(arc);
		}
		
		
		public function removeInnerArc(arc:PetriArc):void {
			if (innerArcs == null) {
				return;
			}
			var index:int = innerArcs.getItemIndex(arc);
			if (index < 0)
				return;
			innerArcs.removeItemAt(index);
		}
		
		
		public function addOutterArc(arc:PetriArc):void {
			if (outterArcs == null) {
				outterArcs = new ArrayCollection();
			}
			outterArcs.addItem(arc);
		}
		
									
		public function removeOutterArc(arc:PetriArc):void {
			if (outterArcs == null) {
				return;
			}
			var index:int = outterArcs.getItemIndex(arc);
			if (index < 0)
				return;
			outterArcs.removeItemAt(index);
		}
		
		
		override public function set x(value:Number):void {
			super.x = value;
			
			for each (var arc:PetriArc in innerArcs) {
				arc.updatePosition();
			}
			
			for each (arc in outterArcs) {
				arc.updatePosition();
			}
		}
		
		
		override public function set y(value:Number):void {
			super.y = value;
			
			for each (var arc:PetriArc in innerArcs) {
				arc.updatePosition();
			}
			
			for each (arc in outterArcs) {
				arc.updatePosition();
			}
		}
		
		
		override public function fromXML(xmlModel:XML, model:PetriBaseModel):PetriBaseModel {
			var newModel:PetriModelWithArcs = super.fromXML(xmlModel, model) as PetriModelWithArcs;
			
			if (xmlModel.innerArcs.children().length()) {
				innerArcsRef = new ArrayCollection();
				for each (var childXML:XML in xmlModel.innerArcs.children()) {
					innerArcsRef.addItem(childXML.@ref.toString());
				}
			}
			
			if (xmlModel.outterArcs.children().length()) {
				outterArcsRef = new ArrayCollection();
				for each (var childXML:XML in xmlModel.outterArcs.children()) {
					outterArcsRef.addItem(childXML.@ref.toString());
				}
			}
			
			return newModel;
		}
		
		
		override public function toXML():XML {
			var xmlModel:XML = super.toXML();
			
			var arcsXML:XML = new XML("<innerArcs/>");
			for each (var model:PetriBaseModel in innerArcs) {
				var node:XML = new XML ("<arc />");
				node.@ref = model.uid;
				arcsXML.appendChild(node);
			}
			xmlModel.appendChild(arcsXML);
			
			arcsXML = new XML("<outterArcs/>");
			for each (model in outterArcs) {
				node = new XML ("<arc />");
				node.@ref = model.uid;
				arcsXML.appendChild(node);
			}
			xmlModel.appendChild(arcsXML);
			
			return xmlModel;
		}
		
		
		override public function remove():void {
			for each (var arc:PetriArc in innerArcs) {
				arc.to = null;
			}
			
			for each (arc in outterArcs) {
				arc.from = null;;
			}
		}
	}
}