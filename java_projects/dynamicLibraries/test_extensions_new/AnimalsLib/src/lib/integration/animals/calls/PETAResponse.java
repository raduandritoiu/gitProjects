package lib.integration.animals.calls;

import lib.integration.common.interfaces.ICall;
import lib.integration.common.interfaces.ICallResponse;

public class PETAResponse implements ICallResponse
{
  private final String _name;
  private final int _sessionId;
  private final String callName;
  
  public PETAResponse(ICall call)
  {
    _name = "FUCK_OFF";
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
    return "Animal Call Response <"+_name+"> \"P.E.T.A. RULLLZ\" in response to Call <"+callName+"> with session <"+_sessionId+">";
  }
}
