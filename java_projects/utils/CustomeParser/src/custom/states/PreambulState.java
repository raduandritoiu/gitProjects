package custom.states;

import machine.InputToken;
import machine.StdState;

public class PreambulState implements StdState
{
  private int len = 0;
  
  public StdState parse(InputToken input)
  {
    len++;
    if (len == 1 && input.getByte() == '<')
      return this;
    
    if (len == 2 && input.getByte() == 'a')
      return this;
    
    if (len == 3 && input.getByte() == 'b')
      return this;
    
    if (len == 4 && input.getByte() == 'c')
      return this;
    
    if (len == 5 && input.getByte() == '>')
      return new AddressState();

    return new ErrorState(this.toString());
  }
  
  public boolean isEndState()
  {
    return false;
  }
}
