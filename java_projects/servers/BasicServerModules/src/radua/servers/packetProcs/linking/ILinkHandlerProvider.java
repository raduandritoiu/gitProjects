package radua.servers.packetProcs.linking;

public interface ILinkHandlerProvider extends I_Linking_Provider, I_Linking_Handler
{
	ILinkHandlerProvider takeout();
}
