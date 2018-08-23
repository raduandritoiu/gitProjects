package nativ.models;

import java.io.File;

import support.models.MyCallback;
import support.models.ValClass;

public class NativeClassA
{
  public float x;
  public float y;
  public float z;
  public String str;
  public ValClass val;

  
  public NativeClassA()
  {
    this(0, 0, 0, "native class A", new ValClass());
  }
  
  public NativeClassA(float nx, float ny, float nz, String nstr, ValClass nval)
  {
    x = nx;
    y = ny;
    z = nz;
    str = nstr;
    val = nval;
  }
  
  
  public ValClass createVal(float off)
  {
    ValClass val = new ValClass(x + off, y + off, z + off);
    return val;
  }
  
  public float setVal(float a, float b, float c)
  {
    x = a;
    y = b;
    z = c;
    float fl = val();
    return fl;
  }
  
  public float setInnerVal(float a, float b, float c)
  {
    val = new ValClass(a, b, c);
    float fl = val.val();
    return fl;
  }
  
  public float setInnerVal(ValClass nVal)
  {
    val = nVal;
    float fl = val.val();
    return fl;
  }
  
  
  public float val()
  {
    float v = 1;
    v = v * x;
    v = v * y;
    v = v * z;
    return v;
  }
  
  public float innerVal()
  {
    float v = 0;
    v = v + val.val();
    return v;
  }
  
  public float totalVal()
  {
    float v = val();
    v = v + val.val();
    return v;
  }
  
  
  // execut o functie din C care pentru anumite valori ale lui lvl face:
  // 0 - trimitere de argumente cu valori primitive                   - returneaza suma args
  // 1 - acceseaza atributele primitive ale clasei curente (this)     - returneaza suma args si atribute
  // 2 - acceseaza atributul object ale clasei curente (this) si      - returneaza suma args si attribute si atributele obj
  //      apoi acceseaza atributele acestui obiect 
  //      - aparent merge si daca sunt private => totul se face prin adrese => who F**ing cares about accessors 
  //
  // 3 - acceseaza functia simpla val() a clasei curente (this)       - returneaza suma args si rezultatul fc 
  // 4 - acceseaza functia totalVal() a clasei curente (this),        - returneaza suma args si rezultatul fc 
  //      functie cre la randul ei acceseaza alta functie
  // 5 - acceseaza atributul object si functia simpla val() a         - returneaza suma args si rezultatulele fc'lor 
  //      clasei curente (this) si apoi functia val() a obiectului
  native public float nativeComputeFunc(float a, float b, float c, int lvl);
  
  
  // setez parametrii din C
  // 0 - calls setVal()
  // 1 - calls setInnerVal()
  // 2 - calls createVal() and setInnerVal()
  // 3 - creates a Val obect and calls setInnerVal()
  native public float setFromC(float a, float b, float c, int lvl);

  
  // tin minte parametrii in C
  native public float rememberVals(float a, float b, float c);
  
  // reiau parametrii stocati in C
  native public float getVals(float off);
  
  
  // save a callback object in the Native part of the app
  native public float registerCallback(MyCallback call);
  
  // cheama functia de callback
  native public float hitCallback(float val);
  
  // sterge functia de callback si tot ce a mai tinut minte
  native public float clearCallback();
  
  
  
  private static boolean loaded = false;
  public static int loadLib()
  {
    if (loaded) return 0;
    
    int succ = 0;
    try
    {      
      String path = new File(".").getCanonicalPath();    
      System.load(path + "\\src\\JavaNative.dll");
      loaded = true;
    }
    catch(Exception ex)
    {
      System.out.println(ex);
      succ = -1;
    }

    return succ;
  }
}
