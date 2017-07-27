package project;

public class Node {
	
	private String name;
	
	private int weight;
	
	private int start;
	
	private int processor;
	
	public Node(String name, int weight) {
		this.name = name;
		this.weight = weight;
		start = 0;
		processor = 0;
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
