package Swizzard.UI.ItemRenderers
{
	import mx.controls.advancedDataGridClasses.AdvancedDataGridSortItemRenderer;
	import mx.controls.advancedDataGridClasses.SortInfo;
	import mx.core.mx_internal;
	import mx.managers.ToolTipManager;
	import mx.managers.ToolTipManagerImpl;

	public class ScheduleSortItemRenderer extends AdvancedDataGridSortItemRenderer
	{
		public function ScheduleSortItemRenderer()
		{
			super();
			toolTip	= "this is a test";
		}
		
		override protected function commitProperties():void
		{
			super.commitProperties();
			
			var sortInfo:SortInfo	= getFieldSortInfo();
			
			if (sortInfo)
			{
				var tipText:String	= "sort ";
				tipText				+= (!sortInfo.descending) ? "descending" : "ascending";
				
				if (sortInfo.sequenceNumber > 1)
				{
					tipText	= "add to " + tipText;
				}
				
				toolTip	= tipText;
				
				// The framework doesn't update this in time and does not cause it to refresh.
				ToolTipManagerImpl.getInstance().mx_internal::currentText	= toolTip;
				ToolTipManagerImpl.getInstance().mx_internal::targetChanged();
			}
			else
			{
				toolTip	= null;
			}
			
			
		}
	}
}