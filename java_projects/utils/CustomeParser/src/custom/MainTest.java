package custom;

import custom.states.PreambulState;
import machine.StateMachine;
import machine.StdState;

public class MainTest
{
  public static void main(String[] argv)
  {
    run();
  }
  
  public static void run()
  {
    StateMachine machine = new StateMachine(new PreambulState());
    
    int xxx=0;
    String str = "<abc>3b3b#abcde67890fghijk";
    byte[] str_b = str.getBytes();
    str_b[9] = 17;
    for (int i = 0; i < str_b.length; i++) 
    {
      machine.parse(new ByteToken(str_b[i]));
      xxx++;
    }
    
    StdState state = machine.crtState();
    
    System.out.println("pm");
  }
}
