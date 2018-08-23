package treehouse.event;

import java.util.*;
import java.util.concurrent.*;

/**
  * An Event is distributed by an EventBus to all registered Listeners.
  * <p>
  * Subclasses of Event should be immutable. That way, all the Listeners will
  * recieve the same event.
  */
public interface Event
{
}
