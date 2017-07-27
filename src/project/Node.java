package project;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private String name;	
	private int weight;
	private int start;
	private int processor;
	
	private List<Node> parents;
	private List<Node> children;
	
	public Node(String name, int weight) {
		this.name = name;
		this.weight = weight;
		start = 0;
		processor = 0;
		
		parents = new ArrayList<>();
		children = new ArrayList<>();
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
