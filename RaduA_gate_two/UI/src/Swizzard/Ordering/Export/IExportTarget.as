package Swizzard.Ordering.Export
{
	public interface IExportTarget
	{
		function exportResult(exportFormat:String, exportType:uint):void;
	}
}