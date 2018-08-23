package lib.integration.common.interfaces;

public interface ICallResponse
{
  /** name for the call response */
  String name();
  
  /** an integer to use as session Id to pair this response with the call */
  int sessionId();
}
