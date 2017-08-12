package scheduler.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides a representation of a graph ADT.
 * 
 * @author Luke Tudor and Warren Yeu
 */
public class Graph {

	final static String EDGE_LINK = "->";

	// nodes stores all the nodes of a graph by mapping string handles to each node object
	// edges stores all the edges of a graph by mapping string handles to each edge object
	// order stores the each graph element in the order it was received
	public Map<String, Node> nodes = new HashMap<String, Node>();
	private Map<String, Edge> edges = new HashMap<String, Edge>();
	private List<Object> order = new ArrayList<Object>();

	// optional graph name
	private String graphName;

	public Graph(String name) {
		graphName = name;
	}

	/**
	 *
	 * Adds a node to the graph.
	 *
	 * @param n the Node object to add to the graph.
	 */
	public void addNode(Node n) {
		nodes.put(n.getName(), n);
		order.add(n);
	}

	/**
	 *
	 * Adds an edge object to the graph
	 *
	 * @param e the edge object to add to the graph
	 */
	public void addEdge(Edge e) {
		Node parent = e.getParent();
		Node child = e.getChild();
		parent.addChildEdgeWeight(child, e.getWeight());
		child.addParentEdgeWeight(parent, e.getWeight());
		edges.put(e.getParent() + EDGE_LINK + e.getChild(), e);
		order.add(e);
	}

	/**
	 * retrieves all graph elements (nodes and edges) in the order that they were added to the graph
	 *
	 * @return Object[] containing all nodes and edges of the graph
	 */
	public Object[] getAllElements() {
		return order.toArray();
	}

	public String getGraphName() {
		return graphName;
	}

	public void setGraphName(String name) {
		graphName = name;
	}

	public Node getNode(String s) {
		return nodes.get(s);
	}

	public List<Node> getAllChildless() {
		List<Node> li = new ArrayList<>();
		for (Node n : nodes.values()) {
			if (n.childEdgeWeights.isEmpty()) {
				li.add(n);
			}
		}
		return li;
	}

	public Set<Node> getAllParentless() {
		Set<Node> li = new HashSet<>();
		for (Node n : nodes.values()) {
			if (n.parentEdgeWeights.isEmpty()) {
				li.add(n);
			}
		}
		return li;
	}

	public Collection<Node> getAllNodes() {
		return nodes.values();
	}
	
	public Collection<Edge> getAllEdges() {
		return edges.values();
	}

	/**
	 * This method finds all the nodes that can currently be reached from the current partial schedule n
	 */
	public Set<Node> getNeighbours(TreeNode n) {
		// Get all nodes that are in this partial schedule
		Set<Node> scheduled = new HashSet<>();
		while (n != null) {
			scheduled.add(n.recentNode);
			n = n.parent;
		}
		
		// For each of those nodes, find their children
		Set<Node> neighbours = new HashSet<>();
		for (Node node : scheduled) {
			Collection<Node> children = node.childEdgeWeights.keySet();
			ChildLoop:
				for (Node child : children) {
					// For each child, if they have a parent not in the partial schedule, that child is not reachable
					for (Node parent : child.parentEdgeWeights.keySet()) {
						if (!scheduled.contains(parent)) {
							continue ChildLoop;
						}
					}
					// If child is reachable, it's a neighbour
					neighbours.add(child);
				}
		}
		return neighbours;
	}
	
	@Override
    public boolean equals(Object obj) {
    	Graph g = (Graph) obj;
    	
    	Collection<Node> c = g.getAllNodes();
    	
    	if (c.size() != nodes.size()) {
    		return false;
    	}
    	
    	for (Node n : nodes.values()) {
    		
    		boolean bool = false;
    		
    		for (Node n1 : c) {
    			if (n.equals(n1)) {
    				bool = true;
    				break;
    			}
    		}
    		
    		if (!bool) {
    			return false;
    		}
    		
    	}
    	
    	Collection<Edge> c1 = g.getAllEdges();
    	
    	if (c1.size() != edges.size()) {
    		return false;
    	}
    	
    	for (Edge e : edges.values()) {

    		boolean bool1 = false;
    		
    		for (Edge e1 : c1) {
    			if (e1.equals(e)) {
    				bool1 = true;
    				break;
    			}
    		}
    		
    		if (!bool1) {
    			return false;
    		}
    		
    	}
    	
    	return true;
    }

}

