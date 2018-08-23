package treehouse.db.jdbc.postgres;

import java.sql.*;
import java.util.*;

import org.projecthaystack.*;

import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.db.*;

class PathNode implements Comparable<PathNode>
{
    static PathNode makeRoot()
    {
        return new PathNode(true, "<root>", 0);
    }

    static PathNode make(String tag, int id)
    {
        return new PathNode(false, tag, id);
    }

    private PathNode(boolean isRoot, String tag, int id)
    {
        this.isRoot = isRoot;
        this.tag = tag;
        this.id = id;
    }

////////////////////////////////////////////////////////////////
// Object
////////////////////////////////////////////////////////////////

    public String toString()
    {
        return tag + ":" + id;
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof PathNode)) return false;
        PathNode that = (PathNode) obj;
        return tag.equals(that.tag);
    }

    public int hashCode()
    {
        return tag.hashCode();
    }

////////////////////////////////////////////////////////////////
// Comparable
////////////////////////////////////////////////////////////////

    public int compareTo(PathNode that)
    {
        return tag.compareTo(that.tag);
    }

////////////////////////////////////////////////////////////////
// dump
////////////////////////////////////////////////////////////////

    public void dump()
    {
        dumpNode(this, 0);
    }

    private static void dumpNode(PathNode node, int indent)
    {
        for (int i = 0; i < indent; i++)
            System.out.print("    ");
        System.out.println(node);

        for (PathNode kid : node.kids.values())
            dumpNode(kid, indent + 1);
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    final boolean isRoot;
    final String tag;
    final int id;
    final TreeMap<String,PathNode> kids = new TreeMap<>();
}
