package lib.integration.common.interfaces;

public interface IEventListener
{
  /** name for the event receiver */
  String name();
  
  /** Type of event it listens for. 'ALL' is a default value for all event types. */
  IEventType eventType();
  
  /** callback to handle events */
  void eventReceived(IEvent event);
}
