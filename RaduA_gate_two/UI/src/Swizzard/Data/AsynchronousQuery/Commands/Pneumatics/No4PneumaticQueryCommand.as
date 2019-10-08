package Swizzard.Data.AsynchronousQuery.Commands.Pneumatics
{
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticType;
	
	
	/**
	 * DEPRECATED. NOT USED ANYMORE.
	 */
	public class No4PneumaticQueryCommand extends BasePneumaticQueryCommand
	{
		public function No4PneumaticQueryCommand() {
			super();
			pneumaticType = PneumaticType.NO_4;
		}
		
		override protected function shouldQuery():Boolean {
			return pneumaticType == PneumaticType.NO_4;
		}
	}
}