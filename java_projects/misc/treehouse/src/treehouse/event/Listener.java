package treehouse.event;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
  * An Listener listens for Events that are distributed via an EventBus.
  * <p>
  * Subclasses of Listener must provide their own ExecutorService.
  */
public abstract class Listener<E extends Event>
{
    /**
      * Create a new Listener
      *
      * @param pool An ExecutorService that an EventBus will use to call recieve(). 
      */
    public Listener(ExecutorService pool)
    {
        this.pool = pool;
    }

    /**
      * Recieve notification of an event.  An EventBus calls this method via
      * a thread obtained from this Listener's ExecutorService.
      * <p>
      * A Listener only recieves events when it is registered with an EventBus.
      * <p>
      * <b><i>This method should never be called directly!</i></b> 
      *  Let an EventBus do it.
      */
    public abstract void receive(E event);

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    AtomicBoolean registered = new AtomicBoolean(false);

    final ExecutorService pool;
}
