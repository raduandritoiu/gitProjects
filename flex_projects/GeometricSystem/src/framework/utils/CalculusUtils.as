package framework.utils
{
	public class CalculusUtils
	{
		public function CalculusUtils()
		{}
		
		/* this should be in a utils class*/
		public static function arctan(x1:Number, y1:Number, x0:Number, y0:Number):Number {
			var alfa:Number;
			if (x1 == x0) {
				alfa = Math.PI/2;
				if (y1 > y0) {
					alfa = Math.PI/2;
				}
				else {
					alfa = -Math.PI/2;
				}
			}
			else {
				alfa = Math.atan((y1 - y0) / (x1 - x0));
				if (x1 < x0) {
					alfa += Math.PI
				}
			}
			return alfa;
		}
		
		
		public static function grade2Ecuation(a:Number, b:Number, c:Number):Object {
			var solution:Object = {results:0};
			if (a == 0) {
				solution.results = 1;
				solution.sol1 = -c / b;
				return solution;
			}
			
			var delta:Number = b * b - 4 * a * c;
			if (delta < 0) {
				solution.results = 0;
				solution.delta = Math.sqrt(-delta);
				return solution;
			}
			delta = Math.sqrt(delta);
			delta = delta / (2*a);
			
			solution.results = 1;
			solution.sol1 = -b/(2*a) + delta;
			
			if (delta == 0) {
				return solution;
			}
			
			solution.results = 2;
			solution.sol2 = -b/(2*a) - delta;
			return solution;
		}
	}
}