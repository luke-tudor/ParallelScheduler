package scheduler.structures;

/**
 * Provides a representation of an edge of the graph.
 * Contains 
 * 	- a reference to the starting Node
 * 	- a reference to the ending Node
 * 	- an int representing the weight of the Node
 * 
 * Once assigned using the constructor, the fields cannot be changed.
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
	
	// Initialises a new instance of the Edge class
	public Edge(Node parent, Node child, int weight) {
		this.parent = parent;
		this.child = child;
		this.weight = weight;
	}
	
	// Acquires the start node of the edge
	public Node getParent() {
		return parent;
	}
	
	// Acquires the end node of the edge
	public Node getChild() {
		return child;
	}
	
	// Acquires the weight of the edge
	public int getWeight() {
		return weight;
	}
	
	/**
	 * .equals overridden for efficiency purposes. This assumes that there will only be one edge 
	 * between two given nodes, therefore the weight of the edge doesn't need to be checked. 
	 */
	@Override
	public boolean equals(Object obj) {
		Edge e = (Edge) obj;
		if (parent.equals(e.getParent())
				&& child.equals(e.getChild())){
			return true;
		}
		return false;
	}

}
