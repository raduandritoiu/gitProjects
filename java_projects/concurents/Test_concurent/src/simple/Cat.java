package simple;

public class Cat
{
  public int age;
  public int weight;
  
  public Cat()
  {
    age = 1;
    weight = 5;
  }
  
  public Cat(int a)
  {
    age = a;
  }
  
  public void aging()
  {
    aging(1);
  }
  
  public void aging(int a)
  {
    age += a;
  }
  
  public String fat(int w)
  {
    int oldW = weight;
    weight += w;
    int newW = weight;
    return "Got fat from " + oldW + " to" + newW + " with (" + w + ")";
  }
  
  public String slim(int w)
  {
    int oldW = weight;
    weight -= w;
    int newW = weight;
    return "Got slim from " + oldW + " to" + newW + " with (" + w + ")";
  }
  
  public String who()
  {
    return "I am a Cat of "+age+" years";
  }
}
