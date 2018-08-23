package testReflection;

public class FieldAccess
{
  public int pubInt;
  private int privInt;
  public final int finInt;
  private final int finPrivInt;
  
  public FieldAccess()
  {
    privInt = 11;
    finInt = 12;
    finPrivInt = 13;
  }
}
