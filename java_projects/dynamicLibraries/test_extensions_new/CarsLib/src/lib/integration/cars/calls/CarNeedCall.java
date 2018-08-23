package lib.integration.cars.calls;

import lib.integration.common.interfaces.ICall;


public class CarNeedCall implements ICall
{
  private final String _name;
  private final int _sessionId;
  
  
  public CarNeedCall(int n_sessionId)
  {
    _name = "NEED_CAR";
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
    return "Car Calls the core and questions <"+_name+"> with session <"+_sessionId+">";
  }
}
