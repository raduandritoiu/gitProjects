package lib.integration.common.interfaces;

public interface ICore
{
  /** callback function for libraries to register to core */
  int registerLib(ILibraryDescriptor lib);
  
  /** way for libraries to ask stuff to the core */
  ICallResponse callCore(ICall call);
  
  /** way to register to receive events */
  int registerForEvent(IEventListener listener);
  
  /** way for libraries to send events */
  int sendEvent(IEvent evt);
}
