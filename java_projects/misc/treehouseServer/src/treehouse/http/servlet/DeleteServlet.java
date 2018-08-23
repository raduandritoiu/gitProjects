package treehouse.http.servlet;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.common.collect.ImmutableSet;

import org.projecthaystack.*;
import treehouse.haystack.*;
import treehouse.haystack.Filter;
import treehouse.haystack.io.*;
import treehouse.db.*;

public class DeleteServlet extends TreehouseServlet
{
    public DeleteServlet(Table table)
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
                table.deleteById(id);
                sendResponse(HStr.make("ok"), response);
            }
            else
            {
                Filter filter = FilterParser.parse(request.getParameter("filter"));
                table.deleteAll(filter);
                sendResponse(HStr.make("ok"), response);
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
                table.deleteById(body.id());
                sendResponse(HStr.make("ok"), response);
            }
            else
            {
                Filter filter = FilterParser.parse(body.getStr("filter"));
                table.deleteAll(filter);
                sendResponse(HStr.make("ok"), response);
            }
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
