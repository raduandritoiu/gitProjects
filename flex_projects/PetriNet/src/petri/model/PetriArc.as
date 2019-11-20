package petri.model
{
	import flash.geom.Point;
	
	import petri.components.utils.CurvedArrow;

	public class PetriArc extends PetriBaseModel
	{
		private var _fromDelta:Point = new Point(0, 0);
		private var _toDelta:Point = new Point(0, 0);
		private var _fromPos:Point = new Point(0, 0);
		private var _toPos:Point = new Point(50, 50);
		private var _from:PetriModelWithArcs;
		private var _to:PetriModelWithArcs;
		private var _fromOrient:String = CurvedArrow.RIGHT;
		private var _toOrient:String = CurvedArrow.LEFT;
		
		
		private var fromRef:String;
		private var toRef:String;
		
		
		public function PetriArc() {
			super();
			name = "Arc " + cnt;
		}
		
		
		override public function get editorClass():String {
			return "petri.PropertiesEditors.ArcPropertiesEditor";
		}
		
		
		[Bindable]
		public function set fromPos(value:Point):void {
			_fromPos = value;
			if (from != null) {
				_fromDelta.x = _fromPos.x - from.x;
				_fromDelta.y = _fromPos.y - from.y;
			}
		}
		
		public function get fromPos():Point {
			return _fromPos;
		}
		
		
		[Bindable]
		public function set toPos(value:Point):void {
			_toPos = value;
			if (to != null) { 
				_toDelta.x = _toPos.x - to.x;
				_toDelta.y = _toPos.y - to.y;
			}
		}
		
		public function get toPos():Point {
			return _toPos;
		}
		
		
		[Bindable]
		public function set from(value:PetriModelWithArcs):void {
			_from = value;
			if (_fromPos != null && _from != null) {
				_fromDelta.x = _fromPos.x - _from.x;
				_fromDelta.y = _fromPos.y - _from.y;
			}
		}
		
		public function get from():PetriModelWithArcs {
			if (_from == null  && fromRef != null) {
				_from = world.getModelByUID(fromRef) as PetriModelWithArcs;
			}
			return _from;
		}
		
		
		[Bindable]
		public function set to(value:PetriModelWithArcs):void {
			_to = value;
			if (_toPos != null && _to != null) {
				_toDelta.x = _toPos.x - _to.x;
				_toDelta.y = _toPos.y - _to.y;
			}
		}
		
		public function get to():PetriModelWithArcs {
			if (_to == null && toRef != null) {
				_to = world.getModelByUID(toRef) as PetriModelWithArcs;
			}
			return _to;
		}
		
		
		[Bindable]
		public function set fromOrient(value:String):void {
			_fromOrient = value;
		}
		
		public function get fromOrient():String {
			return _fromOrient;
		}
		
		
		[Bindable]
		public function set toOrient(value:String):void {
			_toOrient = value;
		}
		
		public function get toOrient():String {
			return _toOrient;
		}
		

		[Bindable]
		override public function set x(value:Number):void {
			var diff:Number = _toPos.x - _fromPos.x;
			if (diff > 0) {
				fromPos.x = value;
				toPos.x = diff + value;
			}
			else {
				fromPos.x = value - diff;
				toPos.x = value;
			}
		}
		
		override public function get x():Number {
			return Math.min(_fromPos.x, _toPos.x);
		}

		
		[Bindable]
		override public function set y(value:Number):void {
			var diff:Number = _toPos.y - _fromPos.y;
			if (diff > 0) {
				fromPos.y = value;
				toPos.y = diff + value;
			}
			else {
				fromPos.y = value - diff;
				toPos.y = value;
			}
		}
		
		override public function get y():Number {
			return Math.min(_fromPos.y, _toPos.y);
		}
		
		
		[Bindable]
		override public function set width(value:Number):void {
			if (_toPos.x > _fromPos.x) {
				toPos.x = _fromPos.x + value;
			}
			else {
				fromPos.x  = _toPos.x + value;
			}
		}
		
		override public function get width():Number {
			return Math.abs(_toPos.x - _fromPos.x);
		}
		
		
		[Bindable]
		override public function set height(value:Number):void {
			if (_toPos.x > _fromPos.x) {
				toPos.x = _fromPos.x + value;
			}
			else {
				fromPos.x  = _toPos.x + value;
			}
		}
		
		override public function get height():Number {
			return Math.abs(_toPos.y - _fromPos.y);
		}
		
		
		public function updatePosition():void {
			if (_fromDelta != null && from != null)
				fromPos = new Point(from.x + _fromDelta.x, from.y + _fromDelta.y);
			if (_toDelta != null && to != null)
				toPos = new Point(to.x + _toDelta.x, to.y + _toDelta.y);
		}
		
		
		override public function fromXML(xmlModel:XML, model:PetriBaseModel):PetriBaseModel {
			var newModel:PetriArc = super.fromXML(xmlModel, model) as PetriArc;
			
			_fromOrient = xmlModel.@fromOrient;
			_toOrient 	= xmlModel.@toOrient;
			
			_fromPos = new Point();
			_fromPos.x = xmlModel.fromPos.@x[0];
			_fromPos.y = xmlModel.fromPos.@y[0];
			
			fromRef = xmlModel.from.@ref[0];
			
			_fromDelta = new Point();
			_fromDelta.x = xmlModel.fromDelta.@x[0];
			_fromDelta.y = xmlModel.fromDelta.@y[0];
			
			_toPos = new Point();
			_toPos.x = xmlModel.toPos.@x[0];
			_toPos.y = xmlModel.toPos.@y[0];
			
			toRef = xmlModel.to.@ref[0];
			
			_toDelta = new Point();
			_toDelta.x = xmlModel.toDelta.@x[0];
			_toDelta.y = xmlModel.toDelta.@y[0];
			
			return newModel;
		}
		
		
		override public function toXML():XML {
			var xmlModel:XML = super.toXML();
			
			xmlModel.@fromOrient 	= _fromOrient;
			xmlModel.@toOrient 		= _toOrient;
			
			var node:XML = new XML("<fromPos/>");
			node.@x = fromPos.x;
			node.@y = fromPos.y;
			xmlModel.appendChild(node);
				
			node = new XML("<from/>");
			node.@ref = from.uid;
			xmlModel.appendChild(node);
				
			node = new XML("<fromDelta/>");
			node.@x = _fromDelta.x;
			node.@y = _fromDelta.y;
			xmlModel.appendChild(node);
			
			node = new XML("<toPos/>");
			node.@x = toPos.x;
			node.@y = toPos.y;
			xmlModel.appendChild(node);
			
			node = new XML("<to/>");
			node.@ref = to.uid;
			xmlModel.appendChild(node);
			
			node = new XML("<toDelta/>");
			node.@x = _toDelta.x;
			node.@y = _toDelta.y;
			xmlModel.appendChild(node);

			return xmlModel;
		}
		
		
		override public function remove():void {
			if (from != null) {
				from.removeOutterArc(this);
			}
			if (to != null) {
				to.removeInnerArc(this);
			}
		}
		
		
		public function acceptsTo(model:PetriBaseModel):Boolean {
			if (model is PetriState && from is PetriTransition)
				return true;
			if (model is PetriTransition && from is PetriState)
				return true;
			return false;
		}
	}
}