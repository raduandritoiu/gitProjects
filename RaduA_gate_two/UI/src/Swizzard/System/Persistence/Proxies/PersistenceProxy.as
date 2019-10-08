package Swizzard.System.Persistence.Proxies
{
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.net.FileFilter;
	
	import mx.controls.Alert;
	
	import Swizzard.System.Models.VSSTProject;
	import Swizzard.System.Persistence.IO.ProjectReader;
	import Swizzard.System.Persistence.IO.ProjectWriter;
	
	import org.puremvc.as3.patterns.proxy.Proxy;
	
	
	public class PersistenceProxy extends Proxy
	{
		public static const NAME:String				= "PersistenceProxy";
		public static const PROJECT_SAVED:String	= "projectSaved";
		public static const PROJECT_LOADED:String	= "projectLoaded";
		public static const PROJECT_CREATED:String	= "projectCreated";

		
		private var savingProjectPhase:Boolean;
		private var projectLocation:File;
		private var currentProject:VSSTProject;
		
		
		public function PersistenceProxy(data:Object=null) {
			super(NAME, data);
		}
		
		
		public function saveProject(project:VSSTProject, saveAs:Boolean=false):void {
			if (currentProject == null) {
				currentProject = project;
			}
			
			if (!projectLocation) {
				savingProjectPhase	= true;
				projectLocation	= File.desktopDirectory;
				projectLocation.addEventListener(Event.SELECT, projectLocationSelectedHandler, false, 0, true);
				projectLocation.browseForSave("Save As...");
			}
			else {
				if (saveAs) {
					savingProjectPhase	= true;
					projectLocation.browseForSave("Save As...");
				}
				else {
					if (projectLocation.isDirectory) {
						savingProjectPhase	= true;
						projectLocation.browseForSave("Save As...");
					}
					else {
						ProjectWriter.getInstance().writeProject(currentProject, projectLocation);
						sendNotification(PROJECT_SAVED);
					}
				}
			}
		}
		
		
		public function loadProject():void {
			if (!projectLocation) {
				projectLocation	= File.desktopDirectory;
				projectLocation.addEventListener(Event.SELECT, projectLocationSelectedHandler, false, 0, true);
			}
			projectLocation.browseForOpen("Open Schedule...", [new FileFilter("Siemens Valve Selection", "*.svs", "SVSS")]);
		}
		
		
		public function reset():void {
			projectLocation	= null;
			currentProject	= null;
		}	
		
		
		private function projectLocationSelectedHandler(e:Event):void {
			// saving project sequence
			if (savingProjectPhase) {
				savingProjectPhase = false;
				
				if (projectLocation.extension != "svs")
					projectLocation.nativePath += ".svs";
				
				ProjectWriter.getInstance().writeProject(currentProject, projectLocation);
				
				sendNotification(PROJECT_SAVED);
			}
			// loading project sequence
			else {
				var project:VSSTProject	= ProjectReader.getInstance().readProjectFile(projectLocation);
				if (project && project.valveSchedule) {
					currentProject = project;
					sendNotification(PROJECT_LOADED, project);
				}
				else {
					currentProject	= null;
					projectLocation	= null;
					Alert.show("Load Error: Corrupted Project File", "Error Loading Project");
				}
			}
		}
	}
}