package project;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	public enum ElementType {NODE, EDGE}
	
	public List<Node> nodes = new ArrayList<Node>();
	
	public List<Edge> edges = new ArrayList<Edge>();
	
	public List<ElementType> order = new ArrayList<ElementType>();

}
