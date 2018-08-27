package radua.servers.server.udp;

import java.net.DatagramPacket;

public class GenericUdpHandler implements IUdpHandler 
{
	
	public boolean isRunning()
	{
		return false;
	}

	public boolean start()
	{
		return false;
	}

	public boolean stop()
	{
		return false;
	}

	public boolean stopWait(){
		return false;
	}

	public boolean startHandler(){
		return false;
	}

	public boolean stopHandler(){
		return false;
	}

	public boolean stopWaitHandler(){
		return false;
	}

	public void setNotifier(IUdpNotifier notifier)
	{
	}

	public void handlePacket(DatagramPacket packet)
	{
	}
}
