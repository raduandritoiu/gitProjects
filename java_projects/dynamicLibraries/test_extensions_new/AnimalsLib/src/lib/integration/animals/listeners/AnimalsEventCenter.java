package lib.integration.animals.listeners;

import lib.integration.common.data.BasicEventTypes;
import lib.integration.common.interfaces.IEvent;
import lib.integration.common.interfaces.IEventListener;
import lib.integration.common.interfaces.IEventType;

public class AnimalsEventCenter implements IEventListener
{
  private final String _name;
  
  public AnimalsEventCenter()
  {
    _name = "AnimalsEventCenter";
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
    System.out.println("AnimalsEventCenter has received the following event " + event.name());
  }
}
