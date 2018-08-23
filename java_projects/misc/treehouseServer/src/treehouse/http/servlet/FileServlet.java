package treehouse.http.servlet;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
  * FileServlet serves a specific file
  */
public class FileServlet extends TreehouseServlet
{
    public FileServlet(String uri)
    {
        this.uri = uri;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    { 
        try
        {
            serveFile(uri, response);
        }
        catch (Exception e)
        {
            handleServletError(e, response);
        }
    }

    private final String uri;
}
