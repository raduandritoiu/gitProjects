package petri.editor.feedback
{
	import com.joeberkovitz.moccasin.view.AbstractHandle;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import flash.geom.Point;

	public class PointSelectionHandle extends AbstractHandle
	{
		
		public var highlightThickness:Number = 2;
		
		private var _distort:Boolean;
		
		public function PointSelectionHandle(context:ViewContext)
		{
			super(context);
		}
		
		public function set distort(value:Boolean):void
		{
			_distort = value;
			updateGraphics();
		}
		
		public function get location():Point
		{
			return new Point(x, y);
		}
		
		override protected function updateGraphics():void
		{	
			graphics.clear();
			
			//hit area
			graphics.beginFill(0, 0)
			graphics.drawRect(-handleSize/2, -handleSize/2, handleSize, handleSize);
					
			graphics.lineStyle(1, 0x00A8FF);
			graphics.beginFill(_distort ? 0x00ff00 : 0xffffff);
			graphics.drawRect(rolled ? -handleSize/2 : -handleSize/4, rolled ? -handleSize/2 : -handleSize/4, 
									rolled ? handleSize : handleSize/2, rolled ? handleSize : handleSize/2);
			graphics.endFill();
		}
		
	}
}