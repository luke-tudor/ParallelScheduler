package scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import scheduler.io.InputParser;
import scheduler.io.OutputFormatter;
import scheduler.structures.Graph;
import scheduler.structures.Node;
import scheduler.structures.TreeNode;
import scheduler.visualisation.Window;

/**
 * The main class for the parallel scheduler.
 * 
 * @author Luke Tudor
 */
public class Scheduler {
	
	private static Scheduler instance;
	
	public static Scheduler getInstance() {
		return instance;
	}

	private Graph graph;
	private int numProcessors;
	private static String outputFileName;

	// Number of threads to use
	private int numThreads;
	
	
	// Queue that all threads are working off, it blocks if multiple threads wish to access it at once
	private PriorityBlockingQueue<TreeNode> q = new PriorityBlockingQueue<>();

	private ExecutorService exe;

	// Current best schedule
	private TreeNode schedule;

	private int total;
	
	private boolean isFinished;
	
	private static Thread appThread;
	
	private ConcurrentHashMap<String, Object> uniqueNodes = new ConcurrentHashMap<String, Object>();
	private Object placeholder = new Object();

	// Scheduler contains the graph, the number of processors and the number of threads
	public Scheduler(Graph graph, int numProc, int numThreads) {
		this.graph = graph;
		numProcessors = numProc;
		exe = Executors.newFixedThreadPool(numThreads);
		this.numThreads = numThreads;
		isFinished = false;
		computeHeuristics();
		instance = this;
	}

	public int getNumProc() {
		return numProcessors;
	}
	
	public String getOuputName() {
		return outputFileName;
	}
	
	public int getNumThreads() {
		return numThreads;
	}
	
	public int getNumOfNodes() {
		return graph.getAllNodes().size();
	}
	
	public TreeNode getNextTN() {
		if (isFinished) {
			return schedule;
		}
		return q.peek();
	}
	
	public Thread getAppThread() {
		return appThread;
	}

	/**
	 * Computes the optimum processing schedule for the graph.
	 */
	public Graph computeSchedule() {
		TreeNode topNode = new TreeNode();
		q.add(topNode);
		// Submit one task for each thread
		for (int i = 0; i < numThreads; i++) {
			try {
				exe.submit(new Runnable() {

					@Override
					public void run() {
						// Busy wait until there are nodes on the queue
						while (!exe.isShutdown()) {
							while (!q.isEmpty()) {
								if (exe.isShutdown()) {
									break;
								}
								// Pop from priority queue
								TreeNode current = q.remove();
								// If current equals goal or complete solution, we have the optimal solution
								// Uses height to determine whether a schedule is complete

								// Find neighbouring nodes
								Set<Node> neighbours = graph.getNeighbours(current);
								if (neighbours.isEmpty()) {
									/*
									 * Set this schedule as the optimal schedule for this graph.
									 * Gracefully terminate all tasks.
									 */
									synchronized (exe) {
										if (schedule == null || current.getStartTime() + current.getNode().getWeight() < schedule.getStartTime() + schedule.getNode().getWeight()) {
											schedule = current;
										}
										exe.shutdown();
									}
									return;
								}

								List<TreeNode> newSchedules = new ArrayList<>();
								
								// For all possible nodes that can be scheduled
								for (Node n : neighbours) {
									Set<TreeNode> processorSchedules = new HashSet<>();
									// For all processors they can be scheduled on
									for (int i = 0; i < numProcessors; i++) {
										// Get the best possible schedule
										TreeNode candidate = new TreeNode(current, n, i);
										// Get the string representation of the schedule
										String candidateString = candidate.getString();
										// If we have already seen it, try the next schedule
										if (uniqueNodes.get(candidateString) != null) {
											continue;
										// Otherwise, store the string representation and add the schedule to the queue
										} else {
											uniqueNodes.put(candidateString, placeholder);
										}
										processorSchedules.add(candidate);
									}
									newSchedules.addAll(processorSchedules);
								}
								q.addAll(newSchedules);
							}
						}
					}
				});
			} catch (RejectedExecutionException e) {
				// If task can't be scheduled, it's because executor was shutdown
				break;
			}
		}
		try {
			exe.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// We aren't interrupting this thread so an exception will never be thrown
		}
		TreeNode tail = schedule;
		while (tail.getNode() != null) {
			tail.getNode().setProcessor(tail.getProcessor() + 1);
			tail.getNode().setStart(tail.getStartTime());
			tail = tail.getParent();
		}
		System.err.println("I FINISHED!");
		isFinished = true;
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
		setPerfectBalance();
	}

	private void setPerfectBalance() {
		int total = 0;
		Collection<Node> nodes = graph.getAllNodes();
		for (Node n : nodes) {
			total += n.getWeight();
		}
		this.total = total;
	}

	public int getTotal() {
		return total;
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
		outputFileName = args[0].split("\\.")[0] + "-output.dot";
		
		int numThreads = 1;
		
		boolean isVisual = false;
		if (args.length > 2) {
			for (int i = 2; i < args.length; i++) {
				if (args[i].equals("-v")) {
					isVisual = true;
				} else if (args[i].equals("-o")) {
					i++;
					outputFileName = args[i];
				} else if (args[i].equals("-N")) {
					i++;
					numThreads = Integer.parseInt(args[i]);
				}
			}
		}

		// Creates the graph
		InputParser ip = new InputParser(inputFileName);		
		Graph inputGraph = ip.parse();

		// Finds the optimum schedule by computing the heuristics and schedule
		Scheduler s = new Scheduler(inputGraph, processorNumber, numThreads);
		
		if (isVisual) {
			appThread = new Thread() {
				public void run() {
					Application.launch(Window.class, args);
				}
			};
			appThread.start();
		}
		
		Graph outputGraph = s.computeSchedule();
		outputGraph.setGraphName("output");

		// Writes the optimum schedule to the output file
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph(outputFileName);
	}

}
