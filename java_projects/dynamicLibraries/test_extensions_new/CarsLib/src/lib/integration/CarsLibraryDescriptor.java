package lib.integration;

import java.util.ArrayList;

import lib.integration.cars.CarsEventCenter;
import lib.integration.cars.actions.DistroyCars;
import lib.integration.cars.actions.RepairCars;
import lib.integration.cars.calls.CarAvailableResponse;
import lib.integration.cars.calls.CarNeedCall;
import lib.integration.cars.calls.RepairsCall;
import lib.integration.cars.calls.TaxiResponse;
import lib.integration.cars.models.BMW;
import lib.integration.cars.models.Volkswagen;
import lib.integration.common.interfaces.IAction;
import lib.integration.common.interfaces.ICall;
import lib.integration.common.interfaces.ICallResponse;
import lib.integration.common.interfaces.ICore;
import lib.integration.common.interfaces.IEvent;
import lib.integration.common.interfaces.ILibraryDescriptor;
import lib.integration.common.interfaces.IModel;


public class CarsLibraryDescriptor implements ILibraryDescriptor
{
  private final String _name;
  private final LibThread _libThread;
  
  private final ArrayList<IAction> _actions;
  private final ArrayList<IModel> _models;
  
  private ICore core;
  private final CarsEventCenter evtCenter;
  
  public CarsLibraryDescriptor()
  {
    _name = "CarsLib";
    _libThread = new LibThread();
    _libThread.sleepTime = 1500;
    
    // prep models
    _models = new ArrayList<>();
    Volkswagen c1 = new Volkswagen();
    _models.add(c1);
    BMW c2 = new BMW(3);
    _models.add(c2);
    BMW c3 = new BMW(5);
    _models.add(c3);
    
    // prep actions
    _actions = new ArrayList<>();
    RepairCars repair = new RepairCars(20);
    repair.cars.add(c1);
    repair.cars.add(c2);
    _actions.add(repair);
    DistroyCars distroy = new DistroyCars(20);
    distroy.cars.add(c2);
    distroy.cars.add(c3);
    _actions.add(distroy);
    
    evtCenter = new CarsEventCenter();
    
    // ---- register the other classes ------
//    ICallResponse resp = new TaxiResponse(null);
//    resp = new CarAvailableResponse(null);
//    ICall call = new CarNeedCall(0);
//    call = new RepairsCall(0);
  }
  
  
  @Override
  public String name()
  {
    return _name;
  }
  
  @Override
  public int start(ICore n_core)
  {
    core = n_core;
    core.registerLib(this);
    core.registerForEvent(evtCenter);
    
    _libThread.running = true;
    _libThread.start();
    
    System.out.println("Started lib : " + _name);
    return 0;
  }
  
  @Override
  public int stop()
  {
    _libThread.running = false;
    try
    {
      _libThread.join();
    }
    catch (InterruptedException intr)
    {
      System.out.println("Join intrerupted by FUCK: " + intr);
    }
    return 0;
  }
  
  
  @Override
  public ICallResponse callLib(ICall call)
  {
    ICallResponse response;
    if (call.name().length() > 10)
      response = new TaxiResponse(call);
    else
      response = new CarAvailableResponse(call);
    return response;
  }
  
  
  @Override
  public ArrayList<IAction> supportedActions()
  {
    return _actions;
  }
  
  @Override
  public ArrayList<IModel> supportedModels()
  {
    return _models;
  }
  
  @Override
  public ArrayList<IEvent> supportedEvents()
  {
    return null;
  }
  
  
  // ----------------------------------------------------
  class LibThread extends Thread
  {
    public boolean running;
    public long sleepTime;
    private int evtSessCnt;
    private int callSessCnt;
    
    
    @Override
    public void run()
    {
      evtSessCnt = 0;
      callSessCnt = 0;
      
      int cmd = -1;
      while (running)
      {
        // sleep
        try
        {
          Thread.sleep(sleepTime);
        }
        catch (InterruptedException intr)
        {
          System.out.println("Sleep intrerupted by FUCK: " + intr);
        }

        switch (cmd)
        {
          case 0:
            running = false;
            break;
          
            // call core
            case 1:
              ICallResponse responde1 = core.callCore(new CarNeedCall(callSessCnt));
              System.out.println(responde1);
              callSessCnt += 2;
              break;

              // call core
            case 2:
              ICallResponse responde2 = core.callCore(new RepairsCall(callSessCnt));
              System.out.println(responde2);
              callSessCnt += 2;
              break;

          // RADU_TODO
        }
      }
    }
  }
}
