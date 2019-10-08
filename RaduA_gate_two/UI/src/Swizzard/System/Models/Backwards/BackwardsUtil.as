package Swizzard.System.Models.Backwards
{
	import mx.collections.ArrayCollection;
	
	import Swizzard.System.Models.CompanyInformation;

	public class BackwardsUtil
	{
		public function BackwardsUtil()
		{}
		
		
		public static function BackwardsCompanyInformations(backObj:Object):CompanyInformation {
			var info:CompanyInformation = new CompanyInformation();
			if (backObj == null) {
				return info;
			}
			
			info.type			 	= backObj.type;					// Either cutomer info or User Info
			info.contactName		= backObj.contactName;			// Contact Name - used as Name in My Info
			info.companyName		= backObj.companyName;			// Company Name - used as Name in Customer Info
			
			info.addressLineOne		= backObj.addressLineOne;		// Address Line One
			info.addressLineTwo		= backObj.addressLineTwo;		// Address Line Two
			info.cityTown			= backObj.cityTown;				// City / Town
			info.stateProvince		= backObj.stateProvince;		// State / Province
			info.postalCode			= backObj.postalCode;			// Postal Code
			info.officePhoneNumber 	= backObj.jobOrProjectNumber;	// Office Phone Number
			info.jobOrProjectNumber	= backObj.jobOrProjectNumber;	// Job or Project Number
			info.jobOrProjectName	= backObj.jobOrProjectName;		// Job or Project Name
			
			return info;
		}
		
		
		public static function BackwardsSchedule(backArr:ArrayCollection):ArrayCollection {
			var newArr:ArrayCollection = new ArrayCollection();
			if (backArr == null) {
				return newArr;
			}
			for each (var obj:ValveScheduleItem_1_9 in backArr) {
				newArr.addItem(obj.getValveSchedule());
			}
			
			return newArr;
		}
	}
}