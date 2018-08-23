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

public class ArchiveReadServlet extends TreehouseServlet
{
    public ArchiveReadServlet(Archive archive)
    {
        this.archive = archive;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    { 
        try
        {
            HDateTimeRange range = null;
            Filter filter = null;

            String strRange = request.getParameter("range"); 
            if (strRange != null)
            {
                HDict mapRange = (HDict) JsonCodec.parse(strRange);
                range = HDateTimeRange.make(
                    mapRange.getDateTime("start"),
                    mapRange.getDateTime("end"));
            }

            String strFilter = request.getParameter("filter"); 
            if (strFilter != null) 
                filter = FilterParser.parse(strFilter);

            int skip  = parseInt(request.getParameter("skip"));
            int limit = parseInt(request.getParameter("limit"));

            read(range, filter, skip, limit, response);
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

            HDateTimeRange range = null;
            Filter filter = null;

            if (body.has("range"))
            {
                HDict mapRange = body.getDict("range");
                range = HDateTimeRange.make(
                    mapRange.getDateTime("start"),
                    mapRange.getDateTime("end"));
            }

            if (body.has("filter"))
            {
                filter = FilterParser.parse(body.getStr("filter"));
            }

            int skip  = body.has("skip")  ? body.getInt("skip")  : -1;
            int limit = body.has("limit") ? body.getInt("limit") : -1;

            read(range, filter, skip, limit, response);
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

    private void read(
        HDateTimeRange range, Filter filter, int skip, int limit, 
        HttpServletResponse response)
    throws Exception
    {
        try (HisCursor cursor = archive.archiveRead(range, filter, skip, limit))
        {
            sendResponse(cursor, response);
        }
    }

////////////////////////////////////////////////////////////////
// Attribs
////////////////////////////////////////////////////////////////
    
    private final Archive archive;
}
