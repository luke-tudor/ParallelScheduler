package project;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	private enum ElementType {NODE, EDGE}
	
	private List<Node> nodes = new ArrayList<Node>();
	
	private List<Edge> edges = new ArrayList<Edge>();
	
	private List<ElementType> order = new ArrayList<ElementType>();
	
	public void addNode(Node n) {
		nodes.add(n);
		order.add(ElementType.NODE);
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
		order.add(ElementType.EDGE);
	}
	
	public Object[] getAllElements() {
		Object[] elements = new Object[order.size()];
		int nodeCount = 0;
		int edgeCount = 0;
		for (int i = 0; i < elements.length; i++) {
			if (order.get(i) == ElementType.NODE) {
				elements[i] = nodes.get(nodeCount);
				nodeCount++;
			} else {
				elements[i] = edges.get(edgeCount);
				edgeCount++;
			}
		}
		return elements;
	}

}
