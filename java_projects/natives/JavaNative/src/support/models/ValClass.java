package support.models;

public class ValClass
{
  float x, y, z;
  
  public ValClass()
  {
    x = 0; 
    y = 0;
    z = 0;
  }
  
  public ValClass(float nx, float ny, float nz)
  {
    x = nx; 
    y = ny;
    z = nz;
  }
  
  public float val()
  {
    float v = 1;
    v = v * x;
    v = v * y;
    v = v * z;
    return v;
  }
}
