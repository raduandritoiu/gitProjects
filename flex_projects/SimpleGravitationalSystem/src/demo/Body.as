package demo
{
	
	import org.cove.ape.*;

	public class Body extends Composite {
		
		private var rgt:AbstractParticle;
		private var ctr:AbstractParticle;
		
		public function Body(dist:Number, right:AbstractParticle, height:int, lineWeight:Number = 1, lineColor:uint = 0x336699) {
			
			var cpx:Number = right.px - dist;
			var cpy:Number = right.py; 
			ctr = new CircleParticle(cpx, cpy, 1, true);	
			ctr.collidable = false;
			
			rgt = new CircleParticle(right.px, right.py, 1);
			rgt.collidable = false;
			rgt.setStyle(3, lineColor, 1, lineColor, 1);
			
			// inner constrainst
			var cr:SpringConstraint = new SpringConstraint(rgt, center, 1);
			cr.setLine(lineWeight, lineColor);
			
			addParticle(ctr);
			addParticle(rgt);
			
			addConstraint(cr);
		}
		
	
		public function get center():AbstractParticle {
			return ctr;	
		}
		
		public function get right():AbstractParticle {
			return rgt;
		}
	}
}