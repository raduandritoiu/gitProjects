package udp.server;

import java.net.SocketAddress;


// or should I say IUdpSocket
public interface IUdpCommunicator
{
  public String read(SocketAddress remoteAddr);
  public void write(String msg, SocketAddress remoteAddr);
}
