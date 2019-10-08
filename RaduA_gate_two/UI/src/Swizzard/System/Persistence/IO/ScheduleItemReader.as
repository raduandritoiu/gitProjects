package Swizzard.System.Persistence.IO
{
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	
	import Swizzard.System.Models.Backwards.ValveScheduleItem_1_9;
	import Swizzard.System.Models.Interfaces.IScheduleItem;
	import Swizzard.System.Utils.VersionUtil;
	
	import utils.LogUtils;

	public class ScheduleItemReader
	{
		public function ScheduleItemReader()
		{}
		
		
		public static function readScheduleItem(schFile:File):IScheduleItem {
			var scheduleItem:IScheduleItem = null;
			var stream:FileStream = new FileStream();
			
			LogUtils.Schedule(" ====== read Schedule Item");
			
			try {
				stream.open(schFile, FileMode.READ);
				
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
					// read the object and do backwards compatibility
					var tmpSchedule:* = stream.readObject();
					var oldValveSchedule:ValveScheduleItem_1_9 = tmpSchedule as ValveScheduleItem_1_9;
					scheduleItem = oldValveSchedule.getValveSchedule();
				}
				else {
					// read the object normally
					scheduleItem = stream.readObject() as IScheduleItem;
				}
				
				// reset loading version
				VersionUtil.LOADING_VERSION = VersionUtil.CURRENT_VERSION();
			}
			catch (err:Error) {
				// Corrupt File
				LogUtils.Debug("Corrupt files");
			}
			finally {
				stream.close();
			}
		
			return scheduleItem;
		}
	}
}