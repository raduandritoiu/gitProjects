package udp.server;

import java.net.SocketAddress;

public class UdpSession
{
  public final SocketAddress remoteAddr;
  public final IUdpCommunicator comm;
  public byte[] initialReq;
  
  public UdpSession(SocketAddress nRemoteAddr, IUdpCommunicator nServer, byte[] nInitial)
  {
    remoteAddr = nRemoteAddr;
    comm = nServer;
    initialReq = nInitial;
  }
}
