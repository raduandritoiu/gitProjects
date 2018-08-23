

import java.util.Random;

import udp.server.ISessionHandler;
import udp.server.UdpSession;

public class TestUdpSessionHandler implements ISessionHandler
{
  public String getName()
  {
    return "bla bla";
  }
  
  public void handleSession(UdpSession session)
  {
    String recvStr, sendStr;
    
    recvStr = new String(session.initialReq);
    int ws = (new Random().nextInt(10)) * (new Random().nextInt(10));
    try { Thread.sleep(ws * 10); }
    catch(Exception ex){}
    sendStr = "Hello. I am the Server.";
    session.comm.write(sendStr, session.remoteAddr);

    recvStr = session.comm.read(session.remoteAddr);
    ws = (new Random().nextInt(10)) * (new Random().nextInt(10));
    try { Thread.sleep(ws * 10); }
    catch(Exception ex){}
    sendStr = "" + ((char)0) + ((char)24) + "The phrase is MUMULET!";
    session.comm.write(sendStr, session.remoteAddr);
    
    recvStr = session.comm.read(session.remoteAddr);
    ws = (new Random().nextInt(10)) * (new Random().nextInt(10));
    try { Thread.sleep(ws * 10); }
    catch(Exception ex){}
    sendStr = "Any time. Bye bye.";
    session.comm.write(sendStr, session.remoteAddr);
    
    System.out.println("** Serviced clinet " + session.remoteAddr);
  }
}
