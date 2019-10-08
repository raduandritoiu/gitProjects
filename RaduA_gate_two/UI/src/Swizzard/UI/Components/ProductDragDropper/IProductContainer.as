package Swizzard.UI.Components.ProductDragDropper
{
	import mx.core.IUIComponent;
	
	public interface IProductContainer extends IUIComponent
	{
		function set currentProductType(val:uint):void;
		function get currentProductType():uint;
		
		function addProduct(product:ProductFamily):void;
		function removeProduct(product:ProductFamily):void;
	}
}