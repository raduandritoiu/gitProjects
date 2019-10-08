package Swizzard.System.Persistence.IO
{
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	
	import mx.controls.Alert;
	
	import Swizzard.System.Models.VSSTProject;
	import Swizzard.System.Utils.VersionUtil;
	
	
	public class ProjectWriter
	{
		public function ProjectWriter()
		{}
		
		
		public function writeProject(project:VSSTProject, targetFile:File):void {
			var stream:FileStream = new FileStream();
			
			try {
				stream.open(targetFile, FileMode.WRITE);
				stream.writeUTFBytes(VersionUtil.USE_VERS);
				stream.writeUTF(VersionUtil.CURRENT_VERSION());
				stream.writeObject(project);
			}
			catch (err:Error) {
				Alert.show("Error saving schedule.", "I/O Error");
			}
			finally {
				stream.close();
			}
		}
		
		
		public static function getInstance():ProjectWriter {
			return new ProjectWriter();
		}
	}
}