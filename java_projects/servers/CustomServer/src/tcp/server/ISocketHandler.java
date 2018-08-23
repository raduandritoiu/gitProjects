package tcp.server;

import java.net.Socket;

public interface ISocketHandler
{
  String getName();
  void handleSocket(Socket sock);
}
