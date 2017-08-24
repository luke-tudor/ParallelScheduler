package test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;
import scheduler.structures.TreeNode;

public class TestGraph {

	@Test
	public void testGetAllNeighbours() {
				
		Node nodeA = new Node("A", 2);
		Node nodeB = new Node("B", 3);
		Node nodeC = new Node("C", 3);
		Node nodeD = new Node("D", 2);
		
		Edge edge1 = new Edge(nodeA, nodeB, 1);
		Edge edge2 = new Edge(nodeA, nodeC, 2);
		Edge edge3 = new Edge(nodeB, nodeD, 2);
		Edge edge4 = new Edge(nodeC, nodeD, 1);
		
		Graph graph = new Graph("simple");

		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		
		graph.addEdge(edge1);
		graph.addEdge(edge2);
		graph.addEdge(edge3);
		graph.addEdge(edge4);
		
		TreeNode first = new TreeNode();
		TreeNode parent = new TreeNode(first, nodeA, 1);
		TreeNode tn = new TreeNode(parent, nodeB, 1);
		
		Set<Node> neighbours = graph.getNeighbours(tn);
		for (Node n : neighbours) {
			System.out.println(n.getName());
		}
		
		assertEquals(neighbours.size(), 1);
		for (Node n : neighbours) {
			assertEquals(n.equals(nodeC), true);
		}
		
	}
	
	@Test
	public void testGetAllNeighbours1() {

		Node nodeA = new Node("A", 2);
		Node nodeB = new Node("B", 3);
		Node nodeC = new Node("C", 3);
		Node nodeD = new Node("D", 2);
		Node nodeE = new Node("E", 2);
		Node nodeF = new Node("F", 3);
		Node nodeG = new Node("G", 3);
		Node nodeH = new Node("H", 2);
		Node nodeI = new Node("I", 2);
		Node nodeJ = new Node("J", 3);
		Node nodeK = new Node("K", 3);
		Node nodeL = new Node("L", 2);

		Edge edge1 = new Edge(nodeA, nodeB, 1);
		Edge edge2 = new Edge(nodeA, nodeC, 2);
		Edge edge3 = new Edge(nodeB, nodeD, 2);
		Edge edge4 = new Edge(nodeC, nodeD, 1);
		Edge edge5 = new Edge(nodeA, nodeB, 1);
		Edge edge6 = new Edge(nodeA, nodeC, 2);
		Edge edge7 = new Edge(nodeB, nodeD, 2);
		Edge edge8 = new Edge(nodeC, nodeD, 1);
		Edge edge9 = new Edge(nodeA, nodeB, 1);
		Edge edgeA = new Edge(nodeA, nodeC, 2);
		Edge edgeB = new Edge(nodeB, nodeD, 2);
		Edge edgeC = new Edge(nodeC, nodeD, 1);
		
		Graph graph = new Graph("simple");

		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		
		graph.addEdge(edge1);
		graph.addEdge(edge2);
		graph.addEdge(edge3);
		graph.addEdge(edge4);
		
		TreeNode first = new TreeNode();
		TreeNode parent = new TreeNode(first, nodeA, 1);
		TreeNode tn = new TreeNode(parent, nodeB, 1);
		
		Set<Node> neighbours = graph.getNeighbours(tn);
		for (Node n : neighbours) {
			System.out.println(n.getName());
		}
		
		assertEquals(neighbours.size(), 1);
		for (Node n : neighbours) {
			assertEquals(n.equals(nodeC), true);
		}
		
	}

}
