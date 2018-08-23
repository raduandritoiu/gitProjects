package queues;

public class Generator
{
  int cnt = 0;
  public void reset()
  {
    cnt = 0;
  }
  
  public int get()
  {
    cnt++;
    return cnt;
  }
}
