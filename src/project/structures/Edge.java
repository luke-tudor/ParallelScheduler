package project.structures;

/**
 * This class represents an edge of the graph object.
 * 
 * @author Luke Tudor
 */
public class Edge {
	
	// parent represents the start node of an edge
	// child represents the end node of an edge
	// weight is the maximum cost of an edge
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
