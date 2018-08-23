package testing.business.data.models;

import java.util.ArrayList;

public class Army
{
  public final String name;
  public final ArrayList<Unit> units;
   
  public Army(String nName)
  {
    name = nName;
    units = new ArrayList<>();
  }
}
