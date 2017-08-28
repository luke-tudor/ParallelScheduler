package scheduler.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides a representation of a directed graph ADT.
 * 
 * It uses Node and Edge objects to hold their respective objects,
 * their weights and relative positions in the graph. 
 * 
 * @author Luke Tudor and Warren Yeu
 */
public class Graph {

	// Graphical representation of the link of the edges
	final static String EDGE_LINK = "->";

	// nodes stores all the nodes of a graph by mapping string handles to each node object
	// edges stores all the edges of a graph by mapping string handles to each edge object
	private Map<String, Node> nodes = new HashMap<String, Node>();
	private Map<String, Edge> edges = new HashMap<String, Edge>();

	private Set<Node> parentless = null;

	// Optional graph name
	private String graphName;
	
	// Initialises a new instance of the Graph class
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
	}

	/**
	 *
	 * Adds an edge object to the graph
	 *
	 * @param edge the edge object to add to the graph
	 */
	public void addEdge(Edge edge) {
		Node parent = edge.getParent();
		Node child = edge.getChild();
		parent.addChildEdgeWeight(child, edge.getWeight());
		child.addParentEdgeWeight(parent, edge.getWeight());
		edges.put(edge.getParent() + EDGE_LINK + edge.getChild(), edge);
	}

	/**
	 * retrieves all graph elements (nodes and edges) in the order that they were added to the graph
	 *
	 * @return Object[] containing all nodes and edges of the graph
	 */

	// Acquires the name of the representation of the directed graph ADT
	public String getGraphName() {
		return graphName;
	}

	// Set the name of the representation of the directed graph ADT
	public void setGraphName(String name) {
		graphName = name;
	}

	/** 
	 * Acquires the node of the directed graph ADT
	 *
	 * @param String of the name of the graph
	 */
	public Node getNode(String s) {
		return nodes.get(s);
	}

	/**
	 * Finds all nodes in the graph that don't have any children, aka. the final nodes
	 * 
	 * @return List<Node> of all the Node objects without children
	 */
	public List<Node> getAllChildless() {
		List<Node> li = new ArrayList<>();
		for (Node n : nodes.values()) {
			if (n.getChildEdgeWeights().isEmpty()) {
				li.add(n);
			}
		}
		return li;
	}

	/**
	 * Finds all nodes in the graph without parents, aka. the root nodes
	 * 
	 * @return List<Node> of all the root nodes in the graph
	 */
	public Set<Node> getAllParentless() {
		if (parentless == null) {
			parentless = new HashSet<>();
			for (Node n : nodes.values()) {
				if (n.getParentEdgeWeights().isEmpty()) {
					parentless.add(n);
				}
			}
		}
		return parentless;
	}

	// Acquires all the nodes in the directed graph ADT
	public Collection<Node> getAllNodes() {
		return nodes.values();
	}

	// Acquires all the node edges in the directed graph ADT
	public Collection<Edge> getAllEdges() {
		return edges.values();
	}

	/**
	 * Finds all the nodes that can currently be reached from the partial schedule treeNode. The set of 
	 * nodes returned by this method are all the nodes that can legally be added to the partial schedule
	 * 
	 * @return Set<Node> All the nodes that can be reached from the given partial schedule.
	 */
	public Set<Node> getNeighbours(TreeNode treeNode) {
		// Get all nodes that are in this partial schedule
		Set<Node> scheduled = new HashSet<>();
		while (treeNode != null && treeNode.getNode() != null) {
			scheduled.add(treeNode.getNode());
			treeNode = treeNode.getParent();
		}
		Set<Node> neighbours = new HashSet<>();		
		// For each of those nodes, find their children
		Set<Node> parentless = getAllParentless();
		for (Node node : parentless) {
			if (!scheduled.contains(node)) {
				neighbours.add(node);
			}
		}

		for (Node node : scheduled) {
			Collection<Node> children = node.getChildEdgeWeights().keySet();
			ChildLoop:
				for (Node child : children) {
					// For each child not in the scheduled set
					if (!scheduled.contains(child)) {
						// For each child, if they have a parent not in the partial schedule, that child is not reachable
						for (Node parent : child.getParentEdgeWeights().keySet()) {
							if (!scheduled.contains(parent)) {
								continue ChildLoop;
							}
						}
						// If child is reachable, it's a neighbour
						neighbours.add(child);
					}
				}
		}
		return neighbours;
	}

	/**
	 * .equals overridden for efficiency purposes.
	 */
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

