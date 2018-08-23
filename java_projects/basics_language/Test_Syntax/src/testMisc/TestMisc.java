package testMisc;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class TestMisc
{
  public static void test()
  {
    ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
    map.put(1, "unu");
    map.put(2, "two");
    map.put(3, "tres");
    map.put(5, "cinque");
    map.put(6, "sase");
    map.put(7, "7777");
    
    for (int i = 0; i < 4; i++)
    {
      System.out.println(i);
    }
    
    for (Enumeration<Integer> prop = map.keys(); prop.hasMoreElements();)
    {
      Integer i = prop.nextElement();
      System.out.println(i + "   " + map.get(i));
    }
    
//    HashMap<SameHashKey, String> sameMap = new HashMap<>();
//    sameMap.put(new SameHashKey(11), "primul");
//    sameMap.put(new SameHashKey(21), "doilea");
//    sameMap.put(new SameHashKey(51), "treilea");
//    sameMap.put(new SameHashKey(31), "patrulea");
//    sameMap.put(new SameHashKey(41), "cincelea");
    
    HashMap<DiffHashKey, String> diffMap = new HashMap<>();
    diffMap.put(new DiffHashKey((2 << 6) + 2), "==  1  ==");
    diffMap.put(new DiffHashKey((3 << 6) + 2), "==  2  ==");
    diffMap.put(new DiffHashKey((0 << 6) + 2), "==  3  ==");
    diffMap.put(new DiffHashKey((1 << 6) + 2), "==  4  ==");
    
    for (int i = 4; i < 40; i++)
    {
      diffMap.put(new DiffHashKey((i << 6) + 2), "==  " + (i+1) + "  ==");
    }
    System.out.println("a");
    
    for (int i = 41; i < 50; i++)
    {
      diffMap.put(new DiffHashKey((i << 6) + 2), "==  " + (i+1) + "  ==");
    }
    System.out.println("a");
    
    
    

//    SameHashKey sk = new SameHashKey(32);
    DiffHashKey dk = new DiffHashKey(2);
    
    String str;
//    str = sameMap.get(sk);
    str = diffMap.get(dk);
    
    System.out.println(str);
  }
}

class SameHashKey 
{
  public final int k;
  public SameHashKey(int i)
  {
    k = i;
  }
  
  public int hashCode()
  {
    return 21;
  }
}

class DiffHashKey 
{
  public final int k;
  public DiffHashKey(int i)
  {
    k = i;
  }
  
  public int hashCode()
  {
    return k;
  }
}
