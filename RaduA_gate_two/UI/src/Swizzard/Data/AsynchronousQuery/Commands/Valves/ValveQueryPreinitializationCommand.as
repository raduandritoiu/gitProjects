package Swizzard.Data.AsynchronousQuery.Commands.Valves
{
	import Swizzard.Data.AsynchronousQuery.Token.ValveQueryToken;
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;
	import Swizzard.Data.Models.Query.ValveQueryModel;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	
	public class ValveQueryPreinitializationCommand extends SimpleCommand
	{
		public function ValveQueryPreinitializationCommand()
		{
			super();
		}
		
		
		override public function execute(notification:INotification):void
		{
			// Check to see if any type will cause valve requery
			var token:ValveQueryToken	= notification.getBody() as ValveQueryToken;
			var valve:ValveQueryModel 	= token.valveQueryModel.valve;
			var willRequery:Boolean	= false;
			
			if (!token.valve)
			{
				if (valve.valveTypes.indexOf(ValveType.BALL) > -1)
					willRequery	||= BallValveQueryCommand.WillRequeryValves(token);
				
				if (!willRequery && valve.valveTypes.indexOf(ValveType.BUTTERFLY) > -1)
					willRequery	||= ButterflyValveQueryCommand.WillRequeryValves(token);
				
				if (!willRequery && valve.valveTypes.indexOf(ValveType.GLOBE) > -1)
					willRequery	||= GlobeValveQueryCommand.WillRequeryValves(token);
				
				if (!willRequery && valve.valveTypes.indexOf(ValveType.MAGNETIC) > -1)
					willRequery	||= MagneticValveQueryCommand.WillRequeryValves(token);
				
				if (!willRequery && valve.valveTypes.indexOf(ValveType.PICV) > -1)
					willRequery	||= PICValveQueryCommand.WillRequeryValves(token);
				
				if (!willRequery && valve.valveTypes.indexOf(ValveType.ZONE) > -1)
					willRequery	||= ZoneValveQueryCommand.WillRequeryValves(token);
			}
			
			token.requeryAllValves	= willRequery;
		}
	}
}