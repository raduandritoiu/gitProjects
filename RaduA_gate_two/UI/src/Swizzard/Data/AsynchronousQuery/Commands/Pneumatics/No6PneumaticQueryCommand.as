package Swizzard.Data.AsynchronousQuery.Commands.Pneumatics
{
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticType;
	
	
	/**
	 * DEPRECATED. NOT USED ANYMORE.
	 */
	public class No6PneumaticQueryCommand extends BasePneumaticQueryCommand
	{
		public function No6PneumaticQueryCommand() {
			super();
			pneumaticType = PneumaticType.NO_6;
		}
		
		override protected function shouldQuery():Boolean {
			return pneumaticType == PneumaticType.NO_6;
		}
	}
}