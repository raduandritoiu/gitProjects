package Swizzard.System.Models
{
	import com.adobe.utils.DateUtil;
	
	public class DatabaseUpdateInfo
	{
		private var _version:uint;			// Version
		private var _dataFileUri:String;	// Data File Uri 
		private var _contentDateModified:Date;
		
		
		public function DatabaseUpdateInfo()
		{}
		

		public function get version():uint {
			return this._version;
		}


		public function get dataFileUri():String {
			return this._dataFileUri;
		}
		
		
		public function get contentDateModified():Date {
			return this._contentDateModified;
		}

		
		public function FillFromXml(xml:XML):void
		{
			var databaseInfo:XML	= xml.database[0];
			var contentInfo:XML		= xml.content[0];
			
			for each (var child:XML in databaseInfo.children())
			{
				switch (child.localName().toLowerCase())
				{
					case "version":
						_version	= parseInt(child.text().toString());
						break;
					
					case "datafileuri":
						_dataFileUri	= child.text().toString();
						break;
				}
			}
			
			for each (var content:XML in contentInfo.children())
			{
				switch (content.localName().toLowerCase())
				{
					case "datemodified":
						_contentDateModified = DateUtil.parseW3CDTF(content.@value.toString());
						break;
				}
			}
		}
		
		
		public static function BuildFromXml(xml:XML):DatabaseUpdateInfo
		{
			var info:DatabaseUpdateInfo	= new DatabaseUpdateInfo();
			info.FillFromXml(xml);
			return info;	
		}
	}
}