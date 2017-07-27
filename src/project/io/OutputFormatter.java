package project.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import project.structures.Edge;
import project.structures.Graph;
import project.structures.Node;

/**
 * This class takes a graph object and writes it to a *.dot file in the correct format.
 * 
 * @author Luke Tudor
 */
public class OutputFormatter {

	List<String> outputLines;

	public OutputFormatter(Graph graph) {
		outputLines = new ArrayList<>();
		outputLines.add("digraph output {");
		Object[] elements = graph.getAllElements();
		for (Object e : elements) {
			if (e.getClass() == Node.class) {
				Node node = (Node) e;
				String nodeEntry = String.format("\t%s\t[Weight=%d,Start=%d,Processor=%d];",
						node.getName(), node.getWeight(), node.getStart(), node.getProcessor());
				outputLines.add(nodeEntry);
			} else {
				Edge edge = (Edge) e;
				String edgeEntry = String.format("\t%s -> %s\t[Weight=%d];",
						edge.getParent(), edge.getChild(), edge.getWeight());
				outputLines.add(edgeEntry);
			}
		}
		outputLines.add("}");
	}

	public void writeGraph(String uri) {
		Path outputFile = Paths.get(uri);
		try {
			Files.write(outputFile, outputLines,
					StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
