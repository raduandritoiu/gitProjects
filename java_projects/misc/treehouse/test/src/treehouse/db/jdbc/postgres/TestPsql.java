package treehouse.db.jdbc.postgres;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.concurrent.*;

import org.projecthaystack.*;
import org.projecthaystack.client.*;
import org.projecthaystack.io.*;

import treehouse.db.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.test.*;

public abstract class TestPsql
{
    private static void verifySql(
        String strFilter, int skip, int limit, String expected)
    {
        Filter filter = FilterParser.parse(strFilter);
        String sql = SqlBuilder.buildSelect("foo", filter, "r0.doc", skip, limit);

//        System.out.println("---------------------------------------------------");
//        System.out.println(filter);
//        System.out.println(expected);
//        System.out.println(sql);

        if (expected == null)
            System.out.println("************************ TODO ************************");
        else
            if (!sql.equals(expected)) throw new IllegalStateException();
    }

    private static void verifySql(HDateTimeRange range, String strFilter, String expected)
    {
        verifyArchiveSql(range, strFilter, -1, -1, expected);
    }

    private static void verifySql(String strFilter, String expected)
    {
        verifySql(strFilter, -1, -1, expected);
    }

    private static void verifyArchiveSql(
        HDateTimeRange range, String strFilter, int skip, int limit, String expected)
    {
        Filter filter = (strFilter == null) ? null : FilterParser.parse(strFilter);
        String sql = SqlBuilder.buildArchiveSelect("foo", "parent", range, filter, skip, limit);

//        System.out.println("---------------------------------------------------");
//        System.out.println(range);
//        System.out.println(filter);
//        System.out.println(expected);
//        System.out.println(sql);

        if (expected == null)
            System.out.println("************************ TODO ************************");
        else
            if (!sql.equals(expected)) throw new IllegalStateException();
    }

    private static void verifyArchiveSql(HDateTimeRange range, String strFilter, String expected)
    {
        verifyArchiveSql(range, strFilter, -1, -1, expected);
    }

    public static void testSqlBuilder()
    {
        System.out.println("testSqlBuilder");

        verifySql(
            "a", 
            "select r0.doc from foo_recs r0\n" + 
            "where (r0.doc ? 'a');");

        verifySql(
            "a and b", 
            "select r0.doc from foo_recs r0\n" + 
            "where ((r0.doc ? 'a') and (r0.doc ? 'b'));");

        verifySql(
            "a or (not b)", 
            "select r0.doc from foo_recs r0\n" + 
            "where ((r0.doc ? 'a') or (not (r0.doc ? 'b')));");

        verifySql(
            "abc == \"@0\"", 
            "select r0.doc from foo_recs r0\n" + 
            "where (r0.doc @> '{\"abc\":\"@0\"}'::jsonb);");

        verifySql(
            "abc has {\"x\":12}", 
            "select r0.doc from foo_recs r0\n" + 
            "where (r0.doc @> '{\"abc\":{\"x\":12}}'::jsonb);");

        verifySql(
            "abc", 
            "select r0.doc from foo_recs r0\n" + 
            "where (r0.doc ? 'abc');");

        verifySql(
            "abc->a and b", 
            "select r0.doc from foo_recs r0\n" +
            "join foo_refs j1 on j1.fromId = r0.id and j1.tag = 'abc'\n" +
            "join foo_recs r1 on j1.toRef  = r1.id and j1.tag = 'abc'\n" +
            "where ((r1.doc ? 'a') and (r0.doc ? 'b'));");

        verifySql(
            "def->def->d", 
            "select r0.doc from foo_recs r0\n" +
            "join foo_refs j1 on j1.fromId = r0.id and j1.tag = 'def'\n" +
            "join foo_recs r1 on j1.toRef  = r1.id and j1.tag = 'def'\n" +
            "join foo_refs j2 on j2.fromId = r1.id and j2.tag = 'def'\n" +
            "join foo_recs r2 on j2.toRef  = r2.id and j2.tag = 'def'\n" +
            "where (r2.doc ? 'd');");

        verifySql(
            "def->abc->a == \"x\"", 
            "select r0.doc from foo_recs r0\n" +
            "join foo_refs j1 on j1.fromId = r0.id and j1.tag = 'def'\n" +
            "join foo_recs r1 on j1.toRef  = r1.id and j1.tag = 'def'\n" +
            "join foo_refs j2 on j2.fromId = r1.id and j2.tag = 'abc'\n" +
            "join foo_recs r2 on j2.toRef  = r2.id and j2.tag = 'abc'\n" +
            "where (r2.doc @> '{\"a\":\"x\"}'::jsonb);");

        //////////////////////////////////////////////////////////////

        verifyArchiveSql(
            HDateTimeRange.make(
                HDateTime.make(HDate.make(2014, 1, 12), HTime.make( 3, 0, 0), HTimeZone.DEFAULT),
                HDateTime.make(HDate.make(2014, 1, 22), HTime.make(14, 0, 0), HTimeZone.DEFAULT)),
            null,
            "select r0.tsChunk, r0.tsOffset, r0.tz, r0.doc from foo_arch r0\n" +
            "where (r0.tsChunk >= 13895 and r0.tsChunk <= 13904)\n" +
            "order by r0.tsChunk, r0.tsOffset;");

        verifyArchiveSql(
            null,
            "abc",
            "select r0.tsChunk, r0.tsOffset, r0.tz, r0.doc from foo_arch r0\n" +
            "where (r0.doc ? 'abc')\n" +
            "order by r0.tsChunk, r0.tsOffset;");

        verifyArchiveSql(
            HDateTimeRange.make(
                HDateTime.make(HDate.make(2014, 1, 12), HTime.make( 3, 0, 0), HTimeZone.DEFAULT),
                HDateTime.make(HDate.make(2014, 1, 22), HTime.make(14, 0, 0), HTimeZone.DEFAULT)),
            "abc",
            "select r0.tsChunk, r0.tsOffset, r0.tz, r0.doc from foo_arch r0\n" +
            "where (r0.tsChunk >= 13895 and r0.tsChunk <= 13904) and (r0.doc ? 'abc')\n" +
            "order by r0.tsChunk, r0.tsOffset;");

        verifyArchiveSql(
            null,
            "abc->def",
            "select r0.tsChunk, r0.tsOffset, r0.tz, r0.doc from foo_arch r0\n" +
            "join foo_archrefs j1 on j1.fromId = r0.rowid and j1.tag = 'abc'\n" +
            "join parent_recs r1 on j1.toRef  = r1.id and j1.tag = 'abc'\n" +
            "where (r1.doc ? 'def')\n" +
            "order by r0.tsChunk, r0.tsOffset;");

        verifyArchiveSql(
            HDateTimeRange.make(
                HDateTime.make(HDate.make(2014, 1, 12), HTime.make( 3, 0, 0), HTimeZone.DEFAULT),
                HDateTime.make(HDate.make(2014, 1, 22), HTime.make(14, 0, 0), HTimeZone.DEFAULT)),
            "abc->def->c",
            "select r0.tsChunk, r0.tsOffset, r0.tz, r0.doc from foo_arch r0\n" +
            "join foo_archrefs j1 on j1.fromId = r0.rowid and j1.tag = 'abc'\n" +
            "join parent_recs r1 on j1.toRef  = r1.id and j1.tag = 'abc'\n" +
            "join parent_refs j2 on j2.fromId = r1.id and j2.tag = 'def'\n" +
            "join parent_recs r2 on j2.toRef  = r2.id and j2.tag = 'def'\n" +
            "where (r0.tsChunk >= 13895 and r0.tsChunk <= 13904) and (r2.doc ? 'c')\n" +
            "order by r0.tsChunk, r0.tsOffset;");
    }
}
