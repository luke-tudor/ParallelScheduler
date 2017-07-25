package project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import project.Graph.ElementType;

public class OutputFormatter {

	Graph g;

	public OutputFormatter(Graph graph) {
		g = graph;
	}

	public void writeGraph() {
		Path outputFile = Paths.get("OUTPUT.dot");
		List<String> lines = new ArrayList<String>();
		lines.add("digraph output {");
		int nodeNum = 0;
		int edgeNum = 0;
		for (ElementType et : g.order) {
			if (et == ElementType.NODE) {
				Node n = g.nodes.get(nodeNum);
				String nodeEntry = String.format("\t%s\t[Weight=%d,Start=%d,Processor=%d];",
						n.name, n.weight, n.start, n.processor);
				lines.add(nodeEntry);
				nodeNum++;
			} else {
				Edge e = g.edges.get(edgeNum);
				String edgeEntry = String.format("\t%s -> %s\t[Weight=%d]",
						e.parent, e.child, e.weight);
				lines.add(edgeEntry);
				edgeNum++;
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
