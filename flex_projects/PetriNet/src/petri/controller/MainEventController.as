package petri.controller
{
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	public class MainEventController extends EventDispatcher
	{
		private static var _instance:MainEventController;
		
		
		public function MainEventController(target:IEventDispatcher=null)
		{
			super(target);
		}
		
		
		public static function get instance():MainEventController {
			if (_instance == null) {
				_instance = new MainEventController();
			}
			return _instance;
		}
		
		
		public function send(evtType:String, info:Object = null):void {
			var evt:MainEvent = new MainEvent(evtType, info);
			dispatchEvent(evt);
		}
		
	}
}