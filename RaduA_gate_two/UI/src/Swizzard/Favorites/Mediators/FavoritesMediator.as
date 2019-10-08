package Swizzard.Favorites.Mediators
{
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.Data.Models.SiemensAccessory;
	import Swizzard.Data.Models.Enumeration.ExtendedProductType;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Favorites.Proxies.DamperFavoritesProxy;
	import Swizzard.Favorites.Proxies.PneumaticFavoritesProxy;
	import Swizzard.Favorites.Proxies.ValveFavoritesProxy;
	import Swizzard.System.ApplicationFacade;
	import Swizzard.System.Models.DamperScheduleItem;
	import Swizzard.System.Models.PneumaticScheduleItem;
	import Swizzard.System.Models.ValveScheduleItem;
	import Swizzard.UI.Components.DataGridClasses.FavoritesColumnsUtil;
	
	import org.puremvc.as3.interfaces.INotification;
	
	
	public class FavoritesMediator extends BaseFavoritesMediator
	{
		public static const NAME:String	= "FavoritesWindowMediator_jgo44";
		
		
		public function FavoritesMediator(productType:uint) {
			super(NAME);
			productTypeChanged(productType);
		}
		
		
		override public function listNotificationInterests():Array {
			return [ApplicationFacade.PRODUCT_CHANGED];
		}
		
		
		override public function handleNotification(notification:INotification):void {
			switch (notification.getName()) {
				case ApplicationFacade.PRODUCT_CHANGED:
					var newProductType:uint = notification.getBody() as uint;
					productTypeChanged(newProductType);
					break;
			}
		}
		
		
		protected function productTypeChanged(newProductType:uint):void {
			if (newProductType == ProductType.VALVE) {
				FAVORITES_PROXY_NAME = ValveFavoritesProxy.NAME;
				FILE_EXTENSION = ValveScheduleItem.FILE_EXTENSION_NAME;
				COLUMNS = FavoritesColumnsUtil.getValveCols();
				TITLE = "Favorite Valves";
				INOF_TEXT = "Drag any valve, actuator, or assembly from the application into the grid below. They will be saved for quick access and future use.";
			}
			else if (newProductType == ProductType.DAMPER) {
				FAVORITES_PROXY_NAME = DamperFavoritesProxy.NAME;
				FILE_EXTENSION = DamperScheduleItem.FILE_EXTENSION_NAME;
				COLUMNS = FavoritesColumnsUtil.getDamperCols();
				TITLE = "Favorite Dampers";
				INOF_TEXT = "Drag any damper or accessory kit from the application into the grid below. They will be saved for quick access and future use.";
			}
			else if (newProductType == ProductType.PNEUMATIC) {
				FAVORITES_PROXY_NAME = PneumaticFavoritesProxy.NAME;
				FILE_EXTENSION = PneumaticScheduleItem.FILE_EXTENSION_NAME;
				COLUMNS = FavoritesColumnsUtil.getPneumaticCols();
				TITLE = "Favorite Pneumatics";
				INOF_TEXT = "Drag any pneumatic or accessory kit from the application into the grid below. They will be saved for quick access and future use.";
			}
			
			updateFavoritesWindow();
		}
		
		
		override protected function areItemsCompatibleObjects(item:*, index:int, arr:Array):Boolean {
			if (FILE_EXTENSION == ValveScheduleItem.FILE_EXTENSION_NAME) {
				if (item is ValveScheduleItem) 
					return true;
				if (item is AbstractSiemensProduct) {
					var absProduct:AbstractSiemensProduct = item as AbstractSiemensProduct;
					if (absProduct.extendedProductType & ExtendedProductType.VALVE) 
						return true;
					if (absProduct.extendedProductType & ExtendedProductType.ACTUATOR_VALVE) 
						return true;
					if (absProduct.extendedProductType & ExtendedProductType.ASSEMBLY) 
						return true;
				}
			}
			
			else if (FILE_EXTENSION == DamperScheduleItem.FILE_EXTENSION_NAME) {
				if (item is DamperScheduleItem) 
					return true;
				if (item is AbstractSiemensProduct) {
					var absProduct:AbstractSiemensProduct = item as AbstractSiemensProduct;
					if (absProduct.extendedProductType & ExtendedProductType.DAMPER_ACTUATOR) 
						return true;
					if (absProduct.productType & ProductType.ACCESSORY) {
						var accessory:SiemensAccessory = item as SiemensAccessory;
						if (accessory.info.fromDamper == 1) 
							return true;
					}
				}
			}
			
			else if (FILE_EXTENSION == PneumaticScheduleItem.FILE_EXTENSION_NAME) {
				if (item is PneumaticScheduleItem) 
					return true;
				if (item is AbstractSiemensProduct) {
					var absProduct:AbstractSiemensProduct = item as AbstractSiemensProduct;
					if (absProduct.productType == ProductType.PNEUMATIC) 
						return true;
					if (absProduct.productType & ProductType.ACCESSORY) {
						var accessory:SiemensAccessory = item as SiemensAccessory;
						if (accessory.info.fromPneumatic == 1) 
							return true;
					}
				}
			}
			
			return false;
		}
	}
}