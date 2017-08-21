package test;

import org.junit.Test;

import scheduler.Scheduler;
import scheduler.io.InputParser;
import scheduler.structures.Graph;

public class TestSpeed {

	@Test
	public void test() {
		InputParser in = new InputParser("testfiles/Nodes_11_OutTree.dot");
		Graph g = in.parse();
		
		long start = System.currentTimeMillis();
		//Scheduler sch = new Scheduler(g, 2, 1);
		//sch.computeSchedule();
		long end = System.currentTimeMillis();
		
		long diff = end - start;
		System.out.println("SINGLE THREAD Time : " + diff); 
		
		start = System.currentTimeMillis();
		Scheduler multiSch = new Scheduler(g, 2, 4);
		multiSch.computeSchedule();
		end = System.currentTimeMillis();
		
		diff = end - start;
		System.out.println("MULTI THREAD (4 of them) Time : " + diff); 
	}

}
