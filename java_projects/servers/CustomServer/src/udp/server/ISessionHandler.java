package udp.server;

public interface ISessionHandler
{
  String getName();
  void handleSession(UdpSession session);
}
