import dataStruct.TestTree;
import testBytecode.Pyramid;
import testInner.TestInner;
import testInner.TopClass;
import testMisc.TestMisc;
import testReflection.TestReflection;

public class Main
{
  public static void main(String[] args) throws Exception
  {
    TestTree.test();
    System.out.println("Done!");
    
    int i1 = 9;
    int i2 = i1 << 1;
    int i3 = i1 >> 1;
    
//    Pyramid p = new Pyramid(3);
//    p.adding(1, 1);
  }
}
