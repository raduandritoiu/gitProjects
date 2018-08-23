package lib.integration.common.basicImpls;

import lib.integration.common.interfaces.IEvtResponse;

public class BasicEvtResponse implements IEvtResponse
{
  private final String _name;
  private final int _sessionId;
  private final String evtName;
  public int val;
  
  public BasicEvtResponse(String n_name, String n_evtName, int n_sessionId, int n_val)
  {
    _name = n_name;
    _sessionId = n_sessionId;
    evtName = n_evtName;
    val = n_val;
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
    return "Common Event Response <"+_name+"> with val <"+val+"> in response to Event <"+evtName+"> with session <"+_sessionId+">";
  }
}
