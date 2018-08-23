package lib.integration.core;

import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import lib.integration.common.interfaces.IAction;
import lib.integration.common.interfaces.ICall;
import lib.integration.common.interfaces.ICallResponse;
import lib.integration.common.interfaces.ICore;
import lib.integration.common.interfaces.IEvent;
import lib.integration.common.interfaces.IEventListener;
import lib.integration.common.interfaces.ILibraryDescriptor;
import lib.integration.common.interfaces.IModel;
import lib.integration.core.calls.OkResponse;


public class Core implements ICore
{
  private final HashMap<String, ILibraryDescriptor> libraries;
  private final HashMap<String, IEventListener> listeners;
  
  public final ArrayList<IAction> actions;
  public final ArrayList<IModel> models;
  
  
  public Core()
  {
    libraries = new HashMap<>();
    listeners = new HashMap<>();
    
    actions = new ArrayList<>();
    models = new ArrayList<>();
  }
  
  
  // ----------- implement the interfacing for libraries ---------------------------------
  @Override
  public ICallResponse callCore(ICall call)
  {
    ICallResponse response = new OkResponse("FROM_CORE", call);
    return response;
  }
  
  @Override
  public int registerLib(ILibraryDescriptor lib)
  {
    libraries.put(lib.name(), lib);
    actions.addAll(lib.supportedActions());
    models.addAll(lib.supportedModels());
    return 0;
  }
  
  @Override
  public int registerForEvent(IEventListener listener)
  {
    listeners.put(listener.name(), listener);
    return 0;
  }
  
  @Override
  public int sendEvent(IEvent event)
  {
    Iterator<IEventListener> listIterator = listeners.values().iterator();
    while (listIterator.hasNext())
    {
      IEventListener listener = listIterator.next();
      listener.eventReceived(event);
    }
    return 0;
  }
  
  
  // ----------- adding functions to talk to libraries ---------------------------------
  
  public String loadLibs(String[] libPath)
  {
    try
    {
      URL[] urls = new URL[libPath.length];
      for (int i = 0; i < libPath.length; i++)
        urls[i] = new URL("jar:file:" + libPath[i] + "!/");
      
      URLClassLoader loader = new URLClassLoader(urls);
      
      for (int i = 0; i < urls.length; i++)
      {
        JarURLConnection conn = (JarURLConnection) urls[i].openConnection();
        Attributes attrs = conn.getMainAttributes();
        String libFullName = (attrs != null) ? attrs.getValue(Attributes.Name.MAIN_CLASS) : null;
        if (libFullName == null)
          libFullName = "lib.integration.LibraryDescriptor";
        
        Class libDescCls = loader.loadClass(libFullName);
        Method libMth = libDescCls.getMethod("start", ICore.class);
        ILibraryDescriptor lib = (ILibraryDescriptor) libDescCls.newInstance();
        String libName = lib.name();
        libMth.invoke(lib, this);
      }
//      loader.close();
      return "OK";
    }
    catch (Exception ex) 
    {
      System.out.println("FUCK!!! " + ex);
      return null;
    }
  }
  
  public String loadLib(String[] libPath)
  {
    try
    {
      URL[] urls = new URL[libPath.length];
      for (int i = 0; i < libPath.length; i++)
        urls[i] = new URL("jar:file:" + libPath[i] + "!/");
      
      URLClassLoader loader = new URLClassLoader(urls);
      
      Class libDescCls = loader.loadClass("lib.integration.LibraryDescriptor");
      Method libMth = libDescCls.getMethod("start", ICore.class);
      ILibraryDescriptor lib = (ILibraryDescriptor) libDescCls.newInstance();
      String libName = lib.name();
      libMth.invoke(lib, this);
      loader.close();
    }
    catch (Exception ex) 
    {
      System.out.println("FUCK!!! " + ex);
      return null;
    }
    return "ok";
  }
  
  public void unloadLib(String libName)
  {
    ILibraryDescriptor lib = libraries.get(libName);
    if (lib != null)
      lib.stop();
    libraries.remove(libName);
  }
  
  public void stop()
  {
    Iterator<ILibraryDescriptor> libIterator = libraries.values().iterator();
    while (libIterator.hasNext())
    {
      ILibraryDescriptor lib = libIterator.next();
      lib.stop();
    }
  }
  
  public ICallResponse callLibrary(String libName, ICall call)
  {
    ILibraryDescriptor lib = libraries.get(libName);
    if (lib != null)
    {
      ICallResponse response = lib.callLib(call);
      return response;
    }
    return null;
  }
}
