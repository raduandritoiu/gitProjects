package Swizzard.Data.Models
{
	[RemoteClass]
	public class SiemensDocument
	{
		private var _name:String;
		private var _partNumber:String;		// Part Number :
		private var _description:String;
		private var _documentType:String;	// Document Type :
		private var _location:String;		// Location :
		private var _isImage:Boolean;
		
		
		public function SiemensDocument()
		{}
		
		
		public function set name(value:String):void {
			_name	= value;
		}
		
		public function get name():String {
			return _name;
		}
		
		
		public function set partNumber(value:String):void {
			_partNumber = value;
		}
		
		public function get partNumber():String {
			return _partNumber;
		}
		
		
		public function set description(value:String):void {
			_description = value;
		}
		
		public function get description():String {
			return _description;
		}
		
		
		public function set documentType(value:String):void {
			_documentType = value;
		}

		public function get documentType():String {
			return _documentType;
		}
		
		
		public function set location(value:String):void {
			_location = value;
		}
		
		public function get location():String {
			return _location;
		}
		
		
		public function set isImage(value:Boolean):void {
			_isImage = value;
		}
		
		public function get isImage():Boolean {
			return _isImage;
		}
	}
}