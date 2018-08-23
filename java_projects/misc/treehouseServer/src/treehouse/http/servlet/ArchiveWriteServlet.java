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

public class ArchiveWriteServlet extends TreehouseServlet
{
    public ArchiveWriteServlet(Archive archive)
    {
        this.archive = archive;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    { 
        try
        {
            HList list = (HList) JsonCodec.parse(
                request.getParameter("items"));

            archive.archiveWrite(toItems(list));
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
            HList list = body.getList("items");

            archive.archiveWrite(toItems(list));
            sendResponse(HStr.make("ok"), response);
        }
        catch (Exception e)
        {
            handleServletError(e, response);
        }
    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    private Iterator<HisItem> toItems(HList list) throws Exception
    {
        List<HisItem> items = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++)
        {
            HDict dict = list.getDict(i);
            items.add(
                new HisItem(
                    dict.getDateTime("ts"),
                    dict.get("obj")));
        }
        return items.iterator();
    }

////////////////////////////////////////////////////////////////
// Attribs
////////////////////////////////////////////////////////////////
    
    private final Archive archive;
}
