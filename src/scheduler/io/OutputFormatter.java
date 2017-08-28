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
 */
public class OutputFormatter {

	// A list to output data into the desired format
	private List<String> outputLines;

	/**
	 * Takes a graph object and formats it into *.dot format 
	 * using a list of strings
	 * 
	 * This provides a valid .dot format by adding all the nodes, followed by all
	 * the edges, guaranteeing for a given edge that the nodes it points to are already 
	 * in the graph.
	 * 
	 * @param graph The graph to reformat
	 */
	public OutputFormatter(Graph graph) {
		outputLines = new ArrayList<>();
		// Write graph designation and name
		outputLines.add("digraph " + graph.getGraphName() + " {");
		
		//for every Node format the output appropriately
		for (Node node : graph.getAllNodes()) {
			String nodeEntry = String.format("\t%s\t[Weight=%d,Start=%d,Processor=%d];",
			node.getName(), node.getWeight(), node.getStart(), node.getProcessor());
			outputLines.add(nodeEntry);
		} 

		//  get all the edges and format appropriately
		for (Edge edge : graph.getAllEdges()){
			String edgeEntry = String.format("\t%s -> %s\t[Weight=%d];",
			edge.getParent().getName(), edge.getChild().getName(), edge.getWeight());
			outputLines.add(edgeEntry);
			
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
