package custom.states;

import machine.InputToken;
import machine.StdState;

public class LengthState implements StdState
{
  public StdState parse(InputToken input)
  {
    int len = input.getByte();
    return new BodyState(len);
  }
  
  public boolean isEndState()
  {
    return false;
  }
}
