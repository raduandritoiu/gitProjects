package framework.events
{
	import flash.events.Event;

	public class PointEvent extends Event
	{
		public static const MOVED:String = "point_moved";
		public static const ROTATED:String = "point_rotated";
		
		private var _deltaTg:Number;
		private var _route:String;
		
		public function PointEvent(type:String, evtRoute:String, deltaTg:Number = 0, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			_route = evtRoute;
			_deltaTg = deltaTg;
		}
		
		public function get deltaTg():Number {
			return _deltaTg;
		}
		
		public function get route():String {
			return _route;
		}
	}
}