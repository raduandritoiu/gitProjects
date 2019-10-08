package Swizzard.Favorites.Proxies
{
	import Swizzard.System.Models.DamperScheduleItem;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	
	
	public class DamperFavoritesProxy extends BaseFavoritesProxy
	{
		public static const NAME:String		= "DamperFavoritesProxy_93kf01";
		
		
		public function DamperFavoritesProxy() {
			super(NAME);
			FILE_EXTENSION = DamperScheduleItem.FILE_EXTENSION_NAME;
		}
		
		
		/**
		 * Create a favorite item. 
		 * This is a stub function and should be extended.
		 */
		override protected function createFavoriteItem():IScheduleItem {
			return new DamperScheduleItem();
		}
		
		
		/**
		 * Update or initialize/set different values for a favorite item.
		 * This is a stub function and should be extended.
		 */
		override protected function paddingFavoriteItem(item:IScheduleItem):void {
		}
	}
}