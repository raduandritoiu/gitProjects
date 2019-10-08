package Swizzard.System.InfosWindows.mediators
{
	import flash.events.MouseEvent;
	
	import Swizzard.System.InfosWindows.UI.UserInfoEditorForm;
	import Swizzard.System.InfosWindows.commands.CompanyInfoCommand;
	import Swizzard.System.Models.CompanyInformation;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	

	public class UserInfoEditorFormMediator extends Mediator
	{
		public static const NAME:String	= "UserInfoEditorFormMediator";
		
		private var userInfo:CompanyInformation;
		
		
		public function UserInfoEditorFormMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
		}
		
		
		private function get editor():UserInfoEditorForm
		{
			return viewComponent as UserInfoEditorForm;
		}
		
		
		override public function onRegister():void
		{
			editor.saveButton.addEventListener(MouseEvent.CLICK, saveButtonClickHandler, false, 0, true);
			editor.closeButton.addEventListener(MouseEvent.CLICK, closeButtonClickHandler, false, 0, true);
			sendNotification(CompanyInfoCommand.GET_COMPANY_INFO);
		}
		
		
		private function populateForm():void
		{
			editor.nameTextInput.text		= userInfo.contactName;
			editor.address1TextInput.text	= userInfo.addressLineOne;
			editor.address2TextInput.text	= userInfo.addressLineTwo;
			editor.cityTextInput.text		= userInfo.cityTown;
			editor.stateTextInput.text		= userInfo.stateProvince;
			editor.zipTextInput.text		= userInfo.postalCode;
			editor.phoneTextInput.text		= userInfo.officePhoneNumber;
			editor.jobNameTextInput.text	= userInfo.jobOrProjectName;
			editor.jobNumberTextInput.text	= userInfo.jobOrProjectNumber;
		}
		
		
		private function saveButtonClickHandler(event:MouseEvent):void
		{
			userInfo.type				= CompanyInformation.USER_INFO;
			userInfo.contactName		= editor.nameTextInput.text;
			userInfo.addressLineOne		= editor.address1TextInput.text;
			userInfo.addressLineTwo		= editor.address2TextInput.text;
			userInfo.cityTown			= editor.cityTextInput.text;
			userInfo.stateProvince		= editor.stateTextInput.text;
			userInfo.postalCode			= editor.zipTextInput.text;
			userInfo.officePhoneNumber	= editor.phoneTextInput.text;
			
			userInfo.jobOrProjectName	= editor.jobNameTextInput.text;
			userInfo.jobOrProjectNumber	= editor.jobNumberTextInput.text;
			
			sendNotification(CompanyInfoCommand.STORE_COMPANY_INFO, userInfo);
			
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
		
		
		override public function listNotificationInterests():Array
		{
			return [CompanyInfoCommand.COMPANY_INFO_RECEIVED];
		}
		
		
		override public function handleNotification(notification:INotification):void
		{
			switch (notification.getName())
			{
				case CompanyInfoCommand.COMPANY_INFO_RECEIVED:
				{
					userInfo = notification.getBody() as CompanyInformation;
					
					if (!userInfo)
						userInfo = new CompanyInformation();
						
					populateForm();
				}
			}	
		}
	}
}