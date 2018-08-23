package testVolatile;

// nu am voie
public /*volatile*/ class VolatileExample
{
  // am voie
  volatile public int cnt;
  
  public VolatileExample()
  {
    cnt = 9;
  }
  
  public int compute(int input)
  {
    // nu am voie
    /*volatile*/ int output = 9;
    output = input * cnt;
    return output;
  }
}
