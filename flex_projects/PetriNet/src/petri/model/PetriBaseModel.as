package petri.model
{
	import flash.events.EventDispatcher;
	import flash.utils.getQualifiedClassName;
	
	import mx.utils.UIDUtil;

	public class PetriBaseModel extends EventDispatcher
	{
		protected static var cnt:int = 0;
		
		public var world:PetriWorld;
		
		private var _name:String;
		private var _uid:String;
		private var _height:Number = 50;
		private var _width:Number = 50;
		private var _x:Number = 0;
		private var _y:Number = 0;		
		private var _tokens:int = 0;
		
		
		public function PetriBaseModel() {
			_uid = UIDUtil.createUID();
			
			cnt ++;
			_name = "Petri Model " + cnt;
		}
		
		
		public function get uid():String {
			return _uid;
		}
		
		
		public function get editorClass():String {
			return "petri.PropertiesEditors.BasePropertiesEditor";
		}
		
		
		[Bindable]
		public function set name(value:String):void {
			_name = value;
		}
		
		public function get name():String {
			return _name;
		}
		
		
		[Bindable]
		public function set tokens(value:int):void {
			_tokens = value;
		}
		
		public function get tokens():int {
			return _tokens;
		}
		
		
		[Bindable]
		public function set height(value:Number):void {
			_height = value;
		}
		
		public function get height():Number {
			return _height;
		}
		
		
		[Bindable]
		public function set width(value:Number):void {
			_width = value;
		}
		
		public function get width():Number {
			return _width;
		}
		
		
		[Bindable]
		public function set x(value:Number):void {
			_x	= value;
		}
		
		public function get x():Number {
			return _x;
		}
		
		
		[Bindable]
		public function set y(value:Number):void {
			_y	= value;
		}
		
		public function get y():Number {
			return _y;
		}
		
		
		public function fromXML(xmlModel:XML, model:PetriBaseModel):PetriBaseModel {
			_uid = xmlModel.@uid;
			_name = xmlModel.@name;
			_x = xmlModel.@x;
			_y = xmlModel.@y;
			_width = xmlModel.@width;
			_height = xmlModel.@height;
			_tokens = xmlModel.@tokens
				
			return model;
		}
		
		
		public function toXML():XML {
			var xmlModel:XML = new XML("<model/>");
			xmlModel.@type = getQualifiedClassName(this);
			xmlModel.@uid = _uid;
			xmlModel.@name = _name;
			xmlModel.@x = _x;
			xmlModel.@y = _y;
			xmlModel.@width = _width;
			xmlModel.@height = _height;
			xmlModel.@tokens = _tokens;
			
			return xmlModel;
		}
		
		
		public function remove():void {
		}
	}
}