package test;

import static org.junit.Assert.*;
import org.junit.Test;

import scheduler.Scheduler;
import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;
import scheduler.structures.TreeNode;

public class TestTreeNode {
	
	/**
	 * Test to ensure the construction of a TreeNode is correct. The TreeNode 
	 * constructor assigns a start time to a Node on a given processor. This 
	 * test ensures that the assigned start time is correct and does not 
	 * conflict with the parent weights, and the finish time of the previous 
	 * task on the given processor.
	 */
	@Test
	public void testConstructor() {
		
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
		
		new Scheduler(graph, 2, 1);
		
		TreeNode t0 = new TreeNode();
		
		assertEquals(t0.getNode(), null);
		assertEquals(t0.getParent(), null);
		assertEquals(t0.getProcessor(), -1);
		assertEquals(t0.getStartTime(), -1);
		
		TreeNode t1 = new TreeNode(t0, nodeA, 1);
		
		assertEquals(t1.getNode(), nodeA);
		assertEquals(t1.getParent(), t0);
		assertEquals(t1.getProcessor(), 1);
		assertEquals(t1.getStartTime(), 0);
		
		TreeNode t2 = new TreeNode(t1, nodeB, 1);
		
		assertEquals(t2.getNode(), nodeB);
		assertEquals(t2.getParent(), t1);
		assertEquals(t2.getProcessor(), 1);
		assertEquals(t2.getStartTime(), 2);		
		
		TreeNode t3 = new TreeNode(t1, nodeB, 0);
		
		assertEquals(t3.getNode(), nodeB);
		assertEquals(t3.getParent(), t1);
		assertEquals(t3.getProcessor(), 0);
		assertEquals(t3.getStartTime(), 3);
			
	}

}
