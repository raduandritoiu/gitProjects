package Swizzard.UI.Controls
{
	import spark.components.FormItem;
	import spark.layouts.HorizontalLayout;
	import spark.layouts.VerticalLayout;
	
	import org.osmf.layout.LayoutMode;
	
	
	public class CustomFormItem extends FormItem
	{
		private var _labelGap:Number = 5;
		
		private var _contentLayout:String = LayoutMode.HORIZONTAL;
		private var layoutChanged:Boolean;
		
		private var _contentGap:Number = 4;
		private var gapChanged:Boolean;
		
		
		public function CustomFormItem() {
			super();
		}
		
		
		public function set labelGap(val:Number):void {
			_labelGap = val;
		}
		
		public function get labelGap():Number {
			return _labelGap;
		}
		
		
		public function set contentGap(val:Number):void {
			if (_contentGap == val) return;
			_contentGap = val;
			gapChanged = true;
			invalidateProperties();
		}
		
		public function get contentGap():Number {
			return _contentGap;
		}
		
		
		public function set contentLayout(val:String):void {
			if (_contentLayout == val) return;
			_contentLayout = val;
			layoutChanged = true;
			invalidateProperties();
		}

		public function get contentLayout():String {
			return _contentLayout ;
		}
		
		
		override protected function commitProperties():void {
			super.commitProperties();
			
			if (layoutChanged) {
				layoutChanged = false;
				gapChanged = false;
				switch (contentLayout) {
					case LayoutMode.HORIZONTAL:
						var hl:HorizontalLayout = new HorizontalLayout();
						hl.gap = contentGap;
						hl.verticalAlign = "middle";
						contentGroup.layout = hl;
						break;
					
					case LayoutMode.VERTICAL:
						var vl:VerticalLayout = new VerticalLayout();
						vl.gap = contentGap;
						vl.horizontalAlign = "left";
						contentGroup.layout = vl;
						break;
				}
			}
			
			if (gapChanged) {
				gapChanged = false;
				if (contentGroup.layout is HorizontalLayout) {
					(contentGroup.layout as HorizontalLayout).gap = contentGap;
				}
				if (contentGroup.layout is VerticalLayout) {
					(contentGroup.layout as VerticalLayout).gap = contentGap;
				}
			}
		}
	}
}