package treehouse.event;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
  * An EventBus asynchronously distributes Events to a Set of registered Listeners.
  */
public class EventBus<E extends Event>
{
    /**
      * Asynchronously dispatch an event to all registered listeners.
      */
    public void dispatch(E event)
    {
        assert(event != null);
        dispatchPool.execute(new Dispatch(event));
    }

    /**
      * Registers the listener so that it will recieve events.
      */
    public void register(Listener<E> listener)
    {
        if (!listener.registered.compareAndSet(false, true))
            throw new IllegalStateException(
                "Listener " + listener + " is already registered.");

        listeners.add(listener);
    }

    /**
      * Unregisters the listener so that it will no longer recieve events.
      */
    public void unregister(Listener<E> listener)
    {
        if (!listener.registered.compareAndSet(true, false))
            throw new IllegalStateException(
                "Listener " + listener + " is not currently registered.");

        listeners.remove(listener);
    }

////////////////////////////////////////////////////////////////
// package
////////////////////////////////////////////////////////////////

    class Dispatch implements Runnable
    {
        Dispatch(E event) 
        { 
            this.event = event; 
        }

        /**
          * Send the event to every listener, via the listener's pool.
          */
        public void run() 
        {
            for (final Listener<E> listener : listeners) 
            {
                listener.pool.execute(new Runnable() {
                    public void run() { 
                        if (listener.registered.get())
                            listener.receive(event); 
                }});
            }
        }

        final E event;
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    final ExecutorService dispatchPool = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors());

    final Set<Listener<E>> listeners = Collections.newSetFromMap(
        new ConcurrentHashMap());
}
