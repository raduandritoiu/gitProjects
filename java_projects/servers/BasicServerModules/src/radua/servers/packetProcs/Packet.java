package radua.servers.packetProcs;

import java.net.SocketAddress;

public class Packet implements IPacket
{
	private final byte[] mData;
	private final int mDataLen;
	private final SocketAddress mRemoteAddr;
	private final PacketDirection mDirection;
	
	public Packet(byte[] nData, int nDataLen, SocketAddress nRemoteAddr, PacketDirection nDirection) 
	{
		mData = nData;
		mDataLen = nDataLen;
		mRemoteAddr = nRemoteAddr;
		mDirection = nDirection;
	}
	
	public byte[] data() { return mData; }
	public int dataLen() { return mDataLen; }
	public SocketAddress remoteAddr() { return mRemoteAddr; }
	public PacketDirection direction() { return mDirection; }
	
	public Packet clone()
	{
		return new Packet(mData, mDataLen, mRemoteAddr, mDirection);
	}
	
	
	
	public static Packet In(byte[] data, int dataLen, SocketAddress remoteAddr) 
	{
		return new Packet(data, dataLen, remoteAddr, PacketDirection.INCOMING);
	}
	public static Packet Out(byte[] data, int dataLen, SocketAddress remoteAddr) 
	{
		return new Packet(data, dataLen, remoteAddr, PacketDirection.OUTGOING);
	}
}
