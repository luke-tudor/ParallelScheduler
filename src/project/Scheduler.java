package project;

public class Scheduler {
	
	Graph inputGraph;
	int numProcessors;

	public Scheduler(Graph graph, int numProcessors) {
		this.inputGraph = graph;
		this.numProcessors = numProcessors;
	}

	private Graph computeSchedule() {
		return inputGraph;
	}
	
	public static void main(String[] args) {
		String inputFileName = args[0];
		int processorNumber = Integer.parseInt(args[1]);
		
		InputParser ip = new InputParser(inputFileName);		
		Graph inputGraph = ip.parse();
		
		Scheduler pm = new Scheduler(inputGraph, processorNumber);
		Graph outputGraph = pm.computeSchedule();
		
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph();
	}

}
