package Swizzard.Data.Models.Enumeration.Pneumatic
{
	import Swizzard.Data.Models.Query.PneumaticQueryModel;

	public class PneumaticSelection
	{
		public static const DAMPER:int			= 251;
		public static const WITH_POSITIONER:int	= 252;
		public static const HIGH_FORCE:int		= 253;
		public static const UL_LISTING:int		= 254;
		public static const ALL:int				= 255;
		public static const NONE:int			= 256;
		
		
		public static function toString(flag:int):String {
			switch (flag) {
				case DAMPER:
					return "Damper";
					
				case WITH_POSITIONER:
					return "Wth Position";
					
				case HIGH_FORCE:
					return "High Force";
					
				case UL_LISTING:
					return "UL Listing";
					
				case ALL:
					return "All";
					
				case NONE:
					return "None";
			}
			
			return "None";
		}			
		
		
		public static function AdjustSelection(pneumaticQueryModel:PneumaticQueryModel, selectedArray:Array):void {
			if (selectedArray.length > 1) {
				pneumaticQueryModel.selection = PneumaticSelection.ALL;
				return;
			}
			else if (selectedArray.length < 1) {
				pneumaticQueryModel.selection = PneumaticSelection.NONE;
				return;
			}
			// first apply the selection then set it - to force 
			// the (binding) update after the selection related changes
			// ApplySelection(pneumaticQueryModel, selectedArray[0]);
			pneumaticQueryModel.selection = selectedArray[0];
		}
		
		
		public static function ApplySelection(pneumaticQueryModel:PneumaticQueryModel, overrideSel:int = -1):void {
			var selection:int = pneumaticQueryModel.selection;
			if (overrideSel != -1) selection = overrideSel;
			
			// suppress all event to avoid unnecessary querying and updates to the UI
			pneumaticQueryModel.suppressEvents = true;
			
			if (selection == WITH_POSITIONER) {
				pneumaticQueryModel.posRelay = PneumaticPosRelay.YES;
			}
			else if (selection == UL_LISTING) {
				pneumaticQueryModel.ULCert = PneumaticULCerts.YES;
			}
			else if (selection == HIGH_FORCE) {
				// do nothing - this will be handled in the Querry Command
			}
			
			pneumaticQueryModel.suppressEvents = false;
		}
	}
}