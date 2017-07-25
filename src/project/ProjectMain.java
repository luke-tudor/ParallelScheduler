package project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ProjectMain {
	
	Graph graph;
	int numProcessors;

	public ProjectMain(List<String> graphLines, int numProcessors) {
		InputParser ip = new InputParser(graphLines);
		graph = ip.parse();
		this.numProcessors = numProcessors;
	}

	private void computeSchedule() {
		OutputFormatter of = new OutputFormatter(graph);
		of.writeGraph();
	}
	
	public static void main(String[] args) throws IOException {
		Path graphFile = Paths.get(args[0]);
		int processorNumber = Integer.parseInt(args[1]);
		List<String> graphLines = Files.readAllLines(graphFile);
		
		ProjectMain pm = new ProjectMain(graphLines, processorNumber);
		pm.computeSchedule();
	}

}
