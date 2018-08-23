package custom.states;

import machine.InputToken;
import machine.StdState;

public class CrcState implements StdState
{
  private int testCrc;
  
  public CrcState(int crc)
  {
    testCrc = crc;
  }
  
  public StdState parse(InputToken input)
  {
    byte readCrc = input.getByte();
    if (readCrc == testCrc)
      return new EndState();
    return new ErrorState(this.toString());
  }
  
  public boolean isEndState()
  {
    return false;
  }
}
