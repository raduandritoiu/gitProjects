package radua.servers.client.interfaces;

import java.net.SocketAddress;

public interface IUdpClient 
{
	public String read();
	public void write(String msg);
	public void write(String msg, SocketAddress destAddr);
}
