package Swizzard.System.Search.ui
{
	import flash.events.Event;
	import flash.events.TimerEvent;
	import flash.filters.GlowFilter;
	import flash.utils.Timer;
	
	import spark.components.ToggleButton;
	import spark.primitives.supportClasses.FilledElement;
	
	
	public class ArrowToggleButton extends ToggleButton
	{
		[SkinPart(required="false")]
		public var arrow:FilledElement;
		
		private var timer:Timer;
		private var glowEffect:GlowFilter;
		private var incrementIndex:uint	= 0;
		
		
		public function ArrowToggleButton() {
			super();
			
			addEventListener(Event.ADDED_TO_STAGE, 		buttonExistenceHandler, false, 0, true);
			addEventListener(Event.REMOVED_FROM_STAGE, 	buttonExistenceHandler, false, 0, true);
			
			timer = new Timer(40);
			timer.addEventListener(TimerEvent.TIMER, glowTimerElapsed, false, 0, true);
			glowEffect	= new GlowFilter(0xffff00, 1, 6, 6, 2, 2, true);
		}
		
		
		private function buttonExistenceHandler(event:Event):void {
			switch (event.type) {
				case Event.ADDED_TO_STAGE:
					timer.start();
					break;
				
				case Event.REMOVED_FROM_STAGE:
					timer.stop();
					break;
			}
		}
		
		
		private function glowTimerElapsed(event:TimerEvent):void {
			if (selected) {
				filters = null;
				return;
			}
			incrementIndex 		+= 1;
			glowEffect.blurY	= Math.abs(Math.sin(incrementIndex / 8)) * 15;
			glowEffect.blurX	= Math.abs(Math.sin(incrementIndex / 8)) * 15;
			filters				= [glowEffect];
			invalidateDisplayList();
		}
	}
}