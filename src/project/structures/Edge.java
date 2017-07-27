package project.structures;

public class Edge {
	
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
