package Swizzard.System.Commands.Init
{
	import Swizzard.System.Utils.DamperFormUtil;
	import Swizzard.System.Utils.PneumaticFormUtil;
	import Swizzard.System.Utils.ValveFormUtil;
	import Swizzard.UI.Components.DataGridClasses.GridColumnsUtil;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;

	public class ConstructEnumerationDataCommand extends SimpleCommand
	{
		public function ConstructEnumerationDataCommand()
		{
			super();
		}
		
		
		override public function execute(notification:INotification):void
		{
			GridColumnsUtil.generateColumnsInfo();
			ValveFormUtil.generateValveTypeInfo();
			DamperFormUtil.generateDamperTypeInfo();
			PneumaticFormUtil.generatePneumaticSelectionInfo();
			PneumaticFormUtil.generatePneumaticSelectionTable();
		}
	}
}