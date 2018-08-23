package lib.integration.common.interfaces;

public interface ICall
{
  /** name for the call */
  String name();
  
  /** an integer to use as session Id to pair the response with this call */
  int sessionId();
}
