package treehouse.haystack.io;

import java.util.*;
import java.io.*;
import java.nio.charset.*;

import org.projecthaystack.*;

import treehouse.haystack.*;

public class FilterParser 
{
    /**
      * Parse a string into a Filter.
      */
    public static Filter parse(String str)
    {
        return parse(
            new ByteArrayInputStream(
                str.getBytes(StandardCharsets.UTF_8)));
    }

    /**
      * Parse an InputStream into a Filter.
      */
    public static Filter parse(InputStream in)
    {
        try
        {
            Parser parser = new Parser(
                new InputStreamReader(
                    in, StandardCharsets.UTF_8));
            return parser.parseFilter();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    } 

}

