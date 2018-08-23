package machine;

public interface StdState
{
  StdState parse(InputToken input);
  boolean isEndState();
}
