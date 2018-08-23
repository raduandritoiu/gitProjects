package custom.states;

import machine.InputToken;
import machine.StdState;

public class AddressState implements StdState
{
  private int len = 0;
  private int addr = 0;
  
  public StdState parse(InputToken input)
  {
    addr += input.getByte();
    len++;
    if (len < 4)
      return this;
    
    return new LengthState();
  }
  
  public boolean isEndState()
  {
    return false;
  }
  
  public int getAddr()
  {
    return addr;
  }
}
