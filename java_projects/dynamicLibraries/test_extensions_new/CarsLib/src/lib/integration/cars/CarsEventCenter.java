package lib.integration.cars;

import lib.integration.common.data.BasicEventTypes;
import lib.integration.common.interfaces.IEvent;
import lib.integration.common.interfaces.IEventListener;
import lib.integration.common.interfaces.IEventType;

public class CarsEventCenter implements IEventListener
{
  private final String _name;
  
  public CarsEventCenter()
  {
    _name = "CarsEventCenter";
  }
  
  @Override
  public String name()
  {
    return _name;
  }
  
  @Override
  public IEventType eventType()
  {
    return BasicEventTypes.ALL;
  }

  @Override
  public void eventReceived(IEvent event)
  {
    System.out.println("CarsEventCenter has received the following event " + event.name());
  }
}
