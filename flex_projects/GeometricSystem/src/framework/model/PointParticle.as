package framework.model
{
	
	import flash.events.EventDispatcher;
	import flash.geom.Point;
	
	import framework.events.PointEvent;
	import framework.utils.CalculusUtils;
	
	import mx.collections.ArrayCollection;
	import mx.core.mx_internal;
	
	
	public class PointParticle extends EventDispatcher
	{
		private static var idCnt:int = 0;
		
		private var _uid		:String;
		private var _x			:Number;
		private var _y			:Number;
		private var _mobility	:int;
		private var _axis		:Axis;
		
//		private var _posPoint1	:PointParticle;
//		private var _l1			:Number;
//		private var _posPoint2	:PointParticle;
//		private var _l2			:Number;
		private var points		:ArrayCollection = new ArrayCollection();
		private var ls			:ArrayCollection = new ArrayCollection();
		
		
		
		private var _jointPoint		:PointParticle;
		private var _jointOffsetPoint	:PointParticle;
		private var _jointAlfa		:Number;
		private var _jointDist		:Number;
		
		private var route:String = "";
		
		
		public function PointParticle(x:Number, y:Number, mobility:int = 0, axis:Axis = null) {
			_x = x;
			_y = y;
			_mobility = mobility;
			_axis = axis;
			if (_axis != null)
				_axis.updateC(_x, _y);
			
			++idCnt;
			_uid = "Point_" + idCnt;
		}
		
		
		public function get uid():String {
			return _uid;
		}
		
		
		public function get x():Number {
			return _x;
		}
		
		public function get y():Number {
			return _y;
		}
		
		
		public function get mobility():int {
			return _mobility;
		}
		
		public function get mobile():Boolean {
			return _mobility == PointMobility.MOBILE;
		}
		
		public function get fixed():Boolean {
			return _mobility == PointMobility.FIXED;
		}
		
		public function get oxFixed():Boolean {
			return _mobility == PointMobility.OY_MOBILE;
		}
		
		public function get oyFixed():Boolean {
			return _mobility == PointMobility.OX_MOBILE;
		}
		
		
		public function move(newX:Number, newY:Number):void {
			route = "";
			
			changePos(newX, newY);
		}
		
		
		/**
		 * Add a fixed joint 
		 */
		public function addFixedJoint(jointPoint:PointParticle, offsetPoint:PointParticle):void {
			_jointPoint = jointPoint;
			_jointPoint.addEventListener(PointEvent.MOVED, jointPointMoved);
			
			_jointOffsetPoint = offsetPoint;
			_jointOffsetPoint.addEventListener(PointEvent.MOVED, jointPointMoved);
			
			_jointDist = Math.sqrt((_x - _jointPoint.x)*(_x - _jointPoint.x) + (_y - _jointPoint.y)*(_y - _jointPoint.y));
			
			var alfa:Number = CalculusUtils.arctan(_x, _y, _jointPoint.x, _jointPoint.y);
			var alfa2:Number = CalculusUtils.arctan(_jointOffsetPoint.x, _jointOffsetPoint.y, _jointPoint.x, _jointPoint.y);
			_jointAlfa = alfa - alfa2;
		}
		
		
		private function jointPointMoved(evt:PointEvent):void {
			if (evt.route.indexOf(uid) != -1) {
				return;
			}
			
			route = evt.route;
			
			var alfa2:Number = CalculusUtils.arctan(_jointOffsetPoint.x, _jointOffsetPoint.y, _jointPoint.x, _jointPoint.y);
			var alfa:Number = alfa2 + _jointAlfa;
			
			var newX:Number = _jointPoint.x + _jointDist * Math.cos(alfa);
			var newY:Number = _jointPoint.y + _jointDist * Math.sin(alfa);
			var newX_tmp:Number = _jointPoint.x - _jointDist * Math.cos(alfa);
			var newY_tmp:Number = _jointPoint.y - _jointDist * Math.sin(alfa);
			
			if (Math.abs(_x - newX) + Math.abs(_y - newY) > 
				Math.abs(_x - newX_tmp) + Math.abs(_y - newY_tmp)) {
				newX = newX_tmp;
				newY = newY_tmp;
			}
			
			changePos(newX, newY);
		}
		
		
		/**
		 * Add a position point, means a point
		 * 
		 */
		public function addPosPoint(point:PointParticle):void {
			points.addItem(point);
			var l:Number = Math.sqrt((_x - point.x) * (_x - point.x) + (_y - point.y) * (_y - point.y));
			ls.addItem(l);
			
			point.addEventListener(PointEvent.MOVED, posPointMoved);
		}

		
		private function posPointMoved(evt:PointEvent):void {
			if (evt.route.indexOf(uid) != -1) {
				return;
			}
			
			trace ("Point <" + uid + ">  moved from :  " + evt.route);
			
			route = new String(evt.route);
			
			switch (mobility) {
				case PointMobility.MOBILE:
					posPointMovedIsMobile(evt);
					break;
				
				case PointMobility.OY_MOBILE:
					posPointMovedIsOyMobile(evt);
					break;
				
				case PointMobility.OX_MOBILE:
					posPointMovedIsOxMobile(evt);
					break;
				
				case PointMobility.AXIS_MOBILE:
					posPointMovedIsAxeMobile(evt);
					break;
					
				case PointMobility.FIXED:
					posPointMovedIsFixed(evt);
					break;
			}
		}
		
		
		protected function posPointMovedIsOyMobile(evt:PointEvent):void {
			var point:PointParticle = evt.target as PointParticle;
			
//			var l:Number = (_posPoint1 == point) ? _l1 : _l2;
			var idx:int = points.getItemIndex(point);
			var l:Number = ls.getItemAt(idx) as Number;

			var dist:Number = l * l - (point.x - _x) * (point.x - _x);
			dist = Math.sqrt(dist);
			
			var newY:Number = point.y - dist;
			var newY_tmp:Number = point.y + dist;
			
			if (Math.abs(_y - newY) > Math.abs(_y - newY_tmp)) {
				newY = newY_tmp;
			}
			
			changePos(_x, newY);
		}
		
		protected function posPointMovedIsOxMobile(evt:PointEvent):void {
			var point:PointParticle = evt.target as PointParticle;			
			
			var idx:int = points.getItemIndex(point);
			var l:Number = ls.getItemAt(idx) as Number;
			
			var dist:Number = l * l - (point.y - _y) * (point.y - _y);
			dist = Math.sqrt(dist);
			
			var newX:Number = point.x - dist;
			var newX_tmp:Number = point.x + dist;
			
			if (Math.abs(_x - newX) > Math.abs(_x - newX_tmp)) {
				newX = newX_tmp;
			}
			
			changePos(newX, _y);
		}
		
		protected function posPointMovedIsAxeMobile(evt:PointEvent):void {
			var point:PointParticle = evt.target as PointParticle;			
			
			var idx:int = points.getItemIndex(point);
			var l:Number = ls.getItemAt(idx) as Number;
			
			var a:Number = 1 + _axis.m * _axis.m;
			var b:Number = 2 * (_axis.m * _axis.mC - point.x - _axis.m * point.y);
			var c:Number = point.x * point.x + point.y * point.y - l * l + _axis.mC * _axis.mC - 2 * _axis.mC * point.y;
			
			var sol:Object = CalculusUtils.grade2Ecuation(a, b, c);
			if (sol.results == 0) {
				// fuck this is not good - must THROW an ERROR
				return;
			}
			
			var newX:Number = sol.sol1;
			var newY:Number = _axis.calcY(newX);
			
			if (sol.results == 2) {
				var newX_tmp:Number = sol.sol2;
				var newY_tmp:Number = _axis.calcY(newX_tmp);
				
				if (Math.abs(_x - newX) + Math.abs(_y - newY) > 
					Math.abs(_x - newX_tmp) + Math.abs(_y - newY_tmp)) {
					newX = newX_tmp;
					newY = newY_tmp;
				}
			}
			
			changePos(newX, newY);
		}
		
		
		protected function posPointMovedIsFixed(evt:PointEvent):void {
			/* do nothing maybe flag a mistake*/
		}
		
		
		protected function posPointMovedIsMobile(evt:PointEvent):void {
			if (points.length < 2) {
				mobileOnePoint(evt);
				return;
			}
			
			var _posPoint1:PointParticle = evt.target as PointParticle;			
			var idx:int = points.getItemIndex(_posPoint1);
			var _l1:Number = ls.getItemAt(idx) as Number;
			
			var _posPoint2:PointParticle = findPoint(idx); 
			idx = points.getItemIndex(_posPoint2);
			var _l2:Number = ls.getItemAt(idx) as Number;
			
			var newX:Number;
			var newY:Number;
			
			var distX:Number = _posPoint2.x - _posPoint1.x;
			var distY:Number = _posPoint2.y - _posPoint1.y;
			var d:Number = Math.sqrt(distX * distX + distY * distY);
			
			if (d == 0) {
				newX = _posPoint1.x;
				newY = _posPoint1.y + _l1;

				var newX_tmp:Number = _posPoint1.x;
				var newY_tmp:Number = _posPoint1.y - _l1;
				if (Math.abs(_x - newX) + Math.abs(_y - newY) > 
					Math.abs(_x - newX_tmp) + Math.abs(_y - newY_tmp)) {
					newX = newX_tmp;
					newY = newY_tmp;
				}

				newX_tmp = _posPoint1.x + _l1;
				newY_tmp = _posPoint1.y;
				if (Math.abs(_x - newX) + Math.abs(_y - newY) > 
					Math.abs(_x - newX_tmp) + Math.abs(_y - newY_tmp)) {
					newX = newX_tmp;
					newY = newY_tmp;
				}
				
				newX_tmp = _posPoint1.x - _l1;
				newY_tmp = _posPoint1.y;
				if (Math.abs(_x - newX) + Math.abs(_y - newY) > 
					Math.abs(_x - newX_tmp) + Math.abs(_y - newY_tmp)) {
					newX = newX_tmp;
					newY = newY_tmp;
				}
			}
			else {
				var a:Number = (( _l1 * _l1) - ( _l2 * _l2) + d * d) / (2 * d);
				if (a > _l1) {
					// an ERROR appeard the 3 point are no longer a triangle according to the initial 2 distances to the other points
					// must throw an ERROR and stop the entire movement propagation and correct the positions of the previous propagation points

					// for now just compute this one point OK
					a = _l1;
				}
				
				var hs:Number = (_l1 * _l1) - (a * a);
				
				var h:Number = Math.sqrt((_l1 * _l1) - (a * a));
				var xCen:Number = _posPoint1.x + (_posPoint2.x - _posPoint1.x) * a / d;
				var yCen:Number = _posPoint1.y + (_posPoint2.y - _posPoint1.y) * a / d;
				
				newX_tmp = xCen + (_posPoint2.y - _posPoint1.y) * h / d;
				newY_tmp = yCen - (_posPoint2.x - _posPoint1.x) * h / d;
				newX = xCen - (_posPoint2.y - _posPoint1.y) * h / d;
				newY = yCen + (_posPoint2.x - _posPoint1.x) * h / d;
	
				if (Math.abs(_x - newX) + Math.abs(_y - newY) > 
					Math.abs(_x - newX_tmp) + Math.abs(_y - newY_tmp)) {
					newX = newX_tmp;
					newY = newY_tmp;
				}
			}			
			changePos(newX, newY);
		}
		
		
		protected function findPoint(idx:int):PointParticle {
			for (var i:int = 0; i < points.length; i++) {
				var point:PointParticle = points.getItemAt(i) as PointParticle;
				if (i != idx && point.fixed) 
					return point;
			}
			idx += 1;
			if (idx == points.length)
				idx = 0;;
			return points.getItemAt(idx) as PointParticle;
		}
		
		
		protected function mobileOnePoint(evt:PointEvent):void {
			// this function is king of fucked up because the program gets here if the point is mobile but has only one point added
			// so you can't calculate a unique position ( ... ok ok 2 unique positions -  the intersection of 2 circles)
			// there are an infinty of positions
			
			// so i will calculate the nearest one - it will be on the axe defined by this point and it's only position point added
			var point:PointParticle = points.getItemAt(0) as PointParticle;
			var l:Number = ls.getItemAt(0) as Number;
			var alfa:Number = CalculusUtils.arctan(_x, _y, point.x, point.y);
			
			var newX:Number = point.x + l * Math.cos(alfa);
			var newY:Number = point.y + l * Math.sin(alfa);
			var newX_tmp:Number = point.x - l * Math.cos(alfa);
			var newY_tmp:Number = point.y - l * Math.sin(alfa);
			
			if (Math.abs(_x - newX) + Math.abs(_y - newY) > 
				Math.abs(_x - newX_tmp) + Math.abs(_y - newY_tmp)) {
				newX = newX_tmp;
				newY = newY_tmp;
			}
			
			changePos(newX, newY);
		}
		
		
		
		private function changePos(newX:Number, newY:Number):void {
			if (isNaN(newX) || isNaN(newY)) {
				return; //need to do something more than return - maybe THROW AN ARROR
			}
			if (fixed) {
				return; //need to do something more than return - maybe THROW AN ARROR
			}
			if (oxFixed) {
				newX = _x;
			}
			if (oyFixed) {
				newY = _y;
			}
			if (_x == newX && _y == newY) {
				return;
			}
			
			_x = newX;
			_y = newY;
			
			
			trace ("\t\t\t <"+ uid +"> dispatches :    - " + route);
			route = route + "*" + uid;
			dispatchEvent(new PointEvent(PointEvent.MOVED, route));
		}
	}
}