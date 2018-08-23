package treehouse.db.jdbc.postgres;

import java.sql.*;
import java.util.*;

import org.projecthaystack.*;

import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.db.*;

class SqlBuilder
{
    static String buildSelect(
        String name,
        Filter filter, String projection, 
        int skip, int limit)
    {
        return (new SqlBuilder(name, null, false, filter)).doBuildSelect(projection, skip, limit);
    }

    static String buildArchiveSelect(
        String name, String parentName,
        HDateTimeRange range, Filter filter, 
        int skip, int limit)
    {
        return (new SqlBuilder(name, parentName, true, filter)).doBuildArchiveSelect(range, skip, limit);
    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    private SqlBuilder(String name, String parentName, boolean isArch, Filter filter) 
    {
        this.name = name;
        this.parentName = parentName;
        this.isArch = isArch;
        this.filter = filter;
        this.rootNode = PathNode.makeRoot();
    }

    private String doBuildSelect(String projection, int skip, int limit)
    {
        PathNodeBuilder pn = new PathNodeBuilder();
        filter.accept(pn);

        StringBuilder joinSb = new StringBuilder();
        buildJoins(rootNode, joinSb);
        String joins = joinSb.toString();

        PredicateBuilder pg = new PredicateBuilder();
        filter.accept(pg);
        String pred = pg.build();

        // sql
        StringBuffer sb = new StringBuffer();
        sb.append("select " + projection + " from " + name + "_recs r0\n");
        sb.append(joins);
        sb.append("where ").append(pred); 
        if (limit != -1) sb.append(" limit ")  .append(limit);
        if (skip  != -1) sb.append(" offset ") .append(skip);
        sb.append(";");

        return sb.toString();
    }

    private String doBuildArchiveSelect(HDateTimeRange range, int skip, int limit)
    {
        String joins = null;
        String pred = null;

        if (filter != null)
        {
            PathNodeBuilder pn = new PathNodeBuilder();
            filter.accept(pn);

            StringBuilder joinSb = new StringBuilder();
            buildJoins(rootNode, joinSb);
            joins = joinSb.toString();

            PredicateBuilder pg = new PredicateBuilder();
            filter.accept(pg);
            pred = pg.build();
        }

        // sql
        StringBuffer sb = new StringBuffer();
        sb.append("select r0.tsChunk, r0.tsOffset, r0.tz, r0.doc ");
        sb.append("from ").append(name).append("_arch r0\n");

        if (filter != null) sb.append(joins);
        sb.append("where ");

        if (range != null) sb.append(rangePredicate(range));
        if ((range != null) && (filter != null)) sb.append(" and ");
        if (filter != null) sb.append(pred); 

        sb.append("\norder by r0.tsChunk, r0.tsOffset");
        if (limit != -1) sb.append(" limit ")  .append(limit);
        if (skip  != -1) sb.append(" offset ") .append(skip);
        sb.append(";");

        return sb.toString();
    }

    private String rangePredicate(HDateTimeRange range)
    {
        int startChunk = PostgresArchive.chunk(range.start.millis());
        int endChunk   = PostgresArchive.chunk(range.end.millis());

        return 
            "(r0.tsChunk >= " + startChunk + " and " + 
             "r0.tsChunk <= " + endChunk + ")";
    }

    /**
      * buildJoins
      */
    private void buildJoins(PathNode node, StringBuilder sb)
    {
        for (PathNode kid : node.kids.values())
        {
            String pr = "r" + node.id;
            String kr = "r" + kid.id;
            String kj = "j" + kid.id;

            // If we are joining from the rootNode, and this is a archive query,
            // then the join table needs to be 'archrefs' instead of 'refs'.
            String refs   = isArch ?
                (node.isRoot ? name + "_archrefs" : parentName + "_refs") :
                name + "_refs";

            // Also, in that case, the rootNode id should be 'rowid', not 'id'
            String rootId = (node.isRoot && isArch) ? "rowid" : "id";

            // from the parent node to the join
            sb.append("join ").append(refs).append(" ").append(kj).append(" on "); 
            sb.append(kj).append(".fromId = ").append(pr).append(".").append(rootId).append(" ");
            sb.append("and ").append(kj).append(".tag = '").append(kid.tag).append("'\n");

            // from the join to the child node
            sb.append("join ").append(isArch ? parentName : name).append("_recs ").append(kr).append(" on "); 
            sb.append(kj).append(".toRef  = ").append(kr).append(".id ");
            sb.append("and ").append(kj).append(".tag = '").append(kid.tag).append("'\n");
        }

        for (PathNode kid : node.kids.values())
            buildJoins(kid, sb);
    }

    /**
      * Add the Path to the Tree
      */
    private void addPath(Path path)
    {
        if (path.size() == 1) return;

        PathNode node = rootNode;
        for (int i = 0; i < path.size()-1; i++)
        {
            String tag = path.get(i);
            PathNode kid = node.kids.get(tag);
            if (kid == null)
                node.kids.put(tag, kid = PathNode.make(tag, nextId++));
            node = kid;
        }
    }

    /**
      * Get the Alias Identifier for the given path
      */
    private int getAliasId(Path path)
    {
        PathNode node = rootNode;
        for (int i = 0; i < path.size()-1; i++)
            node = node.kids.get(path.get(i));
        return node.id;
    }

    /**
      * unsupported
      */
    private static UnsupportedOperationException unsupported(Filter.Compare cmp)
    {
        return new UnsupportedOperationException(
            "PostgresTable does not support the '" + cmp.cmp() + "' operator.");
    }

    /**
      * PathNodeBuilder
      */
    private class PathNodeBuilder implements Filter.Visitor 
    {
        public void visit(Filter.And and)
        {
            and.lhs.accept(this);
            and.rhs.accept(this);
        }

        public void visit(Filter.Or or)
        {
            or.lhs.accept(this);
            or.rhs.accept(this);
        }

        public void visit(Filter.Not not)
        {
            not.filter.accept(this);
        }

        public void visit(Filter.Contains contains) { addPath(contains.path); }
        public void visit(Filter.Eq  eq)  { addPath(eq.path); }
        public void visit(Filter.Has has) { addPath(has.path); }

        public void visit(Filter.Lt lt) { throw unsupported(lt); }
        public void visit(Filter.Le le) { throw unsupported(le); }
        public void visit(Filter.Gt gt) { throw unsupported(gt); }
        public void visit(Filter.Ge ge) { throw unsupported(ge); }
    }

    /**
      * PredicateBuilder
      */
    private class PredicateBuilder implements Filter.Visitor 
    {
        public void visit(Filter.And and)
        {
            sb.append("(");
            and.lhs.accept(this);
            sb.append(" and ");
            and.rhs.accept(this);
            sb.append(")");
        }

        public void visit(Filter.Or or)
        {
            sb.append("(");
            or.lhs.accept(this);
            sb.append(" or ");
            or.rhs.accept(this);
            sb.append(")");
        }

        public void visit(Filter.Not not)
        {
            sb.append("(not ");
            not.filter.accept(this);
            sb.append(")");
        }

        public void visit(Filter.Contains contains)
        {
            Path path = contains.path;
            int aliasId = getAliasId(path);

            sb.append("(r").append(aliasId);
            sb.append(".doc ? '").append(path.last()).append("')");
        }

        public void visit(Filter.Eq eq)
        {
            doHas(eq.path, eq.rhs);
        }

        public void visit(Filter.Has has)
        {
            doHas(has.path, has.rhs);
        }

        void doHas(Path path, HVal rhs)
        {
            sb.append("(r").append(getAliasId(path));
            sb.append(".doc @> '{");
            sb.append("\"").append(path.last()).append("\":");
            sb.append(JsonCodec.encode(rhs));
            sb.append("}'::jsonb)");
        }

        public void visit(Filter.Lt lt) { throw unsupported(lt); }
        public void visit(Filter.Le le) { throw unsupported(le); }
        public void visit(Filter.Gt gt) { throw unsupported(gt); }
        public void visit(Filter.Ge ge) { throw unsupported(ge); }

        private String build() { return sb.toString(); }

        private StringBuilder sb = new StringBuilder();
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private final String name;
    private final String parentName;
    private final boolean isArch;
    private final Filter filter; 
    private final PathNode rootNode;

    private int nextId = 1;
}
