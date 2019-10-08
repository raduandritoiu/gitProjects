package Swizzard.Data.AsynchronousQuery.Commands.Pneumatics
{
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticType;
	
	
	/**
	 * DEPRECATED. NOT USED ANYMORE.
	 */
	public class No3PneumaticQueryCommand extends BasePneumaticQueryCommand
	{
		public function No3PneumaticQueryCommand() {
			super();
			pneumaticType = PneumaticType.NO_3
		}
		
		override protected function shouldQuery():Boolean {
			return pneumaticType == PneumaticType.NO_3;
		}
	}
}