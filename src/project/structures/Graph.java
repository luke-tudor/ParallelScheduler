package project.structures;

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
	
	private Map<String, Node> nodes = new HashMap<String, Node>();
	private Map<String, Edge> edges = new HashMap<String, Edge>();
	private List<Object> order = new ArrayList<Object>();
	
	public void addNode(Node n) {
		nodes.put(n.getName(), n);
		order.add(n);
	}
	
	public void addEdge(Edge e) {
		Node parent = nodes.get(e.getParent());
		Node child = nodes.get(e.getChild());
		parent.addChild(child);
		child.addParent(parent);
		edges.put(e.getParent() + "->" + e.getChild(), e);
		order.add(e);
	}
	
	public Object[] getAllElements() {
		return order.toArray();
	}

}
