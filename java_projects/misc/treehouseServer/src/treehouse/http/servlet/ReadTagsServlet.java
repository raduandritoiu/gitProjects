package treehouse.http.servlet;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.projecthaystack.*;
import treehouse.haystack.*;
import treehouse.haystack.Filter;
import treehouse.haystack.io.*;
import treehouse.db.*;

public class ReadTagsServlet extends TreehouseServlet
{
    public ReadTagsServlet(Table table)
    {
        this.table = table;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    { 
        try
        {
            sendResponse(table.readTags(), response);
        }
        catch (Exception e)
        {
            handleServletError(e, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    { 
        try
        {
            sendResponse(table.readTags(), response);
        }
        catch (Exception e)
        {
            handleServletError(e, response);
        }
    }

////////////////////////////////////////////////////////////////
// Attribs
////////////////////////////////////////////////////////////////
    
    private final Table table;
}
