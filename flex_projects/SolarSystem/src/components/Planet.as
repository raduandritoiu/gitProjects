package components
{
	import mx.collections.ArrayCollection;
	import mx.graphics.IFill;
	import mx.graphics.SolidColor;
	import mx.graphics.SolidColorStroke;
	
	import spark.components.Group;
	import spark.primitives.Ellipse;
	import spark.primitives.Rect;
	
	public class Planet extends Group
	{
		public static var YEAR:Number = 360;
		
		private var _color:IFill 		= new SolidColor(0, 0);
		private var _rotRadius:Number 	= 50;
		private var _revRadius:Number 	= 0;
		private var _rotSpeed:Number 	= 100;
		private var _revSpeed:Number 	= 100;
		private var _rotValue:Number 	= 0;
		private var _revValue:Number 	= 0;
		
		private var innerMark:Rect;
		private var innerPlanet:Ellipse;
		private var innerPlanetHolder:Group;
		
		private var _planets:Array;
		
		
		public function Planet() {
			super();
		}
		
		
		public function set color(value:IFill):void {
			_color = value;
		}
		
		public function get color():IFill {
			return _color;
		}
		
		
		public function set rotRadius(value:Number):void {
			_rotRadius = value;
			if (innerPlanet != null) {
				innerPlanet.width = rotRadius * 2;
				innerPlanet.height = rotRadius * 2;
				innerPlanet.x = -rotRadius;
				innerPlanet.y = -rotRadius;
				innerMark.x = rotRadius;
			}
		}
		
		public function get rotRadius():Number {
			return _rotRadius;
		}
		
		
		public function set revRadius(value:Number):void {
			_revRadius = value;
		}
		
		public function get revRadius():Number {
			return _revRadius;
		}
		
		
		public function set rotSpeed(value:Number):void {
			_rotSpeed = value;
		}
		
		public function get rotSpeed():Number {
			return _rotSpeed;
		}
		
		
		public function set revSpeed(value:Number):void {
			_revSpeed = value;
		}
		
		public function get revSpeed():Number {
			return _revSpeed;
		}
		
		
		public function set rotValue(value:Number):void {
			_rotValue = value;
			if (innerPlanetHolder != null) {
				innerPlanetHolder.rotationZ = value;
			}
		}
		
		public function get rotValue():Number {
			return _rotValue;
		}
		
		
		public function set revValue(value:Number):void {
			_revValue = value;
			x = revRadius * Math.cos(Math.PI * revValue / 180);
			y = revRadius * Math.sin(Math.PI * revValue / 180);
		}
		
		public function get revValue():Number {
			return _revValue;
		}
		
		
		public function set planets(value:Array):void {
			_planets = value;
		}
		
		public function get planets():Array {
			return _planets;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			x = revRadius * Math.cos(revValue);
			y = revRadius * Math.sin(revValue);

			if (innerPlanetHolder == null) {
				innerPlanetHolder = new Group();
				
				innerPlanetHolder.rotationZ = rotValue;
				
				addElement(innerPlanetHolder);
			}
			
			if (innerPlanet == null) {
				innerPlanet = new Ellipse();
				innerPlanet.stroke = new SolidColorStroke();
				innerPlanet.fill = color;
				
				innerPlanet.width = rotRadius * 2;
				innerPlanet.height = rotRadius * 2;
				innerPlanet.x = -rotRadius;
				innerPlanet.y = -rotRadius;
				
				innerPlanetHolder.addElement(innerPlanet);
			}
			
			if (innerMark == null) {
				innerMark = new Rect();
				innerMark.height = 4;
				innerMark.width = 10;
				innerMark.fill = new SolidColor();
				innerMark.y = -2;
				
				innerMark.x = rotRadius;
				
				innerPlanetHolder.addElement(innerMark);
			}
			
			for each (var planet:Planet in planets) {
				addElement(planet);
			}
		}
		
		
		public function moveStar(speed:Number = 1):void {
			for each (var planet:Planet in planets) {
				planet.moveStar(speed);
			}
			
			var delta:Number = speed * YEAR / rotSpeed;
			rotValue += delta;
			
			delta = speed * YEAR / revSpeed;
			revValue += delta;
		}
	}
}