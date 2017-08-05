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
	//Why not reference the nodes themselves? we know they exist at the time the edge is created
	private String parent;
	private String child;
	private int weight;
	
	public Edge(String parent, String child, int weight) {
		this.parent = parent;
		this.child = child;
		this.weight = weight;
	}
	
	public String getParent() {
		return parent;
	}
	
	public String getChild() {
		return child;
	}
	
	public int getWeight() {
		return weight;
	}

}
