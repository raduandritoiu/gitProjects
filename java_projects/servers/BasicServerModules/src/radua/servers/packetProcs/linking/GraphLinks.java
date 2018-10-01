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
		graph.parseLink(link);
		graph.visitedLinks = null;
		boolean cycles = graph.checkCycle();
		if (cycles) {
			return null;
		}
		graph.positionGraph();
		graph.printGraph();
		return graph;
	}
	
	
	private final void initNodes()
	{
		visitedLinks = new Hashtable<>();
		firstNodes = new ArrayList<>();
		lastNodes = new ArrayList<>();
		nodes = new ArrayList<>();
	}
	
	
	private final Node parseLink(A_Linking_Base link)
	{
		if (visitedLinks.containsKey(link)) {
			return visitedLinks.get(link);
		}
		
		Node node = new Node(link);
		visitedLinks.put(link, node);

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
		
		nodes.add(node);
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
		visitedNodes = new Hashtable<>();
		for (Node node : firstNodes) {
			if (checkCycle(node)) {
				return true;
			}
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
		for (Node node : firstNodes) {
			Integer x = 0, y = 0;
			positionNode(node, x, y);
		}
	}
	private final void positionNode(Node node, Integer x, Integer y)
	{
		// do with node
		node.x = x;
		node.y = y;
		boolean first = true;
		for (Node fwdNode : node.mFwds) {
			if (first) {
				positionNode(fwdNode, x++, y + 1);
			}
		}
	}
	
	
	
	public final void printGraph()
	{
		for (Node node : firstNodes) {
			printNode(node, 0);
		}
	}
	private final void printNode(Node node, int off)
	{
		String name = node.mName + " ("+node.x+","+node.y+")";
		log.out(name, off);
		off += name.length() + 5;
		for (Node fwdNode : node.mFwds) {
			printNode(fwdNode, off);
		}
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
			mName = link.getClass().getSimpleName();
			mRews = new ArrayList<>();
			mFwds = new ArrayList<>();
			x = 0; y= 0;
		}
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
}
