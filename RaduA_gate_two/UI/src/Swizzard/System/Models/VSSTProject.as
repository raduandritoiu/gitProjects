package Swizzard.System.Models
{
	import mx.collections.ArrayCollection;
	
	
	[RemoteClass]
	public class VSSTProject
	{
		private var _projectName:String		= "";				// Project Name
		private var _projectNumber:String	= "";				// Project Number
		private var _createdBy:String		= "";				// Created By
		
		private var _customerInformation:CompanyInformation;	// Customer Information
		private var _valveSchedule:ArrayCollection;				// Valve Schedule
		private var _damperSchedule:ArrayCollection;			// Damper Schedule
		private var _pneumaticSchedule:ArrayCollection;			// Pneumatic Schedule
		
		
		public function VSSTProject() {
			_customerInformation = new CompanyInformation();
			_valveSchedule = new ArrayCollection();
			_damperSchedule = new ArrayCollection();
			_pneumaticSchedule = new ArrayCollection();
		}
		
		
		public function set projectName(value:String):void {
			_projectName = value;
		}

		public function get projectName():String {
			return _projectName;
		}
		
		
		public function set projectNumber(value:String):void {
			_projectNumber = value;
		}
		
		public function get projectNumber():String {
			return _projectNumber;
		}

		
		public function set createdBy(value:String):void {
			_createdBy = value;
		}

		public function get createdBy():String {
			return _createdBy;
		}
		
		
		public function set customerInformation(value:CompanyInformation):void {
			_customerInformation = value;
		}

		public function get customerInformation():CompanyInformation {
			return _customerInformation;
		}

		
		public function set valveSchedule(value:ArrayCollection):void {
			_valveSchedule = value;
		}

		public function get valveSchedule():ArrayCollection {
			return _valveSchedule;
		}
		
		
		public function set damperSchedule(value:ArrayCollection):void {
			_damperSchedule = value;
		}
		
		public function get damperSchedule():ArrayCollection {
			return _damperSchedule;
		}
		
		
		public function set pneumaticSchedule(value:ArrayCollection):void {
			_pneumaticSchedule = value;
		}
		
		public function get pneumaticSchedule():ArrayCollection {
			return _pneumaticSchedule;
		}
	}
}