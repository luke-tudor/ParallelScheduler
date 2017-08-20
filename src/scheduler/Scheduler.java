package scheduler;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

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

	// Number of threads to use
	private int numThreads;

	// Queue that all threads are working off, it blocks if multiple threads wish to access it at once
	private PriorityBlockingQueue<TreeNode> q = new PriorityBlockingQueue<>();
	
	private ExecutorService exe;

	// Scheduler contains the graph, the number of processors and the number of threads
	public Scheduler(Graph graph, int numProcessors, int numThreads) {
		this.graph = graph;
		this.numProcessors = numProcessors;
		exe = Executors.newFixedThreadPool(numThreads);
		this.numThreads = numThreads;
	}

	/**
	 * Computes the optimum processing schedule for the graph.
	 */
	public Graph computeSchedule() {
		q.add(new TreeNode());
		for (int i = 0; i < numThreads; i++) {
			// Submit one task for each thread
			exe.submit(new Runnable() {

				@Override
				public void run() {
					// Busy wait until there are nodes on the queue
					while (true) {
						while (!q.isEmpty()) {
							// Pop from priority queue
							TreeNode current = q.remove();
							// If current equals goal or complete solution, we have the optimal solution
							// Uses height to determine whether a schedule is complete
							if (current.getHeight() == graph.getAllNodes().size()) {
								TreeNode tail = current;
								while (tail.getNode() != null) {
									tail.getNode().setProcessor(tail.getProcessor() + 1);
									tail.getNode().setStart(tail.getStartTime());
									tail = tail.getParent();
								}
								exe.shutdownNow();
								return;
							}

							// Find neighbouring nodes
							Set<Node> neighbours = graph.getNeighbours(current);
							for (Node n : neighbours) {
								for (int i = 0; i < numProcessors; i++) {
									TreeNode candidate = new TreeNode(current, n, i);
									q.add(candidate);
								}
							}
						}
					}
				}

			});
		}
		try {
			exe.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return graph;
	}

	/**
	 * Computes the heuristics for task scheduling which utilises the bottom level.
	 * e.g. longest path to exist task starting with node.
	 */
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
			// If the bottom level is known, there is no reason to recompute it
			return n.getBottomLevel();
		} else {
			// If the node has no children, the bottom level is its weight
			Set<Node> children = n.getChildEdgeWeights().keySet();
			if (children.size() == 0) {
				n.setBottomLevel(n.getWeight());
				return n.getWeight();
			} else {
				// Otherwise, the node's bottom level is the max bottom level of its children plus its weight
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

		// Regular expression to construct the output file name from the input file name
		// Utilises the file name without the extension and concatenating it with the other half of the new file name
		String outputFileName = args[0].split("\\.")[0] + "-output.dot";

		// Creates the graph
		InputParser ip = new InputParser(inputFileName);		
		Graph inputGraph = ip.parse();

		// Finds the optimum schedule by computing the heuristics and schedule
		Scheduler s = new Scheduler(inputGraph, processorNumber, 4);
		s.computeHeuristics();
		Graph outputGraph = s.computeSchedule();
		outputGraph.setGraphName("output");

		// Writes the optimum schedule to the output file
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph(outputFileName);
	}

}
