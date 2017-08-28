package scheduler.structures;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a representation of a node element of a graph object.
 * 
 * @author Luke Tudor
 */
public class Node{
	
	// name is the string handle for this object
	// weight is the cost of this node
	// start is the earliest start time of this node
	// processor is which processor the node should be run on
	private String name;
	private int start;
	private int processor;
	private int weight;
	private int heuristic;
	
	// parentEdges stores all nodes that have an edge to this node
	// childEdges stores all nodes that this node has an edge to
	private Map<Node, Integer> parentEdgeWeights;
	private Map<Node, Integer> childEdgeWeights;

	// Initialises a new instance of the Node class
	public Node(String name, int weight) {
		this.weight = weight;
		this.name = name;
		start = 0;
		processor = 0;
		
		parentEdgeWeights = new HashMap<Node, Integer>();
		childEdgeWeights = new HashMap<Node, Integer>();
	}
	
	// Adds the cost of the parent node edge
	void addParentEdgeWeight(Node parent, Integer weight) {
		parentEdgeWeights.put(parent, weight);
	}
	
	// Adds the cost of the child node edge
	void addChildEdgeWeight(Node child, Integer weight) {
		childEdgeWeights.put(child, weight);
	}
	
	// All necessary getters and setters
	// Acquires the string handle for this object
	public String getName() {
		return name;
	}
	
	// Set the earliest start time of this node
	public void setStart(int start) {
		this.start = start;
	}
	
	// Acquires the earliest start time of this node
	public int getStart() {
		return start;
	}
	
	// Set the processor which the node runs on
	public void setProcessor(int processor) {
		this.processor = processor;
	}
	
	// Acquires the processor which the node runs on
	public int getProcessor() {
		return processor;
	}
	
	// Acquires the cost of this node
	public int getWeight() {
		return weight;
	}
	
	// Set the bottom level heuristic for the algorithm
	public void setBottomLevel(int h) {
		heuristic = h;
	}
	
	// Acquires the bottom level heuristic for the algorithm
	public int getBottomLevel() {
		return heuristic;
	}
	
	// Acquires the cost of the parent node edge
	public Map<Node, Integer> getParentEdgeWeights(){
		return parentEdgeWeights;
	}
	
	// Acquires the cost of the child node edge
	public Map<Node, Integer> getChildEdgeWeights(){
		return childEdgeWeights;
	}
	
	/**
	 * .equals overridden for efficiency purposes.
	 */
	@Override
	public boolean equals(Object obj) {
		Node n = (Node) obj;
		//Since an edge is assigned by the name of the node, the name will be unique. 
		//checking to see if they are the same is sufficient to know whether they are 
		//the same
		if (name.equals(n.getName())){
			return true;
		}
		return false;
	}
	
}
