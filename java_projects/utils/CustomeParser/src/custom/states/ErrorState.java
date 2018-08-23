package custom.states;

import machine.InputToken;
import machine.StdState;

public class ErrorState implements StdState
{
  public String msg;
  
  public ErrorState(String lastState)
  {
    msg = "Something very bad happened!\nLast state was:"+lastState;
  }
  
  public StdState parse(InputToken input)
  {
    return this;
  }
  
  public boolean isEndState()
  {
    return true;
  }
}
