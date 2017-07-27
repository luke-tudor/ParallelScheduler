package project;

import project.io.InputParser;
import project.io.OutputFormatter;
import project.structures.Graph;

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
		
		InputParser ip = new InputParser(inputFileName);		
		Graph inputGraph = ip.parse();
		
		Scheduler s = new Scheduler(inputGraph, processorNumber);
		Graph outputGraph = s.computeSchedule();
		
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph();
	}

}
