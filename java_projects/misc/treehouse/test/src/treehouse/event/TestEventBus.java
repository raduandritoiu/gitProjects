package treehouse.event;

import java.util.*;
import java.util.concurrent.*;

public abstract class TestEventBus
{
    static class FooEvent implements Event
    {
        FooEvent(String desc) { this.desc = desc; }
        public String toString() { return desc; }
        final String desc;
    }

    static class FooListener extends Listener<FooEvent>
    {
        public FooListener(ExecutorService pool, String name)
        {
            super(pool);
            this.name = name;
        }

        public String toString() { return name; }

        public void receive(FooEvent event)
        {
            System.out.println(
                name + " " + 
                "recieved " + event + 
                " on " + Thread.currentThread());
        }

        final String name;
    }

    static class FooEventBus extends EventBus<FooEvent> 
    {
    }

    public static void testEvents() throws Exception
    {
        System.out.println("testEvents");

        final FooEventBus bus = new FooEventBus();

        ExecutorService pool = Executors.newFixedThreadPool(8);
        FooListener ls1 = new FooListener(pool, "Listener1");
        FooListener ls2 = new FooListener(pool, "Listener2");

        bus.register(ls1);
        bus.register(ls2);
        System.out.println(bus.listeners);

        for (int i = 0; i < 100; i++)
            bus.dispatch(new FooEvent("Before" + i));

        bus.unregister(ls2);
        System.out.println("==============================");

        for (int i = 0; i < 100; i++)
            bus.dispatch(new FooEvent("After" + i));

        Thread.sleep(2000);
    }
}
