package project.structures;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a node element of a graph object.
 * 
 * @author Luke Tudor
 */
public class Node {
	
	private String name;	
	private int weight;
	private int start;
	private int processor;
	
	private Set<Node> parents;
	private Set<Node> children;
	
	public Node(String name, int weight) {
		this.name = name;
		this.weight = weight;
		start = 0;
		processor = 0;
		
		parents = new HashSet<>();
		children = new HashSet<>();
	}
	
	void addParent(Node n) {
		parents.add(n);
	}
	
	void addChild(Node n) {
		children.add(n);
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
