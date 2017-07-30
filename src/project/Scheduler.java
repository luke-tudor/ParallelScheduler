package project;

import project.io.InputParser;
import project.io.OutputFormatter;
import project.structures.Graph;

/**
 * This class takes a graph and a number of processes and produces a scheduled graph.
 * 
 * @author Luke Tudor
 */
public class Scheduler {
	
	Graph graph;
	int numProcessors;

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
		
		String outputFileName = args[0].split("\\.")[0] + "-output.dot";
		
		InputParser ip = new InputParser(inputFileName);		
		Graph inputGraph = ip.parse();
		
		Scheduler s = new Scheduler(inputGraph, processorNumber);
		Graph outputGraph = s.computeSchedule();
		
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph(outputFileName);
	}

}
