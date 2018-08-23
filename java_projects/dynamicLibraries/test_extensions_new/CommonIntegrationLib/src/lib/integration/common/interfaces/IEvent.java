package lib.integration.common.interfaces;

public interface IEvent
{
  /** name for the event */
  String name();
  
  /** event type */
  IEventType type();
  
  /** an integer to use as session Id to pair the response with this event;
   * even though the event has a reference to the response */
  int sessionId();
  
  /** A key object used to wait/signal when response is ready */
  Object responseKey();
  
  /** The response for an event; if it is that kind of event */
  IEvtResponse response();
}
