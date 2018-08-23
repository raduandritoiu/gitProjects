package treehouse.rhino;

import java.util.*;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.NativeJavaObject;

import org.projecthaystack.*;
import treehouse.haystack.*;

public class HDictAdapter extends NativeJavaObject
{
    public HDictAdapter(
        Scriptable scope, 
        HDict dict,
        Class staticType)
    {
        super(scope, dict, staticType);
        this.dict = dict;
    }

    public void delete(String name)
    {
        throw new IllegalStateException();
//        try
//        {
//            dict.remove(name);
//        }
//        catch(RuntimeException e)
//        {
//            Context.throwAsScriptRuntimeEx(e);
//        }
    }

    public Object get(String name, Scriptable start)
    {
        throw new IllegalStateException();
//        Object value = super.get(name, start);
//        if(value != Scriptable.NOT_FOUND)
//        {
//            return value;
//        }
//        value = dict.get(name);
//        if(value == null)
//        {
//            return Scriptable.NOT_FOUND;
//        }
//        Context cx = Context.getCurrentContext();
//        return cx.getWrapFactory().wrap(cx, this, value, null);
    }

    public String getClassName()
    {
        return "HDictAdapter";
    }

    public Object[] getIds()
    {
        throw new IllegalStateException();
//        return dict.keySet().toArray();
    }

    public boolean has(String name, Scriptable start)
    {
        throw new IllegalStateException();
//        return dict.containsKey(name) || super.has(name, start);
    }

    public void put(String name, Scriptable start, Object value)
    {
        throw new IllegalStateException();
//        try
//        {
//            dict.put(name, Context.jsToJava(value,  
//                    ScriptRuntime.ObjectClass));
//        }
//        catch(RuntimeException e)
//        {
//            Context.throwAsScriptRuntimeEx(e);
//        }
    }

    public String toString()
    {
        return dict.toString();
    }

    private HDict dict;
}

