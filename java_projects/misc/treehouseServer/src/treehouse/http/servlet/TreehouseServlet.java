package treehouse.http.servlet;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.common.io.ByteStreams;
import org.projecthaystack.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import treehouse.db.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.http.*;

public abstract class TreehouseServlet extends HttpServlet
{
    void sendResponse(HVal result, HttpServletResponse response) throws Exception
    {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
        JsonCodec.encode(result, response.getOutputStream());
    }

    void sendResponse(Cursor cursor, HttpServletResponse response) throws Exception
    {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
        JsonCodec.encode(cursor, response.getOutputStream());
    }

    void sendResponse(HisCursor cursor, HttpServletResponse response) throws Exception
    {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
        JsonCodec.encode(cursor, response.getOutputStream());
    }

    void handleServletError(Exception e, HttpServletResponse response)
    {
        LOG.error(e.getMessage());
        e.printStackTrace();

        try 
        { 
            HDict error = new HDictBuilder()
                .add("error", HStr.make(e.getMessage()))
                .toDict();

            response.setContentType(CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonCodec.encode(error, response.getOutputStream());
        } 
        catch (Exception e2) 
        { 
            throw new RuntimeException(e2); 
        }
    }

    /**
      * serve up a file
      */
    void serveFile(String uri, HttpServletResponse response) throws Exception
    {
        if      (uri.endsWith(".js"))   response.setContentType("text/javascript");
        else if (uri.endsWith(".css"))  response.setContentType("text/css");
        else if (uri.endsWith(".html")) response.setContentType("text/html");

        response.setStatus(HttpServletResponse.SC_OK);

        InputStream in = getClass().getResourceAsStream(uri);
        OutputStream out = response.getOutputStream();
        ByteStreams.copy(in, out);
        out.flush();
        out.close();
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private static final Logger LOG = LoggerFactory.getLogger(TreehouseServlet.class);

    public static final String CONTENT_TYPE = "application/json; charset=UTF-8";
}
