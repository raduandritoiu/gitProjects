package testUnsafe.initialization;

import basicUtils.Utils;
import sun.misc.Unsafe;
import testUnsafe.MyOwnUnsafe;


public class Test
{
  public static void test()
  {
    try 
    {
      testInitializationPlus();
    }
    catch(Exception ex)
    {
      Utils.Print("FUCK!: " + ex.toString());
    }
  }
  
  
  public static void testInitializationSimple() throws Exception
  {
    Unsafe unsafe = MyOwnUnsafe.getInstance();
    
    Utils.Print("Test class A:");
    ClassA a;
    Utils.Print("- Init object a1:");
    a = new ClassA();
    Utils.Print(" (i1 = " + a.getI1() + ", i2 = " + a.getI2() + ", i3 = " + a.getI3() + ", i4 = " + a.getI4() + ")\n");
    Utils.Print("- Init object a2:");
    a = ClassA.class.newInstance();
    Utils.Print(" (i1 = " + a.getI1() + ", i2 = " + a.getI2() + ", i3 = " + a.getI3() + ", i4 = " + a.getI4() + ")\n");
    Utils.Print("- Init object a3:");
    a = (ClassA) unsafe.allocateInstance(ClassA.class);
    Utils.Print(" (i1 = " + a.getI1() + ", i2 = " + a.getI2() + ", i3 = " + a.getI3() + ", i4 = " + a.getI4() + ")\n");
    Utils.Print("\n");
    
    Utils.Print("Test class B:");
    ClassB b;
    Utils.Print("- Init object b1:");
    b = new ClassB();
    Utils.Print(" (i1 = " + b.i1 + ", i2 = " + b.i2 + ", i3 = " + b.i3 + ", i4 = " + b.i4 + ")\n");
    Utils.Print("- Init object b2:");
    b = ClassB.class.newInstance();
    Utils.Print(" (i1 = " + b.i1 + ", i2 = " + b.i2 + ", i3 = " + b.i3 + ", i4 = " + b.i4 + ")\n");
    Utils.Print("- Init object b3:");
    b = (ClassB) unsafe.allocateInstance(ClassB.class);
    Utils.Print(" (i1 = " + b.i1 + ", i2 = " + b.i2 + ", i3 = " + b.i3 + ", i4 = " + b.i4 + ")\n");
    Utils.Print("\n");
    
    Utils.Print("Test class C:");
    ClassC c;
    Utils.Print("- Init object c1:");
    c = new ClassC();
    Utils.Print(" (i1 = " + c.getI1() + ", i2 = " + c.getI2() + ", i3 = " + c.getI3() + ", i4 = " + c.getI4() + ")\n");
    Utils.Print("- Init object c2:");
    c = ClassC.class.newInstance();
    Utils.Print(" (i1 = " + c.getI1() + ", i2 = " + c.getI2() + ", i3 = " + c.getI3() + ", i4 = " + c.getI4() + ")\n");
    Utils.Print("- Init object c3:");
    c = (ClassC) unsafe.allocateInstance(ClassC.class);
    Utils.Print(" (i1 = " + c.getI1() + ", i2 = " + c.getI2() + ", i3 = " + c.getI3() + ", i4 = " + c.getI4() + ")\n");
    Utils.Print("\n");
    
    Utils.Print("Test class D:");
    ClassD d;
    Utils.Print("- Init object d1:");
    d = new ClassD();
    Utils.Print(" (i1 = " + d.i1 + ", i2 = " + d.i2 + ", i3 = " + d.i3 + ", i4 = " + d.i4 + ")\n");
    Utils.Print("- Init object d2:");
    d = ClassD.class.newInstance();
    Utils.Print(" (i1 = " + d.i1 + ", i2 = " + d.i2 + ", i3 = " + d.i3 + ", i4 = " + d.i4 + ")\n");
    Utils.Print("- Init object d3:");
    d = (ClassD) unsafe.allocateInstance(ClassD.class);
    Utils.Print(" (i1 = " + d.i1 + ", i2 = " + d.i2 + ", i3 = " + d.i3 + ", i4 = " + d.i4 + ")\n");
    Utils.Print("\n");
  }
  
  
  public static void testInitializationPlus() throws Exception
  {
    Unsafe unsafe = MyOwnUnsafe.getInstance();
    
    Utils.Print("Test class A argument:");
    ClassAarg a;
    Utils.Print("- Init object a1:");
    a = new ClassAarg(107);
    Utils.Print(" (i1 = " + a.getI1() + ", i2 = " + a.getI2() + ", i3 = " + a.getI3() + ", i4 = " + a.getI4() + ")\n");
//    Utils.Print("- Init object a2:");
//    a = ClassAarg.class.newInstance();
//    Utils.Print(" (i1 = " + a.getI1() + ", i2 = " + a.getI2() + ", i3 = " + a.getI3() + ", i4 = " + a.getI4() + ")\n");
    Utils.Print("- Init object a3:");
    a = (ClassAarg) unsafe.allocateInstance(ClassAarg.class);
    Utils.Print(" (i1 = " + a.getI1() + ", i2 = " + a.getI2() + ", i3 = " + a.getI3() + ", i4 = " + a.getI4() + ")\n");
    Utils.Print("\n");
    
    Utils.Print("Test class B argument:");
    ClassBarg b;
    Utils.Print("- Init object b1:");
    b = new ClassBarg(107);
    Utils.Print(" (i1 = " + b.i1 + ", i2 = " + b.i2 + ", i3 = " + b.i3 + ", i4 = " + b.i4 + ")\n");
//    Utils.Print("- Init object b2:");
//    b = ClassBarg.class.newInstance();
//    Utils.Print(" (i1 = " + b.i1 + ", i2 = " + b.i2 + ", i3 = " + b.i3 + ", i4 = " + b.i4 + ")\n");
    Utils.Print("- Init object b3:");
    b = (ClassBarg) unsafe.allocateInstance(ClassBarg.class);
    Utils.Print(" (i1 = " + b.i1 + ", i2 = " + b.i2 + ", i3 = " + b.i3 + ", i4 = " + b.i4 + ")\n");
    Utils.Print("\n");
    
    Utils.Print("Test class C argument:");
    ClassCarg c;
    Utils.Print("- Init object c1:");
    c = new ClassCarg(104);
    Utils.Print(" (i1 = " + c.getI1() + ", i2 = " + c.getI2() + ", i3 = " + c.getI3() + ", i4 = " + c.getI4() + ")\n");
//    Utils.Print("- Init object c2:");
//    c = ClassCarg.class.newInstance();
//    Utils.Print(" (i1 = " + c.getI1() + ", i2 = " + c.getI2() + ", i3 = " + c.getI3() + ", i4 = " + c.getI4() + ")\n");
    Utils.Print("- Init object c3:");
    c = (ClassCarg) unsafe.allocateInstance(ClassCarg.class);
    Utils.Print(" (i1 = " + c.getI1() + ", i2 = " + c.getI2() + ", i3 = " + c.getI3() + ", i4 = " + c.getI4() + ")\n");
    Utils.Print("\n");
    
    Utils.Print("Test class D:");
    ClassDarg d;
    Utils.Print("- Init object d1:");
    d = new ClassDarg(104);
    Utils.Print(" (i1 = " + d.i1 + ", i2 = " + d.i2 + ", i3 = " + d.i3 + ", i4 = " + d.i4 + ")\n");
//    Utils.Print("- Init object d2:");
//    d = ClassDarg.class.newInstance();
//    Utils.Print(" (i1 = " + d.i1 + ", i2 = " + d.i2 + ", i3 = " + d.i3 + ", i4 = " + d.i4 + ")\n");
    Utils.Print("- Init object d3:");
    d = (ClassDarg) unsafe.allocateInstance(ClassDarg.class);
    Utils.Print(" (i1 = " + d.i1 + ", i2 = " + d.i2 + ", i3 = " + d.i3 + ", i4 = " + d.i4 + ")\n");
    Utils.Print("\n");
  }
}
