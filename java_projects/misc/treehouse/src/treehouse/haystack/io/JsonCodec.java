package treehouse.haystack.io;

import java.io.*;
import java.nio.charset.*;
import java.text.*;
import java.util.*;

import org.projecthaystack.*;
import org.projecthaystack.io.*;

import treehouse.haystack.*;
import treehouse.db.*;

/**
  * JsonCodec handles encoding and decoding from JSON format.
  */
public abstract class JsonCodec
{
    /**
      * Parse a string into an HVal.
      */
    public static HVal parse(String str)
    {
        return parse(
            new ByteArrayInputStream(
                str.getBytes(StandardCharsets.UTF_8)));
    }

    /**
      * Parse an InputStream into an HVal.
      */
    public static HVal parse(InputStream in)
    {
        try
        {
            Parser parser = new Parser(
                new InputStreamReader(
                    in, StandardCharsets.UTF_8));
            return parser.parseJson();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Encode an HVal to a String.
      */
    public static String encode(HVal obj)
    {
        return encode(obj, true);
    }

    /**
      * Encode an HVal to an OutputStream.
      */
    public static void encode(HVal obj, OutputStream out)
    {
        encode(obj, true, out);
    }

    /**
      * Encode a Cursor to an OutputStream.
      */
    public static void encode(Cursor cursor, OutputStream out)
    {
        encode(cursor, true, out);
    }

    /**
      * Encode a HisCursor to an OutputStream.
      */
    public static void encode(HisCursor cursor, OutputStream out)
    {
        encode(cursor, true, out);
    }

    /**
      * Encode an HVal to a String.
      */
    public static String encode(HVal obj, boolean includeDis)
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        encode(obj, includeDis, bout);
        return new String(bout.toByteArray(), StandardCharsets.UTF_8);
    }

    /**
      * Encode an HVal to an OutputStream.
      */
    public static void encode(HVal obj, boolean includeDis, OutputStream out)
    {
        PrintWriter pw = new PrintWriter(
            new OutputStreamWriter(out, StandardCharsets.UTF_8));

        (new Encoder(pw, includeDis)).encValue(obj);
        pw.flush();
        pw.close();
    }

    /**
      * Encode a Cursor to an OutputStream.
      */
    public static void encode(Cursor cursor, boolean includeDis, OutputStream out)
    {
        PrintWriter pw = new PrintWriter(
            new OutputStreamWriter(out, StandardCharsets.UTF_8));
        Encoder enc = new Encoder(pw, includeDis);

        boolean started = false;
        pw.print("[");
        while (cursor.hasNext())
        {
            if (started) pw.print(",");
            else started = true;
            
            enc.encValue(cursor.next());
        }
        pw.print("]");

        pw.flush();
        pw.close();
    }

    /**
      * Encode a HisCursor to an OutputStream.
      */
    public static void encode(HisCursor cursor, boolean includeDis, OutputStream out)
    {
        PrintWriter pw = new PrintWriter(
            new OutputStreamWriter(out, StandardCharsets.UTF_8));
        Encoder enc = new Encoder(pw, includeDis);

        boolean started = false;
        pw.print("[");
        while (cursor.hasNext())
        {
            if (started) pw.print(",");
            else started = true;

            HisItem item = cursor.next();
            HDict asMap = new HDictBuilder()
                .add("ts", item.ts)
                .add("obj", item.obj)
                .toDict();
            
            enc.encValue(asMap);
        }
        pw.print("]");

        pw.flush();
        pw.close();
    }

////////////////////////////////////////////////////////////////
// binary
////////////////////////////////////////////////////////////////

//    /**
//      * Encode an object as JSON Binary into a byte array.
//      */
//    public static byte[] encodeBinary(HVal obj) throws Exception
//    {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        encodeBinary(obj, new DataOutputStream(out));
//        return out.toByteArray();
//    }
//
//    /**
//      * Encode an object as JSON Binary into a DataOutputStream
//      */
//    public static void encodeBinary(HVal obj, DataOutputStream out) throws Exception
//    {
//        throw new IllegalStateException();
//        if (obj == null)
//        {
//            out.writeByte(NULL);
//        }
//        else if (obj instanceof HBool)
//        {
//            HBool b = (HBool) obj;
//            out.writeByte(BOOL);
//            out.writeBoolean(b.val);
//        }
//        else if (obj instanceof HNum)
//        {
//            HNum n = (HNum) obj;
//            out.writeByte(NUM);
//            out.writeDouble(n.val);
//        }
//        else if (obj instanceof HStr)
//        {
//            HStr s = (HStr) obj;
//            out.writeByte(STR);
//            out.writeUTF(s.val);
//        }
//        else if (obj instanceof HMarker)
//        {
//            out.writeByte(MARKER);
//        }
//        else if (obj instanceof HRef)
//        {
//            HRef ref = (HRef) obj;
//            out.writeByte(REF);
//            out.writeUTF(ref.val);
//            out.writeBoolean(ref.dis != null);
//            if (ref.dis != null)
//                out.writeUTF(ref.dis);
//        }
//        else if (obj instanceof HUri)
//        {
//            HUri u = (HUri) obj;
//            out.writeByte(URI);
//            out.writeUTF(u.val);
//        }
//        else if (obj instanceof HDateTime)
//        {
//            HDateTime d = (HDateTime) obj;
//            out.writeByte(DATETIME);
//            out.writeUTF(d.toZinc());
//        }
//        else if (obj instanceof HDate)
//        {
//            HDate d = (HDate) obj;
//            out.writeByte(DATE);
//            out.writeUTF(d.toZinc());
//        }
//        else if (obj instanceof HTime)
//        {
//            HTime d = (HTime) obj;
//            out.writeByte(TIME);
//            out.writeUTF(d.toZinc());
//        }
//
//        else if (obj instanceof HList)
//        {
//            HList list = (HList) obj;
//            out.writeByte(LIST);
//            out.writeInt(list.size());
//            for (int i = 0; i < list.size(); i++)
//                encodeBinary(list.get(i), out);
//        }
//        else if (obj instanceof HDict)
//        {
//            HDict dict = (HDict) obj;
//            out.writeByte(MAP);
//            out.writeInt(dict.size());
//
//            Iterator it = dict.iterator();
//            while (it.hasNext())
//            {
//                Map.Entry entry = (Map.Entry) it.next();
//                out.writeUTF((String) entry.getKey());
//                encodeBinary((HVal) entry.getValue(), out);
//            }
//        }
//        else
//        {
//            throw new IllegalStateException(
//                "Cannot encode object of type " + obj.getClass().getName());
//        }
//    }
//
//    /**
//      * Decode an object from JSON Binary byte array.
//      */
//    public static HVal decodeBinary(byte[] bytes) throws Exception
//    {
//        return decodeBinary(new DataInputStream(new ByteArrayInputStream(bytes)));
//    }
//
//    /**
//      * Decode an object from JSON Binary DataInputStream
//      */
//    public static HVal decodeBinary(DataInputStream in) throws Exception
//    {
//        throw new IllegalStateException();
//        int type = in.readByte();
//        switch(type)
//        {
//            case NULL: return null;
//
//            case BOOL:
//                return in.readBoolean() ?
//                    HBool.TRUE : HBool.FALSE;
//
//            case NUM: return HNum.make(in.readDouble());
//            case STR: return HStr.make(in.readUTF());
//            case MARKER: return HMarker.VAL;
//
//            case REF: 
//                String val = in.readUTF();
//                return in.readBoolean() ?
//                    HRef.make(val, in.readUTF()) :
//                    HRef.make(val);
//
//            case URI: return HUri.make(in.readUTF());
//
//            case DATETIME: return (HDateTime) new HZincReader(in.readUTF()).readScalar();
//            case DATE:     return (HDate)     new HZincReader(in.readUTF()).readScalar();
//            case TIME:     return (HTime)     new HZincReader(in.readUTF()).readScalar();
//
//            case LIST:
//                int size = in.readInt();
//                HListBuilder lb = new HListBuilder();
//                for (int i = 0; i < size; i++)
//                    lb.add(decodeBinary(in));
//                return lb.toList();
//
//            case MAP:
//                size = in.readInt();
//                HDictBuilder db = new HDictBuilder();
//                for (int i = 0; i < size; i++)
//                    db.add(in.readUTF(), decodeBinary(in));
//                return db.toDict();
//
//            default:
//                throw new IllegalStateException(
//                    "Cannot decode object of type " + type);
//        }
//    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    private static class Encoder
    {
        private Encoder(PrintWriter pw, boolean includeDis)
        {
            this.pw = pw;
            this.includeDis = includeDis;
        }

        private void encValue(HVal obj)
        {
            if (obj == null)
            {
                pw.print("null");
            }
            else if (obj instanceof HBool)
            {
                HBool b = (HBool) obj;
                pw.print(b.val ?  "true" : "false");
            }
            else if (obj instanceof HNum)
            {
                encNum((HNum) obj);
            }
            else if (obj instanceof HStr)
            {
                encString(((HStr) obj).val);
            }
            else if (obj instanceof HMarker)
            {
                pw.print("\"✓\"");
            }
            else if (obj instanceof HRef)
            {
                encRef((HRef) obj);
            }
            else if 
                ((obj instanceof HUri) ||
                 (obj instanceof HDateTime) ||
                 (obj instanceof HDate) ||
                 (obj instanceof HTime))
                {
                    pw.print("\"");
                    pw.print(((HVal) obj).toZinc());
                    pw.print("\"");
                }

            else if (obj instanceof HList)
            {
                encList((HList) obj);
            }
            else if (obj instanceof HDict)
            {
                encMap((HDict) obj);
            }

            else
            {
                throw new IllegalStateException(
                    "Cannot encode HVal of type " + obj.getClass().getName());
            }
        }

        private void encRef(HRef ref)
        {
            pw.print("\"@");
            pw.print(ref.val);

            if (includeDis && ref.dis != null)
            {
                pw.print(" ");
                pw.print(ref.dis);
            }
            pw.print("\"");
        }

        private void encNum(HNum num)
        {
            // NOTE: we ignore units!

            double val = num.val;

            if ((val == Double.POSITIVE_INFINITY) ||
                (val == Double.NEGATIVE_INFINITY) ||
                Double.isNaN(val))
            {
                throw new IllegalStateException(
                    num.toZinc() + " cannot be encoded to json.");
            }

            // don't format fractions
            double abs = val; if (abs < 0) abs = -abs;
            if (abs > 1.0)
                pw.print(NUM_FORMAT.format(val));
            else
                pw.print(Double.toString(val));
        }

        private void encString(String str)
        {
            pw.print("\"");
            escape(str);
            pw.print("\"");
        }

        private void encList(HList list)
        {
            Iterator<HVal> itr = list.iterator();

            boolean started = false;
            pw.print("[");
            while (itr.hasNext())
            {
                if (started) pw.print(",");
                else started = true;

                encValue(itr.next());
            }
            pw.print("]");
        }

        private void encMap(HDict dict)
        {
            boolean started = false;

            pw.print("{");
            Iterator it = dict.iterator();
            while (it.hasNext())
            {
                if (started) pw.print(",");
                else started = true;

                Map.Entry entry = (Map.Entry) it.next();
                encString((String) entry.getKey());
                pw.print(":");
                encValue((HVal) entry.getValue());
            }
            pw.print("}");
        }

        private void escape(String s) 
        {
            int len = s.length();
            for (int i = 0; i < len; i++)
            {
                char ch = s.charAt(i);
                switch(ch)
                {
                    case '"':  pw.print("\\\""); break;
                    case '\\': pw.print("\\\\"); break;
                    case '\b': pw.print("\\b");  break;
                    case '\f': pw.print("\\f");  break;
                    case '\n': pw.print("\\n");  break;
                    case '\r': pw.print("\\r");  break;
                    case '\t': pw.print("\\t");  break;
                    case '/':  pw.print("\\/");  break;

                    default:

                       if ((ch>='\u0000' && ch<='\u001F') || 
                           (ch>='\u007F' && ch<='\u009F') || 
                           (ch>='\u2000' && ch<='\u20FF'))
                       {
                           String h = Integer.toHexString(ch);
                           pw.print("\\u");
                           for(int k = 0; k < 4 - h.length(); k++)
                               pw.print('0');
                           pw.print(h.toUpperCase());
                       }
                       else
                       {
                           pw.print(ch);
                       }
                }
            }
        }

        private boolean includeDis;
        private final PrintWriter pw;
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private static final DecimalFormat NUM_FORMAT = 
        new DecimalFormat("#0.####", new DecimalFormatSymbols(Locale.ENGLISH));

//    private static final int NULL     = 0x00;
//    private static final int BOOL     = 0x01;
//    private static final int NUM      = 0x02;
//    private static final int STR      = 0x03;
//    private static final int MARKER   = 0x04;
//    private static final int REF      = 0x05;
//    private static final int URI      = 0x06;
//    private static final int DATETIME = 0x07;
//    private static final int DATE     = 0x08;
//    private static final int TIME     = 0x09;
//
//    private static final int LIST     = 0x70;
//    private static final int MAP      = 0x71;
}
