package Swizzard.System.Mediators.Pneumatics
{
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensAccessory;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Favorites.Proxies.PneumaticFavoritesProxy;
	import Swizzard.Favorites.Mediators.BaseFavoritesMediator;
	import Swizzard.System.Models.PneumaticScheduleItem;
	
	
	/**
	 * DEPRECATED. NOT USED ANYMORE.
	 */
	public class PneumaticFavoritesMediator extends BaseFavoritesMediator
	{
		public static const NAME:String	= "PneumaticFavoritesWindowMediator_4p845";
		
		
		public function PneumaticFavoritesMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
			FAVORITES_PROXY_NAME = PneumaticFavoritesProxy.NAME;
			FILE_EXTENSION = PneumaticScheduleItem.FILE_EXTENSION_NAME;
		}
		
		
		override protected function areItemsCompatibleObjects(item:*, index:int, arr:Array):Boolean {
			if (item is PneumaticScheduleItem) return true;
			if (item is AbstractSiemensProduct) {
				var absProduct:AbstractSiemensProduct = item as AbstractSiemensProduct;
				if (absProduct.productType == ProductType.PNEUMATIC) return true;
				if (absProduct.productType & ProductType.ACCESSORY) {
					var accessory:SiemensAccessory = item as SiemensAccessory;
					if (accessory.info.fromPneumatic == 1) return true;
				}
			}
			return false;
		}
	}
}