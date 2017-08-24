package test;

import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;

import org.junit.Before;
import org.junit.Test;

import scheduler.Scheduler;
import scheduler.io.InputParser;
import scheduler.io.OutputFormatter;
import scheduler.structures.Graph;

public class TestSpeed {
	
	private Constructor<Scheduler> c;
	
	@Before
	public void setup() {
		Class[] pt = new Class[3];
		pt[0] = Graph.class;
		pt[1] = int.class;
		pt[2] = int.class;
		
		try {
			c = Scheduler.class.getDeclaredConstructor(pt);
			c.setAccessible(true);
		} catch (Exception e) {
			fail("Failed to get Constructor");
		}
	}

	@Test
	public void test() {
		InputParser in = new InputParser("testfiles/Nodes_8_Random.dot");
		Graph g = in.parse();
		
		Object[] obj = new Object[3];
		obj[0] = g;
		obj[1] = 2;
		obj[2] = 1;
		Scheduler sch = null;
		try {
			sch = c.newInstance(obj);
		} catch (Exception e) {
			fail("Failed to get Constructor");
		}
		
		long start = System.currentTimeMillis();
		Graph singleG = sch.computeSchedule();
		long end = System.currentTimeMillis();
		singleG.setGraphName("Single Core");
		
		long diff = end - start;
		System.out.println("SINGLE THREAD Time : " + diff); 
		
		OutputFormatter out = new OutputFormatter(singleG);
		out.writeGraph("Single Core-output.dot");
		
		obj[0] = g;
		obj[1] = 2;
		obj[2] = 4;
		Scheduler multiSch = null;
		try {
			sch = c.newInstance(obj);
		} catch (Exception e) {
			fail("Failed to get Constructor");
		}
		
		start = System.currentTimeMillis();
		Graph multiG = multiSch.computeSchedule();
		end = System.currentTimeMillis();
		multiG.setGraphName("Multi Core");
		
		diff = end - start;
		System.out.println("MULTI THREAD (4 of them) Time : " + diff); 
		
		out = new OutputFormatter(multiG);
		out.writeGraph("Multi Core-output.dot");
	}

}
