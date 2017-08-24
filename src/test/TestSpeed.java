package test;

import org.junit.Test;

import scheduler.Scheduler;
import scheduler.io.InputParser;
import scheduler.io.OutputFormatter;
import scheduler.structures.Graph;

public class TestSpeed {

	@Test
	public void test() {
		InputParser in = new InputParser("testfiles/Nodes_10_Random.dot");
		Graph g = in.parse();

		Scheduler sch = new Scheduler (g, 2, 1);
		
		long start = System.currentTimeMillis();
		Graph singleG = sch.computeSchedule();
		long end = System.currentTimeMillis();
		singleG.setGraphName("Single Core");
		
		long diff = end - start;
		System.out.println("SINGLE THREAD Time : " + diff); 
		
		OutputFormatter out = new OutputFormatter(singleG);
		out.writeGraph("Single Core-output.dot");
		
		Scheduler multiSch = new Scheduler(g, 2, 4);
		
		start = System.currentTimeMillis();
		Graph multiG = multiSch.computeSchedule();
		end = System.currentTimeMillis();
		multiG.setGraphName("Multi Core");
		
		diff = end - start;
		System.out.println("MULTI THREAD (4 of them) Time : " + diff); 
		
		out = new OutputFormatter(multiG);
		out.writeGraph("Multi Core-output.dot");
	}	
	
	@Test
	public void test16() {
		InputParser in = new InputParser("testfiles/Nodes_10_Random.dot");
		Graph g = in.parse();

		Scheduler sch = new Scheduler (g, 2, 4);
		
		long start = System.currentTimeMillis();
		Graph singleG = sch.computeSchedule();
		long end = System.currentTimeMillis();
		singleG.setGraphName("16 Core");
		
		long diff = end - start;
		System.out.println("16 THREAD Time : " + diff); 
		
	}

}
