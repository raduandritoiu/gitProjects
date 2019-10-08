package Swizzard.System
{
	import flash.desktop.NativeApplication;
	import flash.events.Event;
	import flash.events.InvokeEvent;
	import flash.events.MouseEvent;
	import flash.events.NativeWindowBoundsEvent;
	import flash.filesystem.File;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import flash.utils.clearTimeout;
	import flash.utils.setTimeout;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.controls.MenuBar;
	import mx.events.CloseEvent;
	import mx.events.MenuEvent;
	import mx.events.PropertyChangeEvent;
	
	import spark.components.TextInput;
	import spark.events.IndexChangeEvent;
	import spark.events.TextOperationEvent;
	
	import Swizzard.Data.AsynchronousQuery.Commands.Accessory.AccessoryQueryCommand;
	import Swizzard.Data.AsynchronousQuery.Commands.Dampers.DamperQueryCommand;
	import Swizzard.Data.AsynchronousQuery.Commands.Pneumatics.PneumaticQueryCommand;
	import Swizzard.Data.AsynchronousQuery.Commands.Valves.ValveQueryCommand;
	import Swizzard.Data.AsynchronousQuery.Enumeration.ProductQueryType;
	import Swizzard.Data.AsynchronousQuery.Token.AccessoryQueryToken;
	import Swizzard.Data.AsynchronousQuery.Token.BaseQueryToken;
	import Swizzard.Data.AsynchronousQuery.Token.DamperQueryToken;
	import Swizzard.Data.AsynchronousQuery.Token.PneumaticQueryToken;
	import Swizzard.Data.AsynchronousQuery.Token.ValveQueryToken;
	import Swizzard.Data.Models.SiemensValve;
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Data.Models.Query.AccessoryQueryModel;
	import Swizzard.Data.Models.Query.DamperQueryModel;
	import Swizzard.Data.Models.Query.PneumaticQueryModel;
	import Swizzard.Data.Models.Query.ValveQueryModel;
	import Swizzard.Data.Models.Query.ValveSystemQueryModel;
	import Swizzard.Data.Utils.QueryUtility;
	import Swizzard.Favorites.Mediators.FavoritesMediator;
	import Swizzard.Favorites.UI.FavoritesWindow;
	import Swizzard.Ordering.Export.CSVExporter;
	import Swizzard.Ordering.Export.ExcelExporter;
	import Swizzard.Ordering.Export.PDFExporter;
	import Swizzard.System.Commands.Exit.ShutdownCommand;
	import Swizzard.System.ContentDownload.Mediator.ContentDownloadMediator;
	import Swizzard.System.ContentDownload.Proxy.ContentProxy;
	import Swizzard.System.InfosWindows.UI.CustomerInfoEditorForm;
	import Swizzard.System.InfosWindows.UI.UserInfoEditorForm;
	import Swizzard.System.InfosWindows.mediators.CustomerInfoEditorFormMediator;
	import Swizzard.System.InfosWindows.mediators.UserInfoEditorFormMediator;
	import Swizzard.System.Mediators.ProductDragDropMediator;
	import Swizzard.System.Mediators.Dampers.DamperGridsMediator;
	import Swizzard.System.Mediators.Dampers.DamperParametersFormMediator;
	import Swizzard.System.Mediators.Dampers.DamperScheduleMediator;
	import Swizzard.System.Mediators.Pneumatics.PneumaticGridsMediator;
	import Swizzard.System.Mediators.Pneumatics.PneumaticParametersFormMediator;
	import Swizzard.System.Mediators.Pneumatics.PneumaticScheduleMediator;
	import Swizzard.System.Mediators.Valves.ValveGridsMediator;
	import Swizzard.System.Mediators.Valves.ValveParametersFormMediator;
	import Swizzard.System.Mediators.Valves.ValveScheduleMediator;
	import Swizzard.System.Models.VSSTProject;
	import Swizzard.System.Persistence.Commands.PersistenceCommand;
	import Swizzard.System.Persistence.IO.ProjectReader;
	import Swizzard.System.Persistence.Proxies.PersistenceProxy;
	import Swizzard.System.Preferences.UserPreferences;
	import Swizzard.System.Preferences.proxies.UserPreferencesProxy;
	import Swizzard.System.Search.Mediators.SearchBoxMediator;
	import Swizzard.System.Utils.MessageConstants;
	import Swizzard.System.Utils.ProductTypeItem;
	import Swizzard.System.Utils.SwizzardGlobals;
	import Swizzard.UI.Components.DataGridClasses.GridColumnsUtil;
	import Swizzard.UI.Components.SplashScreen.SplashScreen;
	import Swizzard.UI.Windows.EULAWindow;
	import Swizzard.UI.Windows.HelpWindow;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	import utils.LogUtils;
	
	// RADU_TODO_IMPROVE: (put ths note here) - try and make some GenericQueryCommands for DAmper,Pneumatic and Accessory
	// RADU_TODO_IMPROVE: (put ths note here) - maybe I will have to make QueryCommandsComplete send a special notiffication 
	// 							stored in the token, just as target, or reuse target, have no ideea
	
	
	public class ApplicationMediator extends Mediator
	{
		public static const NAME:String	= "applicationMediator";
		
		private var preferences:UserPreferences;
		private var currentProject:VSSTProject;
		
		public var valveQueryModel:ValveSystemQueryModel;
		public var damperQueryModel:DamperQueryModel;
		public var accessoryQueryModel:AccessoryQueryModel;
		public var pneumaticQueryModel:PneumaticQueryModel;
		
		private var shutdownPending:Boolean;
		private var newProjectPending:Boolean;
		private var projectDirty:Boolean;
		private var openProjectAfterSavingThisOne:Boolean;
		
		private var queryDelay:Number = 100;
		private var valveQueryTimeout:int = -1;
		private var valveCallLaterRunning:Boolean;
		private var damperQueryTimeout:int = -1;
		private var damperCallLaterRunning:Boolean;
		private var accessoryQueryTimeout:int = -1;
		private var accessoryCallLaterRunning:Boolean;
		private var pneumaticQueryTimeout:int = -1;
		private var pneumaticCallLaterRunning:Boolean;
		
		
		public function ApplicationMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			
			valveQueryModel = new ValveSystemQueryModel();
			valveQueryModel.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, valveQueryModelChanged, false, 0, true);
			
			damperQueryModel = new DamperQueryModel();
			damperQueryModel.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, damperQueryModelChanged, false, 0, true);
			
			accessoryQueryModel = new AccessoryQueryModel();
			accessoryQueryModel.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, accessoryQueryModelChanged, false, 0, true);
			
			pneumaticQueryModel = new PneumaticQueryModel();
			pneumaticQueryModel.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, pneumaticQueryModelChanged, false, 0, true);
			
			currentProject	= new VSSTProject();
		}
		
		
		// RADU_TODO_IMPROVE: these must be placed elsewhere, in separate files, maybe in mediators for Product Parameters
		// --------- FUNCTIONS THAT EXECUTE A QUERY WHEN THE QUERY MODEL CHANGES -------- //
		// --- execution is delayed so that you make one query for a bunch of changes --- //
		
		// -- VALVES (AND ACTUATORS) ----------------------------

		private function valveQueryModelChanged(event:PropertyChangeEvent):void {
			var invalidateValves:Boolean 	= QueryUtility.ChangeRequiresValveRefresh(event);
			var isValveQuery:Boolean		= event.source is ValveQueryModel;
			if (valveQueryTimeout > -1) {
				clearTimeout(valveQueryTimeout);			
			}
			valveQueryTimeout = setTimeout(refreshValveQuery, queryDelay, isValveQuery, invalidateValves);
		}
				
		public function refreshValveQuery(isValve:Boolean = true, invalidateValves:Boolean = false):void {
			valveQueryTimeout = -1;
			if (!valveCallLaterRunning) {
				app.callLater(executeValveQuery, [isValve, invalidateValves]);
				valveCallLaterRunning	= true;
			}
		}
		
		private function executeValveQuery(isValve:Boolean, invalidateValves:Boolean):void {
			valveCallLaterRunning		= false;
			var token:ValveQueryToken	= new ValveQueryToken();
			token.target				= ProductQueryType.VALVES;
			token.queryModel			= valveQueryModel;
			token.valve					= app.valveGridsBox.valveGrid.selectedItem as SiemensValve;
			if (invalidateValves) {
				token.isValveQuery			= true;
				token.actuatorQueryPending	= !isValve;
			}
			else {
				token.isValveQuery			= isValve;
				token.actuatorQueryPending	= false;
			}
			
			LogUtils.Debug("************* query request --- beacuse model changed");
			sendNotification(ValveQueryCommand.EXECUTE_VALVE_QUERY, token);
		}
		
		// -- DAMPERS ----------------------------
		
		private function damperQueryModelChanged(event:PropertyChangeEvent):void {
			if (damperQueryTimeout > -1) {
				clearTimeout(damperQueryTimeout);			
			}
			damperQueryTimeout = setTimeout(refreshDamperQuery, queryDelay);
		}
		
		private function refreshDamperQuery():void {
			damperQueryTimeout = -1;
			if (!damperCallLaterRunning) {
				app.callLater(executeDamperQuery);
				damperCallLaterRunning	= true;
			}
		}
		
		private function executeDamperQuery():void {
			damperCallLaterRunning		= false;
			var token:BaseQueryToken 	= new DamperQueryToken();
			token.target 				= ProductQueryType.DAMPERS;
			token.queryModel 			= damperQueryModel;
			
			LogUtils.Debug("************* query request --- beacuse model changed");
			sendNotification(DamperQueryCommand.EXECUTE_DAMPER_QUERY, token);
		}
		
		// -- ACCESSORIES (for Dampers) ----------------------------
		
		private function accessoryQueryModelChanged(event:PropertyChangeEvent):void {
			if (accessoryQueryTimeout > -1) {
				clearTimeout(accessoryQueryTimeout);			
			}
			accessoryQueryTimeout = setTimeout(refreshAccessoryQuery, queryDelay);
		}
		
		private function refreshAccessoryQuery():void {
			accessoryQueryTimeout = -1;
			if (!accessoryCallLaterRunning) {
				app.callLater(executeAccessoryQuery);
				accessoryCallLaterRunning	= true;
			}
		}
		
		private function executeAccessoryQuery():void {
			accessoryCallLaterRunning	= false;
			var token:BaseQueryToken 	= new AccessoryQueryToken();
			token.target 				= ProductQueryType.ACCESSORIES;
			token.queryModel 			= accessoryQueryModel;
			
			LogUtils.Debug("************* query request --- beacuse model changed");
			sendNotification(AccessoryQueryCommand.EXECUTE_ACCESSORY_QUERY, token);
		}
		
		// -- PNEUMATICS ----------------------------
		
		private function pneumaticQueryModelChanged(event:PropertyChangeEvent):void {
			if (pneumaticQueryTimeout > -1) {
				clearTimeout(pneumaticQueryTimeout);			
			}
			pneumaticQueryTimeout = setTimeout(refreshPneumaticQuery, queryDelay);
		}
		
		private function refreshPneumaticQuery():void {
			pneumaticQueryTimeout = -1;
			if (!pneumaticCallLaterRunning) {
				app.callLater(executePneumaticQuery);
				pneumaticCallLaterRunning	= true;
			}
		}
		
		private function executePneumaticQuery():void {
			pneumaticCallLaterRunning	= false;
			var token:BaseQueryToken 	= new PneumaticQueryToken();
			token.target 				= ProductQueryType.PNEUMATICS;
			token.queryModel 			= pneumaticQueryModel;
			
			LogUtils.Debug("************* query request --- beacuse model changed");
			sendNotification(PneumaticQueryCommand.EXECUTE_PNEUMATIC_QUERY, token);
		}
		// -------------- END --- FUNCTIONS FOR QUERY ----------------------- //
		
		
		
		
		private function get app():SimpleSelect {
			return viewComponent as SimpleSelect;
		}
		
		
		override public function onRegister():void {
			// - Get preferences and preference proxy (it is created beafore)
			var prefProxy:UserPreferencesProxy	= facade.retrieveProxy(UserPreferencesProxy.NAME) as UserPreferencesProxy;
			preferences	= prefProxy.preferences;
			// - Configure App From Preferences
			GridColumnsUtil.gridLabelMultiplier	= preferences.globalMultiplier;
			sendNotification(MessageConstants.MULTIPLIER_CHANGED, preferences.globalMultiplier);
			
			
			// --- Set up mediators and listeners -----
			// ----------------------------------------------------------------
			
			// - general preferences mediator
			facade.registerMediator(new FavoritesMediator(SwizzardGlobals.CURRENT_PRODUCT_TYPE));
			
			
			// --- For VALVES
			// - valve form parameter mediator
			var valveFormMediator:ValveParametersFormMediator = new ValveParametersFormMediator(app.valveParamsSlider.valveParameterForm);
			facade.registerMediator(valveFormMediator);
			
			// - set valve and actuator query models for the forms
			app.valveParamsSlider.valveParameterForm.valveQueryModel	= valveQueryModel.valve;
			app.valveParamsSlider.actuatorParameterForm.valveQueryModel = valveQueryModel.valve;
			app.valveParamsSlider.actuatorParameterForm.actuatorQueryModel	= valveQueryModel.actuator;
			
			// - valve grids (valve and acutoator grids) mediator
			var valveGridsMediator:ValveGridsMediator = new ValveGridsMediator(app.valveGridsBox);
			valveGridsMediator.queryModel = valveQueryModel;
			facade.registerMediator(valveGridsMediator);
			
			// - valve schedule mediators - set project schedule to mediator schedule 
			// (and not the other way around) because we presume the project is empty
			var valveScheduleMediator:ValveScheduleMediator = new ValveScheduleMediator(app.valveScheduleBox);
			facade.registerMediator(valveScheduleMediator);
			if (currentProject) {
				currentProject.valveSchedule = valveScheduleMediator.getRawDataProvider();
			}
			
			
			// --- For DAMPERS
			// - damper form parameter mediator
			var damperFormMediator:DamperParametersFormMediator = new DamperParametersFormMediator(app.damperParamsSlider.damperParameterForm);
			facade.registerMediator(damperFormMediator);
			
			// - set damper query models for the forms
			app.damperParamsSlider.damperParameterForm.damperQueryModel = damperQueryModel;
			
			// - damper grids (dampersand accesories grids) mediator
			var damperGridsMediator:DamperGridsMediator = new DamperGridsMediator(app.damperGridsBox);
			damperGridsMediator.damperQueryModel = damperQueryModel;
			damperGridsMediator.accessoryQueryModel = accessoryQueryModel;
			facade.registerMediator(damperGridsMediator);
			
			// - damper schedule mediators - set project schedule to mediator schedule 
			// (and not the other way around) because we presume the project is empty
			var damperScheduleMediator:DamperScheduleMediator = new DamperScheduleMediator(app.damperScheduleBox);
			facade.registerMediator(damperScheduleMediator);
			if (currentProject) {
				currentProject.damperSchedule = damperScheduleMediator.getRawDataProvider();
			}
			
			
			// --- For PNEUMATICS
			// - pneumatic form parameter mediator
			var pneumaticFormMediator:PneumaticParametersFormMediator = new PneumaticParametersFormMediator(app.pneumaticParamsSlider.pneumaticParameterForm);
			facade.registerMediator(pneumaticFormMediator);
			
			// - set damper query models for the forms
			app.pneumaticParamsSlider.pneumaticParameterForm.pneumaticQueryModel = pneumaticQueryModel;
			
			// - damper grids (dampersand accesories grids) mediator
			var pneumaticGridsMediator:PneumaticGridsMediator = new PneumaticGridsMediator(app.pneumaticGridsBox);
			pneumaticGridsMediator.pneumaticQueryModel = pneumaticQueryModel;
			pneumaticGridsMediator.accessoryQueryModel = accessoryQueryModel;
			facade.registerMediator(pneumaticGridsMediator);
			
			// - damper schedule mediators - set project schedule to mediator schedule 
			// (and not the other way around) because we presume the project is empty
			var pneumaticScheduleMediator:PneumaticScheduleMediator = new PneumaticScheduleMediator(app.pneumaticScheduleBox);
			facade.registerMediator(pneumaticScheduleMediator);
			if (currentProject) {
				currentProject.pneumaticSchedule = pneumaticScheduleMediator.getRawDataProvider();
			}
			
			
			// --- Set UI listeners
			// ---- these should stay the same for all products (no matter what) --------------
			// - there are some small thing to add
			
			// - invoke the application by double-clicking a file
			app.addEventListener(InvokeEvent.INVOKE, onApplicationInvoke, false, 0, true);
			// - menu bar
			app.menubar.mainMenuBar.addEventListener(MenuEvent.ITEM_CLICK, menuItemClickHandler, false, 0, true);
			// - productBar
			app.productsBar.addEventListener(IndexChangeEvent.CHANGE, productBarChanged, false, 0, true);
			// - window controls
			app.windowControls.closeButton.addEventListener(MouseEvent.CLICK, applicationCloseButtonClickHandler, false, 0, true);
			
			// - search box mediator
			var searchBoxMediator:SearchBoxMediator = new SearchBoxMediator(app.searchBox);
			facade.registerMediator(searchBoxMediator);
			// - content download mediator
			var contentMediator:ContentDownloadMediator = new ContentDownloadMediator(app.statusView);
			facade.registerMediator(contentMediator);
			
			
			// - product drag-drop mediator
			// - HERE: add EACH query model for each PRODUCT to the mediator
			var productSelectionMediator:ProductDragDropMediator = new ProductDragDropMediator(app.productDragDrop);
			productSelectionMediator.valveQueryModel 		= valveQueryModel;
			productSelectionMediator.damperQueryModel 		= damperQueryModel;
			productSelectionMediator.pneumaticQueryModel 	= pneumaticQueryModel;
			facade.registerMediator(productSelectionMediator);

			// - these are UI hacks
			// - HERE: add event listeners fot EACH PRODUCT shcedule box
			app.valveScheduleBox.customerInfoButton.addEventListener(MouseEvent.CLICK, customerInfoButtonClickHandler, false, 0, true);
			app.damperScheduleBox.customerInfoButton.addEventListener(MouseEvent.CLICK, customerInfoButtonClickHandler, false, 0, true);
			app.pneumaticScheduleBox.customerInfoButton.addEventListener(MouseEvent.CLICK, customerInfoButtonClickHandler, false, 0, true);
			app.valveScheduleBox.multiplierInput.addEventListener(Event.CHANGE, multiplierChangeHandler, false, 0, true);
			app.damperScheduleBox.multiplierInput.addEventListener(Event.CHANGE, multiplierChangeHandler, false, 0, true);
			app.pneumaticScheduleBox.multiplierInput.addEventListener(Event.CHANGE, multiplierChangeHandler, false, 0, true);

			
			// - Export buttons
			app.exportExcelButton.addEventListener(MouseEvent.CLICK, exportExcelButtonClickHandler, false, 0, true);
			app.exportPDFButton.addEventListener(MouseEvent.CLICK, exportPDFButtonClickHandler, false, 0, true);
			app.exportWebshopButton.addEventListener(MouseEvent.CLICK, exportWebShopButtonClickHandler, false, 0, true);
			app.saveButton.addEventListener(MouseEvent.CLICK, saveButtonClickHandler, false, 0, true);
			// - nativeWindow preferences
			NativeApplication.nativeApplication.addEventListener(Event.EXITING, exitingHandler, false, 0, true);
			app.nativeWindow.addEventListener(NativeWindowBoundsEvent.MOVE, windowSizeChangeHandler, false, 0, true);
			app.nativeWindow.addEventListener(NativeWindowBoundsEvent.RESIZE, windowSizeChangeHandler, false, 0, true);
			
			
			// - create and set the property bar types
			// - HERE: add EACH PRODUCT to the array
			app.productsBar.dataProvider = new ArrayCollection([
				new ProductTypeItem(ProductType.VALVE, "Valves & Actuators"), 
				new ProductTypeItem(ProductType.DAMPER, "Electric Actuators"),
				new ProductTypeItem(ProductType.PNEUMATIC, "Pneumatic Actuators")
			]);
			setCurrentProductType(SwizzardGlobals.CURRENT_PRODUCT_TYPE);
		}
		
		
		/** Load the file which has invoked the application.
		 * eg: When one double-clicks a *.svs (schedule file) to open
		 * 		that file invokes the application. */
		private function onApplicationInvoke(e:InvokeEvent):void {
			if (e.arguments && e.arguments.length > 0) {
				var location:String		= e.arguments[0] as String;
				var f:File				= new File(location);
				var project:VSSTProject	= ProjectReader.getInstance().readProjectFile(f);
				sendNotification(PersistenceProxy.PROJECT_LOADED, project);
			}
		}
		
		
		private function applicationCloseButtonClickHandler(event:MouseEvent):void {
			if (projectDirty) {
				Alert.show("Do you want to save your schedule?", "Save?", Alert.YES | Alert.NO | Alert.CANCEL, app, chooseSaveConfirmHandler, null, Alert.YES);
			}
			else {
				sendNotification(ShutdownCommand.SHUTDOWN);
			}
		}
		

		private function exitingHandler(e:Event):void {
			sendNotification(ShutdownCommand.SHUTDOWN);
		}
		
		
		private function windowSizeChangeHandler(event:NativeWindowBoundsEvent):void {
			var preferenceProxy:UserPreferencesProxy = facade.retrieveProxy(UserPreferencesProxy.NAME) as UserPreferencesProxy;
			if (preferenceProxy == null) return;
			preferenceProxy.gatherData();
		}
		
		
		
		private function customerInfoButtonClickHandler(event:MouseEvent):void {
			var win:CustomerInfoEditorForm = CustomerInfoEditorForm.Show();
			if (!facade.hasMediator(CustomerInfoEditorFormMediator.NAME)) {
				var mediator:CustomerInfoEditorFormMediator	= new CustomerInfoEditorFormMediator(win);
				mediator.setProject(currentProject);
				facade.registerMediator(mediator);
			}
		}
		
		
		private function multiplierChangeHandler(event:TextOperationEvent):void {
			var multInput:TextInput = event.target as TextInput;
			var multiplier:Number = parseFloat(multInput.text);
			
			if (isNaN(multiplier)) {
				multiplier = 0;
			}
			if (multiplier == 0) {
				return;
			}
			
			GridColumnsUtil.gridLabelMultiplier	= multiplier;
			preferences.globalMultiplier = multiplier;
			sendNotification(MessageConstants.MULTIPLIER_CHANGED, multiplier);
			projectDirty = true;
		}
		
		
		private function exportExcelButtonClickHandler(event:MouseEvent):void {
			ExcelExporter.getInstance().exportToExcelFile(app.valveScheduleBox.scheduleGrid, app.damperScheduleBox.scheduleGrid, app.pneumaticScheduleBox.scheduleGrid);
		}
		
		
		private function exportPDFButtonClickHandler(event:MouseEvent):void {
			PDFExporter.getInstance().exportToPDFFile(app.valveScheduleBox.scheduleGrid, app.damperScheduleBox.scheduleGrid, app.pneumaticScheduleBox.scheduleGrid, [preferences.companyInfo, currentProject.customerInformation], true);
		}
		
		
		private function exportWebShopButtonClickHandler(event:MouseEvent):void {
			CSVExporter.getInstance().exportToCSVFile(currentProject);
		}
		
		
		private function saveButtonClickHandler(event:MouseEvent):void {
			sendNotification(PersistenceCommand.SAVE_PROJECT, currentProject);
		}
		
		
		private function productBarChanged(evt:Event):void {
			var newType:int = app.productsBar.selectedItem.type;
			if (SwizzardGlobals.CURRENT_PRODUCT_TYPE != newType) {
				sendNotification(ApplicationFacade.PRODUCT_CHANGED, newType);
			}
			// open the menu
			app.productDragDrop.productSource.currentProductType = newType;
			app.productDragDrop.productSource.openAll();
		}
		
		
		// ----------- FUNCTION that handles the MENU CLICKs ---------- //
		
		private function menuItemClickHandler(event:MenuEvent):void {
 			var action:String	= event.item.@action.toString();
			var menu:MenuBar 	= event.target as MenuBar;
 			
 			switch (action) {
 				case "file.new":
					if (projectDirty) {
						if (currentProject == null) {
							createCurrentProject();
						}
 						Alert.show("Do you want to save your current schedules?", "Save?", Alert.NONMODAL | Alert.YES | Alert.NO | Alert.CANCEL, app, newProjectSaveConfirmHandler, null, Alert.YES);
					}
 					else {
						sendNotification(PersistenceCommand.NEW_PROJECT);
					}
 					break;
					
 				case "file.open":
					if (projectDirty) {
						if (currentProject == null) {
							createCurrentProject();
						}
						Alert.show("Do you want to save your schedules?", "Save?", Alert.YES | Alert.NO | Alert.CANCEL, app, openProjectOverride, null, Alert.YES);
					}
					else {
 						sendNotification(PersistenceCommand.LOAD_PROJECT);
					}
 					break;
					
 				case "file.save":
					if (currentProject == null) {
						createCurrentProject();
					}
		 			sendNotification(PersistenceCommand.SAVE_PROJECT, currentProject);
 					break;
					
				case "file.saveAs":
					if (currentProject == null) {
						createCurrentProject();
					}
					sendNotification(PersistenceCommand.SAVE_AS_PROJECT, currentProject);
					break;
					
 				case "file.exit":
					if (projectDirty) {
 						Alert.show("Do you want to save your schedules?", "Save?", Alert.YES | Alert.NO | Alert.CANCEL, app, chooseSaveConfirmHandler, null, Alert.YES);
					}
 					else {
 						sendNotification(ShutdownCommand.SHUTDOWN);
					}
 					break;
					
 				case "options.downloadContent":
					var cd:ContentProxy = facade.retrieveProxy(ContentProxy.NAME) as ContentProxy;
					if (cd == null) {
						cd = new ContentProxy();
						facade.registerProxy(cd);
					}
					cd.downloadCatalog(true);
 					break;
 				
 				case "options.downloadAcrobat":
 					navigateToURL(new URLRequest("http://get.adobe.com/reader/"));
 					break;
					
				case "edit.myInfo":
					var win:UserInfoEditorForm = UserInfoEditorForm.Show();
					if (!facade.hasMediator(UserInfoEditorFormMediator.NAME)) {
						facade.registerMediator(new UserInfoEditorFormMediator(win));
					}
					win.move(app.nativeWindow.x + (app.nativeWindow.width - win.width) *0.5, app.nativeWindow.y + (app.nativeWindow.height - win.height) *0.5);
					break;
				
				case "edit.favorites":
					FavoritesWindow.Show();
					break;
					
				case "edit.resetColumns":
					sendNotification(MessageConstants.RESET_COLUMNS);
					break;
					
				case "help.showUserGuide":
					var file:File = File.applicationDirectory.resolvePath("assets/SimpleSelect_User_Guide_v2.pdf");
					file.copyTo(File.applicationStorageDirectory.resolvePath("SimpleSelect_User_Guide_v2.pdf"), true);
					file = File.applicationStorageDirectory.resolvePath("SimpleSelect_User_Guide_v2.pdf");
					try {
						file.openWithDefaultApplication();
					}
					catch (error:Error) {
						LogUtils.Error(error);
						Alert.show("An error occurred while attempting to open the User Guide.\nPlease verify that the Adobe Acrobate Reader is installed and you have the latest Simple Select.", "Error");
					}
					break;
									
				case "help.showEula":
					EULAWindow.Show();
					break;		
					
				case "help.showHelp":
					HelpWindow.Show();
					break;
					
				case "help.about":
					SplashScreen.Show(true, false);
					break;
					
				case "help.contactUs":
					Alert.show("For Ordering or Order Status, contact customer service 888-593-7876\n\nFor Technical Support of SimpleSelect or HVAC products, contact 800-877-7545 (prompt 2 > 1 > 2)", "Contact");
					break;
 			}
 		}
		
 		
		private function createCurrentProject():void {
			throw new Error("yyyyyyyyyeeeeeyyyyyy");
			createNewProject();
			// Sync Project with Schedules
			var vsm:ValveScheduleMediator 	= facade.retrieveMediator(ValveScheduleMediator.NAME) as ValveScheduleMediator;
			currentProject.valveSchedule 	= vsm.getRawDataProvider();
			var dsm:DamperScheduleMediator 	= facade.retrieveMediator(DamperScheduleMediator.NAME) as DamperScheduleMediator;
			currentProject.damperSchedule 	= dsm.getRawDataProvider();
		}
		
		
		private function createNewProject():void {
			currentProject = new VSSTProject();
		}
		
		
		private function newProjectSaveConfirmHandler(event:CloseEvent):void {
 			switch (event.detail) {
 				case Alert.YES:
 					newProjectPending = true;
		 			sendNotification(PersistenceCommand.SAVE_PROJECT, currentProject);
 					break;
 				
 				case Alert.NO:
					projectDirty = false;
 					sendNotification(PersistenceCommand.NEW_PROJECT);
 					break;
 				
 				case Alert.CANCEL:
 					// Do Nothing
 					break;
 			}
 		}
		
		
		private function openProjectOverride(e:CloseEvent):void {
			switch(e.detail) {
				case Alert.YES:
					openProjectAfterSavingThisOne	= true;
					sendNotification(PersistenceCommand.SAVE_PROJECT, currentProject);
					break;
				
				case Alert.NO:
					projectDirty	= false;
					sendNotification(PersistenceCommand.LOAD_PROJECT);
					break;
			}
		}
 		
		
 		private function chooseSaveConfirmHandler(event:CloseEvent):void {
 			switch (event.detail) {
 				case Alert.YES:
 					shutdownPending	= true;
		 			sendNotification(PersistenceCommand.SAVE_PROJECT, currentProject);
 					break;
 				
 				case Alert.NO:
					projectDirty	= false;
 					// Initiate Shutdown Sequence
 					sendNotification(ShutdownCommand.SHUTDOWN);
 					break;
 				
 				case Alert.CANCEL:
 					// Do Nothing
 					break;
 			}
 		}
		
		
		private function setCurrentProductType(newProductType:uint):void {
			var idx:int = 0;
			for (var i:int = 0; i < app.productsBar.dataProvider.length; i++) {
				if (app.productsBar.dataProvider.getItemAt(i).type == newProductType) {
					idx = i;
					break;
				}
			}
			app.productsBar.selectedIndex = idx;
			SwizzardGlobals.CURRENT_PRODUCT_TYPE = newProductType;
			
			// hide/show panels for current product
			var showValves:Boolean = (newProductType == ProductType.VALVE);
			app.valveParamsSlider.visible 	= showValves;
			app.valveGridsBox.visible 		= showValves;
			app.valveScheduleBox.visible 	= showValves;
			
			var showDamper:Boolean = (newProductType == ProductType.DAMPER);
			app.damperParamsSlider.visible 	= showDamper;
			app.damperGridsBox.visible 		= showDamper;
			app.damperScheduleBox.visible 	= showDamper;
			
			var showPneumatic:Boolean = (newProductType == ProductType.PNEUMATIC);
			app.pneumaticParamsSlider.visible 	= showPneumatic;
			app.pneumaticGridsBox.visible 		= showPneumatic;
			app.pneumaticScheduleBox.visible 	= showPneumatic;
		}
		
				
		// --------------- Mediator FUNCTIONS to handle the NOTIFICATIONS --------------- //
		
		override public function listNotificationInterests():Array {
			return [MessageConstants.CUSTOMER_INFO_CHANGED, 
				MessageConstants.SCHEDULE_CHANGED_LEN,
				PersistenceCommand.NEW_PROJECT, 
				PersistenceProxy.PROJECT_LOADED, 
				PersistenceProxy.PROJECT_SAVED, 
				ApplicationFacade.RESET_QUERY, 
				MessageConstants.PROJECT_DIRTY,
				ApplicationFacade.PRODUCT_CHANGED]; 
		}
		
		
		override public function handleNotification(notification:INotification):void {
			switch (notification.getName()) {
				case PersistenceCommand.NEW_PROJECT:
				{
					// create new project
					createNewProject();
					
					// reset query models
					valveQueryModel.reset();
					damperQueryModel.reset();
					accessoryQueryModel.reset();
					pneumaticQueryModel.reset();
					
					projectDirty			= false;
					app.saveButton.enabled	= false;
					app.menubar.disable("file.save");
					
					// send notification with the new project
					sendNotification(PersistenceProxy.PROJECT_CREATED, currentProject);
					break;
				}
					
				case MessageConstants.PROJECT_DIRTY:
				{
					projectDirty			= true;
					app.saveButton.enabled	= true;
					app.menubar.enable("file.save");
					break;
				}
					
				case PersistenceProxy.PROJECT_LOADED:
				{
					var project:VSSTProject	= notification.getBody() as VSSTProject;
					currentProject = project;
					
					projectDirty			= false;
					app.saveButton.enabled	= false;
					app.menubar.disable("file.save");
					
					var projectHasItems:Boolean = (project.valveSchedule && project.valveSchedule.length > 0);
					app.exportExcelButton.enabled	= projectHasItems;
					app.exportPDFButton.enabled		= projectHasItems;
					app.exportWebshopButton.enabled	= projectHasItems;
					break;
				}
					
				case MessageConstants.SCHEDULE_CHANGED_LEN:
				{
					var len:int = notification.getBody() as int;
					app.exportExcelButton.enabled	= (len > 0);
					app.exportPDFButton.enabled		= (len > 0);
					app.exportWebshopButton.enabled	= (len > 0);
					break;
				}
					
				case PersistenceProxy.PROJECT_SAVED:
				{
					projectDirty			= false;
					app.saveButton.enabled	= false;
					app.menubar.disable("file.save");
					
					if (shutdownPending) {
						// Initiate Shutdown Sequence
	 					sendNotification(ShutdownCommand.SHUTDOWN);
	 				}
	 				else if (newProjectPending) {
	 					// Create New Project
	 					sendNotification(PersistenceCommand.NEW_PROJECT);
	 				}
					else if(openProjectAfterSavingThisOne) {
						openProjectAfterSavingThisOne = false;
						sendNotification(PersistenceCommand.LOAD_PROJECT);
					}
					break;
				}
					
				case ApplicationFacade.RESET_QUERY:
				{
					switch (SwizzardGlobals.CURRENT_PRODUCT_TYPE) {
						case ProductType.VALVE:
							app.valveParamsSlider.gotoToFirst();
							valveQueryModel.reset();
							break;
						
						case ProductType.DAMPER:
							app.damperParamsSlider.gotoToFirst();
							damperQueryModel.reset();
							break;
						
						case ProductType.PNEUMATIC:
							app.pneumaticParamsSlider.gotoToFirst();
							pneumaticQueryModel.reset();
							break;
					}
					break;
				}
					
				case MessageConstants.CUSTOMER_INFO_CHANGED:
				{
					currentProject	= notification.getBody() as VSSTProject;
					break;
				}
					
				case ApplicationFacade.PRODUCT_CHANGED:
				{
					var newProductType:uint = notification.getBody() as uint;
					setCurrentProductType(newProductType);
					break;
				}
			}
		}
	}
}