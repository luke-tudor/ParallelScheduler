package test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import scheduler.io.InputParser;
import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;

public class TestInput {

	@Test
	public void test_7_OutTree() {

		Graph graph = new Graph(null);
		
		Node n0 = new Node("0", 5);
		Node n1 = new Node("1", 6);
		Node n2 = new Node("2", 5);
		Node n3 = new Node("3", 6);
		Node n4 = new Node("4", 4);
		Node n5 = new Node("5", 7);
		Node n6 = new Node("6", 7);
		
		graph.addNode(n0);
		graph.addNode(n1);
		graph.addNode(n2);
		graph.addNode(n3);
		graph.addNode(n4);
		graph.addNode(n5);
		graph.addNode(n6);
		
		graph.addEdge(new Edge(n0, n1, 15));
		graph.addEdge(new Edge(n0, n2, 11));
		graph.addEdge(new Edge(n0, n3, 11));
		graph.addEdge(new Edge(n1, n4, 19));
		graph.addEdge(new Edge(n1, n5, 4));
		graph.addEdge(new Edge(n1, n6, 21));
		
		InputParser in = new InputParser("testfiles/Nodes_7_OutTree.dot");
		Graph testGraph = in.parse();
		
		assertEquals(graph.equals(testGraph), true);
		
	}
	
	@Test
	public void test_11_OutTree() {

		Graph graph = new Graph(null);
		
		Node n0 = new Node("0", 50);
		Node n1 = new Node("1", 70);
		Node n2 = new Node("2", 90);
		Node n3 = new Node("3", 100);
		Node n4 = new Node("4", 40);
		Node n5 = new Node("5", 20);
		Node n6 = new Node("6", 100);
		Node n7 = new Node("7", 80);
		Node n8 = new Node("8", 50);
		Node n9 = new Node("9", 20);
		Node n10 = new Node("10", 20);
		
		graph.addNode(n0);
		graph.addNode(n1);
		graph.addNode(n2);
		graph.addNode(n3);
		graph.addNode(n4);
		graph.addNode(n5);
		graph.addNode(n6);
		graph.addNode(n7);
		graph.addNode(n8);
		graph.addNode(n9);
		graph.addNode(n10);
		
		graph.addEdge(new Edge(n0, n1, 9));
		graph.addEdge(new Edge(n0, n2, 7));
		graph.addEdge(new Edge(n0, n3, 4));
		graph.addEdge(new Edge(n1, n4, 10));
		graph.addEdge(new Edge(n1, n5, 7));
		graph.addEdge(new Edge(n1, n6, 5));
		graph.addEdge(new Edge(n2, n7, 5));
		graph.addEdge(new Edge(n2, n8, 3));
		graph.addEdge(new Edge(n2, n9, 10));
		graph.addEdge(new Edge(n3, n10, 4));
		
		InputParser in = new InputParser("testfiles/Nodes_11_OutTree.dot");
		Graph testGraph = in.parse();
		
		assertEquals(graph.equals(testGraph), true);
		
	}

}
