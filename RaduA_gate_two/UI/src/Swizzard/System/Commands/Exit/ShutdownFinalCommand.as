package Swizzard.System.Commands.Exit
{
	import flash.desktop.NativeApplication;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	
	[ExcludeClass]
	public class ShutdownFinalCommand extends SimpleCommand
	{
		public function ShutdownFinalCommand()
		{
			super();
		}
		
		override public function execute(notification:INotification):void
		{
			NativeApplication.nativeApplication.exit();
		}
	}
}