package Swizzard.System.Mediators.Dampers
{
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensAccessory;
	import Swizzard.Data.Models.Enumeration.ExtendedProductType;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Favorites.Proxies.DamperFavoritesProxy;
	import Swizzard.Favorites.Mediators.BaseFavoritesMediator;
	import Swizzard.System.Models.DamperScheduleItem;
	
	
	/**
	 * DEPRECATED. NOT USED ANYMORE.
	 */
	public class DamperFavoritesMediator extends BaseFavoritesMediator
	{
		public static const NAME:String	= "DamperFavoritesWindowMediator_weifj";
		
		
		public function DamperFavoritesMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
			FAVORITES_PROXY_NAME = DamperFavoritesProxy.NAME;
			FILE_EXTENSION = DamperScheduleItem.FILE_EXTENSION_NAME;
		}
		
		
		override protected function areItemsCompatibleObjects(item:*, index:int, arr:Array):Boolean {
			if (item is DamperScheduleItem) return true;
			if (item is AbstractSiemensProduct) {
				var absProduct:AbstractSiemensProduct = item as AbstractSiemensProduct;
				if (absProduct.extendedProductType & ExtendedProductType.DAMPER_ACTUATOR) return true;
				if (absProduct.productType & ProductType.ACCESSORY) {
					var accessory:SiemensAccessory = item as SiemensAccessory;
					if (accessory.info.fromDamper == 1) return true;
				}
			}
			return false;
		}
	}
}