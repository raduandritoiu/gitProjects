package radua.servers.packetProcs;

public enum PacketDirection 
{
	INCOMING(1),
	OUTGOING(2),
	UNKNOWN(3);
	
	public final int val;
	
	private PacketDirection(int nVal)
	{
		val = nVal;
	}
}
