package Swizzard.System.Mediators.Pneumatics
{
	import Swizzard.UI.Forms.PneumaticParametersForm;
	
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	
	public class PneumaticParametersFormMediator extends Mediator
	{
		public static const NAME:String	= "PneumaticParametersFormMediatorName_v2fe";
		
		
		public function PneumaticParametersFormMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
		}
		
		
		public function get view():PneumaticParametersForm {
			return viewComponent as PneumaticParametersForm;
		}
	}
}