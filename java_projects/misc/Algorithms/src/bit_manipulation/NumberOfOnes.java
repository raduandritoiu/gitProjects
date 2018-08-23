package bit_manipulation;

public class NumberOfOnes
{
  public static void run()
  {
    int a = 9;
    int b = 5;
    int c = a / b * b;
    int d = a % b;
    
    boolean s = testOnes(17);
    s = testOnes(8123);
    s = testOnes(1234);
    s = testOnes(934);
    s = testOnes(63462);
    s = testOnes(384);
    
    s = testOnes(3);
    s = testOnes(6);
    s = testOnes(7);
    s = testOnes(8);
    
    String sss;
  }
  
  private static boolean testOnes(int n)
  {
    int s1 = allOnes(n);
    int s2 = allOnes_super(n);
    boolean b = s1 == s2;
    if (!b)
      System.out.println("FUCK");
    return b;
  }
  
  private static int allOnes_super(int n)
  {
    int sum = 0;
    int nums = n+1;
    int pace = 1;
    int half = 0;
    while (n > 0)
    {
      half = pace;
      pace = pace << 1;
      sum = sum + nums / pace * half + Math.max(nums % pace - half, 0);
      n = n >> 1;
    }
    return sum;
  }
  
  
  private static int allOnes(int n)
  {
    int s = 0;
    for (int i = 0; i <= n; i++)
      s += ones(i);
    return s;
  }
  
  
  private static int ones(int n)
  {
    int s = 0;
    while (n > 0)
    {
      if ((n & 1) == 1)
        s++;
      n = n >> 1;
    }
    return s;
  }
}
