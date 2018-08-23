package dynamic.loader;

import java.io.FileInputStream;
import java.util.HashMap;

public class CustomLoader extends ClassLoader
{
  private String _package;
  private HashMap<String, Class<?>> classes;
  
  
  public CustomLoader(String pkg)
  {
    _package = pkg;
    classes = new HashMap<>();
  }
  
  public void setPackage(String pkg)
  {
    _package = pkg;
  }
  
  @Override
  protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException
  {
    System.out.println("MY_Loader trying to load: " + className);
    
    if (classes.containsKey(className))
      return classes.get(className);
    
    Class<?> result = null;
    
    // I HAVE TO use System loader
    try
    {
      result = super.findSystemClass(className); 
      if (result != null)
        return result;
    }
    catch(Exception ex)
    {
      System.out.println(">>>>>> Not a system class. - SO MY WAY");
    }
    
    String translatedName = translateClassName(className);
    translatedName = translateFileName(translatedName);
    
    byte[] bytes = getBytes(translatedName);
    result = defineClass(null, bytes, 0, bytes.length);
    return result;
  }
  
  
  @Override
  protected Class<?> findClass(final String className) throws ClassNotFoundException
  {
    System.out.println("MY_Loader trying to load: " + className);
    
    if (classes.containsKey(className))
      return classes.get(className);
    
    String translatedName = translateClassName(className);
    translatedName = translateFileName(translatedName);
    
    byte[] bytes = getBytes(translatedName);
    Class<?> result = defineClass(null, bytes, 0, bytes.length);
    return result;
  }
  
  
  private byte[] getBytes(String fileName)
  {
    byte[] bytes = null;
    try
    {
      FileInputStream is = new FileInputStream(fileName);
      int av = is.available();
      bytes = new byte[av];
      is.read(bytes, 0, av);
      is.close();
    }
    catch (Exception ex)
    {
      System.out.println("fuck");
    }
    return bytes;
  }
  
  private String translateClassName(String name)
  {
    String translatedName = name;
    if (_package.toLowerCase().indexOf("barbarians") != -1)
    {
      if (translatedName.indexOf("normal") != -1)
      {
        if (translatedName.indexOf("warrier") != -1)
          translatedName = "dynamic.loader." + _package + "." + "Berserker";
        else if (translatedName.indexOf("weapon") != -1)
          translatedName = "dynamic.loader." + _package + "." + "BattleAxe";
        else if (translatedName.indexOf("pet") != -1)
          translatedName = "dynamic.loader." + _package + "." + "Horse";
      }
      else if (translatedName.indexOf("advanced") != -1)
      {
        if (translatedName.indexOf("warrier") != -1)
          translatedName = "dynamic.loader." + _package + "." + "Chieftain";
        else if (translatedName.indexOf("weapon") != -1)
          translatedName = "dynamic.loader." + _package + "." + "MisticalSword";
        else if (translatedName.indexOf("pet") != -1)
          translatedName = "dynamic.loader." + _package + "." + "MysticSteed";
      }
    }
    else if (_package.toLowerCase().indexOf("elves") != -1)
    {
      if (translatedName.indexOf("normal") != -1)
      {
        if (translatedName.indexOf("warrier") != -1)
          translatedName = "dynamic.loader." + _package + "." + "WoodElf";
        else if (translatedName.indexOf("weapon") != -1)
          translatedName = "dynamic.loader." + _package + "." + "ElfBow";
        else if (translatedName.indexOf("pet") != -1)
          translatedName = "dynamic.loader." + _package + "." + "Pegasus";
      }
      else if (translatedName.indexOf("advanced") != -1)
      {
        if (translatedName.indexOf("warrier") != -1)
          translatedName = "dynamic.loader." + _package + "." + "DarkElf";
        else if (translatedName.indexOf("weapon") != -1)
          translatedName = "dynamic.loader." + _package + "." + "MagicKnives";
        else if (translatedName.indexOf("pet") != -1)
          translatedName = "dynamic.loader." + _package + "." + "AirDragon";
      }
    }
    
    return translatedName;
  }
  
  
  private String translateFileName(String name)
  {
    String translatedName = name.replace(".", "/") + ".class";
    if (_package.toLowerCase().indexOf("barbarians") != -1)
      translatedName = "../CustomLoaderBarbarians/bin/" + translatedName;
    else if (_package.toLowerCase().indexOf("elves") != -1)
      translatedName = "../CustomLoaderElves/bin/" + translatedName;
    return translatedName;
  }
}
