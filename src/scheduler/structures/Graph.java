package scheduler.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
		Node parent = nodes.get(e.getParent());
		Node child = nodes.get(e.getChild());
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
	
	public List<Node> getAllParentless() {
		List<Node> li = new ArrayList<>();
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

    public Set<Node> getNeighbours(TreeNode n) {
        Collection<Node> c = nodes.values();
        Set<Node> neighbours = new HashSet<>();
        for (Node n1 : c) {
            Collection<Node> c1 = n1.childEdgeWeights.keySet();
            for (Node n2 : c1) {
                for (Node n3 : n2.parentEdgeWeights.keySet()) {
                    if (!n3.parentEdgeWeights.keySet().isEmpty()) { //Pseudo-code says if all parents are in collection of nodes??
                        neighbours.add(n3);
                    }
                }
            }
        }
        return neighbours;
    }

}

