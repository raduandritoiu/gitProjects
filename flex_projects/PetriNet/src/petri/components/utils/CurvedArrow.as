package petri.components.utils
{
	import flash.geom.Point;
	
	import mx.graphics.SolidColorStroke;
	
	import spark.primitives.Path;
	
	public class CurvedArrow extends Path
	{
		public static const UP:String = "Up_Orientation";
		public static const DOWN:String = "Down_Orientation";
		public static const LEFT:String = "Left_Orientation";
		public static const RIGHT:String = "Right_Orientation";
		
		private static const LAT:Number = 40;
		private static const HEAD:Number = 10;
		
		
		private var _minCurve:Number = LAT;
		private var _startPoint:Point;
		private var _endPoint:Point;
		private var _startOrient:String = RIGHT;
		private var _endOrient:String = LEFT;
		
		private var dataChanged:Boolean = false;
		
		
		public function CurvedArrow() {
			super();
			stroke = new SolidColorStroke(0, 2);
		}
		
		
		public function set startPoint(value:Point):void {
			_startPoint = value;
			dataChanged = true;
			invalidateProperties();
		}
		
		public function get startPoint():Point {
			return _startPoint;
		}
		
		
		public function set endPoint(value:Point):void {
			_endPoint = value;
			dataChanged = true;
			invalidateProperties();
		}
		
		public function get endPoint():Point {
			return _endPoint;
		}
		
		
		public function set startOrient(value:String):void {
			_startOrient = value;
			dataChanged = true;
			invalidateProperties();
		}
		
		public function get startOrient():String {
			return _startOrient;
		}
		
		
		public function set endOrient(value:String):void {
			_endOrient = value;
			dataChanged = true;
			invalidateProperties();
		}
		
		public function get endOrient():String {
			return _endOrient;
		}
		
		
		public function set minCurve(value:Number):void {
			_minCurve = value;
			dataChanged = true;
			invalidateProperties();
		}
		
		public function get minCurve():Number {
			return _minCurve;
		}
		
		
		override protected function commitProperties():void {
			super.commitProperties();
			
			if (dataChanged) {
				dataChanged = false;
				data = computePath();
			}
		}
		
		
		private function computePath():String {
			var middlePoint:Point = new Point();
			middlePoint.x = Math.abs(startPoint.x - endPoint.x) / 2;
			middlePoint.x = Math.max(middlePoint.x, minCurve);
			middlePoint.y = Math.abs(startPoint.y - endPoint.y) / 2;
			middlePoint.y = Math.max(middlePoint.y, minCurve);
			
			var ret:String = "M " + startPoint.x + " " + startPoint.y + " C";
			switch (startOrient) {
				case UP:
					ret += " " + startPoint.x + " " + (startPoint.y - middlePoint.y);
					break;
				case DOWN:
					ret += " " + startPoint.x + " " + (startPoint.y + middlePoint.y);
					break;
				case LEFT:
					ret += " " + (startPoint.x - middlePoint.x) + " " + startPoint.y;
					break;
				case RIGHT:
					ret += " " + (startPoint.x + middlePoint.x) + " " + startPoint.y;
					break;
			}
			
			switch (endOrient) {
				case UP:
					ret += " " + endPoint.x + " " + (endPoint.y - middlePoint.y);
					break;
				case DOWN:
					ret += " " + endPoint.x + " " + (endPoint.y + middlePoint.y);
					break;
				case LEFT:
					ret += " " + (endPoint.x - middlePoint.x) + " " + endPoint.y;
					break;
				case RIGHT:
					ret += " " + (endPoint.x + middlePoint.x) + " " + endPoint.y;
					break;
			}
			
			ret += " " + endPoint.x + " " + endPoint.y;
			
			switch (endOrient) {
				case UP:
					ret += " L " + (endPoint.x - HEAD) + " " + (endPoint.y - HEAD);
					ret += " M " + endPoint.x + " " + endPoint.y;
					ret += " L " + (endPoint.x + HEAD) + " " + (endPoint.y - HEAD);
					break;
				case DOWN:
					ret += " L " + (endPoint.x - HEAD) + " " + (endPoint.y + HEAD);
					ret += " M " + endPoint.x + " " + endPoint.y;
					ret += " L " + (endPoint.x + HEAD) + " " + (endPoint.y + HEAD);
					break;
				case LEFT:
					ret += " L " + (endPoint.x - HEAD) + " " + (endPoint.y - HEAD);
					ret += " M " + endPoint.x + " " + endPoint.y;
					ret += " L " + (endPoint.x - HEAD) + " " + (endPoint.y + HEAD);
					break;
				case RIGHT:
					ret += " L " + (endPoint.x + HEAD) + " " + (endPoint.y - HEAD);
					ret += " M " + endPoint.x + " " + endPoint.y;
					ret += " L " + (endPoint.x + HEAD) + " " + (endPoint.y + HEAD);
					break;
			}
			
			return ret;
		}
	}
}