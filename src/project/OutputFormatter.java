package project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class OutputFormatter {

	Graph g;

	public OutputFormatter(Graph graph) {
		g = graph;
	}

	public void writeGraph() {
		Path outputFile = Paths.get("OUTPUT.dot");
		List<String> lines = new ArrayList<String>();
		lines.add("digraph output {");
		Object[] elements = g.getAllElements();
		for (Object e : elements) {
			if (e.getClass() == Node.class) {
				Node node = (Node) e;
				String nodeEntry = String.format("\t%s\t[Weight=%d,Start=%d,Processor=%d];",
						node.getName(), node.getWeight(), node.getStart(), node.getProcessor());
				lines.add(nodeEntry);
			} else {
				Edge edge = (Edge) e;
				String edgeEntry = String.format("\t%s -> %s\t[Weight=%d];",
						edge.getParent(), edge.getChild(), edge.getWeight());
				lines.add(edgeEntry);
			}
		}
		lines.add("}");
		try {
			Files.write(outputFile, lines,
					StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
