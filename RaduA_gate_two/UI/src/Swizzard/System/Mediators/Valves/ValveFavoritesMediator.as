package Swizzard.System.Mediators.Valves
{
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.Enumeration.ExtendedProductType;
	import Swizzard.Favorites.Proxies.ValveFavoritesProxy;
	import Swizzard.System.Models.ValveScheduleItem;
	import Swizzard.Favorites.Mediators.BaseFavoritesMediator;
	

	/**
	 * DEPRECATED. NOT USED ANYMORE.
	 */
	public class ValveFavoritesMediator extends BaseFavoritesMediator
	{
		public static const NAME:String	= "ValveFavoritesWindowMediator_dksa8234r";
		
		
		public function ValveFavoritesMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
			FAVORITES_PROXY_NAME = ValveFavoritesProxy.NAME;
			FILE_EXTENSION = ValveScheduleItem.FILE_EXTENSION_NAME;
		}
		
		
		override protected function areItemsCompatibleObjects(item:*, index:int, arr:Array):Boolean {
			if (item is ValveScheduleItem) return true;
			if (item is AbstractSiemensProduct) {
				var absProduct:AbstractSiemensProduct = item as AbstractSiemensProduct;
				if (absProduct.extendedProductType & ExtendedProductType.VALVE) return true;
				if (absProduct.extendedProductType & ExtendedProductType.ACTUATOR_VALVE) return true;
				if (absProduct.extendedProductType & ExtendedProductType.ASSEMBLY) return true;
			}
			return false;
		}
	}
}