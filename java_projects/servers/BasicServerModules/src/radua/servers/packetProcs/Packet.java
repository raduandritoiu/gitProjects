package radua.servers.packetProcs;

import java.net.SocketAddress;

public class Packet implements IPacket
{
	public final byte[] data;
	public final int dataLen;
	public final SocketAddress remoteAddr;
	public final PacketDirection direction;
	
	public Packet(byte[] nData, int nDataLen, SocketAddress nRemoteAddr, PacketDirection nDirection) 
	{
		data = nData;
		dataLen = nDataLen;
		remoteAddr = nRemoteAddr;
		direction = nDirection;
	}
	
	public byte[] data() { return data; }
	public int dataLen() { return dataLen; }
	public SocketAddress remoteAddr() { return remoteAddr; }
	public PacketDirection direction() { return direction; }
	
	
	public static Packet In(byte[] data, int dataLen, SocketAddress remoteAddr) 
	{
		return new Packet(data, dataLen, remoteAddr, PacketDirection.INCOMING);
	}
	
	public static Packet Out(byte[] data, int dataLen, SocketAddress remoteAddr) 
	{
		return new Packet(data, dataLen, remoteAddr, PacketDirection.OUTGOING);
	}
}
