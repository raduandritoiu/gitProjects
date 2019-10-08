package Swizzard.Favorites.Proxies
{
	import Swizzard.System.Models.DamperScheduleItem;
	import Swizzard.System.Models.PneumaticScheduleItem;
	import Swizzard.System.Models.ValveScheduleItem;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	

	/**
	 * DEPRECATED. NOT USED ANYMORE.
	 */
	public class FavoritesProxy extends BaseFavoritesProxy
	{
		public static const NAME:String		= "FavoritesProxy_9gk3j4";
		
		
		public function FavoritesProxy() {
			super(NAME);
			FILE_EXTENSION = DamperScheduleItem.FILE_EXTENSION_NAME;
		}
		
		
		public function setFileExtension(newFileExt:String):void {
			FILE_EXTENSION = newFileExt;
		}
		
		
		/**
		 * Create a favorite item. 
		 * This is a stub function and should be extended.
		 */
		override protected function createFavoriteItem():IScheduleItem {
			if (FILE_EXTENSION == ValveScheduleItem.FILE_EXTENSION_NAME) {
				return new ValveScheduleItem();
			}
			if (FILE_EXTENSION == DamperScheduleItem.FILE_EXTENSION_NAME) {
				return new DamperScheduleItem();
			}
			if (FILE_EXTENSION == PneumaticScheduleItem.FILE_EXTENSION_NAME) {
				return new PneumaticScheduleItem();
			}
			
			return null;
		}
		
		
		/**
		 * Update or initialize/set different values for a favorite item.
		 * This is a stub function and should be extended.
		 */
		override protected function paddingFavoriteItem(item:IScheduleItem):void {
			if (FILE_EXTENSION == ValveScheduleItem.FILE_EXTENSION_NAME) {
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
}