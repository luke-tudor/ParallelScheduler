package test;

import static org.junit.Assert.*;

import org.junit.Test;

import scheduler.io.InputParser;
import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;

public class TestInput {

	/**
	 * Testing that the InputParser is correctly reading the "Nodes_7_OutTree.dot" file 
	 * and creating the corresponding graph correctly.
	 */
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
		
		assertEquals(true, graph.equals(testGraph));
		
	}
	
	/**
	 * Testing that the InputParser is correctly reading the "Nodes_11_OutTree.dot" file 
	 * and creating the corresponding graph correctly.
	 */
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
		
		assertEquals(true, graph.equals(testGraph));
		
	}
	
	/**
	 * Testing that the InputParser is correctly reading the "Nodes_10_Random.dot" file 
	 * and creating the corresponding graph correctly. This file contains additional 
	 * information at the top of the file which other dot file may not contain, so this 
	 * test ensures that the InputParser handles this information correctly.
	 */
	@Test
	public void test_10_Random() {

		Graph graph = new Graph(null);
		
		Node n0 = new Node("0", 6);
		Node n3 = new Node("3", 10);
		Node n4 = new Node("4", 3);
		Node n9 = new Node("9", 8);
		Node n1 = new Node("1", 5);
		Node n2 = new Node("2", 5);
		Node n5 = new Node("5", 7);
		Node n6 = new Node("6", 8);
		Node n8 = new Node("8", 8);
		Node n7 = new Node("7", 3);
		
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
		
		graph.addEdge(new Edge(n0, n3, 34));
		graph.addEdge(new Edge(n0, n4, 24));
		graph.addEdge(new Edge(n0, n9, 44));
		graph.addEdge(new Edge(n1, n2, 48));
		graph.addEdge(new Edge(n1, n5, 19));
		graph.addEdge(new Edge(n1, n6, 39));
		graph.addEdge(new Edge(n2, n3, 10));
		graph.addEdge(new Edge(n2, n7, 48));
		graph.addEdge(new Edge(n2, n8, 48));
		graph.addEdge(new Edge(n4, n6, 10));
		graph.addEdge(new Edge(n4, n7, 48));
		graph.addEdge(new Edge(n4, n8, 48));
		graph.addEdge(new Edge(n4, n9, 39));
		graph.addEdge(new Edge(n6, n7, 15));
		graph.addEdge(new Edge(n6, n8, 39));
		graph.addEdge(new Edge(n6, n9, 29));
		graph.addEdge(new Edge(n7, n8, 15));
		graph.addEdge(new Edge(n7, n9, 34));
		graph.addEdge(new Edge(n8, n9, 39));
		
		InputParser in = new InputParser("testfiles/Nodes_10_Random.dot");
		Graph testGraph = in.parse();
		
		assertEquals(true, graph.equals(testGraph));
		
	}

}
