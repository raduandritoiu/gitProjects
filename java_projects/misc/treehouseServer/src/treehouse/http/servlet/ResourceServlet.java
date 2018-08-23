package treehouse.http.servlet;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
  * ResourceServlet serves a file from the resource directory
  */
public class ResourceServlet extends TreehouseServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    { 
        try
        {
            String uri = request.getRequestURI().toString();
            if (!uri.startsWith("/ui/"))
                throw new IllegalStateException();
            uri = "/treehouse/http/res/" + uri.substring("/ui/".length());

            serveFile(uri, response);
        }
        catch (Exception e)
        {
            handleServletError(e, response);
        }
    }
}
