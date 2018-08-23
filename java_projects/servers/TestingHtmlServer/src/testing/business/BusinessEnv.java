package testing.business;


public class BusinessEnv
{
  
  // ==== singleton stuff ===== 
  private BusinessEnv() {}
  public static BusinessEnv _env;
  public static BusinessEnv get()
  {
    if (_env != null) 
      return _env;
    _env = new BusinessEnv();
    return _env;
  }
}
