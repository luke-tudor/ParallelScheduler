package scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;

/**
 * 
 * @author mvan439
 *
 */
public class TestScheduler {
	
	private Graph _graph;

	@Before
	public void before() {
		_graph = new Graph("test");
	}
	
	/**
	 * cannot define heuristics for an empty graph. Expect the code to run without fault - as no heuristics can be
	 * calculated, the graph object should be returned the same.
	 */
	@Test
	public void testEmptyGraph() {
		try {
			Scheduler scheduler = new Scheduler(_graph, 1);
			scheduler.makeHeuristic();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSingleNodeGraph() {
		Node node = new Node("test", 2);
		_graph.addNode(node);
		Scheduler scheduler = new Scheduler(_graph, 1);
		scheduler.makeHeuristic();
		assertEquals(node.hvalue, 2);	
	}

	@Test
	public void testMultipleStartGraph() {
		Node nodeA = new Node("A", 2);
		Node nodeB = new Node("B", 2);
		Node nodeC = new Node("C", 2);
		Edge edgeA = new Edge("A","C",3);
		Edge edgeB = new Edge("B","C",3);
		_graph.addNode(nodeA);
		_graph.addNode(nodeB);
		_graph.addNode(nodeC);
		_graph.addEdge(edgeA);
		_graph.addEdge(edgeB);
		
		Scheduler scheduler = new Scheduler(_graph, 1);
		scheduler.makeHeuristic();
		
		assertEquals(nodeC.hvalue, 2);
		assertEquals(nodeB.hvalue, 4);
		assertEquals(nodeA.hvalue, 4);
	}
	
	@Test
	public void testMultipleEndGraph() {
		Node nodeA = new Node("A", 2);
		Node nodeB = new Node("B", 2);
		Node nodeC = new Node("C", 2);
		Edge edgeA = new Edge("A","C",3);
		Edge edgeB = new Edge("A","B",3);
		_graph.addNode(nodeA);
		_graph.addNode(nodeB);
		_graph.addNode(nodeC);
		_graph.addEdge(edgeA);
		_graph.addEdge(edgeB);
		
		Scheduler scheduler = new Scheduler(_graph, 1);
		scheduler.makeHeuristic();
		
		assertEquals(nodeB.hvalue, 2);
		assertEquals(nodeC.hvalue, 2);
		assertEquals(nodeA.hvalue, 4);
	}
	
}
