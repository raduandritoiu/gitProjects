package petri.mediators
{
	import com.joeberkovitz.moccasin.controller.DragMediator;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.geom.Point;

	public class PetriPointDragMediator extends DragMediator implements IViewMediator
	{
		
		public static const FROM:String = "fromPos";
		public static const TO:String = "toPos";
		
		
		private var startPosition:Point;
		
		protected var view:DisplayObject;
		protected var model:Object;
		protected var propertyName:String;
		
		
		public function PetriPointDragMediator(context:ViewContext, view:DisplayObject) {
			super(context);
			this.view = view;
		}
		
		
		public function handleViewEvents():void {
			view.addEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown, false, 0, true);
		}
		
		
		public function removeViewEvents():void {
		}
		
		
		public function changeProperty(model:Object, propertyName:String):void {
			this.model = model;
			this.propertyName = propertyName;
		}
		
		
		override protected function handleDragStart(e:MouseEvent):void {
			startPosition = model[propertyName] as Point;
		}
		
		
		override protected function handleDragMove(e:MouseEvent):void {
			if (startPosition == null)
				return;
			
			var newPos:Point = startPosition.add(dragDelta);
			model[propertyName] = newPos;
		}
		
		
		override protected function handleDragEnd(e:MouseEvent):void {
			startPosition = null;
			// ar trebui sa setez si to/from
		}
		
	}
}