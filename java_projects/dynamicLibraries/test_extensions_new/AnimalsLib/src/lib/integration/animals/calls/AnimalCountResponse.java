package lib.integration.animals.calls;

import lib.integration.common.interfaces.ICall;
import lib.integration.common.interfaces.ICallResponse;

public class AnimalCountResponse implements ICallResponse
{
  private final String _name;
  private final int _sessionId;
  private final String callName;
  
  public AnimalCountResponse(ICall call)
  {
    _name = "ANIMAL_NUMBER";
    if (call != null)
    {
      _sessionId = call.sessionId();
      callName = call.name();
    }
    else
    {
      _sessionId = -1;
      callName = "none";
    }
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
    return "Animal Call Response <"+_name+"> \"We have like 5 animals\" in response to Call <"+callName+"> with session <"+_sessionId+">";
  }
}
