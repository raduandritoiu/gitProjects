package custom;

import machine.InputToken;

public class ByteToken implements InputToken
{
  byte bt;
  
  public ByteToken(byte b)
  {
    bt = b;
  }
  
  public byte getByte()
  {
    return bt;
  }
}
