package project.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import project.structures.Edge;
import project.structures.Graph;
import project.structures.Node;

/**
 * This class converts a *.dot file into a Graph object.
 * 
 * @author Luke Tudor
 */
public class InputParser {

	private List<String> graphLines;

	public InputParser(String path) {
		Path file = Paths.get(path);		
		try {
			this.graphLines = Files.readAllLines(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Graph parse() {
		Graph graph = new Graph();
		for (int i = 1; i < graphLines.size() - 1; i++) {
			String[] tokens = graphLines.get(i).split("\\s+");
			if (tokens.length == 3) {
				int nodeWeight = Integer.parseInt(tokens[2].replaceAll("\\D", ""));
				Node n = new Node(tokens[1], nodeWeight);
				graph.addNode(n);
			} else {
				int edgeWeight = Integer.parseInt(tokens[4].replaceAll("\\D", ""));
				Edge e = new Edge(tokens[1], tokens[3], edgeWeight);
				graph.addEdge(e);
			}
		}
		return graph;
	}
}
