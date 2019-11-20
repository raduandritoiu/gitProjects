package demo
{
	import org.cove.ape.*;
	
	public class Robot extends Group {
		
		private var body:Body;
		private var motor:Motor;
		private var legRA:Leg;
		
		private var direction:int;
		private var powerLevel:Number;
		private var powered:Boolean;
		
		
		public function Robot(px:Number, py:Number, scale:Number, power:Number) {
			
			// legs
			legRA = new Leg(px, py,  1, scale);
			
			// body
			body = new Body(31 * scale, legRA.fix, 30 * scale);
			
			// motor
			motor = new Motor(body.center, 8 * scale);
			
			// connect the body to the legs
			var connRA:SpringConstraint = new SpringConstraint(body.right, legRA.fix, 1);

			// connect the legs to the motor
			legRA.cam.position = motor.rimA.position;
			var connRAA:SpringConstraint = new SpringConstraint(legRA.cam, motor.rimA, 1);
			
			
			// add to the engine
			addComposite(legRA);
			addComposite(body); 
			addComposite(motor);
			
			addConstraint(connRA);
			addConstraint(connRAA);
			
			direction = -1
			powerLevel = power; 
			powered = false;
		}
		
		
		public function run():void {
			motor.run();
		}
		
		
		public function togglePower():void {
			
			powered = !powered
			
			if (powered) {
				motor.power = powerLevel * direction;
				stiffness = 1;
				APEngine.damping = 0.99;
			} else {
				motor.power = 0;
				stiffness = 0.2;				
				APEngine.damping = 0.35;
			}
		}
		
		
		public function toggleDirection():void {
			direction *= -1;
			motor.power = powerLevel * direction;
		}
		
		
		private function set stiffness(s:Number):void {
			
			// top level constraints in the group
			for (var i:int = 0; i < constraints.length; i++) {
				var sp:SpringConstraint = constraints[i]; 
				sp.stiffness = s;
			}
			
			// constraints within this groups composites
			for (var j:int = 0; j < composites.length; j++) {
				for (i = 0; i < composites[j].constraints.length; i++) {
					sp = composites[j].constraints[i]; 
					sp.stiffness = s;
				}
			}
		}
	}
}