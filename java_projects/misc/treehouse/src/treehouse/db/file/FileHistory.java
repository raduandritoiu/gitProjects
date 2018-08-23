package treehouse.db.file;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.util.concurrent.Striped;
import org.projecthaystack.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import treehouse.db.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;

public class FileHistory extends History
{
    public FileHistory(
        Database db, 
        String name,
        String baseDir,
        int numRamChunks)
    {
        super(db, name);
//
//        this.baseDir = baseDir;
//        this.numRamChunks = numRamChunks;
//
//        this.cache = CacheBuilder.newBuilder()
//            .maximumSize(numRamChunks)
//            .removalListener(new RemovalListener<Chunk,HisEntries>() {
//                public void onRemoval(RemovalNotification<Chunk,HisEntries> notification) {
//                    LOG.info("Forget Chunk " + notification.getKey());
//                }})
//            .build(new ChunkCacheLoader());
    }

    /**
      * Implementation hook for open().
      */
    protected void doOpen()
    {
    }

    /**
      * Implementation hook for close().
      */
    protected void doClose()
    {
    }

    /**
      * Read history time-series data for given identifier and time range. The
      * entries returned are exclusive of start time and inclusive of end time.
      */
    protected HisCursor doHisRead(HRef id, HDateTimeRange range) throws Exception
    {
        throw new IllegalStateException();
//        if (!range.start.tz.equals(range.end.tz))
//            throw new IllegalStateException(range + " has mismatched timezones.");
//
//        HTimeZone tz = range.start.tz;
//
//        Chunk start = Chunk.make(id, range.start);
//        Chunk end   = Chunk.make(id, range.end);
//
////        if (start.equals(end))
////        {
////            HisEntries entries = cache.get(start);
////        }
//
//        throw new IllegalStateException();
    }

    /**
      * Write a set of history time-series data for the given identifier.
      * If duplicate or out-of-order entries are inserted then they 
      * will be gracefully merged.
      */
    protected void doHisWrite(HRef id, Iterator<HisItem> items) throws Exception
    {
        throw new IllegalStateException();
//        Map<Chunk,HisEntries> chunked = new TreeMap<>();
//
//        // divide up the entries into their proper chunks
//        while (items.hasNext())
//        {
//            HisItem item = items.next();
//            Chunk chunk = Chunk.make(id, item.ts);
//
//            HisEntries entries = chunked.get(chunk);
//            if (entries == null)
//                chunked.put(chunk, entries = new HisEntries());
//
//            entries.put(new HisEntry(item.ts.millis(), item.obj));
//        }
//
//        // append new entries to disk, and put them in the cache too
//        for (Map.Entry<Chunk,HisEntries> e : chunked.entrySet())
//        {
//            Chunk chunk = e.getKey();
//            HisEntries newEntries = e.getValue();
//
//            ReadWriteLock lock = chunkLocks.get(chunk);
//            lock.writeLock().lock();
//            try 
//            {
//                // read old data before appending new data
//                HisEntries oldEntries = cache.get(chunk);
//
//                // append the new entries to disk
//                appendToChunk(chunk, newEntries);
//
//                // add the new entries to the cache
//                oldEntries.putAll(newEntries.entries());
//            } 
//            finally 
//            {
//                lock.writeLock().unlock();
//            }
//        }
    }

//////////////////////////////////////////////////////////////////
//// private
//////////////////////////////////////////////////////////////////
//
//    class ChunkCacheLoader extends CacheLoader<Chunk,HisEntries>
//    {
//        public HisEntries load(Chunk chunk) throws Exception 
//        {
//            ReadWriteLock lock = chunkLocks.get(chunk);
//            lock.readLock().lock();
//            try 
//            {
//                HisEntries entries = new HisEntries();
//
//                Path path = Paths.get(baseDir, chunk.getFilePath());
//                LOG.info("Load Chunk " + chunk + ", " + path.toFile().exists());
//
//                if (path.toFile().exists())
//                {
//                    byte[] bytes = Files.readAllBytes(path);
//                    ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
//                    DataInputStream din = new DataInputStream(bin);
//
//                    while (bin.available() > 0)
//                        entries.put(
//                            new HisEntry(
//                                din.readLong(),
//                                JsonCodec.decodeBinary(din)));
//                }
//
//                return entries;
//            } 
//            finally 
//            {
//                lock.readLock().unlock();
//            }
//        }
//    }
//
//    /**
//      * append a chunk to disk
//      */
//    private void appendToChunk(Chunk chunk, HisEntries newEntries) throws Exception
//    {
//        LOG.info("Append Chunk " + chunk);
//
//        // encode all the entries into a byte array
//        ByteArrayOutputStream bout = new ByteArrayOutputStream();
//        DataOutputStream dout = new DataOutputStream(bout);
//
//        Iterator<HisEntry> itr = newEntries.entries();
//        while (itr.hasNext())
//        {
//            HisEntry e = itr.next();
//            dout.writeLong(e.millis);
//            JsonCodec.encodeBinary(e.obj, dout);
//        } 
//        byte[] bytes = bout.toByteArray();
//
//        // append the bytes to the file
//        Path path = Paths.get(baseDir, chunk.getFilePath());
//        Files.write(
//            path, bytes, 
//            StandardOpenOption.CREATE,
//            StandardOpenOption.WRITE,
//            StandardOpenOption.APPEND);
//    }
//
//////////////////////////////////////////////////////////////////
//// attributes
//////////////////////////////////////////////////////////////////
//
//    static Logger LOG = LoggerFactory.getLogger(FileHistory.class);
//
//    // this seems like enough stripes....
//    private static final int STRIPES = 16;
//
//    private final String baseDir;
//    private final int numRamChunks;
//    private final LoadingCache<Chunk, HisEntries> cache;
//
//    // These locks are used to co-ordinate thread access to chunks.
//    private final Striped<ReadWriteLock> chunkLocks = Striped.readWriteLock(STRIPES);
}
