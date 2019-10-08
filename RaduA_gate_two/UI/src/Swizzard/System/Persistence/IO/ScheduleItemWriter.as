package Swizzard.System.Persistence.IO
{
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	
	import mx.controls.Alert;
	
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.System.Utils.VersionUtil;
	
	import utils.LogUtils;

	public class ScheduleItemWriter
	{
		public function ScheduleItemWriter()
		{}
		
		
		// returns the file it wrote intoi
		public static function writeScheduleItem(scheduleItem:IScheduleItem, favoritesFolder:File):File {
			var fileName:String 		= scheduleItem.product.partNumber;
			fileName 					= fileName.replace("/", "_");
			fileName 					= fileName + "." + scheduleItem.fileExtension;
			
			LogUtils.Schedule(" ====== write Schedule Item");
			
			var favoriteFile:File		= favoritesFolder.resolvePath(fileName);
			var stream:FileStream		= new FileStream();
			var writeSuccess:Boolean	= false;
			
			try {
				stream.open(favoriteFile, FileMode.WRITE);
				stream.writeUTFBytes(VersionUtil.USE_VERS);
				stream.writeUTF(VersionUtil.CURRENT_VERSION());
				stream.writeObject(scheduleItem);
				writeSuccess = true;
			}
			catch (err:Error) {
				// Error Writing Item
				LogUtils.Error("Error Writing Favorite: " + err.message);
				Alert.show("Error writing favorites file.");
				
				if (favoriteFile.exists) {
					stream.close();
					try {
						favoriteFile.deleteFile();
					}
					catch (err:Error) {
						// Error Deleting File
						LogUtils.Error("Error Deleting File");
					}
				}
			}
			finally {
				stream.close();
			}
			
			// if success in writing return the wrote file - we need it for native drag-n-drop
			if (writeSuccess) {
				return favoriteFile;
			}
			// este return null
			else {
				return null;
			}
		}
	}
}