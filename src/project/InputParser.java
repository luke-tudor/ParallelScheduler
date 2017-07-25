package project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import project.Graph.ElementType;

public class InputParser {

	List<String> graphLines;

	public InputParser(String fileName) {
		Path file = Paths.get(fileName);		
		try {
			this.graphLines = Files.readAllLines(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Graph parse() {
		Graph graph = new Graph();
		for(int i = 1; i < graphLines.size() - 1; i++) {
			String[] tokens = graphLines.get(i).split("\\s+");
			if (tokens.length == 3) {
				Node n = new Node();
				n.name = tokens[1];
				n.weight = Integer.parseInt(tokens[2].replaceAll("\\D", ""));
				graph.nodes.add(n);
				graph.order.add(ElementType.NODE);
			} else {
				Edge e = new Edge();
				e.parent = tokens[1];
				e.child = tokens[3];
				e.weight = Integer.parseInt(tokens[4].replaceAll("\\D", ""));
				graph.edges.add(e);
				graph.order.add(ElementType.EDGE);
			}
		}
		return graph;
	}
}
