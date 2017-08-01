package scheduler;

import scheduler.io.InputParser;
import scheduler.io.OutputFormatter;
import scheduler.structures.Graph;

/**
 * This class takes a graph and a number of processes and produces a scheduled graph.
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

	public Graph computeSchedule() {
		return graph;
	}
	
	public static void main(String[] args) {
		String inputFileName = args[0];
		int processorNumber = Integer.parseInt(args[1]);
		
		// Use regular expression to construct output file name from input file name
		// Works by taking the file name without the extension and concatenating with the other half of the new name
		String outputFileName = args[0].split("\\.")[0] + "-output.dot";
		
		InputParser ip = new InputParser(inputFileName);		
		Graph inputGraph = ip.parse();
		
		Scheduler s = new Scheduler(inputGraph, processorNumber);
		Graph outputGraph = s.computeSchedule();
		outputGraph.setGraphName("output");
		
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph(outputFileName);
	}

}
