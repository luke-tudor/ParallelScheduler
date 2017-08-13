package scheduler;

import java.util.PriorityQueue;
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
	
	// This is the queue that all the threads will be working off, it blocks if multiple threads wish to access it at once may not even
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
			// pop from priority queue
			TreeNode current = q.remove();
			// if current == goal or complete solution, then we have optimal solution
			if (current.height == graph.getAllNodes().size()) {
				System.out.println("found it");
				TreeNode tail = current;
				while (tail.recentNode != null) {
					tail.recentNode.setProcessor(tail.recentProcessor + 1);
					tail.recentNode.setStart(tail.recentStartTime);
					System.out.println(tail.recentNode.getName() + tail.recentStartTime);
					tail = tail.parent;
				}
				return graph;
			}

			// find neighbouring nodes
			Set<Node> neighbours = graph.getNeighbours(current);
			
			for (int i = 0; i < numProcessors; i++) {
				for (Node n : neighbours) {
					q.add(new TreeNode(current, n, i));
				}
			}

		}
		System.out.println("I FAILED");
		System.exit(1);
		return null;
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
		Scheduler s = new Scheduler(inputGraph, processorNumber, 1);
		s.makeHeuristic();
		Graph outputGraph = s.computeSchedule();
		outputGraph.setGraphName("output");

		//writes schedule to output file
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph(outputFileName);
	}

}
