package testing.business.data.models;

import java.util.ArrayList;

public class Kingdom
{
  public final String name;
  public final ArrayList<Army> armies;
  public Address addr;
  
  public Kingdom(String nName)
  {
    name = nName;
    armies = new ArrayList<>();
  }
}
