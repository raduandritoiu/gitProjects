package testEnum;

public enum Fruits
{
  // elementele care le declar eu ca facand parte din acest ENUM
  // ele vor deveni niste variabile statice publice de tipul ENUMului curent
  // si se vor defini 
  Apples(1, "apples"),
  Oranges(2, "oranges"),
  Pears(3, "pears", 10),
  GRAPES(4, "grapes"),
  Limes(5, "limes");
  
  
  // toate proprietatile care le fac FINAL trebuie sa 
  // le initializez in constructor - dhaaaaa LOGIC
  public final int type;
  public final String desc;
  
  // pot avea proprietati care nu sunt FINAL 
  // si pot fi modificate la runtime 
  public int price;
  
  // pot avea proprietati private vare nu sunt FINAL
  // si pot fi modificate la runtime
  private int _qty;
  
  
  // Constructorul unul ENUM trebuie sa fie PRIVATE 
  // ca sa nu poti crea in mod direct un obiect de genul respectiv
  // Asta insemna ca nici nu pot extinde un enum
  private Fruits(int t, String d)
  {
    type = t;
    desc = d;
  }
  private Fruits(int t, String d, int p)
  {
    type = t;
    desc = d;
    price = p;
  }
  
  
  // pot avea functii atat private cat si public care sa modifice starea enumului meu. 
  // Functiile acestea sunt "pe obiect" deci pe enumul respectiv
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
  
  
  // un enum are toate functiile unui Object si le poate suprascrie
//  @Override
//  public String toString()
//  {
//    return "Acestea sunt " + _qty + " kg de " + name + "(" + type + ") cu pret " + price;
//  }
}
