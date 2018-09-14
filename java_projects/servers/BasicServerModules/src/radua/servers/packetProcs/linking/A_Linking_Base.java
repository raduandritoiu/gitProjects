package radua.servers.packetProcs.linking;

import java.util.List;

import radua.servers.packetProcs.IPacketHandler;
import radua.servers.packetProcs.IPacketHandlerMulti;
import radua.servers.packetProcs.IPacketHandlerSingle;
import radua.servers.packetProcs.IPacketProvider;
import radua.servers.packetProcs.IPacketProviderMulti;
import radua.servers.packetProcs.IPacketProviderSingle;


/*package_p*/ abstract class A_Linking_Base 
{
	/*package_p*/ boolean isRunning = false;
	/*package_p*/ IPacketProvider provider = null;
	/*package_p*/ IPacketHandler handler = null;
	
	/*package_p*/ List<IPacketProvider> providers = null;
	/*package_p*/ List<IPacketHandler> handlers = null;

	
//===================================================================================================
//== Internal Running ===============================================================================
	protected void internalStart() {}
	protected void internalStop() {}
	protected void internalStopWait() {}
	
	
//===================================================================================================
//== Multiple providers ==============================================================================
	/*package_p*/ final boolean pp_hasProvider(IPacketProvider nProvider)
	{
		if (nProvider == null) return false;
		if (nProvider == provider) return true;
		if (providers == null) return false;
		return handlers.contains(nProvider);
	}
	/*package_p*/ final IPacketProvider pp_addProvider(IPacketProvider nProvider, int pos)
	{
		if (pos == -1) providers.add(nProvider);
		else providers.add(pos, nProvider);
		return nProvider;
	}
	/*package_p*/ final IPacketProvider pp_removeProvider(IPacketProvider nProvider)
	{
		if (providers.remove(nProvider)) return nProvider;
		return null;
	}
	/*package_p*/ final IPacketProvider pp_getProvider(int pos)
	{
		return providers.get(pos);
	}
	/*package_p*/ final int pp_getProviderPos(IPacketProvider nProvider)
	{
		return providers.indexOf(nProvider);
	}
	/*package_p*/ final int pp_getProvidersNum()
	{
		return providers.size();
	}
	
	
//===================================================================================================
//== Multiple handlers ==============================================================================
	/*package_p*/ final boolean pp_hasHandler(IPacketHandler nHandler)
	{
		if (nHandler == null) return false;
		if (nHandler == handler) return true;
		if (handlers == null) return false;
		return handlers.contains(nHandler);
	}
	/*package_p*/ final IPacketHandler pp_addHandler(IPacketHandler nHandler, int pos)
	{
		if (pos == -1) handlers.add(nHandler);
		else handlers.add(pos, nHandler);
		return nHandler;
	}
	/*package_p*/ final IPacketHandler pp_removeHandler(IPacketHandler nHandler)
	{
		if (handlers.remove(nHandler)) return nHandler;
		return null;
	}
	/*package_p*/ final IPacketHandler pp_getHandler(int pos)
	{
		return handlers.get(pos);
	}
	/*package_p*/ final int pp_getHandlerPos(IPacketHandler nHandler)
	{
		return handlers.indexOf(nHandler);
	}
	/*package_p*/ final int pp_getHandlersNum()
	{
		return handlers.size();
	}
	
	
//===================================================================================================
//== Running ========================================================================================
	/*package_p*/ final boolean pp_start()
	{
		// bubble to handlers
		if (handler != null && handler instanceof A_Linking_Base)
			((A_Linking_Base) handler).pp_startHandler();
		if (handlers != null)
			for (IPacketHandler hdl : handlers)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_startHandler();
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to providers
		if (provider != null && provider instanceof A_Linking_Base)
			((A_Linking_Base) provider).pp_startProvider();
		if (providers != null)
			for (IPacketProvider prov : providers)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_startProvider();
		return ret;
	}
	/*package_p*/ final boolean pp_stop()
	{
		// bubble to provider
		if (provider != null && provider instanceof A_Linking_Base)
			((A_Linking_Base) provider).pp_stopProvider();
		if (providers != null)
			for (IPacketProvider prov : providers)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		// bubble to handlers
		if (handler != null && handler instanceof A_Linking_Base)
			((A_Linking_Base) handler).pp_stopHandler();
		if (handlers != null)
			for (IPacketHandler hdl : handlers)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_stopHandler();
		return ret;
	}
	/*package_p*/ final boolean pp_stopWait()
	{
		// bubble to providers
		if (provider != null && provider instanceof A_Linking_Base)
			((A_Linking_Base) provider).pp_stopWaitProvider();
		if (providers != null)
			for (IPacketProvider prov : providers)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handlers
		if (handler != null && handler instanceof A_Linking_Base)
			((A_Linking_Base) handler).pp_stopWaitHandler();
		if (handlers != null)
			for (IPacketHandler hdl : handlers)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_stopHandler();
		return ret;
	}

	
//===================================================================================================
//== Provider Running ===============================================================================
	private final boolean pp_startProvider()
	{
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to providers
		if (provider != null && provider instanceof A_Linking_Base)
			((A_Linking_Base) provider).pp_startProvider();
		if (providers != null)
			for (IPacketProvider prov : providers)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_startProvider();
		return ret;
	}
	private final boolean pp_stopProvider()
	{
		// bubble to providers
		if (provider != null && provider instanceof A_Linking_Base)
			((A_Linking_Base) provider).pp_stopProvider();
		if (providers != null)
			for (IPacketProvider prov : providers)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	private final boolean pp_stopWaitProvider()
	{
		// bubble to providers
		if (provider != null && provider instanceof A_Linking_Base)
			((A_Linking_Base) provider).pp_stopWaitProvider();
		if (providers != null)
			for (IPacketProvider prov : providers)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}
	
	
//===================================================================================================
//== Handler Running ================================================================================
	private final boolean pp_startHandler()
	{
		// bubble to handlers
		if (handler != null && handler instanceof A_Linking_Base)
			((A_Linking_Base) handler).pp_startHandler();
		if (handlers != null)
			for (IPacketHandler hdl : handlers)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_startHandler();
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		return ret;
	}
	private final boolean pp_stopHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		// bubble to handlers
		if (handler != null && handler instanceof A_Linking_Base)
			((A_Linking_Base) handler).pp_stopHandler();
		if (handlers != null)
			for (IPacketHandler hdl : handlers)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_stopHandler();
		return ret;
	}
	private final boolean pp_stopWaitHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handlers
		if (handler != null && handler instanceof A_Linking_Base)
			((A_Linking_Base) handler).pp_stopWaitHandler();
		if (handlers != null)
			for (IPacketHandler hdl : handlers)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_stopWaitHandler();
		return ret;
	}
	
	
//===================================================================================================
//== I Provider Link =================================================================================
	/*package_p*/ final ILinkHandlerProvider pp_linkProvider(IPacketProvider nProvider) // TODO ok
	{
		A_Linking_Base.Link(nProvider, (IPacketHandler) this, false, -1, -1);
		if (nProvider instanceof ILinkHandlerProvider)
			return (ILinkHandlerProvider) nProvider;
		return null;
	}
	/*package_p*/ final IPacketProvider pp_unlinkProvider(IPacketProvider nProvider) // TODO ok
	{
		if (!pp_hasProvider(nProvider)) return null;
		A_Linking_Base.Unlink(nProvider, (IPacketHandler) this);
		return nProvider;
	}
	/*package_p*/ final IPacketProvider pp_unlinkProvider() // TODO ok
	{
		return pp_unlinkProvider(provider);
	}

	
//===================================================================================================
//== I Handler Link ================================================================================
	/*package_p*/ final ILinkHandlerProvider pp_linkHandler(IPacketHandler nHandler) // TODO ok
	{
		A_Linking_Base.Link((IPacketProvider) this, nHandler, true, -1, -1);
		if (nHandler instanceof ILinkHandlerProvider) return
			(ILinkHandlerProvider) nHandler; 
		return null;
	}
	/*package_p*/ final IPacketHandler pp_unlinkHandler(IPacketHandler nHandler) // TODO ok
	{
		if (!pp_hasHandler(nHandler)) return null;
		A_Linking_Base.Unlink((IPacketProvider) this, nHandler);
		return nHandler;
	}
	/*package_p*/ final IPacketHandler pp_unlinkHandler() // TODO ok
	{
		return pp_unlinkHandler(handler);
	}
	
	
//===================================================================================================
//== I Provider Insert ================================================================================
	/** This is for Single Handler (NOT Multi Handler) */
	/*package_p*/ final ILinkHandlerProvider pp_single_insertProvider(ILinkHandlerProvider nProvider) // TODO: ok
	{
		Insert(provider, nProvider, (I_Linking_SingleHandler) this);
		return nProvider;
	}
	/** This is for Multi Handler (NOT Single Handler) */
	/*package_p*/ final ILinkHandlerProvider pp_multi_insertProvider(I_Linking_MultiHandlerProviderNA nProvider) // TODO: ok
	{
		Insert_Provider(nProvider, (I_Linking_MultiHandler) this);
		return nProvider;
	}
	
	
//===================================================================================================
//== I Handler Insert ================================================================================
	/** This is for Provider Single (NOT Provider Multi) */
	/*package_p*/ final ILinkHandlerProvider pp_single_insertHandler(ILinkHandlerProvider nHandler) // TODO: ok
	{
		Insert((I_Linking_ProviderSingle) this, nHandler, handler);
		return nHandler;
	}
	/** This is for Provider Multi (NOT Provider Single) */
	/*package_p*/ final ILinkHandlerProvider pp_multi_insertHandler(I_Linking_NAHandlerProviderMulti nHandler)  // TODO: ok
	{
		Insert_Handler((I_Linking_ProviderMulti) this, nHandler);
		return nHandler;
	}
	
	
//===================================================================================================
//== I Provider Takeout ================================================================================
//		/*package_p*/ final ILinkHandlerProvider pp_takeoutProvider(ILinkHandlerProvider nProvider)
//		{
//			if (nProvider == null || nProvider != provider) return null;
//			IPacketProvider tmp = nProvider.getProvider();
//			Unlink(tmp, nProvider);
//			Unlink(nProvider, (IPacketHandler) this);
//			Link(tmp, (IPacketHandler) this, true);
//			return nProvider;
//		}
//		/*package_p*/ final ILinkHandlerProvider pp_takeoutProvider()
//		{
//			if (provider != null && provider instanceof ILinkHandlerProvider)
//				return pp_takeoutProvider((ILinkHandlerProvider) provider);
//			return null;
//		}

	
//===================================================================================================
//== I Handler Takeout ================================================================================
//	/*package_p*/ final ILinkHandlerProvider pp_takeoutHandler(ILinkHandlerProvider nHandler)
//	{
//		if (nHandler == null || nHandler != handler) return null;
//		IPacketHandler tmp = nHandler.getHandler();
//		Unlink((IPacketProvider) this, nHandler);
//		Unlink(nHandler, tmp);
//		Link((IPacketProvider) this, tmp, true);
//		return nHandler;
//	}
//	/*package_p*/ final ILinkHandlerProvider pp_takeoutHandler()
//	{
//		if (handler != null && handler instanceof ILinkHandlerProvider)
//			return pp_takeoutHandler((ILinkHandlerProvider) handler);
//		return null;
//	}


	
//===================================================================================================
//== I Processor Link ================================================================================
//	/*package_p*/ final ILinkHandlerProvider pp_takeout()
//	{
//		Unlink(provider, (ILinkHandlerProvider) this);
//		Unlink((ILinkHandlerProvider) this, handler);
//		Link(provider, handler, true);
//		return (ILinkHandlerProvider) this;
//	}
	
	
	
//===================================================================================================
//== Link Functions =================================================================================
	private static final void Link(IPacketProvider provider, IPacketHandler handler, 
								   boolean fromProvider, int providerPos, int handlerPos) // TODO ok
	{
		// do not unlink anything ... maybe throw error if already linked
		
		// link new provider and handler
		if (handler != null) {
			if (handler instanceof IPacketHandlerMulti) {
				((IPacketHandlerMulti) handler).addProvider(provider, providerPos);
			} else if (handler instanceof IPacketHandlerSingle) {
				((IPacketHandlerSingle) handler).setProvider(provider);
			}
		}
		if (provider != null) {
			if (provider instanceof IPacketProviderMulti) {
				((IPacketProviderMulti) provider).addHandler(handler, handlerPos);
			} else if (provider instanceof IPacketProviderSingle) {
				((IPacketProviderSingle) provider).setHandler(handler);
			}
		}
		// start one based on the other
		A_Linking_Base provider_a = (A_Linking_Base) provider;
		A_Linking_Base handler_a = (A_Linking_Base) handler;
		if (fromProvider) {
			if (provider_a != null && provider_a.isRunning) {
				handler_a.pp_startHandler();
			}
		} else {
			if (handler_a != null && handler_a.isRunning) {
				provider_a.pp_startProvider();
			}
		}
	}
	
	
	private static final void Unlink(IPacketProvider provider, IPacketHandler handler) // TODO ok
	{
		if (provider instanceof IPacketProviderMulti) {
			((IPacketProviderMulti) provider).removeHandler(handler);
		} else if (provider instanceof IPacketProviderSingle) {
			((IPacketProviderSingle) provider).setHandler(null);
		}
		if (handler instanceof IPacketHandlerMulti) {
			((IPacketHandlerMulti) handler).removeProvider(provider);
		} else if (handler instanceof IPacketHandlerSingle) {
			((IPacketHandlerSingle) handler).setProvider(null);
		}
	}

	
	private static final void Insert(IPacketProvider provider, ILinkHandlerProvider middle, IPacketHandler handler) // TODO: ok
	{
		int p_idx = -1;
		int h_idx = -1;
		if (provider instanceof IPacketProviderMulti) {
			h_idx = ((IPacketProviderMulti) provider).getHandlerPos(handler);
		}
		if (handler instanceof IPacketHandlerMulti) {
			p_idx = ((IPacketHandlerMulti) handler).getProviderPos(provider);
		}
		Unlink(provider, handler);
		Link(middle, handler, false, p_idx, -1);
		Link(provider, middle, true, -1, h_idx);
	}
	
	
	private static final void Insert_Handler(I_Linking_ProviderMulti provider, I_Linking_NAHandlerProviderMulti middle) // TODO: ok
	{
		A_Linking_Base provider_a = (A_Linking_Base) provider;
		for (IPacketHandler handler : provider_a.handlers) {
			int p_idx = -1;
			if (handler instanceof IPacketHandlerMulti) {
				p_idx = ((IPacketHandlerMulti) handler).getProviderPos(provider);
			}
			Unlink(provider, handler);
			Link(middle, handler, false, p_idx, -1);
		}
		Link(provider, middle, true, -1, -1);
	}
	
	
	private static final void Insert_Provider(I_Linking_MultiHandlerProviderNA middle, I_Linking_MultiHandler handler) // TODO: ok
	{
		A_Linking_Base handler_a = (A_Linking_Base) handler;
		for (IPacketProvider provider : handler_a.providers) {
			int h_idx = -1;
			if (provider instanceof IPacketProviderMulti) {
				h_idx = ((IPacketProviderMulti) provider).getHandlerPos(handler);
			}
			Unlink(provider, handler);
			Link(provider, middle, true, -1, h_idx);
		}
		Link(middle, handler, false, -1, -1);
	}
	
	
	
//	private static final void Takeout(IPacketProvider provider, IProcessorLink middle, IPacketHandler handler)
//	{
//		Unlink(provider, middle);
//		Unlink(middle, handler);
//		Link(provider, handler, true);
//	}
}
