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
 * Writes a *.dot file in the correct format.
 * 
 * Currently the ordering of the *.dot file is reliant on the order of graph objects given by the Graph.getAllElements() 
 * method
 * 
 * @author Luke Tudor
 *
 * TODO: test the output file for correct ordering of the nodes and edges.
 */
public class OutputFormatter {

	private List<String> outputLines;

	/**
	 * Takes a graph object and formats it into *.dot format 
	 * using a list of strings
	 * 
	 * @param graph The graph to reformat
	 */
	public OutputFormatter(Graph graph) {
		outputLines = new ArrayList<>();
		// Write graph designation and name
		outputLines.add("digraph " + graph.getGraphName() + " {");
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
						edge.getParent().getName(), edge.getChild().getName(), edge.getWeight());
				outputLines.add(edgeEntry);
			}
		}
		outputLines.add("}");
	}

	/**
	 * Writes a formated graph object, creating a file at the specified path name.
	 * If the file already exists it deletes it, and writes graph info to that file.
	 *
	 * @param path The path to the file to write to, in String format
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
