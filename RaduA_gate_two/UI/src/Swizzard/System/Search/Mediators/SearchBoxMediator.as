package Swizzard.System.Search.Mediators
{
	import flash.events.Event;
	import flash.utils.clearTimeout;
	import flash.utils.setTimeout;
	
	import mx.collections.IList;
	import mx.formatters.NumberFormatter;
	
	import Swizzard.Data.Models.Enumeration.ExtendedProductType;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Data.Proxies.DataProxy;
	import Swizzard.Data.Utils.SynchronousDataUtility;
	import Swizzard.System.ApplicationFacade;
	import Swizzard.System.Commands.Exit.ShutdownCommand;
	import Swizzard.System.Mediators.Dampers.DamperScheduleMediator;
	import Swizzard.System.Mediators.Pneumatics.PneumaticScheduleMediator;
	import Swizzard.System.Mediators.Valves.ValveScheduleMediator;
	import Swizzard.System.Search.Commands.SearchBoxCommand;
	import Swizzard.System.Search.Events.SearchBoxEvent;
	import Swizzard.System.Search.ui.SearchBox;
	import Swizzard.System.Utils.SwizzardGlobals;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	
	public class SearchBoxMediator extends Mediator
	{
		public static const NAME:String = "SearchBox Mediator";
		// some static variables used in the search tooltips
		// a little unorthodox, but better than on the view
		public static var SYNC_DATA:SynchronousDataUtility;
		public static var NR_FORMAT:NumberFormatter;
		
		
		private var queryDelayTimeout:int = -1;
		
		
		public function SearchBoxMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			
			SYNC_DATA = new SynchronousDataUtility(false);
			NR_FORMAT = new NumberFormatter();
			NR_FORMAT.precision	= 2;
			NR_FORMAT.useThousandsSeparator	= true;
		}
		
		
		override public function onRegister():void
		{
			searchBox.addEventListener(SearchBoxEvent.ADD_BUTTON_CLICKED, onAddButtonClicked, false, 0, true);
			searchBox.addEventListener(Event.CHANGE, searchBoxChangeHandler, false, 0, true);
			if (SYNC_DATA.isDisposed)
				SYNC_DATA.reconcile();
		}
		
		
		override public function onRemove():void
		{
			SYNC_DATA.dispose();
		}
		
		
		/**
		 * Casts the viewcomponent as the searchbox
		 * 
		 * @return the searchbox
		 */		
		public function get searchBox():SearchBox
		{
			return viewComponent as SearchBox;	
		}
		
		
		/**
		 * Grabs the valve or actuator from the item renderer and adds it to the valve schedule 
		 * @param e
		 */		
		private function onAddButtonClicked(e:SearchBoxEvent):void
		{
			switch (e.item.extendedProductType) {
				// add valve
				case ExtendedProductType.VALVE:
				// add actuators
				case ExtendedProductType.ACTUATOR_VALVE:
				// add assemblyes
				case ExtendedProductType.ASSEMBLY:
				// add misc products 
				case ExtendedProductType.MISC:
				{
					if (SwizzardGlobals.CURRENT_PRODUCT_TYPE != ProductType.VALVE) {
						sendNotification(ApplicationFacade.PRODUCT_CHANGED, ProductType.VALVE);
					}
					ValveScheduleMediator(facade.retrieveMediator(ValveScheduleMediator.NAME)).addProduct(e.item, 1, false);
					break;
				}
					
				// add dampers
				case ExtendedProductType.DAMPER_ACTUATOR:
				// add accessories
				case ExtendedProductType.ACCESSORY:
				{
					if (SwizzardGlobals.CURRENT_PRODUCT_TYPE != ProductType.DAMPER) {
						sendNotification(ApplicationFacade.PRODUCT_CHANGED, ProductType.DAMPER);
					}
					DamperScheduleMediator(facade.retrieveMediator(DamperScheduleMediator.NAME)).addProduct(e.item);
					break;
				}
					
				case ExtendedProductType.ACTUATOR_BOTH:
				{
					if (SwizzardGlobals.CURRENT_PRODUCT_TYPE == ProductType.VALVE) {
						ValveScheduleMediator(facade.retrieveMediator(ValveScheduleMediator.NAME)).addProduct(e.item, 1, false);
					}
					else if (SwizzardGlobals.CURRENT_PRODUCT_TYPE == ProductType.DAMPER) {
						DamperScheduleMediator(facade.retrieveMediator(DamperScheduleMediator.NAME)).addProduct(e.item);
					}
					break;
				}
					
				case ExtendedProductType.PNEUMATIC:
				{
					if (SwizzardGlobals.CURRENT_PRODUCT_TYPE != ProductType.PNEUMATIC) {
						sendNotification(ApplicationFacade.PRODUCT_CHANGED, ProductType.PNEUMATIC);
					}
					PneumaticScheduleMediator(facade.retrieveMediator(PneumaticScheduleMediator.NAME)).addProduct(e.item, 1, false);
					break;
				}
			}
		}
		
		
		private function searchBoxChangeHandler(event:Event):void
		{
			if (queryDelayTimeout > -1) {
				clearTimeout(queryDelayTimeout);
			}
			queryDelayTimeout	= setTimeout(actuallyQueryData, 250);
		}
		
		
		private function actuallyQueryData():void
		{
			queryDelayTimeout	= -1;
			sendNotification(SearchBoxCommand.SEARCH_PART_NUMBER, searchBox.text);	
		}
		
		
		/**
		 *   NOTIFICATIONS
		 */
		
		override public function listNotificationInterests():Array
		{
			return [SearchBoxCommand.SEARCH_BOX_RESULTS, SearchBoxCommand.SEARCH_CROSSREF_RESULTS, 
				ShutdownCommand.SHUTDOWN, DataProxy.RELEASE_DATABASE, DataProxy.ATTACH_DATABASE];
		}

		
		override public function handleNotification(notification:INotification):void
		{
			//set dataprovider of the list ('dropdown') to data received
			switch (notification.getName())
			{
				case SearchBoxCommand.SEARCH_BOX_RESULTS:
				{
					searchBox.dataProvider = notification.getBody() as IList;
					break;
				}
					
				case SearchBoxCommand.SEARCH_CROSSREF_RESULTS:
				{
					searchBox.crossReferenceDataProvider = notification.getBody() as IList;
					break;
				}
				
				case ShutdownCommand.SHUTDOWN:
				{
					// Release DB Resources
					SYNC_DATA.dispose();
					break;
				}
					
				case DataProxy.RELEASE_DATABASE:
				{
					SYNC_DATA.dispose();
					break;
				}
					
				case DataProxy.ATTACH_DATABASE:
				{
					if (SYNC_DATA.isDisposed)
						SYNC_DATA.reconcile();
					break;
				}
			} 
		}		
	}
}