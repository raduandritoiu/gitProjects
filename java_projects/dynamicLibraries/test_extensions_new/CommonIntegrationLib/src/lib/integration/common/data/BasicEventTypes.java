package lib.integration.common.data;

import lib.integration.common.interfaces.IEventType;

public class BasicEventTypes
{
  public static IEventType ALL = new AllEventType();
  public static IEventType START = new StartEventType();
  public static IEventType SSTOP = new StopEventType();
  
  
// ======================== 
  
  private static class AllEventType implements IEventType
  {
    private final int _hash;
    public AllEventType() {_hash = "ALL".hashCode(); }
    public int hashCode() { return _hash; }
    public String typeName() { return "ALL"; }
  }
  
  private static class StartEventType implements IEventType
  {
    private final int _hash;
    public StartEventType() {_hash = "START".hashCode(); }
    public int hashCode() { return _hash; }
    public String typeName() { return "START"; }
  }
  
  private static class StopEventType implements IEventType
  {
    private final int _hash;
    public StopEventType() {_hash = "STOP".hashCode(); }
    public int hashCode() { return _hash; }
    public String typeName() { return "STOP"; }
  }
}
