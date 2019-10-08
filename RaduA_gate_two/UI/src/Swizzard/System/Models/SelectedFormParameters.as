package Swizzard.System.Models
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	
	import Swizzard.System.Utils.VersionUtil;

	[RemoteClass]
	public class SelectedFormParameters
	{
		private var _selectedMedium:uint;			// Selected Medium
		private var _pressureDrop:Number;			// Pressure Drop
		private var _pressureDrop_B:Number;			// Pressure Drop
		private var _requiredFlow:Number;			// Required Flow
		private var _steamQuantity:Number;			// Steam Quantity
		private var _steamSpecificVolume:Number;	// Steam Specific Volume
		private var _percentGlycol:uint;			// Percent Glycol
		
		
		public function SelectedFormParameters()
		{}
		
		
		public function set selectedMedium(value:uint):void {
			_selectedMedium	= value;
		}
		public function get selectedMedium():uint {
			return _selectedMedium;
		}
		
		
		public function set pressureDrop(value:Number):void { 
			_pressureDrop = value;
		}
		public function get pressureDrop():Number {
			return _pressureDrop;
		}

		
		public function set pressureDrop_B(value:Number):void { 
			_pressureDrop_B = value;
		}
		public function get pressureDrop_B():Number {
			return _pressureDrop_B;
		}
		
		
		public function set requiredFlow(value:Number):void { 
			_requiredFlow = value;
		}
		public function get requiredFlow():Number {
			return _requiredFlow;
		}

		
		public function set steamQuantity(value:Number):void { 
			_steamQuantity = value;
		}
		public function get steamQuantity():Number {
			return _steamQuantity;
		}


		public function set steamSpecificVolume(value:Number):void { 
			_steamSpecificVolume = value;
		}
		public function get steamSpecificVolume():Number {
			return _steamSpecificVolume;
		}
		
		
		public function set percentGlycol(value:uint):void { 
			_percentGlycol = value;
		}
		public function get percentGlycol():uint { 
			return _percentGlycol;
		}
		
		
		// IExternalizable IMPLEMENTATION

		public function writeExternal(output:IDataOutput):void {
			output.writeUnsignedInt(_selectedMedium);
			output.writeDouble(_pressureDrop);
			output.writeDouble(_requiredFlow);
			output.writeDouble(_steamQuantity);
			output.writeDouble(_steamSpecificVolume);
			output.writeUnsignedInt(_percentGlycol);
			output.writeDouble(_pressureDrop_B);
		}
		
		public function readExternal(input:IDataInput):void {
			_selectedMedium 	= input.readUnsignedInt();
			_pressureDrop 		= input.readDouble();
			_requiredFlow 		= input.readDouble();
			_steamQuantity 		= input.readDouble();
			_steamSpecificVolume = input.readDouble();
			_percentGlycol 		= input.readUnsignedInt();
			
			// do backwards compatibility
			if (VersionUtil.Compare(VersionUtil.LOADING_VERSION, "2.1.8") == 1) {
				_pressureDrop_B = 0;
			}
			else {
				_pressureDrop_B = input.readDouble();
			}
		}
	}
}