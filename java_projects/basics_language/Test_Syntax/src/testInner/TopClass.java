package testInner;

public class TopClass
{
  public static String type;
  public String name;
  public int x;
  
  static
  {
    type = "This is the main Top Class.";
  }
  
  public TopClass(String n)
  {
    name = "The main Top Class: "+ n;
    x = 2;
  }
  
  
  public void doSomeWork(int x, int y, int z)
  {
    InnerClass ic = new InnerClass("insidere 1");
    int a = ic.getValue() + x;
    
    // aici creez o local class
    
    int b = y;
    
    
    // aici creez o anonymous class
    InnerClass ic_ann = new InnerClass("anonymous one") {
      public int z = 20;
      
      @Override
      public int getValue()
      {
        return z;
      }
    };
    int c = ic_ann.getValue() + z;
    
    System.out.println(ic_ann.name);
  }
  
  
  
  // definitii de Inner Classes
  
  public class InnerClass
  {
    public static final String type = "WTF?";
    public String name;
    
    public InnerClass(String n)
    {
      name = "The Static Inner Class: " + n + " for x=" + x; // poate accesa x - o prop a clasei principale
    }
    
    public int getValue()
    {
      return 7;
    }
  }
  
  protected static class InnerStaticClass
  {
    public static String type;
    public String name;
    
    static
    {
      type = "This is a Static Inner Class.";
    }
    
    public InnerStaticClass(String n)
    {
      name = "The Static Inner Class: " + n + " for x="; // nu poate accesa x - o prop a clasei principale
    }
  }
}


// aceasta a fi package-private ONLY
class AnotherTopClass
{
  public static String type;
  public String name;
  
  static
  {
    type = "This is another Top Class.";
  }
  
  public AnotherTopClass(String n)
  {
    name = "The private Top Class: " + n;
  }
}
