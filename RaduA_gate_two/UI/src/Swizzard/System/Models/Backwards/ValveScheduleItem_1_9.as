package Swizzard.System.Models.Backwards
{
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	
	import mx.collections.ArrayCollection;
	
	import Swizzard.Data.Models.AbstractSiemensProduct;
	import Swizzard.System.Models.SelectedFormParameters;
	import Swizzard.System.Models.ValveScheduleItem;
	
	
	[RemoteClass(alias=">Swizzard.Ordering.ScheduleItem")]
	public class ValveScheduleItem_1_9 extends ValveScheduleItem
	{
		public function ValveScheduleItem_1_9()
		{}
		
		
		override public function writeExternal(output:IDataOutput):void 
		{}
		
		
		/**
		 * @private 
		 * 
		 * Function to deserialize class from binary.
		 * @param input
		 * 
		 */		
		override public function readExternal(input:IDataInput):void {
			_product			= input.readObject() as AbstractSiemensProduct;
			_tags				= input.readUTF();
			_quantity			= input.readInt();
			_notes				= input.readUTF();
			
			_calculatedCv		= input.readDouble();
			_gpm				= input.readDouble();
			_actualPressureDrop	= input.readDouble();
			_formParams			= input.readObject() as SelectedFormParameters;
			
			_subitems			= input.readObject() as ArrayCollection;
			_priceMultiplier	= input.readDouble();
			
			for each (var item:ValveScheduleItem_1_9 in _subitems) {
				item._parent = this;
			}
		}
		
		
		public function getValveSchedule():ValveScheduleItem {
			return clone() as ValveScheduleItem;
		}
	}
}