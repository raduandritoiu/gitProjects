package Swizzard.Favorites.Proxies
{
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.System.Models.ValveScheduleItem;

	public class ValveFavoritesProxy extends BaseFavoritesProxy
	{
		public static const NAME:String		= "ValvesFavoritesProxy_kmo823";
		
		
		public function ValveFavoritesProxy() {
			super(NAME);
			FILE_EXTENSION = ValveScheduleItem.FILE_EXTENSION_NAME;
		}
		
		
		/**
		 * Create a favorite item. 
		 * This is a stub function and should be extended.
		 */
		override protected function createFavoriteItem():IScheduleItem {
			return new ValveScheduleItem();
		}
		
		
		/**
		 * Update or initialize/set different values for a favorite item.
		 * This is a stub function and should be extended.
		 */
		override protected function paddingFavoriteItem(item:IScheduleItem):void {
			var valveItem:ValveScheduleItem = item as ValveScheduleItem;
			valveItem.calculatedCv	= 0;
			valveItem.calculatedCv_B = 0;
			valveItem.gpm			= 0;
			valveItem.setQuantity(1, null, 0, 0, 0, true);
			valveItem.tags			= "";
			valveItem.formParams	= null;
		}
	}
}