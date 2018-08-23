package treehouse.rhino;

import java.util.*;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaArray;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrapFactory;

import org.projecthaystack.*;
import treehouse.haystack.*;

public class HaystackWrapFactory extends WrapFactory
{
    public HaystackWrapFactory()
    {
        setJavaPrimitiveWrap(false);
    }

    public Object wrap(Context cx, Scriptable scope, Object obj, Class staticType)
    {
        if (obj instanceof HList)
        {
            HList list = (HList) obj;
            Object[] arr = list.toArray();
            return new NativeJavaArray(scope, arr);
        }
        else if (obj instanceof HDict)
        {
            return new HDictAdapter(scope, (HDict) obj, staticType);
        }
        else if (obj instanceof HNum)
        {
            return new Double(((HNum) obj).val);
        }
        else if (obj instanceof HBool)
        {
            return ((HBool) obj).val ? Boolean.TRUE : Boolean.FALSE;
        }
        else if (obj instanceof HVal)
        {
            return ((HVal) obj).toZinc();
        }
        else
        {
            return obj;
        }
    }
}


