package Swizzard.System.InfosWindows.commands
{
	import Swizzard.System.Preferences.proxies.UserPreferencesProxy;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	import Swizzard.System.Models.CompanyInformation;
	
	
	public class CompanyInfoCommand extends SimpleCommand
	{
		public static const STORE_COMPANY_INFO:String		= "storeCompanyInfo";
		public static const GET_COMPANY_INFO:String			= "getCompanyInfo";
		public static const COMPANY_INFO_RECEIVED:String	= "companyInfoReceived";
		
		
		public function CompanyInfoCommand()
		{
			super();
		}
		
		
		override public function execute(notification:INotification):void
		{
			var proxy:UserPreferencesProxy	= facade.retrieveProxy(UserPreferencesProxy.NAME) as UserPreferencesProxy;
			switch (notification.getName())
			{
				case STORE_COMPANY_INFO:
					var info:CompanyInformation	= notification.getBody() as CompanyInformation;
					if (info)
					{
						proxy.preferences.companyInfo = info;
					}	 
					break;
				
				case GET_COMPANY_INFO:
					sendNotification(COMPANY_INFO_RECEIVED, proxy.preferences.companyInfo);
					break;
			}
		}
	}
}