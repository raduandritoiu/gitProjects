package treehouse.rhino;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSConstructor;

import org.projecthaystack.*;

import treehouse.db.*;
import treehouse.db.jdbc.postgres.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;

public class RhinoTable extends ScriptableObject 
{
    @JSConstructor
    public RhinoTable() {}

    @Override
    public String getClassName() { return "Table"; }

////////////////////////////////////////////////////////////////
// public
////////////////////////////////////////////////////////////////

    @JSFunction
    public final HList readAll(String str)
    {
        Filter filter = FilterParser.parse(str);
        try (Cursor cursor = table.readAll(filter))
        {
            HListBuilder lb = new HListBuilder();
            while (cursor.hasNext())
                lb.add(cursor.next());
            return lb.toList();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    //private static final Database db;
    public static Table table;

    //static
    //{
    //    db = new PostgresDatabase(
    //        "localhost", 5432, "treehouse",
    //        "treehouse_user", "treehouse_user");
    //    table = db.addTable("demo");
    //}
}
