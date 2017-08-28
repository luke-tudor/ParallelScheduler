package scheduler.visualisation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

/**
 * 
 * @author nathan
 *
 */

public class WindowTimer extends TimerTask {

	private Window _window;
	
	public WindowTimer(Window window) {
		super();
		_window = window;
	}
	
	@Override
	public void run() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now)); 
		
		// _window.setTreeNode(call method from scheduler);
		// _window.setNumOfTreeNodes(call method from scheduler);
		// _window.setnumof nodes done
	}

}
