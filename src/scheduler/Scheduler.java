package scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import scheduler.io.InputParser;
import scheduler.io.OutputFormatter;
import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;

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
		// set of nodes we can reach
		PriorityQueue<Node> openSet = new PriorityQueue<>();
		// set of nodes we have evaluated
		List<Node> closedSet = new ArrayList<>();
		
		while (!openSet.isEmpty()) {
			// pop from priority queue
			Node s = openSet.remove();
			// if current == goal or complete solution, then we have optimal solution
			
			// find neighbouring nodes, to be cleaned up
			Edge[] childEdges = new Edge[0];
			Object[] otherElements = graph.getAllElements();
			List<Node> otherNodes = new ArrayList<>();
			for (Object o : otherElements) {
				if (o.getClass() == Node.class && !o.equals(s)) {
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
					openSet.add(n);
				}
				
				// if this is not a good path, continue
				
				// if this is a good path, update estimates
			}
		}
		return graph;
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
		Graph outputGraph = s.computeSchedule();
		outputGraph.setGraphName("output");
		
		//writes schedule to output file
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph(outputFileName);
	}

}
