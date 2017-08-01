package scheduler.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a graph ADT.
 * 
 * @author Luke Tudor
 */
public class Graph {
	
	final static String EDGE_LINK = "->";
	
	// nodes stores all the nodes of a graph by mapping string handles to each node object
	// edges stores all the edges of a graph by mapping string handles to each edge object
	// order stores the each graph element in the order it was received
	private Map<String, Node> nodes = new HashMap<String, Node>();
	private Map<String, Edge> edges = new HashMap<String, Edge>();
	private List<Object> order = new ArrayList<Object>();
	
	// optional graph name
	private String graphName;
	
	public Graph(String name) {
		graphName = name;
	}
	
	public void addNode(Node n) {
		nodes.put(n.getName(), n);
		order.add(n);
	}
	
	public void addEdge(Edge e) {
		Node parent = nodes.get(e.getParent());
		Node child = nodes.get(e.getChild());
		parent.addChild(child);
		child.addParent(parent);
		edges.put(e.getParent() + EDGE_LINK + e.getChild(), e);
		order.add(e);
	}
	
	public Object[] getAllElements() {
		return order.toArray();
	}
	
	public String getGraphName() {
		return graphName;
	}
	
	public void setGraphName(String name) {
		graphName = name;
	}

}
