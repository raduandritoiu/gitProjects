package testEnum;

public class TestEnum
{
  public static void testAll()
  {
    System.out.println("Test Enums:");
    test1();
    test2();
    test3();
    test4();
  }
  
  
  public static void test1()
  {
    System.out.println("- Test creare de elemente identice:");
    
    // daca creez doua enumuri de acelasi fel (adica doua Pears) 
    // acele doua obiecte rezultate f1 si f2 sunt acelasi obiect, pointeaza 
    // catre aceeasi instanta de obiect
    Fruits f1 = Fruits.Pears;
    Fruits f2 = Fruits.Pears;
    f2.qty(12);
    
    Fruits f3 = Fruits.Apples;
    f3.qty(32);
    
    System.out.println("  * f1.type: " + f1.type);
    System.out.println("  * f1.desc: " + f1.desc);
    System.out.println("  * f1.price: " + f1.price);
    System.out.println("  * f1.qty(): " + f1.qty());
    System.out.println("  * f1.toStr: " + f1.toStr());
    System.out.println("");
  }
  
  
  public static void test2()
  {
    System.out.println("- Test proprietati default:");
    
    // orice element dintrun ENUM are doua proprietati by default, care sunt variabile private cu accesori publici
    // name - care este un string egal cu definitia elementului
    // ordinal - care este egal cu pozitia lui cand a fost definit
    
    Fruits f1 = Fruits.GRAPES;
    System.out.println("  * f1: " + f1);
    System.out.println("  * f1.name: " + f1.name());
    System.out.println("  * f1.ordinal: " + f1.ordinal());
    
    // o clasa de ENUMS are default propritatea statica values() care intoarce un array
    // cu toate elementele definite in ENUM
    Fruits[] fs = Fruits.values();
    System.out.println("  * fs.len: " + fs.length);
    
    // o clasa de ENUMS are default propritatea statica valueOf() care primeste un string,
    // ce reprezinta numele (implicit al) elementului (egal cu definirea elementului) si 
    // intoarce elementul respectiv
    Fruits f2 = Fruits.valueOf("Apples");
    System.out.println("  * f2: " + f2);
    System.out.println("");
  }  
  
  public static void test3()
  {
    System.out.println("- Test (PRESUDO ENUM) creare de elemente identice:");
    
    // daca creez doua enumuri de acelasi fel (adica doua Pears) 
    // acele doua obiecte rezultate f1 si f2 sunt acelasi obiect, pointeaza 
    // catre aceeasi instanta de obiect
    Fruits_exposed f1 = Fruits_exposed.Pears;
    Fruits_exposed f2 = Fruits_exposed.Pears;
    f2.qty(12);
    
    Fruits_exposed f3 = Fruits_exposed.Apples;
    f3.qty(32);
    
    System.out.println("  * f1.type: " + f1.type);
    System.out.println("  * f1.desc: " + f1.desc);
    System.out.println("  * f1.price: " + f1.price);
    System.out.println("  * f1.qty(): " + f1.qty());
    System.out.println("  * f1.toStr: " + f1.toStr());
    System.out.println("");
  }
  
  
  public static void test4()
  {
    System.out.println("- Test proprietati default:");
    
    // orice element dintrun ENUM are doua proprietati by default, care sunt variabile private cu accesori publici
    // name - care este un string egal cu definitia elementului
    // ordinal - care este egal cu pozitia lui cand a fost definit
    
    Fruits_exposed f1 = Fruits_exposed.GRAPES;
    System.out.println("  * f1: " + f1);
    System.out.println("  * f1.name: " + f1.name());
    System.out.println("  * f1.ordinal: " + f1.ordinal());
    
    // o clasa de ENUMS are default propritatea statica values() care intoarce un array
    // cu toate elementele definite in ENUM
    Fruits_exposed[] fs = Fruits_exposed.values();
    System.out.println("  * fs.len: " + fs.length);
    
    // o clasa de ENUMS are default propritatea statica valueOf() care primeste un string,
    // ce reprezinta numele (implicit al) elementului (egal cu definirea elementului) si 
    // intoarce elementul respectiv
    Fruits_exposed f2 = Fruits_exposed.valueOf("Apples");
    System.out.println("  * f2: " + f2);
    System.out.println("");
  }
}
