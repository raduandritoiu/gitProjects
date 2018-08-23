package lib.integration.common.interfaces;

import java.util.ArrayList;

public interface ILibraryDescriptor
{
  /** name for the library */
  String name();
  
  /** The core calls this function to Start the library.
   * The library must register to the core in this class */
  int start(ICore core);
  
  /** The core calls this function to Stop the library */
  int stop();
  
  /** name for the library */
  ICallResponse callLib(ICall call);
  
  /** getter for the supported actions of this library */
  ArrayList<IAction> supportedActions();
  /** getter for the supported actions of this library */
  ArrayList<IModel> supportedModels();
  /** getter for the supported actions of this library */
  ArrayList<IEvent> supportedEvents();
}
