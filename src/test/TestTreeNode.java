package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import scheduler.Scheduler;
import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;
import scheduler.structures.TreeNode;

public class TestTreeNode {

	@Test
	public void testEquals() {
		
		Node nodeA = new Node("A", 2);
		Node nodeB = new Node("B", 3);
		Node nodeC = new Node("C", 3);
		Node nodeD = new Node("D", 2);
		
		Edge edge1 = new Edge(nodeA, nodeB, 1);
		Edge edge2 = new Edge(nodeA, nodeC, 1);
		Edge edge3 = new Edge(nodeB, nodeD, 1);
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
		
		Scheduler sch = new Scheduler(graph, 2, 2);
		
		TreeNode t1 = new TreeNode();
		TreeNode t2 = new TreeNode(t1, nodeA, 1);
		
		assertEquals(true, t2.equals(t2));
		
		TreeNode t3 = new TreeNode(t2, nodeB, 1);
		TreeNode t4 = new TreeNode(t2, nodeB, 0);
		
		assertEquals(false, t3.equals(t4));
		assertEquals(false, t3.equals(t2));
		

		TreeNode t5 = new TreeNode(t2, nodeC, 1);

		assertEquals(false, t5.equals(t3));
		assertEquals(false, t5.equals(t4));
		
		TreeNode t6 = new TreeNode(t5, nodeB, 0);
		TreeNode t7 = new TreeNode(t3, nodeC, 0);
		
		assertEquals(true, t6.equals(t7));
		
		TreeNode t8 = new TreeNode(t4, nodeC, 1);
		
		assertEquals(true, t8.equals(t6));
		assertEquals(true, t8.equals(t7));
		
		TreeNode t9 = new TreeNode(t3, nodeC, 1);

		assertEquals(false, t8.equals(t9));
		
	}

}
