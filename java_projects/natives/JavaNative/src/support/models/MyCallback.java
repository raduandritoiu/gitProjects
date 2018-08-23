package support.models;

public class MyCallback
{
  float cnt = 0;
  float inc = 0;
  
  public MyCallback(float nInc)
  {
    inc = nInc;
  }
  
  
  public float callFunction(float a)
  {
    System.out.println("call MyCallback with " + a);
    cnt = cnt + inc + a;
    return cnt;
  }
  
  
  @Override
  protected void finalize()
  {
    System.out.println("GC MyCallback that reached to " + cnt);
  }
}
