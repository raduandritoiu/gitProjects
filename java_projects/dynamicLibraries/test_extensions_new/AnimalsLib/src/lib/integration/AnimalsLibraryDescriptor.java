package lib.integration;

import java.util.ArrayList;

import lib.integration.animals.actions.AgeAnimalsAction;
import lib.integration.animals.actions.KillTheDogAction;
import lib.integration.animals.calls.AnimalsCall;
import lib.integration.animals.calls.PETACall;
import lib.integration.animals.calls.AnimalCountResponse;
import lib.integration.animals.calls.PETAResponse;
import lib.integration.animals.listeners.AnimalsEventCenter;
import lib.integration.animals.models.Cat;
import lib.integration.animals.models.Dog;
import lib.integration.animals.models.Racoon;
import lib.integration.common.interfaces.IAction;
import lib.integration.common.interfaces.ICall;
import lib.integration.common.interfaces.ICallResponse;
import lib.integration.common.interfaces.ICore;
import lib.integration.common.interfaces.IEvent;
import lib.integration.common.interfaces.ILibraryDescriptor;
import lib.integration.common.interfaces.IModel;

public class AnimalsLibraryDescriptor implements ILibraryDescriptor
{
  private final String _name;
  private final LibThread _libThread;
  
  private final ArrayList<IAction> _actions;
  private final ArrayList<IModel> _models;
  
  private ICore core;
  private final AnimalsEventCenter evtCenter;
  
  public AnimalsLibraryDescriptor()
  {
    _name = "AnimalsLib";
    _libThread = new LibThread();
    _libThread.sleepTime = 2500;
    
    // prep models
    _models = new ArrayList<>();
    Dog dog = new Dog();
    _models.add(dog);
    Cat cat = new Cat("Mr. Kittens");
    _models.add(cat);
    cat = new Cat("Miau-Miau");
    _models.add(cat);
    Racoon racoon = new Racoon();
    _models.add(racoon);
    
    // prep actions
    _actions = new ArrayList<>();
    _actions.add(new KillTheDogAction(dog));
    AgeAnimalsAction ageAction = new AgeAnimalsAction(5);
    ageAction.animals.add(dog);
    ageAction.animals.add(cat);
    _actions.add(ageAction);
    
    evtCenter = new AnimalsEventCenter();
    
    
    // ---- register the other classes ------
//    ICallResponse resp = new AnimalCountResponse(null);
//    resp = new PETAResponse(null);
//    ICall call = new AnimalsCall(0);
//    call = new PETACall(0);
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
    {
      response = new PETAResponse(call);
      System.out.println(response);
    }
    else 
    {
      response = new AnimalCountResponse(call);
      System.out.println(response);
    }
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
    
    private int callSessCnt;
    
    
    @Override
    public void run()
    {
      int cmd = -1;
      callSessCnt = 0;
      
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
          
            // call core one
            case 1:
              ICallResponse responde1 = core.callCore(new AnimalsCall(callSessCnt));
              System.out.println(responde1);
              callSessCnt += 2;
              break;
              
            // call core two
            case 2:
              ICallResponse responde2 = core.callCore(new PETACall(callSessCnt));
              System.out.println(responde2);
              callSessCnt += 2;
              break;
              
          // RADU_TODO
        }
      }
    }
  }
}
