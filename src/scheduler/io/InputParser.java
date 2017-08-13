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
 * Converts a *.dot file into a Graph object.
 *
 * The .dot file must be in the form 
 * digraph "graph_title" {
 * 	X	|Weight=N|;
 *	X -> Y 	|Weight=M|;
 *}
 * Where X and Y are titles of Nodes, and N and M are their respective weights. 
 * A node must appear before an edge containing the node.
 *
 * @author Luke Tudor
 */
public class InputParser {

	private List<String> graphLines;

	/**
	 * Takes a file path and reads all the lines of the file located at that path
	 * 
	 * @param path The String representation of the path to the .dot file
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
	 * Parses the input file into a graph data structure using regular expressions and string splicing
	 *
	 * @return graph The graph representation of the previously read input file.
	 */
	public Graph parse() {
		Graph graph = new Graph(null);
		// Ignore line 1 of the graph
		// For lines 1 to n -1, split the lines of the graph file by whitespace '\\s+'
		for (int i = 1; i < graphLines.size() - 1; i++) {
			String[] tokens = graphLines.get(i).split("\\s+");
			if (tokens[1].equals("graph")) {
				while(!tokens[1].equals("];")) {
					i++;
					tokens = graphLines.get(i).split("\\s+");
				}
			}
			// If there are only 3 strings left after splitting by whitespace, the line is a node
			if (tokens.length == 3) {
				// Extract weight property by removing non-decimal characters, replace '\\D' with empty strings
				int nodeWeight = Integer.parseInt(tokens[2].replaceAll("\\D", ""));
				Node n = new Node(tokens[1], nodeWeight);
				graph.addNode(n);
			// Otherwise, the line is an edge
			} else if (tokens.length == 5) {
				// Extract weight property by removing non-decimal characters, replace '\\D' with empty strings
				int edgeWeight = Integer.parseInt(tokens[4].replaceAll("\\D", ""));
				Edge e = new Edge(graph.getNode(tokens[1]), graph.getNode(tokens[3]), edgeWeight);
				graph.addEdge(e);
			}
		}
		// Ignore line n
		return graph;
	}
}
