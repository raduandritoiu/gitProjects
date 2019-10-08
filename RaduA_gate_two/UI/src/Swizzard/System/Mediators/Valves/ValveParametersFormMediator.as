package Swizzard.System.Mediators.Valves
{
	import Swizzard.UI.Forms.ValveParametersForm;
	
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	
	public class ValveParametersFormMediator extends Mediator
	{
		public static const NAME:String	= "ValveParametersFormMediatorName";
		
		private static var _instance:ValveParametersFormMediator;
		
		
		public static function getInstance():ValveParametersFormMediator {
			return _instance;
		}

		
		public function ValveParametersFormMediator(viewComponent:Object = null) {
			super(NAME, viewComponent);
			_instance = this;
		}
		
		
		private function get view():ValveParametersForm {
			return viewComponent as ValveParametersForm;
		}
		
		
		public function get pressureDrop():Number {
			if (view.pressureDropInput.text) { 
				return parseFloat(view.pressureDropInput.text);
			}
			return parseFloat(view.pressureDropInput.defaultText);
		}
		
		
		public function get pressureDrop_B():Number {
			if (!view.water_6way && !view.glycol_6way)
				return 0;
			if (view.pressureDropInput_B.text) {
				return parseFloat(view.pressureDropInput_B.text);
			}
			return parseFloat(view.pressureDropInput_B.defaultText);
		}
		
		
		public function get selectedMedium():uint {
			if (view.mediumSelector.selectedItem != null) {
				return view.mediumSelector.selectedItem.value;
			}
			return 0;
		}
		
		
		public function get steamQuantity():Number {
			return parseFloat(view.steamQuantityInput.text);
		}
		
		
		public function get lastSpecificVolume():Number {
			return view.lastSpecificVolume;
		}
		
		
		public function get percentGlycol():Number {
			return view.percentGlycolSelector.selectedItem as Number;
		}
		
		
		public function get requiredFlow():Number {
			return parseFloat(view.requiredFlowInput.text);
		}
		
		
		public function get requiredFlow_B():Number {
			return parseFloat(view.requiredFlowInput_B.text);
		}
		
		
		public function get isWater_6way():Boolean {
			return view.water_6way;
		}
		
		
		public function get isGlycol_6way():Boolean {
			return view.glycol_6way;
		}
	}
}