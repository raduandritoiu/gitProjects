package Swizzard.Data.AsynchronousQuery.Commands.Dampers
{
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;
	
	
	public class FastDamperQueryCommand extends BaseDamperQueryCommand
	{
		public function FastDamperQueryCommand() {
			super();
			damperType = DamperType.FAST_ACTING;
		}
	}
}