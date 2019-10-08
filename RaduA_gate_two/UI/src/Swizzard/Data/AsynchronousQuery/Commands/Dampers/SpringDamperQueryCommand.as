package Swizzard.Data.AsynchronousQuery.Commands.Dampers
{
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;
	
	
	public class SpringDamperQueryCommand extends BaseDamperQueryCommand
	{
		public function SpringDamperQueryCommand() {
			super();
			damperType = DamperType.SPRING_RETURN;
		}
	}
}