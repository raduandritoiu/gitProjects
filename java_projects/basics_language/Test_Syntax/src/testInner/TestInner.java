package testInner;

//import testInner.TopClass.InnerClass;

//import testInner.TopClass.InnerStaticClass;

public class TestInner
{
  public static void test1()
  {
    // pot defini obiecte din fiecare clasa
    TopClass tp = new TopClass("Unu");
    AnotherTopClass tp2 = new AnotherTopClass("Doi");
    
    
    // asa se instantiaza o clasa inner statica (fiindca a facut import testInner.TopClass.InnerStaticClass;)
//    InnerStaticClass isc1 = new InnerStaticClass("Trei");
    // daca nu facea import asa se instantia
    TopClass.InnerStaticClass isc2 = new TopClass.InnerStaticClass("Patru");
    
    // asa se instantiaza o clasa inner care nu e statica
    // la fel si faza daca face import (sau nu) direct la definitia clasei
    TopClass.InnerClass ic1 = tp.new InnerClass("Cinci");
    
    
    tp.doSomeWork(15,  16,  17);
    
    System.out.println("");
  }
}
