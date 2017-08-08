package scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import scheduler.io.InputParser;
import scheduler.io.OutputFormatter;
import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;
import scheduler.structures.TreeNode;

/**
 * The main class for the parallel scheduler.
 * 
 * @author Luke Tudor
 */
public class Scheduler {

	private Graph graph;
	private int numProcessors;

	public Scheduler(Graph graph, int numProcessors) {
		this.graph = graph;
		this.numProcessors = numProcessors;
	}

	/**
	 * computes the optimum processing schedule for the graph 
	 * 
	 * TODO: implement scheduling algorithm
	 */
	public Graph computeSchedule() {
		// set of nodes we have evaluated
		Set<TreeNode> closedSet = new HashSet<>();
		// set of nodes we can reach
		PriorityQueue<TreeNode> openSet = new PriorityQueue<>();
		openSet.add(new TreeNode());
	    // the node that this node came from such that it is the most efficient
		Map<TreeNode, TreeNode> cameFrom = new HashMap<TreeNode, TreeNode>();
		
		// the cost to get to this node from the start
		Map<TreeNode, Integer> costTo = new HashMap<TreeNode, Integer>();
		while (!openSet.isEmpty()) {
			// pop from priority queue
			TreeNode current = openSet.remove();
			// if current == goal or complete solution, then we have optimal solution
			closedSet.add(current);

			// find neighbouring nodes, to be cleaned up
			Edge[] childEdges = new Edge[0];
			Object[] otherElements = graph.getAllElements();
			List<Node> otherNodes = new ArrayList<>();
			for (Object o : otherElements) {
				if (o.getClass() == Node.class && !o.equals(new Integer[0])) {
					otherNodes.add((Node) o);
				}
			}
			for (Edge e : childEdges) {
				otherNodes.add(graph.getNode(e.getChild()));
			}

			// for neighbouring nodes
			for (Node n : otherNodes) {
				// if we have done already, don't bother
				if (closedSet.contains(n)) {
					continue;
				}
				// adding a new neighbouring node
				if (!openSet.contains(n)) {
					//openSet.add(n);
				}

				// if this is not a good path, continue

				// if this is a good path, update estimates
			}
		}
		return graph;
	}

	public void makeHeuristic() {
		for (Node n : graph.nodes.values()) {
			n.hvalue = getHValue(n);
		}
		for (Node n : graph.nodes.values()) {
			System.out.println(n.hvalue);
		}
	}

	private int getHValue(Node node) {
		if (node.childEdgeWeights.isEmpty()) {
			node.hvalue = node.getWeight();
			return node.getWeight();
		} else if (node.hvalue != 0) {
			return node.hvalue;
		} else {
			int maxHValue = 0;
			for (Node n : node.childEdgeWeights.keySet()) {
				if (getHValue(n) > maxHValue) {
					maxHValue = getHValue(n);
				}
			}
			maxHValue += node.getWeight();
			node.hvalue = maxHValue;
			return node.hvalue;
		}
	}

	public static void main(String[] args) {
		String inputFileName = args[0];
		int processorNumber = Integer.parseInt(args[1]);

		// Use regular expression to construct output file name from input file name
		// Works by taking the file name without the extension and concatenating with the other half of the new name
		String outputFileName = args[0].split("\\.")[0] + "-output.dot";

		//creates the graph
		InputParser ip = new InputParser(inputFileName);		
		Graph inputGraph = ip.parse();

		//finds the optimum schedule
		Scheduler s = new Scheduler(inputGraph, processorNumber);
		s.makeHeuristic();
		Graph outputGraph = s.computeSchedule();
		outputGraph.setGraphName("output");

		//writes schedule to output file
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph(outputFileName);
	}

}
