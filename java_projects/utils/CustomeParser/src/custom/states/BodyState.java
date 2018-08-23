package custom.states;

import machine.InputToken;
import machine.StdState;

public class BodyState implements StdState
{
  private int len = 0;
  private int crt = 0;
  private byte crc = 0;
  byte[] msg;
  
  public BodyState(int l)
  {
    len = l;
    msg = new byte[len];
  }
  
  public StdState parse(InputToken input)
  {
    msg[crt] = input.getByte();
    crc = (byte) (crc | msg[crt]);
    crt ++;
    if (crt > len)
      new CrcState(crc);
    return this;
  }
  
  public boolean isEndState()
  {
    return false;
  }
}
