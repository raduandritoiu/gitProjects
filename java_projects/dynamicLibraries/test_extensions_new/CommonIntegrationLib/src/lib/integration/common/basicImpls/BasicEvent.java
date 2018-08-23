package lib.integration.common.basicImpls;

import lib.integration.common.data.BasicEventTypes;
import lib.integration.common.interfaces.IEvent;
import lib.integration.common.interfaces.IEventType;
import lib.integration.common.interfaces.IEvtResponse;

public class BasicEvent implements IEvent
{
  private final String _name;
  private final int _sessionId;
  private final EvtKey _key;
  public int val;
  private BasicEvtResponse _response;

  public BasicEvent(String n_name, String n_callName, int n_sessionId, int n_val)
  {
    _name = n_name;
    _sessionId = n_sessionId;
    _key = new EvtKey(n_val);
    val = n_val;
  }

  
  @Override
  public String name()
  {
    return _name;
  }
  
  @Override
  public IEventType type()
  {
    return BasicEventTypes.ALL;
  }
  
  @Override
  public int sessionId()
  {
    return _sessionId;
  }
  
  @Override
  public Object responseKey()
  {
    return _key;
  }
  
  @Override
  public IEvtResponse response()
  {
    return _response;
  }

  /** set a response to this event */
  public void setResponse(BasicEvtResponse resp)
  {
    _response = resp;
  }
  
  
  // -----------------------------------------------
  public static class EvtKey
  {
    int keyVal;
    
    public EvtKey(int v)
    {
      keyVal = v;
    }
  }
  
  
  @Override
  public String toString()
  {
    String str = "Common Event <"+_name+"> with session <"+_sessionId+"> val <"+val+"> with a RESPONSE: ";
    if (_response != null) str += _response.toString();
    return str;
  }
}
