

import java.util.ArrayList;
import java.util.Iterator;

import lib.integration.common.interfaces.IAction;
import lib.integration.common.interfaces.ICallResponse;
import lib.integration.common.interfaces.IModel;
import lib.integration.core.Core;
import lib.integration.core.calls.CallOne;
import lib.integration.core.calls.CallTwo;
import lib.integration.core.listeners.CoreEventCenter;

public class Main
{
  public static void main (String[] args)
  {
    CoreEventCenter eventCenter = new CoreEventCenter();
    
    Core core = new Core();
    core.registerForEvent(eventCenter);
    
    
    String[] libs = new String[2];
    libs[0] = "../AnimalsLib/lib/AnimalsLib.jar";
    libs[1] = "../CarsLib/lib/CarsLib.jar";
    String libName = core.loadLibs(libs);
    System.out.println("CORE: loaded library " + libName);
    
    ArrayList<IModel> models= core.models;
    IModel model = models.get(3);
    Class<?> cls = model.getClass();
    ClassLoader ld1 = cls.getClassLoader();
    ClassLoader ld2 = ld1.getParent();
    ClassLoader ld3 = ld2.getParent();
    ClassLoader ld4 = ld3.getParent();
    
    
    
    System.out.println("------- Models -------------------");
    Iterator<IModel> modelIter = core.models.iterator();
    while (modelIter.hasNext())
    {
      System.out.println(" M : " + modelIter.next().toString());
    }
    System.out.println("------- Actions ------------------");
    Iterator<IAction> actionIter = core.actions.iterator();
    while (actionIter.hasNext())
    {
      System.out.println(" A : " + actionIter.next().toString());
    }
    System.out.println("----------------------------------");
    
    
    int callSessId = 1;
    
    long sleepTime = 100;
    boolean running = true;
    int cmd = -1;
    while (running)
    {
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
        // exit
        case 0:
          core.stop();
          running = false;
          break;
          
          // call one library one
          case 1:
            callSessId ++;
            CallOne oneCall1 = new CallOne(callSessId);
            ICallResponse resp1 = core.callLibrary("AnimalsLib", oneCall1);
            System.out.println(resp1);
            break;

          // call two library one
          case 2:
            callSessId ++;
            CallTwo carCall2 = new CallTwo(callSessId);
            ICallResponse resp2 = core.callLibrary("AnimalsLib", carCall2);
            System.out.println(resp2);
            break;
            
          // call one library two
          case 3:
            callSessId ++;
            CallOne oneCall3 = new CallOne(callSessId);
            ICallResponse resp3 = core.callLibrary("CarsLib", oneCall3);
            System.out.println(resp3);
            break;

          // call two library two
          case 4:
            callSessId ++;
            CallTwo carCall4 = new CallTwo(callSessId);
            ICallResponse resp4 = core.callLibrary("CarsLib", carCall4);
            System.out.println(resp4);
            break;
            
          // send event for all libraries to do something
          case 31:
            
            break;
            
          // send event for cars to respond to
          case 41:
            break;
            
      }
    }
  }
}
