package treehouse.db;

import java.util.*;

import org.projecthaystack.*;
import treehouse.haystack.*;

/**
  * A Cursor is a closeable, unmodifiable Iterator over a sequence of HMaps.
  * <p>
  * You <b><i>must</i></b> call close() on the cursor when you are done
  * with it. The use of a try-with-resources statement is encouraged.
  */
public interface Cursor 
    extends Iterator<HDict>, AutoCloseable
{
}
