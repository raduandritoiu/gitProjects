/* Parser.java */
/* Generated By:JavaCC: Do not edit this line. Parser.java */
package treehouse.haystack.io;

import java.util.*;
import java.io.*;
import java.nio.charset.*;

import org.projecthaystack.*;
import org.projecthaystack.io.*;

import treehouse.haystack.*;

class Parser implements ParserConstants {
    private boolean isEscaped(String str)
    {
        int len = str.length();
        for (int i = 0; i < len; i++)
            if (str.charAt(i) == '\u005c\u005c')
                return true;
        return false;
    }

    private String unescape(String str)
    {
        StringBuffer sb = new StringBuffer();
        int n = 0;
        int len = str.length();
        while (n < len)
        {
            char ch = str.charAt(n++);
            if (ch == '\u005c\u005c')
            {
                ch = str.charAt(n++);

                switch (ch)
                {
                    case 'b':   sb.append('\u005cb'); break;
                    case 'f':   sb.append('\u005cf'); break;
                    case 'n':   sb.append('\u005cn'); break;
                    case 'r':   sb.append('\u005cr'); break;
                    case 't':   sb.append('\u005ct'); break;
                    case '\u005c\u005c':  sb.append('\u005c\u005c'); break;
                    case '/':   sb.append('/');  break;
                    case '"':   sb.append('"');  break;

                    case 'u':
                        int n3 = fromHex(str.charAt(n++));
                        int n2 = fromHex(str.charAt(n++));
                        int n1 = fromHex(str.charAt(n++));
                        int n0 = fromHex(str.charAt(n++));
                        char uc = (char) ((n3 << 12) | (n2 << 8) | (n1 << 4) | (n0));

                        sb.append(uc);
                        break;

                    default:
                        throw new IllegalStateException(
                            "Invalid escape sequence: \u005c\u005c" + (char) ch);
                }
            }
            else
            {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private int fromHex(int c)
    {
      if ('0' <= c && c <= '9') return c - '0';
      if ('a' <= c && c <= 'f') return c - 'a' + 10;
      if ('A' <= c && c <= 'F') return c - 'A' + 10;
      throw new IllegalStateException(
          "Invalid hex char: " + ((char) c));
    }

    private static final HNum NEG_ONE = HNum.make(-1);
    private static final HNum ZERO    = HNum.make(0);
    private static final HNum ONE     = HNum.make(1);
    private static final HNum TWO     = HNum.make(2);
    private static final HNum THREE   = HNum.make(3);
    private static final HNum FOUR    = HNum.make(4);
    private static final HNum FIVE    = HNum.make(5);

    private static final int MARKER = 0x2713;

////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
  final public 
Filter parseFilter() throws ParseException {Filter f;
    f = expr();
    jj_consume_token(0);
{if ("" != null) return f;}
    throw new Error("Missing return statement in function");
  }

  final public Filter expr() throws ParseException {Filter f;
    f = or();
{if ("" != null) return f;}
    throw new Error("Missing return statement in function");
  }

  final public Filter or() throws ParseException {Filter a;
    Filter b;
    a = and();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case OR:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      jj_consume_token(OR);
      b = and();
a = a.or(b);
    }
{if ("" != null) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Filter and() throws ParseException {Filter a;
    Filter b;
    a = not();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case AND:{
        ;
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      jj_consume_token(AND);
      b = not();
a = a.and(b);
    }
{if ("" != null) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Filter not() throws ParseException {boolean not = false;
    Filter f;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      jj_consume_token(NOT);
not = true;
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    f = eq();
{if ("" != null) return not ? Filter.not(f) : f;}
    throw new Error("Missing return statement in function");
  }

  final public Filter eq() throws ParseException {Path p;
    HVal obj;
    Filter f;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case TAG:{
      p = path();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EQ:
      case NE:
      case LT:
      case LE:
      case GT:
      case GE:
      case HAS:{
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case HAS:{
          jj_consume_token(HAS);
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case LBRACE:{
            obj = object();
            break;
            }
          case LBRACKET:{
            obj = array();
            break;
            }
          default:
            jj_la1[3] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
{if ("" != null) return Filter.has(p,obj);}
          break;
          }
        case EQ:{
          jj_consume_token(EQ);
          obj = primitive();
{if ("" != null) return Filter.eq(p,obj);}
          break;
          }
        case NE:{
          jj_consume_token(NE);
          obj = primitive();
{if ("" != null) return Filter.not(Filter.eq(p,obj));}
          break;
          }
        case LT:{
          jj_consume_token(LT);
          obj = primitive();
{if ("" != null) return Filter.lt(p,obj);}
          break;
          }
        case LE:{
          jj_consume_token(LE);
          obj = primitive();
{if ("" != null) return Filter.le(p,obj);}
          break;
          }
        case GT:{
          jj_consume_token(GT);
          obj = primitive();
{if ("" != null) return Filter.gt(p,obj);}
          break;
          }
        case GE:{
          jj_consume_token(GE);
          obj = primitive();
{if ("" != null) return Filter.ge(p,obj);}
          break;
          }
        default:
          jj_la1[4] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
        }
      default:
        jj_la1[5] = jj_gen;
        ;
      }
{if ("" != null) return Filter.contains(p);}
      break;
      }
    case LPAREN:{
      jj_consume_token(LPAREN);
      f = expr();
      jj_consume_token(RPAREN);
{if ("" != null) return f;}
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Path path() throws ParseException {List<String> tags = new ArrayList<String>();
    Token t;
    label_3:
    while (true) {
      if (jj_2_1(2)) {
        ;
      } else {
        break label_3;
      }
      t = jj_consume_token(TAG);
tags.add(t.image);
      jj_consume_token(ARROW);
    }
    t = jj_consume_token(TAG);
tags.add(t.image);
{if ("" != null) return new Path(tags.toArray(new String[tags.size()]));}
    throw new Error("Missing return statement in function");
  }

////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
  final public 
HVal parseJson() throws ParseException {HVal obj;
    obj = value();
    jj_consume_token(0);
{if ("" != null) return obj;}
    throw new Error("Missing return statement in function");
  }

  final public HVal object() throws ParseException {HDictBuilder hdb = new HDictBuilder();
    HVal val;
    jj_consume_token(LBRACE);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case STRING:{
      pair(hdb);
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[7] = jj_gen;
          break label_4;
        }
        jj_consume_token(COMMA);
        pair(hdb);
      }
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      ;
    }
    jj_consume_token(RBRACE);
{if ("" != null) return hdb.toDict();}
    throw new Error("Missing return statement in function");
  }

  final public void pair(HDictBuilder hdb) throws ParseException {Token t;
    HVal val;
    t = jj_consume_token(STRING);
    jj_consume_token(COLON);
    val = value();
int len = t.image.length();
        String key = t.image.substring(1, len-1);
        hdb.add(key, val);
  }

  final public HVal array() throws ParseException {HListBuilder hlb = new HListBuilder();
    HVal elem;
    jj_consume_token(LBRACKET);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case TRUE:
    case FALSE:
    case NULL:
    case LBRACE:
    case LBRACKET:
    case INTEGER:
    case FLOAT:
    case DATE_TIME:
    case DATE:
    case TIME:
    case STRING:{
      elem = value();
hlb.add(elem);
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[9] = jj_gen;
          break label_5;
        }
        jj_consume_token(COMMA);
        elem = value();
hlb.add(elem);
      }
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      ;
    }
    jj_consume_token(RBRACKET);
{if ("" != null) return hlb.toList();}
    throw new Error("Missing return statement in function");
  }

  final public HVal value() throws ParseException {HVal obj;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LBRACE:{
      obj = object();
      break;
      }
    case LBRACKET:{
      obj = array();
      break;
      }
    case TRUE:
    case FALSE:
    case NULL:
    case INTEGER:
    case FLOAT:
    case DATE_TIME:
    case DATE:
    case TIME:
    case STRING:{
      obj = primitive();
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return obj;}
    throw new Error("Missing return statement in function");
  }

  final public HVal primitive() throws ParseException {Token t;
    HVal obj;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NULL:{
      jj_consume_token(NULL);
obj = null;
      break;
      }
    case TRUE:{
      jj_consume_token(TRUE);
obj = HBool.TRUE;
      break;
      }
    case FALSE:{
      jj_consume_token(FALSE);
obj = HBool.FALSE;
      break;
      }
    case DATE_TIME:{
      t = jj_consume_token(DATE_TIME);
int len = t.image.length();
            String str = t.image.substring(1,len-1);
            obj = HDateTime.make(str);
      break;
      }
    case DATE:{
      t = jj_consume_token(DATE);
int len = t.image.length();
            String str = t.image.substring(1,len-1);
            obj = HDate.make(str);
      break;
      }
    case TIME:{
      t = jj_consume_token(TIME);
int len = t.image.length();
            String str = t.image.substring(1,len-1);
            obj = HTime.make(str);
      break;
      }
    case STRING:{
      t = jj_consume_token(STRING);
int len = t.image.length();
            String str = t.image.substring(1,len-1);
            if (isEscaped(str)) str = unescape(str);

            // marker
            if ((str.length() == 1) && (str.charAt(0) == MARKER))
            {
                obj = HMarker.VAL;
            }
            // ref
            else if (str.startsWith("@"))
            {
                int n = str.indexOf(" ");

                // there is no 'dis'
                if (n == -1)
                    obj = HRef.make(str.substring(1));
                // there is a 'dis'
                else
                    obj = HRef.make(str.substring(1,n), str.substring(n+1));
            }
            // uri
            else if (str.startsWith("`") && str.endsWith("`"))
            {
                obj = HUri.make(str.substring(1,str.length()-1));
            }
            // str
            else
            {
                obj = HStr.make(str);
            }
      break;
      }
    case INTEGER:{
      t = jj_consume_token(INTEGER);
if (t.image.length() < 10)
            {
                int n = Integer.parseInt(t.image);
                switch (n)
                {
                    case -1: obj = NEG_ONE; break;
                    case  0: obj = ZERO;    break;
                    case  1: obj = ONE;     break;
                    case  2: obj = TWO;     break;
                    case  3: obj = THREE;   break;
                    case  4: obj = FOUR;    break;
                    case  5: obj = FIVE;    break;

                    default: obj = HNum.make(n);
                }
            }
            else
            {
                obj = HNum.make(Long.parseLong(t.image));
            }
      break;
      }
    case FLOAT:{
      t = jj_consume_token(FLOAT);
obj = HNum.make(Double.valueOf(t.image));
      break;
      }
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return obj;}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_3_1()
 {
    if (jj_scan_token(TAG)) return true;
    if (jj_scan_token(ARROW)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[13];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x0,0x0,0x0,0xa00,0xf0000000,0xf0000000,0x4000000,0x2000,0x1000000,0x2000,0x1760bc0,0x1760bc0,0x17601c0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x20,0x10,0x8,0x0,0x7,0x7,0x40,0x0,0x0,0x0,0x0,0x0,0x0,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[39];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 13; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 39; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
