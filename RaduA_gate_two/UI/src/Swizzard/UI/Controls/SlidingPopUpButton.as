package Swizzard.UI.Controls
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.core.IVisualElement;
	import mx.managers.PopUpManager;
	
	import spark.components.ToggleButton;
	
	
	/**
	 * Use this class to make a button that adds a popup
	 * on a separate layer above, below, or to the sides
	 * of the button.
	 *  
	 * <p>Usage:
	 * 
	 * <code>
	 *	 <controls:SlidingPopUpButton label="Customize Chart">
	 *		<!-- Any UIComponent can go in here. BUT ONLY ONE -->
	 *	 </controls:SlidingPopUpButton>
	 * </code>
	 * </p>
	 * 
	 * @author JD
	 * @version 0.1
	 */	
	[DefaultProperty("child")]
	public class SlidingPopUpButton extends ToggleButton
	{
		protected var _popUpPosition:String = "below";
		protected var sideChanged:Boolean;
		
		protected var _child:IVisualElement;
		protected var childrenChanged:Boolean;
		
		protected var popUp:SlidingPopUp;
		protected var resizing:Boolean;
		
		public function SlidingPopUpButton()
		{
			super();

			addEventListener(MouseEvent.CLICK, _clickHandler, false, 0, true);
			addEventListener(Event.REMOVED_FROM_STAGE, removePopUp, false, 0, true);
		}
		
		[Inspectable(category="General", enumeration="above,below,left,right", defaultValue="below")]
		public function set popUpPosition(value:String):void {
			_popUpPosition = value;
			sideChanged = true;
			invalidateProperties();
		}
		
		public function get popUpPosition():String {
			return _popUpPosition;
		}

		
		public function set child(value:IVisualElement):void {
			_child = value;
			childrenChanged = true;
			invalidateProperties();
		}
		
		public function get child():IVisualElement {
			return _child;
		}
		
		
		override protected function commitProperties():void
		{
			super.commitProperties();

			if (childrenChanged) {
				if (popUp == null) {
					popUp = new SlidingPopUp(this);
					popUp.side = popUpPosition;
				}
				childrenChanged = false;
				popUp.removeAllElements();
				popUp.addElement(child);
			}
			
			if (sideChanged) {
				sideChanged = false;
				if (popUp != null) {
					popUp.side = popUpPosition;
				}
			}
		}

		
		private function _clickHandler(e:MouseEvent):void {
			if (!popUp) 
				return;
			
			if (selected) {
				addPopUp();
			} 
			else {
				removePopUp();
			}
		}
		
		
		private function addPopUp():void {
			PopUpManager.addPopUp(popUp, this);
			stage.addEventListener(Event.RESIZE, onResize, false, 0, true);
			stage.addEventListener(MouseEvent.MOUSE_UP, removePopUp, true, 0, true);
			dispatchEvent(new Event(Event.OPEN));
		}
		
		
		private function removePopUp(e:Event=null):void {
			if (popUp && !resizing) {
				//if the click is within the popup, don't do anything
				if (e && (e is MouseEvent) && popUp.getBounds(stage).contains(MouseEvent(e).stageX, MouseEvent(e).stageY)) {
					return;
				}
				
				dispatchEvent(new Event(Event.CLOSING));
				
				stage.removeEventListener(Event.RESIZE, onResize);
				stage.removeEventListener(MouseEvent.MOUSE_UP, removePopUp, true);
				stage.removeEventListener(MouseEvent.CLICK, removePopUp);
				
				popUp.dispose();
				PopUpManager.removePopUp(popUp);
				
				if (selected) 
					selected = false;
				
				dispatchEvent(new Event(Event.CLOSE));
			}
			resizing = false;
		}
		
		
		private function onResize(e:Event=null):void {
			resizing = true;
		}
	}
}