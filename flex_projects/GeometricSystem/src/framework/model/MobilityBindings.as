package framework.model
{
	public class MobilityBindings
	{
		public function MobilityBindings() {
		}
		
		
		public static function lineBinding(firstPoint:PointParticle, secondPoint:PointParticle):void {
			firstPoint.addPosPoint(secondPoint);
			secondPoint.addPosPoint(firstPoint);
		}
		
		
		public static function triangleBinding1(firstPoint:PointParticle, secondPoint:PointParticle, thirdPoint:PointParticle):void {
			lineBinding(firstPoint, secondPoint);
			lineBinding(secondPoint, thirdPoint);
			lineBinding(thirdPoint, firstPoint);
		}
		
		
		public static function triangleBinding2(firstPoint:PointParticle, secondPoint:PointParticle, thirdPoint:PointParticle):void {
			thirdPoint.addFixedJoint(secondPoint, firstPoint);
			firstPoint.addFixedJoint(secondPoint, thirdPoint);
						
			secondPoint.addPosPoint(thirdPoint);
			secondPoint.addPosPoint(firstPoint);
		}
		
		
		public static function triangleBinding(firstPoint:PointParticle, secondPoint:PointParticle, thirdPoint:PointParticle):void {
			triangleBinding1(firstPoint, secondPoint, thirdPoint);
		}

	}
}