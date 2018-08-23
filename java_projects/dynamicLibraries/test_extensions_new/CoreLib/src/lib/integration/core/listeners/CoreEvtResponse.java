package lib.integration.core.listeners;

import lib.integration.common.interfaces.ICallResponse;
import lib.integration.common.interfaces.IEvent;

public class CoreEvtResponse implements ICallResponse
{
  private final String _name;
  private final int _sessionId;
  
  
  public CoreEvtResponse(String n_name, IEvent evt)
  {
    _name = n_name;
    _sessionId = evt.sessionId();
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
    return "Core Event Response <"+_name+"> in response to event with session <"+_sessionId+">";
  }
}