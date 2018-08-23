package machine;

public class StateMachine
{
  protected final StdState startState;
  protected StdState crtState;
  protected StdState endState;
  private boolean endReached;
  
  
  public StateMachine(StdState st)
  {
    startState = st;
    crtState = st;
    endState = null;
    endReached = false;
  }
  
  public StdState startState()
  {
    return startState;
  }
  
  public StdState crtState()
  {
    return crtState;
  }
  
  public StdState endState()
  {
    return endState;
  }
  
  public boolean endReached()
  {
    return endReached;
  }
  
  public void parse(InputToken input)
  {
    if (endReached)
      return;
    crtState = crtState.parse(input);
    endReached = testEnd(crtState) || crtState.isEndState();
    if (endReached)
      endState = crtState;
  }
  
  protected boolean testEnd(StdState state)
  {
    return false;
  }
}
