package project;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	private List<Node> nodes = new ArrayList<Node>();
	
	private List<Edge> edges = new ArrayList<Edge>();
	
	private List<Object> order = new ArrayList<Object>();
	
	public void addNode(Node n) {
		nodes.add(n);
		order.add(n);
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
		order.add(e);
	}
	
	public Object[] getAllElements() {
		return order.toArray();
	}

}
