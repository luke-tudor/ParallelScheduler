package scheduler;

import java.util.PriorityQueue;
import java.util.Set;

import scheduler.io.InputParser;
import scheduler.io.OutputFormatter;
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
		PriorityQueue<TreeNode> q = new PriorityQueue<>();
		q.add(new TreeNode(numProcessors));
		while (!q.isEmpty()) {
			// pop from priority queue
			TreeNode current = q.remove();
			// if current == goal or complete solution, then we have optimal solution
			if (current.height == graph.getAllNodes().size()) {
				System.out.println("found it");
				TreeNode tail = current;
				while (tail.recentNode != null) {
					tail.recentNode.setProcessor(tail.recentProcessor);
					tail.recentNode.setStart(tail.recentStartTime);
					System.out.println(tail.recentNode.getName() + tail.recentStartTime);
					tail = tail.parent;
				}
				System.exit(0);
			}

			// find neighbouring nodes
			Set<Node> neighbours = graph.getNeighbours(current);
			/*List<Node> neighbour;
			if (current.recentNode == null) {
				neighbour = new ArrayList<>(graph.getAllParentless());
			} else {
				neighbour = new ArrayList<>(current.recentNode.childEdgeWeights.keySet());
			}
			if (current.recentNode != null && current.parent.recentNode != null) {
				for (Node n : current.parent.recentNode.childEdgeWeights.keySet()) {
					neighbour.add(n);
				}
			}*/
			
			for (int i = 0; i < numProcessors; i++) {
				for (Node n : neighbours) {
					if (current.parent == null) {
						q.add(new TreeNode(current, n, i, 0, 1, new int[numProcessors]));
					} else {
						q.add(new TreeNode(current, n, i));
					}
				}
			}

		}
		System.exit(1);
		return graph;
	}

	public void makeHeuristic() {
		for (Node n : graph.nodes.values()) {
			n.hvalue = getHValue(n);
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
