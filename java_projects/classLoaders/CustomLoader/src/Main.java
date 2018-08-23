import dynamic.loader.Apple;
import dynamic.loader.CustomLoader;
import dynamic.loader.interfaces.ICharacter;

public class Main
{
  public static void main(String[] args) throws Exception
  {
    Apple app = new Apple();
    Class<?> cls = app.getClass();
    ClassLoader cl0 = ClassLoader.getSystemClassLoader();
    ClassLoader cl1 = cls.getClassLoader();
    ClassLoader cl2 = cl1.getParent();
    ClassLoader cl3 = cl2.getParent();
    
    

    
    System.out.println("start loading classes");
    CustomLoader loader = new CustomLoader("barbarians");
    Object obj;
    
    Class ch_cls1 = loader.loadClass("normal warrier");
    ClassLoader cl4 = ch_cls1.getClassLoader();
    ClassLoader cl5 = cl4.getParent();
    ClassLoader cl6 = cl5.getParent();
    ClassLoader cl7 = cl6.getParent();
    
    
    
    
    loader.setPackage("elves");
    Class ch_cls2 = loader.loadClass("advanced warrier");
    
    
    obj = ch_cls2.newInstance();
    ICharacter ch2 = (ICharacter) obj;
    ch2.init();
    
    obj = ch_cls1.newInstance();
    ICharacter ch1 = (ICharacter) obj;
    ch1.init();
    
    System.out.println("done");
  }
}
