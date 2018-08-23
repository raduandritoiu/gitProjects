package treehouse.db;

import java.util.*;
import java.util.concurrent.*;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.Service.State;
import com.google.common.util.concurrent.Service;

import treehouse.db.event.*;
import treehouse.event.*;

/**
  * A Database is a collection of Tables, Archives and Histories.
  */
public abstract class Database extends AbstractIdleService
{
    /**
      * Start the database
      */
    public final void startUp() 
    {
        doOpen();

        for (Table t : tables.values())
            t.open();

        for (Archive t : archives.values())
            t.open();
    }

    /**
      * Stop the database.
      */
    public final void shutDown() 
    {
        doClose();

        for (Table t : tables.values())
            t.close();

        for (Archive t : archives.values())
            t.close();
    }

    /**
      * Implementation hook for startUp().
      */
    protected abstract void doOpen(); 

    /**
      * Implementation hook for shutDown().
      */
    protected abstract void doClose(); 

////////////////////////////////////////////////////////////////
// Table
////////////////////////////////////////////////////////////////

    /**
      * Add a Table to the database.  
      */
    public final Table addTable(String name)
    {
        checkState(Service.State.NEW);

        assert(!tables.containsKey(name));
        assert(!archives.containsKey(name));

        Table table = doAddTable(name);
        tables.put(name, table);
        return table;
    }

    /**
      * Return the Table having the given name.
      */
    public final Table getTable(String name)
    {
        assert(tables.containsKey(name));
        return tables.get(name);
    }

    /**
      * Return all of the Tables.
      */
    public final List<Table> getTables()
    {
       return new ImmutableList.Builder<Table>()
           .addAll(tables.values())
           .build();
    }

    /**
      * Implementation hook for addTable().
      */
    protected abstract Table doAddTable(String name);

////////////////////////////////////////////////////////////////
// Archive
////////////////////////////////////////////////////////////////

    /**
      * Add a Archive to the database.  
      */
    public final Archive addArchive(Table parent, String name)
    {
        checkState(Service.State.NEW);

        assert(!tables.containsKey(name));
        assert(!archives.containsKey(name));

        Archive archive = doAddArchive(parent, name);
        archives.put(name, archive);
        return archive;
    }

    /**
      * Return the Archive having the given name.
      */
    public final Archive getArchive(String name)
    {
        assert(archives.containsKey(name));
        return archives.get(name);
    }

    /**
      * Return all of the Archives.
      */
    public final List<Archive> getArchives()
    {
       return new ImmutableList.Builder<Archive>()
           .addAll(archives.values())
           .build();
    }

    /**
      * Implementation hook for addArchive().
      */
    protected abstract Archive doAddArchive(Table parent, String name);

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    private void checkState(Service.State expected)
    {
        if (!state().equals(expected))
            throw new IllegalStateException(
                "Invalid state of " + state() + ", expected " + expected);
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private Map<String,Table> tables = new TreeMap();
    private Map<String,Archive> archives = new TreeMap();
}
