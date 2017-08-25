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

	public Node(String name, int weight) {
		this.weight = weight;
		this.name = name;
		start = 0;
		processor = 0;
		
		parentEdgeWeights = new HashMap<Node, Integer>();
		childEdgeWeights = new HashMap<Node, Integer>();
	}
	
	void addParentEdgeWeight(Node parent, Integer weight) {
		parentEdgeWeights.put(parent, weight);
	}
	
	void addChildEdgeWeight(Node child, Integer weight) {
		childEdgeWeights.put(child, weight);
	}
	
	//All necessary getters and setters
	public String getName() {
		return name;
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
	
	public int getWeight() {
		return weight;
	}
	
	public void setBottomLevel(int h) {
		heuristic = h;
	}
	
	public int getBottomLevel() {
		return heuristic;
	}
	
	public Map<Node, Integer> getParentEdgeWeights(){
		return parentEdgeWeights;
	}
	
	public Map<Node, Integer> getChildEdgeWeights(){
		return childEdgeWeights;
	}
	
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
