package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import scheduler.io.InputParser;
import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;

public class TestScheduler {
	
	private Map<String, Graph> _graphs = new HashMap<String, Graph>();
	
	@Before
	public void setup() {
		
		Graph g = new Graph("simple");
		
		Node n1 = new Node("a", 2);
		Node n2 = new Node("b", 3);
		Node n3 = new Node("c", 3);
		Node n4 = new Node("d", 2);

		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		g.addNode(n4);

		g.addEdge(new Edge(n1, n2, 1));
		g.addEdge(new Edge(n1, n3, 2));
		g.addEdge(new Edge(n2, n4, 2));
		g.addEdge(new Edge(n3, n4, 1));
		
		_graphs.put(g.getGraphName(), g);
		
		InputParser in = new InputParser("testfiles/Node_9_SeriesParallel.dot");
		_graphs.put("Node_9_SeriesParallel", in.parse());
		
		in = new InputParser("testfiles/Nodes_10_Random.dot");
		_graphs.put("Node_10_Random", in.parse());
		
		in = new InputParser("testfiles/Nodes_11_OutTree.dot");
		_graphs.put("Node_11_OutTree", in.parse());
		
		Graph graph = new Graph("simple2");
		
		Node node1 = new Node("a", 2);
		Node node2 = new Node("b", 3);
		Node node3 = new Node("c", 3);
		Node node4 = new Node("d", 2);

		graph.addNode(node1);
		graph.addNode(node2);
		graph.addNode(node3);
		graph.addNode(node4);

		graph.addEdge(new Edge(node1, node2, 1));
		graph.addEdge(new Edge(node1, node3, 2));
		graph.addEdge(new Edge(node2, node4, 3));
		graph.addEdge(new Edge(node3, node4, 1));
		
		_graphs.put(graph.getGraphName(), graph);
		
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}