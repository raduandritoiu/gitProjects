package Swizzard.Favorites.Proxies
{
	import flash.filesystem.File;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.System.Persistence.IO.ScheduleItemReader;
	import Swizzard.System.Persistence.IO.ScheduleItemWriter;
	import Swizzard.System.Preferences.proxies.UserPreferencesProxy;
	
	import org.puremvc.as3.patterns.proxy.Proxy;
	
	import utils.LogUtils;

	
	/**
	 * Favorites Proxy
	 * 
	 * This class adds and removes items from the Favroite's file system. 
	 * @author michael
	 */	
	public class BaseFavoritesProxy extends Proxy
	{
		private const favoritesFolder:File = File.applicationStorageDirectory.resolvePath("favorites");
		
		protected var FILE_EXTENSION:String;
		protected var favs:ArrayCollection;
		
		
		public function BaseFavoritesProxy(proxyName:String) {
			super(proxyName);
			favs  = new ArrayCollection();
		}
		
		
		override public function onRegister():void {
			try {
				if (!favoritesFolder.exists)
					favoritesFolder.createDirectory();
			}
			catch (err:Error) {
				// Error can't create favorites folder.
				LogUtils.Error("Corrup files");
				return;
			}
				
			refresh();
		}
		
		
		/**
		 * Collection of Favorite Schedule Items.
		 * 
		 * @see Swizzard.Ordering.ValveScheduleItem
		 */		
		public function get favorites():ArrayCollection {
			return favs;
		}
		
		
		/**
		 * Searched for the product by it's part number.
		 */
		public function hasProduct(product:AbstractSiemensProduct):Boolean {
			try {
				// start by searchinf in the Favoritesarray
				for each (var item:IScheduleItem in favs) {
					if (item.product.partNumber == product.partNumber) {
						return true;
					}
				}
				
				//continue by searching inthe file system - though it is unlikely to find it
				var favorites:Array	= favoritesFolder.getDirectoryListing();
				for each (var fav:File in favorites) {
					if (fav.name == (product.partNumber + "." + FILE_EXTENSION)) {
						return true;
					}
				}
			}
			catch (err:Error) {
				// Error checking product, assume it doesn't exist
				LogUtils.Error("Error checking product, assume it doesn't exist");
			}
			
			return false;
		}
		
		
		/**
		 * Remove an item from Favorites Array and removes the associated Favorite File.
		 * @param item
		 */		
		public function removeItem(item:IScheduleItem):void {
			// search through the  Favorites array 
			for (var i:int = 0; i < favs.length; i++) {
				var existingItem:IScheduleItem = favs.getItemAt(i) as IScheduleItem;
				if (existingItem.product.partNumber == item.product.partNumber) {
					// Delete Favorites File
					var fileName:String = existingItem.product.partNumber;
					fileName 			= fileName.replace("/", "_");
					fileName 			= fileName + "." + item.fileExtension;
					var favoriteFile:File = favoritesFolder.resolvePath(fileName);
					if (favoriteFile.exists) {
						try {
							favs.removeItemAt(i);
							favoriteFile.deleteFile();
						}
						catch (err:Error) {
							// Error Deleting File
							LogUtils.Error("Error Deleting Favorite File");
							Alert.show("Error Deleting Favorite File.");
						}
					}
					else {
						favs.removeItemAt(i);
					}
					break;
				}
			}
		}
		
		
		/**
		 * Add Product to favorites. This adds the Favorite Item and the asscoiated Favorite File
		 * @param product
		 */		
		public function addProduct(product:AbstractSiemensProduct):void {
			var prefProxy:UserPreferencesProxy	= facade.retrieveProxy(UserPreferencesProxy.NAME) as UserPreferencesProxy;
			var scheduleItem:IScheduleItem		= createFavoriteItem();
			if (scheduleItem == null) {
				return;
			}
			
			scheduleItem.product			= product;
			scheduleItem.priceMultiplier	= prefProxy.preferences.globalMultiplier; 
			addScheduleItem(scheduleItem, false);
		}
		
		
		/**
		 * Add Schedule Item to Favorites Array. Also adds the associated Favorite File
		 * @param item
		 */
		public function addScheduleItem(item:IScheduleItem, cloneItem:Boolean):void {
			if (hasProduct(item.product)) {
				return;
			}
			
			if (cloneItem) {
				item = item.clone(false);
			}		
			
			paddingFavoriteItem(item);
			
			var writeSuccess:File = ScheduleItemWriter.writeScheduleItem(item, favoritesFolder);
			if (writeSuccess != null) {
				favs.addItem(item);
			}
		}
		
		
		/**
		 * Create a favorite item. 
		 * This is a stub function and should be extended.
		 */
		protected function createFavoriteItem():IScheduleItem {
			return null;
		}
		
		
		/**
		 * Update or initialize/set different values for a favorite item.
		 * This is a stub function and should be extended.
		 */
		protected function paddingFavoriteItem(item:IScheduleItem):void {
		}
		
		
		/**
		 * Add favorite file to Favorites folder, but does not load the item inside the file, into the arrays.
		 * @param file
		 */		
		public function addFile(file:File):void {
			var target:File	= favoritesFolder.resolvePath(file.name);
			file.copyTo(target, true);
		}
		

		/**
		 * Refresh Favorites
		 */		
		public function refresh():void {
			var files:Array;
			
			try {
				files	= favoritesFolder.getDirectoryListing().filter(favoriteFileFilter);
			}
			catch (err:Error) {
				// Error Access favorites folder.	
				LogUtils.Error("Error Access favorites folder");
			}
			
			favs.removeAll();
			
			if (!files) {
				return;
			}
			
			for each (var f:File in files) {
				var schItem:IScheduleItem = ScheduleItemReader.readScheduleItem(f);
				
				if (schItem) {
					favs.addItem(schItem);
				}
				else {
					try {
						f.deleteFile();
					}
					catch (err:Error) {
						// Can't delete invalid file
						LogUtils.Error("Can't delete invalid file");
					}
				}
			}
			
			favs.refresh();
		}
		
		
		private function favoriteFileFilter(file:*, index:int, arr:Array):Boolean {
			if (!file)
				return false;
			
			if (!(file is File))
				return false;
			
			if (!file.extension)
				return false;
			
			return (!file.isDirectory && file.extension.toLowerCase() == FILE_EXTENSION);
		}
	}
}