package framework.model
{
	public class Axis
	{
		private var _a:Number = 0;
		private var _b:Number = 1;
		private var _c:Number = 0;
		
		
		/* the default axes is the OX */
		
		public function Axis(a:Number = 0, b:Number = 1, c:Number = 0) {
			_a = a;
			_b = b;
			_c = c;
		}
		
		
		public function set a(value:Number):void {
			_a = value;
		}
		
		public function get a():Number {
			return _a;
		}
		
		
		public function set b(value:Number):void {
			_b = value;
		}
		
		public function get b():Number {
			return _b;
		}
		
		
		public function set c(value:Number):void {
			_c = value;
		}
		
		public function get c():Number {
			return _c;
		}
		
		
		public function get m():Number {
			return -a/b;
		}
		
		
		public function get mC():Number {
			return -c/b;
		}
		
		
		public function updateC(x:Number, y:Number):void {
			var newC:Number = - a *x - b * y;
			if (c != 0 && c != newC) {
//				return; // this is fucked up - throw an ERROR because the point is not on the axis
			}
			c = newC;
		}
		
		
		public function calcX(y:Number):Number {
			if (a != 0)
				return (-c - b * y) / a ;
			if (y != -c/b)
				return Number.MAX_VALUE; // this is fucked up - throw an error
			return Number.MAX_VALUE;
		}
		
		
		public function calcY(x:Number):Number {
			if (b != 0)
				return (-c - a * x) / b;
			if (x != -c/a)
				return Number.MAX_VALUE; // this is fucked up - throw an error
			return Number.MAX_VALUE;
		}
	}
}