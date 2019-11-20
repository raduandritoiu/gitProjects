package framework.view
{
	import framework.model.MobilityBindings;
	import framework.model.PointParticle;
	
	import mx.collections.ArrayCollection;
	
	
	public class PolygonComp extends TriangleComp
	{
		private var _points:ArrayCollection = new ArrayCollection();
		private var lastLength:int = 0;
		private var pointArrInit:Boolean = false;
		
		
		public function PolygonComp() {
			super();
		}
		
		
		public function addPoint(point:PointParticle):void {
			_points.addItem(point);
			addPointParticle(point);
		}
		
		
		public function getPointAt(idx:int):PointParticle {
			if (idx < 0 || idx >= _points.length)
				return null;
			return _points.getItemAt(idx) as PointParticle;
		}
		
		
		public function get length():int {
			return _points.length;
		}
		
		
		public function set points(pointsArray:ArrayCollection):void {
			if (pointArrInit)
				return;
			pointArrInit = true;
			for (var i:int = 0; i < pointsArray.length; i++) {
				var point:PointParticle = pointsArray.getItemAt(i) as PointParticle;
				addPoint(point);
			}
		}
		
		
		override public function set firstPoint(value:PointParticle):void {
			if (length == 0)
				addPoint(value);
		}
		
		override public function get firstPoint():PointParticle {
			return getPointAt(0);
		}
		
		
		override public function set secondPoint(value:PointParticle):void {
			if (length == 1)
				addPoint(value);
		}
		
		override public function get secondPoint():PointParticle {
			return getPointAt(1);
		}
		
		
		override public function set thirdPoint(value:PointParticle):void {
			if (length == 2)
				addPoint(value);
		}
		
		override public function get thirdPoint():PointParticle {
			return getPointAt(2);
		}
		
		
		override protected function setAutoBindings():void {
			if (length < 3)
				return;
			
			if (lastLength >= length)
				return;
			
			lastLength = length;
			
			/* to set autobinding */
			var firstP:PointParticle = getPointAt(length - 3);
			var secondP:PointParticle = getPointAt(length - 2);
			var thirdP:PointParticle = getPointAt(length - 1);
			
			MobilityBindings.triangleBinding(firstP, secondP, thirdP);
		}
		
		
		override protected function drawShape():void {
			if (length < 3)
				return;
			
			x = firstPoint.x;
			y = firstPoint.y;
			
			var dataStr:String = "";
			for (var i:int = 1; i < length; i++) {
				var point:PointParticle = getPointAt(i);
				dataStr = dataStr + "L " + (point.x - firstPoint.x) + " " + (point.y - firstPoint.y) + " ";
			}
			dataStr = dataStr + "Z";
			line.data = dataStr;
		}
	}
}