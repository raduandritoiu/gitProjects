package Swizzard.System.Search.ui
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.collections.IList;
	
	import spark.components.Button;
	import spark.components.supportClasses.SkinnableComponent;
	
	import Swizzard.System.Search.Events.SearchBoxEvent;
	import Swizzard.UI.Forms.TextInputWithDefault;
	
	
	[Event(name="change", type="flash.events.Event")]
	public class SearchBox extends SkinnableComponent
	{
		[SkinPart(required="true")]
		public var textField:TextInputWithDefault;
		
		[SkinPart(required="true")]
		public var searchXButton:Button;
		
		[SkinPart(required="true")]
		public var dropdown:SearchDropDown;
		
		
		private var _dataProvider:IList
		private var dataProviderChanged:Boolean;
		
		private var _crossReferenceDataProvider:IList;
		private var crossReferenceDataProviderChanged:Boolean;
		
		private var dropdownIsOnScreen:Boolean = false;
		
		
		public function SearchBox()
		{
			super();
		}
		
		
		public function set crossReferenceDataProvider(value:IList):void {
			_crossReferenceDataProvider 		= value;
			crossReferenceDataProviderChanged	= true;
			invalidateProperties();
		}
		
		public function get crossReferenceDataProvider():IList {
			return _crossReferenceDataProvider;
		}
		
		
		public function set dataProvider(value:IList):void {
			_dataProvider = value;
			dataProviderChanged	= true;
			invalidateProperties();
		}
		
		public function get dataProvider():IList {
			return _dataProvider;
		}
		
		
		public function get text():String {
			if (textField)
				return textField.text;
			return null;
		}
		
		
		override protected function commitProperties():void {
			super.commitProperties();
			
			if (dataProviderChanged) {
				dataProviderChanged	= false;
				if (dropdown) {
					dropdown.dataProvider = dataProvider;
				}
			}
			
			if (crossReferenceDataProviderChanged) {
				crossReferenceDataProviderChanged = false;
				if (dropdown) {
					dropdown.crossReferencedItems = crossReferenceDataProvider;
				}
			}
		}
		
		
		override protected function partAdded(partName:String, instance:Object):void {
			if (instance == textField) {
				textField.addEventListener(Event.CHANGE, onTextFieldChange, false, 0, true);
			}
			
			if (instance == searchXButton) {
				searchXButton.addEventListener(MouseEvent.CLICK, onSearchXButtonClick, false, 0, true);
			}
			
			// do not redispatch
			//if (instance == dropdown) {
			//	dropdown.addEventListener(SearchBoxEvent.ADD_BUTTON_CLICKED, redispatchAddEvent, false, 0, true);
			//}
		}
		
		
		private function onSearchXButtonClick(e:MouseEvent):void {
			textField.text = "";
			onTextFieldChange(null);
		}
		
		
		private function onTextFieldChange(e:Event=null):void {
			// if I have text and the dropdown is not shown
			if (textField.text && dropdownIsOnScreen == false)  {				
				dropdownIsOnScreen 			= true;
				dropdown.includeInLayout 	= dropdownIsOnScreen;
				dropdown.visible 			= dropdownIsOnScreen;
				searchXButton.visible 		= dropdownIsOnScreen;
				textField.right 			= 60;
				invalidateDisplayList();
			}
			
			// if the dropdown is shown and I do not have text
			if (dropdownIsOnScreen && textField.text == "")  {
				dropdownIsOnScreen 			= false;
				dropdown.includeInLayout 	= dropdownIsOnScreen;
				dropdown.visible 			= dropdownIsOnScreen;
				searchXButton.visible 		= dropdownIsOnScreen;
				textField.right 			= 40;
				
				// Clear Data - and resetthe data
				dataProvider				= null;
				crossReferenceDataProvider	= null;
				invalidateDisplayList();
			}
			
			// Redispatch Event
			if (e) {
				dispatchEvent(e);
			}
		}
		
		
		private function redispatchAddEvent(e:SearchBoxEvent):void {
			dispatchEvent(e);
		}
	}
}