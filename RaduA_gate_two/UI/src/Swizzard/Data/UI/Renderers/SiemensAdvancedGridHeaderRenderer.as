package Swizzard.Data.UI.Renderers
{
	import mx.controls.advancedDataGridClasses.AdvancedDataGridHeaderRenderer;
	import mx.core.mx_internal;
	
	
	public class SiemensAdvancedGridHeaderRenderer extends AdvancedDataGridHeaderRenderer
	{
		public function SiemensAdvancedGridHeaderRenderer()
		{
			super();
		}
		
		override protected function measure():void
		{
			super.measure();
			if (label && label.numLines > 1)
			{
				measuredMinHeight = measuredHeight += 4;
			}
		}
		
		
		// Fixes: Issue #1800
		override public function get height():Number
		{
			if (super.height == 0) 
			{
				return this.mx_internal::$height;
			}
			return super.height;
		}
	}
}