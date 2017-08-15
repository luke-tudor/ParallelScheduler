package scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

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

	// This is the queue that all the threads will be working off, it blocks if multiple threads wish to access it at once and may not even
	// be necessary for multi-threading
	private PriorityBlockingQueue<TreeNode> q = new PriorityBlockingQueue<>();
	// This executor is going to be executing a "processNode()" task, whatever that ends up being
	private ExecutorService exe;

	public Scheduler(Graph graph, int numProcessors, int numThreads) {
		this.graph = graph;
		this.numProcessors = numProcessors;
		exe = Executors.newFixedThreadPool(numThreads);
	}

	/**
	 * computes the optimum processing schedule for the graph 
	 */
	public Graph computeSchedule() {
		PriorityQueue<TreeNode> q = new PriorityQueue<>();
		q.add(new TreeNode());
		while (!q.isEmpty()) {
			// Pop from priority queue
			TreeNode current = q.remove();
			// If current == goal or complete solution, then we have optimal solution
			// Uses height to determine if a schedule is complete
			if (current.getHeight() == graph.getAllNodes().size()) {
				TreeNode tail = current;
				while (tail.getNode() != null) {
					tail.getNode().setProcessor(tail.getProcessor() + 1);
					tail.getNode().setStart(tail.getStartTime());
					tail = tail.getParent();
				}
				return graph;
			}

			// Find neighbouring nodes
			Set<Node> neighbours = graph.getNeighbours(current);
			for (Node n : neighbours) {
				// Mapping is used to remove duplicate schedules, noticeable effect on inputs with large processor numbers
				Map<Integer, TreeNode> uniqueSchedules = new HashMap<Integer, TreeNode>();
				for (int i = 0; i < numProcessors; i++) {
					TreeNode candidate = new TreeNode(current, n, i);
					uniqueSchedules.put(candidate.getStartTime(), candidate);
				}
				q.addAll(uniqueSchedules.values());
			}

		}
		System.out.println("I FAILED");
		System.exit(1);
		return null;
	}

	public void computeHeuristics() {
		for (Node n : graph.getAllNodes()) {
			setBottomLevel(n);
		}
	}

	/**
	 * Sets the bottom level of a node by recursively finding the bottom level of a node's children.
	 * Uses memoization to optimize finding the bottom level.
	 * @param n the node whose bottom level should be set.
	 * @return the bottom level of Node n.
	 */
	public int setBottomLevel(Node n) {
		if (n.getBottomLevel() != 0) {
			// If we already know the bottom level, no need to recompute it
			return n.getBottomLevel();
		} else {
			// If this node has no children, bottom level is its weight
			Set<Node> children = n.getChildEdgeWeights().keySet();
			if (children.size() == 0) {
				n.setBottomLevel(n.getWeight());
				return n.getWeight();
			} else {
				// Otherwise, this node's bottom level is the max bottom level of its children + its weight
				int max = 0;
				for (Node child : children) {
					int value = setBottomLevel(child);
					if (value > max) {
						max = value;
					}
				}
				n.setBottomLevel(max);
				return max;
			}
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
		Scheduler s = new Scheduler(inputGraph, processorNumber, 1);
		s.computeHeuristics();
		Graph outputGraph = s.computeSchedule();
		outputGraph.setGraphName("output");

		//writes schedule to output file
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph(outputFileName);
	}

}
