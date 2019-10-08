package Swizzard.UI.Components.DataGridClasses
{
	import mx.collections.IList;
	import mx.core.IVisualElement;
	
	import Swizzard.UI.Controls.SlidingPopUpButton;
	
	public class ColumnsPopUpButton extends SlidingPopUpButton
	{
		private var _columnsChild:ColumnsVisibleEditor;
		
		public function ColumnsPopUpButton() {
			super();
			_columnsChild = new ColumnsVisibleEditor();
			childrenChanged = true;
		}
		
		
		override public function set child(value:IVisualElement):void {}
		override public function get child():IVisualElement {
			return _columnsChild;
		}
		
		
		public function set columns(val:IList):void {
			_columnsChild.columns = val;
		}
		
		public function get columns():IList {
			return _columnsChild.columns;
		}
	}
}