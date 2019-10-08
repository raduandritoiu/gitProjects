package Swizzard.Favorites.Proxies
{
	import Swizzard.System.Models.PneumaticScheduleItem;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	
	
	public class PneumaticFavoritesProxy extends BaseFavoritesProxy
	{
		public static const NAME:String		= "PneumaticFavoritesProxy_jd920";
		
		
		public function PneumaticFavoritesProxy() {
			super(NAME);
			FILE_EXTENSION = PneumaticScheduleItem.FILE_EXTENSION_NAME;
		}
		
		
		/**
		 * Create a favorite item. 
		 * This is a stub function and should be extended.
		 */
		override protected function createFavoriteItem():IScheduleItem {
			return new PneumaticScheduleItem();
		}
		
		
		/**
		 * Update or initialize/set different values for a favorite item.
		 * This is a stub function and should be extended.
		 */
		override protected function paddingFavoriteItem(item:IScheduleItem):void {
		}
	}
}