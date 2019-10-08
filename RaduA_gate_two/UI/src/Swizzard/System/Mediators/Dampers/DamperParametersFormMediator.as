package Swizzard.System.Mediators.Dampers
{
	import Swizzard.UI.Forms.DamperParametersForm;
	
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	
	public class DamperParametersFormMediator extends Mediator
	{
		public static const NAME:String	= "DamperParametersFormMediatorName";
		
		
		public function DamperParametersFormMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
		}
		
		
		public function get view():DamperParametersForm {
			return viewComponent as DamperParametersForm;
		}
		
		
		public function get selectedTorque():Number {
			var torqueValue:Number = (view.torqueSelector.selectedItem != null) ? parseFloat(view.torqueSelector.selectedItem.toString()) : 0;
			return torqueValue;
		}
	}
}