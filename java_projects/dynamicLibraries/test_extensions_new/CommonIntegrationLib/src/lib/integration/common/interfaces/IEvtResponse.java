package lib.integration.common.interfaces;

public interface IEvtResponse
{
  /** name for the response */
  String name();
  
  /** an integer to use as session Id to pair this response with the event */
  int sessionId();
}
