package Swizzard.System.Models
{
	[RemoteClass]
	public class CompanyInformation
	{
		public static const CUSTOMER_INFO:uint	= 2;
		public static const USER_INFO:uint		= 4;
		
		
		private var _type:uint;						// Either cutomer info or User Info
		private var _contactName:String;			// Contact Name - used as Name in My Info
		private var _companyName:String;			// Company Name - used as Name in Customer Info
		
		private var _addressLineOne:String;			// Address Line One
		private var _addressLineTwo:String;			// Address Line Two
		private var _cityTown:String;				// City / Town
		private var _stateProvince:String;			// State / Province
		private var _postalCode:String;				// Postal Code
		private var _officePhoneNumber:String;		// Office Phone Number
		private var _jobOrProjectNumber:String;		// Job or Project Number
		private var _jobOrProjectName:String;		// Job or Project Name
		
		
		public function CompanyInformation()
		{}
		
		
		public function set type(value:uint):void {
			_type = value;
		}

		
		public function set contactName(value:String):void {
			_contactName = value;
		}
		
		public function get contactName():String {
			return _contactName;
		}
		
		
		public function set companyName(value:String):void {
			_companyName = value;
		}

		public function get companyName():String {
			return _companyName;
		}
		
		
		public function set addressLineOne(value:String):void {
			_addressLineOne = value;
		}

		public function get addressLineOne():String {
			return _addressLineOne;
		}

		
		public function set addressLineTwo(value:String):void {
			_addressLineTwo = value;
		}

		public function get addressLineTwo():String {
			return _addressLineTwo;
		}

		
		public function set cityTown(value:String):void {
			_cityTown = value;
		}

		public function get cityTown():String {
			return _cityTown;
		}
		
		
		public function set stateProvince(value:String):void {
			_stateProvince = value;
		}

		public function get stateProvince():String {
			return _stateProvince;
		}

		
		public function set postalCode(value:String):void {
			_postalCode = value;
		}

		public function get postalCode():String {
			return _postalCode;
		}

		
		public function set officePhoneNumber(value:String):void {
			_officePhoneNumber = value;
		}

		public function get officePhoneNumber():String {
			return _officePhoneNumber;
		}

		
		public function set jobOrProjectNumber(value:String):void {
			_jobOrProjectNumber	= value;
		}
		
		public function get jobOrProjectNumber():String {
			return _jobOrProjectNumber;
		}
		
		
		public function set jobOrProjectName(value:String):void {
			_jobOrProjectName	= value;
		}
		
		public function get jobOrProjectName():String {
			return _jobOrProjectName;
		}
		
		
		public function toString():String
		{
			var result:String	= new String();
			
			if (_type == CUSTOMER_INFO && companyName) {
				result += companyName;
			}
			else if(contactName) {
				result += contactName;
			}
			
			if (_type == CUSTOMER_INFO) {
				result	+= (jobOrProjectNumber == null || jobOrProjectNumber.length < 1) ? "" : "\r\nProject# " +jobOrProjectNumber;
				result	+= (jobOrProjectName == null || jobOrProjectName.length < 1) ? "" : "\r\nProject: " +jobOrProjectName;
			}
			
			if (addressLineOne) {
				result	+= (addressLineOne == null || addressLineOne.length < 1) ? "" : "\r\n" + addressLineOne;
				if (addressLineTwo) {
					result += (addressLineTwo == null || addressLineTwo.length < 1) ? "" : "\r\n" + addressLineTwo;
				}
			}
			
			if (cityTown) {
				result += "\r\n" + cityTown;
			}
			
			if (stateProvince) {
				result += ", " + stateProvince;
			}
			
			if (postalCode) {
				result += " " + postalCode;
			}
				
			if (officePhoneNumber) {
				result += "\r\n" + officePhoneNumber;
			}
			
			if (_type == USER_INFO) {
				result	+= (jobOrProjectNumber == null || jobOrProjectNumber.length < 1) ? "" : "\r\nJob# " +jobOrProjectNumber;
				result	+= (jobOrProjectName == null || jobOrProjectName.length < 1) ? "" : "\r\nJob: " +jobOrProjectName;
			}
			
			return result;
		}
	}
}