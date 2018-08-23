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

/**
  * Insert some records.
  */
public class InsertServlet extends TreehouseServlet
{
    public InsertServlet(Table table)
    {
        this.table = table;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    { 
        try
        {
            HDict record = (HDict) JsonCodec.parse(request.getParameter("record"));

            table.insert(record);
            sendResponse(HStr.make("ok"), response);
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
            HDict body = (HDict) JsonCodec.parse(request.getInputStream());
            HDict record = body.getDict("record");

            table.insert(record);
            sendResponse(HStr.make("ok"), response);
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
