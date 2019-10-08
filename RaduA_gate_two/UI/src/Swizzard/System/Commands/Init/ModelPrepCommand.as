package Swizzard.System.Commands.Init
{
	import Swizzard.Data.Proxies.DataProxy;
	import Swizzard.Favorites.Proxies.DamperFavoritesProxy;
	import Swizzard.Favorites.Proxies.PneumaticFavoritesProxy;
	import Swizzard.Favorites.Proxies.ValveFavoritesProxy;
	import Swizzard.System.Preferences.proxies.UserPreferencesProxy;
	import Swizzard.System.Updater.Proxies.ApplicationUpdaterProxy;
	import Swizzard.System.Updater.Proxies.DatabaseUpdaterProxy;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	
	
	public class ModelPrepCommand extends SimpleCommand
	{
		//Called by the macro StartupCommand 
		override public function execute(notification:INotification):void
		{
			facade.registerProxy(new ApplicationUpdaterProxy());
			facade.registerProxy(new DatabaseUpdaterProxy());
			facade.registerProxy(new UserPreferencesProxy());
			facade.registerProxy(new DataProxy());
			facade.registerProxy(new ValveFavoritesProxy());
			facade.registerProxy(new DamperFavoritesProxy());
			facade.registerProxy(new PneumaticFavoritesProxy());
		}
	}
}