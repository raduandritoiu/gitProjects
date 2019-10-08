package Swizzard.System.InfosWindows.mediators
{
	import flash.events.MouseEvent;
	
	import Swizzard.System.InfosWindows.UI.CustomerInfoEditorForm;
	import Swizzard.System.Models.CompanyInformation;
	import Swizzard.System.Models.VSSTProject;
	import Swizzard.System.Utils.MessageConstants;
	
	import org.puremvc.as3.patterns.mediator.Mediator;

	public class CustomerInfoEditorFormMediator extends Mediator
	{
		public static const NAME:String	= "CustomerInfoEditorFormMediator";
		
		private var project:VSSTProject;
		
		
		public function CustomerInfoEditorFormMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
		}
		
		
		private function get editor():CustomerInfoEditorForm
		{
			return viewComponent as CustomerInfoEditorForm;
		}
		
		
		public function setProject(proj:VSSTProject):void
		{
			project = proj;
		}
		
		
		override public function onRegister():void
		{
			editor.saveButton.addEventListener(MouseEvent.CLICK, saveButtonClickHandler, false, 0, true);
			editor.closeButton.addEventListener(MouseEvent.CLICK, closeButtonClickHandler, false, 0, true);
			populateForm();
		}
		
		
		private function populateForm():void
		{
			editor.nameTextInput.text		= project.customerInformation.companyName;
			editor.address1TextInput.text	= project.customerInformation.addressLineOne;
			editor.address2TextInput.text	= project.customerInformation.addressLineTwo;
			editor.cityTextInput.text		= project.customerInformation.cityTown;
			editor.stateTextInput.text		= project.customerInformation.stateProvince;
			editor.zipTextInput.text		= project.customerInformation.postalCode;
			editor.phoneTextInput.text		= project.customerInformation.officePhoneNumber;
			
			editor.projectNameTextInput.text	= project.projectName;
			editor.projectNumberTextInput.text	= project.projectNumber;
		}
		
		
		private function saveButtonClickHandler(event:MouseEvent):void
		{
			project.customerInformation.type				= CompanyInformation.CUSTOMER_INFO;
			project.customerInformation.companyName			= editor.nameTextInput.text;
			project.customerInformation.addressLineOne		= editor.address1TextInput.text;
			project.customerInformation.addressLineTwo		= editor.address2TextInput.text;
			project.customerInformation.cityTown			= editor.cityTextInput.text;
			project.customerInformation.stateProvince		= editor.stateTextInput.text;
			project.customerInformation.postalCode			= editor.zipTextInput.text;
			project.customerInformation.officePhoneNumber	= editor.phoneTextInput.text;
			project.customerInformation.jobOrProjectName	= editor.projectNameTextInput.text;
			project.customerInformation.jobOrProjectNumber	= editor.projectNumberTextInput.text;
			
			project.projectName		= editor.projectNameTextInput.text;
			project.projectNumber	= editor.projectNumberTextInput.text;
			
			sendNotification(MessageConstants.CUSTOMER_INFO_CHANGED, project);
			sendNotification(MessageConstants.PROJECT_DIRTY);
			
			dispose();
		}
		
		
		private function closeButtonClickHandler(event:MouseEvent):void
		{
			dispose();
		}
		
		
		public function dispose():void
		{
			facade.removeMediator(NAME);
			editor.dispose();
			delete this;
		}
	}
}