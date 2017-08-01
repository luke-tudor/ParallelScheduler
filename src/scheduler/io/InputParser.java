package scheduler.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;

/**
 * This class converts a *.dot file into a Graph object.
 * 
 * @author Luke Tudor
 */
public class InputParser {

	private List<String> graphLines;

	/**
	 * This constructor takes a string path and reads all the lines of the file located at that path
	 * @param path
	 */
	public InputParser(String path) {
		Path file = Paths.get(path);		
		try {
			this.graphLines = Files.readAllLines(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method parses the input file into a graph data structure using regular expressions and string slicing
	 * @return graph
	 */
	public Graph parse() {
		Graph graph = new Graph();
		// Ignore line 1 of the graph
		// For lines 1 to n -1, split the lines of the graph file by whitespace '\\s+'
		for (int i = 1; i < graphLines.size() - 1; i++) {
			String[] tokens = graphLines.get(i).split("\\s+");
			// If there are only 3 strings left after splitting by whitespace, the line is a node
			if (tokens.length == 3) {
				// Extract weight property by removing non-decimal characters, replace '\\D' with empty strings
				int nodeWeight = Integer.parseInt(tokens[2].replaceAll("\\D", ""));
				Node n = new Node(tokens[1], nodeWeight);
				graph.addNode(n);
			// Otherwise, the line is an edge
			} else {
				// Extract weight property by removing non-decimal characters, replace '\\D' with empty strings
				int edgeWeight = Integer.parseInt(tokens[4].replaceAll("\\D", ""));
				Edge e = new Edge(tokens[1], tokens[3], edgeWeight);
				graph.addEdge(e);
			}
		}
		// Ignore line n
		return graph;
	}
}
