package radua.servers.packetProcs.linking;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import radua.utils.logs.Log;


public class GraphLinks 
{
	private static GraphLog log = new GraphLog();
	
	
	private Map<A_Linking_Base, Node> visitedLinks;
	private Map<Node, Node> visitedNodes;
	private ArrayList<Node> firstNodes;
	private ArrayList<Node> lastNodes;
	private ArrayList<Node> nodes;
	
	
	public static final GraphLinks CreateGraph(A_Linking_Base link)
	{
		GraphLinks graph = new GraphLinks();
		graph.initNodes();
		graph.parseGrpah(link);
//		boolean cycles = graph.checkCycle();
//		if (cycles) {
//			return null;
//		}
		graph.positionGraph();
		return graph;
	}
	
	
	private final void initNodes()
	{
		firstNodes = new ArrayList<>(3);
		lastNodes = new ArrayList<>(3);
		nodes = new ArrayList<>();
	}
	
	
	private final void parseGrpah(A_Linking_Base link)
	{
		visitedLinks = new Hashtable<>();
		parseLink(link);
		visitedLinks = null;
	}
	private final Node parseLink(A_Linking_Base link)
	{
		if (visitedLinks.containsKey(link)) {
			return visitedLinks.get(link);
		}
		
		Node node = new Node(link);
		visitedLinks.put(link, node);
		nodes.add(node);

		if (link.mOuter != null) {
			Node fNode = parseLink((A_Linking_Base) link.mOuter);
			node.mRews.add(fNode);
		}
		if (link.mOuters != null) {
			for (IOuter outer : link.mOuters) {
				if (outer != null) {
					Node fNode = parseLink((A_Linking_Base) outer);
					node.mRews.add(fNode);
				}
			}
		}
		if (link.mInner != null) {
			Node fNode = parseLink((A_Linking_Base) link.mInner);
			node.mFwds.add(fNode);
		}
		if (link.mInners != null) {
			for (IInner inner : link.mInners) {
				if (inner != null) {
					Node fNode = parseLink((A_Linking_Base) inner);
					node.mFwds.add(fNode);
				}
			}
		}		
		
		if (node.mRews.size() == 0) {
			firstNodes.add(node);
		}
		if (node.mFwds.size() == 0) {
			lastNodes.add(node);
		}
		
		return node;
	}
	
	
	private final boolean checkCycle() 
	{
		for (Node node : firstNodes) {
			visitedNodes = new Hashtable<>();
			if (checkCycle(node)) {
				return true;
			}
			visitedNodes = null;
		}
		return false;
	}
	private final boolean checkCycle(Node node) 
	{
		visitedNodes.put(node, node);
		for (Node fwdNode : node.mFwds) {
			if (visitedNodes.containsKey(fwdNode)) {
				return true;
			}
			checkCycle(fwdNode);
		}
		return false;
	}
	
	
	public final void positionGraph()
	{
		visitedNodes = new Hashtable<>();
		IncInt x = new IncInt(0);
		int y = 0;
		for (Node node : firstNodes) {
			positionNode(node, x, y);
			x.inc();
		}
		visitedNodes = null;
	}
	private final void positionNode(Node node, IncInt x, int y)
	{
		visitedNodes.put(node, node);
		node.x = x.mVal;
		node.y = y;
		boolean first = true;
		for (Node fwdNode : node.mFwds) {
			// node is not positioned 
			if (!visitedNodes.containsKey(fwdNode)) {
				if (!first) { x.inc(); }
				first = false;
				positionNode(fwdNode, x, y+1);
			}
			else {
				repositionNode(fwdNode, y+1);
			}
		}
	}
	private final void repositionNode(Node node, int y)
	{
		if (node.y < y)
			node.y = y;
		for (Node fwdNode : node.mFwds) {
			repositionNode(fwdNode, y+1);
		}
	}
	
	
	public final void printOut()
	{
		visitedNodes = new Hashtable<>();
		for (Node node : firstNodes) {
			printNode(node, 0);
		}
		visitedNodes = null;
	}
	private final void printNode(Node node, int off)
	{
		visitedNodes.put(node, node);
		String name = node.mName + " ("+node.x+","+node.y+")";
		log.out(name, off);
		off += name.length() + 5;
		for (Node fwdNode : node.mFwds) {
			if (!visitedNodes.containsKey(fwdNode)) {
				printNode(fwdNode, off);
			}
		}
	}
	
	
	public final void printGraph()
	{
		int maxX = 0, maxY = 0, maxL = 0;
		for (Node node : nodes) {
			if (node.x > maxX) maxX = node.x;
			if (node.y > maxY) maxY = node.y;
			if (node.mName.length() > maxL) maxL = node.mName.length();
		}
		GraphCanvas canvas = new GraphCanvas(maxX, maxY, maxL, 3, 12, 3);
		for (Node node : nodes) {
			for (Node fwdNode : node.mFwds) {
				canvas.putEdgeNodes(node, fwdNode);
			}
		}
		canvas.printOut(log);
	}
	
	
	
//===================================================================================================
//===================================================================================================
	private static class Node
	{
		private final A_Linking_Base mLink;
		private final ArrayList<Node> mRews;
		private final ArrayList<Node> mFwds;
		private final String mName;
		private int x, y;
		
		public Node(A_Linking_Base link)
		{
			mLink = link;
			mName = mLink.getClass().getSimpleName();
			mRews = new ArrayList<>(3);
			mFwds = new ArrayList<>(3);
			x = 0; y= 0;
		}
		
		public String toString() { return mName; }
	}
	
	
	private static class GraphLog extends Log
	{
		public void out(String str, int off) {
			for (int i = 0; i < off; i++) {
				str = " " + str;
			}
			super.out(str);
		}
	}
	
	
	private static class IncInt
	{
		private int mVal;
		public IncInt(int val) { mVal = val; }
		public void inc() { mVal++; }
	}
	
	
	private static class GraphCanvas
	{
		private final static byte EMPTY = ' ';
		private final static byte VERT = '|';
		private final static byte HORZ = '-';
		private final static byte ANGL = '\\';
		private final static byte STAR = '+';
		private final static byte CROS = '#';
		
		
		private final int mMaxX, mMaxY, mMaxL, mDiffX, mDiffY;
		private final int mHalfY, mLineNum, mLineLen;
		private final byte[][] canvas;
		
		
		public GraphCanvas(int maxX, int maxY, int maxL, int diffX, int diffY, int halfY)
		{
			mMaxX = maxX; mMaxY = maxY; mMaxL = maxL;
			mDiffX = diffX; mDiffY = diffY; mHalfY = halfY;
			
			mLineNum = toLineX(mMaxX) + 1;
			mLineLen = toLineY(mMaxY) + mMaxL;

			canvas = new byte[mLineNum][mLineLen];
			for (int i = 0; i < mLineNum; i++) {
				for (int j = 0; j < mLineLen; j++) {
					canvas[i][j] = EMPTY;
				}
			}
		}
		
		public void putEdgeNodes(Node n1, Node n2) 
		{
			int lineX1 = toLineX(n1.x);
			int lineX2 = toLineX(n2.x);
			int lineY1 = toLineY(n1.y);
			int lineY2 = toLineY(n2.y);
			
			if (lineX1 == lineX2) {
				put(lineX1, lineY1, HORZ, lineY2 - lineY1);
			}
			else {
				put(lineX1, lineY1, HORZ, mHalfY);
				put(lineX1, lineY1 + mHalfY, ANGL);
				put(lineX2, lineY1 + mHalfY, ANGL);
				
				if (lineX1 < lineX2) {
					for (int i = lineX1 + 1; i < lineX2; i++) {
						put(i, lineY1 + mHalfY, VERT);
					}
				}
				else {
					for (int i = lineX2 + 1; i < lineX1; i++) {
						put(i, lineY1 + mHalfY, VERT);
					}
				}
					
				put(lineX2, lineY1 + mHalfY + 1, HORZ, lineY2 - (lineY1 + mHalfY + 1));
			}
			
			put(toLineX(n1.x), toLineY(n1.y), n1.mName);
			put(toLineX(n2.x), toLineY(n2.y), n2.mName);
		}
		
		
		public void put(int lineX, int lineY, String s)
		{
			put(lineX, lineY, s.getBytes());
		}
		public void put(int lineX, int lineY, byte[] b)
		{
			for (int i = 0; i < b.length; i++)
				canvas[lineX][lineY+i] = add(canvas[lineX][lineY+i], b[i]);
		}
		public void put(int lineX, int lineY, byte b)
		{
			put(lineX, lineY, b, 1);
		}
		public void put(int lineX, int lineY, byte b, int len)
		{
			for (int i = 0; i < len; i++)
				canvas[lineX][lineY+i] = add(canvas[lineX][lineY+i], b);
		}
		
		
		public int toLineX(int x) { return x * (1 + mDiffX); }
		public int toLineY(int y) { return y * (mMaxL + mDiffY); }
		
		
		public void printOut(Log log)
		{
			for (int i = 0; i < mLineNum; i++) {
				String line = new String(canvas[i]);
				log.out(line);
			}
		}
		
		
		public static byte add(byte b1, byte b2)
		{
			if (b2 == HORZ) {
				switch (b1) {
					case EMPTY:
						return HORZ;
					case HORZ:
						return HORZ;
					case VERT:
						return CROS;
					case ANGL:
						return STAR;
					case STAR:
						return STAR;
//					case STAR:
//						return STAR;
//					case STAR:
//						return STAR;
					case CROS:
						return CROS;
					default:
						return b2;
				}
			}
			else if (b2 == VERT) {
				switch (b1) {
					case EMPTY:
						return VERT;
					case HORZ:
						return CROS;
					case VERT:
						return VERT;
					case ANGL:
						return STAR;
					case STAR:
						return STAR;
	//				case STAR:
	//					return STAR;
	//				case STAR:
	//					return STAR;
					case CROS:
						return CROS;
					default:
						return b2;
				}
			}
			else if (b2 == ANGL) {
				switch (b1) {
					case EMPTY:
						return ANGL;
					case HORZ:
						return STAR;
					case ANGL:
						return STAR;
					case STAR:
						return STAR;
					default:
						return b2;
				}
			}

			return b2;
		}
	}
}
