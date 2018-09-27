package radua.servers.packetProcs.linking;

import java.util.List;


/*package_p*/ abstract class A_Linking_Base 
{
	/*package_p*/ boolean isRunning = false;
	/*package_p*/ IOuter mOuter = null;
	/*package_p*/ IInner mInner = null;
	
	/*package_p*/ List<IOuter> mOuters = null;
	/*package_p*/ List<IInner> mInners = null;

	
//===================================================================================================
//== Internal Running ===============================================================================
	protected void internalStart() {}
	protected void internalStop() {}
	protected void internalStopWait() {}
	
	
//===================================================================================================
//== Multiple outers ==============================================================================
	/*package_p*/ final boolean pp_hasOuter(IOuter outer)
	{
		if (outer == null) return false;
		if (outer == mOuter) return true;
		if (mOuters == null) return false;
		return mInners.contains(outer);
	}
	/*package_p*/ final IOuter pp_addOuter(IOuter outer, int pos)
	{
		if (pos == -1) mOuters.add(outer);
		else mOuters.add(pos, outer);
		return outer;
	}
	/*package_p*/ final IOuter pp_removeOuter(IOuter outer)
	{
		if (mOuters.remove(outer)) return outer;
		return null;
	}
	/*package_p*/ final IOuter pp_getOuter(int pos)
	{
		return mOuters.get(pos);
	}
	/*package_p*/ final int pp_getOuterPos(IOuter outer)
	{
		return mOuters.indexOf(outer);
	}
	/*package_p*/ final int pp_getOuterNum()
	{
		return mOuters.size();
	}
	
	
//===================================================================================================
//== Multiple Inners ==============================================================================
	/*package_p*/ final boolean pp_hasInner(IInner inner)
	{
		if (inner == null) return false;
		if (inner == mInner) return true;
		if (mInners == null) return false;
		return mInners.contains(inner);
	}
	/*package_p*/ final IInner pp_addInner(IInner inner, int pos)
	{
		if (pos == -1) mInners.add(inner);
		else mInners.add(pos, inner);
		return inner;
	}
	/*package_p*/ final IInner pp_removeInner(IInner inner)
	{
		if (mInners.remove(inner)) return inner;
		return null;
	}
	/*package_p*/ final IInner pp_getInner(int pos)
	{
		return mInners.get(pos);
	}
	/*package_p*/ final int pp_getInnerPos(IInner inner)
	{
		return mInners.indexOf(inner);
	}
	/*package_p*/ final int pp_getInnersNum()
	{
		return mInners.size();
	}
	
	
//===================================================================================================
//== Running ========================================================================================
	/*package_p*/ final boolean pp_start()
	{
		// bubble to Inners
		if (mInner != null && mInner instanceof A_Linking_Base)
			((A_Linking_Base) mInner).pp_startInner();
		if (mInners != null)
			for (IInner hdl : mInners)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_startInner();
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to Outers
		if (mOuter != null && mOuter instanceof A_Linking_Base)
			((A_Linking_Base) mOuter).pp_startOuter();
		if (mOuters != null)
			for (IOuter prov : mOuters)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_startOuter();
		return ret;
	}
	/*package_p*/ final boolean pp_stop()
	{
		// bubble to Outers
		if (mOuter != null && mOuter instanceof A_Linking_Base)
			((A_Linking_Base) mOuter).pp_stopOuter();
		if (mOuters != null)
			for (IOuter prov : mOuters)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_stopOuter();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		// bubble to Inners
		if (mInner != null && mInner instanceof A_Linking_Base)
			((A_Linking_Base) mInner).pp_stopInner();
		if (mInners != null)
			for (IInner hdl : mInners)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_stopInner();
		return ret;
	}
	/*package_p*/ final boolean pp_stopWait()
	{
		// bubble to Outers
		if (mOuter != null && mOuter instanceof A_Linking_Base)
			((A_Linking_Base) mOuter).pp_stopWaitOuter();
		if (mOuters != null)
			for (IOuter prov : mOuters)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_stopWaitOuter();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to Inners
		if (mInner != null && mInner instanceof A_Linking_Base)
			((A_Linking_Base) mInner).pp_stopWaitInner();
		if (mInners != null)
			for (IInner hdl : mInners)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_stopInner();
		return ret;
	}

	
//===================================================================================================
//== Outer Running ===============================================================================
	private final boolean pp_startOuter()
	{
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to Outers
		if (mOuter != null && mOuter instanceof A_Linking_Base)
			((A_Linking_Base) mOuter).pp_startOuter();
		if (mOuters != null)
			for (IOuter prov : mOuters)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_startOuter();
		return ret;
	}
	private final boolean pp_stopOuter()
	{
		// bubble to Outers
		if (mOuter != null && mOuter instanceof A_Linking_Base)
			((A_Linking_Base) mOuter).pp_stopOuter();
		if (mOuters != null)
			for (IOuter prov : mOuters)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_stopOuter();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	private final boolean pp_stopWaitOuter()
	{
		// bubble to Outers
		if (mOuter != null && mOuter instanceof A_Linking_Base)
			((A_Linking_Base) mOuter).pp_stopWaitOuter();
		if (mOuters != null)
			for (IOuter prov : mOuters)
				if (prov != null && prov instanceof A_Linking_Base)
					((A_Linking_Base) prov).pp_stopWaitOuter();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}
	
	
//===================================================================================================
//== Inner Running ================================================================================
	private final boolean pp_startInner()
	{
		// bubble to Inners
		if (mInner != null && mInner instanceof A_Linking_Base)
			((A_Linking_Base) mInner).pp_startInner();
		if (mInners != null)
			for (IInner hdl : mInners)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_startInner();
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		return ret;
	}
	private final boolean pp_stopInner()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		// bubble to Inners
		if (mInner != null && mInner instanceof A_Linking_Base)
			((A_Linking_Base) mInner).pp_stopInner();
		if (mInners != null)
			for (IInner hdl : mInners)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_stopInner();
		return ret;
	}
	private final boolean pp_stopWaitInner()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to Inners
		if (mInner != null && mInner instanceof A_Linking_Base)
			((A_Linking_Base) mInner).pp_stopWaitInner();
		if (mInners != null)
			for (IInner hdl : mInners)
				if (hdl != null && hdl instanceof A_Linking_Base)
					((A_Linking_Base) hdl).pp_stopWaitInner();
		return ret;
	}
	
	
//===================================================================================================
//== I Outer Link =================================================================================
	/*package_p*/ final IMiddle pp_linkOuter(IOuter outer) // TODO ok
	{
		A_Linking_Base.Link(outer, (IInner) this, false, -1, -1);
		if (outer instanceof IMiddle)
			return (IMiddle) outer;
		return null;
	}
	/*package_p*/ final IOuter pp_unlinkOuter(IOuter outer) // TODO ok
	{
		if (!pp_hasOuter(outer)) return null;
		A_Linking_Base.Unlink(outer, (IInner) this);
		return outer;
	}
	/*package_p*/ final IOuter pp_unlinkOuter() // TODO ok
	{
		return pp_unlinkOuter(mOuter);
	}

	
//===================================================================================================
//== I Inner Link ================================================================================
	/*package_p*/ final IMiddle pp_linkInner(IInner inner) // TODO ok
	{
		A_Linking_Base.Link((IOuter) this, inner, true, -1, -1);
		if (inner instanceof IMiddle) return
			(IMiddle) inner; 
		return null;
	}
	/*package_p*/ final IInner pp_unlinkInner(IInner inner) // TODO ok
	{
		if (!pp_hasInner(inner)) return null;
		A_Linking_Base.Unlink((IOuter) this, inner);
		return inner;
	}
	/*package_p*/ final IInner pp_unlinkInner() // TODO ok
	{
		return pp_unlinkInner(mInner);
	}
	
	
//===================================================================================================
//== I Outer Insert ================================================================================
	/** This is for Single Inner (NOT Multi Inner) */
	/*package_p*/ final IMiddle pp_single_insertOuter(IMiddle outer) // TODO: ok
	{
		Insert(mOuter, outer, (I_Linking_Inner_Single) this);
		return outer;
	}
	/** This is for Multi Inner (NOT Single Inner) */
	/*package_p*/ final IMiddle pp_multi_insertOuter(I_Linking_Middle_Multi_NA outer) // TODO: ok
	{
		Insert_Outer(outer, (I_Linking_Inner_Multi) this);
		return outer;
	}
	
	
//===================================================================================================
//== I Inner Insert ================================================================================
	/** This is for Outer Single (NOT Outer Multi) */
	/*package_p*/ final IMiddle pp_single_insertInner(IMiddle inner) // TODO: ok
	{
		Insert((I_Linking_Outer_Single) this, inner, mInner);
		return inner;
	}
	/** This is for Outer Multi (NOT Outer Single) */
	/*package_p*/ final IMiddle pp_multi_insertInner(I_Linking_Middle_NA_Multi inner)  // TODO: ok
	{
		Insert_Inner((I_Linking_Outer_Multi) this, inner);
		return inner;
	}
	
	
//===================================================================================================
//== I Outer Takeout ================================================================================
//		/*package_p*/ final ILinkInnerOuter pp_takeoutOuter(ILinkInnerOuter nOuter)
//		{
//			if (nOuter == null || nOuter != outer) return null;
//			IPacketOuter tmp = nOuter.getOuter();
//			Unlink(tmp, nOuter);
//			Unlink(nOuter, (IPacketInner) this);
//			Link(tmp, (IPacketInner) this, true);
//			return nOuter;
//		}
//		/*package_p*/ final ILinkInnerOuter pp_takeoutOuter()
//		{
//			if (outer != null && outer instanceof ILinkInnerOuter)
//				return pp_takeoutOuter((ILinkInnerOuter) outer);
//			return null;
//		}

	
//===================================================================================================
//== I Inner Takeout ================================================================================
//	/*package_p*/ final ILinkInnerOuter pp_takeoutInner(ILinkInnerOuter nInner)
//	{
//		if (nInner == null || nInner != inner) return null;
//		IPacketInner tmp = nInner.getInner();
//		Unlink((IPacketOuter) this, nInner);
//		Unlink(nInner, tmp);
//		Link((IPacketOuter) this, tmp, true);
//		return nInner;
//	}
//	/*package_p*/ final ILinkInnerOuter pp_takeoutInner()
//	{
//		if (inner != null && inner instanceof ILinkInnerOuter)
//			return pp_takeoutInner((ILinkInnerOuter) inner);
//		return null;
//	}


	
//===================================================================================================
//== I Processor Link ================================================================================
//	/*package_p*/ final ILinkInnerOuter pp_takeout()
//	{
//		Unlink(outer, (ILinkInnerOuter) this);
//		Unlink((ILinkInnerOuter) this, inner);
//		Link(outer, inner, true);
//		return (ILinkInnerOuter) this;
//	}
	
	
	
//===================================================================================================
//== Link Functions =================================================================================
	private static final void Link(IOuter outer, IInner inner, 
								   boolean fromOuter, int outerPos, int innerPos) // TODO ok
	{
		// do not unlink anything ... maybe throw error if already linked
		
		// link new outer and inner
		if (inner != null) {
			if (inner instanceof I_Linking_Inner_Multi) {
				((I_Linking_Inner_Multi) inner).addOuter(outer, outerPos);
			} else if (inner instanceof I_Linking_Inner_Single) {
				((I_Linking_Inner_Single) inner).setOuter(outer);
			}
		}
		if (outer != null) {
			if (outer instanceof I_Linking_Outer_Multi) {
				((I_Linking_Outer_Multi) outer).addInner(inner, innerPos);
			} else if (outer instanceof I_Linking_Outer_Single) {
				((I_Linking_Outer_Single) outer).setInner(inner);
			}
		}
		// start one based on the other
		A_Linking_Base outer_a = (A_Linking_Base) outer;
		A_Linking_Base inner_a = (A_Linking_Base) inner;
		if (fromOuter) {
			if (outer_a != null && outer_a.isRunning) {
				inner_a.pp_startInner();
			}
		} else {
			if (inner_a != null && inner_a.isRunning) {
				outer_a.pp_startOuter();
			}
		}
	}
	
	
	private static final void Unlink(IOuter outer, IInner inner) // TODO ok
	{
		if (outer instanceof I_Linking_Outer_Multi) {
			((I_Linking_Outer_Multi) outer).removeInner(inner);
		} else if (outer instanceof I_Linking_Outer_Single) {
			((I_Linking_Outer_Single) outer).setInner(null);
		}
		if (inner instanceof I_Linking_Inner_Multi) {
			((I_Linking_Inner_Multi) inner).removeOuter(outer);
		} else if (inner instanceof I_Linking_Inner_Single) {
			((I_Linking_Inner_Single) inner).setOuter(null);
		}
	}

	
	private static final void Insert(IOuter outer, IMiddle middle, IInner inner) // TODO: ok
	{
		int p_idx = -1;
		int h_idx = -1;
		if (outer instanceof I_Linking_Outer_Multi) {
			h_idx = ((I_Linking_Outer_Multi) outer).getInnerPos(inner);
		}
		if (inner instanceof I_Linking_Inner_Multi) {
			p_idx = ((I_Linking_Inner_Multi) inner).getOuterPos(outer);
		}
		Unlink(outer, inner);
		Link(middle, inner, false, p_idx, -1);
		Link(outer, middle, true, -1, h_idx);
	}
	
	
	private static final void Insert_Inner(I_Linking_Outer_Multi outer, I_Linking_Middle_NA_Multi middle) // TODO: ok
	{
		A_Linking_Base outer_a = (A_Linking_Base) outer;
		for (IInner inner : outer_a.mInners) {
			int p_idx = -1;
			if (inner instanceof I_Linking_Inner_Multi) {
				p_idx = ((I_Linking_Inner_Multi) inner).getOuterPos(outer);
			}
			Unlink(outer, inner);
			Link(middle, inner, false, p_idx, -1);
		}
		Link(outer, middle, true, -1, -1);
	}
	
	
	private static final void Insert_Outer(I_Linking_Middle_Multi_NA middle, I_Linking_Inner_Multi inner) // TODO: ok
	{
		A_Linking_Base inner_a = (A_Linking_Base) inner;
		for (IOuter outer : inner_a.mOuters) {
			int h_idx = -1;
			if (outer instanceof I_Linking_Outer_Multi) {
				h_idx = ((I_Linking_Outer_Multi) outer).getInnerPos(inner);
			}
			Unlink(outer, inner);
			Link(outer, middle, true, -1, h_idx);
		}
		Link(middle, inner, false, -1, -1);
	}
	
	
	
//	private static final void Takeout(IPacketOuter outer, IProcessorLink middle, IPacketInner inner)
//	{
//		Unlink(outer, middle);
//		Unlink(middle, inner);
//		Link(outer, inner, true);
//	}
}
