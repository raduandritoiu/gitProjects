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
public class BatchInsertServlet extends TreehouseServlet
{
    public BatchInsertServlet(Table table)
    {
        this.table = table;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    { 
        try
        {
            HList records = (HList) JsonCodec.parse(request.getParameter("records"));

            table.batchInsert(asIterator(records));
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
            HList records = body.getList("records");

            table.batchInsert(asIterator(records));
            sendResponse(HStr.make("ok"), response);
        }
        catch (Exception e)
        {
            handleServletError(e, response);
        }
    }

    private Iterator<HDict> asIterator(HList list)
    {
        List<HDict> result = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++)
            result.add(list.getDict(i));
        return result.iterator();
    }

////////////////////////////////////////////////////////////////
// Attribs
////////////////////////////////////////////////////////////////
    
    private final Table table;
}
