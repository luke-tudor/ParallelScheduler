package scheduler.structures;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides a representation of a node element of a graph object.
 * 
 * @author Luke Tudor
 */
public class Node {
	
	// name is the string handle for this object
	// weight is the cost of this node
	// start is the earliest start time of this node
	// processor is which processor the node should be run on
	private String name;	
	private int weight;
	private int start;
	private int processor;
	
	// parentEdges stores all nodes that have an edge to this node
	// childEdges stores all nodes that this node has an edge to
	private Set<Edge> parentEdges;
	private Set<Edge> childEdges;

	public Node(String name, int weight) {
		this.name = name;
		this.weight = weight;
		start = 0;
		processor = 0;
		
		parentEdges = new HashSet<>();
		childEdges = new HashSet<>();
	}
	
	void addParentEdge(Edge e) {
		parentEdges.add(e);
	}
	
	void addChildEdge(Edge e) {
		childEdges.add(e);
	}
	
	public String getName() {
		return name;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setProcessor(int processor) {
		this.processor = processor;
	}
	
	public int getProcessor() {
		return processor;
	}

}
