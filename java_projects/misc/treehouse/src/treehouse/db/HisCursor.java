package treehouse.db;

import java.util.*;

import com.google.common.collect.AbstractIterator;
import org.projecthaystack.*;
import treehouse.haystack.*;

/**
  * A HisCursor is a closeable, unmodifiable Iterator over a sequence of HisItems.
  * <p>
  * You <b><i>must</i></b> call close() on the cursor when you are done
  * with it. The use of a try-with-resources statement is encouraged.
  */
public interface HisCursor 
    extends Iterator<HisItem>, AutoCloseable
{
}
