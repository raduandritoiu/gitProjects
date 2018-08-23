package treehouse.http;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import javax.servlet.*;
import javax.servlet.http.*;

import treehouse.db.*;
import treehouse.db.event.*;
import treehouse.event.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.http.servlet.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
  * ListenerSocket
  */
public class ListenerSocket extends WebSocketAdapter
{
    public static WebSocketServlet makeFactoryServlet()
    {
        return new WebSocketServlet() {
            public void configure(WebSocketServletFactory factory) {
                factory.register(ListenerSocket.class);
        }};
    }

////////////////////////////////////////////////////////////////
// WebSocketAdapter
////////////////////////////////////////////////////////////////

    @Override
    public void onWebSocketConnect(Session sess)
    {
        super.onWebSocketConnect(sess);
        LOG.info("Socket Connected: aaa " + sess.getLocalAddress().getAddress());
    }

    @Override
    public void onWebSocketText(String str)
    {
        super.onWebSocketText(str);
        LOG.info("Received TEXT msg: " + str);

//        try
//        {
//            HDict msg = (HDict) JsonCodec.parse(str);
//
//            // subscribe
//            if (msg.getStr("msg").equals("subscribe"))
//            {
//                String id = msg.getStr("id");
//                if (!listeners.containsKey(id))
//                {
//                    TableListener ls = new TableListener(id);
//                    HttpServer.TABLE.register(ls);
//                    listeners.put(id, ls);
//                }
//            }
//            // unsubscribe
//            else if (msg.getStr("msg").equals("unsubscribe"))
//            {
//                String id = msg.getStr("id");
//                TableListener ls = listeners.get(id);
//                if (ls != null)
//                {
//                    HttpServer.TABLE.unregister(ls);
//                    listeners.remove(id);
//                }
//            }
//            else
//            {
//                LOG.error("Socket Error: unknown msg " + str);
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace(System.err);
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        super.onWebSocketClose(statusCode,reason);
        LOG.info("Socket Closed: [" + statusCode + "] " + reason);

//        try
//        {
//            for (TableListener ls : listeners.values())
//                HttpServer.TABLE.unregister(ls);
//
//            listeners.clear();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace(System.err);
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void onWebSocketError(Throwable cause)
    {
        super.onWebSocketError(cause);
        LOG.error("Socket Error: " + cause.getMessage());
        cause.printStackTrace(System.err);
    }

////////////////////////////////////////////////////////////////
// TableListener
////////////////////////////////////////////////////////////////

    class TableListener extends Listener<TableEvent>
    {
        public TableListener(String id)
        {
            super(POOL);
            this.id = id;
        }

        public void receive(TableEvent event)
        {
            // this is asynchronous
            getRemote().sendStringByFuture(JsonCodec.encode(event.toJson()));
        }

        final String id;
    }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

    private static final Logger LOG = LoggerFactory.getLogger(ListenerSocket.class);

    static final ExecutorService POOL = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors());

//    private ConcurrentMap<String,TableListener> listeners = 
//        new ConcurrentHashMap<String,TableListener>();
}

