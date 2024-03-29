package test;

import org.junit.Test;

import scheduler.Scheduler;
import scheduler.io.InputParser;
import scheduler.io.OutputFormatter;
import scheduler.structures.Graph;

public class TestSpeed {

	/**
	 * Test that will always pass.
	 * This test was only implemented to easily time the algorithm for the file Nodes_11_OutTree.dot
	 * This test will print the time taken in milliseconds for the algorithm to compute a schedule 
	 * using one thread and using 4 threads to allow quick comparison between the 2.
	 */
	@Test
	public void test() {
		InputParser in = new InputParser("testfiles/Nodes_11_OutTree.dot");
		Graph g = in.parse();
		
		long start = System.currentTimeMillis();
		Scheduler sch = new Scheduler (g, 4, 1);
		Graph singleG = sch.computeSchedule();
		long end = System.currentTimeMillis();
		singleG.setGraphName("Single Core");
		
		long diff = end - start;
		System.out.println("SINGLE THREAD Time : " + diff); 
		
		OutputFormatter out = new OutputFormatter(singleG);
		out.writeGraph("Single Core-output.dot");
		
		start = System.currentTimeMillis();
		Scheduler multiSch = new Scheduler(g, 4, 4);
		Graph multiG = multiSch.computeSchedule();
		end = System.currentTimeMillis();
		multiG.setGraphName("Multi Core");
		
		diff = end - start;
		System.out.println("MULTI THREAD (4 of them) Time : " + diff); 
		
		out = new OutputFormatter(multiG);
		out.writeGraph("Multi Core-output.dot");
	}	
	
}
