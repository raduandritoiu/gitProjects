package radua.servers.packetProcs;

import java.net.SocketAddress;

public class GenericPacket implements IPacket
{
	public final int dataLen;
	public final byte[] data;
	public final SocketAddress remoteAddr;
	
	public GenericPacket(int nDataLen, byte[] nData, SocketAddress nRemoteAddr) 
	{
		dataLen = nDataLen;
		data = nData;
		remoteAddr = nRemoteAddr;
	}
	
	public int dataLen() { return dataLen; }
	public byte[] data() { return data; }
	public SocketAddress remoteAddr() { return remoteAddr; }
}
