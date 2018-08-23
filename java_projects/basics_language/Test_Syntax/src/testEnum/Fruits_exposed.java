package testEnum;

public class Fruits_exposed
{
  // proprietatile FINAL
  public final int type;
  public final String desc;
  // proprietati care nu sunt FINAL 
  public int price;
  // proprietati private care nu sunt FINAL
  private int _qty;
  
  // Constructorul clasei mele de ENUM 
  /** constructorului i se mai adauga inca doi parametri */
  private Fruits_exposed(String n, int o, int t, String d)
  {
    _name = n;
    _ordinal = o;
    type = t;
    desc = d;
  }
  private Fruits_exposed(String n, int o, int t, String d, int p)
  {
    _name = n;
    _ordinal = o;
    type = t;
    desc = d;
    price = p;
  }
  
  // functiile
  public void qty(int c)
  {
    _qty = c;
  }
  public int qty()
  {
    return _qty;
  }
  public String toStr()
  {
    return "Acestea sunt " + _qty + " kg de " + desc + "(" + type + ") cu pret " + price;
  }
  
  
  /** aestea sunt proprietatile/functiile default cu care vine ENUM CLASS */
  private final String _name;
  private final int _ordinal;
  
  public String name()
  {
    return _name;
  }
  public int ordinal()
  {
    return _ordinal;
  }
  @Override
  public String toString()
  {
    return _name;
  }
  
  /** aestea sunt proprietatile/functiile  STATICE default cu care vine ENUM CLASS */
  private static Fruits_exposed[] _values;
  
  public static Fruits_exposed[] values()
  {
    return _values;
  }
  public static Fruits_exposed valueOf(String n)
  {
    for (int i = 0; i < _values.length; i++)
      if (_values[i]._name.equals(n))
        return _values[i];
    return null;
  }
  
  /** in asta se traduc elementele care le adaug eu la inceputul clasei ENUM, cand creez un ENUM */
  public static Fruits_exposed Apples   = new Fruits_exposed("Apples",  0, 1, "apples");
  public static Fruits_exposed Oranges  = new Fruits_exposed("Oranges", 1, 2, "oranges");
  public static Fruits_exposed Pears    = new Fruits_exposed("Pears",   2, 3, "pears", 10);
  public static Fruits_exposed GRAPES   = new Fruits_exposed("GRAPES",  3, 4, "grapes");
  public static Fruits_exposed Limes    = new Fruits_exposed("Limes",   4, 5, "limes");

  static
  {
    _values = new Fruits_exposed[5];
    _values[0] = Apples;
    _values[1] = Oranges;
    _values[2] = Pears;
    _values[3] = GRAPES;
    _values[4] = Limes;
  }
}
