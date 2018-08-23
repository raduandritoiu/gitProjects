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

import com.google.common.base.Joiner;

/** Path is a simple tag name or a complex path using the "->" separator */
public class Path
{
    public Path(String[] tags)
    {
        this.tags = tags;
    }

    public int size() { return tags.length; }
    public String get(int index) { return tags[index]; }
    public String last() { return tags[tags.length-1]; }

    public String toString() { return Joiner.on("->").join(tags); }

    private String[] tags;
}
