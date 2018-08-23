package testDispatch;

import testDispatch.simulateDouble.TestDouble;
import testDispatch.single.TestSingle;

public class TestDispatch
{
  public static void testAll()
  {
    TestSingle.test();
    TestDouble.test();
  }
}
