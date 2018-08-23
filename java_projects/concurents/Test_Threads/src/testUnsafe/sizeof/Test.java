package testUnsafe.sizeof;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Hashtable;

import basicUtils.Utils;
import sun.misc.Unsafe;
import sun.misc.VMSupport;
import testUnsafe.MyOwnUnsafe;


public class Test
{
  public static void test()
  {
    try 
    {
      testClass(new BaseA());
      Utils.Print("\n");
      testClass(new ExtendA());
    }
    catch(Exception ex)
    {
      Utils.Print("FUCK!: " + ex.toString());
    }
  }
  
  
  public static void testClass(BaseA obj) throws Exception
  {
    Unsafe unsafe = MyOwnUnsafe.getInstance();
    
    Class cls = obj.getClass();
    Utils.Print("Class: " + cls.getName());
    Hashtable<String, Field> directFields = new Hashtable<>();
    for (Field f : cls.getFields())
    {
      if (directFields.containsKey(f.getName()))
        Utils.Print("*** Direct already has field: " + f.getName());
      directFields.put(f.getName(), f);
    }
    
    Hashtable<String, Field> fieldsName = new Hashtable<>();
    while (cls != Object.class)
    {
      for (Field f : cls.getDeclaredFields())
      {
        if (fieldsName.containsKey(f.getName()))
          Utils.Print("*** All already has field: " + f.getName());
        fieldsName.put(f.getName(), f);
      }
      cls = cls.getSuperclass();
    }
    
    // Print the fields
    Utils.Print("All fields: ");
    Hashtable<Long, Field> fieldsOff = new Hashtable<>();
    long maxOff = 0;
    for (Field f : fieldsName.values())
    {
      boolean st = (f.getModifiers() & Modifier.STATIC) == 0 ;
      long off = -1;
      if (st)
      {
        off = unsafe.objectFieldOffset(f);
        if (off > maxOff)
          maxOff = off;
        fieldsOff.put(new Long(off), f);
        //Utils.Print("    " + f.getName() + "\t\t(" + st + ")   off: " + off);
      }
    }
    
    
    for (long i = 0; i <= maxOff; i++)
    {
      Long l = new Long(i);
      if (fieldsOff.containsKey(l))
      {
        Field f = fieldsOff.get(l);
        Utils.Print("    " + f.getName() + "\t\t off: " + i);
      }
    }
    
    Utils.Print("    [0]: " + unsafe.getByte(obj, 0L));
    Utils.Print("    [1]: " + unsafe.getByte(obj, 1L));
    Utils.Print("    [2]: " + unsafe.getByte(obj, 2L));
    Utils.Print("    [3]: " + unsafe.getByte(obj, 3L));
    Utils.Print("    [4]: " + unsafe.getByte(obj, 4L));
    Utils.Print("    [5]: " + unsafe.getByte(obj, 5L));
    Utils.Print("    [6]: " + unsafe.getByte(obj, 6L));
    Utils.Print("    [7]: " + unsafe.getByte(obj, 7L));
    Utils.Print("    [8]: " + unsafe.getByte(obj, 8L));
    Utils.Print("    [9]: " + unsafe.getByte(obj, 9L));
    Utils.Print("    [10]: " + unsafe.getByte(obj, 10L));
    Utils.Print("    [11]: " + unsafe.getByte(obj, 11L));
    
    int i = unsafe.getInt(obj, 4L);
    Utils.Print(" int[4]: " + i);
    
    i = unsafe.getInt(obj, 8L);
    Utils.Print(" int[8]: " + i);
    long l = unsafe.getLong(obj, 8L);
    Utils.Print(" long[8]: " + l);
    l = l + 12L;
    Utils.Print(" long[8]: " + l);
  }
}
