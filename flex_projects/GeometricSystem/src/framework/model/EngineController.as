package framework.model
{
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.utils.Timer;

	public class EngineController
	{
		private var _point:PointParticle;
		private var _transformFunction:Function;
		private var _autoStart:Boolean = true;
		private var _delay:Number = 10;
		
		private var timer:Timer;
		
		
		public function EngineController(nPoint:PointParticle = null, nTransformFunction:Function = null, nAutoStart:Boolean = true, nDelay:Number = 10)
		{
			_point = nPoint;
			_transformFunction = nTransformFunction;
			_autoStart = nAutoStart;
			_delay = nDelay;
			initTimer();
		}
		
		
		public function set point(value:PointParticle):void {
			_point = value;
			initTimer();
		}
		
		public function get point():PointParticle {
			return _point;
		}
		
		
		public function set delay(value:Number):void {
			_delay = value;
			initTimer();
		}
		
		public function get delay():Number {
			return _delay;
		}
		
		
		public function set transformFunction(value:Function):void {
			_transformFunction = value;
			initTimer();
		}
		
		
		public function set autoStart(value:Boolean):void {
			_autoStart = value;
			if (value && timer != null) {
				timer.start();
			}
		}
		
		
		public function start():void {
			if (timer != null) {
				timer.start();
			}
		}
		
		
		public function stop():void {
			if (timer != null) {
				timer.stop();
			}
		}
		
		
		public function step():void {
			if (timer != null && timer.running) {
				return;
			}
			onTimerHandler(null);
		}
		
		
		
		private function initTimer():void {
			if (_transformFunction == null || _point == null) {
				return;
			}
			
			if (timer != null) {
				timer.stop();
				timer.removeEventListener(TimerEvent.TIMER, onTimerHandler);
			}
			timer = new Timer(_delay, 0);
			timer.addEventListener(TimerEvent.TIMER, onTimerHandler);
			if (_autoStart) {
				timer.start();
			}
		}
		
		private function onTimerHandler(evt:TimerEvent):void {
			var newCoord:Point = _transformFunction.call(null, new Point(_point.x, _point.y));
			_point.move(newCoord.x, newCoord.y);
		}
	}
}