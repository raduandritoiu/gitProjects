package treehouse.http;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.projecthaystack.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import treehouse.db.*;
import treehouse.db.event.*;
import treehouse.db.jdbc.postgres.*;
import treehouse.db.jdbc.sqlite.*;
import treehouse.event.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.http.servlet.*;

public abstract class HttpServer
{
    public static void init(HDict options) throws Exception
    {
        HDict hdb = options.getDict("db");

        String dbType = hdb.getStr("type");
        if (dbType.equals("postgres"))
        {
            DB = new PostgresDatabase(
                hdb.getStr("host"),
                hdb.getInt("port"),
                hdb.getStr("database"),
                hdb.getStr("username"),
                hdb.getStr("password"));
        }
        else if (dbType.equals("sqlite"))
        {
            DB = new SqliteDatabase(
                hdb.getStr("fileName"));
        }
        else
        {
            throw new IllegalStateException("unknown database type '" + dbType);
        }

        HList list = hdb.getList("tables");
        for (int i = 0; i < list.size(); i++)
        {
            HDict dict = list.getDict(i);
            DB.addTable(dict.getStr("name"));
        }

        list = hdb.getList("archives");
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                HDict dict = list.getDict(i);
                DB.addArchive(
                    DB.getTable(dict.getStr("parent")),
                    dict.getStr("name"));
            }
        }

        HDict serverOpt = options.getDict("server");
        port = serverOpt.getInt("port");
    }

    public static void start() throws Exception
    {
        DB.startUp();

        try
        {
            serverStart();
        }
        catch (Exception e)
        {
            DB.shutDown();
        }
    }

    public static void stop() throws Exception
    {
        DB.shutDown();
    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    private static void serverStart() throws Exception
    {
        Server server = new Server(port);
        server.setStopAtShutdown(true);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // tables
        for (Table table : DB.getTables())
        {
            String prefix = "/api/table/" + table.getName();

            context.addServlet(new ServletHolder(new BatchInsertServlet(table)), prefix + "/batchInsert/*");
            context.addServlet(new ServletHolder(new DeleteServlet(table)),      prefix + "/delete/*");
            context.addServlet(new ServletHolder(new InsertServlet(table)),      prefix + "/insert/*");
            context.addServlet(new ServletHolder(new ReadServlet(table)),        prefix + "/read/*");
            context.addServlet(new ServletHolder(new ReadTagsServlet(table)),    prefix + "/readTags/*");
            context.addServlet(new ServletHolder(new UpdateServlet(table)),      prefix + "/update/*");
        }

        // his
        for (Archive archive : DB.getArchives())
        {
            String prefix = "/api/archive/" + archive.getName();
            context.addServlet(new ServletHolder(new ArchiveReadServlet(archive)),  prefix + "/archiveRead/*");
            context.addServlet(new ServletHolder(new ArchiveWriteServlet(archive)), prefix + "/archiveWrite/*");
        }

        // websocket
        //context.addServlet(new ServletHolder(ListenerSocket.makeFactoryServlet()), prefix + "/websocket/*");

        // UI
        context.addServlet(new ServletHolder(new FileServlet("/treehouse/http/res/treehouse.html")), "/ui/");
        context.addServlet(new ServletHolder(new ResourceServlet()), "/ui/*");

        server.start();
        //server.dump(System.out); // this prints a bunch of useful-looking stuff
        server.join();
    }

////////////////////////////////////////////////////////////////
// main
////////////////////////////////////////////////////////////////

    /**
      * Read the treehouse options file
      */
    private static HDict readOptions(String optionsFile) throws Exception
    {
        return (HDict) JsonCodec.parse(
            new String(
                Files.readAllBytes(Paths.get(optionsFile)),
                StandardCharsets.UTF_8));
    }

    /**
      * Start a treehouse server
      */
    public static void main(String[] args) throws Exception
    {
        HDict options = readOptions(args[0]);
        HttpServer.init(options);

        (new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.print("> ");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String cmd = br.readLine();
                    while (true)
                    {
                        if (cmd.equals("quit"))
                        {
                            LOG.info("Stopping Treehouse");
                            stop();
                            System.exit(0);
                        }
                        else
                        {
                            LOG.error("'" + cmd + "' is not a valid command.");
                        }

                        System.out.print("> ");
                        cmd = br.readLine();
                    }
                }
                catch (Exception e) { 
                    e.printStackTrace();
                }
        }}, "HttpServer-CommandLine")).start();

        start();
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private static final Logger LOG = LoggerFactory.getLogger(HttpServer.class);

    private static int port;

    static Database DB;

////////////////////////////////////////////////////////////////
// temporary
////////////////////////////////////////////////////////////////

    static class TemporaryListener extends Listener<TableEvent>
    {
        public TemporaryListener()
        {
            super(Executors.newFixedThreadPool(4));
        }

        public void receive(TableEvent event)
        {
            System.out.println(
                "recieved " + event + 
                " on " + Thread.currentThread());
        }
    }
}
