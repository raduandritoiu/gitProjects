package treehouse.test;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.concurrent.*;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;
import org.mozilla.javascript.tools.shell.Global;

import org.projecthaystack.*;
import org.projecthaystack.client.*;
import org.projecthaystack.io.*;

import treehouse.db.*;
import treehouse.db.jdbc.postgres.*;
import treehouse.event.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.rhino.*;
import treehouse.util.*;

public abstract class TreehouseTest
{
    static void verify(boolean flag)
    {
        if (!flag) 
            throw new IllegalStateException();
    }

    static void verifyEq(Object a, Object b)
    {
        if (a == null && b == null) return;

        if (a == null && b != null) throw new IllegalStateException();
        if (a != null && b == null) throw new IllegalStateException();

        if (a != null && b != null && !a.equals(b)) throw new IllegalStateException();
    }

////////////////////////////////////////////////////////////////
// json
////////////////////////////////////////////////////////////////

//    private static void fetchDemoData() throws Exception
//    {
//        HClient client = HClient.open(
//            "http://localhost:8080/api/demo/",
//            "su",
//            "abcd1234");
//
//        String filter = "id";
//        int limit = Integer.MAX_VALUE;
//
//        HGridBuilder b = new HGridBuilder();
//        b.addCol("filter");
//        b.addCol("limit");
//        b.addRow(new HVal[] { HStr.make(filter), HNum.make(limit) });
//        HGrid req = b.toGrid();
//
//        String reqStr = HZincWriter.gridToString(req);
//        String response = client.postString("read", reqStr);
//
//        JsonCodec.encode(JsonCodec.parse(response));
//
//        PrintWriter out = new PrintWriter(
//            "demo.js", 
//            StandardCharsets.UTF_8.name());
//
//        out.print(response);
//        out.flush();
//        out.close();
//    }

    private static void roundTripJson(String str) throws Exception
    {
        roundTripJson(str, str);
    }

    private static void roundTripJson(String strFrom, String strTo) throws Exception
    {
        // string
        HVal obj = JsonCodec.parse(strFrom);
        String enc = JsonCodec.encode(obj);

        //if (obj == null)
        //    System.out.println("roundJson: '" + strFrom + "', null");
        //else
        //    System.out.println("roundJson: '" + strFrom + "', " + obj.getClass().getName());

        if (!strTo.equals(enc)) throw new IllegalStateException(
            strTo + " ::: " + enc);

//        // binary
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        JsonCodec.encodeBinary(obj, new DataOutputStream(out));
//
//        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
//        Object dec = JsonCodec.decodeBinary(new DataInputStream(in));
//
//        verifyEq(obj, dec);
    }

    private static void testJson() throws Exception
    {
        System.out.println("testJson");

        roundTripJson("null");
        roundTripJson("true");
        roundTripJson("false");

        roundTripJson("1", "1.0");
        roundTripJson("0", "0.0");
        roundTripJson("-1", "-1.0");
        roundTripJson("-2");
        roundTripJson("6");
        roundTripJson("9999999999999");
        roundTripJson("123.456");
        roundTripJson("1.23E12", "1230000000000");
        roundTripJson("123.456e-7", "1.23456E-5");

        roundTripJson("\"abc\"");
        roundTripJson("\"abc\\ndef\"");
        roundTripJson("\"a\\rbc\\nd\\\"ef\"");
        roundTripJson("\"\\u20FF\"");

        // Marker
        verify("✓".equals("\u2713"));
        verify(JsonCodec.encode(HMarker.VAL).equals("\"✓\""));
        verify(HMarker.VAL.equals(JsonCodec.parse("\"✓\"")));
        roundTripJson("\"✓\"");

        // Ref, including dis
        roundTripJson("\"@abc\"");
        roundTripJson("\"@1a169900-2ac0a318 Floor 1\"");

        // Uri
        roundTripJson("\"`FIN/Assets/userLib/Images/windowshadow.png`\"");

        // Date
        roundTripJson("\"2014-10-28T22:15:40.252Z UTC\"");
        roundTripJson("\"2014-10-28T22:15:40.252+01:23 Los_Angeles\"");
        roundTripJson("\"2014-10-28\"");
        roundTripJson("\"22:15:40.252\"");
        roundTripJson("\"03:00:00\"");
        roundTripJson("\"2014-01-12T03:00:00-05:00 New_York\"");

        // list
        roundTripJson("[]");
        roundTripJson("[3]");
        roundTripJson("[3,2]");
        roundTripJson("[3,2,\"abc\",true,null,[]]");

        // main
        roundTripJson("{}");
        roundTripJson("{\"a\":7}");
        roundTripJson("{\"b\":2,\"c\":3,\"a\":7}");
        roundTripJson("{\"b\":2,\"c\":[7,2,3,{\"z\":4}],\"a\":{}}");
        roundTripJson("{\"a\":\"✓\"}");

//        // TODO units
//
//        // encode/decode demo.js
//        System.out.println("*** demo.js ***");
//        JsonCodec.encode(JsonCodec.parse(new FileInputStream("demo.js")));
    }

////////////////////////////////////////////////////////////////
// filter
////////////////////////////////////////////////////////////////

    private static void roundTripFilter(String from, String to)
    {
//System.out.println("roundTripFilter: '" + from + "'");

        Filter f = FilterParser.parse(from);
        String enc = f.toString();

        if (!to.equals(enc)) throw new IllegalStateException(
            to + " ::: " + enc);
    }

    private static void roundTripFilter(String str)
    {
        roundTripFilter(str, str);
    }

    private static void testFilter()
    {
        System.out.println("testFilter");

        roundTripFilter("abc");
        roundTripFilter("a->b");
        roundTripFilter("a->b->c->d");

        roundTripFilter("not a", "(not a)");

        roundTripFilter("a == 3", "(a == 3)");
        roundTripFilter("(b has [3,2])");
        roundTripFilter("(c123 has {\"z\":[3,2,{}]})");
        roundTripFilter("z != \"abc\"", "(not (z == \"abc\"))");

        roundTripFilter("(a and b)");
        roundTripFilter(
            "(a == true or b has {} and c and d->e == null) and z",
            "(((a == true) or (((b has {}) and c) and (d->e == null))) and z)");

        roundTripFilter("(a < 3)");
        roundTripFilter("(a > 3)");
        roundTripFilter("(a <= 3)");
        roundTripFilter("(a >= 3)");
    }

//////////////////////////////////////////////////////////////////
//// postgres
//////////////////////////////////////////////////////////////////
//
//    private static void doArchiveRead(Archive archive, HDateTimeRange range, Filter filter)
//    throws Exception
//    {
//        try (HisCursor cursor = archive.archiveRead(range, filter))
//        {
//            HListBuilder lb = new HListBuilder();
//            while (cursor.hasNext())
//            {
//                HisItem item = cursor.next();
//                lb.add(new HDictBuilder()
//                    .add("ts", item.ts)
//                    .add("obj", item.obj)
//                    .toDict());
//            }
//            System.out.println("doArchiveRead: " + range + ", " + filter + ", " + lb.toDict().size());
//        }
//    }
//
//    private static void testArchive() throws Exception
//    {
//        PostgresDatabase db = new PostgresDatabase(
//            "localhost", 5432, "treehouse",
//            "treehouse_user", "treehouse_user");
//
//        Table table = db.addTable("aaa");
//        Archive archive = db.addArchive(table, "ccc");
//        db.startUp();
//
//        HDict[] records = new HDict[] { 
//            parseMap("{'id':'@foo0', 'bar':'@bar0', 'a':'x'}"),
//            parseMap("{'id':'@foo1', 'bar':'@bar0', 'b':'x'}"),
//            parseMap("{'id':'@foo2', 'bar':'@bar1', 'a':'x'}"),
//            parseMap("{'id':'@foo3', 'bar':'@bar1', 'b':'x'}"),
//            parseMap("{'id':'@bar0', 'bar':'@bar0', 'c':'x'}"),
//            parseMap("{'id':'@bar1', 'bar':'@bar0', 'd':'x'}"),
//        };
//        table.batchInsert(Arrays.<HDict>asList(records).iterator());
//
//        List<HisItem> items = new ArrayList<>();
//        for (int day = 1; day < 29; day++)
//        {
//            HDate date = HDate.make(2014, 1, day);
//            for (int hour = 0; hour < 24; hour++)
//            {
//                HTime time = HTime.make(hour, 0, 0);
//                HDateTime ts = HDateTime.make(date, time, HTimeZone.DEFAULT);
//                HNum num = HNum.make(day*hour);
//                items.add(new HisItem(ts, num));
//
//                time = HTime.make(hour, 1, 0);
//                ts = HDateTime.make(date, time, HTimeZone.DEFAULT);
//                HDict rec = new HDictBuilder()
//                    .add("foo", HRef.make("foo" + ((day*hour) % 4))) 
//                    .add("num", num)
//                    .toDict();
//                items.add(new HisItem(ts, rec));
//            }
//        }
//        archive.archiveWrite(items.iterator());
//
//        doArchiveRead(
//            archive,
//            HDateTimeRange.make(
//                HDateTime.make(HDate.make(2014, 1, 12), HTime.make( 3, 0, 0), HTimeZone.DEFAULT),
//                HDateTime.make(HDate.make(2014, 1, 22), HTime.make(14, 0, 0), HTimeZone.DEFAULT)),
//            null);
//
//        doArchiveRead(
//            archive,
//            null,
//            FilterParser.parse("foo"));
//
//        doArchiveRead(
//            archive,
//            HDateTimeRange.make(
//                HDateTime.make(HDate.make(2014, 1, 12), HTime.make( 3, 0, 0), HTimeZone.DEFAULT),
//                HDateTime.make(HDate.make(2014, 1, 22), HTime.make(14, 0, 0), HTimeZone.DEFAULT)),
//            FilterParser.parse("foo"));
//
//        doArchiveRead(
//            archive,
//            null,
//            FilterParser.parse("foo->bar"));
//
//        doArchiveRead(
//            archive,
//            HDateTimeRange.make(
//                HDateTime.make(HDate.make(2014, 1, 12), HTime.make( 3, 0, 0), HTimeZone.DEFAULT),
//                HDateTime.make(HDate.make(2014, 1, 22), HTime.make(14, 0, 0), HTimeZone.DEFAULT)),
//            FilterParser.parse("foo->bar->c"));
//
//        db.shutDown();
//    }
//
//    private static HDict parseMap(String str)
//    {
//        return (HDict) JsonCodec.parse(str.replace("'", "\""));
//    }

    private static void testDemoInsert() throws Exception
    {
        System.out.println("testDemoInsert");

        HDict demo = (HDict) JsonCodec.parse(new FileInputStream("test/demo.js"));
        HList rows = demo.getList("rows");

        PostgresDatabase db = new PostgresDatabase(
            "localhost", 5432, "treehouse",
            "treehouse_user", "treehouse_user");
        Table table = db.addTable("demo");
        db.startUp();

        long begin = System.currentTimeMillis();

        // insert some records
        for (int i = 0; i < rows.size(); i++)
        {
            HDict record = (HDict) rows.get(i);
            HRef id = record.id();
            HDictBuilder rb = new HDictBuilder().add(record);

            // move id.dis into dis field
            if (id.dis != null)
            {
                rb.add("id", HRef.make(id.val));
                rb.add("dis", HStr.make(id.dis));
            }

            Iterator itr = record.iterator();
            while (itr.hasNext())
            {
                Map.Entry entry = (Map.Entry) itr.next();
                String tag = (String) entry.getKey();
                HVal obj = (HVal) entry.getValue();

                // get rid of 'dis' on all hrefs
                if (!tag.equals("id") && (obj instanceof HRef))
                {
                    HRef ref = (HRef) obj;
                    if (ref.dis != null)
                        rb.add(tag, HRef.make(ref.val));
                }
                // parse embedded grids
                else if (obj instanceof HStr)
                {
                    HStr str = (HStr) obj;
                    if (str.val.startsWith("ver:\"2.0\""))
                    {
                        HGrid grid = (new HZincReader(str.val + "\n")).readGrid();
                        HListBuilder lb = new HListBuilder();
                        for (int r = 0; r < grid.numRows(); r++)
                            lb.add(new HDictBuilder().add(grid.row(r)).toDict());
                        rb.add(tag, lb.toList());
                    }
                }
            }

            table.insert(rb.toDict());
        }

        long end = System.currentTimeMillis();
        long elapsed = end - begin;
        System.out.println("---- Inserted " + rows.size() + " records in " + elapsed + "ms.");

        db.shutDown();
    }

//    private static void archiveRead(Archive archive, HDateTimeRange range, Filter filter)
//    throws Exception
//    {
//        try (HisCursor cursor = archive.archiveRead(range, filter))
//        {
//            HListBuilder lb = new HListBuilder();
//            while (cursor.hasNext())
//            {
//                HisItem item = cursor.next();
//                lb.add(new HDictBuilder()
//                    .add("ts", item.ts)
//                    .add("obj", item.obj)
//                    .toDict());
//            }
//            System.out.println("archiveRead: " + range + ", " + filter + ", " + lb.toList().size());
//        }
//    }
//
////    private static String randomTag(Random rnd)
////    {
////        char ch = (char) ('a' + rnd.nextInt(26));
////        return new String(new char[] { ch, ch, ch });
////    }
////
////    private static Map<String,HVal> randomTags(Random rnd)
////    {
////        int num = rnd.nextInt(5) + 1;
////
////        Map<String,HVal> map = new HashMap<>();
////        for (int i = 0; i < num; i++)
////        {
////            String tag = randomTag(rnd);
////            while (map.containsKey(tag))
////                tag = randomTag(rnd);
////            map.add(tag, HMarker.VAL);
////        }
////        return map;
////    }
////
////    private static int generateEquipAlarms(Random rnd, Archive alarms, HRef equipId) throws Exception
////    {
////        int numAlarms = 600;
////
////        Set<HDateTime> timestamps = new TreeSet<>();
////        for (int i = 0; i < numAlarms; i++)
////        {
////            int year = 2014;
////            int month = rnd.nextInt(12) + 1; 
////            int md = HDate.daysInMonth(year, month);
////            int day = rnd.nextInt(md) + 1; 
////            HDate date = HDate.make(year, month, day);
////
////            int hour = rnd.nextInt(24);
////            int min  = rnd.nextInt(60);
////            int sec  = rnd.nextInt(60);
////            int ms   = rnd.nextInt(1000);
////            HTime time = HTime.make(hour, min, sec, ms);
////
////            timestamps.add(HDateTime.make(date, time, HTimeZone.DEFAULT));
////        }
////
////        List<HisItem> items = new ArrayList<>(timestamps.size());
////        for (HDateTime ts : timestamps)
////        {
////            HDict rec = new HDictBuilder()
////                .add("equipRef", equipId)
////                .putAll(randomTags(rnd))
////                .toDict();
////            items.add(new HisItem(ts, rec));
////        }
////        alarms.archiveWrite(items.iterator());
////
////        return numAlarms;
////    }
////
////    private static void testBig() throws Exception
////    {
////        System.out.println("testBig");
////
////        long begin = System.currentTimeMillis();
////        System.out.println("SEED: " + begin);
////        Random rnd = new Random(begin);
////
////        Database db = new PostgresDatabase(
////            "localhost", 5432, "treehouse",
////            "treehouse_user", "treehouse_user");
////        Table table = db.addTable("foo");
////        Archive alarms = db.addArchive(table, "alarms");
////        db.startUp();
////
////        int num = 0;
////
////        int numSites = 60;
////        int numEquips = 100;
////        int numPoints = 50;
//////        int numSites = 6;
//////        int numEquips = 10;
//////        int numPoints = 5;
////
////        List<HDict> siteList = new ArrayList<>();
////        for (int i = 1; i <= numSites; i++)
////        {
////            HRef siteId = HRef.make("S.site" + i);
////            System.out.println(siteId);
////
////            HDict site = new HDictBuilder()
////                .add("id", siteId)
////                .add("site", HMarker.VAL)
////                .putAll(randomTags(rnd))
////                .toDict();
////            num++;
////            siteList.add(site);
////
////            // equips
////            List<HDict> equipList = new ArrayList<>();
////            for (int j = 1; j <= numEquips; j++)
////            {
////                HRef equipId = HRef.make("S.site" + i + ".equip" + j);
////                System.out.println("    " + equipId);
////
////                HDict equip = new HDictBuilder()
////                    .add("id", equipId)
////                    .add("equip", HMarker.VAL)
////                    .add("siteRef", siteId)
////                    .putAll(randomTags(rnd))
////                    .toDict();
////                num++;
////                equipList.add(equip);
////
////                // alarms
////                num += generateEquipAlarms(rnd, alarms, equipId);
////
////                // points
////                List<HDict> pointList = new ArrayList<>();
////                for (int k = 1; k <= numPoints; k++)
////                {
////                    HRef pointId = HRef.make("S.site" + i + ".equip" + j + ".point" + k);
////
////                    HDict point = new HDictBuilder()
////                        .add("id", pointId)
////                        .add("point", HMarker.VAL)
////                        .add("equipRef", equipId)
////                        .add("siteRef", siteId)
////                        .putAll(randomTags(rnd))
////                        .toDict();
////                    num++;
////                    pointList.add(point);
////                }
////                table.batchInsert(pointList.iterator());
////            }
////            table.batchInsert(equipList.iterator());
////        }
////        table.batchInsert(siteList.iterator());
////
////        db.shutDown();
////
////        long elapsed = System.currentTimeMillis() - begin;
////        System.out.println();
////        System.out.println("Inserted " + num + " records in " + elapsed + "ms.");
////    }
//
//////////////////////////////////////////////////////////////////
//// rhino
//////////////////////////////////////////////////////////////////
//
//    /**
//      * loadJsFile
//      */
//    private static void loadJsFile(
//        Context cx, Scriptable scope, String fileName) 
//    throws Exception
//    {
//         Reader reader = new FileReader(fileName);
//         cx.evaluateReader(scope, reader, fileName, 1, null);
//    }
//
////    private static void printEval(Context cx, Scriptable scope, String source) 
////    throws Exception
////    {
////         Object obj = cx.evaluateString(scope, source, "<eval>", 1, null);
////        System.out.println(source + ": " + obj.getClass() + ", " + Context.toString(obj));
////    }
////
////    private static void printGet(Scriptable scope, String name) throws Exception
////    {
////        Object obj = scope.get(name, scope);
////        System.out.println(name + ": " + obj.getClass() + ", " + Context.toString(obj));
////    }
//
//    private static void testRhino() throws Exception
//    {
//        PostgresDatabase db = new PostgresDatabase(
//            "localhost", 5432, "treehouse",
//            "treehouse_user", "treehouse_user");
//        Table table = db.addTable("demo");
//        RhinoTable.table = table;
//
//        db.startUp();
//        Context cx = Context.enter();
//
//        try 
//        {
//            //Scriptable scope = cx.initStandardObjects();
//            Global scope = new Global(cx);
//
//            // set up the WrapFactory for Haystack classes
//            cx.setWrapFactory(new HaystackWrapFactory());
//
//            // define classes
//            ScriptableObject.defineClass(scope, RhinoTable.class);
//
//            // load underscore
//            loadJsFile(cx, scope, "test/underscore.js");
//
//            // enter REPL loop
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            while (true)
//            {
//                System.out.print("> ");
//                String str = br.readLine();
//
//                if (str.startsWith("$quit")) 
//                {
//                    break;
//                }
//                //else if (str.startsWith("$load"))
//                //{
//                //    String fileName = str.substring(str.indexOf(" ") + 1);
//                //    loadJsFile(cx, scope, fileName);
//                //}
//                else
//                {
//                    try
//                    {
//                        Object result = cx.evaluateString(scope, str, "<cmd>", 1, null);
//                        String res = Context.toString(result);
//                        if (!res.equals("undefined")) System.out.println(res);
//                        //System.out.println(res);
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } 
//        finally 
//        {
//            db.shutDown();
//            Context.exit();
//        }
//    }

////////////////////////////////////////////////////////////////
// main
////////////////////////////////////////////////////////////////

    public static void main(String[] args) throws Exception
    {
        //testRhino();

        testJson();
        testFilter();
        TestPsql.testSqlBuilder();
        //TestEventBus.testEvents();

        //testBig();
        testDemoInsert();
        //testArchive();

        System.exit(0);
    }
}
