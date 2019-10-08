package Swizzard.Data.AsynchronousQuery.Commands.Dampers
{
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;
	
	
	public class NonSpringDamperQueryCommand extends BaseDamperQueryCommand
	{
		public function NonSpringDamperQueryCommand() {
			super();
			damperType = DamperType.NON_SPRING_RETURN;
		}
	}
}