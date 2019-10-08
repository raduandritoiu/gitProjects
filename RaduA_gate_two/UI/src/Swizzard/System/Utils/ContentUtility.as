package Swizzard.System.Utils
{
	import Swizzard.System.ContentDownload.Proxy.ContentProxy;
	
	import flash.filesystem.File;
	
	
	public class ContentUtility
	{
		public function ContentUtility() {}
		
		
		public static function getContent(url:String):String {
			var fileName:String	= url.substr(url.lastIndexOf("/") + 1);
			var storageFolder:File	= ContentProxy.contentStorageFolder;
			var file:File = storageFolder.resolvePath(fileName);
			if (file.exists) {
				return file.url;
			}
			else {
				return url;
			}
		}
	}
}