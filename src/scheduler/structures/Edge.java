package scheduler.structures;

/**
 * Provides a representation of an edge of the graph.
 * 
 * @author Luke Tudor
 */
public class Edge {
	
	// parent represents the start node of the edge
	// child represents the end node of the edge
	// weight is the weight of the edge
	private Node parent;
	private Node child;
	private int weight;
	
	public Edge(Node parent, Node child, int weight) {
		this.parent = parent;
		this.child = child;
		this.weight = weight;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public Node getChild() {
		return child;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public boolean equals(Object obj) {
		Edge e = (Edge) obj;
		if (parent.equals(e.getParent())
				&& child.equals(getChild())
				&& weight == e.getWeight()) {
			return true;
		}
		return false;
	}

}
