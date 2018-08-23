package treehouse.db.jdbc.postgres;

import java.sql.*;
import java.util.*;

import org.projecthaystack.*;

import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.db.*;

class DisLookupException extends RuntimeException 
{
    DisLookupException(String name, HRef id)
    {
        super("Cannot look up dis for " + id + " in table " + name);
    }
}
