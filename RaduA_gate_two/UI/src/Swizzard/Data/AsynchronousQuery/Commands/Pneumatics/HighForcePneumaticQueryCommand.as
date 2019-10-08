package Swizzard.Data.AsynchronousQuery.Commands.Pneumatics
{
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticType;
	
	
	/**
	 * DEPRECATED. NOT USED ANYMORE.
	 */
	public class HighForcePneumaticQueryCommand extends BasePneumaticQueryCommand
	{
		public function HighForcePneumaticQueryCommand() {
			super();
			pneumaticType = PneumaticType.HIGH_FORCE;
		}
		
		override protected function shouldQuery():Boolean {
			return pneumaticType == PneumaticType.HIGH_FORCE;
		}
	}
}