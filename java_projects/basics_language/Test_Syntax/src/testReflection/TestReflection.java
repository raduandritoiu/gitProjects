package testReflection;

import java.lang.reflect.AccessibleWorkaround;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class TestReflection
{
  public static void test1() throws Exception
  {
    // normal class
    Extended ex = new Extended(10);
    Class cls1 = ex.getClass();
    Class cls2 = Extended.class;
    Class cls3 = Class.forName("testReflection.Extended");
    
    // primitive
    Class cls4 = int.class; // doar asta merge
    Class cls51 = Class.forName("I");
    
    // arrays
    Extended[] exs = new Extended[5];
    Class cls5 = exs.getClass();
    Class cls6 = Extended[].class;
    Class cls7 = Class.forName("[LtestReflection.Extended;");
    
    
    // now more nutty stuff
    FieldTypes<String, Base> ft1 = new FieldTypes<>();
    Class cls8 = ft1.getClass();
    FieldTypes<Number, Extended> ft2 = new FieldTypes<>();
    Class cls9 = ft1.getClass();
    Class cls10 = FieldTypes.class;
    Class cls11 = Class.forName("testReflection.FieldTypes");
    
    System.out.println("-----");
  }
  
  
  public static void test2() throws Exception
  {
    // now more nutty stuff
    FieldTypes<String, Base> ft1 = new FieldTypes<>();
    Class cls8 = ft1.getClass();
    FieldTypes<Number, Extended> ft2 = new FieldTypes<>();
    Class cls9 = ft1.getClass();
    Class cls10 = FieldTypes.class;
    Class cls11 = Class.forName("testReflection.FieldTypes");
    
    printFields(cls8);
    printFields(cls9);
    printFields(cls10);
    printFields(cls11);
  }
  
  
  public static void test3() throws Exception
  {
    FieldAccess fa = new FieldAccess();
    fa.pubInt = 31;
    // fa.privInt = 32; // da eroare de compilare
    // fa.finInt = 34; // da eroare de compilare
    // fa.finInt = 35; // da eroare de compilare
    
    Class cls = fa.getClass();
    Field f1 = cls.getDeclaredField("finInt");
    Field f2 = cls.getDeclaredField("privInt");
    Field f3 = cls.getDeclaredField("finPrivInt");
    // f1.setInt(fa, 9); // da eroare la rulare pana sa ii setes accessible
    
//    f1.setAccessible(true);
//    f2.setAccessible(true);
//    f3.setAccessible(true);
    
    // and a workaround - mother fuckers
    AccessibleWorkaround workaround = new AccessibleWorkaround();
    workaround.setAccessible(f1, true);
    workaround.setAccessible(f2, true);
    workaround.setAccessible(f3, true);
    
    f1.setInt(fa, 31);
    f2.setInt(fa, 31);
    f3.setInt(fa, 31);
    
    System.out.println("!");
  }
  
  
  public static void test4()
  {
    Class cls1 = Constructors.class;
    cls1.getConstructors();
  }
  
  public static void printFields(Class cls)
  {
    Field[] flds = cls.getDeclaredFields();
    for (int i = 0; i < flds.length; i++)
    {
      System.out.println(flds[i].getName() + "\t: " + flds[i].getType());
      Type gen = flds[i].getGenericType();
      System.out.println("\t: " + gen);
      int x = 0;
    }
    
    TypeVariable[] typeParams = cls.getTypeParameters();
    for (int i = 0; i < typeParams.length; i++)
    {
      Type[] bounds = typeParams[i].getBounds();
      System.out.println("******  " + typeParams[i].getTypeName());
      System.out.println("        " + typeParams[i].getName());
    }
    
    System.out.println("------------------");
  }
}
