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

public class UpdateServlet extends TreehouseServlet
{
    public UpdateServlet(Table table)
    {
        this.table = table;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    { 
        try
        {
            HDict md = (HDict) JsonCodec.parse(request.getParameter("diff"));
            Diff diff = Diff.make(
                (HDict) md.get("put", false),
                convertList((HList) md.get("remove", false)));

            String strId = request.getParameter("id"); 
            if (strId != null) 
            {
                HRef id = (HRef) JsonCodec.parse('"' + strId + '"');
                table.updateById(id, diff);
                sendResponse(HStr.make("ok"), response);
            }
            else
            {
                Filter filter = FilterParser.parse(request.getParameter("filter"));
                table.updateAll(filter, diff);
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

            HDict md = body.getDict("diff");
            Diff diff = Diff.make(
                (HDict) md.get("put", false),
                convertList((HList) md.get("remove", false)));

            if (body.has("id"))
            {
                table.updateById(body.id(), diff);
                sendResponse(HStr.make("ok"), response);
            }
            else
            {
                Filter filter = FilterParser.parse(body.getStr("filter"));
                table.updateAll(filter, diff);
                sendResponse(HStr.make("ok"), response);
            }
        }
        catch (Exception e)
        {
            handleServletError(e, response);
        }
    }

    private ImmutableSet<String> convertList(HList list)
    {
        if (list == null) return null;

        ImmutableSet.Builder<String> b = new ImmutableSet.Builder<String>();
        for (int i = 0; i < list.size(); i++)
        {
            HVal val = list.get(i);

            if (!(val instanceof HStr))
                throw new IllegalArgumentException(
                    JsonCodec.encode(list) + " must contain only Strings.");
            b.add(((HStr) val).val);
        }
        return b.build();
    }

////////////////////////////////////////////////////////////////
// Attribs
////////////////////////////////////////////////////////////////
    
    private final Table table;
}
