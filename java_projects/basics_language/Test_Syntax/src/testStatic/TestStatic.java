package testStatic;

public class TestStatic
{
  public static void test()
  {
    BaseCls base1 = new BaseCls(10);
    ExtendCls ext1 = new ExtendCls(234);
    BaseCls poli1 = new ExtendCls(813);
    
    BaseCls base2 = BaseCls.create(5);
    ExtendCls ext2 = (ExtendCls) ExtendCls.create(234);
    BaseCls poli2 = ExtendCls.create(813);
    
    BaseCls base3 = base1.create(5);
    ExtendCls ext3 = (ExtendCls) ext1.create(234);
    BaseCls poli3 = poli1.create(813);
    
    
    System.out.println(base1.def());
    System.out.println(ext1.def());
    System.out.println(poli1.def());
    System.out.println("--------------------------------");
    
    System.out.println(base2.def());
    System.out.println(ext2.def());
    System.out.println(poli2.def());
    System.out.println("--------------------------------");
    
    System.out.println(base3.def());
    System.out.println(ext3.def());
    System.out.println(poli3.def());
  }
}
