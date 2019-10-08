package Swizzard.System.Persistence.IO
{
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	
	import Swizzard.System.Models.CompanyInformation;
	import Swizzard.System.Models.VSSTProject;
	import Swizzard.System.Models.Backwards.BackwardsUtil;
	import Swizzard.System.Utils.VersionUtil;
	
	import utils.LogUtils;
	
	
	public class ProjectReader
	{
		public function ProjectReader()
		{}
		
		
		public function readProjectFile(file:File):VSSTProject {
			var stream:FileStream = new FileStream();
			var project:VSSTProject;
			
			try {
				stream.open(file, FileMode.READ);
				
				// get the version this file was saved with
				var savedVersion:String;
				var hasVersioning:String = stream.readUTFBytes(3);
				if (hasVersioning != VersionUtil.USE_VERS) {
					// if it doesn't have version info means it was pre 2.0
					// also reset the reading position in the stream
					savedVersion = "1.999.999";
					stream.position = 0;
				}
				else {
					savedVersion = stream.readUTF();
				}
				
				// set loading version
				VersionUtil.LOADING_VERSION = savedVersion;

				// if this is a 1.*.* version file
				if (VersionUtil.Compare(savedVersion, "2.0.0") == 1) {
					var tmpObject:* = stream.readObject();
					
					// now get the new project file
					var tmpProject:VSSTProject 	= new VSSTProject();
					tmpProject.createdBy 		= tmpObject.createdBy;
					tmpProject.projectName 		= tmpObject.projectName;
					tmpProject.projectNumber 	= tmpObject.projectNumber;
					tmpProject.customerInformation 	= BackwardsUtil.BackwardsCompanyInformations(tmpObject.customerInformation);
					tmpProject.valveSchedule 		= BackwardsUtil.BackwardsSchedule(tmpObject.schedule);
					
					project = tmpProject;
				}
				else {
					project = stream.readObject() as VSSTProject;
				}
				
				// reset loading version
				VersionUtil.LOADING_VERSION = VersionUtil.CURRENT_VERSION();
			}
			catch (err:Error) {
				// Error Reading Object
				LogUtils.Debug("Error Reading Object");
			}
			finally {
				stream.close();
			}
			
			return project;
		}
		
		
		public static function getInstance():ProjectReader {
			return new ProjectReader();
		}
	}
}