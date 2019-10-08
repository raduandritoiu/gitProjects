package Swizzard.System.Utils
{
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;
	
	public class MenuItem extends EventDispatcher
	{
		private var _name:String;				// Label 
		private var _children:ArrayCollection;	// Children
				
		
		public function MenuItem()
		{
			_children	= new ArrayCollection();
		}
		
		
		public function set name(value:String):void {
			_name = value;
		}

		public function get name():String {
			return _name;
		}
		
		
		[Bindable]
		public function set children(value:ArrayCollection):void {
			_children = value;
		}
		
		public function get children():ArrayCollection {
			return _children;
		}
		
		
		public function get isBranch():Boolean {
			return (children && children.length > 0);
		}
		
		
		public function get visible():Boolean {
			return (children && children.length > 0);
		}
		
		
		public function get enabled():Boolean {
			return (children && children.length > 0);
		}
	}
}