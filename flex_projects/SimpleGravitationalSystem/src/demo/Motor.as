package demo
{
	import org.cove.ape.*;
	import flash.display.Graphics;

	public class Motor extends Composite {
		
		private static var ONE_THIRD:Number = (Math.PI * 2) / 3;
		
		private var wheel:WheelParticle;
		private var radius:Number;
		private var _rimA:CircleParticle;
		private var sg:Graphics;
		private var color:uint
		
		
		public function Motor(attach:AbstractParticle, radius:Number, color:uint = 0x336699) {
			
			sg = sprite.graphics;
			
			wheel = new WheelParticle(attach.px, attach.py - .01, radius);
			wheel.setStyle(0,0xFF0000,0, 0xFF0000,0.5);
			var axle:SpringConstraint = new SpringConstraint(wheel, attach);

			_rimA = new CircleParticle(0,0,2, true);
			
			wheel.collidable = false;
			_rimA.collidable = false;
			
			addParticle(_rimA);
			addParticle(wheel);
			addConstraint(axle);
			
			this.color = color;	
			this.radius = radius;
			
			// run it once to make sure the rim particles are in the right place
			run();
		}
		
		
		public function set power(p:Number):void {
			wheel.speed = p;
		}
		
		
		public function get rimA():AbstractParticle {
			return _rimA;
		}
		
		
		public function run():void {
			// align the rim particle based on the wheel rotation
			var theta:Number = wheel.radian;
			_rimA.px = -radius * Math.sin(theta) + wheel.px;
			_rimA.py =  radius * Math.cos(theta) + wheel.py; 
		}
		
		
		// doing some custom painting here. contrast this with the custom painting of the
		// legs. in this case we draw the shape in the init method, and then just move/rotate
		// it in the paint method. one important thing here - the initial drawing happens in
		// object space (i.e., x = 0, y = 0) not world space. Another option would be to
		// just draw everything dynamically using the wheel and rim point locations.
		public override function init():void {
			
			sg.clear();
			sg.lineStyle(0, color, 1);
			sg.beginFill(color);
			sg.drawCircle(0, 0, 3);
			sg.endFill();
			
			var theta:Number = 0;
			var cx:Number = -radius * Math.sin(theta);
			var cy:Number =  radius * Math.cos(theta); 
			sg.moveTo(0,0);
			sg.lineTo(cx,cy);
			sg.drawCircle(cx, cy, 2);
		
			sg.drawCircle(cx, cy, 2);			
		}
		
		public override function paint():void {
			sprite.x = wheel.px;
			sprite.y = wheel.py;
			sprite.rotation = wheel.angle;
		}
	}
}