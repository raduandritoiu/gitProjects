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

public class ReadServlet extends TreehouseServlet
{
    public ReadServlet(Table table)
    {
        this.table = table;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    { 
        try
        {
            String strId = request.getParameter("id"); 
            if (strId != null) 
            {
                HRef id = (HRef) JsonCodec.parse('"' + strId + '"');
                readById(id, response);
            }
            else
            {
                Filter filter = FilterParser.parse(request.getParameter("filter"));
                int skip = parseInt(request.getParameter("skip"));
                int limit = parseInt(request.getParameter("limit"));
                readAll(filter, skip, limit, response);
            }
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
            if (body.has("id"))
            {
                readById(body.id(), response);
            }
            else
            {
                Filter filter = FilterParser.parse(body.getStr("filter"));
                int skip  = body.has("skip")  ? body.getInt("skip")  : -1;
                int limit = body.has("limit") ? body.getInt("limit") : -1;
                readAll(filter, skip, limit, response);
            }
        }
        catch (Exception e)
        {
            handleServletError(e, response);
        }
    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    private int parseInt(String str)
    {
        if (str == null) return -1;
        return Integer.parseInt(str);
    }

    private void readById(HRef id, HttpServletResponse response)
    throws Exception
    {
        HDict record = table.readById(id);
        sendResponse(record, response);
    }

    private void readAll(Filter filter, int skip, int limit, HttpServletResponse response)
    throws Exception
    {
        try (Cursor cursor = table.readAll(filter, skip, limit))
        {
            sendResponse(cursor, response);
        }
    }

////////////////////////////////////////////////////////////////
// Attribs
////////////////////////////////////////////////////////////////
    
    private final Table table;
}
