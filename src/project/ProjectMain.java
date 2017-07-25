package project;

public class ProjectMain {
	
	Graph graph;
	int numProcessors;

	public ProjectMain(Graph graph, int numProcessors) {
		this.graph = graph;
		this.numProcessors = numProcessors;
	}

	private Graph computeSchedule() {
		return graph;
	}
	
	public static void main(String[] args) {
		String inputFileName = args[0];
		int processorNumber = Integer.parseInt(args[1]);
		
		InputParser ip = new InputParser(inputFileName);		
		Graph inputGraph = ip.parse();
		
		ProjectMain pm = new ProjectMain(inputGraph, processorNumber);
		Graph outputGraph = pm.computeSchedule();
		
		OutputFormatter of = new OutputFormatter(outputGraph);
		of.writeGraph();
	}

}
