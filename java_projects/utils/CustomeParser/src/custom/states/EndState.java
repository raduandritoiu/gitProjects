package custom.states;

import machine.InputToken;
import machine.StdState;

public class EndState implements StdState
{
  public String msg = "Happy dayse are here to stay!";
  
  public StdState parse(InputToken input)
  {
    return new ErrorState(this.toString());
  }
  
  public boolean isEndState()
  {
    return true;
  }
}
