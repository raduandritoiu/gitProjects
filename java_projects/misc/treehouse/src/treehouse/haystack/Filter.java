//
// Copyright (c) 2011, Brian Frank
// Licensed under the Academic Free License version 3.0
//
// History:
//   04 Oct 2011  Brian Frank  Creation
//   20 Sep 2014  Mike Jarmy   Transmogrified from Project Haystack
//
//
package treehouse.haystack;

import java.util.*;

import org.projecthaystack.*;
import treehouse.haystack.io.*;

public abstract class Filter
{
    /** Package constructor */
    public Filter() {}

//////////////////////////////////////////////////////////////////////////
// Factories
//////////////////////////////////////////////////////////////////////////

    /**
     * Match records which have the specified tag path defined.
     */
    public static Filter contains(Path path) { return new Contains(path); }

    /**
     * Match records which have a tag that is equal to the specified value.
     * If the path is not defined then it is unmatched.
     */
    public static Filter eq(Path path, HVal rhs) { return new Eq(path, rhs); }

    /**
     * Match records which have a tag that is equal to the specified value.
     * If the path is not defined then it is unmatched.
     */
    public static Filter has(Path path, HVal rhs) { return new Has(path, rhs); }

    /**
     * Match records which have a tag that is not equal to the specified value.
     * If the path is not defined then it is unmatched.
     */
    public static Filter lt(Path path, HVal rhs) { return new Lt(path, rhs); }
    public static Filter le(Path path, HVal rhs) { return new Le(path, rhs); }
    public static Filter gt(Path path, HVal rhs) { return new Gt(path, rhs); }
    public static Filter ge(Path path, HVal rhs) { return new Ge(path, rhs); }

    /**
     * Return a query which is the logical-negation of the filter.
     */
    public static Filter not(Filter filter) { return new Not(filter); }

    /**
     * Return a query which is the logical-and of this and that query.
     */
    public Filter and(Filter that) { return new And(this, that); }

    /**
     * Return a query which is the logical-or of this and that query.
     */
    public Filter or(Filter that) { return new Or(this, that); }

//////////////////////////////////////////////////////////////////////////
// Access
//////////////////////////////////////////////////////////////////////////

    /** String encoding */
    public abstract String toString();

    /** Hash code is based on string encoding */
    public final int hashCode() { return toString().hashCode(); }

    /** Equality is based on string encoding */
    public final boolean equals(Object that)
    {
        if (!(that instanceof Filter)) return false;
        return toString().equals(that.toString());
    }

    /** include */
    public abstract boolean include(HDict record, Pather pather);

    /** visitor */
    public abstract void accept(Visitor visitor);

////////////////////////////////////////////////////////////////
// Visitor
////////////////////////////////////////////////////////////////

    public interface Visitor {
        public void visit(And and);
        public void visit(Or or);
        public void visit(Not not);
        public void visit(Contains contains);
        public void visit(Eq eq);
        public void visit(Has has);
        public void visit(Lt lt);
        public void visit(Le le);
        public void visit(Gt gt);
        public void visit(Ge ge);
    }

////////////////////////////////////////////////////////////////
// Pather
////////////////////////////////////////////////////////////////

    /** Pather is a callback interface used to resolve query paths. */
    public interface Pather
    {
      /**
       * Given a string identifier, resolve to an entity's
       *  respresentation or return null.
       */
      public HDict find(HRef id);
    }

//////////////////////////////////////////////////////////////////////////
// PathFilter
//////////////////////////////////////////////////////////////////////////

    /**
      * PathFilter
      */
    public static abstract class PathFilter extends Filter
    {
        public PathFilter(Path path) 
        { 
            super();
            this.path = path; 
        }

        public final boolean include(HDict record, Pather pather)
        {
            HVal val = record.get(path.get(0));
            if (path.size() != 1)
            {
                HDict nt = record;
                for (int i=1; i<path.size(); ++i)
                {
                    if (!(val instanceof HRef)) { val = null; break; }
                    nt = pather.find((HRef)val);
                    if (nt == null) { val = null; break; }
                    val = nt.get(path.get(i));
                }
            }
            return doInclude(val);
        }

        public abstract boolean doInclude(HVal val);

        public final Path path;
    }

    /**
      * Contains
      */
    public static final class Contains extends PathFilter
    {
        public Contains(Path path) { super(path); }

        public String toString() { return path.toString(); }

        public boolean doInclude(HVal val) { return val != null; }

        public void accept(Visitor visitor) { visitor.visit(this); }
    }

    /**
      * Compare
      */
    public static abstract class Compare extends PathFilter
    {
        public Compare(Path path, HVal rhs) 
        { 
            super(path); 
            this.rhs = rhs; 
        }

        public String toString()
        {
            return "(" + path + " " + cmp() + " " + JsonCodec.encode(rhs) + ")";
        }

        public abstract String cmp();

        public final HVal rhs;
    }

    /**
      * Eq
      */
    public static final class Eq extends Compare
    {
        public Eq(Path path, HVal rhs) { super(path, rhs); }
        public final String cmp() { return "=="; }

        public boolean doInclude(HVal val) { return rhs.equals(val); }

        public void accept(Visitor visitor) { visitor.visit(this); }
    }

    /**
      * Has
      */
    public static final class Has extends Compare
    {
        public Has(Path path, HVal rhs) { super(path, rhs); }
        public final String cmp() { return "has"; }

        public boolean doInclude(HVal val) { return rhs.equals(val); }

        public void accept(Visitor visitor) { visitor.visit(this); }
    }

    /**
      * Lt
      */
    public static final class Lt extends Compare
    {
        public Lt(Path path, HVal rhs) { super(path, rhs); }
        public final String cmp() { return "<"; }

        public boolean doInclude(HVal val) 
        { 
            if ((val instanceof Comparable) && (rhs instanceof Comparable))
                return ((Comparable) val).compareTo((Comparable) rhs) < 0;
            else 
                return false;
        }

        public void accept(Visitor visitor) { visitor.visit(this); }
    }

    /**
      * Le
      */
    public static final class Le extends Compare
    {
        public Le(Path path, HVal rhs) { super(path, rhs); }
        public final String cmp() { return "<="; }

        public boolean doInclude(HVal val) 
        { 
            if ((val instanceof Comparable) && (rhs instanceof Comparable))
                return ((Comparable) val).compareTo((Comparable) rhs) <= 0;
            else 
                return false;
        }

        public void accept(Visitor visitor) { visitor.visit(this); }
    }

    /**
      * Gt
      */
    public static final class Gt extends Compare
    {
        Gt(Path path, HVal rhs) { super(path, rhs); }
        public final String cmp() { return ">"; }

        public boolean doInclude(HVal val) 
        { 
            if ((val instanceof Comparable) && (rhs instanceof Comparable))
                return ((Comparable) val).compareTo((Comparable) rhs) > 0;
            else 
                return false;
        }

        public void accept(Visitor visitor) { visitor.visit(this); }
    }

    /**
      * Ge
      */
    public static final class Ge extends Compare
    {
        public Ge(Path path, HVal rhs) { super(path, rhs); }
        public final String cmp() { return ">="; }

        public boolean doInclude(HVal val) 
        { 
            if ((val instanceof Comparable) && (rhs instanceof Comparable))
                return ((Comparable) val).compareTo((Comparable) rhs) >= 0;
            else 
                return false;
        }

        public void accept(Visitor visitor) { visitor.visit(this); }
    }

//////////////////////////////////////////////////////////////////////////
// Unary
//////////////////////////////////////////////////////////////////////////

    /**
      * Not
      */
    public static final class Not extends Filter
    {
        public Not(Filter filter) { this.filter = filter; }

        public String toString() { return "(not " + filter + ")"; }

        public boolean include(HDict record, Pather pather) {
            return !filter.include(record, pather);
        }

        public void accept(Visitor visitor) { visitor.visit(this); }

        public final Filter filter;
    }

//////////////////////////////////////////////////////////////////////////
// Compound
//////////////////////////////////////////////////////////////////////////

    /**
      * Compound
      */
    public static abstract class Compound extends Filter
    {
        public Compound(Filter lhs, Filter rhs, String keyword) 
        { 
            this.lhs = lhs; 
            this.rhs = rhs; 
            this.keyword = keyword;
        }

        public String toString() { return "(" + lhs + " " + keyword + " " + rhs + ")"; }

        public final Filter lhs;
        public final Filter rhs;
        public final String keyword;
    }

    /**
      * And
      */
    public static final class And extends Compound
    {
        public And(Filter lhs, Filter rhs) { super(lhs, rhs, "and"); }

        public boolean include(HDict record, Pather pather) {
            return lhs.include(record, pather) && rhs.include(record, pather);
        }

        public void accept(Visitor visitor) { visitor.visit(this); }
    }

    /**
      * Or
      */
    public static final class Or extends Compound
    {
        public Or(Filter lhs, Filter rhs) { super(lhs, rhs, "or"); }

        public boolean include(HDict record, Pather pather) {
            return lhs.include(record, pather) || rhs.include(record, pather);
        }

        public void accept(Visitor visitor) { visitor.visit(this); }
    }
}
