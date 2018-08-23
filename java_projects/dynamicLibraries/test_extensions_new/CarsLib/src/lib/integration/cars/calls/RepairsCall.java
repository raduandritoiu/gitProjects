package lib.integration.cars.calls;

import lib.integration.common.interfaces.ICall;

public class RepairsCall implements ICall
{
  private final String _name;
  private final int _sessionId;
  
  
  public RepairsCall(int n_sessionId)
  {
    _name = "WE_ARE REPAIRING A CAR_FUUU";
    _sessionId = n_sessionId;
  }
  
  @Override
  public String name()
  {
    return _name;
  }
  
  @Override
  public int sessionId()
  {
    return _sessionId;
  }
  
  @Override
  public String toString()
  {
    return "Car Calls the core and tells \"They are repairing a car!\" with session <"+_sessionId+">";
  }
}
