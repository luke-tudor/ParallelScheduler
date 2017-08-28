package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import scheduler.Scheduler;
import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;
import scheduler.structures.TreeNode;

public class TestGraph {
	
	private Graph test;
	
	/**
	 * Setting up test graphs to be accessed throughout the tests
	 */
	@Before
	public void setup() {
		
		Node nodeA = new Node("A", 2);
		Node nodeB = new Node("B", 3);
		Node nodeC = new Node("C", 3);
		Node nodeD = new Node("D", 2);
		
		Edge edge1 = new Edge(nodeA, nodeB, 1);
		Edge edge2 = new Edge(nodeA, nodeC, 2);
		Edge edge3 = new Edge(nodeB, nodeD, 2);
		Edge edge4 = new Edge(nodeC, nodeD, 1);
		
		test = new Graph("simple");

		test.addNode(nodeA);
		test.addNode(nodeB);
		test.addNode(nodeC);
		test.addNode(nodeD);
		
		test.addEdge(edge1);
		test.addEdge(edge2);
		test.addEdge(edge3);
		test.addEdge(edge4);

	}

	/**
	 * Test for checking that getAllNeighbours is correcting 
	 * identifying the one neighbour on a four node graph.
	 */
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
		
		new Scheduler(graph, 2, 1);
		
		TreeNode first = new TreeNode();
		TreeNode parent = new TreeNode(first, nodeA, 1);
		TreeNode tn = new TreeNode(parent, nodeB, 1);
		
		Set<Node> neighbours = graph.getNeighbours(tn);
		
		assertEquals(neighbours.size(), 1);
		for (Node n : neighbours) {
			assertEquals(true, n.equals(nodeC));
		}
		
	}
	
	/**
	 * Test for checking that getAllNeighbours is correcting 
	 * identifying neighbours on a 12 node graph. With some 
	 * nodes having half visited parents, a node with no 
	 * parents or children, etc.
	 */
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
		Edge edge5 = new Edge(nodeG, nodeB, 1);
		Edge edge6 = new Edge(nodeG, nodeD, 2);
		Edge edge7 = new Edge(nodeG, nodeE, 2);
		Edge edge8 = new Edge(nodeI, nodeF, 1);
		Edge edge9 = new Edge(nodeI, nodeG, 1);
		Edge edgeA = new Edge(nodeI, nodeJ, 2);
		Edge edgeB = new Edge(nodeI, nodeK, 2);
		Edge edgeC = new Edge(nodeK, nodeL, 1);
		
		Graph graph = new Graph("simple");

		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);
		graph.addNode(nodeG);
		graph.addNode(nodeH);
		graph.addNode(nodeI);
		graph.addNode(nodeJ);
		graph.addNode(nodeK);
		graph.addNode(nodeL);
		
		graph.addEdge(edge1);
		graph.addEdge(edge2);
		graph.addEdge(edge3);
		graph.addEdge(edge4);
		graph.addEdge(edge5);
		graph.addEdge(edge6);
		graph.addEdge(edge7);
		graph.addEdge(edge8);
		graph.addEdge(edge9);
		graph.addEdge(edgeA);
		graph.addEdge(edgeB);
		graph.addEdge(edgeC);

		new Scheduler(graph, 2, 1);
		
		TreeNode t0 = new TreeNode();
		TreeNode t1 = new TreeNode(t0, nodeA, 1);
		TreeNode t2 = new TreeNode(t1, nodeB, 0);
		TreeNode t3 = new TreeNode(t2, nodeC, 1);
		TreeNode t4 = new TreeNode(t3, nodeI, 0);
		TreeNode t5 = new TreeNode(t4, nodeJ, 1);
		
		Set<Node> neighbours = graph.getNeighbours(t5);
		Set<Node> nodeSet = new HashSet<Node>();
		nodeSet.add(nodeF);
		nodeSet.add(nodeG);
		nodeSet.add(nodeH);
		nodeSet.add(nodeK);
		
		assertEquals(neighbours.size(), 4);
		for (Node n : neighbours) {
			assertEquals(true, nodeSet.contains(n));
		}
		
	}
	
	/**
	 * One test to check that both the methods of "getAllParentless()" 
	 * and "getAllChildless()" are correctly working. They are fairly 
	 * simple methods so only required one test each.
	 */
	@Test
	public void testGetAllLess() {
		
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
		Edge edge5 = new Edge(nodeG, nodeB, 1);
		Edge edge6 = new Edge(nodeG, nodeD, 2);
		Edge edge7 = new Edge(nodeG, nodeE, 2);
		Edge edge8 = new Edge(nodeI, nodeF, 1);
		Edge edge9 = new Edge(nodeI, nodeG, 1);
		Edge edgeA = new Edge(nodeI, nodeJ, 2);
		Edge edgeB = new Edge(nodeI, nodeK, 2);
		Edge edgeC = new Edge(nodeK, nodeL, 1);
		
		Graph graph = new Graph("simple");

		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);
		graph.addNode(nodeG);
		graph.addNode(nodeH);
		graph.addNode(nodeI);
		graph.addNode(nodeJ);
		graph.addNode(nodeK);
		graph.addNode(nodeL);
		
		graph.addEdge(edge1);
		graph.addEdge(edge2);
		graph.addEdge(edge3);
		graph.addEdge(edge4);
		graph.addEdge(edge5);
		graph.addEdge(edge6);
		graph.addEdge(edge7);
		graph.addEdge(edge8);
		graph.addEdge(edge9);
		graph.addEdge(edgeA);
		graph.addEdge(edgeB);
		graph.addEdge(edgeC);
		
		Set<Node> nodeSet = new HashSet<Node>();
		nodeSet.add(nodeA);
		nodeSet.add(nodeI);
		nodeSet.add(nodeH);
		
		Set<Node> nS = graph.getAllParentless();
		for (Node n : nS) {
			assertEquals(true, nodeSet.contains(n));
		}
		
		Set<Node> nodeSetChild = new HashSet<Node>();
		nodeSetChild.add(nodeD);
		nodeSetChild.add(nodeE);
		nodeSetChild.add(nodeF);
		nodeSetChild.add(nodeH);
		nodeSetChild.add(nodeJ);
		nodeSetChild.add(nodeL);
		
		List<Node> list = graph.getAllChildless();
		for (Node n : list) {
			assertEquals(true, nodeSetChild.contains(n));
		}
		
	}
	
	/**
	 * Test that checks the graph equals method works. The graph equals 
	 * method also tests that Node.equals() and Edge.equals() methods both 
	 * are functioning as they are called with the Graph.equals() method.
	 * Note that the .equals() method can return true even if the references 
	 * are different, assuming the values are the same.
	 */
	@Test
	public void testEquals() {
		
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
		
		assertEquals(true, graph.equals(test));
		
	}

}
