package lib.integration.core.listeners;

import lib.integration.common.data.BasicEventTypes;
import lib.integration.common.interfaces.IEvent;
import lib.integration.common.interfaces.IEventListener;
import lib.integration.common.interfaces.IEventType;

public class CoreEventCenter implements IEventListener
{
  public final String _name;
  
  public CoreEventCenter()
  {
    _name = "I WILL RESPOND TO EVENTS";
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
    if (event.name() == "")
    {
      
    }
    else if (event.name() == "")
    {
      
    }
    System.out.println("CORE: ignore event: " + event.toString());
  }
}
