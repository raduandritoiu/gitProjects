package simpleEditor.model
{
	import flash.events.EventDispatcher;
	import flash.utils.getDefinitionByName;
	import flash.utils.getQualifiedClassName;
	
	import mx.utils.UIDUtil;
	
	import simpleEditor.enums.ResizeDirection;
	
	
	public class BaseModel extends EventDispatcher
	{
		protected static var cnt:int = 0;
		
		private var _uid:String;
		private var _oridinalUid:String;
		private var _name:String;
		private var _world:SimpleWorld;
		private var _parent:ContainerModel;
		private var _resizable:int 		= ResizeDirection.RESIZE_BOTH;
		private var _width:Number		= 1;
		private var _height:Number 		= 1;
		private var _minWidth:Number 	= 1;
		private var _minHeight:Number 	= 1;
		private var _x:Number 			= 0;
		private var _y:Number 			= 0;
		
		
		public function BaseModel() {
			_uid = UIDUtil.createUID();
			_name = "Model_" + cnt;
			cnt ++;
		}
		
		public function get uid():String {
			return _uid;
		}
		
		
		public function get originalUid():String {
			return _oridinalUid;
		}
		
		
		public function set world(value:SimpleWorld):void {
			_world = value;
		}
		
		public function get world():SimpleWorld {
			return _world;
		}
		
		
		public function set parent(value:ContainerModel):void {
			_parent = value;
		}
		
		public function get parent():ContainerModel {
			return _parent;
		}
		
		
		[Bindable]
		public function set name(value:String):void {
			_name = value;
		}
		
		public function get name():String {
			return _name;
		}
		
		
		[Bindable]
		public function set resizable(value:int):void {
			_resizable = value;
		}
		
		public function get resizable():int {
			return _resizable;
		}
		
		
		[Bindable]
		public function set width(value:Number):void {
			if (value < _minWidth)
				value = _minWidth;
			_width = value;
		}
		
		public function get width():Number {
			return _width;
		}
		
		
		[Bindable]
		public function set height(value:Number):void {
			if (value < _minHeight)
				value = _minHeight;
			_height = value;
		}
		
		public function get height():Number {
			return _height;
		}
		
		
		[Bindable]
		public function set minWidth(value:Number):void {
			_minWidth = value;
			if (width < _minWidth)
				width = _minWidth;
		}
		
		public function get minWidth():Number {
			return _minWidth;
		}
		
		
		[Bindable]
		public function set minHeight(value:Number):void {
			_minHeight = value;
			if (height < _minHeight)
				height = _minHeight;
		}
		
		public function get minHeight():Number {
			return _minHeight;
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
		
		
		public function removed():void {
		}
		
		
		public function clone():BaseModel {
			var cloneClass:Class = getDefinitionByName(getQualifiedClassName(this)) as Class;
			var clone:BaseModel = new cloneClass();
			
			clone._oridinalUid 	= uid;
			clone.world 		= world;
			clone.parent 		= parent;
			clone.resizable 	= resizable;
			clone.width 		= width;
			clone.height 		= height;
			clone.x 			= x;
			clone.y 			= y;
			
			return clone;
		}
	}
}