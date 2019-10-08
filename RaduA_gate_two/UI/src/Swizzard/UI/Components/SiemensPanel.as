package Swizzard.UI.Components
{
	import flash.display.DisplayObject;
	
	import mx.containers.HBox;
	import mx.containers.Panel;

	public class SiemensPanel extends Panel
	{
		private var headerBox:HBox;
		private var _headerChildren:Array;
		
		
		public function SiemensPanel()
		{
			super();
		}
		
		
		public function set headerChildren(value:Array):void
		{
			_headerChildren	= value;
		}
		
		
		public function get headerChildren():Array
		{
			return _headerChildren;
		}
		
		
		override protected function createChildren():void
		{
			super.createChildren();
			
			if (!headerBox)
			{
				headerBox = new HBox();
				headerBox.setStyle("paddingRight",		-2);
				headerBox.setStyle("horizontalAlign",	"right");
				headerBox.setStyle("verticalAlign",		"middle");
				
				for each (var child:DisplayObject in headerChildren)
				{
					headerBox.addChild(child);
				}
				
				rawChildren.addChild(headerBox); 
			}
		}
		
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);
			headerBox.setActualSize(w - titleBar.getExplicitOrMeasuredWidth() - titleBar.x, getStyle("headerHeight"));
		}
	}
}