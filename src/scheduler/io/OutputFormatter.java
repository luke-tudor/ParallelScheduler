package scheduler.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;

/**
 * This class takes a graph object and writes it to a *.dot file in the correct format.
 * 
 * @author Luke Tudor
 */
public class OutputFormatter {

	private List<String> outputLines;

	/**
	 * This constructor takes a graph object and formats it into a *.dot format list of strings
	 * @param graph
	 */
	public OutputFormatter(Graph graph, String graphName) {
		outputLines = new ArrayList<>();
		// Write graph designation and name
		outputLines.add("digraph " + graphName + " {");
		// Get all elements from the graph object, in the order they were read
		Object[] elements = graph.getAllElements();
		for (Object e : elements) {
			// For each object, if it is a node, format the output appropriately
			if (e.getClass() == Node.class) {
				Node node = (Node) e;
				String nodeEntry = String.format("\t%s\t[Weight=%d,Start=%d,Processor=%d];",
						node.getName(), node.getWeight(), node.getStart(), node.getProcessor());
				outputLines.add(nodeEntry);
			// Otherwise, it must be an edge, so format appropriately
			} else {
				Edge edge = (Edge) e;
				String edgeEntry = String.format("\t%s -> %s\t[Weight=%d];",
						edge.getParent(), edge.getChild(), edge.getWeight());
				outputLines.add(edgeEntry);
			}
		}
		outputLines.add("}");
	}

	/**
	 * This method writes a formated graph object, creating a file at the specified path name.
	 * This method creates a file if none exists, deletes all content from a file if one exists, and writes graph info to that file.
	 * @param path
	 */
	public void writeGraph(String path) {
		Path outputFile = Paths.get(path);
		try {
			Files.write(outputFile, outputLines,
					StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
