package Swizzard.Data.AsynchronousQuery.Commands.Dampers
{
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;
	
	
	public class FireDamperQueryCommand extends BaseDamperQueryCommand
	{
		public function FireDamperQueryCommand() {
			super();
			damperType = DamperType.FIRE_AND_SMOKE;
		}
	}
}